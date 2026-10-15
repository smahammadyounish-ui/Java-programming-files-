
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class Customer {
    int customerId;
    String name;
    String phone;
    String email;

    Customer(int customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    void display() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
    }
}

class TravelPackage {
    int packageId;
    String destination;
    String packageName;
    double price;
    int availableSeats;
    LocalDate travelDate;

    TravelPackage(int packageId, String packageName,
                  String destination, double price,
                  int availableSeats, LocalDate travelDate) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.destination = destination;
        this.price = price;
        this.availableSeats = availableSeats;
        this.travelDate = travelDate;
    }

    void display() {
        System.out.println("\nPackage ID: " + packageId);
        System.out.println("Package: " + packageName);
        System.out.println("Destination: " + destination);
        System.out.printf("Price per person: Rs. %.2f%n", price);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Travel Date: " + travelDate);
    }
}

class Booking {
    int bookingId;
    Customer customer;
    TravelPackage travelPackage;
    int travelers;
    double totalAmount;
    double amountPaid;
    String paymentStatus;

    Booking(int bookingId, Customer customer,
            TravelPackage travelPackage, int travelers) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.travelPackage = travelPackage;
        this.travelers = travelers;
        this.totalAmount = travelPackage.price * travelers;
        this.amountPaid = 0;
        this.paymentStatus = "Pending";
    }

    void updatePayment(double amount) {
        amountPaid += amount;

        if (amountPaid >= totalAmount) {
            paymentStatus = "Paid";
        } else {
            paymentStatus = "Pending";
        }
    }

    void display() {
        System.out.println("\n======= BOOKING DETAILS =======");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Customer: " + customer.name);
        System.out.println("Destination: "
                + travelPackage.destination);
        System.out.println("Package: " + travelPackage.packageName);
        System.out.println("Travel Date: " + travelPackage.travelDate);
        System.out.println("Number of Travelers: " + travelers);
        System.out.printf("Total Amount: Rs. %.2f%n", totalAmount);
        System.out.printf("Amount Paid: Rs. %.2f%n", amountPaid);
        System.out.printf("Balance Due: Rs. %.2f%n",
                totalAmount - amountPaid);
        System.out.println("Payment Status: " + paymentStatus);
        System.out.println("===============================");
    }
}

public class TravelBookingSystem {
    static Scanner sc = new Scanner(System.in);

    static Customer[] customers = new Customer[100];
    static TravelPackage[] packages = new TravelPackage[20];
    static Booking[] bookings = new Booking[200];

    static int customerCount = 0;
    static int packageCount = 0;
    static int bookingCount = 0;

    static int nextCustomerId = 1;
    static int nextBookingId = 1;

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

    static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            }

            System.out.println("Invalid amount. Try again.");
            sc.next();
        }
    }

    static Customer findCustomer(int id) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].customerId == id) {
                return customers[i];
            }
        }
        return null;
    }

    static TravelPackage findPackage(int id) {
        for (int i = 0; i < packageCount; i++) {
            if (packages[i].packageId == id) {
                return packages[i];
            }
        }
        return null;
    }

    static Booking findBooking(int id) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].bookingId == id) {
                return bookings[i];
            }
        }
        return null;
    }

    // Initialize sample travel packages
    static void initializePackages() {
        packages[packageCount++] = new TravelPackage(
                1, "Beach Holiday", "Goa",
                12000, 20, LocalDate.of(2027, 1, 15));

        packages[packageCount++] = new TravelPackage(
                2, "Mountain Adventure", "Manali",
                18000, 15, LocalDate.of(2027, 2, 10));

        packages[packageCount++] = new TravelPackage(
                3, "Heritage Tour", "Jaipur",
                10000, 25, LocalDate.of(2027, 3, 5));
    }

    // 1. Register customer
    static void registerCustomer() {
        if (customerCount >= customers.length) {
            System.out.println("Customer storage is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        customers[customerCount] = new Customer(
                nextCustomerId++, name, phone, email);

        System.out.println("Customer registered successfully.");
        System.out.println("Customer ID: "
                + customers[customerCount].customerId);

        customerCount++;
    }

    // 2. Display available packages
    static void displayPackages() {
        System.out.println("\n===== AVAILABLE PACKAGES =====");

        boolean found = false;

        for (int i = 0; i < packageCount; i++) {
            if (packages[i].availableSeats > 0
                    && !packages[i].travelDate.isBefore(LocalDate.now())) {
                packages[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No upcoming packages available.");
        }
    }

    // 3. Book a package
    static void bookPackage() {
        if (bookingCount >= bookings.length) {
            System.out.println("Booking storage is full.");
            return;
        }

        int customerId = readInt("Enter customer ID: ");
        Customer customer = findCustomer(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Register first.");
            return;
        }

        displayPackages();

        int packageId = readInt("Enter package ID: ");
        TravelPackage travelPackage = findPackage(packageId);

        if (travelPackage == null
                || travelPackage.availableSeats <= 0
                || travelPackage.travelDate.isBefore(LocalDate.now())) {
            System.out.println("Package is unavailable.");
            return;
        }

        int travelers = readInt("Enter number of travelers: ");

        if (travelers <= 0
                || travelers > travelPackage.availableSeats) {
            System.out.println("Invalid number of travelers.");
            return;
        }

        Booking booking = new Booking(
                nextBookingId++, customer, travelPackage, travelers);

        bookings[bookingCount++] = booking;
        travelPackage.availableSeats -= travelers;

        System.out.println("Booking successful!");
        booking.display();
    }

    // 4. Record a payment
    static void recordPayment() {
        int id = readInt("Enter booking ID: ");
        Booking booking = findBooking(id);

        if (booking == null) {
            System.out.println("Booking not found.");
            return;
        }

        double remaining = booking.totalAmount - booking.amountPaid;

        if (remaining <= 0) {
            System.out.println("Booking is already fully paid.");
            return;
        }

        System.out.printf("Outstanding amount: Rs. %.2f%n", remaining);

        double amount = readDouble("Enter payment amount: ");

        if (amount <= 0 || amount > remaining) {
            System.out.println(
                    "Payment must be positive and cannot exceed the balance.");
            return;
        }

        booking.updatePayment(amount);

        System.out.println("Payment recorded successfully.");
        booking.display();
    }

    // 5. View booking details
    static void viewBooking() {
        int id = readInt("Enter booking ID: ");
        Booking booking = findBooking(id);

        if (booking == null) {
            System.out.println("Booking not found.");
        } else {
            booking.display();
        }
    }

    // 6. Display all bookings
    static void displayAllBookings() {
        if (bookingCount == 0) {
            System.out.println("No bookings available.");
            return;
        }

        for (int i = 0; i < bookingCount; i++) {
            bookings[i].display();
        }
    }

    public static void main(String[] args) {
        initializePackages();

        int choice;

        do {
            System.out.println("\n===== TRAVEL BOOKING SYSTEM =====");
            System.out.println("1. Register Customer");
            System.out.println("2. View Available Packages");
            System.out.println("3. Book Travel Package");
            System.out.println("4. Record Payment");
            System.out.println("5. View Booking Details");
            System.out.println("6. Display All Bookings");
            System.out.println("7. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    displayPackages();
                    break;
                case 3:
                    bookPackage();
                    break;
                case 4:
                    recordPayment();
                    break;
                case 5:
                    viewBooking();
                    break;
                case 6:
                    displayAllBookings();
                    break;
                case 7:
                    System.out.println(
                            "Thank you for using the Travel Booking System.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 7);

        sc.close();
    }
}
