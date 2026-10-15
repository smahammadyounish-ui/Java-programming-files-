import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Book {
    int id;
    String title;
    String author;
    int totalCopies;
    int availableCopies;

    Book(int id, String title, String author, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.totalCopies = copies;
        this.availableCopies = copies;
    }

    void display() {
        System.out.println("Book ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Total Copies: " + totalCopies);
        System.out.println("Available Copies: " + availableCopies);
        System.out.println("--------------------------");
    }
}

class Member {
    int id;
    String name;

    Member(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class IssueRecord {
    int bookId;
    int memberId;
    LocalDate issueDate;
    LocalDate dueDate;

    IssueRecord(int bookId, int memberId, LocalDate issueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(14);
    }

    long calculateFine() {
        long overdueDays = ChronoUnit.DAYS.between(
                dueDate, LocalDate.now());

        return Math.max(0, overdueDays) * 5;
    }

    void display() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Member ID: " + memberId);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Current Fine: Rs. " + calculateFine());
        System.out.println("--------------------------");
    }
}

public class LibraryAutomationSystem {
    static Scanner sc = new Scanner(System.in);

    static Book[] books = new Book[100];
    static Member[] members = new Member[100];
    static IssueRecord[] records = new IssueRecord[300];

    static int bookCount = 0;
    static int memberCount = 0;
    static int recordCount = 0;

    static int nextBookId = 1;
    static int nextMemberId = 1;

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

    static Book findBook(int id) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].id == id) {
                return books[i];
            }
        }
        return null;
    }

    static Member findMember(int id) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].id == id) {
                return members[i];
            }
        }
        return null;
    }

    static IssueRecord findIssue(int bookId, int memberId) {
        for (int i = 0; i < recordCount; i++) {
            if (records[i] != null
                    && records[i].bookId == bookId
                    && records[i].memberId == memberId) {
                return records[i];
            }
        }
        return null;
    }

    // 1. Add a book
    static void addBook() {
        if (bookCount >= books.length) {
            System.out.println("Book inventory is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        System.out.print("Enter author name: ");
        String author = sc.nextLine();

        int copies = readInt("Enter number of copies: ");

        if (copies <= 0) {
            System.out.println("Copies must be greater than zero.");
            return;
        }

        books[bookCount] = new Book(
                nextBookId++, title, author, copies);

        System.out.println("Book added successfully.");
        System.out.println("Book ID: " + books[bookCount].id);
        bookCount++;
    }

    // 2. Register a member
    static void registerMember() {
        if (memberCount >= members.length) {
            System.out.println("Member storage is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter member name: ");
        String name = sc.nextLine();

        members[memberCount] = new Member(nextMemberId++, name);

        System.out.println("Member registered successfully.");
        System.out.println("Member ID: " + members[memberCount].id);
        memberCount++;
    }

    // 3. Search for a book
    static void searchBook() {
        sc.nextLine();

        System.out.print("Enter book title to search: ");
        String query = sc.nextLine();

        boolean found = false;

        for (int i = 0; i < bookCount; i++) {
            if (books[i].title.toLowerCase()
                    .contains(query.toLowerCase())) {
                books[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    // 4. Display inventory
    static void displayBooks() {
        if (bookCount == 0) {
            System.out.println("No books in the library.");
            return;
        }

        for (int i = 0; i < bookCount; i++) {
            books[i].display();
        }
    }

    // 5. Issue a book
    static void issueBook() {
        if (recordCount >= records.length) {
            System.out.println("Issue record storage is full.");
            return;
        }

        int bookId = readInt("Enter book ID: ");
        int memberId = readInt("Enter member ID: ");

        Book book = findBook(bookId);
        Member member = findMember(memberId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (member == null) {
            System.out.println("Member not found. Register first.");
            return;
        }

        if (book.availableCopies <= 0) {
            System.out.println("Book is currently unavailable.");
            return;
        }

        if (findIssue(bookId, memberId) != null) {
            System.out.println(
                    "This member already has this book issued.");
            return;
        }

        records[recordCount++] = new IssueRecord(
                bookId, memberId, LocalDate.now());

        book.availableCopies--;

        System.out.println("Book issued successfully.");
        System.out.println("Issue Date: " + LocalDate.now());
        System.out.println("Due Date: " + LocalDate.now().plusDays(14));
    }

    // 6. Return a book and calculate the fine
    static void returnBook() {
        int bookId = readInt("Enter book ID: ");
        int memberId = readInt("Enter member ID: ");

        Book book = findBook(bookId);
        IssueRecord issue = findIssue(bookId, memberId);

        if (book == null || issue == null) {
            System.out.println("Active issue record not found.");
            return;
        }

        long fine = issue.calculateFine();

        System.out.println("Book returned successfully.");
        System.out.println("Due Date: " + issue.dueDate);
        System.out.println("Return Date: " + LocalDate.now());
        System.out.println("Overdue Fine: Rs. " + fine);

        book.availableCopies++;

        for (int i = 0; i < recordCount; i++) {
            if (records[i] == issue) {
                for (int j = i; j < recordCount - 1; j++) {
                    records[j] = records[j + 1];
                }
                records[--recordCount] = null;
                break;
            }
        }
    }

    // 7. Display active issue records
    static void displayIssues() {
        if (recordCount == 0) {
            System.out.println("No active issue records.");
            return;
        }

        for (int i = 0; i < recordCount; i++) {
            records[i].display();
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== LIBRARY AUTOMATION SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Search Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Active Issues");
            System.out.println("8. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    registerMember();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    displayBooks();
                    break;
                case 5:
                    issueBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    displayIssues();
                    break;
                case 8:
                    System.out.println("Thank you for using the library system.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        sc.close();
    }
}
