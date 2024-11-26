package Intermediate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Bank {
    private double balance;
    private final ArrayList<String> transactionHistory;
    private String customerName;

    public Bank(String customerName) {
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.customerName = customerName;
    }

    public double getBalance() {
        return balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
        System.out.println("Successfully deposited $" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        transactionHistory.add("Withdrew: $" + amount);
        System.out.println("Successfully withdrew $" + amount);
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History for " + customerName + ":");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    // Static class to manage multiple customers
    public static class BankSystem {
        private static final Map<String, Bank> customers = new HashMap<>();

        public static void addCustomer(String customerName) {
            if (customers.containsKey(customerName)) {
                System.out.println("Customer already exists.");
            } else {
                customers.put(customerName, new Bank(customerName));
                System.out.println("Customer added: " + customerName);
            }
        }

        public static Bank getCustomerAccount(String customerName) {
            return customers.get(customerName);
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nBank System Management");
                System.out.println("1. Add Customer");
                System.out.println("2. Access Customer Account");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter customer name: ");
                        String name = scanner.nextLine();
                        addCustomer(name);
                    }
                    case 2 -> {
                        System.out.print("Enter customer name: ");
                        String name = scanner.nextLine();
                        Bank account = getCustomerAccount(name);
                        if (account != null) {
                            boolean manageAccount = true;
                            while (manageAccount) {
                                System.out.println("\nManaging Account for " + name);
                                System.out.println("1. Deposit");
                                System.out.println("2. Withdraw");
                                System.out.println("3. Check Balance");
                                System.out.println("4. View Transaction History");
                                System.out.println("5. Go Back");
                                System.out.print("Choose an option: ");
                                int accountChoice = scanner.nextInt();

                                switch (accountChoice) {
                                    case 1 -> {
                                        System.out.print("Enter amount to deposit: ");
                                        double depositAmount = scanner.nextDouble();
                                        account.deposit(depositAmount);
                                    }
                                    case 2 -> {
                                        System.out.print("Enter amount to withdraw: ");
                                        double withdrawAmount = scanner.nextDouble();
                                        account.withdraw(withdrawAmount);
                                    }
                                    case 3 -> System.out.println("Current Balance: $" + account.getBalance());
                                    case 4 -> account.showTransactionHistory();
                                    case 5 -> manageAccount = false;
                                    default -> System.out.println("Invalid choice. Please try again.");
                                }
                            }
                        } else {
                            System.out.println("Customer not found.");
                        }
                    }
                    case 3 -> {
                        System.out.println("Exiting Bank System. Thank you!");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    // JUnit test class
    public static class BankTest {
        private Bank bank;

        @BeforeEach
        public void setUp() {
            bank = new Bank("Test Customer");
        }

        @Test
        public void testInitialBalance() {
            assertEquals(0.0, bank.getBalance(), "Initial balance should be zero.");
        }

        @Test
        public void testDeposit() {
            bank.deposit(100.0);
            assertEquals(100.0, bank.getBalance(), "Balance should reflect deposited amount.");
        }

        @Test
        public void testWithdraw() {
            bank.deposit(200.0);
            bank.withdraw(100.0);
            assertEquals(100.0, bank.getBalance(), "Balance should reflect withdrawal.");
        }

        @Test
        public void testWithdrawInsufficientBalance() {
            bank.withdraw(100.0);
            assertEquals(0.0, bank.getBalance(), "Balance should remain zero when withdrawal exceeds balance.");
        }

        @Test
        public void testDepositNegativeAmount() {
            bank.deposit(-50.0);
            assertEquals(0.0, bank.getBalance(), "Balance should remain unchanged on negative deposit.");
        }

        @Test
        public void testWithdrawNegativeAmount() {
            bank.withdraw(-50.0);
            assertEquals(0.0, bank.getBalance(), "Balance should remain unchanged on negative withdrawal.");
        }

        @Test
        public void testTransactionHistory() {
            bank.deposit(100.0);
            bank.withdraw(50.0);
            assertEquals(2, bank.transactionHistory.size(), "Transaction history should have 2 entries.");
            assertEquals("Deposited: $100.0", bank.transactionHistory.get(0), "First entry should match deposit.");
            assertEquals("Withdrew: $50.0", bank.transactionHistory.get(1), "Second entry should match withdrawal.");
        }
    }
}