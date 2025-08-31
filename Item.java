import java.util.Scanner;

class Item {
    int code;
    String name;
    int quantity;
    double pricePerUnit;

    void inputDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Item Code: ");
        code = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Item Name: ");
        name = sc.nextLine();
        System.out.print("Enter Quantity: ");
        quantity = sc.nextInt();
        System.out.print("Enter Price per Unit: ");
        pricePerUnit = sc.nextDouble();
    }

    double totalPrice() {
        return quantity * pricePerUnit;
    }

    void displayBill() {
        System.out.println("Item Code: " + code + ", Name: " + name + ", Quantity: " + quantity + ", Unit Price: " + pricePerUnit + ", Total: " + totalPrice());
    }

    public static void main(String[] args) {
        Item i = new Item();
        i.inputDetails();
        i.displayBill();
    }
}
