import java.util.ArrayList;
import java.util.List;

public class WarehouseEmployee extends Employee {
    private String warehouseId;
    private List<Order> orderHistory;

    public WarehouseEmployee(String fullName, String warehouseId, String managerId) {
        super(fullName, managerId);
        this.warehouseId = warehouseId;
        this.orderHistory = new ArrayList<>();
    }

    public WarehouseEmployee(String id, String fullName, String warehouseId, String managerId) {
        super(id, fullName, managerId);
        this.warehouseId = warehouseId;
        this.orderHistory = new ArrayList<>();
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}