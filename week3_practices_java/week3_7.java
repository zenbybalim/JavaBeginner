package week3_practices_java;
import java.util.Scanner;
//Klavyeden girilen pozitif sayının 7’ye tam bölünüp bölünmediğini bulan ve sonucu ekrana yazdıran program

public class week3_7 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        Double sayi, kalan;

        System.out.println("Pozitif bir sayi giriniz : ");
        sayi= input.nextDouble();

        kalan = sayi%7;

        if(kalan==0){
            System.out.println("Sayi 7'ye tam bölünür.");
        }
        else{
            System.out.println("Sayi 7'ye tam bölünmez, kalan = " +kalan);
        }


    }

}    