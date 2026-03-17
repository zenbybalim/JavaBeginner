package week4_practices_java;
import java.util.Scanner;

public class week4_919 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int x, y;
        int sonuc=0;

        System.out.println("-30 ve 30 araliginda x, y'den farkli olmak üzere, bir x degeri giriniz = ");
        x = input.nextInt();

        System.out.println("-30 ve 30 araliginda x, y'den farkli olmak üzere, bir y degeri giriniz = ");
        y = input.nextInt();

        if(x<0){
            x = x * -1;
        }
        if(y<0){
            y = y * -1;
        }

        if((x + y)<=30){
            sonuc = x + y;
            System.out.println("|x|+|y| = " +sonuc);
        }

    }
    
}
