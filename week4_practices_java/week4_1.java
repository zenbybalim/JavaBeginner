package week4_practices_java;
//0-100 arasindaki tek sayilarin do-while döngüsü ile ekrana yazilmasi
public class week4_1 {
    public static void main(String[] args){
        int sayi=0;
        int kalan;

        do{
            kalan =  sayi%2;
            if(kalan == 1)
                System.out.println(sayi);
            sayi++;
        }
        while(sayi<100);
    }
    
}
