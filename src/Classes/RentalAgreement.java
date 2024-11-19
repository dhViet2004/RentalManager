package Classes;

import java.time.Period;
import java.util.List;
import java.util.Objects;

public class RentalAgreement implements Comparable<RentalAgreement> {
    private String contractId;
    private Owner owner;
    private Tenant mainTenant;
    private List<Tenant> subTenants;
    private Property rentedProperty;
    private List<Host> hosts;
    private RentalCycleType rentalCycle; // Thêm thuộc tính rentalCycle cho chu kỳ thuê
    private int duration;  // Dùng số lượng cho các đơn vị như ngày, tuần, tháng
    private String contractTerms;
    private double rentalFee;
    private RentalAgreementStatus status;

    // Enum định nghĩa trạng thái hợp đồng
    public enum RentalAgreementStatus {
        NEW, ACTIVE, COMPLETED;
    }

    // Enum định nghĩa chu kỳ thuê
    public enum RentalCycleType {
        DAILY, WEEKLY, FORTNIGHTLY, MONTHLY, YEARLY;
    }

    // Constructor cho RentalAgreement
    public RentalAgreement(String contractId,Owner owner ,Tenant mainTenant, List<Tenant> subTenants,
                           Property rentedProperty, List<Host> hosts, RentalCycleType rentalCycle,
                           int duration, String contractTerms, double rentalFee, RentalAgreementStatus status) {
        this.contractId = contractId;
        this.owner = owner;
        this.mainTenant = mainTenant;
        this.subTenants = subTenants;
        this.rentedProperty = rentedProperty;
        this.hosts = hosts;
        this.rentalCycle = rentalCycle;
        this.duration = duration;  // duration là số đơn vị (ngày, tuần, tháng, v.v.)
        this.contractTerms = contractTerms;
        this.rentalFee = rentalFee;
        this.status = status;
    }

    public RentalAgreement() {
    }

    // Getters và Setters
    public RentalCycleType getRentalCycle() {
        return rentalCycle;
    }

    public void setRentalCycle(RentalCycleType rentalCycle) {
        this.rentalCycle = rentalCycle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Tenant getMainTenant() {
        return mainTenant;
    }

    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public List<Tenant> getSubTenants() {
        return subTenants;
    }

    public void setSubTenants(List<Tenant> subTenants) {
        this.subTenants = subTenants;
    }

    public Property getRentedProperty() {
        return rentedProperty;
    }

    public void setRentedProperty(Property rentedProperty) {
        this.rentedProperty = rentedProperty;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public String getContractTerms() {
        return contractTerms;
    }

    public void setContractTerms(String contractTerms) {
        this.contractTerms = contractTerms;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(double rentalFee) {
        this.rentalFee = rentalFee;
    }

    public RentalAgreementStatus getStatus() {
        return status;
    }

    public void setStatus(RentalAgreementStatus status) {
        this.status = status;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    // Triển khai phương thức compareTo để sắp xếp theo rentalFee
    @Override
    public int compareTo(RentalAgreement other) {
        return Double.compare(this.rentalFee, other.rentalFee);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RentalAgreement that = (RentalAgreement) o;
        return Objects.equals(contractId, that.contractId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contractId);
    }

    @Override
    public String toString() {
        return "RentalAgreement{" +
                "contractId='" + contractId + '\'' +
                ", owner=" + owner.getFullName() +
                ", mainTenant=" + mainTenant.getFullName() +
                ", subTenants=" + subTenants +
                ", rentedProperty=" + rentedProperty +
                ", hosts=" + hosts +
                ", rentalCycle=" + rentalCycle +
                ", duration=" + duration +
                ", contractTerms='" + contractTerms + '\'' +
                ", rentalFee=" + rentalFee +
                ", status=" + status +
                '}';
    }
}
