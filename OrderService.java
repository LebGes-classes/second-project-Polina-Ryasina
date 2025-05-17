import java.util.*;

public class OrderService {
    private static List<Order> allOrders;

    public static void addToCart(Customer customer, List<Product> products) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Список доступных товаров:");
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("" + i + 1 + ". " + products.get(i).getName(), products.get(i).getSellCost());
        }
        System.out.print("Введите номер товара: ");
        int choice = -1;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Номер должен быть числом!");
            }
            if (choice > 0 & choice <= products.size()) {
                break;
            } else {
                System.out.println("Неверный ввод.");
            }
        }

        System.out.print("Введите количество: ");
        int quantity = -1;
        while (true) {
            try {
                quantity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Количество должно быть числом!");
            }
            if (quantity > 0 & quantity < 100) {
                break;
            } else {
                System.out.println("Невозможно заказать такое количество товара.");
            }
        }
        customer.getCart().addItem(products.get(choice), quantity);
    }

    public static String selectSalesPoint(List<SalesPoint> salesPoints) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Доступные пункты выдачи:");
        for (int i = 0; i < salesPoints.size(); i++) {
            System.out.printf("" + i + 1 + ". " + salesPoints.get(i).getAddress());
        }

        System.out.print("Введите номер пункта выдачи: ");
        int choice = -1;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Номер должен быть числом!");
            }
            if (choice > 0 & choice <= salesPoints.size()) {
                break;
            } else {
                System.out.println("Неверный ввод.");
            }
        }

        return salesPoints.get(choice).getId();
    }

    public static void placeOrder(Customer customer, List<SalesPoint> salesPoints) {
        List<OrderItem> cartItems = new ArrayList<>(customer.getCart().getItems());
        Order order = new Order(customer.getId(), cartItems, selectSalesPoint(salesPoints), customer.getOrderNumber());
        customer.getOrderHistory().add(order);
        customer.getCart().clear();
        allOrders.add(order);
        System.out.println("Заказ оформлен.");
    }

    public static Product getProductById(String productId, List<Product> products) {
        return products.stream().filter(p -> p.getId().equals(productId)).toList().getFirst();
    }

    public static void viewOrders(Customer customer) {
        for (Order order : customer.getOrderHistory()) {
            System.out.println("Заказ №" + order.getOrderNumber());
            for (OrderItem item : order.getItems()) {
                System.out.println("- " + item.getProduct().getName() + ", " + item.getQuantity());
            }
        }
    }



}