package week3_practices_java;
import java.util.Scanner;
//Klavyeden girilen pozitif bir sayının tek ise ekrana “TEK”, tek değilse “TEK DEĞİLDİR” yazdıran program

public class week3_6 {
  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    Double sayi;
    Double a;

    System.out.println("Pozitif bir tam sayi giriniz : ");
    sayi = input.nextDouble();

    a = sayi%2;

    if(a==0){
        System.out.println("Sayi tek değil.");
    }
    else{
        System.out.println("Sayi tek.");
    }
  }    
}
