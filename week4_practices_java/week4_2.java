package week4_practices_java;
//0-100 arasidanki sayilarin while dongusu ile yazilmasi

public class week4_2 {
    public static void main(String[] args){
    int sayi=0;
    int kalan;

    while(sayi<100){
        
        kalan = sayi %2;
        if(kalan==1)
            System.out.println(sayi);
        sayi++;
    }   
}
}