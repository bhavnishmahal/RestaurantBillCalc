import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic Swing GUI for Restaurant Bill Calculator.
 */
public class RestaurantBillGUI extends JFrame {

    private List<MenuItem> menu;
    private Order currentOrder;

    // UI Components
    private JComboBox<MenuItem> cmbMenuItems;
    private JSpinner spinQuantity;
    private JButton btnAddItem;
    private JButton btnRemoveItem;
    private JButton btnClearOrder;

    private JTable orderTable;
    private DefaultTableModel tableModel;

    private JTextField txtDiscount;
    private JTextField txtTaxRate;
    private JTextField txtServiceCharge;

    private JLabel lblSubtotal;
    private JLabel lblDiscountAmount;
    private JLabel lblTaxAmount;
    private JLabel lblServiceCharge;
    private JLabel lblGrandTotal;

    private JButton btnCalculate;
    private JButton btnViewReceipt;

    public RestaurantBillGUI() {
        super("Restaurant Bill Calculator");
        this.currentOrder = new Order();
        initDefaultMenu();
        initUI();
    }

    private void initDefaultMenu() {
        menu = new ArrayList<>();
        menu.add(new MenuItem("Paneer Butter Masala", 220.0, "Main Course"));
        menu.add(new MenuItem("Veg Biryani", 180.0, "Main Course"));
        menu.add(new MenuItem("Butter Naan", 40.0, "Bread"));
        menu.add(new MenuItem("Spring Rolls", 150.0, "Starter"));
        menu.add(new MenuItem("Gulab Jamun", 90.0, "Dessert"));
        menu.add(new MenuItem("Masala Chai", 50.0, "Beverage"));
        menu.add(new MenuItem("Cold Coffee", 110.0, "Beverage"));
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. TOP PANEL: Add Items
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("1. Add Menu Item"));

        topPanel.add(new JLabel("Item:"));
        cmbMenuItems = new JComboBox<>(menu.toArray(new MenuItem[0]));
        topPanel.add(cmbMenuItems);

        topPanel.add(new JLabel("Qty:"));
        spinQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        topPanel.add(spinQuantity);

        btnAddItem = new JButton("Add to Order");
        topPanel.add(btnAddItem);

        add(topPanel, BorderLayout.NORTH);

