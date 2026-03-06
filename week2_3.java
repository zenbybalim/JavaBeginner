import java.util.Scanner;

public class week2_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double exam1, exam2, average;

        System.out.println("enter your first exam note");
        exam1 = input.nextDouble();

        System.out.println("Enter your second exam note");
        exam2 = input.nextDouble();

        average =(exam1 + exam2)/2.0;

        System.out.println("Average : " +average);

        if(average >= 70)
            System.out.println("successful");
        else
            System.out.println("unsuccessful");
    }
    
}
