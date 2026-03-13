package week4_practices_java;
import java.util.Scanner;
//Klavyeden negatif sayı girilinceye kadar 10 dan büyük sayıları
//toplayan ve bu sayıların adetini bulan programın kodlarını yazınız.

public class week4_910 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int sayi ;
        int toplam=0, adet=0;

        do{
            System.out.println("Sayi giriniz: ");
            sayi = input.nextInt();
            if(sayi>10){
                toplam += sayi;
            }
            adet++;
        }
        while(sayi>0);

        System.out.println("10 sayisindan buyuk sayilarin toplami = " +toplam);
        System.out.println("girdiginiz sayi adeti = (negatif sayi dahil)" +adet);
    
        }
    
}
