package week4_practices_java;
//0-100 arasindaki tek sayilarin while dongusu ile büyükten kucuge yazilmasi
public class week4_5 {
    public static void main(String[] args){
        int sayi=100;
        int kalan;

        while(sayi>=0){
            kalan = sayi %2;
            if(kalan==1)
                System.out.println(sayi);
            sayi--;
        }
    }
    
}
