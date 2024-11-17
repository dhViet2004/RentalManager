package Interface;

import Classes.Host;

import java.util.ArrayList;
import java.util.List;

public class HostManager implements RentalManager<Host> {
    private List<Host> hosts = new ArrayList<>();

    @Override
    public boolean add(Host item) {
        hosts.add(item);
        return true;
    }

    @Override
    public void update(Host item) {
        for (int i = 0; i < hosts.size(); i++) {
            if (hosts.get(i).getId().equals(item.getId())) {
                hosts.set(i, item);
                return;
            }
        }
    }

    @Override
    public void remove(String id) {
        hosts.removeIf(host -> host.getId().equals(id));
    }

    @Override
    public Host getOne(String id) {
        for (Host host : hosts) {
            if (host.getId().equals(id)) {
                return host;
            }
        }
        return null;
    }

    @Override
    public List<Host> getAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public List<String> getAllIDs() {
        List<String> ids = new ArrayList<>();
        for (Host host : hosts) {
            ids.add(host.getId());
        }
        return ids;
    }

    @Override
    public List<Host> getAllByCustomerID(String customerID) {
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