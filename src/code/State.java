package code;

import java.util.ArrayList;

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
    int hostagesAlive;
    int agentsKilled;
    ArrayList<Integer> hostagesCarriedDamage;

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
    int agentsKilled,
    ArrayList<Integer> hostagesCarriedDamage;
    ) {
        //m is the columns (width)
        //n is the rows (height)
        this.n=n;
        this.m=m;
        this.c=c;
        this.telephoneBoothX=telephoneBoothX;
        this.telephoneBoothY=telephoneBoothY;
        this.neoLocationX=neoLocationX;
        this.neoLocationY=neoLocationY;
        this.hostagesSaved=hostagesSaved;
        this.hostagesDead=hostagesDead;
        this.agentsKilled=agentsKilled;
        this.grid=grid;
        this.hostagesCarriedDamage=hostagesCarriedDamage;
    }

    public String toString() {
        String s = "\n";
        s += "n=" + n + "\n";
        s += "m=" + m + "\n";
        s += "c=" + c + "\n";
        s += "telephoneBoothX=" + telephoneBoothX + "\n";

        s += "telephoneBoothY=" + telephoneBoothY + "\n";
        s += "neoLocationX=" + neoLocationX + "\n";
        s += "neoLocationY=" + neoLocationY + "\n";
        s += "hostageLocationX=";
        for (int i = 0; i < hostageLocationX.size(); i++) {
            s += hostageLocationX.get(i) + " ";
        }
        s += "\n";
        s += "hostageLocationY=";
        for (int i = 0; i < hostageLocationY.size(); i++) {
            s += hostageLocationY.get(i) + " ";
        }
        s += "\n";
        s += "hostageDamage=";
        for (int i = 0; i < hostageDamage.size(); i++) {
            s += hostageDamage.get(i) + " ";
        }
        s += "\n";
        s += "pillLocationX=";
        for (int i = 0; i < pillLocationX.size(); i++) {
            s += pillLocationX.get(i) + " ";
        }
        s += "\n";
        s += "pillLocationY=";
        for (int i = 0; i < pillLocationY.size(); i++) {
            s += pillLocationY.get(i) + " ";
        }
        s += "\n";
        s += "startPadLocationX=";
        for (int i = 0; i < startPadLocationX.length; i++) {
            s += startPadLocationX[i] + " ";
        }
        s += "\n";
        s += "startPadLocationY=";
        for (int i = 0; i < startPadLocationY.length; i++) {
            s += startPadLocationY[i] + " ";
        }
        s += "\n";
        s += "finishPadLocationX=";
        for (int i = 0; i < finishPadLocationX.length; i++) {
            s += finishPadLocationX[i] + " ";
        }
        s += "\n";
        s += "finishPadLocationY=";
        for (int i = 0; i < finishPadLocationY.length; i++) {
            s += finishPadLocationY[i] + " ";
        }
        s += "\n";
        s += "agentsLocationX=";
        for (int i = 0; i < agentsLocationX.size(); i++) {
            s += agentsLocationX.get(i) + " ";
        }
        s += "\n";
        s += "agentsLocationY=";
        for (int i = 0; i < agentsLocationY.size(); i++) {
            s += agentsLocationY.get(i) + " ";
        }
        s += "\n";
        return s;
    }

    public boolean MoveUp() {

        // making sure there are no hostages in the new location that were never carried
        // by neo before that have damage
        // of 98 or higher since they are going to become agents in the next step
        for (int i = 0; i < hostageLocationX.size(); i++) {
            if (hostageLocationX.get(i) == (neoLocationX - 1) && hostageLocationY.get(i) == neoLocationY
                    && hostageDamage.get(i) >= 98) {
                return false;
            }
        }
        // making sure there are no agents in the new location
        for (int i = 0; i < agentsLocationX.size(); i++) {
            if (agentsLocationX.get(i) == (neoLocationX - 1) && agentsLocationY.get(i) == neoLocationY) {
                return false;
            }
        }

        // making sure new location is in the grid
        if ((neoLocationX - 1) >= 0) {
            neoLocationX--;
            // updating the carried hostages location
            for (int i = 0; i < hostageLocationX.size(); i++) {
                if (hostageCarried.get(i) == true) {
                    hostageLocationX.set(i, neoLocationX);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean MoveDown() {

        // making sure there are no hostages in the new location that were never carried
        // by neo before that have damage
        // of 98 or higher since they are going to become agents in the next step
        for (int i = 0; i < hostageLocationX.size(); i++) {
            if (hostageLocationX.get(i) == (neoLocationX + 1) && hostageLocationY.get(i) == neoLocationY
                    && hostageDamage.get(i) >= 98) {
                return false;
            }
        }
        // making sure there are no agents in the new location
        for (int i = 0; i < agentsLocationX.size(); i++) {
            if (agentsLocationX.get(i) == (neoLocationX + 1) && agentsLocationY.get(i) == neoLocationY) {
                return false;
            }
        }

        // making sure new location is in the grid
        if (neoLocationX + 1 < n) {
            neoLocationX++;
            // updating the carried hostages location
            for (int i = 0; i < hostageLocationX.size(); i++) {
                if (hostageCarried.get(i) == true) {
                    hostageLocationX.set(i, neoLocationX);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean MoveLeft() {

        // making sure there are no hostages in the new location that were never carried
        // by neo before that have damage
        // of 98 or higher since they are going to become agents in the next step
        for (int i = 0; i < hostageLocationX.size(); i++) {
            if (hostageLocationX.get(i) == neoLocationX && hostageLocationY.get(i) == (neoLocationY - 1)
                    && hostageDamage.get(i) >= 98) {
                return false;
            }
        }
        // making sure there are no agents in the new location
        for (int i = 0; i < agentsLocationX.size(); i++) {
            if (agentsLocationX.get(i) == neoLocationX && agentsLocationY.get(i) == (neoLocationY - 1)) {
                return false;
            }
        }

        // making sure new location is in the grid
        if (neoLocationY - 1 >= 0) {
            neoLocationY--;
            // updating the carried hostages location
            for (int i = 0; i < hostageLocationX.size(); i++) {
                if (hostageCarried.get(i) == true) {
                    hostageLocationY.set(i, neoLocationY);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean MoveRight() {

        // making sure there are no hostages in the new location that were never carried
        // by neo before that have damage
        // of 98 or higher since they are going to become agents in the next step
        for (int i = 0; i < hostageLocationX.size(); i++) {
            if (hostageLocationX.get(i) == neoLocationX && hostageLocationY.get(i) == (neoLocationY + 1)
                    && hostageDamage.get(i) >= 98) {
                return false;
            }
        }
        // making sure there are no agents in the new location
        for (int i = 0; i < agentsLocationX.size(); i++) {
            if (agentsLocationX.get(i) == neoLocationX && agentsLocationY.get(i) == (neoLocationY + 1)) {
                return false;
            }
        }

        // making sure new location is in the grid
        if (neoLocationY + 1 < m) {
            neoLocationY++;
            // updating the carried hostages location
            for (int i = 0; i < hostageLocationX.size(); i++) {
                if (hostageCarried.get(i) == true) {
                    hostageLocationY.set(i, neoLocationY);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean carry() {
        if (grid[neoLocationX][neoLocationY] != null) {
            String[] cellContent = grid[neoLocationX][neoLocationY].split(",");
            if (cellContent[0].equals("H")) {
                grid[neoLocationX][neoLocationY]=null;
                hostagesCarriedDamage.add(Integer.parseInt(cellContent[1]));
                return true;
            }
        }

        return false;
    }

    public boolean drop() {
        if (grid[neoLocationX][neoLocationY] != null && grid[neoLocationX][neoLocationY].split(",")[0].equals("TB")&&!hostagesCarriedDamage.isEmpty()){
            //loop over carried hostages and for each hostage whose damage is less than 100 we increase the hostages saved counter
            for(int i =0;i<hostagesCarriedDamage.size();i++)
                if(hostagesCarriedDamage.get(i)<100)
                    hostagesSaved++;
            hostagesCarriedDamage=new ArrayList<Integer>();
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
            neoDamage -= 20;
            if (neoDamage < 0)
                neoDamage = 0;
            // loop over all living hostages and decrease their damage by 22
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    // check if there is a hostage in the same location as neo
                    if (grid[i][j] != null) {
                        String[] cellContent = grid[i][j].split(",");
                        if (cellContent[0].equals("H")) {
                            int hostDam = Integer.parseInt(cellContent[1]);
                                hostDam -= 20;
                                if(hostDam<0)
                                    hostDam=0;
                                grid[i][j] = "H," + hostDam;
                        }
                    }
                }
            }
            //loop over all carried hostages 
            for( int i =0;i<hostagesCarriedDamage.size();i++){
                //make sure that if neo is carrying dead hostages we don't decrease their damage
                if(hostagesCarriedDamage.get(i)<100){
                    //make sure that no hostage has damage less than 0
                    if(hostagesCarriedDamage.get(i)<20)
                        hostagesCarriedDamage.set(i,0);
                    else 
                        hostagesCarriedDamage.set(i, hostagesCarriedDamage.get(i)-20);

                }
            }
        return true;
    }
        return false;
    }

    public boolean kill() {

        // check if there is no alive hostage in the same location as neo
        // note that this check happens before increasing the hostage damage by 2

        if (grid[neoLocationX][neoLocationY] != null && grid[neoLocationX][neoLocationY].split(",")[0].equals("H"))
            return false;

        // loop over agents arraylist and check if neo is adjacent
        // to an an agent then kill it and remove it from the arraylist and decrease
        // neo's damage by 20

        boolean killed = false;

        // kill all agents in adjacent cells
        if (grid[neoLocationX + 1][neoLocationY] != null
                && grid[neoLocationX + 1][neoLocationY].split(",")[0].equals("A")) {
            grid[neoLocationX + 1][neoLocationY] = null;
            killed = true;
            agentsKilled++;
        }
        if (grid[neoLocationX - 1][neoLocationY] != null
                && grid[neoLocationX - 1][neoLocationY].split(",")[0].equals("A")) {
            grid[neoLocationX - 1][neoLocationY] = null;
            killed = true;
            agentsKilled++;
        }
        if (grid[neoLocationX][neoLocationY + 1] != null
                && grid[neoLocationX][neoLocationY + 1].split(",")[0].equals("A")) {
            grid[neoLocationX][neoLocationY + 1] = null;
            killed = true;
            agentsKilled++;
        }
        if (grid[neoLocationX][neoLocationY - 1] != null
                && grid[neoLocationX + 1][neoLocationY - 1].split(",")[0].equals("A")) {
            grid[neoLocationX][neoLocationY - 1] = null;
            killed = true;
            agentsKilled++;
        }

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
                // check if there is a hostage in the same location as neo
                if (grid[i][j] != null) {
                    String[] cellContent = grid[i][j].split(",");
                    if (cellContent[0].equals("H")) {
                        int hostDam = Integer.parseInt(cellContent[1]);
                        if (hostDam < 98) {
                            hostDam += 2;
                            grid[i][j] = "H," + hostDam;
                        } else {
                            if (hostDam >= 98 && hostDam < 100) {
                                grid[i][j] = null;
                                hostagesDead++;
                            }
                        }
                        // convert hostage to agent if damage is equal to or greater than 100 and is not
                        // carried by neo
                        if (hostDam >= 100) {
                            grid[i][j] = "A";
                            hostagesDead++;
                        }
                    }

                }

            }
        }
    }

}
