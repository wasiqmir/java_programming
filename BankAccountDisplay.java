class BankAccountDisplay {
    int accountNumber;
    String accountHolder;
    double balance;

    BankAccountDisplay(int accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
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
        System.out.println("Account No: " + accountNumber + ", Holder: " + accountHolder + ", Balance: " + balance);
    }

    public static void main(String[] args) {
        BankAccount b = new BankAccount(101, "Rahul", 5000);
        b.deposit(2000);
        b.withdraw(1000);
        b.displayBalance();
    }
}
