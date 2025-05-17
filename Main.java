import java.util.*;

public class Main {
    private static List<Warehouse> warehouses = new ArrayList<>();
    private static List<SalesPoint> salesPoints = new ArrayList<>();
    private static List<Product> products = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static List<Producer> producers = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Manager> managers = new ArrayList<>();
    private static List<WarehouseEmployee> warehouseEmployees = new ArrayList<>();
    private static List<SalesPointEmployee> salesPointEmployees = new ArrayList<>();
    private static MovementService movementService = new MovementService(warehouses, salesPoints);


    public static void main(String[] args) {
        initializeTestData();
        AuthMenu();
    }

    private static void AuthMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        // Главное меню авторизации/регистрации
        while (flag) {
            System.out.println("\n=== Система управления товарооборотом ===");
            System.out.println("1. Вход");
            System.out.println("2. Регистрация");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loginMenu();
                    break;
                case "2":
                    AuthService.register(managers, customers);
                    break;
                case "3":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void loginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Вход в систему ===");
        System.out.println("1. Вход как покупатель");
        System.out.println("2. Вход как менеджер");
        System.out.print("Выберите пункт: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            Customer customer = AuthService.loginCustomer(customers);
            if (customer != null) {
                customerMenu(customer);
            }
        } else if (choice.equals("2")) {
            Manager manager = AuthService.loginManager(managers);
            if (manager != null) {
                managerMenu(manager);
            }
        } else {
            System.out.println("Неверный ввод!");
        }
    }

