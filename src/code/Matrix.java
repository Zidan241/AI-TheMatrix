package code;
import java.util.Random;
import java.util.ArrayList;

public class Matrix extends GenericSearch {
    public Matrix(String[] operators, State initialState) {
        super(operators, initialState);
    }

    public boolean GoalTest(State currentState) {

        if(currentState.neoLocationX==currentState.telephoneBoothX &&
        currentState.neoLocationY==currentState.telephoneBoothY &&
        (currentState.totalHostages // total hostages
        -currentState.hostagesSaved // total hostages saved
        -currentState.agentHosatgesKilled // total mutated hostages
        -currentState.carriedHostagesDead) // total hostages who died while neo carried them
        ==0){ // this means that we must save all hostages and kill all of those who where mutatued
            return true;
        }
        else{
            return false;
        }
    }

    public int PathCost(State state, State nextState) {

        //IMPORTANT NOTE:
        //note that we will prioritize deaths then kills then number of actions
        //therefore cost of Hostages deaths > cost of kills > cost of actions

        //we will add the cost to go from state to nextState, were state represents the parent's state

        //we will add one for each step taken
        int stepCost = 1;

        //for each agent killed we will increase the cost by 5
        stepCost += (nextState.agentsKilled - state.agentsKilled) * 5;

        //for each hostage death we will increase the cost by 20
        stepCost += (nextState.hostagesDead - state.hostagesDead) * 20;

        return stepCost;
    }

