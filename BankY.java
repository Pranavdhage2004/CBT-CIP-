import java.io.*;
import java.util.*;

class BankAccount implements Serializable 
{
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName, double initialDeposit) 
    {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() 
    {
        return accountNumber;
    }

    public String getAccountHolderName() 
    {
        return accountHolderName;
    }

    public double getBalance() 
    {
        return balance;
    }

    public void deposit(double amount) 
    {
        if (amount > 0) {
            balance += amount;
            System.out.println(" Deposit successful!    New balance: " + balance);
        } else {
            System.out.println(" Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) 
    {
        if (amount > 0 && amount <= balance) 
        {
            balance -= amount;
            System.out.println(" Withdrawal successful! New balance: " + balance);
        } else 
        {
            System.out.println(" Invalid or insufficient funds for withdrawal.");
        }
    }

    public boolean transferFunds(BankAccount recipient, double amount) 
    {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            recipient.deposit(amount);
            System.out.println(" Transfer successful!");
            return true;
        } else {
            System.out.println(" Invalid or insufficient funds for transfer.");
            return false;
        }
    }

    @Override
    public String toString() 
    {
        return String.format("Account Number: %s, Account Holder: %s, Balance: %.2f", accountNumber, accountHolderName, balance);
    }
}

public class BankY 
{
    private static final String DATA_FILE = "accounts.dat";
    private Map<String, BankAccount> accounts;

    public BankY() 
    {
        accounts = loadAccounts();
    }

    private Map<String, BankAccount> loadAccounts() 
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (Map<String, BankAccount>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void saveAccounts() 
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public void createAccount(String accountNumber, String accountHolderName, double initialDeposit) 
    {
        if (!accounts.containsKey(accountNumber)) 
        {
            BankAccount newAccount = new BankAccount(accountNumber, accountHolderName, initialDeposit);
            accounts.put(accountNumber, newAccount);
            saveAccounts();
            System.out.println(" Account created successfully!");
        } else 
        {
            System.out.println("\u2718 Account with this number already exists.");
        }
    }

    public BankAccount getAccount(String accountNumber) 
    {
        return accounts.get(accountNumber);
    }

    public void displayAllAccounts() 
    {
        if (accounts.isEmpty()) {
            System.out.println("\u2718 No accounts found.");
        } else {
            System.out.println("\n--- Account List ---");
            accounts.values().forEach(System.out::println);
        }
    }

    public static void main(String[] args) 
    {
        BankY bank = new BankY();
        Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            System.out.println("\n---- *** Bank Management System *** ----");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");
            System.out.print(" Choose an option: ");

            int choice;
            try 
            {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\u2718 Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) 
            {
                case 1 -> {
                    System.out.print("Enter account number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter account holder name: ");
                    String accHolder = scanner.nextLine();
                    System.out.print("Enter initial deposit: ");
                    double deposit;
                    try {
                        deposit = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("\u2718 Invalid deposit amount.");
                        continue;
                    }
                    bank.createAccount(accNum, accHolder, deposit);
                }
                case 2 -> 
                {
                    System.out.print("Enter account number: ");
                    String accNum = scanner.nextLine();
                    BankAccount account = bank.getAccount(accNum);
                    if (account != null) {
                        System.out.print("Enter deposit amount: ");
                        double amount;
                        try {
                            amount = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("\u2718 Invalid amount.");
                            continue;
                        }
                        account.deposit(amount);
                        bank.saveAccounts();
                    } else {
                        System.out.println("\u2718 Account not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter account number: ");
                    String accNum = scanner.nextLine();
                    BankAccount account = bank.getAccount(accNum);
                    if (account != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double amount;
                        try {
                            amount = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("\u2718 Invalid amount.");
                            continue;
                        }
                        account.withdraw(amount);
                        bank.saveAccounts();
                    } else {
                        System.out.println("\u2718 Account not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter your account number: ");
                    String senderAccNum = scanner.nextLine();
                    BankAccount sender = bank.getAccount(senderAccNum);
                    if (sender != null) {
                        System.out.print("Enter recipient account number: ");
                        String recipientAccNum = scanner.nextLine();
                        BankAccount recipient = bank.getAccount(recipientAccNum);
                        if (recipient != null) {
                            System.out.print("Enter transfer amount: ");
                            double amount;
                            try {
                                amount = Double.parseDouble(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("\u2718 Invalid amount.");
                                continue;
                            }
                            sender.transferFunds(recipient, amount);
                            bank.saveAccounts();
                        } else {
                            System.out.println("\u2718 Recipient account not found.");
                        }
                    } else {
                        System.out.println("\u2718 Your account not found.");
                    }
                }
                case 5 -> bank.displayAllAccounts();
                case 6 -> {
                    System.out.println(" Exiting BankY. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("\u2718 Invalid option. Please try again.");
            }
        }
    }
}
