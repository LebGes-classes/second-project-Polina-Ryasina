import java.io.Serializable;
import java.util.List;

public class Producer implements Serializable {
    private String id;
    private String name;
    private List<String> productsIds;

    // Конструктор с параметрами
    public Producer(String name, List<String> productsIds) {
        this.id = MakeId.makeId();
        this.name = name;
        this.productsIds = productsIds;
    }

    public Producer(String id, String name, List<String> productsIds) {
        this.id = id;
        this.name = name;
        this.productsIds = productsIds;
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getProductsIds() {
        return productsIds;
    }

    // Добавление нового товара
    public void addProduct(String productId) {
        if (productsIds.contains(productId)) {
            System.out.println("Товар уже есть");
        } else {
            productsIds.add(productId);
            System.out.println("Товар добавлен!");
        }
    }

    // Удаление товара через переданный id (в основном коде выводим список)
    public void deleteProduct(String productId) {
        productsIds.remove(productId);
        System.out.println("Товар удален!");
    }

    @Override
    public String toString() {
        return name + ", id=" + id;
    }
}