package week4_practices_java;
import java.util.Scanner;
//0 dan klavyeden girilen değere kadar 
// aradaki sayılardan 7 nin katlarını 
// ekrana yazdıran do-while programi.

public class week4_7 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int sayi=0;
        int bitis=0;
        int sayi2=0;

        System.out.println("Sayi giriniz = ");
        bitis = input.nextInt();

        do{
            sayi2 = sayi%7;
            if(sayi2==0)
                System.out.println(sayi);
            sayi++;
        }while(sayi<=bitis);
    }    
}
