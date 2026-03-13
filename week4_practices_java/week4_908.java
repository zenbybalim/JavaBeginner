package week4_practices_java;
//1 den 1000 e kadar 5’in katı olup 2 nin katı olmayan sayıların adetini
//bulan programı yazınız.
public class week4_908 {
    public static void main(String[] args){
        int sayi=0, adet=0;
        int kalan2, kalan5;

        for(sayi=0;sayi<1000;sayi++){
            kalan2 = sayi%2;
            kalan5 = sayi%5;
            if(kalan2 != 0 && kalan5 == 0){
                adet++;
            }


        }
         System.out.println("5 sayisinin kati olup 2 sayisinin kati olmayan sayilarin adeti = " +adet);
    }
    
}
