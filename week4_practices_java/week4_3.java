package week4_practices_java;
//0-100 arasindaki tek sayilarin for döngüsü ile yazilmasi
public class week4_3 {
    public static void main(String[] args){
        int sayi=0;
        int kalan;

        for(sayi=0; sayi<100; sayi++){
            kalan = sayi%2;
            if(kalan==1)
                System.out.println(sayi);
        }
    }
}
