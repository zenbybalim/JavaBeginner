package week4_practices_java;
import java.util.Random;

public class week4_904 {
    public static void main(String[] args){
        Random rdm = new Random();

        int sayi;

        sayi = rdm.nextInt(10);
        System.out.println(sayi);

        System.out.println("---");

        for(int i=0; i < 5; i++){
            sayi = 10 + rdm.nextInt(40);
            System.out.println(sayi);
        }

    }  
}
