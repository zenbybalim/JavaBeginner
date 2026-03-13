package week3_practices_java;
import java.util.Scanner;
//Aşağıdaki ölçütleri temel alarak öğrencinin bir kursa kabul edilmeye uygun olup olmadığını belirleyen programı yazınız.
// Şartlar şu şekildedir;
//Öğrencinin Matematik Notu >=65 olmalı,
//Öğrencinin Fizik Notu >=55 olmalı,
//Öğrencinin Kimya Notu >=50 olmalı,
//Her üç dersten notları toplam >=190 olmalı veya Matematik ve Fizik Toplamı >=140 olmalı
//Bu işlem gerçekleştiren programın kodlarını yazınız.
public class week3_915 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        Double mat, fiz, kim, fizvemat=0.0, toplam=0.0;

        System.out.println("Matematik notunuzu giriniz : ");
        mat = input.nextDouble();

        System.out.println("Fizik notunuzu giriniz : ");
        fiz = input.nextDouble();

        System.out.println("Kimya notunuzu giriniz : ");
        kim = input.nextDouble();

        toplam = mat + fiz + kim;
        fizvemat = mat + fiz;

        if(mat >= 65){
            if(fiz >= 55){
                if(kim >= 50){
                    if(fizvemat >=140 || toplam>=190){
                            System.out.println("Ogrenci kursa katilmaya uygun.");
                    }
                    else{
                            System.out.println("Ogrenci kursa katilmaya uygun degil.");
                        }
                }
                else{
                            System.out.println("Ogrenci kursa katilmaya uygun degil.");
                        }
            }
            else{
                            System.out.println("Ogrenci kursa katilmaya uygun degil.");
                        }
        }
        else{
                            System.out.println("Ogrenci kursa katilmaya uygun degil.");
                        }
    }
    
}
