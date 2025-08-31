class Calculator {
    int calculate(int a, int b) {
        return a + b;
    }

    double calculate(double a, double b) {
        return a * b;
    }

    int calculate(int a) {
        return a * a;
    }

    public static void main(String[] args) {
        Calculator c = new Calculator();
        System.out.println("Sum: " + c.calculate(5, 10));
        System.out.println("Product: " + c.calculate(2.5, 4.0));
        System.out.println("Square: " + c.calculate(6));
    }
}
