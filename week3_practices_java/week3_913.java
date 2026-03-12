package week3_practices_java;
import java.util.Scanner;
// Bir otomobil kışın ısıdan dolayı %5 daha fazla yakıt tüketir. 
// Yazın ortalama olarak 1-50 km/h giderken 100 km de 10 litre, 
// 51-100 km/h giderken 100 km de 7,5 litre, 
// 101-150 km/h giderken 100 km de 9 litre yakıt tüketmektedir. 
// Buna göre mevsim (yaz için Y, kış için K girildiğini düşününüz), 
// ortalama hız bilgisi ve gideceği mesafe girildikten sonra 
// kaç litre yakıt tüketeceğini hesaplayıp ekrana yazdıran programın kodlarını yazınız.
public class week3_913 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        Double tüketim=0.0, km=0.0, orthiz=0.0;
        String mevsim;

        System.out.println("Kaç kilometre gittiniz, kilometre cinsinden yaziniz = ");
            km = input.nextDouble();

            System.out.println("ortalama kaç km/h ile gittiniz = ");
            orthiz = input.nextDouble();

            if(orthiz>0 && orthiz<50){
                tüketim =  km * 0.1;         
            }
            else if(orthiz>=50 && orthiz<100){
                tüketim =  km * (7.5/100);
            }
            else if(orthiz>=100 && orthiz<150){
                tüketim =  km * 0.09;
            }

        System.out.println("Hangi mevsimdesiniz, yaz için 'y'' harfini kiş için 'k harfini tuşlayiniz = ");
        mevsim = input.next();

        switch(mevsim){
            case "y":
            case "Y":
                tüketim = tüketim*1;
                break;
            
            case "k":
            case "K":
                tüketim = tüketim + tüketim*0.05;
                break;

            default:
                System.out.println("Mevsim için yanlis değer girdiniz");
            
        }
        if(mevsim.equals("y") || mevsim.equals("Y")|| mevsim.equals("k") || mevsim.equals("K")){
        
            System.out.println("Ortalama hiziniz = " +orthiz);
            System.out.println("Tükettiğiniz toplam yakit miktari = " +tüketim);
            
        }


    }
    
}
