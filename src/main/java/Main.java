import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // If --cli is passed, or if headless environment, run CLI mode
        boolean forceCli = false;
        for (String arg : args) {
            if ("--cli".equalsIgnoreCase(arg)) {
                forceCli = true;
                break;
            }
        }

        if (!forceCli && !GraphicsEnvironment.isHeadless()) {
            // Launch GUI by default in desktop environment
            RestaurantBillGUI.main(args);
        } else {
            runCli();
        }
    }

    public static void runCli() {
        Scanner scanner = new Scanner(System.in);

        // 1. Define the menu
        List<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem("Paneer Butter Masala", 220.0, "Main Course"));
        menu.add(new MenuItem("Veg Biryani", 180.0, "Main Course"));
        menu.add(new MenuItem("Butter Naan", 40.0, "Bread"));
        menu.add(new MenuItem("Spring Rolls", 150.0, "Starter"));
        menu.add(new MenuItem("Gulab Jamun", 90.0, "Dessert"));
        menu.add(new MenuItem("Masala Chai", 50.0, "Beverage"));
        menu.add(new MenuItem("Cold Coffee", 110.0, "Beverage"));

        // 2. Display menu
        System.out.println("===== WELCOME TO THE RESTAURANT =====");
        System.out.println("\n--- MENU ---");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.printf("%d. %-25s Rs. %.2f  [%s]%n",
                    i + 1, item.getName(), item.getPrice(), item.getCategory());
        }

        // 3. Take orders in a loop
        Order order = new Order();
        while (true) {
            System.out.print("\nEnter item number to order (0 to finish): ");
            int choice = readInt(scanner);

            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > menu.size()) {
                System.out.println("Invalid item number. Try again.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = readInt(scanner);
            if (qty <= 0) {
                System.out.println("Quantity must be positive. Try again.");
                continue;
            }

            MenuItem selected = menu.get(choice - 1);
            order.addItem(selected, qty);
            System.out.println(qty + " x " + selected.getName() + " added to order.");
        }

        if (order.isEmpty()) {
            System.out.println("\nNo items ordered. Exiting.");
            scanner.close();
            return;
        }

        // 4. Discount (optional)
        System.out.print("\nEnter discount percentage (0 if none): ");
        double discount = readDouble(scanner);

        // 5. Fixed rates for tax and service charge (customize as needed)
        double taxRate = 0.05;          // 5% GST
        double serviceChargeRate = 0.10; // 10% service charge

        Bill bill = new Bill(order, taxRate, discount, serviceChargeRate);

        // 6. Print receipt
        System.out.println();
        System.out.print(bill.generateReceiptText());

        scanner.close();
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double readDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
