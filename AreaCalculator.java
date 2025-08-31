class AreaCalculator {
    int area(int side) {
        return side * side;
    }

    int area(int l, int b) {
        return l * b;
    }

    double area(double radius) {
        return Math.PI * radius * radius;
    }

    public static void main(String[] args) {
        AreaCalculator ac = new AreaCalculator();
        System.out.println("Square Area: " + ac.area(5));
        System.out.println("Rectangle Area: " + ac.area(4, 6));
        System.out.println("Circle Area: " + ac.area(3.5));
    }
}
