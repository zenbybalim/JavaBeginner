package week4_practices_java;
import java.util.Scanner;
import java.util.Random;
//Toplama işlemini öğretmeye çalışan bir oyun programı yazılacaktır. 
// Oyun başladığı zaman rastgele 2 tane 1-100 arasında sayı tutulacak, 
// tutulan sayılar ekrana gösterilecek ve kullanıcıya bu sayıların toplamı nedir diye sorulacaktır. 
// Eğer kullanıcı doğru cevap verirse “Tebrikler Bildiniz” değil ise “Üzgünüm Bilemediniz” diye mesaj verecektir. 
// Her cevaptan sonra “Tekrar Oynamak istiyormusunuz(e/E)?” şeklinde bir soru sorulacak ve 
// eğer kullanıcı “e” veya “E” ile karşılık verirse oyun tekrar başlayacaktır. 
// Kullanıcının puanı her doğru cevap için 5 puan artacak, her yanlış cevap için ise 2 puan azalacaktır.
//  Oyun sonlandığında kullanıcının verdiği doğru cevap sayısı, yanlış cevap sayısı ve puanı ekranda listelenmelidir.
//  Bu işlemleri yapan programın kodlarını yazınız.

public class week4_922 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Random rastgele = new Random();
        int say1, say2, toplam, cevap;
        int puan=0;
        int dogruSayisi = 0;
        int yanlisSayisi = 0;
        String harf;

        do{
        say1 = rastgele.nextInt(101);
        say2 = rastgele.nextInt(101);
        cevap = say1 + say2;

        System.out.println(say1 +"+" +say2 +" = ");
        toplam = input.nextInt();

        if(toplam ==  cevap){
            System.out.println("Tebrikler Dogru Bildiniz!");
            puan = puan+5;
            dogruSayisi++;
        }
        else{
            System.out.println("Yanlis Bildiniz");
            puan = puan - 2;
            yanlisSayisi++;
        }
        System.out.println("dogru cevap sayiniz = " +dogruSayisi);
        System.out.println("Yanlis cevap sayiniz = " +yanlisSayisi);
        System.out.println("Toplam puaniniz = " +puan); 

        System.out.println("Tekrar denemek ister misiniz ? (tekrar denemek için e/E tuslayiniz)");
        harf = input.next();

    }while(harf.equals("e")|| harf.equals("E"));

    }
    
}
