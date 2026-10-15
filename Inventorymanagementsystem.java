import java.util.*;

class Product {
    int id;
    String name;
    double price;
    double gstRate;
    int stock;

    Product(int id, String name, double price,
            double gstRate, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.gstRate = gstRate;
        this.stock = stock;
    }
}

public class InventoryManagement {
    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Product> products = new LinkedHashMap<>();

    static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (products.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        System.out.print("Enter GST Rate (%): ");
        double gst = sc.nextDouble();

        System.out.print("Enter Stock Quantity: ");
        int stock = sc.nextInt();

        if (price < 0 || gst < 0 || stock < 0) {
            System.out.println("Invalid product details!");
            return;
        }

        products.put(id,
            new Product(id, name, price, gst, stock));

        System.out.println("Product added successfully!");
    }

    static void viewProducts() {
        System.out.println("\n--------- INVENTORY ---------");
        System.out.printf("%-6s %-18s %10s %8s %8s%n",
                "ID", "Name", "Price", "GST%", "Stock");

        for (Product p : products.values()) {
            System.out.printf("%-6d %-18s %10.2f %8.2f %8d%n",
                    p.id, p.name, p.price,
                    p.gstRate, p.stock);
        }
    }

    static void generateBill() {
        if (products.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        double subtotal = 0;
        double totalDiscount = 0;
        double totalGST = 0;
        double grandTotal = 0;

        Map<Integer, Integer> cart = new LinkedHashMap<>();

        while (true) {
            viewProducts();
            System.out.print(
                "\nEnter Product ID (0 to finish): ");
            int id = sc.nextInt();

            if (id == 0) {
                break;
            }

            Product p = products.get(id);

            if (p == null) {
                System.out.println("Invalid Product ID!");
                continue;
            }

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            int alreadyAdded = cart.getOrDefault(id, 0);

            if (qty <= 0 || qty > p.stock - alreadyAdded) {
                System.out.println("Invalid quantity or insufficient stock!");
                continue;
            }

            cart.put(id, alreadyAdded + qty);
        }

        if (cart.isEmpty()) {
            System.out.println("No items selected.");
            return;
        }

        System.out.print(
            "Enter discount percentage (0-100): ");
        double discountRate = sc.nextDouble();

        if (discountRate < 0 || discountRate > 100) {
            System.out.println("Invalid discount!");
            return;
        }

        System.out.println("\n========== CUSTOMER BILL ==========");
        System.out.printf("%-16s %5s %10s %10s%n",
                "Product", "Qty", "Amount", "GST");

        for (Map.Entry<Integer, Integer> entry
                : cart.entrySet()) {
            Product p = products.get(entry.getKey());
            int qty = entry.getValue();

            double amount = p.price * qty;
            double discount = amount * discountRate / 100.0;
            double taxableAmount = amount - discount;
            double gst = taxableAmount * p.gstRate / 100.0;
            double lineTotal = taxableAmount + gst;

            subtotal += amount;
            totalDiscount += discount;
            totalGST += gst;
            grandTotal += lineTotal;

            System.out.printf("%-16s %5d %10.2f %10.2f%n",
                    p.name, qty, amount, gst);
        }

        // Update stock only after the bill is calculated.
        for (Map.Entry<Integer, Integer> entry
                : cart.entrySet()) {
            Product p = products.get(entry.getKey());
            p.stock -= entry.getValue();
        }

        System.out.println("------------------------------------");
        System.out.printf("Subtotal       : Rs. %.2f%n", subtotal);
        System.out.printf("Discount       : Rs. %.2f%n", totalDiscount);
        System.out.printf("Taxable Amount : Rs. %.2f%n",
                subtotal - totalDiscount);
        System.out.printf("GST Amount     : Rs. %.2f%n", totalGST);
        System.out.printf("GRAND TOTAL    : Rs. %.2f%n", grandTotal);
        System.out.println("Stock updated successfully!");
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== SUPERMARKET MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Generate Bill");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    generateBill();
                    break;
                case 4:
                    System.out.println("Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
