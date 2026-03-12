package week1_practices_java;
import java.util.Scanner;

public class week1_3 {
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    double exam1, exam2, sum=0, average;

    System.out.println("Enter your first exam note :");
    exam1 = input.nextDouble();

    System.out.println("Enter your second exam note :");
    exam2 = input.nextDouble();

    sum = exam1*0.4 + exam2*0.6;
    average = sum;

    System.out.println("Your average is :" +average);
}    
}
