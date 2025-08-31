import java.util.Scanner;

class Student {
    int rollNo;
    String name;
    String course;
    int[] marks = new int[3];
    int total;
    double average;

    void acceptDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll Number: ");
        rollNo = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        name = sc.nextLine();
        System.out.print("Enter Course: ");
        course = sc.nextLine();
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter marks in subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }
        calculate();
    }

    void calculate() {
        total = 0;
        for (int m : marks) total += m;
        average = total / 3.0;
    }

    void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Total Marks: " + total);
        System.out.println("Average Marks: " + average);
    }

    public static void main(String[] args) {
        Student s = new Student();
        s.acceptDetails();
        s.display();
    }
}
