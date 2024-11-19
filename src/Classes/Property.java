package Classes;

import java.util.Objects;

public abstract class Property {
    private String propertyId;
    private String address;
    private double pricing;
    private PropertyStatus status;

    // Enum định nghĩa các trạng thái của Property
    public enum PropertyStatus {
        AVAILABLE, RENTED, UNDER_MAINTENANCE;
    }

    public Property(String id) {
        this.propertyId = id;
    }

    public Property(String propertyId, String address, double pricing, PropertyStatus status) {
        this.propertyId = propertyId;
        this.address = address;
        this.pricing = pricing;
        this.status = status;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public void setStatus(PropertyStatus status) {
        this.status = status;
    }

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(propertyId, property.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(propertyId);
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyId='" + propertyId + '\'' +
                ", address='" + address + '\'' +
                ", pricing=" + pricing +
                ", status=" + status +
                '}';
    }
}
