package week4_practices_java;
import java.util.Scanner;
//Klavyeden girilen 2 sayıyı toplayıp sonucunu ekrana yazdıktan sonra,
//kullanıcıya “Tekrar Hesaplama Yapmak İstiyormusunuz?” diye soru
//sorup, eğer kullanıcı “e” tuşuna basarsa yeniden işlemleri
//gerçekleştiren program kodlarını yazınız.
public class week4_909 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int sayi1, sayi2, toplam;
        String secim;

        do{
        System.out.println("ilk sayiyi giriniz = ");
        sayi1 = input.nextInt();

        System.out.println("ikinci sayiyi giriniz = ");
        sayi2 = input.nextInt();

        toplam = sayi1 + sayi2;
        System.out.println("Toplam = " +toplam);

        System.out.println("tekrar hesaplama yapmak için e harfine basiniz = ");
        secim = input.next();

        }while(secim.equals("e")|| secim.equals("E"));
        
        
    }
}
