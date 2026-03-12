package week3_practices_java;
import java.util.Scanner;
//Bir öğrencinin bir dersten aldığı başarı notunun hesaplanması şu şekilde olacaktır,
//a.
//Öğrenci 3 adet Kısa sınav, 2 adet Vize sınavına ve 1 adet Final sınavına girmektedir.
//b.
//Kısa sınav notları ve vize notları klavyeden girilerek bu notların aritmetik ortalamaları alınacaktır,
//c.
//Son olarak final notu girilerek, başarı puanı hesaplanıp ekrana yazılacaktır.
//d.
//Başarı puanı şu şekilde hesaplanmaktadır : BP = (KSort * 50/100 +Vort * 50 / 100 ) * 40/100 + F * 60/100;
//Not : BP : Başarı puanı, KSort :Kısa sınavların not ortalaması, Vort :Vizelerin not ortalaması, F: Final Notu

public class week3_911 {
    public static void main(String[] args){
        Scanner input =  new Scanner(System.in);

        Double ks1, ks2, ks3, v1, v2, f;
        Double ksort, vort, bp=0.0;
        
        System.out.println("1. Kisa sinav sonucunuzu giriniz : ");
        ks1 = input.nextDouble();

        System.out.println("2. Kisa sinav sonucunuzu giriniz : ");
        ks2 = input.nextDouble();

        System.out.println("3. Kisa sinav sonucunuzu giriniz : ");
        ks3 = input.nextDouble();

        System.out.println("1. Vize sonucunuzu giriniz : ");
        v1 = input.nextDouble();

        System.out.println("2. Vize sonucunuzu giriniz : ");
        v2 = input.nextDouble();

        System.out.println("Final Sinavi sonucunuzu giriniz : ");
        f = input.nextDouble();

        ksort = (ks1 + ks2 + ks3)/3.0;
        System.out.println("Kisa sinav ortalamaniz = " +ksort);

        vort = (v1 + v2)/2.0;
        System.out.println("Vize sinavlari ortalamaniz = " +vort);

        bp = (((ksort * 0.5) + (vort * 0.5) ) * 0.4) + (f * 0.6);
        System.out.println("Başari Puaniniz = " +bp);    
    }
}
