import java.util.Scanner;
public class week1_4 {
    public static void main(String[] args) {
        
    Scanner input = new Scanner(System.in);

    double kenar1, kenar2, hipotenüs;

    System.out.println("1. kenari giriniz: ");
    kenar1 = input.nextDouble();

    System.out.println("2. kenari giriniz:");
    kenar2 = input.nextDouble();

    hipotenüs = Math.sqrt((kenar1*kenar1) + (kenar2*kenar2))
;

    System.out.println("Hipotenüs =" +hipotenüs);
    }
}
