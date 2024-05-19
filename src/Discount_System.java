import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Discount_System {
    public static void main(String... shop) {
        float total_price;

        LocalDate FirstDate = LocalDate.of(2024 , 01, 31);

        //LocalDate PresentDate = LocalDate.of(2024, 03, 20); //(For testing the program that this is working Properly or not)
        LocalDate PresentDate = LocalDate.now();

        Period difference = Period.between(FirstDate,PresentDate);
        int day = difference.getDays();
        int month = difference.getMonths();
        int year = difference.getYears();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Total Price: ");
        total_price = sc.nextFloat();

        if(year == 0) {
            if (month == 0) {
                if (day == 0) {
                    total_price -= 100;
                    System.out.println("Discounted Price: Rs " + total_price);
                } else if (day > 0 && day <= 19) {
                    total_price -= 50;
                    System.out.println("Discounted Price: Rs " + total_price);
                } else if (day > 19 && day < 30) {
                    total_price -= 10;
                    System.out.println("Discounted Price: Rs " + total_price);
                }
            }
        }
        if (year == 0 || month == 0){
            System.out.println("Sorry Discount Period is Over!!");
        }
    }
}