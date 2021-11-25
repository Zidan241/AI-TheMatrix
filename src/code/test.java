package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class test {

    public static void main(String[] args) {
        ArrayList<String> arrayListObject = new ArrayList<>(); 
         
        arrayListObject.add("A");
        arrayListObject.add("B");
        arrayListObject.add("C");
        arrayListObject.add("D");
         
        System.out.println(arrayListObject);   
         
        ArrayList<String> arrayListClone =  (ArrayList<String>) arrayListObject.clone();

        System.out.println(arrayListClone);  


        arrayListClone.add("E");
        arrayListClone.set(0, "Z");

        System.out.println("======");   

        System.out.println(arrayListObject);   
         
        System.out.println(arrayListClone);   

        arrayListObject.add("F");
        arrayListObject.set(1, "change");

        System.out.println("======");   

        System.out.println(arrayListObject);   
         
        System.out.println(arrayListClone); 

        System.out.println("======");

        ArrayList<Integer> hostagesDamage= new  ArrayList<Integer> (2);
        ArrayList<Boolean> hostagesCarried= new  ArrayList<Boolean> (Arrays.asList(new Boolean[2]));
        Collections.fill(hostagesCarried, false);
        hostagesDamage.add(0);
        hostagesDamage.add(1);
        hostagesDamage.add(2);

        ArrayList<Integer> hostagesDamageClone =  (ArrayList<Integer>) hostagesDamage.clone();
        ArrayList<Boolean> hostagesCarriedClone =  (ArrayList<Boolean>) hostagesCarried.clone();


        System.out.println(hostagesDamage);
        System.out.println(hostagesCarried);
        System.out.println(hostagesDamageClone);
        System.out.println(hostagesCarriedClone);

        System.out.println("======");

        hostagesDamageClone.add(3);
        hostagesCarriedClone.add(true);

        System.out.println(hostagesDamage);
        System.out.println(hostagesCarried);
        System.out.println(hostagesDamageClone);
        System.out.println(hostagesCarriedClone);

        System.out.println("======");

        int[] array = {1,2,3};

        int[] arrayClone =  array.clone();

        System.out.print("[");
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
        System.out.println("]");
        System.out.print("[");
        for(int i = 0; i < arrayClone.length; i++) {
            System.out.print(arrayClone[i]);
        }
        System.out.println("]");

        System.out.println("======");

        arrayClone[0] = 5;

        System.out.print("[");
        for(int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
        System.out.println("]");
        System.out.print("[");
        for(int i = 0; i < arrayClone.length; i++) {
            System.out.print(arrayClone[i]);
        }
        System.out.println("]");

        System.out.println("======");

        int a = 7;
        int b = a;

        System.out.println(a);
        System.out.println(b);

        System.out.println("======");

        b++;

        System.out.println(a);
        System.out.println(b);

    }
    
}
