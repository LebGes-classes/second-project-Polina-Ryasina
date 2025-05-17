import java.io.Serializable;

public class OrderItem implements Serializable {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            System.out.println("Количество должно быть положительным");
        } else {
            this.quantity += quantity;
        }
    }

    // Добавить в основной класс проверку, не стало ли товара 0 штук
    public boolean decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            System.out.println("Количество должно быть положительным");
            return false;
        } else if (this.quantity - quantity < 0) {
            System.out.println("Невозможно удалить введенное количество товара!");
            return false;
        } else {
            this.quantity -= quantity;
            return true;
        }
    }

    public double calculateItemTotalCost() {
        return product.getPurchaseCost() * quantity;
    }

    public String toString() {
        return product.getName() + " - " + quantity;
    }
}