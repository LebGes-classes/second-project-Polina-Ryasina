import java.util.*;

public class AuthService {
    public static void register(List<Manager> managers, List<Customer> customers) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Зарегистрироваться как покупатель");
        System.out.println("2. Зарегистрироваться как менеджер");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.print("Введите логин: ");
            String login = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            customers.add(new Customer(login, password));
            System.out.println("Покупатель зарегистрирован.");
        } else if (choice.equals("2")) {
            System.out.print("Введите логин: ");
            String login = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.print("Введите свое ФИО: ");
            String fullName = scanner.nextLine();
            managers.add(new Manager(login, password, fullName));
            System.out.println("Менеджер зарегистрирован.");
        } else {
            System.out.println("Неверный ввод.");
        }
    }

    public static Customer loginCustomer(List<Customer> customers) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        for (Customer customer : customers) {
            if (customer.getLogin().equals(login) && customer.checkPassword(password)) {
                return customer;
            }
        }
        System.out.println("Неверные учетные данные.");
        return null;
    }

    public static Manager loginManager(List<Manager> managers) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        for (Manager manager : managers) {
            if (manager.getLogin().equals(login) && manager.checkPassword(password)) {
                return manager;
            }
        }
        System.out.println("Неверные учетные данные.");
        return null;
    }
}