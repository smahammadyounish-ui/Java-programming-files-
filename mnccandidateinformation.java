import java.util.Scanner;

class Candidate {
    int candidateId;
    String name;
    String email;
    String qualification;
    String skills;
    String status;

    Candidate(int candidateId, String name, String email,
              String qualification, String skills) {
        this.candidateId = candidateId;
        this.name = name;
        this.email = email;
        this.qualification = qualification;
        this.skills = skills;
        this.status = "Applied";
    }

    void display() {
        System.out.println("\nCandidate ID: " + candidateId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Qualification: " + qualification);
        System.out.println("Skills: " + skills);
        System.out.println("Recruitment Status: " + status);
        System.out.println("-------------------------------");
    }
}

public class CandidateInformationSystem {

    static Scanner sc = new Scanner(System.in);
    static Candidate[] candidates = new Candidate[1000];

    static int count = 0;
    static int nextId = 1001;

    static final String[] STATUSES = {
        "Applied",
        "Shortlisted",
        "Interview Scheduled",
        "Selected",
        "Rejected"
    };

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

    static Candidate findCandidate(int id) {
        for (int i = 0; i < count; i++) {
            if (candidates[i].candidateId == id) {
                return candidates[i];
            }
        }

        return null;
    }

    // 1. Register candidate
    static void registerCandidate() {
        if (count >= candidates.length) {
            System.out.println("Candidate storage is full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter candidate name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        System.out.print("Enter qualification: ");
        String qualification = sc.nextLine().trim();

        System.out.print("Enter skills (comma-separated): ");
        String skills = sc.nextLine().trim();

        if (name.isEmpty() || email.isEmpty()
                || qualification.isEmpty() || skills.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        candidates[count] = new Candidate(
                nextId++, name, email, qualification, skills);

        System.out.println("Candidate registered successfully.");
        System.out.println("Candidate ID: "
                + candidates[count].candidateId);

        count++;
    }

    // 2. Display all candidates
    static void displayCandidates() {
        if (count == 0) {
            System.out.println("No candidate records available.");
            return;
        }

        System.out.println("\n===== ALL CANDIDATES =====");

        for (int i = 0; i < count; i++) {
            candidates[i].display();
        }
    }

    // 3. Search candidates by skill
    static void searchBySkill() {
        if (count == 0) {
            System.out.println("No candidate records available.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter skill to search: ");
        String skill = sc.nextLine().trim();

        if (skill.isEmpty()) {
            System.out.println("Skill cannot be empty.");
            return;
        }

        boolean found = false;

        System.out.println("\n===== SKILL SEARCH RESULTS =====");

        for (int i = 0; i < count; i++) {
            String[] candidateSkills =
                    candidates[i].skills.split(",");

            for (String candidateSkill : candidateSkills) {
                if (candidateSkill.trim().equalsIgnoreCase(skill)) {
                    candidates[i].display();
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No candidates found with skill: " + skill);
        }
    }

    // 4. Update recruitment status
    static void updateStatus() {
        int id = readInt("Enter candidate ID: ");
        Candidate c = findCandidate(id);

        if (c == null) {
            System.out.println("Candidate not found.");
            return;
        }

        System.out.println("\nSelect recruitment status:");

        for (int i = 0; i < STATUSES.length; i++) {
            System.out.println((i + 1) + ". " + STATUSES[i]);
        }

        int choice = readInt("Enter status choice: ");

        if (choice < 1 || choice > STATUSES.length) {
            System.out.println("Invalid status choice.");
            return;
        }

        c.status = STATUSES[choice - 1];

        System.out.println("Recruitment status updated successfully.");
        c.display();
    }

    // 5. Search by candidate ID
    static void searchById() {
        int id = readInt("Enter candidate ID: ");
        Candidate c = findCandidate(id);

        if (c == null) {
            System.out.println("Candidate not found.");
        } else {
            c.display();
        }
    }

    // 6. Generate recruitment report
    static void recruitmentReport() {
        if (count == 0) {
            System.out.println("No recruitment data available.");
            return;
        }

        int applied = 0;
        int shortlisted = 0;
        int interview = 0;
        int selected = 0;
        int rejected = 0;

        for (int i = 0; i < count; i++) {
            switch (candidates[i].status) {
                case "Applied":
                    applied++;
                    break;
                case "Shortlisted":
                    shortlisted++;
                    break;
                case "Interview Scheduled":
                    interview++;
                    break;
                case "Selected":
                    selected++;
                    break;
                case "Rejected":
                    rejected++;
                    break;
            }
        }

        System.out.println("\n===== RECRUITMENT REPORT =====");
        System.out.println("Total Candidates: " + count);
        System.out.println("Applied: " + applied);
        System.out.println("Shortlisted: " + shortlisted);
        System.out.println("Interview Scheduled: " + interview);
        System.out.println("Selected: " + selected);
        System.out.println("Rejected: " + rejected);

        double selectionRate = selected * 100.0 / count;

        System.out.printf("Selection Rate: %.2f%%%n", selectionRate);
        System.out.println("==============================");
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== MNC CANDIDATE INFORMATION SYSTEM ===");
            System.out.println("1. Register Candidate");
            System.out.println("2. Display All Candidates");
            System.out.println("3. Search Candidates by Skill");
            System.out.println("4. Update Interview/Recruitment Status");
            System.out.println("5. Search Candidate by ID");
            System.out.println("6. Generate Recruitment Report");
            System.out.println("7. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerCandidate();
                    break;
                case 2:
                    displayCandidates();
                    break;
                case 3:
                    searchBySkill();
                    break;
                case 4:
                    updateStatus();
                    break;
                case 5:
                    searchById();
                    break;
                case 6:
                    recruitmentReport();
                    break;
                case 7:
                    System.out.println(
                            "Thank you for using the Candidate Information System.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }
}
