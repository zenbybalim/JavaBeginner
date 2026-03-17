package week4_practices_java;
import java.util.Scanner;
//Kuvvet hesaplayan bir program yazınız. Klavyeden girilen sayı ve istenilen kuvvetini hesaplayınız.

public class week4_912 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int sayi;
        int i=1, kuvvet=0;
        int sonuc=1;
        
        System.out.println("Sayi giriniz");
        sayi = input.nextInt();

        System.out.println("kuvvet giriniz:");
        kuvvet = input.nextInt();

        do{
            sonuc = sonuc * sayi;
            i++;        
        }while(i<=kuvvet);

        System.out.println("Sonuc = "+sonuc);
    }
}
