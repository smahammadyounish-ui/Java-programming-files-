import java.util.Scanner;

class Student {
    int id;
    String name, department;
    int semester;
    int totalClasses, attendedClasses;
    String[] subjects;
    int[] marks;
    int subjectCount;
    boolean marksEntered = false;

    Student(int id, String name, String department,
            int semester, int subjectCount) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.semester = semester;
        this.subjectCount = subjectCount;
        subjects = new String[subjectCount];
        marks = new int[subjectCount];
    }

    void displayProfile() {
        System.out.println("\nStudent ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Semester: " + semester);
    }

    void recordAttendance(Scanner sc) {
        System.out.print("Enter total classes held: ");
        int total = sc.nextInt();

        System.out.print("Enter classes attended: ");
        int attended = sc.nextInt();

        if (total <= 0 || attended < 0 || attended > total) {
            System.out.println("Invalid attendance details.");
            return;
        }

        totalClasses = total;
        attendedClasses = attended;
        System.out.println("Attendance recorded successfully.");
    }

    void enterMarks(Scanner sc) {
        sc.nextLine();

        for (int i = 0; i < subjectCount; i++) {
            System.out.print("Enter subject name: ");
            subjects[i] = sc.nextLine();

            System.out.print("Enter marks out of 100: ");
            int mark = sc.nextInt();
            sc.nextLine();

            if (mark < 0 || mark > 100) {
                System.out.println("Invalid marks. Try again.");
                i--;
            } else {
                marks[i] = mark;
            }
        }

        marksEntered = true;
        System.out.println("Marks recorded successfully.");
    }

    double attendancePercentage() {
        if (totalClasses == 0) return 0;
        return attendedClasses * 100.0 / totalClasses;
    }

    int totalMarks() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }

    double percentage() {
        return totalMarks() / (double) subjectCount;
    }

    String calculateGrade() {
        double p = percentage();

        if (p >= 90) return "A+";
        if (p >= 80) return "A";
        if (p >= 70) return "B";
        if (p >= 60) return "C";
        if (p >= 50) return "D";
        return "F";
    }

    void generateReportCard() {
        if (!marksEntered) {
            System.out.println("Please enter marks first.");
            return;
        }

        System.out.println("\n====== STUDENT REPORT CARD ======");
        displayProfile();

        for (int i = 0; i < subjectCount; i++) {
            System.out.println(subjects[i] + ": "
                    + marks[i] + "/100");
        }

        System.out.println("Total Marks: " + totalMarks()
                + "/" + (subjectCount * 100));

        System.out.printf("Percentage: %.2f%%%n", percentage());
        System.out.println("Grade: " + calculateGrade());

        if (totalClasses > 0) {
            System.out.printf("Attendance: %.2f%%%n",
                    attendancePercentage());
        } else {
            System.out.println("Attendance: Not recorded");
        }

        System.out.println("=================================");
    }
}

public class UniversityStudentRecordSystem {
    static Scanner sc = new Scanner(System.in);
    static Student[] students = new Student[100];
    static int count = 0;

    static Student findStudent(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].id == id) {
                return students[i];
            }
        }
        return null;
    }

    static void addStudent() {
        if (count >= students.length) {
            System.out.println("Student limit reached.");
            return;
        }

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.nextLine();

        System.out.print("Enter Semester: ");
        int semester = sc.nextInt();

        System.out.print("Enter Number of Subjects: ");
        int subjectCount = sc.nextInt();

        if (semester < 1 || subjectCount < 1
                || subjectCount > 20) {
            System.out.println("Invalid semester or subject count.");
            return;
        }

        students[count++] = new Student(
                id, name, department, semester, subjectCount);

        System.out.println("Student added successfully.");
    }

    static Student selectStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        Student s = findStudent(id);

        if (s == null) {
            System.out.println("Student not found.");
        }

        return s;
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== UNIVERSITY STUDENT RECORD SYSTEM ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Student Profile");
            System.out.println("3. Record Attendance");
            System.out.println("4. Enter Marks");
            System.out.println("5. Generate Report Card");
            System.out.println("6. Display All Students");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2: {
                    Student s = selectStudent();
                    if (s != null) s.displayProfile();
                    break;
                }

                case 3: {
                    Student s = selectStudent();
                    if (s != null) s.recordAttendance(sc);
                    break;
                }

                case 4: {
                    Student s = selectStudent();
                    if (s != null) s.enterMarks(sc);
                    break;
                }

                case 5: {
                    Student s = selectStudent();
                    if (s != null) s.generateReportCard();
                    break;
                }

                case 6:
                    for (int i = 0; i < count; i++) {
                        students[i].displayProfile();
                    }
                    if (count == 0) {
                        System.out.println("No student records.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting application. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        sc.close();
    }
}
