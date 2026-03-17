package week4_practices_java;
import java.util.Scanner;
//Klavyeden girilen bir sayının tam bölenlerini ekrana yazdırınız.

public class week4_913 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int sayi;
        int bolen=1;
        int kalan=0;

        System.out.println("sayi giriniz");
        sayi = input.nextInt();

        System.out.println("Giridiğiniz sayinin tam bölenleri = ");

        do{
            kalan = sayi%bolen;

            if(kalan==0){
                System.out.println(bolen);
            }
            bolen++;
        }while(bolen<=sayi);
    }
    
}
