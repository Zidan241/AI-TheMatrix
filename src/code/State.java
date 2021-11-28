package code;

public class State {
    String[][] grid;
    int n;
    int m;
    int c;
    int telephoneBoothX;
    int telephoneBoothY;
    int neoLocationX;
    int neoLocationY;
    int neoDamage;
    int hostagesSaved;
    int hostagesDead;
    int totalHostages;
    int agentsKilled;
    int agentHosatgesKilled;
    int carriedHostagesDead;
    int currentCarried;

    // Constructor
    public State(
    String[][] grid,  
    int n,int m,int c,
    int telephoneBoothX,
    int telephoneBoothY,
    int neoLocationX,
    int neoLocationY,
    int neoDamage,
    int hostagesSaved,
    int hostagesDead,
    int totalHostages,
    int agentsKilled,
    int agentHosatgesKilled,
    int carriedHostagesDead,
    int currentCarried
    ) {
        this.grid=grid;
        this.n=n;
        this.m=m;
        this.c=c;
        this.telephoneBoothX=telephoneBoothX;
        this.telephoneBoothY=telephoneBoothY;
        this.neoLocationX=neoLocationX;
        this.neoLocationY=neoLocationY;
        this.neoDamage=neoDamage;
        this.hostagesSaved=hostagesSaved;
        this.hostagesDead=hostagesDead;
        this.totalHostages=totalHostages;
        this.agentsKilled=agentsKilled;
        this.agentHosatgesKilled=agentHosatgesKilled;
        this.carriedHostagesDead=carriedHostagesDead;
        this.currentCarried=currentCarried;
    }
    
