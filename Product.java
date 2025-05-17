import java.io.Serializable;

public class Product implements Serializable {

    private String id;
    private String name;
    private double sellCost;
    private double purchaseCost;
    private String producerId;

    // Конструктор cо всеми параметрами
    public Product(String name, double sellCost, double purchaseCost, String producerId) {
        this.id = MakeId.makeId();
        this.name = name;
        this.sellCost = sellCost;
        this.purchaseCost = purchaseCost;
        this.producerId = producerId;
    }

    public Product(String id, String name, double sellCost, double purchaseCost, String producerId) {
        this.id = id;
        this.name = name;
        this.sellCost = sellCost;
        this.purchaseCost = purchaseCost;
        this.producerId = producerId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSellCost() {
        return sellCost;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public String getProducerId() {
        return producerId;
    }

    // Изменение цены закупки
    public void updateSellCost(double newCost) {
        if (newCost <= 0) {
            System.out.println("Цена должна быть положительной!");
        }
        this.sellCost = newCost;
    }

    // Изменение цены продажи
    public void updatePurchaseCost(double newCost) {
        if (newCost <= 0) {
            System.out.println("Цена должна быть положительной!");
        }
        this.purchaseCost = newCost;
    }

    // Информация о товаре
    @Override
    public String toString() {
        return name + ":\n" + "id: " + id + "\ncost: " + purchaseCost;
    }
}