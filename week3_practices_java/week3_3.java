package week3_practices_java;
import java.util.Scanner;

public class week3_3 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        double a, b, c;
        double d1, d2, x1, x2;

        System.out.println("For ax^2 + bx + c, a =");
        a = input.nextDouble();

        System.out.println("b = ");
        b = input.nextDouble();

        System.out.println("c = ");
        c= input.nextDouble();

        d1 = (b*b - 4*a*c);
        
        System.out.println("Delta : " +d1);

        if(d1 > 0){

        d2 = Math.sqrt((b*b - 4*a*c));

        x1 = (- b + d2)/(2*a);
        x2 = (- b - d2)/(2*a);

        System.out.println("x1 =" +x1);
        System.out.println("x2= " +x2);
        }

        else if(d1 == 0){
            x1 = -b / (2 * a);
            System.out.println("Tek kök var = x1 = x2 = " +x1);
        }

        else{
            System.out.println("Kök yok");
        } 
    }    
}