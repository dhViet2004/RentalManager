package Interface;

import Classes.RentalAgreement;

import java.util.ArrayList;
import java.util.List;

public class RentalAgreementManager implements RentalManager<RentalAgreement> {
    private List<RentalAgreement> agreements = new ArrayList<>();

    @Override
    public boolean add(RentalAgreement item) {
        agreements.add(item);
        return true;
    }

    @Override
    public void update(RentalAgreement item) {
        for (int i = 0; i < agreements.size(); i++) {
            if (agreements.get(i).getContractId().equals(item.getContractId())) {
                agreements.set(i, item);
                return;
            }
        }
    }

    @Override
    public void remove(String id) {
        agreements.removeIf(agreement -> agreement.getContractId().equals(id));
    }

    @Override
    public RentalAgreement getOne(String id) {
        for (RentalAgreement agreement : agreements) {
            if (agreement.getContractId().equals(id)) {
                return agreement;
            }
        }
        return null;
    }

    @Override
    public List<RentalAgreement> getAll() {
        return new ArrayList<>(agreements);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (RentalAgreement agreement : agreements) {
            ids.add(agreement.getContractId());
        }
        return ids;
    }

    @Override
    public List<RentalAgreement> getAllByCustomerID(String customerID) {
        // Phương thức này có thể được điều chỉnh theo yêu cầu cụ thể
        return new ArrayList<>();
    }

    @Override
    public void saveToFile(String fileName) {
        // Thực hiện lưu vào file
    }

    @Override
    public void loadFromFile(String fileName) {
        // Thực hiện tải từ file
    }
}