class Employee {
    int empID;
    String name;
    double basicSalary;
    double hra;
    double da;
    double grossSalary;

    Employee(int empID, String name, double basicSalary) {
        this.empID = empID;
        this.name = name;
        this.basicSalary = basicSalary;
        calculateSalary();
    }

    void calculateSalary() {
        hra = 0.2 * basicSalary;
        da = 0.1 * basicSalary;
        grossSalary = basicSalary + hra + da;
    }

    void printSalarySlip() {
        System.out.println("Employee ID: " + empID);
        System.out.println("Name: " + name);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("HRA: " + hra);
        System.out.println("DA: " + da);
        System.out.println("Gross Salary: " + grossSalary);
    }

    public static void main(String[] args) {
        Employee e = new Employee(1, "Amit", 30000);
        e.printSalarySlip();
    }
}
