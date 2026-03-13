package week4_practices_java;
import java.util.Scanner;
//klavyeden girilen başlangıç ve bitiş değeri arasında 8'in katı olup 5'in
//katı olmayan sayıları ekrana yazdıran programı yazınız. (sayı 8
//bölünebilecek ama 5 e bölünemeyecek, başlangıç sayısının bitiş
//sayısından küçük olduğunu varsayınız)
//do-while

public class week4_901 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int baslangic, bitis;
        int kalan5, kalan8;

        System.out.println("baslangic icin deger giriniz : ");
        baslangic = input.nextInt();

        System.out.println("Bitis icin deger giriniz : ");
        bitis = input.nextInt();

        do{
            kalan5 = baslangic%5;
            kalan8 = baslangic%8;

            if(kalan5 != 0 && kalan8==0)
                System.out.println(baslangic);
            baslangic++;
        }
        while(baslangic<=bitis);
    }
    
}
