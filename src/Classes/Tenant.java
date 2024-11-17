package Classes;

import java.util.Date;
import java.util.List;

public class Tenant extends Person {

    private List<RentalAgreement> rentalAgreements;
    private List<Payment> paymentRecords;

    public Tenant(String fullName, String id, Date dateOfBirth, String contactInfo, List<RentalAgreement> rentalAgreements, List<Payment> paymentRecords) {
        super(fullName, id, dateOfBirth, contactInfo);
        this.rentalAgreements = rentalAgreements;
        this.paymentRecords = paymentRecords;
    }

    public Tenant(String id) {
        super(id);
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public List<Payment> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<Payment> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }




    @Override
    public String toString() {
        return "Tenant{" +
                "fullName='" + getFullName() + '\'' +
                ", id='" + getId() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", contactInfo='" + getContactInfo() + '\'' +
                ", rentalAgreements=" + (rentalAgreements != null ? rentalAgreements.size() + " rental agreements" : "no rental agreements") +
                ", paymentRecords=" + (paymentRecords != null ? paymentRecords.size() + " payment records" : "no payment records") +
                '}';
    }

}
