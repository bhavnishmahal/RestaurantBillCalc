import javax.swing.ImageIcon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public final class MenuArt {
    private static final int WIDTH = 220;
    private static final int HEIGHT = 130;

    private MenuArt() {
    }

    public static ImageIcon createMenuImage(MenuItem item) {
        ImageIcon customImage = loadCustomImage(item);
        if (customImage != null) {
            return customImage;
        }

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            paintBackground(graphics, item.getCategory());
            paintIllustration(graphics, item);
            paintNameRibbon(graphics, item.getName());
        } finally {
            graphics.dispose();
        }
        return new ImageIcon(image);
    }

    private static ImageIcon loadCustomImage(MenuItem item) {
        String imageName = item.getName().trim().toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        String[] extensions = {".png", ".jpg", ".jpeg"};

        for (String extension : extensions) {
            String resourcePath = "/images/" + imageName + extension;
            try (InputStream input = MenuArt.class.getResourceAsStream(resourcePath)) {
                if (input != null) {
                    BufferedImage image = ImageIO.read(input);
                    if (image != null) {
                        Image scaled = image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
                        return new ImageIcon(scaled);
                    }
                }
            } catch (IOException ignored) {
                return null;
            }
        }
        return null;
    }

    private static void paintBackground(Graphics2D graphics, String category) {
        Color top;
        Color bottom;
        if (category == null) {
            top = new Color(245, 239, 230);
            bottom = new Color(226, 214, 197);
        } else {
            switch (category.trim().toLowerCase()) {
                case "main course":
                    top = new Color(255, 215, 166);
                    bottom = new Color(235, 145, 72);
                    break;
                case "dessert":
                    top = new Color(255, 214, 228);
                    bottom = new Color(227, 131, 168);
                    break;
                case "bread":
                    top = new Color(248, 232, 190);
                    bottom = new Color(216, 177, 95);
                    break;
                case "starter":
                    top = new Color(216, 241, 212);
                    bottom = new Color(121, 186, 115);
                    break;
                case "beverage":
                    top = new Color(214, 238, 255);
                    bottom = new Color(92, 153, 214);
                    break;
                default:
                    top = new Color(236, 236, 236);
                    bottom = new Color(192, 192, 192);
                    break;
            }
        }

        graphics.setPaint(new GradientPaint(0, 0, top, 0, HEIGHT, bottom));
        graphics.fillRoundRect(0, 0, WIDTH, HEIGHT, 28, 28);

        graphics.setColor(new Color(255, 255, 255, 50));
        graphics.fillOval(-20, -25, 90, 90);
        graphics.fillOval(150, -10, 85, 85);
    }

    private static void paintIllustration(Graphics2D graphics, MenuItem item) {
        String category = item.getCategory() == null ? "" : item.getCategory().trim().toLowerCase();
        String name = item.getName() == null ? "" : item.getName().trim().toLowerCase();

        switch (category) {
            case "main course":
                if (name.contains("biryani")) {
                    drawBiryani(graphics);
                } else {
                    drawCurryBowl(graphics);
                }
                break;
            case "dessert":
                drawDessertPlate(graphics);
                break;
            case "bread":
                drawBread(graphics);
                break;
            case "starter":
                drawSpringRolls(graphics);
                break;
            case "beverage":
                if (name.contains("coffee")) {
                    drawCoffee(graphics);
                } else {
                    drawTea(graphics);
                }
                break;
            default:
                drawGenericPlate(graphics);
                break;
        }
    }

    private static void paintNameRibbon(Graphics2D graphics, String name) {
        graphics.setColor(new Color(0, 0, 0, 70));
        graphics.fillRoundRect(14, HEIGHT - 34, WIDTH - 28, 22, 18, 18);
        graphics.setColor(Color.WHITE);
        String label = name == null ? "" : name;
        if (label.length() > 20) {
            label = label.substring(0, 19) + "…";
        }
        graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 13f));
        graphics.drawString(label, 22, HEIGHT - 19);
    }

    private static void drawCurryBowl(Graphics2D graphics) {
        graphics.setColor(new Color(117, 72, 33));
        graphics.fillRoundRect(56, 70, 108, 36, 28, 28);
        graphics.setColor(new Color(252, 246, 229));
        graphics.fillOval(44, 52, 132, 56);
        graphics.setColor(new Color(183, 98, 48));
        graphics.fillOval(56, 60, 108, 42);
        graphics.setColor(new Color(255, 188, 73));
        graphics.fillOval(75, 66, 48, 26);
        graphics.fillOval(108, 72, 36, 18);
        drawSteam(graphics, 88, 28);
    }

    private static void drawBiryani(Graphics2D graphics) {
        graphics.setColor(new Color(117, 72, 33));
        graphics.fillRoundRect(52, 72, 116, 32, 24, 24);
        graphics.setColor(new Color(248, 244, 231));
        graphics.fillOval(42, 52, 136, 54);
        graphics.setColor(new Color(188, 134, 78));
        graphics.fillOval(52, 60, 116, 40);
        graphics.setColor(new Color(247, 202, 76));
        graphics.fillOval(60, 64, 96, 28);
        graphics.setColor(new Color(215, 92, 62));
        graphics.fillOval(86, 58, 22, 22);
        graphics.fillOval(114, 66, 20, 20);
        drawSteam(graphics, 92, 26);
    }

    private static void drawDessertPlate(Graphics2D graphics) {
        graphics.setColor(new Color(247, 243, 239));
        graphics.fillOval(44, 57, 132, 56);
        graphics.setColor(new Color(217, 145, 79));
        graphics.fillRoundRect(86, 49, 48, 38, 10, 10);
        graphics.setColor(new Color(240, 200, 110));
        graphics.fillPolygon(new int[]{86, 134, 110}, new int[]{49, 49, 28}, 3);
        graphics.setColor(new Color(194, 63, 94));
        graphics.fillOval(100, 28, 14, 14);
        graphics.setColor(new Color(255, 255, 255, 120));
        graphics.fillOval(92, 56, 18, 10);
    }

    private static void drawBread(Graphics2D graphics) {
        graphics.setColor(new Color(221, 167, 87));
        graphics.fillRoundRect(58, 54, 104, 48, 28, 28);
        graphics.setColor(new Color(245, 206, 137));
        graphics.fillRoundRect(66, 46, 92, 48, 26, 26);
        graphics.setColor(new Color(191, 123, 47));
        graphics.drawRoundRect(58, 54, 104, 48, 28, 28);
        graphics.setStroke(new BasicStroke(3f));
        graphics.drawLine(87, 57, 81, 88);
        graphics.drawLine(114, 55, 112, 91);
        graphics.drawLine(141, 57, 145, 87);
    }

    private static void drawSpringRolls(Graphics2D graphics) {
        graphics.setColor(new Color(247, 244, 233));
        graphics.fillOval(44, 60, 132, 50);
        graphics.setColor(new Color(193, 136, 69));
        graphics.fillRoundRect(74, 60, 70, 18, 10, 10);
        graphics.fillRoundRect(88, 76, 70, 18, 10, 10);
        graphics.setColor(new Color(229, 176, 102));
        graphics.fillRoundRect(76, 62, 66, 12, 10, 10);
        graphics.fillRoundRect(90, 78, 66, 12, 10, 10);
        graphics.setColor(new Color(121, 186, 115));
        graphics.fillOval(52, 50, 22, 22);
        graphics.fillOval(156, 74, 20, 20);
    }

    private static void drawTea(Graphics2D graphics) {
        graphics.setColor(new Color(246, 246, 242));
        graphics.fillOval(44, 62, 132, 42);
        graphics.setColor(new Color(255, 252, 245));
        graphics.fillRoundRect(84, 48, 52, 46, 16, 16);
        graphics.setColor(new Color(195, 108, 65));
        graphics.fillRoundRect(88, 56, 44, 28, 12, 12);
        graphics.setColor(new Color(255, 255, 255));
        graphics.drawArc(128, 58, 18, 20, -40, 260);
        drawSteam(graphics, 96, 28);
    }

    private static void drawCoffee(Graphics2D graphics) {
        graphics.setColor(new Color(244, 241, 236));
        graphics.fillOval(42, 62, 136, 42);
        graphics.setColor(new Color(254, 251, 245));
        graphics.fillRoundRect(84, 44, 56, 52, 18, 18);
        graphics.setColor(new Color(101, 65, 39));
        graphics.fillRoundRect(90, 54, 44, 30, 12, 12);
        graphics.setColor(new Color(255, 255, 255));
        graphics.drawArc(132, 58, 18, 20, -40, 260);
        graphics.setColor(new Color(255, 214, 132));
        graphics.fillOval(94, 50, 16, 10);
        drawSteam(graphics, 98, 26);
    }

    private static void drawGenericPlate(Graphics2D graphics) {
        graphics.setColor(new Color(245, 245, 245));
        graphics.fillOval(46, 54, 128, 58);
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillOval(64, 68, 92, 28);
        graphics.setColor(new Color(214, 214, 214));
        graphics.drawOval(64, 68, 92, 28);
    }

    private static void drawSteam(Graphics2D graphics, int centerX, int topY) {
        graphics.setColor(new Color(255, 255, 255, 180));
        graphics.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graphics.drawArc(centerX - 26, topY, 16, 28, -10, 180);
        graphics.drawArc(centerX - 6, topY - 4, 16, 30, -10, 180);
        graphics.drawArc(centerX + 14, topY, 16, 28, -10, 180);
    }
}