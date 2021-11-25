package code;
import java.util.ArrayList;

public class State {
    int n;
    int m;
    int c;
    int telephoneBoothX;
    int telephoneBoothY;
    int neoLocationX;
    int neoLocationY;
    String[][] grid;
    int currentCarried;
    int hostagesSaved;
    int hostagesDead;
    int agentsKilled;

    //Constructor
    public State(int n,int m,int c,
    int telephoneBoothX,
    int telephoneBoothY,
    int neoLocationX,
    int neoLocationY,
    int neoDamage,   
    ArrayList<Integer>  hostageLocationX,    
    ArrayList<Integer>  hostageLocationY,    
    ArrayList<Integer>  hostageDamage,
    ArrayList<Boolean>  hostageCarried,
    int currentCarried,
    int hostagesSaved,
    int hostagesDead,
    ArrayList<Integer>  pillLocationX,    
    ArrayList<Integer>  pillLocationY,
    int[] startPadLocationX,
    int[] startPadLocationY,
    int[] finishPadLocationX,
    int[] finishPadLocationY,    
    ArrayList<Integer> agentsLocationX,    
    ArrayList<Integer> agentsLocationY,
    int agentsKilled
    ) {
        //m is the columns (width)
        //n is the rows (height)
        this.n=n;
        this.m=m;
        this.c=c;
        this.hostageCarried = hostageCarried;
        this.telephoneBoothX=telephoneBoothX;
        this.telephoneBoothY=telephoneBoothY;
        this.neoLocationX=neoLocationX;
        this.neoLocationY=neoLocationY;
        this.hostageLocationX=hostageLocationX;
        this.hostageLocationY=hostageLocationY;
        this.hostageDamage=hostageDamage;
        this.hostageCarried=hostageCarried;
        this.currentCarried=currentCarried;
        this.hostagesSaved=hostagesSaved;
        this.hostagesDead=hostagesDead;
        this.pillLocationX=pillLocationX;
        this.pillLocationY=pillLocationY;
        this.startPadLocationX=startPadLocationX;
        this.startPadLocationY=startPadLocationY;
        this.finishPadLocationX=finishPadLocationX;
        this.finishPadLocationY=finishPadLocationY;
        this.agentsLocationX=agentsLocationX;
        this.agentsLocationY=agentsLocationY;
        this.agentsKilled=agentsKilled;
    }
    
    public String toString(){
        String s="\n";
        s+="n="+n+"\n";
        s+="m="+m+"\n";
        s+="c="+c+"\n";
        s+="telephoneBoothX="+telephoneBoothX+"\n";
    
        s+="telephoneBoothY="+telephoneBoothY+"\n";
        s+="neoLocationX="+neoLocationX+"\n";
        s+="neoLocationY="+neoLocationY+"\n";
        s+="hostageLocationX=";
        for(int i=0;i<hostageLocationX.size();i++){
            s+=hostageLocationX.get(i)+" ";
        }
        s+="\n";
        s+="hostageLocationY=";
        for(int i=0;i<hostageLocationY.size();i++){
            s+=hostageLocationY.get(i)+" ";
        }
        s+="\n";
        s+="hostageDamage=";
        for(int i=0;i<hostageDamage.size();i++){
            s+=hostageDamage.get(i)+" ";
        }
        s+="\n";
        s+="pillLocationX=";
        for(int i=0;i<pillLocationX.size();i++){
            s+=pillLocationX.get(i)+" ";
        }
        s+="\n";
        s+="pillLocationY=";
        for(int i=0;i<pillLocationY.size();i++){
            s+=pillLocationY.get(i)+" ";
        }
        s+="\n";
        s+="startPadLocationX=";
        for(int i=0;i<startPadLocationX.length;i++){
            s+=startPadLocationX[i]+" ";
        }
        s+="\n";
        s+="startPadLocationY=";
        for(int i=0;i<startPadLocationY.length;i++){
            s+=startPadLocationY[i]+" ";
        }
        s+="\n";
        s+="finishPadLocationX=";
        for(int i=0;i<finishPadLocationX.length;i++){
            s+=finishPadLocationX[i]+" ";
        }
        s+="\n";
        s+="finishPadLocationY=";
        for(int i=0;i<finishPadLocationY.length;i++){
            s+=finishPadLocationY[i]+" ";
        }
        s+="\n";
        s+="agentsLocationX=";
        for(int i=0;i<agentsLocationX.size();i++){
            s+=agentsLocationX.get(i)+" ";
        }
        s+="\n";
        s+="agentsLocationY=";
        for(int i=0;i<agentsLocationY.size();i++){
            s+=agentsLocationY.get(i)+" ";
        }
        s+="\n";
        return s;
    }

