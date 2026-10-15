import java.util.Scanner;

class Student {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    void display(int number) {
        System.out.println("\nQ" + number + ". " + question);

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}

class Result {
    int studentId;
    int score;
    int totalMarks;
    double percentage;

    Result(int studentId, int score, int totalMarks) {
        this.studentId = studentId;
        this.score = score;
        this.totalMarks = totalMarks;
        this.percentage = totalMarks == 0
                ? 0 : score * 100.0 / totalMarks;
    }

    void display(Student student) {
        System.out.println("\n===== EXAMINATION RESULT =====");
        System.out.println("Student ID: " + student.id);
        System.out.println("Student Name: " + student.name);
        System.out.println("Marks Obtained: " + score
                + " / " + totalMarks);
        System.out.printf("Percentage: %.2f%%%n", percentage);

        if (percentage >= 40) {
            System.out.println("Status: PASS");
        } else {
            System.out.println("Status: FAIL");
        }

        System.out.println("==============================");
    }
}

public class OnlineExaminationSystem {

    static Scanner sc = new Scanner(System.in);

    static Student[] students = new Student[100];
    static Question[] questions = new Question[100];
    static Result[] results = new Result[200];

    static int studentCount = 0;
    static int questionCount = 0;
    static int resultCount = 0;

    static int nextStudentId = 1;

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
        for (int i = 0; i < studentCount; i++) {
            if (students[i].id == id) {
                return students[i];
            }
        }
        return null;
    }

    static Result findResult(int studentId) {
        for (int i = 0; i < resultCount; i++) {
            if (results[i].studentId == studentId) {
                return results[i];
            }
        }
        return null;
    }

    // 1. Register a student
    static void registerStudent() {
        if (studentCount >= students.length) {
            System.out.println("Student storage is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        students[studentCount] =
                new Student(nextStudentId++, name);

        System.out.println("Student registered successfully.");
        System.out.println("Student ID: "
                + students[studentCount].id);

        studentCount++;
    }

    // 2. Create an MCQ
    static void createQuestion() {
        if (questionCount >= questions.length) {
            System.out.println("Question storage is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter question: ");
        String question = sc.nextLine();

        String[] options = new String[4];

        for (int i = 0; i < 4; i++) {
            System.out.print("Enter option " + (i + 1) + ": ");
            options[i] = sc.nextLine();
        }

        int answer;

        do {
            answer = readInt("Enter correct option (1-4): ");

            if (answer < 1 || answer > 4) {
                System.out.println("Choose an option from 1 to 4.");
            }
        } while (answer < 1 || answer > 4);

        questions[questionCount++] =
                new Question(question, options, answer);

        System.out.println("Question added successfully.");
    }

    // 3. Display questions without answers
    static void displayQuiz() {
        if (questionCount == 0) {
            System.out.println("No questions available.");
            return;
        }

        for (int i = 0; i < questionCount; i++) {
            questions[i].display(i + 1);
        }
    }

    // 4. Conduct examination and evaluate answers
    static void takeExam() {
        if (questionCount == 0) {
            System.out.println("No examination questions available.");
            return;
        }

        if (resultCount >= results.length) {
            System.out.println("Result storage is full.");
            return;
        }

        int id = readInt("Enter student ID: ");
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (findResult(id) != null) {
            System.out.println(
                    "This student has already completed the exam.");
            return;
        }

        int score = 0;

        System.out.println("\n===== EXAMINATION STARTED =====");
        System.out.println("Each correct answer carries 1 mark.");

        for (int i = 0; i < questionCount; i++) {
            questions[i].display(i + 1);

            int answer;

            do {
                answer = readInt("Enter answer (1-4): ");

                if (answer < 1 || answer > 4) {
                    System.out.println("Enter a number from 1 to 4.");
                }
            } while (answer < 1 || answer > 4);

            if (answer == questions[i].correctAnswer) {
                score++;
            }
        }

        results[resultCount++] =
                new Result(id, score, questionCount);

        System.out.println("\nExamination submitted successfully.");
        results[resultCount - 1].display(student);
    }

    // 5. Display an individual result
    static void viewResult() {
        int id = readInt("Enter student ID: ");

        Student student = findStudent(id);
        Result result = findResult(id);

        if (student == null) {
            System.out.println("Student not found.");
        } else if (result == null) {
            System.out.println("Result not available yet.");
        } else {
            result.display(student);
        }
    }

    // 6. Display all available results
    static void displayAllResults() {
        if (resultCount == 0) {
            System.out.println("No examination results available.");
            return;
        }

        System.out.println("\n===== ALL EXAMINATION RESULTS =====");

        for (int i = 0; i < resultCount; i++) {
            Student student = findStudent(results[i].studentId);

            if (student != null) {
                results[i].display(student);
            }
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== ONLINE EXAMINATION SYSTEM ===");
            System.out.println("1. Register Student");
            System.out.println("2. Create Quiz Question");
            System.out.println("3. Display Quiz Questions");
            System.out.println("4. Take Examination");
            System.out.println("5. View Student Result");
            System.out.println("6. Display All Results");
            System.out.println("7. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerStudent();
                    break;

                case 2:
                    createQuestion();
                    break;

                case 3:
                    displayQuiz();
                    break;

                case 4:
                    takeExam();
                    break;

                case 5:
                    viewResult();
                    break;

                case 6:
                    displayAllResults();
                    break;

                case 7:
                    System.out.println(
                            "Thank you for using the examination system.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }
}
