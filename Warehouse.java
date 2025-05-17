import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Warehouse implements Serializable {
    private String id;
    private String city;
    private String address;
    private List<StorageCell> cells;
    private List<String> employeeIds;
    private List<String> productsIds;
    private IsActiveStatus status;

    public Warehouse(String city, String address) {
        this.id = MakeId.makeId();
        this.city = city;
        this.address = address;
        this.cells = new ArrayList<>();
        this.employeeIds = new ArrayList<>();
        this.productsIds = new ArrayList<>();
        this.status = IsActiveStatus.ACTIVE;
    }

    public Warehouse(String city, String address, IsActiveStatus status) {
        this.id = MakeId.makeId();
        this.city = city;
        this.address = address;
        this.cells = new ArrayList<>();
        this.employeeIds = new ArrayList<>();
        this.productsIds = new ArrayList<>();
        this.status = status;
    }

    public Warehouse(String id, String city, String address, IsActiveStatus status) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.cells = new ArrayList<>();
        this.employeeIds = new ArrayList<>();
        this.productsIds = new ArrayList<>();
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<StorageCell> getCells() {
        return cells;
    }

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public List<String> getProductsIds() {
        return productsIds;
    }

    public IsActiveStatus getStatus() {
        return status;
    }

    public void addStorageCell(StorageCell cell) {
        cells.add(cell);
    }

    public void addProduct(String productId, int quantity) {
        StorageCell cell = cells.stream().filter(c -> c.getProductId().equals(productId)).toList().getFirst();
        if (cell != null) {
            int avaliableCapacity = (cell.getCapacity() - cell.getProductAmount());
            if (avaliableCapacity >= quantity) {
                cell.addProduct(productId, quantity);
            } else {
                cell.addProduct(productId, avaliableCapacity);
                int remains = quantity - avaliableCapacity;
                while (remains > 0) {
                    StorageCell newCell = new StorageCell(cell.getCapacity(), id, productId);
                    addStorageCell(newCell);
                    newCell.addProduct(productId, remains - cell.getCapacity());
                    remains -= cell.getCapacity();
                }
            }
        }
    }

    public void removeProduct(String productId, int quantity) {
        StorageCell cell = cells.stream().filter(c -> c.getProductId().equals(productId)).toList().getFirst();
        cell.removeProduct(quantity);
    }

    public int getProductQuantity(String productId) {
        if (productsIds.contains(productId)) {
            return cells.stream().filter(c -> c.getProductId().equals(productId)).toList().getFirst().getProductAmount();
        } else {
            return 0;
        }
    }

    public boolean hireEmployee(String employeeId) {
        if (!employeeIds.contains(employeeId)) {
            employeeIds.add(employeeId);
            return true;
        }
        return false;
    }

    public boolean fireEmployee(String employeeId) {
        if (employeeIds.contains(employeeId)) {
            employeeIds.remove(employeeId);
            return true;
        }
        return false;
    }

    // Добавить логику перемещения товаров
    public void close() {
        status = IsActiveStatus.INACTIVE;
    }

    public void open() { status = IsActiveStatus.ACTIVE; }

    public String toString() {
        return city + ", " + address;
    }
}