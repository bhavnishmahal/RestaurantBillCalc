import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.util.List;

/**
 * Shows the restaurant menu with a picture (icon) for each item.
 * This is the first screen the user sees. Clicking "Proceed to Order"
 * moves on ("aage") to the existing RestaurantBillGUI order screen.
 */
public class MenuDisplayFrame extends JFrame {

    private static final Color INK = new Color(38, 34, 30);
    private static final Color CREAM = new Color(250, 247, 241);
    private static final Color TERRACOTTA = new Color(181, 83, 54);
    private static final Color SAGE = new Color(83, 112, 91);
    private final Order currentOrder = new Order();

    public MenuDisplayFrame() {
        super("Restaurant Menu");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(CREAM);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel();
        header.setBackground(INK);
        header.setBorder(BorderFactory.createEmptyBorder(18, 12, 16, 12));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JLabel heading = new JLabel("THE DAILY TABLE", SwingConstants.CENTER);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setForeground(new Color(247, 220, 177));
        heading.setFont(new Font("Serif", Font.BOLD, 25));
        JLabel subtitle = new JLabel("Choose your favourites, then review your order", SwingConstants.CENTER);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(235, 229, 218));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        header.add(heading);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        add(header, BorderLayout.NORTH);

        List<MenuItem> menu = MenuData.getDefaultMenu();

        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 14, 14));
        gridPanel.setBackground(CREAM);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));

        for (MenuItem item : menu) {
            gridPanel.add(createMenuCard(item));
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setPreferredSize(new Dimension(600, 420));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CREAM);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnProceed = styledButton("Review Order  \u2192", TERRACOTTA, Color.WHITE);
        btnProceed.addActionListener(e -> {
            if (currentOrder.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Add at least one item before proceeding.",
                        "Order is Empty", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            new RestaurantBillGUI(currentOrder).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 14));
        bottomPanel.setBackground(CREAM);
        bottomPanel.add(btnProceed);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createMenuCard(MenuItem item) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel imageLabel = new JLabel(MenuArt.createMenuImage(item));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setForeground(INK);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 15));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel categoryLabel = new JLabel(item.getCategory(), SwingConstants.CENTER);
        categoryLabel.setFont(categoryLabel.getFont().deriveFont(Font.ITALIC, 11f));
        categoryLabel.setForeground(Color.GRAY);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel(String.format("Rs. %.2f", item.getPrice()), SwingConstants.CENTER);
        priceLabel.setForeground(TERRACOTTA);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel quantityLabel = new JLabel("0 added", SwingConstants.CENTER);
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityLabel.setForeground(new Color(70, 70, 70));

        JButton addButton = new JButton("+1");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleSmallButton(addButton, SAGE, Color.WHITE);
        addButton.addActionListener(e -> {
            currentOrder.addItem(item, 1);
            quantityLabel.setText(getQuantityText(item));
        });

        JButton removeButton = new JButton("-1");
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleSmallButton(removeButton, new Color(118, 87, 72), Color.WHITE);
        removeButton.addActionListener(e -> {
            currentOrder.removeQuantity(item, 1);
            quantityLabel.setText(getQuantityText(item));
        });

        JPanel quantityButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        quantityButtons.setOpaque(false);
        quantityButtons.add(removeButton);
        quantityButtons.add(addButton);

        card.add(imageLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(nameLabel);
        card.add(categoryLabel);
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(quantityLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(quantityButtons);

        return card;
    }

    private String getQuantityText(MenuItem item) {
        for (OrderItem orderItem : currentOrder.getItems()) {
            if (orderItem.getMenuItem().equals(item)) {
                return orderItem.getQuantity() + " added";
            }
        }
        return "0 added";
    }

    private static JButton styledButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setUI(new BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        return button;
    }

    private static void styleSmallButton(JButton button, Color background, Color foreground) {
        button.setUI(new BasicButtonUI());
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 13, 5, 13));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new MenuDisplayFrame().setVisible(true);
        });
    }
}
