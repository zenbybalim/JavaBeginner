import java.util.Scanner;

public class week3_1 {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        Double number1, number2, number3;
        char operation;

        System.out.println("First Number : ");
        number1 = input.nextDouble();

        System.out.println("Seconde Number : ");
        number2 = input.nextDouble();

        System.out.println("Which operation you want to do? ");
        operation = input.next().charAt(0);

        {
        if( operation == '+')
            number3 = number1+number2;

        else if( operation == '-')
            number3 = number1-number2;

        else if( operation == '/')
            number3 = number1/number2;

        else 
            number3 = number1*number2;
        
    }

    System.out.println("result : " +number3);

    }
}
