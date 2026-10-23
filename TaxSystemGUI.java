import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class IncomeTaxSystem extends JFrame {

    private JTextField nameField;
    private JTextField incomeField;
    private JTextField deductionField;
    private JTextArea resultArea;

    public IncomeTaxSystem() {
        setTitle("Income Tax Estimation and Detection System");
        setSize(650, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 247, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel(
                "INCOME TAX ESTIMATION SYSTEM",
                SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(35, 75, 135));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBackground(new Color(240, 247, 255));

        nameField = new JTextField();
        incomeField = new JTextField();
        deductionField = new JTextField();

        formPanel.add(new JLabel("Taxpayer Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Annual Income (Rs.):"));
        formPanel.add(incomeField);

        formPanel.add(new JLabel("Total Deductions (Rs.):"));
        formPanel.add(deductionField);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 10)
        );
        buttonPanel.setBackground(new Color(240, 247, 255));

        JButton estimateButton = new JButton("Estimate Tax");
        JButton detectButton = new JButton("Detect / Verify");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(estimateButton);
        buttonPanel.add(detectButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        resultArea = new JTextArea(10, 45);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("System Report")
        );

        JPanel centerPanel = new JPanel(new BorderLayout(10, 15));
        centerPanel.setBackground(new Color(240, 247, 255));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel);

        estimateButton.addActionListener(e -> estimateTax());
        detectButton.addActionListener(e -> detectDetails());
        clearButton.addActionListener(e -> clearFields());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private double[] readIncomeDetails() {
        String name = nameField.getText().trim();
        String incomeText = incomeField.getText().trim();
        String deductionText = deductionField.getText().trim();

        if (name.isEmpty() || incomeText.isEmpty()
                || deductionText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please fill in all three fields."
            );
        }

        double income;
        double deductions;

        try {
            income = Double.parseDouble(
                    incomeText.replace(",", "").replace(" ", "")
            );

            deductions = Double.parseDouble(
                    deductionText.replace(",", "").replace(" ", "")
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "Enter valid numeric income and deductions."
            );
        }

        if (!Double.isFinite(income)
                || !Double.isFinite(deductions)) {
            throw new IllegalArgumentException(
                    "Enter finite numeric values only."
            );
        }

        if (income < 0 || deductions < 0) {
            throw new IllegalArgumentException(
                    "Income and deductions cannot be negative."
            );
        }

        if (deductions > income) {
            throw new IllegalArgumentException(
                    "Deductions cannot exceed annual income."
            );
        }

        return new double[] { income, deductions };
    }

    private double calculateTax(double taxableIncome) {
        // Illustrative demo slabs only, not current official tax rules.
        double tax = 0;

        if (taxableIncome > 1500000) {
            tax += (taxableIncome - 1500000) * 0.30;
            taxableIncome = 1500000;
        }

        if (taxableIncome > 1200000) {
            tax += (taxableIncome - 1200000) * 0.20;
            taxableIncome = 1200000;
        }

        if (taxableIncome > 900000) {
            tax += (taxableIncome - 900000) * 0.15;
            taxableIncome = 900000;
        }

        if (taxableIncome > 600000) {
            tax += (taxableIncome - 600000) * 0.10;
            taxableIncome = 600000;
        }

        if (taxableIncome > 300000) {
            tax += (taxableIncome - 300000) * 0.05;
        }

        return tax;
    }

    private String formatRupees(double amount) {
        NumberFormat formatter =
                NumberFormat.getCurrencyInstance(
                        new Locale("en", "IN")
                );

        return formatter.format(amount);
    }

    private void estimateTax() {
        try {
            double[] details = readIncomeDetails();

            double income = details[0];
            double deductions = details[1];
            double taxableIncome = income - deductions;
            double tax = calculateTax(taxableIncome);

            resultArea.setText(
                    "INCOME TAX ESTIMATION REPORT\n"
                    + "--------------------------------------\n"
                    + "Taxpayer Name : "
                    + nameField.getText().trim() + "\n"
                    + "Annual Income : "
                    + formatRupees(income) + "\n"
                    + "Deductions    : "
                    + formatRupees(deductions) + "\n"
                    + "Taxable Income: "
                    + formatRupees(taxableIncome) + "\n"
                    + "Estimated Tax : "
                    + formatRupees(tax) + "\n"
                    + "--------------------------------------\n"
                    + "DEMO ESTIMATE ONLY\n"
                    + "Actual tax may differ under official rules."
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void detectDetails() {
        try {
            double[] details = readIncomeDetails();

            double income = details[0];
            double deductions = details[1];

            resultArea.setText(
                    "INCOME DETAILS VERIFICATION\n"
                    + "--------------------------------------\n"
                    + "Taxpayer Name: "
                    + nameField.getText().trim() + "\n"
                    + "Income Entry: VALID\n"
                    + "Deduction Entry: VALID\n"
                    + "Income-Deduction Check: PASSED\n"
                    + "--------------------------------------\n"
                    + "Taxable Income: "
                    + formatRupees(income - deductions) + "\n"
                    + "Verification Status: INPUTS VALID\n\n"
                    + "Note: This program checks entered values only.\n"
                    + "It does not detect actual tax fraud."
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Verification Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            resultArea.setText(
                    "VERIFICATION FAILED\n"
                    + "Please correct the entered details."
            );
        }
    }

    private void clearFields() {
        nameField.setText("");
        incomeField.setText("");
        deductionField.setText("");
        resultArea.setText("");
        nameField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IncomeTaxSystem app = new IncomeTaxSystem();
            app.setVisible(true);
        });
    }
}
