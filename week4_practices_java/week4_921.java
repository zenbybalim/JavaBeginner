package week4_practices_java;
//Fibonacci dizisi bir sayı dizisidir ve
//  {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, …} şeklinde devam eden sonsuz sayılardan oluşur. 
// İlk 100 terimi ekrana yazdırınız.

public class week4_921 {
    public static void main(String[] args){
        int eleman1=1, eleman2=1;
        int sonuc=0;
        int i=0;

        System.out.println("1. terim = " +eleman1);

        for(i=1; i<100; i++){
            sonuc = eleman1 + eleman2;
            eleman1 = eleman2;
            eleman2 = sonuc;

             System.out.println((i+1) +". terim = " +sonuc);
        }
        
       


    }
    
}
