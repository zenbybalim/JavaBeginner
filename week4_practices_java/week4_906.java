package week4_practices_java;
import java.util.Scanner;
//carpim tablosu

public class week4_906 {
    public static void main(String[] args){
            Scanner input = new Scanner(System.in);

            int  sonuc, sayi=0, carpim;

            System.out.print("bir sayi giriniz = ");
            sayi = input.nextInt();

            System.out.print("carpim giriniz = ");
            carpim = input.nextInt();

            for(int i=0; i<=sayi; i++){
                sonuc = i * carpim;
                System.out.println(+i + "x" +carpim + "=" +sonuc);
            }

    }
    
}
