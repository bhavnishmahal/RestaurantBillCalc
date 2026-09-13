import java.util.ArrayList;
import java.util.List;

/**
 * Single shared source of the restaurant menu.
 * Both the CLI (Main) and the Swing GUI (RestaurantBillGUI, MenuDisplayFrame)
 * pull from here, so the menu only needs to be edited in one place.
 */
public class MenuData {

    public static List<MenuItem> getDefaultMenu() {
        List<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem("Paneer Butter Masala", 220.0, "Main Course"));
        menu.add(new MenuItem("Veg Biryani", 180.0, "Main Course"));
        menu.add(new MenuItem("Butter Naan", 40.0, "Bread"));
        menu.add(new MenuItem("Spring Rolls", 150.0, "Starter"));
        menu.add(new MenuItem("Gulab Jamun", 90.0, "Dessert"));
        menu.add(new MenuItem("Masala Chai", 50.0, "Beverage"));
        menu.add(new MenuItem("Cold Coffee", 110.0, "Beverage"));
        return menu;
    }
}
