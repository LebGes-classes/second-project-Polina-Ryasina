import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String customerId;
    private List<OrderItem> items;
    private double totalCost;
    private OrderStatus status;
    private String salesPointId;
    private int orderNumber;

    public Order(String customerId, List<OrderItem> items, String salesPointId, int orderNumber) {
        this.id = MakeId.makeId();
        this.customerId = customerId;
        this.items = items;
        this.totalCost = calculateTotal();
        this.status = OrderStatus.PROCESSING;
        this.salesPointId = salesPointId;
        this.orderNumber = orderNumber;
    }

    public Order(String id, String customerId, List<OrderItem> items, String salesPointId, int orderNumber) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalCost = calculateTotal();
        this.status = OrderStatus.PROCESSING;
        this.salesPointId = salesPointId;
        this.orderNumber = orderNumber;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getSalesPointId() {
        return salesPointId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    private double calculateTotal() {
        return items.stream()
                .mapToDouble(OrderItem::calculateItemTotalCost)
                .sum();
    }

    public boolean updateStatus(OrderStatus newStatus) {
        if (status == OrderStatus.CANCELLED) {
            return false;
        } else {
            this.status = newStatus;
            return true;
        }
    }

    public void cancel() {
        if (status == OrderStatus.RECEIVED) {
            System.out.println("Забранный заказ нельзя отменить!");
        } else {
            this.status = OrderStatus.CANCELLED;
        }
    }

    public void returnItem(Product product, int quantity) {
        if ((status == OrderStatus.READY | status == OrderStatus.PROCESSING) & items.stream().anyMatch(item -> item.getProduct().equals(product))) {
            OrderItem item = items.stream().filter(i -> i.getProduct().equals(product)).toList().getFirst();
            totalCost -= item.calculateItemTotalCost();
            items.remove(item);
        } else if (status == OrderStatus.RECEIVED) {
            System.out.println("Вернуть товар можно, только если заказ не получен.");
        } else if (items.stream().noneMatch(item -> item.getProduct().equals(product))) {
            System.out.println("Товара нет в заказе!");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Заказ " + orderNumber + "\n"));
        sb.append(String.format("Статус: %s\n", status));
        sb.append("Состав заказа:\n");
        for (OrderItem item : items) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append(String.format("Итого: %.2f руб.", totalCost));
        return sb.toString();
    }
}