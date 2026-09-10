public class Bill {
    private Order order;
    private double taxRate;          // e.g. 0.05 for 5%
    private double discountPercent;  // e.g. 10 for 10%
    private double serviceChargeRate; // e.g. 0.10 for 10%

    public Bill(Order order, double taxRate, double discountPercent, double serviceChargeRate) {
        this.order = order;
        this.taxRate = taxRate;
        this.discountPercent = discountPercent;
        this.serviceChargeRate = serviceChargeRate;
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
}
