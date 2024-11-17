package Classes;

import java.util.Date;
import java.util.Objects;

public class Payment {
    private String paymentId;
    private Tenant tenant;
    private double amount;
    private Date date;
    private String paymentMethod;

    public Payment(String paymentMethod, Date date, double amount, Tenant tenant, String paymentId) {
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.amount = amount;
        this.tenant = tenant;
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(paymentId);
    }

    @Override
    public String toString() {
        return String.format("|%-15s|%-15s|%-15s|%-15s|%-30s|",
                "PaymentId=" + paymentId,
                "Tenant=" + tenant,
                "Amount=" + amount,
                "Date=" + date,
                "PaymentMethod=" + paymentMethod);
    }
}
