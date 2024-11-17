package Interface;

import java.util.List;

public interface RentalManager<T> {
    /**
     * Thêm một đối tượng mới vào hệ thống quản lý.
     * @param item đối tượng cần thêm.
     */
    boolean add(T item);

    /**
     * Cập nhật thông tin của một đối tượng trong hệ thống quản lý.
     * @param item đối tượng cần cập nhật.
     */
    void update(T item);

    /**
     * Xóa một đối tượng khỏi hệ thống quản lý dựa vào ID của đối tượng.
     * @param id ID của đối tượng cần xóa.
     */
    void remove(String id);

    /**
     * Lấy thông tin của một đối tượng cụ thể dựa vào ID.
     * @param id ID của đối tượng cần lấy thông tin.
     * @return đối tượng với ID tương ứng, hoặc null nếu không tìm thấy.
     */
    T getOne(String id);

    /**
     * Lấy tất cả các đối tượng trong hệ thống quản lý.
     * @return danh sách tất cả các đối tượng.
     */
    List<T> getAll();

    /**
     * Lấy tất cả các ID của các đối tượng trong hệ thống.
     * @return danh sách các ID của đối tượng.
     */
    List<String> getAllIDs();

    /**
     * Lấy tất cả các đối tượng dựa vào ID của khách hàng (Customer ID).
     * @param customerID ID của khách hàng.
     * @return danh sách các đối tượng thuộc về khách hàng với ID tương ứng.
     */
    List<T> getAllByCustomerID(String customerID);

    /**
     * Lưu dữ liệu của hệ thống quản lý vào một file.
     * @param fileName tên file cần lưu.
     */
    void saveToFile(String fileName);

    /**
     * Tải dữ liệu vào hệ thống quản lý từ một file.
     * @param fileName tên file cần tải.
     */
    void loadFromFile(String fileName);
}
