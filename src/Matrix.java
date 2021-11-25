import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
public class Matrix extends GenericSearch {
    public Matrix(String[] operators, State initialState) {
        super(operators, initialState);
    }

    public boolean GoalTest(State currentState) {
        if (currentState.neoLocationX == currentState.telephoneBoothX
                && currentState.neoLocationY == currentState.telephoneBoothY) {
            for (int i = 0; i < currentState.hostageDamage.size(); i++) {
                if (currentState.hostageDamage.get(i) != 100
                        && currentState.hostageLocationX.get(i) != currentState.telephoneBoothX
                        && currentState.hostageLocationY.get(i) != currentState.telephoneBoothY) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int PathCost(State state, String operator) {

        return 1;
    }

    public State ApplyOperator(State state, String operator) {
        State newState = new State(state.n, state.m, state.c, state.telephoneBoothX, state.telephoneBoothY,
                state.neoLocationX, state.neoLocationY, state.hostageLocationX, state.hostageLocationY,
                state.hostageDamage, state.pillLocationX, state.pillLocationY, state.startPadLocationX,
                state.startPadLocationY, state.finishPadLocationX, state.finishPadLocationY, state.agentsLocationX,state.agentsLocationY );
        // TODO: implement ApplyOperator (check if it is even possible else return null)
        switch (operator) {
            case "up":
             if( newState.MoveUp() ){
                 return newState;
             }
             else{
                 return null;
             }
            case "down":
            if( newState.MoveDown() ){
                return newState;
            }
            else{
                return null;
            }
            case "left":
            if( newState.MoveLeft() ){
                return newState;
            }
            else{
                return null;
            }
            case "right":
            if( newState.MoveRight() ){
                return newState;
            }
            else{
                return null;
            }
            case "carry":
            if( newState.carry()){
                return newState;
            }
            else{
                return null;
            }
            case "drop":
            if( newState.drop()){
                return newState;
            }
            else{
                return null;
            }

            case "fly":
            if( newState.fly()){
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

    public static void ViewGrid(String Grid) {
        String[] GridSplit = Grid.split(";");
        String[] GridSize = GridSplit[0].split(",");
        // String[] NeoCarry=GridSplit[1].split(",");
        String[][] GridView = new String[Integer.parseInt(GridSize[0])][Integer.parseInt(GridSize[1])];
        String[] Neo = GridSplit[2].split(",");
        String[] Telephone = GridSplit[3].split(",");
        GridView[Integer.parseInt(Telephone[0])][Integer.parseInt(Telephone[1])] = "T    ";

        GridView[Integer.parseInt(Neo[0])][Integer.parseInt(Neo[1])] = "N    ";
        String[] Agents = GridSplit[4].split(",");
        for (int i = 0; i < Agents.length - 1; i += 2) {
            GridView[Integer.parseInt(Agents[i])][Integer.parseInt(Agents[i + 1])] = "A   ";
        }
        String[] Pills = GridSplit[5].split(",");
        for (int i = 0; i < Pills.length - 1; i += 2) {
            GridView[Integer.parseInt(Pills[i])][Integer.parseInt(Pills[i + 1])] = "P    ";
        }
        String[] Pads = GridSplit[6].split(",");
        for (int i = 0; i < Pads.length - 3; i += 4) {
            GridView[Integer.parseInt(Pads[i])][Integer.parseInt(Pads[i + 1])] = "SP" + ((i / 4) + 1) + "  ";
            GridView[Integer.parseInt(Pads[i + 2])][Integer.parseInt(Pads[i + 3])] = "FP" + ((i / 4) + 1) + "  ";
        }
        String[] Hostages = GridSplit[7].split(",");
        for (int i = 0; i < Hostages.length - 2; i += 3) {
            GridView[Integer.parseInt(Hostages[i])][Integer.parseInt(Hostages[i + 1])] = "H" + ((i / 3) + 1) + "-"
                    + Hostages[i + 2];
        }
        System.out.println(Hostages.length);
        for (int i = 0; i < GridView.length; i++) {
            System.out.println(Arrays.toString(GridView[i]));
        }
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
        }        //initializing problem 
    
        State initialState=new State(n,m,c,telephoneX,telephoneY,NeoX,NeoY,hostagesX,hostagesY,hostagesDamage,pillsX,pillsY,startPadsX,startPadsY,finishPadsX,finishPadsY,agentsX,agentsY);
        String[] operators={"up", "down", "left", "right", "carry","drop", "takePill", "kill","fly"};
        Matrix problem = new Matrix(operators,initialState);
        GenericSearchProcedure(problem, "BF");
        return "";
    }
    public static void main(String[] args) throws Exception {
        // String grid = genGrid();
        // String[] operators = {"left","right","up","down"};
        // Matrix m = new Matrix(operators, grid);
        // ViewGrid(grid);
        solve("5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80", "BF", true);

    }
}