    public int Heuristic1(State state) {
        String [][] grid=state.grid;
        int n=state.n;
        int m=state.m;
        //Loop over the grid and get min distance to hostage
        //Loop over grid and get max distance between two pads
        //get min actions needed to save closest hostage
        //multiply by number of hostages that can be saved
        int minHostageDistance=n*m;
        int maxPadDistance=0;
        int hostagesToSave=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i!=state.neoLocationX && j!=state.neoLocationY){
                    if(grid[i][j]!=null){
                        String[] cellContent=grid[i][j].split(",");
                        if(cellContent[0].equals("H")){
                            int hostageManhattan=Math.abs(state.neoLocationX-i)+Math.abs(state.neoLocationY-j);
                            hostagesToSave+=1;
                            if(hostageManhattan<minHostageDistance){
                                minHostageDistance=hostageManhattan;                          
                            }
                        }
                        if(cellContent[0].equals("P")){
                            int fromX=i;
                            int fromY=j;
                            int ToX=Integer.parseInt(cellContent[1]);
                            int ToY=Integer.parseInt(cellContent[2]);
                            int padManhattan=Math.abs(fromX-ToX)+Math.abs(fromY-ToY);
                            if(padManhattan>maxPadDistance){
                                maxPadDistance=padManhattan;
                            }
                        }
                    }
                }
            }
        }
        // check if taking the pad would be needed to decrease the distance and add 1 step for taking the pill
        // int minMoves=minHostageDistance;
        // if(minHostageDistance>maxPadDistance){
        //     minMoves=minHostageDistance-maxPadDistance+1;
        // }

        int minMoves=minHostageDistance-maxPadDistance;
        if(minMoves<=0){
            minMoves=2;
        }else{
            minMoves++;
        }  
         // after finding the minimum distance to save the closest hostages multiply that for each hostage  
        return minMoves*hostagesToSave;
    }

    public int Heuristic2(State state) {
        //calculate manhattan distance to telephone booth 
        //calculate number of hostages that will die while on my way to telephone booth  
        //add number of dead hostages to manhattan distance calculated
        // to have cost =  steps to reach the booth + death of hostages on the way
        int d = Math.abs(state.telephoneBoothX- state.neoLocationX)+Math.abs(state.telephoneBoothY- state.neoLocationY);
        String [][] grid=state.grid;
        int n=state.n;
        int m=state.m;
        int dead= 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]!=null){
                    String[] cellContent=grid[i][j].split(",");
                    if(cellContent[0].equals("H")){
                    if(Math.abs(Integer.parseInt(cellContent[1])+d)>=100){
                        dead++;
                    }              
                    }
        
                }
            }
        }
        
        return d+dead;         
     }

    public int Heuristic3(State state) {
        int totalReturn = 0;
        int totalHostagesAliveNotCarriedWithDamageBiggerThan97 = 0;
        int totalHostagesAliveNotCarriedWithDamageLessThan98 = 0;
        if(state.grid[state.neoLocationX][state.neoLocationY].split(",")[0].equals("H")){
            totalReturn += 1;
        }
        for(int i=0; i<state.grid.length; i++){
            for(int j=0; j<state.grid[i].length; j++){
                if(state.grid[i][j]!=null){
                    String [] cellContent = state.grid[i][j].split(",");
                    if(cellContent[0].equals("H")){
                        if(Integer.parseInt(cellContent[1])>97){
                            totalHostagesAliveNotCarriedWithDamageBiggerThan97++;
                        }
                        else{
                            totalHostagesAliveNotCarriedWithDamageLessThan98++;
                        }
                    }
                }
            }
        }
        if(!state.grid[state.neoLocationX][state.neoLocationY].split(",")[0].equals("P")){
            totalReturn += totalHostagesAliveNotCarriedWithDamageBiggerThan97*20;
        }
        else{
            totalReturn += totalHostagesAliveNotCarriedWithDamageBiggerThan97*2;
        }
        totalReturn += totalHostagesAliveNotCarriedWithDamageLessThan98*2;
        return totalReturn;
    }

    public State ApplyOperator(State state, String operator) {
        //if neo dies he cannot do any operation
        if(state.neoDamage==100){
            return null;
        }

        String[][] A1 = state.grid.clone();
        for (int i = 0; i < A1.length; i++) {
            A1[i] = A1[i].clone();
        }
        State newState = new State(
            A1,
            state.n,
            state.m, 
            state.c, 
            state.telephoneBoothX, 
            state.telephoneBoothY,
            state.neoLocationX,
            state.neoLocationY,
            state.neoDamage,
            state.hostagesSaved,
            state.hostagesDead,
            state.totalHostages,
            state.agentsKilled,
            state.agentHosatgesKilled,
            state.carriedHostagesDead,
            state.currentCarried
            );

        //we first update the state according to the operator before we increase all hostages damage by 2
        //because for example if we are in the same cell as a hostage with damage of 99 and we preform a kill operation
        //this would be an illegal action therefore we make sure that the action is valid first then we incresase all hostages damage by 2
        //we will increase all hostages damage by 2 in all operation execpt the take pill one, as we will decrease all hostages damage by 20
        switch (operator) {
            case "up":{
                if( newState.MoveUp() ){
                    newState.Step();
                    return newState;
                }
                else{
                    return null;
                }
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

    public static void ViewGrid(State Grid,String number) {
        System.out.println(number+" - ");
        String[][] GridView = Grid.grid.clone();
        for (int i = 0; i < Grid.m; i++) {
            for (int j = 0; j < Grid.n; j++) {
                if(GridView[i][j]==null)
                    GridView[i][j] = "-";
            }
        }

        GridView[Grid.neoLocationX][Grid.neoLocationY] += "-N"+"("+Grid.neoDamage+")"+"("+Grid.currentCarried+"H)-";

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
            System.out.format(leftAlignFormat, (Object[])GridView[i]);
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
        String[] agent2D = GridSplit[4].split(",");
        String[] pill2D = GridSplit[5].split(",");
        String[] pad2D = GridSplit[6].split(",");
        int hostagesSize= ((GridSplit[7].split(",")).length)/3;
        String[] hostages2D = GridSplit[7].split(",");
        String[][] gridReturn = new String[m][n];
            gridReturn[telephoneX][telephoneY]="TB";
        for(int i=0;i<hostages2D.length;i+=3){
            gridReturn[Integer.parseInt(hostages2D[i])][Integer.parseInt(hostages2D[i+1])] = "H,"+hostages2D[i+2];
        }
        for(int i=0;i<pill2D.length;i+=2){
            gridReturn[Integer.parseInt(pill2D[i])][Integer.parseInt(pill2D[i+1])] = "P";
        }
        for(int i =0;i<pad2D.length;i+=4){
            gridReturn[Integer.parseInt(pad2D[i])][Integer.parseInt(pad2D[i+1])] = "Pad,"+pad2D[i+2]+","+pad2D[i+3];
            gridReturn[Integer.parseInt(pad2D[i+2])][Integer.parseInt(pad2D[i+3])] = "Pad,"+pad2D[i]+","+pad2D[i+1];
        }
        for(int i=0;i<agent2D.length;i+=2){
            gridReturn[Integer.parseInt(agent2D[i])][Integer.parseInt(agent2D[i+1])] = "A";
        }

        //initializing problem 
        State initialState=new State(
            gridReturn,
            n,m,c,
            telephoneX,
            telephoneY,
            NeoX,
            NeoY,
            0,
            0,
            0,
            hostagesSize,
            0,
            0,
            0,
            0
            );
     
        String[] operators={"carry", "takePill", "drop", "kill", "up", "down", "left", "right", "fly"};
        Matrix problem = new Matrix(operators,initialState);
        SearchTreeNode solution = GenericSearchProcedure(problem, strategy);
        if(solution == null){
            ViewGrid(initialState, "Original Grid : ");
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
                    ViewGrid(states.get(i),i+"");
                    System.out.println();
                }
            }
            solutionString+=plan;
            solutionString+=";";
            solutionString+=solution.state.hostagesDead;
            solutionString+=";";
            solutionString+=(solution.state.agentsKilled+solution.state.agentHosatgesKilled);
            solutionString+=";";
            solutionString+=problem.nodesExpanded;
            return solutionString;
        }
    }
    
    public static void main(String[] args) throws Exception {
        String grid = "5,5;3;1,3;4,0;0,1,3,2,4,3,2,4,0,4;3,4,3,0,4,2;1,4,1,2,1,2,1,4,0,3,1,0,1,0,0,3;4,4,45,3,3,12,0,2,88";
        String BFSSol = solve(grid, "BF", false);
        System.out.print("Solution: ");
        System.out.println(BFSSol);

    }
}

