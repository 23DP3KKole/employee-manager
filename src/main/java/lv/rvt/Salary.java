package lv.rvt;

public class Salary {
    private String employeeId;
    private double salary;

    // Default constructor
    public Salary() {
    }

    public Salary(String employeeId, double salary) {
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Salary{" +
               "employeeId='" + employeeId + '\'' +
               ", salary=" + salary +
               '}';
    }
}
