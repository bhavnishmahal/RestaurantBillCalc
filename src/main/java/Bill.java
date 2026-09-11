public class Bill {
    private Order order;
    private double taxRate;          // e.g. 0.05 for 5%
    private double discountPercent;  // e.g. 10 for 10%
    private double serviceChargeRate; // e.g. 0.10 for 10%

    public Bill(Order order, double taxRate, double discountPercent, double serviceChargeRate) {
        this.order = (order != null) ? order : new Order();
        this.taxRate = Math.max(0.0, taxRate);
        this.discountPercent = Math.max(0.0, Math.min(100.0, discountPercent));
        this.serviceChargeRate = Math.max(0.0, serviceChargeRate);
    }

    public Order getOrder() {
        return order;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public double getServiceChargeRate() {
        return serviceChargeRate;
    }

    public double getSubtotal() {
        return order.getSubtotal();
    }

    public double getDiscountAmount() {
        return getSubtotal() * (discountPercent / 100.0);
    }

    public double getTaxableAmount() {
        return getSubtotal() - getDiscountAmount();
    }

    public double getTaxAmount() {
        return getTaxableAmount() * taxRate;
    }

    public double getServiceCharge() {
        return getTaxableAmount() * serviceChargeRate;
    }

    public double getFinalTotal() {
        return getTaxableAmount() + getTaxAmount() + getServiceCharge();
    }

    public String generateReceiptText() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("                 BILL RECEIPT                     \n");
        sb.append("==================================================\n");
        sb.append(String.format("%-25s %5s %10s %12s%n", "Item", "Qty", "Price", "Total"));
        sb.append("--------------------------------------------------\n");

        for (OrderItem oi : order.getItems()) {
            sb.append(String.format("%-25s %5d %10.2f %12.2f%n",
                    oi.getMenuItem().getName(),
                    oi.getQuantity(),
                    oi.getMenuItem().getPrice(),
                    oi.getLineTotal()));
        }

        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-25s %22.2f%n", "Subtotal:", getSubtotal()));
        if (discountPercent > 0) {
            sb.append(String.format("%-25s %22.2f%n", "Discount (" + discountPercent + "%):", getDiscountAmount()));
            sb.append(String.format("%-25s %22.2f%n", "Taxable Amount:", getTaxableAmount()));
        }
        sb.append(String.format("%-25s %22.2f%n", "Tax / GST (" + (taxRate * 100) + "%):", getTaxAmount()));
        sb.append(String.format("%-25s %22.2f%n", "Service Charge (" + (serviceChargeRate * 100) + "%):", getServiceCharge()));
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-25s %22.2f%n", "TOTAL PAYABLE:", getFinalTotal()));
        sb.append("==================================================\n");
        sb.append("           Thank you for dining with us!          \n");
        sb.append("==================================================\n");
        return sb.toString();
    }
}
