import java.util.Scanner;

public class week2_2 {
public static void main(String[] args){
    Scanner input = new Scanner(System.in);

    int sayi, mod;

    System.out.println("sayi giriniz=");
    sayi = input.nextInt();

    mod = sayi%2;

    
        if(mod == 0) {
            System.out.println("Çift sayi");

        }else{
            System.out.println("Tek sayi");
}
}    
}
