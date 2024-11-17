package DAO;

import Classes.Payment;
import Classes.Tenant;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentDAO {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String FILE_PATH = "src/File/payments.txt";  // Đường dẫn đến tệp lưu trữ thanh toán

    // Convert Payment object to String for file writing
    private String convertPaymentToString(Payment payment) {
        return String.join(",",
                payment.getPaymentId(),
                payment.getTenant().getId(), // Only save Tenant ID
                String.valueOf(payment.getAmount()),
                dateFormat.format(payment.getDate()),
                payment.getPaymentMethod()
        );
    }

    // Convert String from file to Payment object
    private Payment convertStringToPayment(String line) throws ParseException {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            System.err.println("Invalid format: " + line);
            return null;
        }

        String paymentId = parts[0];
        String tenantId = parts[1]; // Only Tenant ID
        double amount = Double.parseDouble(parts[2]);
        Date date = dateFormat.parse(parts[3]);
        String paymentMethod = parts[4];

        // Create a Tenant with only the ID
        Tenant tenant = new Tenant(tenantId); // Create Tenant with just ID

        return new Payment(paymentMethod, date, amount, tenant, paymentId);
    }

    // Write a list of payments to a file (overwrite file content)
    public void writeToFile(List<Payment> payments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Payment payment : payments) {
                writer.write(convertPaymentToString(payment));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read payments from a file and return them as a list
    public List<Payment> readFromFile() {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Payment payment = convertStringToPayment(line);
                if (payment != null) {
                    payments.add(payment);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return payments;
    }
    // Update payment information in the list and write it back to the file
    public boolean update(Payment updatedPayment) {
        List<Payment> payments = readFromFile();  // Đọc danh sách thanh toán từ file

        boolean paymentFound = false;
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getPaymentId().equals(updatedPayment.getPaymentId())) {
                // Cập nhật thông tin thanh toán
                payments.set(i, updatedPayment);
                paymentFound = true;
                break;
            }
        }

        if (paymentFound) {
            // Ghi lại danh sách thanh toán đã cập nhật vào file
            writeToFile(payments);
            System.out.println("Payment updated successfully!");
            return true;
        } else {
            System.out.println("Payment not found!");
            return false;
        }
    }
    // Delete payment by paymentId and update the file
    public boolean delete(String paymentId) {
        List<Payment> payments = readFromFile(); // Đọc danh sách thanh toán từ file

        boolean paymentFound = false;
        Iterator<Payment> iterator = payments.iterator();
        while (iterator.hasNext()) {
            Payment payment = iterator.next();
            if (payment.getPaymentId().equals(paymentId)) {
                iterator.remove(); // Xóa payment khỏi danh sách
                paymentFound = true;
                break;
            }
        }

        if (paymentFound) {
            // Ghi lại danh sách thanh toán sau khi xóa vào file
            writeToFile(payments);
            System.out.println("Payment deleted successfully!");
            return true;
        } else {
            System.out.println("Payment not found!");
            return false;
        }
    }
}
