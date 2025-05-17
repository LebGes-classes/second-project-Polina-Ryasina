import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable {
    private String id;
    private Cart cart;
    private int orderNumber;
    private List<Order> orderHistory;

    public Customer(String login, String password) {
        super(login, password);
        this.id = MakeId.makeId();
        this.orderHistory = new ArrayList<>();
        this.cart = new Cart(MakeId.makeId());
        this.orderNumber = 1;
    }

    public Customer(String id, String login, String password) {
        super(login, password);
        this.id = id;
        this.orderHistory = new ArrayList<>();
        this.cart = new Cart(MakeId.makeId());
        this.orderNumber = 1;
    }

    public String getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void deleteCart() {
        cart.clear();
    }

    public void addOrderToHistory(Order order)  {
        if (!orderHistory.contains(order)) {
            orderHistory.add(order);
        } else {
            System.out.println("Заказ уже в истории!");
        }
    }

    public void updateOrderNumber() {
        orderNumber++;
    }
}