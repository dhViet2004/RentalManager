package Classes;

public class ResidentialProperty extends Property {
    private int numBedrooms;
    private boolean gardenAvailability;
    private boolean petFriendliness;

    // Constructor sử dụng PropertyStatus thay vì String cho status
    public ResidentialProperty(String propertyId, String address, double pricing, PropertyStatus status, int numBedrooms, boolean gardenAvailability, boolean petFriendliness) {
        super(propertyId, address, pricing, status);
        this.numBedrooms = numBedrooms;
        this.gardenAvailability = gardenAvailability;
        this.petFriendliness = petFriendliness;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public boolean isGardenAvailability() {
        return gardenAvailability;
    }

    public void setGardenAvailability(boolean gardenAvailability) {
        this.gardenAvailability = gardenAvailability;
    }

    public boolean isPetFriendliness() {
        return petFriendliness;
    }

    public void setPetFriendliness(boolean petFriendliness) {
        this.petFriendliness = petFriendliness;
    }

    @Override
    public String toString() {
        return "ResidentialProperty{" +
                "propertyId='" + getPropertyId() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", pricing=" + getPricing() +
                ", status=" + getStatus() + // Hiển thị status từ lớp cha
                ", numBedrooms=" + numBedrooms +
                ", gardenAvailability=" + gardenAvailability +
                ", petFriendliness=" + petFriendliness +
                '}';
    }
}
