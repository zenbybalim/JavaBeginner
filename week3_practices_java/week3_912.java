package week3_practices_java;
import java.util.Scanner;
//Yaz okulu ücretlerini hesaplayan bir programın yazılması istenmektedir. 
// Öncelikle kullanıcı dersi hangi fakülteden alacak onun bilgisi girilmelidir. 
// Teknoloji Fakültesi için TF, Mühendislik Fakültesi için MF, Eğitim Fakültesi için EF bilgisi girilmektedir. 
// Öğrenciye daha sonra hangi dersi alacağı sorulacaktır. 
// Öğrencinin Fizik 1 için FİZ1, Fizik 2 için FİZ2, Kalkülüs 1 için KLK1 ve Kalkülüs 2 için KLK2 bilgisi girişi yapmaktadır.
// Öğrenci sadece fakülte seçimi ve 1 ders seçimi yapacaktır. 
// Eğer dersi Teknoloji Fakültesinden alıyorsa ders saati başına 20TL, 
// eğer dersi Mühendislik Fakültesinden alıyorsa ders saati başına 22TL, 
// eğer dersi Eğitim Fakültesinden alıyorsa ders saati başına 19TL ödemesi gerekiyor. 
// Fizik 1 ve Fizik 2 derslerinin ders saati 3, Kalkülüs 1 ve Kalkülüs 2 derslerinin ders saati 4 tür.
// Klavyeden fakülte ve ders bilgisi girildikten sonra 
// ödemesi gereken yaz okulu ücretini ekrana yazdıran programın kodlarını yazınız.
public class week3_912 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        String fakülte;
        String d;
        Double dücret=0.0, tücret=0.0;

        System.out.println("Hangi Fakültedesiniz; Teknoloji Fakültesi=TF, Mühendislik Fakültesi=MF, Eğitim Fakültesi=EF");
        fakülte = input.next();

        switch(fakülte){
            case "tf":
            case "TF":
                dücret = 20.0;
                break;

            case "mf":
            case "MF":
                dücret = 22.0;
                break;

            case "ef":
            case "EF":
                dücret = 19.0;
                break;

                default:
                    System.out.println("fakülte için geçersiz değer girdiniz");
            }


        System.out.println("Hangi dersi alacaksiniz (sadece 1 ders seçiniz); f1, f2, k1 veya k2");
        d = input.next();


            if(fakülte.equals("tf") ||fakülte.equals("TF") || fakülte.equals("mf") || fakülte.equals("MF") || fakülte.equals("ef") || fakülte.equals("EF")){

        if(d.equals("f1") || d.equals("f2")){
            tücret = dücret*3;
            System.out.println("Toplam ödemeniz gereken ücret = " +tücret);
        }
        else if(d.equals("k1") || d.equals("k2")){
            tücret = dücret*4;
            System.out.println("Toplam ödemeniz gereken ücret = " +tücret);
        }

        }   
    }
}
