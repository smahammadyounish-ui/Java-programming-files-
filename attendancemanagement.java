import java.util.Scanner;
import java.time.LocalDate;

class Student {
    int id;
    String name;
    int presentDays;
    int absentDays;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
        presentDays = 0;
        absentDays = 0;
    }

    int totalDays() {
        return presentDays + absentDays;
    }

    double attendancePercentage() {
        if (totalDays() == 0) {
            return 0.0;
        }

        return presentDays * 100.0 / totalDays();
    }

    void displayReport() {
        System.out.println("\nStudent ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Present Days: " + presentDays);
        System.out.println("Absent Days: " + absentDays);
        System.out.println("Total Classes: " + totalDays());
        System.out.printf("Attendance: %.2f%%%n",
                attendancePercentage());

        if (totalDays() == 0) {
            System.out.println("Status: Attendance not recorded");
        } else if (attendancePercentage() < 75.0) {
            System.out.println("Status: LOW ATTENDANCE WARNING");
        } else {
            System.out.println("Status: Attendance satisfactory");
        }
    }
}

public class AttendanceManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static Student[] students = new Student[100];
    static int count = 0;

    static int readInt(String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextInt()) {
                return sc.nextInt();
            }

            System.out.println("Invalid input. Enter a whole number.");
            sc.next();
        }
    }

    static Student findStudent(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].id == id) {
                return students[i];
            }
        }

        return null;
    }

    // Register a student
    static void addStudent() {
        if (count >= students.length) {
            System.out.println("Student capacity reached.");
            return;
        }

        int id = readInt("Enter student ID: ");

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        students[count++] = new Student(id, name);

        System.out.println("Student registered successfully.");
    }

    // Record attendance for one student
    static void markAttendance() {
        int id = readInt("Enter student ID: ");
        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("1. Present");
        System.out.println("2. Absent");

        int choice = readInt("Enter attendance status: ");

        if (choice == 1) {
            s.presentDays++;
            System.out.println("Attendance marked Present.");
        } else if (choice == 2) {
            s.absentDays++;
            System.out.println("Attendance marked Absent.");
        } else {
            System.out.println("Invalid attendance choice.");
        }
    }

    // Display individual report
    static void individualReport() {
        int id = readInt("Enter student ID: ");
        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
        } else {
            s.displayReport();
        }
    }

    // Display reports for all students
    static void overallReport() {
        if (count == 0) {
            System.out.println("No student records available.");
            return;
        }

        System.out.println("\n===== ATTENDANCE REPORT =====");

        for (int i = 0; i < count; i++) {
            students[i].displayReport();
            System.out.println("-----------------------------");
        }
    }

    // Identify students below the threshold
    static void lowAttendanceReport() {
        boolean found = false;

        System.out.println("\n===== LOW ATTENDANCE STUDENTS =====");

        for (int i = 0; i < count; i++) {
            Student s = students[i];

            if (s.totalDays() > 0
                    && s.attendancePercentage() < 75.0) {
                s.displayReport();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No students currently meet the low-attendance criteria.");
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== ATTENDANCE MANAGEMENT SYSTEM ===");
            System.out.println("1. Register Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Individual Attendance Report");
            System.out.println("4. Overall Attendance Report");
            System.out.println("5. Low Attendance Report");
            System.out.println("6. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    markAttendance();
                    break;

                case 3:
                    individualReport();
                    break;

                case 4:
                    overallReport();
                    break;

                case 5:
                    lowAttendanceReport();
                    break;

                case 6:
                    System.out.println(
                            "Thank you for using the system.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
