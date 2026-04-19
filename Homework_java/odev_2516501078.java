package Homework_java;

import java.util.Random;
import java.util.Scanner;

public class odev_2516501078 {

    public static String kelimesecimi(String[] ilkkisim, String onkelime) {

        Random rnd = new Random();
        int i = rnd.nextInt(10);

        while (ilkkisim[i].equals(onkelime)) {
            i = rnd.nextInt(10);
        }

        return ilkkisim[i];
    }

    public static String golusturma(String kelime) {

        int harfsayi = kelime.length();
        char[] golusumu = new char[harfsayi];

        golusumu[0] = Character.toUpperCase(kelime.charAt(0));

        if (harfsayi == 6 || harfsayi == 7) {

            for (int a = 1; a < harfsayi; a++) {
                golusumu[a] = '*';
            }

        } else if (harfsayi == 8 || harfsayi == 9) {

            for (int a = 1; a < harfsayi - 1; a++) {
                golusumu[a] = '*';
            }

            golusumu[harfsayi - 1] = Character.toUpperCase(kelime.charAt(harfsayi - 1));
        }

        return new String(golusumu);
    }

    public static int harfkontrol(String kelime, char harf) {

        for (int i = 0; i < kelime.length(); i++) {

            if (Character.toLowerCase(kelime.charAt(i)) == Character.toLowerCase(harf)) {
                return 1;
            }
        }

        return 0;
    }

    public static String ikincikisim(String kelime, String gosterim, char harf) {

        int harfsayi = kelime.length();
        char[] imdat = new char[harfsayi];

        for (int b = 0; b < harfsayi; b++) {

            if (Character.toLowerCase(kelime.charAt(b)) == Character.toLowerCase(harf)) {
                imdat[b] = Character.toUpperCase(harf);
            } else {
                imdat[b] = gosterim.charAt(b);
            }
        }

        return new String(imdat);
    }

    public static int yildizkontrolzamani(String gosterim) {

        for (int i = 0; i < gosterim.length(); i++) {
            if (gosterim.charAt(i) == '*') {
                return 1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] kelimeler = {
                "maalesef", "sezgisel", "tutarli", "kelebek",
                "evrensel", "ayrica", "oysaki",
                "herkes", "bilgisayar", "muhendislik"
        };

        int oyun = 0;
        String onkelime = "";
        char cevap = 'e';

        while ((cevap == 'e' || cevap == 'E') && oyun < 5) {

            String kelime = kelimesecimi(kelimeler, onkelime);
            onkelime = kelime;
            oyun++;

            String gosterim = golusturma(kelime);

            System.out.println("Kelime " + kelime.length() + " harfli");
            System.out.println(gosterim);

            int kontrol = 1;

            for (int hak = 1; hak <= 15 && kontrol == 1; hak++) {

                System.out.print("Harf giriniz: ");
                char harf = sc.next().charAt(0);

                if (harfkontrol(kelime, harf) == 1) {

                    gosterim = ikincikisim(kelime, gosterim, harf);
                    System.out.println(gosterim);

                } else {

                    System.out.println("Harf yok");
                }

                kontrol = yildizkontrolzamani(gosterim);

                if (kontrol == 0) {
                    System.out.println("Tebrikler");
                    break;
                }

                if (hak == 15 && kontrol == 1) {
                    System.out.println("Bilemediniz: " + kelime.toUpperCase());
                }
            }

            if (oyun < 5) {
                System.out.print("Tekrar oynamak istiyor musunuz (e/h): ");
                cevap = sc.next().charAt(0);
            } else {
                System.out.println("5 oyun hakkini kullandiniz");
            }
        }
    }
}