        // 2. CENTER PANEL: Order Table & item controls
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("2. Current Order"));

        String[] columns = {"Item Name", "Category", "Price (Rs.)", "Qty", "Total (Rs.)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(tableModel);
        orderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setPreferredSize(new Dimension(560, 160));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel tableButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnRemoveItem = new JButton("Remove Selected");
        btnClearOrder = new JButton("Clear Order");
        tableButtons.add(btnRemoveItem);
        tableButtons.add(btnClearOrder);
        centerPanel.add(tableButtons, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // 3. BOTTOM PANEL: Settings & Bill Summary
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("3. Bill Calculation"));

        // Inputs Panel (Discount, Tax, Service Charge)
        JPanel inputsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        inputsPanel.add(new JLabel("Discount (%):"));
        txtDiscount = new JTextField("0.0", 5);
        inputsPanel.add(txtDiscount);

        inputsPanel.add(new JLabel("GST Tax (%):"));
        txtTaxRate = new JTextField("5.0", 5);
        inputsPanel.add(txtTaxRate);

        inputsPanel.add(new JLabel("Service Charge (%):"));
        txtServiceCharge = new JTextField("10.0", 5);
        inputsPanel.add(txtServiceCharge);

        // Results Panel
        JPanel resultsPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        resultsPanel.add(new JLabel("Subtotal:"));
        lblSubtotal = new JLabel("Rs. 0.00");
        resultsPanel.add(lblSubtotal);

        resultsPanel.add(new JLabel("Discount:"));
        lblDiscountAmount = new JLabel("Rs. 0.00");
        resultsPanel.add(lblDiscountAmount);

        resultsPanel.add(new JLabel("GST (5%):"));
        lblTaxAmount = new JLabel("Rs. 0.00");
        resultsPanel.add(lblTaxAmount);

        resultsPanel.add(new JLabel("Service Charge (10%):"));
        lblServiceCharge = new JLabel("Rs. 0.00");
        resultsPanel.add(lblServiceCharge);

        resultsPanel.add(new JLabel("TOTAL PAYABLE:"));
        lblGrandTotal = new JLabel("Rs. 0.00");
        lblGrandTotal.setFont(lblGrandTotal.getFont().deriveFont(Font.BOLD, 14f));
        lblGrandTotal.setForeground(new Color(0, 120, 0));
        resultsPanel.add(lblGrandTotal);

        JPanel splitBottom = new JPanel(new GridLayout(1, 2, 10, 5));
        splitBottom.add(inputsPanel);
        splitBottom.add(resultsPanel);
        bottomPanel.add(splitBottom, BorderLayout.CENTER);

        // Action Buttons at Bottom
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnCalculate = new JButton("Calculate Bill");
        btnCalculate.setFont(btnCalculate.getFont().deriveFont(Font.BOLD));
        btnViewReceipt = new JButton("View Receipt");

        actionsPanel.add(btnCalculate);
        actionsPanel.add(btnViewReceipt);
        bottomPanel.add(actionsPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Wire Event Listeners
        wireEvents();

        pack();
        setLocationRelativeTo(null);
    }

    private void wireEvents() {
        // Add Item
        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuItem selected = (MenuItem) cmbMenuItems.getSelectedItem();
                int qty = (Integer) spinQuantity.getValue();
                if (selected != null && qty > 0) {
                    currentOrder.addItem(selected, qty);
                    refreshTable();
                    updateBillDisplay();
                }
            }
        });

        // Remove Item
        btnRemoveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow >= 0) {
                    currentOrder.removeItem(selectedRow);
                    refreshTable();
                    updateBillDisplay();
                } else {
                    JOptionPane.showMessageDialog(RestaurantBillGUI.this,
                            "Please select an item in the table to remove.",
                            "Selection Required", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Clear Order
        btnClearOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOrder.clear();
                refreshTable();
                updateBillDisplay();
            }
        });

        // Calculate
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBillDisplay();
            }
        });

        // View Receipt
        btnViewReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReceiptDialog();
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (OrderItem item : currentOrder.getItems()) {
            tableModel.addRow(new Object[]{
                    item.getMenuItem().getName(),
                    item.getMenuItem().getCategory(),
                    String.format("%.2f", item.getMenuItem().getPrice()),
                    item.getQuantity(),
                    String.format("%.2f", item.getLineTotal())
            });
        }
    }

    private Bill computeCurrentBill() {
        double discount = parsePercent(txtDiscount.getText(), 0.0);
        double tax = parsePercent(txtTaxRate.getText(), 5.0) / 100.0;
        double serviceCharge = parsePercent(txtServiceCharge.getText(), 10.0) / 100.0;

        return new Bill(currentOrder, tax, discount, serviceCharge);
    }

    private void updateBillDisplay() {
        Bill bill = computeCurrentBill();

        lblSubtotal.setText(String.format("Rs. %.2f", bill.getSubtotal()));
        lblDiscountAmount.setText(String.format("Rs. %.2f", bill.getDiscountAmount()));
        lblTaxAmount.setText(String.format("Rs. %.2f", bill.getTaxAmount()));
        lblServiceCharge.setText(String.format("Rs. %.2f", bill.getServiceCharge()));
        lblGrandTotal.setText(String.format("Rs. %.2f", bill.getFinalTotal()));
    }

    private void showReceiptDialog() {
        if (currentOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Order is empty. Add some items first!",
                    "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Bill bill = computeCurrentBill();
        String receipt = bill.generateReceiptText();

        JTextArea textArea = new JTextArea(receipt);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(450, 350));

        JOptionPane.showMessageDialog(this, scroll, "Bill Receipt", JOptionPane.PLAIN_MESSAGE);
    }

    private double parsePercent(String text, double defaultValue) {
        if (text == null || text.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            double val = Double.parseDouble(text.trim());
            return Math.max(0.0, val);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                }
                new RestaurantBillGUI().setVisible(true);
            }
        });
    }
}
