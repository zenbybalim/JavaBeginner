package week4_practices_java;
//0-100 arasindaki tek sayiların do-while dongusu ile buyukten kucuge yazilmasi 
public class week4_4 {
    public static void main(String[] args){
         int sayi=100;
         int kalan;

         do{
            kalan = sayi%2;
            if(kalan==1)
                System.out.println(sayi);
            sayi--;
         }
         while(sayi>=0);
    }
    
}
