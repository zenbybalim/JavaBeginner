package week3_practices_java;
import java.util.Scanner;
//Klavyeden girilen iki direnç değerinden sonra yine 
// klavyeden girilen bağlantı tipine göre eşdeğer direnç 
// değerini bulan program(direnç değerlerinin ohm olarak girildiğini varsayın, 
// seri bağlantı için klavyeden s, paralel bağlantı için klavyeden p değerinin girildiği varsayılsın)

public class week3_9 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        Double direnc1, direnc2, eşdeğerdirenc;
        char baglanti;

        System.out.println("1. direnci giriniz : ");
        direnc1 = input.nextDouble();

        System.out.println("İkinci direnci giriniz : ");
        direnc2 = input.nextDouble();

        System.out.println("Seri bağlanti için s, paralel bağlanti için p giriniz :  ");
        baglanti = input.next().charAt(0);

        if(baglanti == 's'){
            eşdeğerdirenc = direnc1 + direnc2;
            System.out.println("seri bağlanti için eş değer direnç = " +eşdeğerdirenc);
        }
        else{
            eşdeğerdirenc = (1/direnc1) + (1/direnc2);
            System.out.println("paralel bağlanti için eş değer direnç = " +eşdeğerdirenc);
        }
    }
    
}
