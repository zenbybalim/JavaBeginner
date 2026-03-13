package week4_practices_java;
//0-100 arasindaki tek sayilarin for dobgusu ile ekranara yazdirilmasi
public class week4_6 {
    public static void main(String[] args){
        int sayi=100;
        int kalan;

        for(sayi=100; sayi>=0; sayi--){
            kalan = sayi%2;
            if(kalan==1)
                System.out.println(sayi);
        }

    }
    
}
