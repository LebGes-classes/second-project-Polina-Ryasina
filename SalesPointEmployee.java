public class SalesPointEmployee extends Employee {
    private String salesPointId;

    public SalesPointEmployee(String fullName, String salesPointId, String managerId) {
        super(fullName, managerId);
        this.salesPointId = salesPointId;
    }

    public SalesPointEmployee(String id, String fullName, String salesPointId, String managerId) {
        super(id, fullName, managerId);
        this.salesPointId = salesPointId;
    }

    // Геттеры и сеттеры
    public String getSalesPointId() {
        return salesPointId;
    }

    public void setSalesPointId(String salesPointId) {
        this.salesPointId = salesPointId;
    }

}