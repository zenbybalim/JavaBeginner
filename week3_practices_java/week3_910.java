package week3_practices_java;
import java.util.Scanner;
//İdeal kilo hesabı yapan bir program yazılmak istenmektedir. 
// Programın çalışması şu şekilde olmalıdır. Kişinin boy(cm olarak girildiği düşünülecek-örnek:170)
// yaş, cinsiyet(Erkek için E, kız için K bilisi girildiği düşünülecek) 
// ve o andaki kilosu (kg olarak girildiği düşünülecek-örnek: 65) 
// klavyeden girilerek aşağıdaki formüle göre ideal kilosunu hesaplanacaktır ve ekrana yazdırılacaktır. 
// İdeal kilosu ekrana yazdırıldıktan sonra öğrencinin kilo farkını ekrana yazdırılmalıdır 
// (örnek: 10 kilo vermelisiniz veya 2,5 kilo almalısınız veya tebrikler ideal kilonuzdasınız).
//a.
//İdeal Kilo = ( boy–100 + yaş/10 )*K formülü ile hesaplanır,
//b.
//K sabiti için eğer cinsiyet kız ise K = 0,8 alınmalı,
//c.
//K sabiti için eğer erkek ise K = 0,9 alınmalı,
public class week3_910 {
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);

    double boy, yas, kilo, idealkilo=0, kilofarki=0;
    char cinsiyet;

    System.out.println("Boyunuzu santimetre cinsinden giriniz = ");
    boy = input.nextDouble();

    System.out.println("Yasinizi giriniz = ");
    yas = input.nextDouble();

    System.out.println("Güncel kilonuzu kilogram cinsinden giriniz = ");
    kilo = input.nextDouble();

    System.out.println("Cinsiyetinizi giriniz (k/e) : ");
    cinsiyet = input.next().charAt(0);

    switch(cinsiyet){
        case 'k':
        case 'K':
            idealkilo = ((boy - 100 + (yas / 10)) * 0.8);
            System.out.println("İdeal Kilonuz = " +idealkilo);
            break;

        case 'e':
        case 'E':
           idealkilo = ((boy - 100 + (yas / 10)) * 0.9); 
           System.out.println("İdeal Kilonuz = " +idealkilo);
           break;

        default: 
           System.out.println("Hata: Cinsiyet için geçersiz harf girdiniz!");
    }

    if (idealkilo > 0){

    kilofarki = kilo - idealkilo;
    System.out.println("kilo farkiniz = " +kilofarki);
{
    if(kilofarki == 0){
        System.out.println("Tebrikler, ideal kilonuzdasiniz!");
    }
    else if(kilofarki < 0){
        System.out.println("ideal kilonuza ulaşmak için almaniz gereken kilo = " + kilofarki );
    }
    else if(kilofarki > 0){
        System.out.println("İdeal kilonuza ulaşmak için vermeniz gereken kilo =" + kilofarki);
    }
}   
    } 
  }  
}
