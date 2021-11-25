import java.util.ArrayList;

public class State {
    int n;
    int m;
    int c;
    int telephoneBoothX;
    int telephoneBoothY;
    int neoLocationX;
    int neoLocationY;
    int neoDamage;
    ArrayList<Integer>  hostageLocationX;
    ArrayList<Integer>  hostageLocationY;
    ArrayList<Integer>  hostageDamage;
    ArrayList<Boolean>  hostageCarried; 
    int currentCarried;
    int hostagesSaved;
    int hostagesDead;
    ArrayList<Integer> pillLocationX;
    ArrayList<Integer>  pillLocationY;
    int[] startPadLocationX;
    int[] startPadLocationY;
    int[] finishPadLocationX;
    int[] finishPadLocationY;
    ArrayList<Integer> agentsLocationX;
    ArrayList<Integer>  agentsLocationY;


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
    ArrayList<Integer> agentsLocationY
    ) {
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
        this.pillLocationX=pillLocationX;
        this.pillLocationY=pillLocationY;
        this.startPadLocationX=startPadLocationX;
        this.startPadLocationY=startPadLocationY;
        this.finishPadLocationX=finishPadLocationX;
        this.finishPadLocationY=finishPadLocationY;
        this.agentsLocationX=agentsLocationX;
        this.agentsLocationY=agentsLocationY;
    }
    
    public String toString(){
        String s="";
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
            if(hostageLocationX.get(i)==(neoLocationX+1) && hostageLocationY.get(i)==neoLocationY && hostageDamage.get(i)>=98 && hostageCarriedBefore.get(i)==false){
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

    public boolean MoveDown (){

        //making sure there are no hostages in the new location that were never carried by neo before that have damage
        //of 98 or higher since they are going to become agents in the next step
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==(neoLocationX-1) && hostageLocationY.get(i)==neoLocationY && hostageDamage.get(i)>=98 && hostageCarriedBefore.get(i)==false){
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
        if(neoLocationX-1<n){
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

    public boolean MoveLeft (){

        //making sure there are no hostages in the new location that were never carried by neo before that have damage
        //of 98 or higher since they are going to become agents in the next step
        for(int i=0;i<hostageLocationX.size();i++){
            if(hostageLocationX.get(i)==neoLocationX && hostageLocationY.get(i)==(neoLocationY-1) && hostageDamage.get(i)>=98 && hostageCarriedBefore.get(i)==false){
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
        if(neoLocationY-1<n){
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
            if(hostageLocationX.get(i)==neoLocationX && hostageLocationY.get(i)==(neoLocationY+1) && hostageDamage.get(i)>=98 && hostageCarriedBefore.get(i)==false){
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
        if(neoLocationY+1<n){
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
        if(neoLocationX==telephoneBoothX && neoLocationY==telephoneBoothY && currentCarried>0 ){
            for (int i = 0; i < hostageLocationX.size(); i++) {
                if (hostageCarried.get(i)==true) {
                    hostagesSaved++;
                    currentCarried--;
                    hostageLocationX.remove(i);
                    hostageLocationY.remove(i);
                    hostageCarried.remove(i);
                    hostageDamage.remove(i);
                    dropped=true;
                }
            }
        }
        if(!dropped){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean fly(){
        for(int i=0; i<startPadLocationX.length;i++){
            if(neoLocationX==startPadLocationX[i] && neoLocationY==startPadLocationX[i]){
                neoLocationX=finishPadLocationX[i];
                neoLocationY=finishPadLocationY[i];
                return true;
            }
        }
        return true;
    }


    public boolean takePill(){


        return false;
    }
    
    public boolean kill(){

        return false;
    }

    public void Step(){
        //increase hostage damage by 2
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
                    int x = hostageLocationX.remove(i);
                    int y = hostageLocationY.remove(i);
                    hostageDamage.remove(i);
                    agentsLocationX.add(x);
                    agentsLocationY.add(y);
                }
            }
        }
    }

}
