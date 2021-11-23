import java.util.ArrayList;

public class State {
    int n;
    int m;
    int c;
    int telephoneBoothX;
    int telephoneBoothY;
    int neoLocationX;
    int neoLocationY;
    int neoDamage=0;
    int[] hostageLocationX;
    int[] hostageLocationY;
    boolean[] hostageCarried; 
    int currentCarried=0;
    int[] hostageDamage;
    int[] pillLocationX;
    int[] pillLocationY;
    int[] startPadLocationX;
    int[] startPadLocationY;
    int[] finishPadLocationX;
    int[] finishPadLocationY;
    int[] agentsLocationX;
    int[] agentsLocationY;
    public State(int n,int m,int c,int telephoneBoothX,int telephoneBoothY,int neoLocationX,int neoLocationY,int[] hostageLocationX,int[] hostageLocationY,int[] hostageDamage,int[] pillLocationX,int[] pillLocationY,int[] startPadLocationX,int[] startPadLocationY,int[] finishPadLocationX,int[] finishPadLocationY,int[] agentsLocationX,int[] agentsLocationY){
        this.n=n;
        this.m=m;
        this.c=c;
        this.hostageCarried = new boolean[hostageLocationX.length];; 
        this.telephoneBoothX=telephoneBoothX;
        this.telephoneBoothY=telephoneBoothY;
        this.neoLocationX=neoLocationX;
        this.neoLocationY=neoLocationY;
        this.hostageLocationX=hostageLocationX;
        this.hostageLocationY=hostageLocationY;
        this.hostageDamage=hostageDamage;
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
        for(int i=0;i<hostageLocationX.length;i++){
            s+=hostageLocationX[i]+" ";
        }
        s+="\n";
        s+="hostageLocationY=";
        for(int i=0;i<hostageLocationY.length;i++){
            s+=hostageLocationY[i]+" ";
        }
        s+="\n";
        s+="hostageDamage=";
        for(int i=0;i<hostageDamage.length;i++){
            s+=hostageDamage[i]+" ";
        }
        s+="\n";
        s+="pillLocationX=";
        for(int i=0;i<pillLocationX.length;i++){
            s+=pillLocationX[i]+" ";
        }
        s+="\n";
        s+="pillLocationY=";
        for(int i=0;i<pillLocationY.length;i++){
            s+=pillLocationY[i]+" ";
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
        for(int i=0;i<agentsLocationX.length;i++){
            s+=agentsLocationX[i]+" ";
        }
        s+="\n";
        s+="agentsLocationY=";
        for(int i=0;i<agentsLocationY.length;i++){
            s+=agentsLocationY[i]+" ";
        }
        s+="\n";
        return s;
    }
}
