package week3_practices_java;
import java.util.Scanner;
//1.1.Klavyeden girilen bir sayının pozitif, negatif veya sıfıra eşit olduğunu ekrana yazdıran program

public class week3_4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
      
        double sayi;

        System.out.println("Sayi giriniz : ");
        sayi = input.nextDouble();

        if (sayi == 0){
            System.out.println("Sayi sifira eşit");
        }

        else if(sayi < 0){
            System.out.println("Sayi negatif");
        }

        else{
            System.out.println("Sayi Pozitif");
        }
}
}