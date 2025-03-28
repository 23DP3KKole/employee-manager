package lv.rvt;

public class Employee {
    private String name;
    private String ID;
    private String department;
    private double salary;
    public Employee(String employeeName, String employeeID, String employeeDepartment, double employeeSalary){
        this.name = employeeName;
        this.ID = employeeID;
        this.department = employeeDepartment;
        this.salary = employeeSalary;
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.ID;
    }

    public String getDepartment(){
        return this.department;
    }

    public double getSalary(){
        return this.salary;
    }
}