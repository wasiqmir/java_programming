class Rectangle {
    private double length;
    private double breadth;

    void setLength(double length) {
        this.length = length;
    }

    void setBreadth(double breadth) {
        this.breadth = breadth;
    }

    double getLength() {
        return length;
    }

    double getBreadth() {
        return breadth;
    }

    double area() {
        return length * breadth;
    }

    double perimeter() {
        return 2 * (length + breadth);
    }

    public static void main(String[] args) {
        Rectangle r = new Rectangle();
        r.setLength(5);
        r.setBreadth(3);
        System.out.println("Area: " + r.area());
        System.out.println("Perimeter: " + r.perimeter());
    }
}