    public boolean MoveUp (){

        //making sure there are no hostages in the new location that were never carried by neo before that have damage
        //of 98 or higher since they are going to become agents in the next step
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==(neoLocationX-1) && hostageLocationY.get(i)==neoLocationY && hostageDamage.get(i)>=98){
                return false;
            }
        }
        //making sure there are no agents in the new location
        for(int i=0;i<agentsLocationX.size();i++){
            if(agentsLocationX.get(i)==(neoLocationX-1) && agentsLocationY.get(i)==neoLocationY){
                return false;
            }
        }

        //making sure new location is in the grid
        if((neoLocationX-1)>=0){
            neoLocationX--;
            //updating the carried hostages location
            for(int i=0;i<hostageLocationX.size();i++){
                if(hostageCarried.get(i)==true){
                    hostageLocationX.set(i,neoLocationX);
                }
            }
            return true;
        }
        else {
            return false;
        }
        }

    public boolean MoveDown (){

        //making sure there are no hostages in the new location that were never carried by neo before that have damage
        //of 98 or higher since they are going to become agents in the next step
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==(neoLocationX+1) && hostageLocationY.get(i)==neoLocationY && hostageDamage.get(i)>=98){
                return false;
            }
        }
        //making sure there are no agents in the new location
        for(int i=0;i<agentsLocationX.size();i++){
            if(agentsLocationX.get(i)==(neoLocationX+1) && agentsLocationY.get(i)==neoLocationY){
                return false;
            }
        }

        //making sure new location is in the grid
        if(neoLocationX+1<n){
            neoLocationX++;
            //updating the carried hostages location
            for(int i=0;i<hostageLocationX.size();i++){
                if(hostageCarried.get(i)==true){
                    hostageLocationX.set(i,neoLocationX);
                }
            }
        return true;
        }
        else {
            return false;
        }
    }

    public boolean MoveLeft (){

        //making sure there are no hostages in the new location that were never carried by neo before that have damage
        //of 98 or higher since they are going to become agents in the next step
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==neoLocationX && hostageLocationY.get(i)==(neoLocationY-1) && hostageDamage.get(i)>=98){
                return false;
            }
        }
        //making sure there are no agents in the new location
        for(int i=0;i<agentsLocationX.size();i++){
            if(agentsLocationX.get(i)==neoLocationX && agentsLocationY.get(i)==(neoLocationY-1)){
                return false;
            }
        }

        //making sure new location is in the grid
        if(neoLocationY-1>=0){
            neoLocationY--;
            //updating the carried hostages location
            for(int i=0;i<hostageLocationX.size();i++){
                if(hostageCarried.get(i)==true){
                    hostageLocationY.set(i,neoLocationY);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public boolean MoveRight (){

        //making sure there are no hostages in the new location that were never carried by neo before that have damage
        //of 98 or higher since they are going to become agents in the next step
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==neoLocationX && hostageLocationY.get(i)==(neoLocationY+1) && hostageDamage.get(i)>=98){
                return false;
            }
        }
        //making sure there are no agents in the new location
        for(int i=0;i<agentsLocationX.size();i++){
            if(agentsLocationX.get(i)==neoLocationX && agentsLocationY.get(i)==(neoLocationY+1)){
                return false;
            }
        }

        //making sure new location is in the grid
        if(neoLocationY+1<m){
            neoLocationY++;
            //updating the carried hostages location
            for(int i=0;i<hostageLocationX.size();i++){
                if(hostageCarried.get(i)==true){
                    hostageLocationY.set(i,neoLocationY);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public boolean carry (){
        for (int i = 0; i < hostageLocationX.size(); i++) {
            if (neoLocationX == hostageLocationX.get(i)&& neoLocationY == hostageLocationY.get(i) && c>currentCarried) {
                hostageCarried.set(i,true);
                currentCarried++;
                return true;
            }
        }
            return false;
    }

    public boolean drop(){
        boolean dropped= false;
        ArrayList<Integer> hostagesToBeRemovedIndexes = new ArrayList<Integer>();
        if(neoLocationX==telephoneBoothX && neoLocationY==telephoneBoothY && currentCarried>0 ){
            for (int i = 0; i < hostageLocationX.size(); i++) {
                if (hostageCarried.get(i)==true) {
                    if(hostageDamage.get(i)<100){
                        hostagesSaved++;
                    }
                    currentCarried--;
                    hostagesToBeRemovedIndexes.add(i);
                    dropped=true;
                }
            }
            for(int i=0;i<hostagesToBeRemovedIndexes.size();i++){
                hostageDamage.remove(hostagesToBeRemovedIndexes.get(i)-i);
                hostageCarried.remove(hostagesToBeRemovedIndexes.get(i)-i);
                hostageLocationX.remove(hostagesToBeRemovedIndexes.get(i)-i);
                hostageLocationY.remove(hostagesToBeRemovedIndexes.get(i)-i);
            }
        }
        return dropped;
    }

    public boolean fly(){
        for(int i=0; i<startPadLocationX.length;i++){
            if(neoLocationX==startPadLocationX[i] && neoLocationY==startPadLocationX[i]){
                neoLocationX=finishPadLocationX[i];
                neoLocationY=finishPadLocationY[i];
                //updating the carried hostages location
                for(int j=0;j<hostageLocationX.size();j++){
                    if(hostageCarried.get(j)==true){
                        hostageLocationX.set(j,finishPadLocationX[i]);
                        hostageLocationY.set(j,finishPadLocationY[i]);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean takePill(){
        //loop over pills arraylist and check if neo is at the same location as a pill then take it and remove it from the arraylist
        for(int i=0;i<pillLocationX.size();i++){
            if(neoLocationX==pillLocationX.get(i) && neoLocationY==pillLocationY.get(i)){
                pillLocationX.remove(i);
                pillLocationY.remove(i);
                // loop over all living hostages and decrease their damage by 22 
                for(int j=0;j<hostageLocationX.size();j++){
                    //make sure that if neo is carrying dead hostages we don't decrease their damage
                    if(hostageDamage.get(j)!=100){
                        hostageDamage.set(j,hostageDamage.get(j)-20);
                        if(hostageDamage.get(j)<0)
                            hostageDamage.set(j,0);
                    }
                }
                //decreasing neo's damage by 20
                neoDamage-=20;
                if(neoDamage<0)
                    neoDamage=0;
                return true;
            }
        }
        return false;
    }
    
    public boolean kill(){

        //check if there is no alive hostage in the same location as neo
        //note that this check happens before increasing the hostage damage by 2
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==neoLocationX && hostageLocationY.get(i)==neoLocationY && hostageCarried.get(i)==false){
                return false;
            }
        }

        //loop over agents arraylist and check if neo is adjacent 
        //to an an agent then kill it and remove it from the arraylist and decrease neo's damage by 20

        boolean killed=false;

        //kill all agents in adjacent cells
        ArrayList<Integer> agentsToBeRemovedIndexes = new ArrayList<Integer>();
        for(int i=0;i<agentsLocationX.size();i++){
            if(isAgentAdjacent(agentsLocationX.get(i),agentsLocationY.get(i))){
                agentsKilled++;
                agentsToBeRemovedIndexes.add(i);
            }
        }
        for(int i=0;i<agentsToBeRemovedIndexes.size();i++){
            agentsLocationX.remove(agentsToBeRemovedIndexes.get(i)-i);
            agentsLocationY.remove(agentsToBeRemovedIndexes.get(i)-i);
        }
        //if neo kills an agent(s) then increase neo's damage by 20
        if(killed){
            neoDamage+=20;
            if(neoDamage>100)
                neoDamage=100;
        }
        return killed;
    }
    
    public boolean isAgentAdjacent(int agentX,int agentY){
        if(neoLocationX==agentX && neoLocationY==agentY+1){
            return true;
        }
        else if(neoLocationX==agentX && neoLocationY==agentY-1){
            return true;
        }
        else if(neoLocationX==agentX+1 && neoLocationY==agentY){
            return true;
        }
        else if(neoLocationX==agentX-1 && neoLocationY==agentY){
            return true;
        }
        else {
            return false;
        }
    }
    
    public void Step(){
        //increase hostage damage by 2
        ArrayList<Integer> hostagesToBeRemovedIndexes = new ArrayList<Integer>();
        for(int i =0;i<hostageDamage.size();i++){
            //only increase damage if hostage is not dead
            int hostDam = hostageDamage.get(i);
            if(hostDam<98){
                hostageDamage.set(i,hostDam+2);
            }
            else{
                if(hostDam>=98 && hostDam<100){
                    hostageDamage.set(i,100);
                    hostagesDead++;
                }
            }
            //convert hostage to agent if damage is equal to or greater than 100 and is not carried by neo
            if(hostDam>=100){
                if(hostageCarried.get(i)==false){
                    agentsLocationX.add(hostageLocationX.get(i));
                    agentsLocationY.add(hostageLocationY.get(i));
                    hostagesToBeRemovedIndexes.add(i);
                }
            }
        }
        for(int i=0;i<hostagesToBeRemovedIndexes.size();i++){
            hostageLocationX.remove(hostagesToBeRemovedIndexes.get(i)-i);
            hostageLocationY.remove(hostagesToBeRemovedIndexes.get(i)-i);
            hostageDamage.remove(hostagesToBeRemovedIndexes.get(i)-i);
            hostageCarried.remove(hostagesToBeRemovedIndexes.get(i)-i);
        }
    }

}
