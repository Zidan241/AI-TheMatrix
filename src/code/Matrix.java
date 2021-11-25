package code;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class Matrix extends GenericSearch {
    public Matrix(String[] operators, State initialState) {
        super(operators, initialState);
    }

    public boolean GoalTest(State currentState) {
        //We reach a goal state if there are no more alive hostages to save
        if(currentState.hostageLocationX.isEmpty() && currentState.hostagesDead==0){
            return true;
        }
        else{
            return false;
        }
    }

    public int PathCost(State state, State nextState) {

        //IMPORTANT NOTE:
        //note that we will prioritize deaths then kills then number of actions
        //therefore cost of deaths > cost of kills > cost of actions

        //we will add the cose to go from state to nextState, were state represents the parent's state

        //we will add one for each step taken
        int stepCost = 1;

        //for each agent taken we will increase the cost by 5
        stepCost += (nextState.agentsKilled - state.agentsKilled) * 5;

        //for each hostage death we will increase the cost by 20
        stepCost += (nextState.hostagesDead - state.hostagesDead) * 20;

        return stepCost;
    }

    public State ApplyOperator(State state, String operator) {
        //if neo dies he cannot do any operation or there is no one else left to save, because we know that this state is NOT a goal state
        if(state.neoDamage==100 || (state.hostageLocationX.isEmpty())){
            return null;
        }

        State newState = new State(
            state.n, 
            state.m, 
            state.c, 
            state.telephoneBoothX, 
            state.telephoneBoothY,
            state.neoLocationX,
            state.neoLocationY,
            state.neoDamage, 
            (ArrayList<Integer>)state.hostageLocationX.clone(), 
            (ArrayList<Integer>)state.hostageLocationY.clone(),
            (ArrayList<Integer>)state.hostageDamage.clone(),
            (ArrayList<Boolean>)state.hostageCarried.clone(),
            state.currentCarried,
            state.hostagesSaved,
            state.hostagesDead,
            (ArrayList<Integer>)state.pillLocationX.clone(),
            (ArrayList<Integer>)state.pillLocationY.clone(),
            state.startPadLocationX.clone(),
            state.startPadLocationY.clone(), 
            state.finishPadLocationX.clone(),
            state.finishPadLocationY.clone(),
            (ArrayList<Integer>)state.agentsLocationX.clone(),
            (ArrayList<Integer>)state.agentsLocationY.clone(),
            state.agentsKilled);

        //we first update the state according to the operator before we increase all hostages damage by 2
        //because for example if we are in the same cell as a hostage with damage of 99 and we preform a kill operation
        //this would be an illegal action therefore we make sure that the action is valid first then we incresase all hostages damage by 2
        //we will increase all hostages damage by 2 in all operation execpt the take pill one, as we will decrease all hostages damage by 20
        switch (operator) {
            case "up":
                if( newState.MoveUp() ){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
            case "down":
                if( newState.MoveDown() ){
                    newState.Step();

                    return newState;
                }
                else{
                    return null;
                }
            case "left":
                if( newState.MoveLeft() ){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
            case "right":
                if( newState.MoveRight() ){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
            case "carry":
                if( newState.carry()){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
            case "drop":
            if( newState.drop()){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
            case "fly":
                if( newState.fly()){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
            case "takePill":
                if( newState.takePill()){
                    return newState;
                }
                else{
                    return null;
                }
            case "kill":
                if( newState.kill()){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
        }
        return newState;
    }

    public static String genGrid() {

        Random rand = new Random();
        int M = rand.nextInt((15 - 5) + 1) + 5;
        int N = rand.nextInt((15 - 5) + 1) + 5;
        boolean[][] gridArray = new boolean[M][N];
        int C = rand.nextInt((4 - 1) + 1) + 1;
        int NeoX = rand.nextInt((M - 1) + 1);
        int NeoY = rand.nextInt((N - 1) + 1);
        gridArray[NeoX][NeoY] = true;
        /*
         * Decreasing available grid cells since we now know location of neo , note that
         * we need to reserve at least 4 more spaces, 1 for min. 1 Agent, 1 for min.
         * Pill, 2 for min. 1 start pad + 1 finish pad
         */
        /*
         * This is done in order not to run into a situation where we generated a random
         * number of agents that is equal to all the remaining empty grid cells
         */
        int AvailableGridCells = (M * N) - 5;
        String Grid = "" + M + "," + N + ";" + C + ";" + NeoX + "," + NeoY + ";";
        while (true) {
            // Randomly Generating Telephone Booth Location
            int TelephoneX = rand.nextInt((M - 1) + 1);
            int TelephoneY = rand.nextInt((N - 1) + 1);
            if (!gridArray[TelephoneX][TelephoneY]) {
                gridArray[TelephoneX][TelephoneY] = true;
                // Decreasing available grid cells by 1 for telephone booth
                AvailableGridCells -= 1;
                Grid += TelephoneX + "," + TelephoneY + ";";
                break;
            }
        }
        int Hostages = rand.nextInt((10 - 3) + 1) + 3;
        int Pills = rand.nextInt(Hostages) + 1;
        AvailableGridCells -= Hostages;
        AvailableGridCells -= Pills - 1;
        int Agents = rand.nextInt(AvailableGridCells) + 1;
        AvailableGridCells -= Agents - 1;
        // Here no. of pairs cannot exceed half of the available grid cells (1 pair =>
        // start pad + finish pad)
        int Pads = rand.nextInt(AvailableGridCells / 2) + 1;
        AvailableGridCells -= Pads * 2 - 2;
        // Generating Agents locations
        for (int i = 0; i < Agents; i++) {
            while (true) {
                int AgentX = rand.nextInt((M - 1) + 1);
                int AgentY = rand.nextInt((N - 1) + 1);
                if (!gridArray[AgentX][AgentY]) {
                    gridArray[AgentX][AgentY] = true;
                    Grid += AgentX + "," + AgentY;
                    break;
                }
            }
            if (i != Agents - 1)
                Grid += ",";
        }
        Grid += ";";
        // Generating Pills location
        for (int i = 0; i < Pills; i++) {
            while (true) {
                int PillX = rand.nextInt((M - 1) + 1);
                int PillY = rand.nextInt((N - 1) + 1);
                if (!gridArray[PillX][PillY]) {
                    gridArray[PillX][PillY] = true;
                    Grid += PillX + "," + PillY;
                    break;
                }
            }
            if (i != Pills - 1)
                Grid += ",";
        }
        Grid += ";";
        // Generating Pad PAirs locations
        for (int i = 0; i < Pads; i++) {
            while (true) {
                int StartPadX = rand.nextInt((M - 1) + 1);
                int StartPadY = rand.nextInt((N - 1) + 1);
                if (!gridArray[StartPadX][StartPadY]) {
                    while (true) {
                        int FinishPadX = rand.nextInt((M - 1) + 1);
                        int FinishPady = rand.nextInt((N - 1) + 1);
                        if (StartPadX != FinishPadX && StartPadY != FinishPady) {
                            gridArray[StartPadX][FinishPady] = true;
                            gridArray[FinishPadX][FinishPady] = true;
                            Grid += StartPadX + "," + StartPadY + "," + FinishPadX + "," + FinishPady;
                            break;
                        }
                    }
                    break;
                }

            }
            if (i != Pads - 1)
                Grid += ",";
        }
        Grid += ";";

        // Generating Hostages locations and damage
        for (int i = 0; i < Hostages; i++) {
            while (true) {
                int HostageX = rand.nextInt((M - 1) + 1);
                int HostageY = rand.nextInt((N - 1) + 1);
                if (!gridArray[HostageX][HostageY]) {
                    gridArray[HostageX][HostageY] = true;
                    int HostageDamage = rand.nextInt(99) + 1;
                    Grid += HostageX + "," + HostageY + "," + HostageDamage;
                    break;
                }
            }
            if (i != Hostages - 1)
                Grid += ",";
        }
        return Grid;
    }

    public static void ViewGrid(State Grid,int number) {
        System.out.println(number+" - ");
        String[][] GridView = new String[Grid.m][Grid.n];
        for (int i = 0; i < Grid.m; i++) {
            for (int j = 0; j < Grid.n; j++) {
                GridView[i][j] = "-";
            }
        }

        GridView[Grid.telephoneBoothX][Grid.telephoneBoothY] += "TB-";

        GridView[Grid.neoLocationX][Grid.neoLocationY] += "N"+"("+Grid.neoDamage+")-";
        for (int i = 0 ; i < Grid.agentsLocationX.size() ; i++) {
            GridView[Grid.agentsLocationX.get(i)][Grid.agentsLocationY.get(i)] += "A-";
        }

        for (int i = 0 ; i < Grid.pillLocationX.size() ; i++) {
            GridView[Grid.pillLocationX.get(i)][Grid.pillLocationY.get(i)] += "P-";
        }

        for (int i = 0; i < Grid.startPadLocationX.length; i+=4) {
            GridView[Grid.startPadLocationX[i]][Grid.startPadLocationY[i]] += "P" + (i) + "-";
            GridView[Grid.finishPadLocationX[i]][Grid.finishPadLocationY[i]] += "P" + (i) + "-";
        }

        for (int i = 0; i < Grid.hostageLocationX.size() ; i++) {
            GridView[Grid.hostageLocationX.get(i)][Grid.hostageLocationY.get(i)] += "H" +  "("+ Grid.hostageDamage.get(i)+")-";
        }

        String leftAlignFormat = "| ";
        String line="+";
        for (int i = 0 ; i < Grid.n ; i++) {
            leftAlignFormat += " %-25s |";
            line += "---------------------------+";
        }
        line+="\n";
        leftAlignFormat += "%n";

        for (int i = 0 ; i < Grid.m ; i++) {
            System.out.format(line);
            System.out.format(leftAlignFormat, GridView[i]);
        }
        System.out.format(line);
    }

    public static String solve(String grid, String strategy, boolean visualize){
        // decoding grid string
        String[] GridSplit=grid.split(";");
        // String[] length = GridSplit[0].split(",");
        int m =Integer.parseInt((GridSplit[0].split(","))[0]);
        int n =Integer.parseInt(GridSplit[0].split(",")[1]);
        int c = Integer.parseInt(GridSplit[1]);
        int NeoX= Integer.parseInt(GridSplit[2].split(",")[0]);
        int NeoY = Integer.parseInt(GridSplit[2].split(",")[1]);
        int telephoneX= Integer.parseInt(GridSplit[3].split(",")[0]);
        int telephoneY =Integer.parseInt(GridSplit[3].split(",")[1]);
        int agentSize= ((GridSplit[4].split(",")).length)/2;
        String[] agent2D = GridSplit[4].split(",");
        int pillSize= ((GridSplit[5].split(",")).length)/2;
        String[] pill2D = GridSplit[5].split(",");
        int padSize= ((GridSplit[6].split(",")).length)/4;
        String[] pad2D = GridSplit[6].split(",");
        int hostagesSize= ((GridSplit[7].split(",")).length)/3;
        String[] hostages2D = GridSplit[7].split(",");
        ArrayList<Integer> hostagesX= new  ArrayList<Integer> (hostagesSize);
        ArrayList<Integer> hostagesY= new  ArrayList<Integer> (hostagesSize);
        ArrayList<Integer> hostagesDamage= new  ArrayList<Integer> (hostagesSize);
        ArrayList<Boolean> hostagesCarried= new  ArrayList<Boolean> (Arrays.asList(new Boolean[hostagesSize]));
        Collections.fill(hostagesCarried, false);
        for(int i=0;i<hostages2D.length;i+=3){
            hostagesX.add(i/3,Integer.parseInt(hostages2D[i]));
            hostagesY.add(i/3,Integer.parseInt(hostages2D[i+1]));
            hostagesDamage.add(i/3,Integer.parseInt(hostages2D[i+2]));    
        }

        ArrayList<Integer> pillsX= new  ArrayList<Integer> (pillSize);
        ArrayList<Integer> pillsY= new  ArrayList<Integer> (pillSize); 
        for(int i=0;i<pill2D.length;i+=2){
            pillsX.add(i/2,Integer.parseInt(pill2D[i]));
            pillsY.add(i/2,Integer.parseInt(pill2D[i+1]));
        }
        int[] startPadsX=new int[padSize*2];
        int[] startPadsY=new int[padSize*2];
        int[] finishPadsX=new int[padSize*2];
        int[] finishPadsY=new int[padSize*2];

        for(int i =0;i<pad2D.length;i+=4){
            startPadsX[i/4]=Integer.parseInt(pad2D[i]);
            startPadsY[i/4]=Integer.parseInt(pad2D[i+1]);
            finishPadsX[i/4]=Integer.parseInt(pad2D[i+2]);
            finishPadsY[i/4]=Integer.parseInt(pad2D[i+3]);
        }
        for(int i =pad2D.length;i<pad2D.length*2;i+=4){
            finishPadsX[i/4]=Integer.parseInt(pad2D[i/4]);
            finishPadsY[i/4]=Integer.parseInt(pad2D[i/4+1]);
            startPadsX[i/4]=Integer.parseInt(pad2D[i/4+2]);
            startPadsY[i/4]=Integer.parseInt(pad2D[i/4+3]);
        }

        ArrayList<Integer> agentsX =new  ArrayList<Integer> (agentSize); 
        ArrayList<Integer> agentsY =new  ArrayList<Integer> (agentSize);
        for(int i=0;i<agent2D.length;i+=2){
            agentsX.add(i/2,Integer.parseInt(agent2D[i]));
            agentsY.add(i/2,Integer.parseInt(agent2D[i+1]));
        }        
        //initializing problem 
        State initialState=new State(n,m,c,telephoneX,telephoneY,NeoX,NeoY,0,hostagesX,hostagesY,hostagesDamage,hostagesCarried,0,0,0,pillsX,pillsY,startPadsX,startPadsY,finishPadsX,finishPadsY,agentsX,agentsY,0);
        String[] operators={"carry", "drop", "takePill", "up", "down", "left", "right", "fly", "kill"};
        Matrix problem = new Matrix(operators,initialState);
        SearchTreeNode solution = GenericSearchProcedure(problem, strategy);
        if(solution == null){
            return "No Solution";
        }
        else{
            String solutionString = "";
            String plan = "";
            ArrayList<State> states = new ArrayList<State>();
            SearchTreeNode tempNode=solution;
            plan += tempNode.operator;
            states.add(tempNode.state);
            if(tempNode.parentNode!=null){
                tempNode=tempNode.parentNode;
                while(true){
                    if(tempNode.parentNode==null){
                        states.add(0,tempNode.state);
                        break;
                    }
                    else{
                        states.add(0,tempNode.state);
                        plan=tempNode.operator+","+plan;
                        tempNode=tempNode.parentNode;
                    }
                }
            }
            if(visualize){
                for(int i=0;i<states.size();i++){
                    ViewGrid(states.get(i),i);
                    System.out.println();
                }
            }
            solutionString+=plan;
            solutionString+=";";
            solutionString+=solution.state.hostagesDead;
            solutionString+=";";
            solutionString+=solution.state.agentsKilled;
            solutionString+=";";
            solutionString+=problem.nodesExpanded;
            return solutionString;
        }
    }
    
    public static void main(String[] args) throws Exception {
        String grid = "5,5;1;1,4;1,0;0,4;0,0,2,2;3,4,4,2,4,2,3,4;0,2,32,0,1,38";
        String BFSSol = solve(grid, "BF", true);
        System.out.print("Solution: ");
        System.out.println(BFSSol);

    }
}

