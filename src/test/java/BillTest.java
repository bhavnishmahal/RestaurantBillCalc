public class BillTest {

    public static void main(String[] args) {
        System.out.println("Running RestaurantBillCalc Test Suite...\n");

        testOrderSubtotalAndDuplicates();
        testDiscountAndTaxes();
        testOrderRemovalAndClear();
        testReceiptGeneration();

        System.out.println("\nAll 4 test suites passed successfully!");
    }

    private static void testOrderSubtotalAndDuplicates() {
        System.out.print("1. Testing Order subtotal and duplicate item merging... ");
        Order order = new Order();
        MenuItem paneer = new MenuItem("Paneer Butter Masala", 220.0, "Main Course");
        MenuItem naan = new MenuItem("Butter Naan", 40.0, "Bread");

        order.addItem(paneer, 1);
        order.addItem(naan, 2);
        // Add more naan - should merge into existing line
        order.addItem(naan, 1);

        assertEquals(2, order.getItems().size(), "Item line count");
        assertEquals(3, order.getItems().get(1).getQuantity(), "Naan quantity merged");
        // 220 + 3 * 40 = 340
        assertEquals(340.0, order.getSubtotal(), 0.001, "Order subtotal");
        System.out.println("PASSED");
    }

    private static void testDiscountAndTaxes() {
        System.out.print("2. Testing Bill discount, tax, service charge, and totals... ");
        Order order = new Order();
        order.addItem(new MenuItem("Veg Biryani", 180.0, "Main Course"), 2); // 360
        order.addItem(new MenuItem("Cold Coffee", 110.0, "Beverage"), 1);     // 110
        // Subtotal = 470.0

        double taxRate = 0.05;           // 5% GST
        double discountPercent = 10.0;   // 10% discount
        double serviceChargeRate = 0.10; // 10% service charge

        Bill bill = new Bill(order, taxRate, discountPercent, serviceChargeRate);

        assertEquals(470.0, bill.getSubtotal(), 0.001, "Subtotal");
        // Discount 10% of 470 = 47.0
        assertEquals(47.0, bill.getDiscountAmount(), 0.001, "Discount amount");
        // Taxable = 470 - 47 = 423.0
        assertEquals(423.0, bill.getTaxableAmount(), 0.001, "Taxable amount");
        // Tax 5% of 423 = 21.15
        assertEquals(21.15, bill.getTaxAmount(), 0.001, "Tax amount");
        // Service Charge 10% of 423 = 42.30
        assertEquals(42.30, bill.getServiceCharge(), 0.001, "Service charge");
        // Final Total = 423 + 21.15 + 42.30 = 486.45
        assertEquals(486.45, bill.getFinalTotal(), 0.001, "Final Total");
        System.out.println("PASSED");
    }

    private static void testOrderRemovalAndClear() {
        System.out.print("3. Testing Order item removal and clear... ");
        Order order = new Order();
        MenuItem rolls = new MenuItem("Spring Rolls", 150.0, "Starter");
        MenuItem chai = new MenuItem("Masala Chai", 50.0, "Beverage");

        order.addItem(rolls, 2);
        order.addItem(chai, 2);
        assertEquals(2, order.getItems().size(), "Item count before remove");

        // Remove by MenuItem
        boolean removed = order.removeItem(rolls);
        assertTrue(removed, "Removed spring rolls");
        assertEquals(1, order.getItems().size(), "Item count after remove");
        assertEquals(100.0, order.getSubtotal(), 0.001, "Subtotal after remove");

        // Clear
        order.clear();
        assertTrue(order.isEmpty(), "Order is empty after clear");
        assertEquals(0.0, order.getSubtotal(), 0.001, "Subtotal after clear");
        System.out.println("PASSED");
    }

    private static void testReceiptGeneration() {
        System.out.print("4. Testing receipt text generation... ");
        Order order = new Order();
        order.addItem(new MenuItem("Gulab Jamun", 90.0, "Dessert"), 2);

        Bill bill = new Bill(order, 0.05, 0.0, 0.10);
        String receipt = bill.generateReceiptText();

        assertTrue(receipt.contains("BILL RECEIPT"), "Contains header");
        assertTrue(receipt.contains("Gulab Jamun"), "Contains item name");
        assertTrue(receipt.contains("TOTAL PAYABLE:"), "Contains total line");
        assertTrue(receipt.contains("Thank you for dining with us!"), "Contains footer");
        System.out.println("PASSED");
    }

    private static void assertEquals(double expected, double actual, double delta, String msg) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(String.format("FAIL [%s]: Expected %.2f, but got %.2f", msg, expected, actual));
        }
    }

    private static void assertEquals(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) {
            throw new AssertionError(String.format("FAIL [%s]: Expected %s, but got %s", msg, expected, actual));
        }
    }

    private static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError("FAIL: Condition is false - " + msg);
        }
    }
}
