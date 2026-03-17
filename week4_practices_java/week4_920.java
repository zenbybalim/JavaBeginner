package week4_practices_java;
import java.util.Scanner;
//-30 ve 30 aralığında x, y'den farklı olmak üzere 
// |x|+|y|<=30 eşitsizliğini sağlayan tamsayı çiftlerini ekrana yazan programın kodlarını yazınız.
public class week4_920 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int x, y;
        int sonuc=0;

        for(x=-30;x<=30;x++){
            for(y=-30;y<=30;y++){

                int mutlakX = (x < 0) ? (x * -1) : x;
                int mutlakY = (y < 0) ? (y * -1) : y;

                if(x != y){
                    if(x+y<=30){
                        sonuc = mutlakX + mutlakY;

                        sonuc = mutlakX + mutlakY;
                        System.out.println("|" + x + "| + |" + y + "| = " + sonuc);
                    }
                }
            }
        }

    }
}