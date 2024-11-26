import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorSwing extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public CalculatorSwing() {
        setTitle("Swing Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createMainMenu(), "MainMenu");
        mainPanel.add(createBasicArithmeticPanel(), "BasicArithmetic");
        mainPanel.add(createScientificPanel(), "Scientific");
        mainPanel.add(createConversionPanel(), "Conversion");

        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");
    }

    private JPanel createMainMenu() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JLabel label = new JLabel("Select Operation", JLabel.CENTER);
        panel.add(label);

        JButton basicButton = new JButton("Basic Arithmetic");
        JButton scientificButton = new JButton("Scientific Calculations");
        JButton conversionButton = new JButton("Unit Conversions");
        JButton exitButton = new JButton("Exit");

        panel.add(basicButton);
        panel.add(scientificButton);
        panel.add(conversionButton);
        panel.add(exitButton);

        basicButton.addActionListener(e -> cardLayout.show(mainPanel, "BasicArithmetic"));
        scientificButton.addActionListener(e -> cardLayout.show(mainPanel, "Scientific"));
        conversionButton.addActionListener(e -> cardLayout.show(mainPanel, "Conversion"));
        exitButton.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel createBasicArithmeticPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel label = new JLabel("Basic Arithmetic", JLabel.CENTER);
        panel.add(label);

        JTextField num1Field = new JTextField();
        JTextField num2Field = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");
        panel.add(new JLabel("Number 1:"));
        panel.add(num1Field);
        panel.add(new JLabel("Number 2:"));
        panel.add(num2Field);

        JButton addButton = new JButton("Add");
        JButton subtractButton = new JButton("Subtract");
        JButton multiplyButton = new JButton("Multiply");
        JButton divideButton = new JButton("Divide");

        panel.add(addButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(divideButton);
        panel.add(resultLabel);

        ActionListener arithmeticListener = e -> {
            try {
                double num1 = Double.parseDouble(num1Field.getText());
                double num2 = Double.parseDouble(num2Field.getText());
                double result = 0;

                if (e.getSource() == addButton) result = num1 + num2;
                if (e.getSource() == subtractButton) result = num1 - num2;
                if (e.getSource() == multiplyButton) result = num1 * num2;
                if (e.getSource() == divideButton) {
                    if (num2 == 0) {
                        resultLabel.setText("Error: Division by zero");
                        return;
                    }
                    result = num1 / num2;
                }
                resultLabel.setText("Result: " + result);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid input");
            }
        };

        addButton.addActionListener(arithmeticListener);
        subtractButton.addActionListener(arithmeticListener);
        multiplyButton.addActionListener(arithmeticListener);
        divideButton.addActionListener(arithmeticListener);

        return panel;
    }

    private JPanel createScientificPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("Scientific Calculations", JLabel.CENTER);
        panel.add(label);

        JTextField numField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");
        panel.add(numField);

        JButton sqrtButton = new JButton("Square Root");
        JButton powerButton = new JButton("Exponentiation");

        panel.add(sqrtButton);
        panel.add(powerButton);
        panel.add(resultLabel);

        sqrtButton.addActionListener(e -> {
            try {
                double num = Double.parseDouble(numField.getText());
                resultLabel.setText("Result: " + Math.sqrt(num));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid input");
            }
        });

        powerButton.addActionListener(e -> {
            try {
                String[] inputs = numField.getText().split(",");
                double base = Double.parseDouble(inputs[0].trim());
                double exponent = Double.parseDouble(inputs[1].trim());
                resultLabel.setText("Result: " + Math.pow(base, exponent));
            } catch (Exception ex) {
                resultLabel.setText("Error: Invalid input format. Use base,exponent.");
            }
        });

        return panel;
    }

    private JPanel createConversionPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JLabel label = new JLabel("Unit Conversions", JLabel.CENTER);
        panel.add(label);

        JTextField inputField = new JTextField();
        JLabel resultLabel = new JLabel("Result: ");
        panel.add(inputField);

        JButton tempButton = new JButton("Celsius to Fahrenheit");
        JButton currencyButton = new JButton("USD to INR");

        panel.add(tempButton);
        panel.add(currencyButton);
        panel.add(resultLabel);

        tempButton.addActionListener(e -> {
            try {
                double celsius = Double.parseDouble(inputField.getText());
                resultLabel.setText("Result: " + ((celsius * 9 / 5) + 32) + " °F");
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid input");
            }
        });

        currencyButton.addActionListener(e -> {
            try {
                double usd = Double.parseDouble(inputField.getText());
                double rate = 82.0; // Static conversion rate
                resultLabel.setText("Result: ₹" + (usd * rate));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Error: Invalid input");
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorSwing calculator = new CalculatorSwing();
            calculator.setVisible(true);
        });
    }
}
