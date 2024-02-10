import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Finance {
    
    static class Transaction {
        String date;
        double income;
        double expense;
        String category;
        
        public Transaction(String date, double income, double expense, String category) {
            this.date = date;
            this.income = income;
            this.expense = expense;
            this.category = category;
        }
    }
    
    public static ArrayList<Transaction> loadTransactions(String filePath) throws FileNotFoundException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String date = parts[0];
            double income = Double.parseDouble(parts[1]);
            double expense = Double.parseDouble(parts[2]);
            String category = parts[3];
            transactions.add(new Transaction(date, income, expense, category));
        }
        scanner.close();
        return transactions;
    }
    
    public static void analyzeTransactions(ArrayList<Transaction> transactions) {
        double totalIncome = 0;
        double totalExpense = 0;
        HashMap<String, Double> expenseByCategory = new HashMap<>();
        
        for (Transaction transaction : transactions) {
            totalIncome += transaction.income;
            totalExpense += transaction.expense;
            
            double currentExpense = expenseByCategory.getOrDefault(transaction.category, 0.0);
            expenseByCategory.put(transaction.category, currentExpense + transaction.expense);
        }
        
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpense);
        double savingsRatio = (totalIncome - totalExpense) / totalIncome;
        System.out.println("Savings Ratio: " + savingsRatio);
        
        System.out.println("\nExpense Categories:");
        for (Map.Entry<String, Double> entry : expenseByCategory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        String filePath = "your_dataset.csv";
        try {
            ArrayList<Transaction> transactions = loadTransactions(filePath);
            analyzeTransactions(transactions);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
