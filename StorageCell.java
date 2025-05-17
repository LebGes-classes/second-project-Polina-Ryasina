import java.io.Serializable;

public class StorageCell implements Serializable {
    private String id;
    private int capacity;
    private int productAmount;
    private String warehouseId;
    private String productId;

    // Конструктор с 3 параметрами для создания пустой ячейки без привязки товара
    public StorageCell(int capacity, String warehouseId) {
        this.id = MakeId.makeId();
        this.capacity = capacity;
        this.productAmount = 0;
        this.warehouseId = warehouseId;
        this.productId = null;
    }

    // Конструктор с 4 параметрами для создания пустой ячейки с привязкой товара
    public StorageCell(int capacity, String warehouseId, String productId) {
        this.id = MakeId.makeId();
        this.capacity = capacity;
        this.productAmount = 0;
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    // Конструктор со всеми параметрами для создания непустой ячейки
    public StorageCell(int capacity, int productAmount, String warehouseId, String productId) {
        this.id = MakeId.makeId();
        this.capacity = capacity;
        this.productAmount = productAmount;
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    public StorageCell(String id, int capacity, int productAmount, String warehouseId, String productId) {
        this.id = id;
        this.capacity = capacity;
        this.productAmount = productAmount;
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getProductId() {
        return productId;
    }

    // сеттеры на случай полной смены товара в ячейке
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Добавление товара в ячейку
    public boolean addProduct(String productId, int quantity) {
        if (this.productId == null && quantity <= capacity) { // Проверяем, что к ячейке не привязан товар и желаемое количество входит в ячейку
            this.productId = productId;
            productAmount = quantity;
            System.out.println("Товар добавлен!");
            return true;
        } else if (this.productId == null & quantity > capacity) { // Проверяем, что к ячейке не привязан товар, но введенное количество больше вместимости
            return false;
        } else if (this.productId != null & this.productId.equals(productId) & (productAmount + quantity) <= capacity) { // Проверяем, что товар совпадает с привязанным к ячейке товаром и введенное количество вмещается в ячейку
            productAmount += quantity;
            System.out.println("Товар добавлен!");
            return true;
        } else {
            System.out.println("Не удалось добавить товар.");
            return false;
        }
    }

    // Удаление товара (в основном классе выводим список всех ячеек, пользователь выбирает нужную и пишет количество товара на удаление для выбранной ячейки)
    public boolean removeProduct(int quantity) {
        if (quantity <= productAmount & quantity >= 0) {
            productAmount -= quantity;
            return true;
        } else {
            System.out.println("Невозможно удалить введенное количество товара!");
            return false;
        }
    }
}