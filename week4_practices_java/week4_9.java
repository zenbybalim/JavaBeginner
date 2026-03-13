package week4_practices_java;
import java.util.Scanner;
//0 dan klavyeden girilen değere kadar 
// aradaki sayılardan 7 nin katlarını 
// ekrana yazdıran for döngüsü programi

public class week4_9 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);


        int sayi=0;
        int bitis=0;
        int sayi2=0;

        System.out.println("Sayi giriniz : ");
        bitis = input.nextInt();

        for(sayi=0; sayi<=bitis; sayi++){

            sayi2 = sayi%7;
            if(sayi2==0)
                System.out.println(sayi);

        }
        


    }
    
}
