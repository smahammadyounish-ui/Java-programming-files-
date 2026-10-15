import java.util.Scanner;

class Patient {
    int patientId;
    String name;
    int age;
    String gender;
    String phone;
    String medicalHistory = "No medical history recorded";

    Patient(int patientId, String name, int age,
            String gender, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    void displayDetails() {
        System.out.println("\nPatient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phone);
        System.out.println("Medical History: " + medicalHistory);
    }
}

class Appointment {
    int appointmentId;
    int patientId;
    String doctorName;
    String date;
    String time;

    Appointment(int appointmentId, int patientId,
                String doctorName, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    void displayAppointment() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor: " + doctorName);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
    }
}

public class HospitalPatientManagementSystem {
    static Scanner sc = new Scanner(System.in);

    static Patient[] patients = new Patient[100];
    static Appointment[] appointments = new Appointment[200];

    static int patientCount = 0;
    static int appointmentCount = 0;
    static int nextPatientId = 1;
    static int nextAppointmentId = 1;

    static double consultationFee = 500.0;

    static int readInt(String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextInt()) {
                return sc.nextInt();
            }

            System.out.println("Please enter a valid whole number.");
            sc.next();
        }
    }

    static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            }

            System.out.println("Please enter a valid amount.");
            sc.next();
        }
    }

    static Patient findPatient(int id) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].patientId == id) {
                return patients[i];
            }
        }
        return null;
    }

    // 1. Patient registration
    static void registerPatient() {
        if (patientCount >= patients.length) {
            System.out.println("Patient storage is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        int age = readInt("Enter age: ");

        if (age < 0 || age > 130) {
            System.out.println("Invalid age.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        patients[patientCount] = new Patient(
                nextPatientId++, name, age, gender, phone);

        System.out.println("Patient registered successfully.");
        System.out.println("Patient ID: "
                + patients[patientCount].patientId);

        patientCount++;
    }

    // 2. Appointment booking
    static void bookAppointment() {
        if (appointmentCount >= appointments.length) {
            System.out.println("Appointment storage is full.");
            return;
        }

        int id = readInt("Enter patient ID: ");
        Patient p = findPatient(id);

        if (p == null) {
            System.out.println("Patient not found. Register first.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter doctor name: ");
        String doctor = sc.nextLine();

        System.out.print("Enter appointment date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("Enter appointment time: ");
        String time = sc.nextLine();

        appointments[appointmentCount] = new Appointment(
                nextAppointmentId++, id, doctor, date, time);

        System.out.println("Appointment booked successfully.");
        System.out.println("Appointment ID: "
                + appointments[appointmentCount].appointmentId);

        appointmentCount++;
    }

    // 3. View appointments
    static void viewAppointments() {
        int id = readInt("Enter patient ID: ");
        boolean found = false;

        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].patientId == id) {
                appointments[i].displayAppointment();
                found = true;
                System.out.println("--------------------");
            }
        }

        if (!found) {
            System.out.println("No appointments found for this patient.");
        }
    }

    // 4. Add medical history
    static void updateMedicalHistory() {
        int id = readInt("Enter patient ID: ");
        Patient p = findPatient(id);

        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter medical history or doctor's notes: ");
        p.medicalHistory = sc.nextLine();

        System.out.println("Medical record updated successfully.");
    }

    // 5. View patient record
    static void viewPatientRecord() {
        int id = readInt("Enter patient ID: ");
        Patient p = findPatient(id);

        if (p == null) {
            System.out.println("Patient not found.");
        } else {
            p.displayDetails();
        }
    }

    // 6. Generate bill
    static void generateBill() {
        int id = readInt("Enter patient ID: ");
        Patient p = findPatient(id);

        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }

        double fee = readDouble("Enter consultation fee: ");
        double otherCharges = readDouble("Enter other charges: ");

        if (fee < 0 || otherCharges < 0) {
            System.out.println("Charges cannot be negative.");
            return;
        }

        double total = fee + otherCharges;

        System.out.println("\n====== HOSPITAL BILL ======");
        System.out.println("Patient ID: " + p.patientId);
        System.out.println("Patient Name: " + p.name);
        System.out.printf("Consultation Fee: Rs. %.2f%n", fee);
        System.out.printf("Other Charges: Rs. %.2f%n", otherCharges);
        System.out.printf("Total Amount: Rs. %.2f%n", total);
        System.out.println("===========================");
    }

    // 7. Display all patients
    static void displayAllPatients() {
        if (patientCount == 0) {
            System.out.println("No patient records available.");
            return;
        }

        for (int i = 0; i < patientCount; i++) {
            patients[i].displayDetails();
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM ===");
            System.out.println("1. Register Patient");
            System.out.println("2. Book Appointment");
            System.out.println("3. View Appointments");
            System.out.println("4. Update Medical History");
            System.out.println("5. View Patient Record");
            System.out.println("6. Generate Bill");
            System.out.println("7. Display All Patients");
            System.out.println("8. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    bookAppointment();
                    break;
                case 3:
                    viewAppointments();
                    break;
                case 4:
                    updateMedicalHistory();
                    break;
                case 5:
                    viewPatientRecord();
                    break;
                case 6:
                    generateBill();
                    break;
                case 7:
                    displayAllPatients();
                    break;
                case 8:
                    System.out.println("Thank you for using the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 8);

        sc.close();
    }
}
