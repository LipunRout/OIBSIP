import java.util.*;

class User {
    String userId;
    String userPin;
    double balance;
    List<String> transactionHistory = new ArrayList<>();

    public User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
    }

    public void addTransaction(String detail) {
        transactionHistory.add(detail);
    }

    public void showTransactions() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String tx : transactionHistory) {
                System.out.println(tx);
            }
        }
    }
}

class Bank {
    Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.userId, user);
    }

    public User authenticate(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.userPin.equals(pin)) {
            return user;
        }
        return null;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }
}

class ATMOperations {
    Scanner sc = new Scanner(System.in);
    Bank bank;
    User currentUser;

    public ATMOperations(Bank bank) {
        this.bank = bank;
    }

    public void login() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        currentUser = bank.authenticate(userId, pin);
        if (currentUser != null) {
            System.out.println("Login Successful!\n");
            showMenu();
        } else {
            System.out.println("Invalid credentials!\n");
        }
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n----- ATM Menu -----");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: viewHistory(); break;
                case 2: withdraw(); break;
                case 3: deposit(); break;
                case 4: transfer(); break;
                case 5: System.out.println("Thank you for using the ATM. Goodbye!"); break;
                default: System.out.println("Invalid option.");
            }
        } while (choice != 5);
    }

    private void viewHistory() {
        currentUser.showTransactions();
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (amount > 0 && amount <= currentUser.balance) {
            currentUser.balance -= amount;
            currentUser.addTransaction("Withdrawn: ₹" + amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    private void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        if (amount > 0) {
            currentUser.balance += amount;
            currentUser.addTransaction("Deposited: ₹" + amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private void transfer() {
        System.out.print("Enter recipient User ID: ");
        String recipientId = sc.nextLine();
        User recipient = bank.getUser(recipientId);
        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();

        if (amount > 0 && amount <= currentUser.balance) {
            currentUser.balance -= amount;
            recipient.balance += amount;

            currentUser.addTransaction("Transferred ₹" + amount + " to " + recipientId);
            recipient.addTransaction("Received ₹" + amount + " from " + currentUser.userId);

            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }
}

public class ATM {
    public static void main(String[] args) {
        Bank bank = new Bank();
        // Adding dummy users
        bank.addUser(new User("user1", "1234", 10000.0));
        bank.addUser(new User("user2", "5678", 8000.0));

        ATMOperations atm = new ATMOperations(bank);

        while (true) {
            atm.login();
        }
    }
}
