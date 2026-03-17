package week4_practices_java;
import java.util.Scanner;
//Klavyeden girilen 10 tane sayıdan negatiflerin sayısını
// pozitiflerin toplamını bulan ve her ikisini de ekrana yazan programın kodlarını yazınız
public class week4_915 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        int i=1, sayi=0;
        int nsayac=0, ptoplam=0;

        for(i=1; i<=10;i++){
        System.out.println("Sayi giriniz = ");
        sayi = input.nextInt();


        if(sayi<0){
            nsayac++;
        }

        else{
            ptoplam = ptoplam + sayi;
        } 

        
       }
       System.out.println(nsayac +" tane negait sayi girdiniz ve giridiginiz pozitif sayilatin toplami = " +ptoplam);
    }
  }
