import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class StudentManagementSystem extends JFrame {
    private final Map<String, Student> studentDatabase = new HashMap<>();
    private final DefaultTableModel tableModel;

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Navigation Panel
        JPanel navPanel = new JPanel();
        JButton addStudentButton = new JButton("Add Student");
        JButton updateStudentButton = new JButton("Update Student");
        JButton deleteStudentButton = new JButton("Delete Student");
        JButton viewStudentsButton = new JButton("View Students");

        navPanel.add(addStudentButton);
        navPanel.add(updateStudentButton);
        navPanel.add(deleteStudentButton);
        navPanel.add(viewStudentsButton);

        add(navPanel, BorderLayout.NORTH);

        // Table to Display Students
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Grade"}, 0);
        JTable studentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Event Handling
        addStudentButton.addActionListener(e -> openAddStudentDialog());
        updateStudentButton.addActionListener(e -> openUpdateStudentDialog(studentTable));
        deleteStudentButton.addActionListener(e -> deleteStudent(studentTable));
        viewStudentsButton.addActionListener(e -> updateTable());

        // Populate table on startup
        updateTable();
    }

    private void openAddStudentDialog() {
        // Create Add Student Dialog
        JDialog dialog = new JDialog(this, "Add Student", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("Student ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField gradeField = new JTextField();
        JButton addButton = new JButton("Add");

        dialog.add(idLabel);
        dialog.add(idField);
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(ageLabel);
        dialog.add(ageField);
        dialog.add(gradeLabel);
        dialog.add(gradeField);
        dialog.add(new JLabel());
        dialog.add(addButton);

        addButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String grade = gradeField.getText();

            try {
                int age = Integer.parseInt(ageField.getText());
                if (id.isEmpty() || name.isEmpty() || grade.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }
                if (studentDatabase.containsKey(id)) {
                    JOptionPane.showMessageDialog(dialog, "Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    studentDatabase.put(id, new Student(id, name, age, grade));
                    JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                    dialog.dispose();
                    updateTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid age. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void openUpdateStudentDialog(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = table.getValueAt(selectedRow, 0).toString();
        Student student = studentDatabase.get(id);

        // Create Update Student Dialog
        JDialog dialog = new JDialog(this, "Update Student", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField nameField = new JTextField(student.getName());
        JTextField ageField = new JTextField(String.valueOf(student.getAge()));
        JTextField gradeField = new JTextField(student.getGrade());
        JButton updateButton = new JButton("Update");

        dialog.add(new JLabel("Student ID: " + id));
        dialog.add(new JLabel());
        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(ageLabel);
        dialog.add(ageField);
        dialog.add(gradeLabel);
        dialog.add(gradeField);
        dialog.add(new JLabel());
        dialog.add(updateButton);

        updateButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String grade = gradeField.getText();

                if (name.isEmpty() || grade.isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }

                student.setName(name);
                student.setAge(age);
                student.setGrade(grade);

                JOptionPane.showMessageDialog(dialog, "Student updated successfully!");
                dialog.dispose();
                updateTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid age. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void deleteStudent(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = table.getValueAt(selectedRow, 0).toString();
        studentDatabase.remove(id);
        JOptionPane.showMessageDialog(this, "Student deleted successfully!");
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear table
        for (Student student : studentDatabase.values()) {
            tableModel.addRow(new Object[]{student.getId(), student.getName(), student.getAge(), student.getGrade()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystem app = new StudentManagementSystem();
            app.setVisible(true);
        });
    }
}

class Student {
    private final String id;
    private String name;
    private int age;
    private String grade;

    public Student(String id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
