import java.util.ArrayList;
import java.util.List;

public class MovementService {
    private List<Warehouse> warehouses;
    private List<SalesPoint> salesPoints;
    private List<ProductMovement> movementHistory;

    public MovementService(List<Warehouse> warehouses, List<SalesPoint> salesPoints) {
        this.warehouses = warehouses;
        this.salesPoints = salesPoints;
        this.movementHistory = new ArrayList<>();
    }

    public boolean moveFromWarehouseToSalesPoint(String warehouseId, String salesPointId, String productId, int quantity, List<Product> products) {
        boolean isWarehouseExist = warehouses.stream().anyMatch(w -> w.getId().equals(warehouseId));
        boolean isSalesPointExist = salesPoints.stream().anyMatch(s -> s.getId().equals(salesPointId));

        if (isWarehouseExist & isSalesPointExist) {
            Warehouse warehouse = warehouses.stream().filter(w -> w.getId().equals(warehouseId)).toList().getFirst();
            SalesPoint salesPoint = salesPoints.stream().filter(s -> s.getId().equals(salesPointId)).toList().getFirst();
            if (checkProductAvailability(warehouse, productId, quantity)) {
                ProductMovement movement = new ProductMovement(warehouseId, salesPointId, productId, quantity, MovementType.DELIVERY);
                warehouse.removeProduct(productId, quantity);
                salesPoint.addProduct(productId, quantity, products);
                movementHistory.add(movement);
                return true;
            }
        }
        return false;
    }

    public boolean moveProductsBetweenWarehouses(String fromWarehouseId, String toWarehouseId, String productId, int quantity) {
        boolean isWarehouseFromExist = warehouses.stream().anyMatch(w -> w.getId().equals(fromWarehouseId));
        boolean isWarehouseToExist = warehouses.stream().anyMatch(s -> s.getId().equals(toWarehouseId));
        if (isWarehouseToExist & isWarehouseFromExist) {
            Warehouse fromWarehouse = warehouses.stream().filter(w -> w.getId().equals(fromWarehouseId)).toList().getFirst();
            Warehouse toWarehouse = warehouses.stream().filter(s -> s.getId().equals(toWarehouseId)).toList().getFirst();
            ProductMovement movement = new ProductMovement(fromWarehouseId, toWarehouseId, productId, quantity, MovementType.BETWEEN_WAREHOUSES);
            fromWarehouse.removeProduct(productId, quantity);
            toWarehouse.addProduct(productId, quantity);
            movementHistory.add(movement);
            return true;
        }
        return false;
    }

    public boolean moveFromSalesPointToWarehouse(String salesPointId, String warehouseId, String productId, int quantity) {
        boolean isWarehouseExist = warehouses.stream().anyMatch(w -> w.getId().equals(warehouseId));
        boolean isSalesPointExist = salesPoints.stream().anyMatch(s -> s.getId().equals(salesPointId));

        if (isWarehouseExist & isSalesPointExist) {
            Warehouse warehouse = warehouses.stream().filter(w -> w.getId().equals(warehouseId)).toList().getFirst();
            SalesPoint salesPoint = salesPoints.stream().filter(s -> s.getId().equals(salesPointId)).toList().getFirst();
            ProductMovement movement = new ProductMovement(salesPointId, warehouseId, productId, quantity, MovementType.RETURNING);
            salesPoint.removeProduct(productId, quantity);
            warehouse.addProduct(productId, quantity);
            movementHistory.add(movement);
            return true;
        }
        return false;
    }


    public boolean checkProductAvailability(Warehouse warehouse, String productId, int quantity) {
        return warehouse.getProductQuantity(productId) >= quantity;
    }

    public List<ProductMovement> getMovementHistoryForProduct(String productId) {
        return movementHistory.stream()
                .filter(m -> m.getProductId().equals(productId))
                .toList();
    }

    public List<ProductMovement> getMovementHistoryForWarehouse(String warehouseId) {
        return movementHistory.stream()
                .filter(m -> m.getFromLocationId().equals(warehouseId) || m.getToLocationId().equals(warehouseId)).toList();
    }
}