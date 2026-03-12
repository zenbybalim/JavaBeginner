package week3_practices_java;
import java.util.Scanner;

//Bir yılın artık yıl olup olmadığını belirlemek için aşağıdaki adımları izleyin:
//1) Yıl 4 ile eşit bölünebilir ise, adım 2'e gidin. Aksi durumda, 5. adıma gidin.
//2) Yıl 100 ile eşit bölünebilir ise, adım 3'e gidin. Aksi durumda, 4. adıma gidin.
//3) Yıl 400 ile eşit bölünebilir ise, adım 4'e gidin. Aksi durumda, 5. adıma gidin.
//4) Yıl artık yıldır (366 gün vardır).
//5) Yıl artık yıl değildir (365 gün vardır).
//Buna göre klavyeden girilen bir yılın artık yıl olup olmadığını belirleyen kodları yazınız.

public class week3_914 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int yil;

        System.out.println("Yil giriniz = ");
        yil = input.nextInt();

        if(yil % 4 == 0){
            if(yil % 100 == 0){
                if(yil % 400 ==0){
                    System.out.println( "girdiğiniz yil artik yildir.");
                }
                else{
                    System.out.println("Girdiğiniz yil artik yil değildir.");
                }
            System.out.println("girdiğiniz yil artik yil değildir.");
            }
            else{
                System.out.println("girdiğiniz yil artik yildir.");
            }
        }
        else{
            System.out.println("Girdiğiniz yil artik yil değildir.");
        }    
        

    }
    
}
