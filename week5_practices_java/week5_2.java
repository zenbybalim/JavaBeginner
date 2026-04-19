package week5_practices_java;
import java.util.Random;
// 100 elemanlı bir diziye 1-200 arasında rastgele sayılar üreterek aktaran, bu sayılar üzerinde; 
// a)100'den büyük sayıların sayısını bulan 
// b)100'den küçük sayıların ortalamasını bulan
// c)Çift sayıların sayısını bulan

public class week5_2 {
    public static void main(String[] args) {
        Random rastgele = new Random();

        int[] sayi = new int[100];
        int i, sayac1=0, sayac2=0, sayac3=0, toplam=0, sayi2;
        double ortalama=0.0;
        
        for(i=0;i<100;i++){
            sayi[i] = rastgele.nextInt(200);
        
        if(sayi[i] > 100){
            sayac1++;
        }
        else if (sayi[i]<100){
            toplam = toplam + sayi[i];
            sayac3++;

        }

        sayi2 = sayi[i]%2;

        if(sayi2==0){
            sayac2++;
        }
    }
       ortalama = toplam/sayac3;

        System.out.println("100 den buyuk sayilarin sayisi = " +sayac1);
        System.out.println("100 den kucuk sayilarin toplami = " +toplam);
        System.out.println("çift sayilarin sayisi = " +sayac2);
    }
    
}