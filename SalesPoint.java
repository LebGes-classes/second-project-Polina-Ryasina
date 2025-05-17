import org.apache.commons.compress.archivers.ar.ArArchiveEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Дописать метод выдачи заказов

public class SalesPoint implements Serializable {
    private String id;
    private String city;
    private String address;
    private List<String> employeeIds;
    private List<Order> orderHistory;
    private IsActiveStatus status;
    private double income;
    private List<OrderItem> avaliableProducts;

    public SalesPoint(String city, String address) {
        this.id = MakeId.makeId();
        this.city = city;
        this.address = address;
        this.employeeIds = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.status = IsActiveStatus.ACTIVE;
        this.income = 0;
        this.avaliableProducts = new ArrayList<>();
    }

    public SalesPoint(String city, String address, ArrayList<String> employeeIds) {
        this.id = MakeId.makeId();
        this.city = city;
        this.address = address;
        this.employeeIds = employeeIds;
        this.orderHistory = new ArrayList<>();
        this.status = IsActiveStatus.ACTIVE;
        this.income = 0;
        this.avaliableProducts = new ArrayList<>();
    }

    public SalesPoint(String id, String city, String address, ArrayList<String> employeeIds, ArrayList<Order> orderHistory, IsActiveStatus status, int income, ArrayList<OrderItem> avaliableProducts) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.employeeIds = employeeIds;
        this.orderHistory = orderHistory;
        this.status = status;
        this.income = income;
        this.avaliableProducts = avaliableProducts;
    }

    public void addProduct(String productId, int quantity, List<Product> products) {
        avaliableProducts.add(new OrderItem(OrderService.getProductById(productId, products), quantity));
    }

    public void removeProduct(String productId, int quantity) {
        OrderItem item = avaliableProducts.stream().filter(i -> i.getProduct().getId().equals(productId)).toList().getFirst();
        if (item.getQuantity() > quantity) {
            item.decreaseQuantity(quantity);
        } else if (item.getQuantity() == quantity) {
            avaliableProducts.remove(item);
        } else {
            System.out.println("Невозможно вернуть указанное количество товара");
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

    // Добавить логику удаления пвз из списка доступных
    public void close() {
        status = IsActiveStatus.INACTIVE;
    }

    public void open() {
        status = IsActiveStatus.ACTIVE;
    }

    public void addOrderToHistory(Order order, double amount) {
        orderHistory.add(order);
        income += amount;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public IsActiveStatus getStatus() {
        return status;
    }

    public double getIncome() {
        return income;
    }

    public List<OrderItem> getAvaliableProducts() {
        return avaliableProducts;
    }
}