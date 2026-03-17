package week4_practices_java;
import java.util.Scanner;
//Klavyeden girilen bir tam sayının asal sayı olup olmadığını bulan programın kodlarını yazınız

public class week4_914 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int sayi;
        int i=0, bolen=1;
        int kalan=0;

        System.out.println("Sayi giriniz; ");
        sayi = input.nextInt();

        while(bolen<=sayi){
            kalan = sayi%bolen;

            if(kalan==0){
                i++;
            }
            bolen++;
        }
         if(i==2){
            System.out.println("girdiginiz sayi bir asal sayi");
         }
         else{
            System.out.println("Girdiginiz sayi asal sayi degil");
         }
    }
}
