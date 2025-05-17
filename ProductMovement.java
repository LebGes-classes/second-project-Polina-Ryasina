public class ProductMovement {
    private String fromLocationId;
    private String toLocationId;
    private String productId;
    private int quantity;
    private MovementType movementType;

    public ProductMovement(String fromLocationId, String toLocationId, String productId, int quantity, MovementType movementType) {
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.productId = productId;
        this.quantity = quantity;
        this.movementType = movementType;
    }

    public String getFromLocationId() {
        return fromLocationId;
    }

    public String getToLocationId() {
        return toLocationId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public MovementType getMovementType() {
        return movementType;
    }
}