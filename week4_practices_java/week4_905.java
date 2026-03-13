package week4_practices_java;
import java.util.Scanner;
import java.util.Random;

public class week4_905 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Random rndm = new Random();

        int tahmin, sayi;

        sayi = rndm.nextInt(101);
        System.out.println("Tutulan sayiyi bul.");

        do{
            System.out.print("Tahmininizi giriniz = ");
            tahmin = input.nextInt();

            if(tahmin<sayi)
                System.out.println("daha yüksek bir deger girin!");
            else if(tahmin>sayi)
                System.out.println("Daha dusuk bir deger girin!");
            else if (tahmin==sayi)
                System.out.println("Dogru Tahmin!");
        }
        while(tahmin != sayi);
    }
    
}
