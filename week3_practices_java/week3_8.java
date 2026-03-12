package week3_practices_java;
import java.util.Scanner;
//Klavyeden girilen a ve b sayılarından a nın b ye tam bölünüp bölünmediğini bulan ve sonucu ekrana yazdıran program

public class week3_8 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Double a, b, kalan;

        System.out.println("ilk sayiyi giriniz : ");
        a = input.nextDouble();

        System.out.println("İkinci sayiyi giriniz : ");
        b = input.nextDouble();

        kalan = a%b;

        if(kalan==0){
            System.out.println("sayilar birbirine tam bölünür. ");
        }
        else{
            System.out.println("sayilar birbirine tam bölünmez, kalan = " +kalan);
        }

    }
    
}
