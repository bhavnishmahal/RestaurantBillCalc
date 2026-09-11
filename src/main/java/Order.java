import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem menuItem, int quantity) {
        if (menuItem == null || quantity <= 0) {
            return;
        }
        // If item already in order, just increase quantity instead of duplicate line
        for (OrderItem oi : items) {
            if (oi.getMenuItem().getName().equalsIgnoreCase(menuItem.getName())) {
                oi.addQuantity(quantity);
                return;
            }
        }
        items.add(new OrderItem(menuItem, quantity));
    }

    public boolean removeItem(MenuItem menuItem) {
        if (menuItem == null) return false;
        return items.removeIf(oi -> oi.getMenuItem().getName().equalsIgnoreCase(menuItem.getName()));
    }

    public boolean removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            return true;
        }
        return false;
    }

    public void clear() {
        items.clear();
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

    public int getItemCount() {
        int count = 0;
        for (OrderItem oi : items) {
            count += oi.getQuantity();
        }
        return count;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
