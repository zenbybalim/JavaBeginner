package week3_practices_java;
import java.util.Scanner;
//Market ürünlerinde KDV oranı %1, Yeme-İçme ürünlerinde KDV oranı %10 diğer ürünlerde ise %20 dir. 
// Buna göre klavyeden girilecek ürün gurubu bilgisine 
// ve ürün fiyatına göre ödenmesi gereken KDV miktarını hesaplayıp ekrana yazdıran programı yazınız.
// (Market için M, Yeme-içme için Y ve diğer ürünler için D bilgisinin girildiğini varsayınız.)

public class week3_916 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

    String urun;
    Double fiyat=0.0, kdv=0.0, toplam=0.0;

     System.out.println("Hangi market urununu aldiniz : Market icin M, Yeme-icme icin Y ve diger urunler icin D");
     urun = input.next();
     
     switch(urun){
        case "m":
        case "M":
            kdv = 0.01;
            break;
        case "y":
        case "Y":
            kdv = 0.1;
            break;
        case "d":
        case "D":
            kdv = 0.2;
            break;
        default: 
        System.out.println("Hata: Gecersiz urun bilgisi girdiniz!");        
     }
     if(urun.equals("m")||urun.equals("M")|| urun.equals("y")|| urun.equals("Y")||urun.equals("d")||urun.equals("D")){
      //if(kdv>0){} !!!seklinde de yazabilirim.
        System.out.println("Aldiginiz urunun fiyatini giriniz = ");
        fiyat = input.nextDouble();

        toplam = fiyat + (fiyat * kdv);
        kdv = fiyat * kdv;

        System.out.println("odemeniz gereken KdV tuttari = " +kdv);
        System.out.println("Odemeniz gereken toplam tutar = " +toplam);
     
     }
    }
    
}
