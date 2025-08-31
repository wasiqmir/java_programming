class BankAccount {
    int accNo;
    String holderName;
    double balance;

    BankAccount(int accNo, String holderName, double balance) {
        this.accNo = accNo;
        this.holderName = holderName;
        this.balance = balance;
    }

    void deposit(double amt) {
        balance += amt;
    }

    void withdraw(double amt) {
        if (amt <= balance) balance -= amt;
        else System.out.println("Insufficient Balance");
    }

    void displayBalance() {
        System.out.println("Account No: " + accNo);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Balance: " + balance);
    }

    public static void main(String[] args) {
        BankAccount b = new BankAccount(101, "Rahul", 5000);
        b.deposit(2000);
        b.withdraw(1000);
        b.displayBalance();
    }
}
