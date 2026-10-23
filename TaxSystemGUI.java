
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Person {
    protected String name;

    Person(String name) {
        this.name = name;
    }
}

class TaxPayer extends Person {
    private double income;
    private double deduction;

    TaxPayer(String name, double income, double deduction) {
        super(name);
        this.income = income;
        this.deduction = deduction;
    }

    public double calculateTaxableIncome() {
        return Math.max(0, income - deduction);
    }

    public double calculateTax() {
        double taxableIncome = calculateTaxableIncome();
        double tax = 0;

        // Simplified tax slabs for demonstration
        if (taxableIncome > 1000000) {
            tax += (taxableIncome - 1000000) * 0.30;
            taxableIncome = 1000000;
        }

        if (taxableIncome > 500000) {
            tax += (taxableIncome - 500000) * 0.20;
            taxableIncome = 500000;
        }

        if (taxableIncome > 250000) {
            tax += (taxableIncome - 250000) * 0.05;
        }

        return tax;
    }
}

public class IncomeTaxSystem extends JFrame implements ActionListener {

    JTextField nameField, incomeField, deductionField;
    JTextArea resultArea;
    JButton calculateButton, clearButton;

    IncomeTaxSystem() {
        setTitle("Income Tax Estimation and Deduction System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(
            "INCOME TAX ESTIMATION SYSTEM",
            JLabel.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.BLUE);

        nameField = new JTextField();
        incomeField = new JTextField();
        deductionField = new JTextField();

        panel.add(new JLabel("Taxpayer Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Annual Income (Rs.):"));
        panel.add(incomeField);

        panel.add(new JLabel("Total Deductions (Rs.):"));
        panel.add(deductionField);

        calculateButton = new JButton("Calculate Tax");
        clearButton = new JButton("Clear");

        panel.add(calculateButton);
        panel.add(clearButton);

        resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        calculateButton.addActionListener(this);

        clearButton.addActionListener(e -> {
            nameField.setText("");
            incomeField.setText("");
            deductionField.setText("");
            resultArea.setText("");
        });

        setLayout(new BorderLayout(10, 10));
        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String name = nameField.getText().trim();
            double income = Double.parseDouble(incomeField.getText());
            double deduction = Double.parseDouble(deductionField.getText());

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "Please enter the taxpayer name."
                );
                return;
            }

            if (income < 0 || deduction < 0) {
                JOptionPane.showMessageDialog(
                    this, "Income and deductions cannot be negative."
                );
                return;
            }

            if (deduction > income) {
                JOptionPane.showMessageDialog(
                    this, "Deductions cannot exceed annual income."
                );
                return;
            }

            TaxPayer taxpayer = new TaxPayer(name, income, deduction);

            double taxableIncome = taxpayer.calculateTaxableIncome();
            double tax = taxpayer.calculateTax();

            resultArea.setText(
                "===== INCOME TAX REPORT =====\n\n" +
                "Taxpayer Name   : " + name + "\n" +
                String.format("Annual Income   : Rs. %.2f%n", income) +
                String.format("Total Deductions: Rs. %.2f%n", deduction) +
                String.format("Taxable Income
```
