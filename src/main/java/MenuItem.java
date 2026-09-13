import java.util.Objects;

public class MenuItem {
    private String name;
    private double price;
    private String category;
    private String icon;

    // Old constructor still works — picks a default icon based on category
    public MenuItem(String name, double price, String category) {
        this(name, price, category, defaultIconFor(category));
    }

    // New constructor lets you set a specific icon for the item
    public MenuItem(String name, double price, String category, String icon) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.icon = (icon != null && !icon.isEmpty()) ? icon : defaultIconFor(category);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getIcon() {
        return icon;
    }

    private static String defaultIconFor(String category) {
        if (category == null) {
            return "\uD83C\uDF7D"; // generic plate icon
        }
        switch (category.trim().toLowerCase()) {
            case "main course":
                return "\uD83C\uDF5B"; // curry bowl
            case "bread":
                return "\uD83C\uDF5E"; // bread
            case "starter":
                return "\uD83E\uDD5F"; // dumpling
            case "dessert":
                return "\uD83C\uDF6E"; // custard/dessert
            case "beverage":
                return "\uD83E\uDD64"; // cup with straw
            default:
                return "\uD83C\uDF7D"; // generic plate icon
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s - Rs. %.2f [%s]", icon, name, price, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