    private static void customerMenu(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Меню покупателя ===");
            System.out.println("1. Просмотреть товары");
            System.out.println("2. Добавить товар в корзину");
            System.out.println("3. Просмотреть корзину");
            System.out.println("4. Оформить заказ");
            System.out.println("5. Просмотреть историю заказов");
            System.out.println("6. Выйти из аккаунта");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewProducts();
                    break;
                case "2":
                    OrderService.addToCart(customer, products);
                    break;
                case "3":
                    System.out.println(customer.getCart());
                    break;
                case "4":
                    OrderService.placeOrder(customer, salesPoints);
                    break;
                case "5":
                    OrderService.viewOrders(customer);
                    break;
                case "6":
                    flag = false;
                    AuthMenu();
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void managerMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Меню менеджера ===");
            System.out.println("1. Управление сотрудниками");
            System.out.println("2. Управление товарами");
            System.out.println("3. Управление складами");
            System.out.println("4. Управление пунктами продаж");
            System.out.println("5. Просмотр аналитики");
            System.out.println("6. Выйти из аккаунта");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    employeeManagementMenu(manager);
                    break;
                case "2":
                    productManagementMenu(manager);
                    break;
                case "3":
                    warehouseManagementMenu(manager);
                    break;
                case "4":
                    salesPointManagementMenu(manager);
                    break;
                case "5":
                    analyticsMenu(manager);
                    break;
                case "6":
                    flag = false;
                    AuthMenu();
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void employeeManagementMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Управление сотрудниками ===");
            System.out.println("1. Нанять работника склада");
            System.out.println("2. Уволить работника склада");
            System.out.println("3. Нанять работника пункта продаж");
            System.out.println("4. Уволить работника пункта продаж");
            System.out.println("5. Назначить менеджера");
            System.out.println("6. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    hireWarehouseEmployee(manager);
                    break;
                case "2":
                    fireWarehouseEmployee(manager);
                    break;
                case "3":
                    hireSalesPointEmployee(manager);
                    break;
                case "4":
                    fireSalesPointEmployee(manager);
                    break;
                case "5":
                    assignManager(manager);
                    break;
                case "6":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void hireWarehouseEmployee(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        int choice = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice < openWarehouses.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        System.out.print("Введите ФИО сотрудника: ");
        String employeeFullName = scanner.nextLine();

        WarehouseEmployee employee = new WarehouseEmployee(employeeFullName, openWarehouses.get(choice).getId(), manager.getId());
        openWarehouses.get(choice).hireEmployee(employee.getId());
        manager.addSubordinate(employee);
    }

    private static void fireWarehouseEmployee(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flagW = true;

        int w = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(wh -> wh.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flagW) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                w = scanner.nextInt() - 1;
                if (w > 0 & w <= openWarehouses.size()) {
                    flagW = false;
                } else {
                    System.out.println("Неверный ввод.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        boolean flagE = true;
        Warehouse warehouse = openWarehouses.get(w);

        List<WarehouseEmployee> wEmployees = warehouseEmployees.stream().filter(e -> (e.getWarehouseId().equals(warehouse.getId()) & e.getManagerId().equals(manager.getId()) & e.getStatus() == EmployeeStatus.HIRED)).toList();

        if (!wEmployees.isEmpty()) {

            int e = -1;

            while (flagE) {
                System.out.println("Сотрудники в подчинении: ");
                for (int i = 0; i < wEmployees.size(); i++) {
                    System.out.println("" + i + 1 + ". " + wEmployees.get(i));
                }
                System.out.print("Выберите номер сотрудника: ");
                try {
                    e = scanner.nextInt() - 1;
                    if (e >= 0 & e <= wEmployees.size()) {
                        flagE = false;
                    } else {
                        System.out.println("Неверный ввод.");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Номер должен быть числом!");
                }
                scanner.nextLine();
            }

            WarehouseEmployee employee = wEmployees.get(e);
            warehouse.fireEmployee(employee.getId());
            manager.fireSubordinate(employee);
        } else {
            System.out.println("Нет сотрудников в подчинении на выбранном складе.");
        }
    }

    private static void hireSalesPointEmployee(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        int choice = -1;
        List<SalesPoint> openSalesPoints = salesPoints.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < openSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + openSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openSalesPoints.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        System.out.print("Введите ФИО сотрудника: ");
        String employeeFullName = scanner.nextLine();

        SalesPointEmployee employee = new SalesPointEmployee(employeeFullName, openSalesPoints.get(choice).getId(), manager.getId());
        openSalesPoints.get(choice).hireEmployee(employee.getId());
        manager.addSubordinate(employee);
    }

    private static void fireSalesPointEmployee(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flagS = true;

        int s = -1;
        List<SalesPoint> openSalesPoints = salesPoints.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flagS) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < openSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + openSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                s = scanner.nextInt() - 1;
                if (s >= 0 & s <= openSalesPoints.size()) {
                    flagS = false;
                } else {
                    System.out.println("Неверный ввод.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        boolean flagE = true;
        SalesPoint salesPoint = openSalesPoints.get(s);

        List<SalesPointEmployee> sEmployees = salesPointEmployees.stream().filter(e -> (e.getSalesPointId().equals(salesPoint.getId()) & e.getManagerId().equals(manager.getId()))).toList();

        if (!sEmployees.isEmpty()) {

            int e = -1;

            while (flagE) {
                System.out.println("Сотрудники в подчинении: ");
                for (int i = 0; i < sEmployees.size(); i++) {
                    System.out.println("" + i + 1 + ". " + sEmployees.get(i));
                }
                System.out.print("Выберите номер сотрудника: ");
                try {
                    e = scanner.nextInt() - 1;
                    if (e >= 0 & e <= sEmployees.size()) {
                        flagE = false;
                    } else {
                        System.out.println("Неверный ввод.");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Номер должен быть числом!");
                }
                scanner.nextLine();
            }

            SalesPointEmployee employee = sEmployees.get(e);
            salesPoint.fireEmployee(employee.getId());
            manager.fireSubordinate(employee);
        } else {
            System.out.println("Нет сотрудников в подчинении на выбранном пункте продаж.");
        }
    }

    private static void assignManager(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("1. Сменить менеджера у работника склада");
            System.out.println("2. Сменить менеджера у работника пункта продаж");
            System.out.println("3. Назад");
            System.out.print("Выберите пункт: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    List<WarehouseEmployee> wEmployees = warehouseEmployees.stream().filter(e -> e.getManagerId().equals(manager.getId()) & e.getStatus() == EmployeeStatus.HIRED).toList();
                    if (!wEmployees.isEmpty()) {

                        boolean flagE = true;
                        int e = -1;

                        while (flagE) {
                            System.out.println("Сотрудники: ");
                            for (int i = 0; i < wEmployees.size(); i++) {
                                System.out.println("" + i + 1 + ". " + wEmployees.get(i));
                            }
                            System.out.print("Выберите номер сотрудника: ");

                            try {
                                e = scanner.nextInt() - 1;
                                if (e >= 0 & e <= wEmployees.size()) {
                                    flagE = false;
                                } else {
                                    System.out.println("Неверный ввод.");
                                }
                            } catch (InputMismatchException ex) {
                                System.out.println("Номер должен быть числом!");
                            }
                            scanner.nextLine();
                        }



                        boolean flagM = true;
                        int m = -1;

                        while (flagM) {
                            System.out.println("Менеджеры: ");
                            for (int i = 0; i < managers.size(); i++) {
                                System.out.println("" + i + 1 + ". " + managers.get(i));
                            }
                            System.out.print("Выберите номер менеджера: ");

                            try {
                                m = scanner.nextInt() - 1;
                                if (m >= 0 & m <= managers.size()) {
                                    flagM = false;
                                } else {
                                    System.out.println("Неверный ввод.");
                                }
                            } catch (InputMismatchException ex) {
                                System.out.println("Номер должен быть числом!");
                            }
                            scanner.nextLine();
                        }

                        WarehouseEmployee employee = wEmployees.get(e);
                        Manager newManager = managers.get(m);
                        employee.updateManagerId(newManager.getId());
                        manager.changeSubordinatesManager(employee, newManager);
                    } else {
                        System.out.println("Нет сотрудников склада, у которых можно сменить менеджера.");
                    }
                    break;
                case "2":
                    List<SalesPointEmployee> sEmployees = salesPointEmployees.stream().filter(e -> e.getManagerId().equals(manager.getId()) & e.getStatus() == EmployeeStatus.HIRED).toList();
                    if (!sEmployees.isEmpty()) {

                        boolean flagE = true;
                        int e = -1;

                        while (flagE) {
                            System.out.println("Сотрудники: ");
                            for (int i = 0; i < sEmployees.size(); i++) {
                                System.out.println("" + i + 1 + ". " + sEmployees.get(i));
                            }
                            System.out.print("Выберите номер сотрудника: ");

                            try {
                                e = scanner.nextInt() - 1;
                                if (e >= 0 & e <= sEmployees.size()) {
                                    flagE = false;
                                } else {
                                    System.out.println("Неверный ввод.");
                                }

                            } catch (InputMismatchException ex) {
                                System.out.println("Номер должен быть числом!");
                            }
                            scanner.nextLine();
                        }



                        boolean flagM = true;
                        int m = -1;

                        while (flagM) {
                            System.out.println("Менеджеры: ");
                            for (int i = 0; i < managers.size(); i++) {
                                System.out.println("" + i + 1 + ". " + managers.get(i));
                            }
                            System.out.print("Выберите номер менеджера: ");
                            try {
                                m = scanner.nextInt() - 1;
                                if (m >= 0 & m <= managers.size()) {
                                    flagM = false;
                                } else {
                                    System.out.println("Неверный ввод.");
                                }

                            } catch (InputMismatchException ex) {
                                System.out.println("Номер должен быть числом!");
                            }
                            scanner.nextLine();
                        }

                        SalesPointEmployee employee = sEmployees.get(e);
                        Manager newManager = managers.get(m);
                        employee.updateManagerId(newManager.getId());
                        manager.changeSubordinatesManager(employee, newManager);
                    } else {
                        System.out.println("Нет сотрудников пункта продаж, у которых можно сменить менеджера.");
                    }
                    break;
                case "3":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод.");
                    break;
            }
        }
    }

    private static void productManagementMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Управление товарами ===");
            System.out.println("1. Добавить товар");
            System.out.println("2. Удалить товар");
            System.out.println("3. Переместить товар в пункт продаж");
            System.out.println("4. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    boolean addFlag = true;
                    while (addFlag) {
                        System.out.println("1. Добавить товар");
                        System.out.println("2. Вернуться назад");
                        String addChoice = scanner.nextLine();
                        if (addChoice.equals("1")) {
                            System.out.print("Название: ");
                            String name = scanner.nextLine();
                            double sell = -1.0;
                            while (true) {
                                System.out.print("Цена продажи: ");
                                try {
                                    sell = Double.parseDouble(scanner.nextLine().trim());
                                } catch (InputMismatchException e) {
                                    System.out.println("Цена должна быть числом!");
                                }
                                if (sell > 0) {
                                    break;
                                } else {
                                    System.out.println("Неверный ввод.");
                                }
                            }
                            double purchase = -1;
                            while (true) {
                                System.out.print("Цена закупки: ");
                                try {
                                    purchase = Double.parseDouble(scanner.nextLine().trim());
                                } catch (InputMismatchException e) {
                                    System.out.println("Цена должна быть числом!");
                                }
                                if (purchase > 0) {
                                    break;
                                } else {
                                    System.out.println("Неверный ввод.");
                                }
                            }

                            System.out.print("Производитель: ");
                            String producer = scanner.nextLine();

                            boolean isExist = producers.stream().anyMatch(p -> p.getName().equalsIgnoreCase(producer));
                            Producer newProducer;

                            if (isExist) {
                                newProducer = producers.stream()
                                        .filter(p -> p.getName().equalsIgnoreCase(scanner.nextLine())).toList().getFirst();
                            } else {
                                newProducer = new Producer(producer, new ArrayList<>());
                            }

                            Product product = new Product(name, sell, purchase, newProducer.getId());
                            products.add(product);
                            newProducer.addProduct(product.getId());
                            System.out.println("Товар добавлен!");

                        } else if (addChoice.equals("2")) {
                            addFlag = false;
                        } else {
                            System.out.println("Неверный ввод.");
                        }
                    }
                    break;
                case "2":
                    removeProduct(manager);
                    break;
                case "3":
                    moveProductToSalesPoint(manager);
                    break;
                case "4":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод!");
                    break;
            }
        }
    }

    private static void removeProduct(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;



        int p = -1;
        while (flag) {

            try {
                System.out.println("Товары: ");
                for (int i = 0; i < products.size(); i++) {
                    System.out.println("" + i + 1 + ". " + products.get(i));
                }
                System.out.print("Выберите номер товара: ");

                p = scanner.nextInt() - 1;
                if (p >= 0 & p <= products.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException ex) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        products.remove(p);
        System.out.println("Товар удален!");
    }

    private static void moveProductToSalesPoint(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flagW = true;
        int w = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(wh -> wh.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flagW) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                w = scanner.nextInt() - 1;
                if (w >= 0 & w <= openWarehouses.size()) {
                    flagW = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        Warehouse warehouse = openWarehouses.get(w);
        List<Product> wProducts = products.stream().filter(p -> warehouse.getProductsIds().contains(p.getId())).toList();

        boolean flagP = true;
        int p = -1;
        while (flagP) {
            System.out.println("Доступные товары: ");
            for (int i = 0; i < wProducts.size(); i++) {
                System.out.println("" + (i+1) + ". " + wProducts.get(i));
            }
            System.out.print("Выберите номер товара: ");
            try {
                p = scanner.nextInt() - 1;
                if (p >= 0 & p <= wProducts.size()) {
                    flagP = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        boolean flagQ = true;
        int quantity = 0;
        while (flagQ) {
            System.out.print("Введите количество: ");
            try {
                quantity = scanner.nextInt() - 1;

                if (quantity > 0 & quantity <= 100) {
                    flagQ = false;
                } else {
                    System.out.println("Более 100 товара заказать нельзя.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Количество должно быть числом!");
            }
            scanner.nextLine();
        }

        boolean flagS = true;
        int s = -1;
        while (flagS) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < salesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + salesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                s = scanner.nextInt() - 1;
                if (s >= 0 & s <= salesPoints.size()) {
                    flagS = false;
                } else {
                    System.out.println("Введен неверный номер.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        movementService.moveFromWarehouseToSalesPoint(warehouse.getId(), salesPoints.get(s).getId(), wProducts.get(p).getId(), quantity, products);
    }

    private static void warehouseManagementMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Управление складами ===");
            System.out.println("1. Закрыть склад");
            System.out.println("2. Открыть склад");
            System.out.println("3. Добавить ячейку хранения");
            System.out.println("4. Просмотреть информацию о складе");
            System.out.println("5. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    closeWarehouse(manager);
                    break;
                case "2":
                    openWarehouse(manager);
                    break;
                case "3":
                    addStorageCell(manager);
                    break;
                case "4":
                    viewWarehouseInfo(manager);
                    break;
                case "5":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void closeWarehouse(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flagW = true;
        int w = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(wh -> wh.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flagW) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                w = scanner.nextInt() - 1;
                if (w >= 0 & w <= openWarehouses.size()) {
                    flagW = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        Warehouse warehouseToClose = openWarehouses.get(w);
        List<Warehouse> availableWarehouses = warehouses.stream()
                .filter(wh -> wh.getStatus() == IsActiveStatus.ACTIVE && !wh.getId().equals(warehouseToClose.getId()))
                .toList();
        boolean flag = true;
        if (availableWarehouses.isEmpty()) {
            System.out.println("Нет доступных складов для перемещения товаров! Невозможно закрыть склад.");
        } else {
            for (String productId : warehouseToClose.getProductsIds()) {
                int quantity = warehouseToClose.getProductQuantity(productId);
                if (!movementService.moveProductsBetweenWarehouses(warehouseToClose.getId(), availableWarehouses.getFirst().getId(), productId, quantity)) {
                    System.out.println("Ошибка при перемещении товара " + OrderService.getProductById(productId, products).getName());
                    flag = false;
                }
            }
            if (flag) {
                warehouseToClose.close();
                System.out.println("Склад закрыт. Все товары перемещены.");
            } else {
                System.out.println("Не удалось закрыть склад.");
            }
        }
    }

    private static void openWarehouse(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flagW = true;
        int w = -1;
        List<Warehouse> closedWarehouses = warehouses.stream().filter(wh -> wh.getStatus() == IsActiveStatus.INACTIVE).toList();
        while (flagW) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < closedWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + closedWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                w = scanner.nextInt() - 1;
                if (w >= 0 & w <= closedWarehouses.size()) {
                    flagW = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }
        closedWarehouses.get(w).open();
    }

    private static void addStorageCell(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        int choice = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openWarehouses.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        int capacity = 0;
        boolean flagC = true;
        while (flagC) {
            try {
                System.out.print("Введите вместимость ячейки: ");
                capacity = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException e) {
                System.out.println("Вместимость должна быть числом!");
            }

            if (capacity > 0 & capacity < 1000) {
                flagC = false;
            } else {
                System.out.println("Неверный ввод.");
            }
        }

        boolean flagP = true;
        int p = -1;
        while (flagP) {
            System.out.println("Доступные товары: ");
            for (int i = 0; i < products.size(); i++) {
                System.out.println("" + (i+1) + ". " + products.get(i));
            }
            System.out.print("Выберите номер товара: ");
            try {
                p = scanner.nextInt() - 1;
                if (p >= 0 & p <= products.size()) {
                    flagP = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        StorageCell cell = new StorageCell(capacity, openWarehouses.get(choice).getId(), products.get(p).getId());
        openWarehouses.get(choice).addStorageCell(cell);
    }

    private static void viewWarehouseInfo(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        int choice = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openWarehouses.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        Warehouse w = openWarehouses.get(choice);
        System.out.println("Склад: ");
        System.out.println("Город: " + w.getCity());
        System.out.println("Адрес: " + w.getAddress());
        System.out.println("Количество ячеек: " + w.getCells().size());
        System.out.println("Количество сотрудников: " + w.getEmployeeIds().size());
    }

    private static void salesPointManagementMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Управление пунктами продаж ===");
            System.out.println("1. Закрыть пункт продаж");
            System.out.println("2. Открыть пункт продаж");
            System.out.println("3. Просмотреть информацию о пункте продаж");
            System.out.println("4. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    closeSalesPoint(manager);
                    break;
                case "2":
                    openSalesPoint(manager);
                    break;
                case "3":
                    viewSalesPointInfo(manager);
                    break;
                case "4":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void closeSalesPoint(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        int choice = -1;
        List<SalesPoint> openSalesPoints = salesPoints.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < openSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + openSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openSalesPoints.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        SalesPoint salesPointToClose = openSalesPoints.get(choice);
        List<Warehouse> availableWarehouses = warehouses.stream()
                .filter(wh -> wh.getStatus() == IsActiveStatus.ACTIVE).toList();
        boolean flagM = true;
        if (availableWarehouses.isEmpty()) {
            System.out.println("Нет доступных складов для перемещения товаров! Невозможно закрыть пункт продаж.");
        } else {
            for (OrderItem item : salesPointToClose.getAvaliableProducts()) {
                int quantity = item.getQuantity();
                if (!movementService.moveFromSalesPointToWarehouse(salesPointToClose.getId(), availableWarehouses.getFirst().getId(), item.getProduct().getId(), quantity)) {
                    System.out.println("Ошибка при перемещении товара " + item.getProduct().getName());
                    flagM = false;
                }
            }
            if (flagM) {
                salesPointToClose.close();
                System.out.println("Пункт продаж закрыт. Все товары перемещены.");
            } else {
                System.out.println("Не удалось закрыть пункт продаж.");
            }
        }
    }

    private static void openSalesPoint(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flagS = true;
        int s = -1;
        List<SalesPoint> closedSalesPoints = salesPoints.stream().filter(sp -> sp.getStatus() == IsActiveStatus.INACTIVE).toList();
        while (flagS) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < closedSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + closedSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                s = scanner.nextInt() - 1;
                if (s >= 0 & s <= closedSalesPoints.size()) {
                    flagS = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }
        closedSalesPoints.get(s).open();
    }

    private static void viewSalesPointInfo(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        int choice = -1;
        List<SalesPoint> openSalesPoints = salesPoints.stream().filter(s -> s.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < openSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + openSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openSalesPoints.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        SalesPoint s = openSalesPoints.get(choice);
        System.out.println("Пункт продаж: ");
        System.out.println("Город: " + s.getCity());
        System.out.println("Адрес: " + s.getAddress());
        System.out.println("Доход: " + s.getIncome());
        System.out.println("Количество сотрудников: " + s.getEmployeeIds().size());
    }

    private static void analyticsMenu(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.println("\n=== Аналитика ===");
            System.out.println("1. Доходность пункта продаж");
            System.out.println("2. Товары на складе");
            System.out.println("3. Товары в пункте продаж");
            System.out.println("4. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewSalesPointProfit(manager);
                    break;
                case "2":
                    viewWarehouseProducts(manager);
                    break;
                case "3":
                    viewSalesPointProducts(manager);
                    break;
                case "4":
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private static void viewSalesPointProfit(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        int choice = -1;
        List<SalesPoint> openSalesPoints = salesPoints.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < openSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + openSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openSalesPoints.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }
        System.out.println("Доход: " + openSalesPoints.get(choice).getIncome());
    }

    private static void viewWarehouseProducts(Manager manager) {
        Scanner scanner = new Scanner(System.in);

        boolean flagW = true;
        int w = -1;
        List<Warehouse> openWarehouses = warehouses.stream().filter(wh -> wh.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flagW) {
            System.out.println("Доступные склады: ");
            for (int i = 0; i < openWarehouses.size(); i++) {
                System.out.println("" + (i+1) + ". " + openWarehouses.get(i));
            }
            System.out.print("Выберите номер склада: ");
            try {
                w = scanner.nextInt() - 1;
                if (w >= 0 & w <= openWarehouses.size()) {
                    flagW = false;
                } else {
                    System.out.println("Неверный ввод.");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        Warehouse warehouse = openWarehouses.get(w);
        System.out.println("Доступные товары: ");
        for (StorageCell cell : warehouse.getCells()) {
            System.out.println(OrderService.getProductById(cell.getProductId(), products).getName() + " - " + cell.getProductAmount());
        }
    }

    private static void viewSalesPointProducts(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        int choice = -1;
        List<SalesPoint> openSalesPoints = salesPoints.stream().filter(w -> w.getStatus() == IsActiveStatus.ACTIVE).toList();
        while (flag) {
            System.out.println("Доступные пункты продаж: ");
            for (int i = 0; i < openSalesPoints.size(); i++) {
                System.out.println("" + (i+1) + ". " + openSalesPoints.get(i));
            }
            System.out.print("Выберите номер пункта продаж: ");
            try {
                choice = scanner.nextInt() - 1;
                if (choice >= 0 & choice <= openSalesPoints.size()) {
                    flag = false;
                } else {
                    System.out.println("Неверный ввод.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Номер должен быть числом!");
            }
            scanner.nextLine();
        }

        SalesPoint salesPoint = openSalesPoints.get(choice);
        System.out.println("Доступные товары: ");
        for (OrderItem item : salesPoint.getAvaliableProducts()) {
            System.out.println(item.getProduct().getName() + " - " + item.getQuantity());
        }
    }

    private static void viewProducts() {
        System.out.println("\nСписок товаров:");
        products.forEach(p ->
                System.out.println(p.getId() + ". " + p.getName() + " - " + p.getSellCost())
        );
    }

    private static void initializeTestData() {
        // Инициализация тестовых данных
        warehouses.add(new Warehouse("Москва", "Красная Площадь"));
        warehouses.add(new Warehouse("Санкт-Петербург", "Невский проспект"));

        salesPoints.add(new SalesPoint("Москва", "ул. Тверская, 10"));
        salesPoints.add(new SalesPoint("Санкт-Петербург", "Невский пр., 20"));

        producers.add(new Producer("ООО Ромашка", new ArrayList<>()));

        products.add(new Product("Ноутбук", 100000, 89999, producers.getFirst().getId()));
        products.add(new Product("Смартфон", 80000, 69999,  producers.getFirst().getId()));

        customers.add(new Customer("user1", "pass1"));
        managers.add(new Manager("admin", "admin123", "Иванов Иван Иванович"));
    }

//    public static void loadAllData() {
//
//        try (InputStream file = new FileInputStream(DATA_FILE);
//             Workbook workbook = new XSSFWorkbook(file)) {
//
//            // Чтение складов
//            warehouses = DataService.readWarehouses(workbook.getSheet("Warehouses"));
//            // Чтение пунктов продаж
//            salesPoints = DataService.readSalesPoints(workbook.getSheet("SalesPoints"));
//            // Чтение товаров
//            products = DataService.readProducts(workbook.getSheet("Products"));
//            // Чтение заказов
//            orders = DataService.readOrders(workbook.getSheet("Orders"));
//            // Чтение производителей
//            producers = DataService.readProducers(workbook.getSheet("Producers"));
//            // Чтение клиентов
//            customers = DataService.readCustomers(workbook.getSheet("Customers"));
//            // Чтение менеджеров
//            managers = DataService.readManagers(workbook.getSheet("Managers"));
//            // Чтение сотрудников складов
//            warehouseEmployees = DataService.readWarehouseEmployees(workbook.getSheet("WarehouseEmployees"));
//            // Чтение сотрудников пунктов продаж
//            salesPointEmployees = DataService.readSalesPointEmployees(workbook.getSheet("SalesPointEmployees"));
//
//        } catch (FileNotFoundException e) {
//            System.out.println("Файл данных не найден. Будет создан новый при сохранении.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}