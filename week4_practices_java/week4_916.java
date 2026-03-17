package week4_practices_java;
import java.util.Scanner;
//Klavyeden girilen 30 tane sayının aritmetik ortalamasını bulan programın kodlarını yazınız.

public class week4_916 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int i=1, sayi;
        int aort=0, toplam=0;

        for(i=1;i<=30;i++){
            System.out.println("Sayi giriniz = ");
            sayi = input.nextInt();

            toplam = toplam + sayi;
            aort = toplam/i;
        }
        System.out.println("Aritmetik ortalama = " +aort);
    }
}
