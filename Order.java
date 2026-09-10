import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem menuItem, int quantity) {
        // If item already in order, just increase quantity instead of duplicate line
        for (OrderItem oi : items) {
            if (oi.getMenuItem().getName().equals(menuItem.getName())) {
                oi.addQuantity(quantity);
                return;
            }
        }
        items.add(new OrderItem(menuItem, quantity));
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (OrderItem oi : items) {
            subtotal += oi.getLineTotal();
        }
        return subtotal;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
