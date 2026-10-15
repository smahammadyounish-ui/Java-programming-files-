import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

public class BirthdayReminderSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Accept person's details
        System.out.print("Enter person's name: ");
        String name = sc.nextLine();

        System.out.print("Enter birth year: ");
        int year = sc.nextInt();

        System.out.print("Enter birth month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter birth day: ");
        int day = sc.nextInt();

        try {
            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate today = LocalDate.now();

            // Validate birth date
            if (birthDate.isAfter(today)) {
                System.out.println("Birth date cannot be in the future.");
                sc.close();
                return;
            }

            // Calculate current age
            int age = Period.between(birthDate, today).getYears();

            // Determine next birthday
            MonthDay birthday = MonthDay.from(birthDate);
            LocalDate nextBirthday = birthday.atYear(today.getYear());

            if (nextBirthday.isBefore(today)) {
                nextBirthday = birthday.atYear(today.getYear() + 1);
            }

            // Calculate remaining days
            long daysRemaining = ChronoUnit.DAYS.between(
                    today, nextBirthday);

            // Display results
            System.out.println("\n===== BIRTHDAY REMINDER =====");
            System.out.println("Name: " + name);
            System.out.println("Date of Birth: " + birthDate);
            System.out.println("Current Age: " + age);

            if (daysRemaining == 0) {
                System.out.println("Happy Birthday, " + name + "!");
            } else {
                System.out.println("Next Birthday: " + nextBirthday);
                System.out.println("Days Remaining: " + daysRemaining);
            }

            System.out.println("=============================");

        } catch (java.time.DateTimeException e) {
            System.out.println("Invalid date entered. Please try again.");
        }

        sc.close();
    }
}
