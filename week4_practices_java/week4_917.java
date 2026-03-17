package week4_practices_java;
import java.util.Scanner;
//Meteoroloji merkezi için bir program tasarlanması istenilmiştir. Programın çalışma şekli ise şöyle olmalıdır:
//a. İlk önce hangi ay için sıcaklık bilgisi girileceği kullanıcıya sorulacaktır.
// (Kullanıcının Ocak, Şubat, Mart, Nisan, Mayıs, Haziran, Temmuz, Ağustos,
//  Eylül, Ekim, Kasım ve Aralık bilgisinden birisini girdiğini varsayınız.)
//b. Girilen ay bilgisine uygun olarak o ayda kaç tane gün var ise 
// kullanıcıdan gün sayısı kadar sıcaklık bilgisi girilmesi istenilecektir
// (Şubat ayı için gün sayısını 28 alınız, diğer ayların gün sayısını 30 alınız).
//c. Sıcaklık veri girişi bittikten sonra o ayın sıcaklık ortalaması bilgisi ekrana yazdırılacaktır. 
// Bu işlemden sonra program sonlanacaktı
//Örnek Çıktı: Şubat Ayına ait Ortalama Sıcaklık=15,6 derecedir.

public class week4_917 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        double sayi, gün=0, sort=0, toplam=0;
        int i=0;
        String ay;

        System.out.println("Hangi ay icin ortalama sicaklik hesabi yapmak istiyorsunuz");
        ay = input.next();

        if(ay.equals("Subat")|| ay.equals("subat")){
            gün = 28;
        }
        else{
            gün =30;
        }

        for(i=1;i<=gün;i++){
            System.out.println(i +".gün icin sicaklik bilgisi giriniz = ");
            sayi = input.nextDouble();
            toplam = toplam + sayi;
            
        }
        sort = toplam/gün;
        System.out.println(ay + " ayina ait sicaklik ortalamasi = " +sort);

    }
    
}
