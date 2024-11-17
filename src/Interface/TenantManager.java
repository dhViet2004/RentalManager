package Interface;

import Classes.Tenant;

import java.util.ArrayList;
import java.util.List;

public class TenantManager implements RentalManager<Tenant> {
    private List<Tenant> tenants = new ArrayList<>();

    @Override
    public boolean add(Tenant item) {
        tenants.add(item);
        return true;
    }

    @Override
    public void update(Tenant item) {
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getId().equals(item.getId())) {
                tenants.set(i, item);
                return;
            }
        }
    }

    @Override
    public void remove(String id) {
        tenants.removeIf(tenant -> tenant.getId().equals(id));
    }

    @Override
    public Tenant getOne(String id) {
        for (Tenant tenant : tenants) {
            if (tenant.getId().equals(id)) {
                return tenant;
            }
        }
        return null;
    }

    @Override
    public List<Tenant> getAll() {
        return new ArrayList<>(tenants);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Tenant tenant : tenants) {
            ids.add(tenant.getId());
        }
        return ids;
    }

    @Override
    public List<Tenant> getAllByCustomerID(String customerID) {
        // Phương thức này có thể được điều chỉnh theo yêu cầu cụ thể
        return new ArrayList<>();
    }

    @Override
    public void saveToFile(String fileName) {

    }


    @Override
    public void loadFromFile(String fileName) {
        // Thực hiện tải từ file
    }
}