# Restaurant Bill Calculator (RestaurantBillCalc)

A lightweight, clean Java application for calculating restaurant bills with itemized order tracking, customizable discounts, GST (5%), service charges (10%), and formatted receipt generation.

Includes both an **extremely basic, intuitive Swing GUI** and an **interactive terminal CLI**, fully structured to open directly in **Apache NetBeans**.

---

## Features

- **Extremely Basic Swing GUI (`RestaurantBillGUI`)**:
  - Quick menu item selection dropdown with category and pricing.
  - Quantity selector with **Add to Order** button.
  - Live order table displaying Item, Category, Unit Price, Quantity, and Line Total.
  - Controls to remove selected items or clear the entire order.
  - Real-time billing breakdown: Subtotal, Discount, Taxable Amount, GST (5%), Service Charge (10%), and Grand Total.
  - **View Receipt** popup dialog displaying a formatted ASCII receipt.
- **Interactive Terminal CLI (`Main --cli`)**:
  - Numbered restaurant menu display.
  - Continuous loop order entry with automatic line merging for duplicate items.
  - Discount input and instant receipt printing.
- **Robust Domain Logic**:
  - **Duplicate item merging**: Ordering an existing item updates its quantity rather than creating duplicate lines.
  - **Input validation**: Safely guards against negative inputs, non-numeric values, or empty orders.
- **Direct NetBeans Compatibility**:
  - Configured with a standard Maven `pom.xml`, allowing NetBeans to open, build, and run it with zero manual setup.
- **Automated Unit Tests**:
  - Test suite covering subtotals, quantity merging, tax, discount, service charge, and receipt generation.

---

## Project Structure

```
RestaurantBillCalc/
├── pom.xml                                   # NetBeans Maven project descriptor
├── README.md                                 # Documentation & guide
├── run.bat                                   # One-click Windows launcher (GUI, CLI, Tests)
└── src/
    ├── main/
    │   └── java/
    │       ├── Bill.java                     # Computes subtotal, taxes, discount, final total, and receipt
    │       ├── Main.java                     # Application entry point (launches GUI or CLI)
    │       ├── MenuItem.java                 # Menu item model (name, price, category)
    │       ├── Order.java                    # Manages list of order items, merging, and removals
    │       ├── OrderItem.java                # Represents an ordered item line with quantity & line total
    │       └── RestaurantBillGUI.java        # Clean, minimal Swing GUI interface
    └── test/
        └── java/
            └── BillTest.java                 # Automated verification test suite
```

---

## Opening Directly in Apache NetBeans

1. Open **Apache NetBeans**.
2. Click **File** &rarr; **Open Project...** (or press `Ctrl + Shift + O`).
3. Browse to the folder containing this repository:
   ```
   ...\RestaurantBillCalc
   ```
   *(NetBeans will immediately identify it with a Maven project coffee cup icon).*
4. Click **Open Project**.
5. Press **F6** (or right-click the project name in the left sidebar and choose **Run**).
   - NetBeans will compile and launch the Swing GUI!

---

## Running from Terminal / Command Prompt

### Option A: Using `run.bat` (Windows)
Double-click `run.bat` in File Explorer, or run in PowerShell/CMD:
```cmd
run.bat
```
This presents a simple menu to launch the **GUI**, the **Terminal CLI**, or run the **Unit Tests**.

### Option B: Using `javac` and `java`
Compile all sources:
```bash
javac -d target/classes src/main/java/*.java
```

Launch the **Swing GUI**:
```bash
java -cp target/classes Main
```

Run the **Terminal CLI**:
```bash
java -cp target/classes Main --cli
```

### Option C: Using Apache Maven
```bash
# Build project
mvn clean package

# Run GUI
mvn exec:java

# Run CLI
mvn exec:java -Dexec.args="--cli"
```

---

## Billing & Calculation Logic

The bill is computed using the following standard restaurant billing formula:

$$\text{Subtotal} = \sum (\text{Price}_i \times \text{Quantity}_i)$$

$$\text{Discount Amount} = \text{Subtotal} \times \frac{\text{Discount \%}}{100}$$

$$\text{Taxable Amount} = \text{Subtotal} - \text{Discount Amount}$$

$$\text{GST Amount} = \text{Taxable Amount} \times \text{Tax Rate (5\%) }$$

$$\text{Service Charge} = \text{Taxable Amount} \times \text{Service Charge Rate (10\%) }$$

$$\text{Final Total} = \text{Taxable Amount} + \text{GST Amount} + \text{Service Charge}$$

---

## Sample Receipt Output

```text
==================================================
                 BILL RECEIPT                     
==================================================
Item                        Qty      Price        Total
--------------------------------------------------
Paneer Butter Masala          1     220.00       220.00
Veg Biryani                   1     180.00       180.00
Butter Naan                   2      40.00        80.00
Masala Chai                   2      50.00       100.00
--------------------------------------------------
Subtotal:                                       580.00
Discount (10.0%):                                58.00
Taxable Amount:                                 522.00
Tax / GST (5.0%):                                26.10
Service Charge (10.0%):                          52.20
--------------------------------------------------
TOTAL PAYABLE:                                  600.30
==================================================
           Thank you for dining with us!          
==================================================
```

---

## Running Unit Tests

Run the test suite to verify calculation accuracy and order management logic:

```bash
javac -d target/test-classes -cp target/classes src/test/java/BillTest.java
java -cp "target/classes;target/test-classes" BillTest
```

Expected output:
```text
Running RestaurantBillCalc Test Suite...

1. Testing Order subtotal and duplicate item merging... PASSED
2. Testing Bill discount, tax, service charge, and totals... PASSED
3. Testing Order item removal and clear... PASSED
4. Testing receipt text generation... PASSED

All 4 test suites passed successfully!
```

---

## License

This project is licensed under the MIT License - see the repository details for more information.
