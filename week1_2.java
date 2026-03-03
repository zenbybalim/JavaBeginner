import java.util.Scanner;

public class week1_2 {
    public static void main(String[] args) {
        int not1, not2, sum=0;
        double avarage;

        Scanner input = new Scanner(System.in);

        System.out.println("Enter your first note :");
        not1 = input.nextInt();

        System.out.println("Enter your second note :");
        not2 = input.nextInt();

        sum = not1 + not2;
        avarage = sum/2.0;

        System.out.println("Your avarage is :" +avarage);
    }
}
