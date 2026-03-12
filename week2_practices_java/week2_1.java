package week2_practices_java;
import java.util.Scanner;

public class week2_1 {
public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        double saat, maas1, maas2, sigorta, vergi;

        System.out.println("Çalişan kaç saat çalisti");
        saat = input.nextDouble();

        maas1 = saat*100;
        sigorta = maas1*0.15;
        vergi = maas1*0.10;

        maas2 = maas1 - (sigorta + vergi);

        System.out.println("Brüt maas=" +maas1);
        System.out.println("Sigorta=" +sigorta);
        System.out.println("vergi=" +vergi);
        System.out.println("net maas=" +maas2);

}
}
