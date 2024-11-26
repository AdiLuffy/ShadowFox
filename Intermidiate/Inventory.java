package Intermediate;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Inventory {
    private final JFrame frame;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField nameField;
    private final JTextField quantityField;
    private final ArrayList<Item> inventoryList;

    // Item class to represent inventory items
    private static class Item {
        String name;
        int quantity;

        Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return name + "," + quantity;
        }
    }

    public Inventory() {
        inventoryList = new ArrayList<>();
        loadInventory();  // Load inventory from file
        
        // Create JFrame
        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Create Table
        tableModel = new DefaultTableModel(new String[]{"Item Name", "Quantity"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        nameField = new JTextField(10);
        quantityField = new JTextField(5);
        
        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton saveButton = new JButton("Save Inventory");

        // Action listeners for buttons
        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());
        saveButton.addActionListener(e -> saveInventory());

        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(saveButton);
        
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
    }

    private void addItem() {
        String name = nameField.getText();
        int quantity = validateQuantity(quantityField.getText());

        if (quantity < 0) return; // Invalid quantity
        
        inventoryList.add(new Item(name, quantity));
        tableModel.addRow(new Object[]{name, quantity});
        clearFields();
    }

    private void updateItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String name = nameField.getText();
            int quantity = validateQuantity(quantityField.getText());

            if (quantity < 0) return; // Invalid quantity

            inventoryList.get(selectedRow).name = name;
            inventoryList.get(selectedRow).quantity = quantity;
            tableModel.setValueAt(name, selectedRow, 0);
            tableModel.setValueAt(quantity, selectedRow, 1);
            clearFields();
        } else {
            showError("Select an item to update.");
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            inventoryList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            showError("Select an item to delete.");
        }
    }

    private void saveInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inventory.txt"))) {
            for (Item item : inventoryList) {
                writer.write(item.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(frame, "Inventory saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            showError("Error saving inventory: " + e.getMessage());
        }
    }

    private void loadInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("inventory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    inventoryList.add(new Item(name, quantity));
                    tableModel.addRow(new Object[]{name, quantity});
                }
            }
        } catch (IOException e) {
            // Ignore if the file doesn't exist
        } catch (NumberFormatException e) {
            showError("Invalid data format in inventory file.");
        }
    }

    private int validateQuantity(String quantityText) {
        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity < 0) {
                showError("Quantity cannot be negative.");
            }
            return quantity;
        } catch (NumberFormatException e) {
            showError("Invalid quantity. Please enter a valid number.");
            return -1; // Indicate invalid quantity
        }
    }

    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Inventory::new);
    }
}