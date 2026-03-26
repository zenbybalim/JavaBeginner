package week5_practices_java;
import java.util.Scanner;
//1 haftaya ait sıcaklık bilgilerini içerisinde barındırabilecek bir dizi tanımlayınız. 
//Klavyeden bu dizinin elemanlarına veri girişi yapınız. Ardından sıcaklık ortalamasını hesaplayıp ekrana yazdırınız.

public class week5_1 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int[] sicaklik = new int[7];
        int i=0, toplam=0;
        double ortalama=0.0;

        for(i=0;i<7;i++){
            System.out.println((i+1) +". günün sicakligi = ");
            sicaklik[i] = input.nextInt();
        }

        for(i=0;i<7;i++){
            toplam = toplam + sicaklik[i];
        }
        ortalama = toplam/7;
        System.out.println("7 gunluk ortalama = " +ortalama);

    }
    
}
