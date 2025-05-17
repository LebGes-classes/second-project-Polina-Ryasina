import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart implements Serializable {
    private String buyerId;
    private List<OrderItem> items;
    private double cost;

    public Cart(String buyerId) {
        this.buyerId = buyerId;
        this.items = new ArrayList<>();
        this.cost = 0;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getCost() {
        return cost;
    }

    public void addItem(Product product, int quantity) {
        boolean isExist = items.stream().anyMatch(item -> item.getProduct().equals(product));
        if (isExist) {
            OrderItem existItem = items.stream().filter(item -> item.getProduct().equals(product)).toList().getFirst();
            existItem.increaseQuantity(quantity);
        } else {
            items.add(new OrderItem(product, quantity));
        }
        cost += product.getPurchaseCost() * quantity;
    }

    public void removeItem(Product product, int quantity) {
        boolean isExist = items.stream().anyMatch(item -> item.getProduct().equals(product));
        if (isExist) {
            OrderItem existItem = items.stream().filter(item -> item.getProduct().equals(product)).toList().getFirst();
            if (existItem.decreaseQuantity(quantity)) {
                if (existItem.getQuantity() == 0) {
                    items.remove(existItem);
                }
                cost -= product.getPurchaseCost() * quantity;
            }
        } else {
            System.out.println("Товар невозможно удалить, так как его нет в корзине.");
        }
    }

    public void clear() {
        items.clear();
        cost = 0;
    }

    public double calculateTotal() {
        return items.stream()
                .mapToDouble(OrderItem::calculateItemTotalCost)
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Корзина покупателя ID: ").append(buyerId).append("\n");
        items.forEach(item -> sb.append("- ").append(item).append("\n"));
        sb.append(String.format("Итого: %.2f руб.", calculateTotal()));
        return sb.toString();
    }
}