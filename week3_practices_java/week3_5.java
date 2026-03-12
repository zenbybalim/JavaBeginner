package week3_practices_java;
import java.util.Scanner; 

//Klavyeden girilen birbirine eşit olmadığı düşünülen iki sayıdan büyüğünü ekrana yazdıran program

public class week3_5 {
    public static void main(String[] args){
       Scanner input = new Scanner(System.in);
       
       Double sayi1, sayi2;

       System.out.println("1.sayiyi Giriniz : ");
       sayi1 = input.nextDouble();

       System.out.println("2.sayiyi Giriniz : ");
       sayi2 = input.nextDouble();

       if(sayi1>sayi2){
        System.out.println("Büyük olan sayi = " +sayi1);
       }
       
       else{
        System.out.println("Büyük olan sayi = " +sayi2);
       }
       
    }
    
}

