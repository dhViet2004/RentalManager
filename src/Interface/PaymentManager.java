package Interface;

import Classes.Payment;
import Classes.Tenant;
import DAO.PaymentDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PaymentManager implements RentalManager<Payment> {
    private List<Payment> payments = new ArrayList<>();
    private PaymentDAO paymentDAO = new PaymentDAO(); // Create PaymentDAO object to save and load from file
    private TenantManager tenantManager = new TenantManager();
    @Override
    public boolean add(Payment item) {
        Payment temp = new Payment(item.getPaymentMethod(), item.getDate(), item.getAmount(), item.getTenant(), item.getPaymentId());

        if (payments.contains(temp)) {
            System.out.println("Error: paymentId already exists: " + item.getPaymentId());
            return false;
        }

        // If paymentId does not exist, add to the list
        payments.add(item);
        System.out.println("Payment successfully added: " + item);
        return true;
    }

    @Override
    public void update(Payment item) {
        // Call update method from DAO to update payment and write back to file
        if (paymentDAO.update(item)) {
            System.out.println("Payment updated successfully!");
        } else {
            System.out.println("No payment found with paymentId: " + item.getPaymentId());
        }
    }

    @Override
    public void remove(String id) {
        boolean removed = payments.removeIf(payment -> payment.getPaymentId().equals(id));
        if (removed) {
            System.out.println("Payment with paymentId removed: " + id);
            saveToFile("src/File/payments.txt"); // Update data to file after removal
        } else {
            System.out.println("No payment found with paymentId: " + id);
        }
    }

    @Override
    public Payment getOne(String id) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(id)) {
                return payment;
            }
        }
        return null;
    }

    @Override
    public List<Payment> getAll() {
        return new ArrayList<>(payments);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Payment payment : payments) {
            ids.add(payment.getPaymentId());
        }
        return ids;
    }

    @Override
    public List<Payment> getAllByCustomerID(String customerID) {
        List<Payment> customerPayments = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.getTenant().getId().equals(customerID)) {
                customerPayments.add(payment);
            }
        }
        return customerPayments;
    }

    @Override
    public void saveToFile(String fileName) {
        try {
            // Call PaymentDAO to save the payment list to a file
            paymentDAO.writeToFile(payments);
            System.out.println("Successfully saved to file: " + fileName);
        } catch (Exception e) {
            System.out.println("Error while saving to file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fileName) {
        try {
            // Call PaymentDAO to load the payment list from a file
            payments = paymentDAO.readFromFile();
            if (payments.isEmpty()) {
                System.out.println("No payment data found in file: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error while reading from file: " + fileName);
            e.printStackTrace();
        }
    }

    public Payment inputPaymentData() {
        Scanner scanner = new Scanner(System.in);

        // Nhập paymentId
        String paymentId;
        while (true) {
            System.out.print("Enter paymentId: ");
            paymentId = scanner.nextLine();

            // Kiểm tra xem paymentId đã tồn tại hay chưa
            boolean exists = false;
            for (Payment p : payments) {
                if (p.getPaymentId().equals(paymentId)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Error: paymentId already exists. Please re-enter.");
            } else {
                break;
            }
        }

        // Nhập tenantId và tự động gán Tenant
        System.out.print("Enter tenantId: ");
        String tenantId = scanner.nextLine();
        tenantManager.loadFromFile("src/File/tenants.txt");
        Tenant tenant = tenantManager.getOne(tenantId); // Tìm tenant theo tenantId
        if (tenant != null) {
            System.out.println("Found tenant: " + tenant); // Hiển thị thông tin tenant tìm thấy
        } else {
            System.out.println("No tenant found with id: " + tenantId); // Nếu không tìm thấy tenant
        }

        // Nhập số tiền thanh toán
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine(); // Đọc dòng dư

        // Nhập phương thức thanh toán
        System.out.print("Enter payment method (e.g., Credit Card, Bank Transfer): ");
        String paymentMethod = scanner.nextLine();

        // Tạo đối tượng Payment với thông tin đã nhập
        Payment payment = new Payment(paymentMethod, new Date(), amount, tenant, paymentId);

        return payment; // Trả về đối tượng Payment đã tạo
    }

}
