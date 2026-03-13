package week4_practices_java;
//2015 yılı itibarı ile ülke nüfusu 77 milyondur. Yıllık nüfus artış oranı
//%1.3 tür. Sonraki 10 yılda ülke nüfusunu yıllara göre programın
//kodlarını yazınız.
public class week4_911 {
    public static void main(String[] args){
        double nufus=77.0;
        double artmik;

        for(int i=0; i<10; i++){
            artmik = nufus*0.013;
            nufus = nufus + artmik;
            System.out.println((i+1) +". yil icin nufus = " +nufus);
        }
    }
    
}
