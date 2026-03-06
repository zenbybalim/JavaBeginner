import java.util.Scanner;

public class week3_2 {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);

        double exam;

        System.out.println("Your exam note : ");
        exam = input.nextDouble();
        
        {
            if( exam >= 0 && exam < 25)
                System.out.println("F");

            else if(exam >= 25 && exam < 45)
                System.out.println("E");

            else if(exam >= 45 && exam < 55)
                System.out.println("D");

            else if(exam >= 55 && exam < 70)
                System.out.println("C");

            else if(exam >=70 && exam < 85)
                System.out.println("B");
            else
                System.out.println("A"); 
            
            }
    }
}
