class EmployeeCalculation {
    int empId;
    String name;
    double basicSalary;
    double hra;
    double da;
    double grossSalary;

    EmployeeCalculation(int empId, String name, double basicSalary) {
        this.empId = empId;
        this.name = name;
        this.basicSalary = basicSalary;
        calculate();
    }

    void calculate() {
        hra = 0.2 * basicSalary;
        da = 0.1 * basicSalary;
        grossSalary = basicSalary + hra + da;
    }

    void display() {
        System.out.println("ID: " + empId + ", Name: " + name);
        System.out.println("Basic: " + basicSalary + ", HRA: " + hra + ", DA: " + da + ", Gross: " + grossSalary);
    }

    public static void main(String[] args) {
        Employee e1 = new Employee(1, "Amit", 30000);
        Employee e2 = new Employee(2, "Riya", 40000);
        Employee e3 = new Employee(3, "Raj", 50000);
        e1.display();
        e2.display();
        e3.display();
    }
}
