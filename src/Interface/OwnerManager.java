package Interface;

import Classes.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerManager implements RentalManager<Owner> {
    private List<Owner> owners = new ArrayList<>();

    @Override
    public boolean add(Owner item) {
        owners.add(item);
        return true;
    }

    @Override
    public void update(Owner item) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(item.getId())) {
                owners.set(i, item);
                return;
            }
        }
    }

    @Override
    public void remove(String id) {
        owners.removeIf(owner -> owner.getId().equals(id));
    }

    @Override
    public Owner getOne(String id) {
        for (Owner owner : owners) {
            if (owner.getId().equals(id)) {
                return owner;
            }
        }
        return null;
    }

    @Override
    public List<Owner> getAll() {
        return new ArrayList<>(owners);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Owner owner : owners) {
            ids.add(owner.getId());
        }
        return ids;
    }

    @Override
    public List<Owner> getAllByCustomerID(String customerID) {
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