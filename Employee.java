import java.io.Serializable;

public class Employee implements Serializable {
    protected String id;
    protected String fullName;
    protected EmployeeStatus status;
    protected String managerId;

    public Employee(String fullName, String managerId) {
        this.id = MakeId.makeId();
        this.fullName = fullName;
        this.status = EmployeeStatus.HIRED;
        this.managerId = managerId;
    }

    public Employee(String id, String fullName, String managerId) {
        this.id = id;
        this.fullName = fullName;
        this.status = EmployeeStatus.HIRED;
        this.managerId = managerId;
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

    public String getManagerId() {
        return managerId;
    }

    public void updateStatus(EmployeeStatus status) {
        this.status = status;
    }

    public void updateManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String toString() {
        return fullName;
    }
}