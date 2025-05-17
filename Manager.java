import java.util.ArrayList;
import java.util.List;

public class Manager extends User {
    private String id;
    private String fullName;
    private EmployeeStatus status;
    private List<String> subordinates;

    public Manager(String login, String password, String fullName) {
        super(login, password);
        this.id = MakeId.makeId();
        this.fullName = fullName;
        this.status = EmployeeStatus.HIRED;
        this.subordinates = new ArrayList<>();
    }

    public Manager(String login, String password, String fullName, List<String> subordinates) {
        super(login, password);
        this.id = MakeId.makeId();
        this.fullName = fullName;
        this.status = EmployeeStatus.HIRED;
        this.subordinates = subordinates;
    }

    public Manager(String id, String login, String password, String fullName, List<String> subordinates) {
        super(login, password);
        this.id = id;
        this.fullName = fullName;
        this.status = EmployeeStatus.HIRED;
        this.subordinates = subordinates;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public List<String> getSubordinates() {
        return subordinates;
    }

    public boolean addSubordinate(Employee employee) {
        if (subordinates.contains(employee.getId())) {
            System.out.println("Сотрудник уже в подчинении!");
            return false;
        } else {
            subordinates.add(employee.getId());
            employee.updateManagerId(id);
            System.out.println("Сотрудник добавлен!");
            return true;
        }
    }

    public boolean changeSubordinatesManager(Employee employee, Manager manager) {
        if (subordinates.contains(employee.getId())) {
            subordinates.remove(employee.getId());
            manager.addSubordinate(employee);
            System.out.println("Менеджер сотрудника изменен!");
            return true;
        } else {
            System.out.println("Сотрудник не был в подчинении.");
            return false;
        }

    }

    public void fireSubordinate(Employee employee) {
        if (subordinates.contains(employee.getId())) {
            employee.updateStatus(EmployeeStatus.FIRED);
            employee.updateManagerId(null);
        } else {
            System.out.println("Сотрудник не был в подчинении.");
        }

    }


}