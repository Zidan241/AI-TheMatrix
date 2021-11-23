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
    public State(int n,int m,int c,int telephoneBoothX,int telephoneBoothY,int neoLocationX,int neoLocationY,int[] hostageLocationX,int[] hostageLocationY,int[] hostageDamage,int[] pillLocationX,int[] pillLocationY,int[] startPadLocationX,int[] startPadLocationY,int[] finishPadLocationX,int[] finishPadLocationY){
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
    }
}