    public String toString() {
        String hostages = "(H,";
        //String agents = "(A,";
        String agentHostages = "(AH,";
        //String hostagesCarried = "(CH,";
        String pills = "(P,";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]!=null){
                    String[] cellContent = grid[i][j].split(",");
                    if(cellContent[0].equals("H"))
                        hostages+=i+","+j+",";
                    //if(cellContent[0].equals("A"))
                        //agents+=i+","+j+",";
                    if(cellContent[0].equals("AH"))
                        agentHostages+=i+","+j+",";
                    if(cellContent[0].equals("P"))
                        pills+=i+","+j+",";
                    //if(cellContent[0].equals("CH"))
                        //hostagesCarried+=i+","+j+","+cellContent[1]+",";
                }
            }
        }
        //hostagesCarried += ")";
        //agents += ")";
        agentHostages += ")";
        hostages += ")";
        pills += ")";

        return "(N,"+neoLocationX+","+neoLocationY+","+neoDamage
        +")(HS,"+hostagesSaved
        //+")(HD,"+hostagesDead
        +")(AK,"+agentsKilled
        +")(AHK,"+agentHosatgesKilled
        //+")(CHD,"+carriedHostagesDead
        +")"+pills
        //+agents
        +hostages
        //+hostagesCarried
        +agentHostages
        ;
    }

    public boolean MoveDown() {

        // making sure new location is in the grid
        if (neoLocationX - 1 >= 0) {
            // making sure there are no hostages in the new location that were never carried
            // by neo before that have damage
            // of 98 or higher since they are going to become agents in the next step
            if (grid[neoLocationX-1][neoLocationY] != null) {
                String[] cellContent = grid[neoLocationX-1][neoLocationY].split(",");
                if (cellContent[0].equals("H")) {
                    if(Integer.parseInt(cellContent[1])>=98)
                        return false;
                }
                // making sure there are no agents in the new location
                if (cellContent[0].equals("A")||cellContent[0].equals("AH")) {
                    return false;
                }
            }

            //updating neo's location
            neoLocationX--;
            //note: no need to update the carried hostages location
            return true;
        } else {
            return false;
        }
    }

    public boolean MoveUp() {

        // making sure new location is in the grid
        if (neoLocationX + 1 < n) {
            // making sure there are no hostages in the new location that were never carried
            // by neo before that have damage
            // of 98 or higher since they are going to become agents in the next step
            if (grid[neoLocationX+1][neoLocationY] != null) {
                String[] cellContent = grid[neoLocationX+1][neoLocationY].split(",");
                if (cellContent[0].equals("H")) {
                    if(Integer.parseInt(cellContent[1])>=98)
                        return false;
                }
                // making sure there are no agents in the new location
                if (cellContent[0].equals("A")||cellContent[0].equals("AH")) {
                    return false;
                }
            }

            //updating neo's location
            neoLocationX++;
            //note: no need to update the carried hostages location
            return true;
        } else {
            return false;
        }
    }

    public boolean MoveLeft() {

        // making sure new location is in the grid
        if (neoLocationY - 1 >= 0) {
            // making sure there are no hostages in the new location that were never carried
            // by neo before that have damage
            // of 98 or higher since they are going to become agents in the next step
            if (grid[neoLocationX][neoLocationY-1] != null) {
                String[] cellContent = grid[neoLocationX][neoLocationY-1].split(",");
                if (cellContent[0].equals("H")) {
                    if(Integer.parseInt(cellContent[1])>=98)
                        return false;
                }
                // making sure there are no agents in the new location
                if (cellContent[0].equals("A")||cellContent[0].equals("AH")) {
                    return false;
                }
            }

            //updating neo's location
            neoLocationY--;
            //note: no need to update the carried hostages location
            return true;
        } else {
            return false;
        }
    }

    public boolean MoveRight() {

        // making sure new location is in the grid
        if (neoLocationY + 1 < m) {
            // making sure there are no hostages in the new location that were never carried
            // by neo before that have damage
            // of 98 or higher since they are going to become agents in the next step
            if (grid[neoLocationX][neoLocationY+1] != null) {
                String[] cellContent = grid[neoLocationX][neoLocationY+1].split(",");
                if (cellContent[0].equals("H")) {
                    if(Integer.parseInt(cellContent[1])>=98)
                        return false;
                }
                // making sure there are no agents in the new location
                if (cellContent[0].equals("A")||cellContent[0].equals("AH")) {
                    return false;
                }
            }

            //updating neo's location
            neoLocationY++;
            //note: no need to update the carried hostages location
            return true;
        } else {
            return false;
        }
    }

    public boolean carry() {
        if(currentCarried==c) 
            return false; 
        if (grid[neoLocationX][neoLocationY] != null) {
            String[] cellContent = grid[neoLocationX][neoLocationY].split(",");
            if (cellContent[0].equals("H")) {
                grid[neoLocationX][neoLocationY]="CH,"+cellContent[1];
                currentCarried++;
                return true;
            }
        }
        return false;
    }

    public boolean drop() {
        if (grid[neoLocationX][neoLocationY] != null && grid[neoLocationX][neoLocationY].split(",")[0].equals("TB")&&currentCarried>0){
            //loop over carried hostages and for each hostage whose damage is less than 100 we increase the hostages saved counter
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[i].length;j++){
                    if(grid[i][j]!=null){
                       String[] cellContent = grid[i][j].split(",");
                       if(cellContent[0].equals("CH")){
                            grid[i][j]=null;
                            // Question: will we increase +2 as a step before dropping?
                            if(Integer.parseInt(cellContent[1])<100){
                                hostagesSaved++;
                            }
                       }
                    }
                }
            }
            currentCarried=0;
            return true;
        }
        return false;
    }

    public boolean fly() {
        if (grid[neoLocationX][neoLocationY] != null ) {
            String[] cellContent=grid[neoLocationX][neoLocationY].split(",");
            if(cellContent[0].equals("Pad")){
                neoLocationX=Integer.parseInt(cellContent[1]);
                neoLocationY=Integer.parseInt(cellContent[2]);
                return true;
            //we don't update carried hostages location since they are removed from the grid arrau so we  don't need to track their location anymore
            }
        }
        return false;
    }

    public boolean takePill() {
        // loop over pills arraylist and check if neo is at the same location as a pill
        // then take it and remove it from the arraylist
        if (grid[neoLocationX][neoLocationY] != null && grid[neoLocationX][neoLocationY].split(",")[0].equals("P")) {
            grid[neoLocationX][neoLocationY]=null;
            neoDamage -= 20;
            if (neoDamage < 0){
                neoDamage = 0;
            }
            // loop over all living hostages and decrease their damage by 20
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] != null) {
                        String[] cellContent = grid[i][j].split(",");
                        if (cellContent[0].equals("H")) {
                            int hostDam = Integer.parseInt(cellContent[1]);
                                hostDam -= 20;
                                //make sure that no hostage has damage less than 0
                                if(hostDam<0)
                                    hostDam=0;
                                grid[i][j] = "H," + hostDam;
                        }
                        //loop over all carried hostages 
                        if(cellContent[0].equals("CH")){
                            int hostDam = Integer.parseInt(cellContent[1]);
                            //make sure that if neo is carrying dead hostages we don't decrease their damage
                            if(hostDam<100){
                                hostDam -= 20;
                                //make sure that no hostage has damage less than 0
                                if(hostDam<0)
                                    hostDam=0;
                                grid[i][j] = "CH," + hostDam;
                            }
                        }
                    }
                }
            }
        return true;
    }
        return false;
    }

    public boolean kill() {

        // check if there is no alive hostage with damage bigger than or equal 98 in the same location as neo
        // because if we do the kill action we will not move and the hostage will become an agent
        // therefore neo will be in the same cell as an agent which is an illegal action
        // note that this check happens before increasing the hostage damage by 2

        if (grid[neoLocationX][neoLocationY] != null ){
            String[] cellContent = grid[neoLocationX][neoLocationY].split(",");
            if(cellContent[0].equals("H")){
                int hostDam = Integer.parseInt(cellContent[1]);
                if(hostDam>=98){
                    return false;
                }
            }
        }

        // loop over agents arraylist and check if neo is adjacent
        // to an an agent then kill it and remove it from the arraylist 
        // and increase neo's damage by 20

        boolean killed = false;

        // kill all agents in adjacent cells
        if ((neoLocationX+1)<n && grid[neoLocationX + 1][neoLocationY] != null
                && grid[neoLocationX + 1][neoLocationY].split(",")[0].equals("A")) {
            grid[neoLocationX + 1][neoLocationY] = null;
            killed = true;
            agentsKilled++;
        }
        if ((neoLocationX+1)<n && grid[neoLocationX + 1][neoLocationY] != null
                && grid[neoLocationX + 1][neoLocationY].split(",")[0].equals("AH")) {
            grid[neoLocationX + 1][neoLocationY] = null;
            killed = true;
            agentHosatgesKilled++;
        }
///////////////
        if ((neoLocationX-1)>=0 && grid[neoLocationX - 1][neoLocationY] != null
                && grid[neoLocationX - 1][neoLocationY].split(",")[0].equals("A")) {
            grid[neoLocationX - 1][neoLocationY] = null;
            killed = true;
            agentsKilled++;
        }
        if ((neoLocationX-1)>=0 && grid[neoLocationX - 1][neoLocationY] != null
                && grid[neoLocationX - 1][neoLocationY].split(",")[0].equals("AH")) {
            grid[neoLocationX - 1][neoLocationY] = null;
            killed = true;
            agentHosatgesKilled++;
        }
///////////////
        if ((neoLocationY+1)<m && grid[neoLocationX][neoLocationY + 1] != null
                && grid[neoLocationX][neoLocationY + 1].split(",")[0].equals("A")) {
            grid[neoLocationX][neoLocationY + 1] = null;
            killed = true;
            agentsKilled++;
        }
        if ((neoLocationY+1)<m && grid[neoLocationX][neoLocationY + 1] != null
                && grid[neoLocationX][neoLocationY + 1].split(",")[0].equals("AH")) {
            grid[neoLocationX][neoLocationY + 1] = null;
            killed = true;
            agentHosatgesKilled++;
        }
///////////////
        if ((neoLocationY-1)>=0 && grid[neoLocationX][neoLocationY - 1] != null
                && grid[neoLocationX][neoLocationY - 1].split(",")[0].equals("A")) {
            grid[neoLocationX][neoLocationY - 1] = null;
            killed = true;
            agentsKilled++;
        }
        if ((neoLocationY-1)>=0 && grid[neoLocationX][neoLocationY - 1] != null
                && grid[neoLocationX][neoLocationY - 1].split(",")[0].equals("AH")) {
            grid[neoLocationX][neoLocationY - 1] = null;
            killed = true;
            agentHosatgesKilled++;
        }
///////////////
        // if neo kills an agent(s) then increase neo's damage by 20
        if (killed) {
            neoDamage += 20;
            if (neoDamage > 100)
                neoDamage = 100;
        }
        return killed;
    }

    public void Step() {
        // increase hostage damage by 2
        // loop over grid 2d array
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    String[] cellContent = grid[i][j].split(",");
                    if (cellContent[0].equals("H")) {
                        int hostDam = Integer.parseInt(cellContent[1]);
                        if (hostDam < 98) {
                            hostDam += 2;
                            grid[i][j] = "H," + hostDam;
                        } else {
                            // convert hostage to agent if damage is equal to or greater than 100 and is not
                            // carried by neo
                            grid[i][j] = "AH";
                            hostagesDead++;
                        }
                    }
                    if(cellContent[0].equals("CH")){
                        int hostDam = Integer.parseInt(cellContent[1]);
                        if(hostDam<98){
                            hostDam += 2;
                            grid[i][j] = "CH," + hostDam;
                        }
                        else{
                            if(hostDam >= 98 && hostDam < 100){
                                hostDam = 100;
                                grid[i][j] = "CH," + hostDam;
                                hostagesDead++;
                                carriedHostagesDead++;
                            }
                        }
                    }

                }

            }
        }
    }

}
