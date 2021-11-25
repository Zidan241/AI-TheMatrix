public class test {
    int x=0;

    public void updatex(){
        x++;
    }

    public static void main(String[] args) {
        test t = new test();
        System.out.println(t.x);
        t.updatex();
        System.out.println(t.x);
    }
    
}
