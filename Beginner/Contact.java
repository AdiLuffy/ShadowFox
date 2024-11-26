import java.util.ArrayList;
import java.util.Scanner;

public class Contact {
    private static final Scanner scanner = new Scanner(System.in);
    private final ArrayList<ContactEntry> contacts = new ArrayList<>();

    public static void main(String[] args) {
        Contact contactManager = new Contact();
        boolean continueManaging = true;

        while (continueManaging) {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> contactManager.addContact();
                case 2 -> contactManager.viewContacts();
                case 3 -> contactManager.updateContact();
                case 4 -> contactManager.deleteContact();
                case 5 -> continueManaging = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Contact Management System!");
    }

    private void addContact() {
        System.out.print("Enter contact name: ");
        String name = scanner.next();
        System.out.print("Enter phone number: ");
        String phone = scanner.next();
        System.out.print("Enter email address: ");
        String email = scanner.next();

        ContactEntry newContact = new ContactEntry(name, phone, email);
        contacts.add(newContact);
        System.out.println("Contact added successfully!");
    }

    private void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("\nContacts List:");
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    private void updateContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to update.");
            return;
        }
        viewContacts();
        System.out.print("Enter the contact number to update: ");
        int contactIndex = scanner.nextInt() - 1;

        if (contactIndex >= 0 && contactIndex < contacts.size()) {
            System.out.print("Enter new name (leave blank to keep current): ");
            String newName = scanner.next();
            System.out.print("Enter new phone (leave blank to keep current): ");
            String newPhone = scanner.next();
            System.out.print("Enter new email (leave blank to keep current): ");
            String newEmail = scanner.next();

            ContactEntry contact = contacts.get(contactIndex);
            if (!newName.isBlank()) contact.setName(newName);
            if (!newPhone.isBlank()) contact.setPhone(newPhone);
            if (!newEmail.isBlank()) contact.setEmail(newEmail);

            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    private void deleteContact() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts to delete.");
            return;
        }
        viewContacts();
        System.out.print("Enter the contact number to delete: ");
        int contactIndex = scanner.nextInt() - 1;

        if (contactIndex >= 0 && contactIndex < contacts.size()) {
            contacts.remove(contactIndex);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    // Inner ContactEntry Class to Represent a Contact
    private static class ContactEntry {
        private String name;
        private String phone;
        private String email;

        public ContactEntry(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        public void setName(String name) { this.name = name; }
        public void setPhone(String phone) { this.phone = phone; }
        public void setEmail(String email) { this.email = email; }

        @Override
        public String toString() {
            return "Name: " + name + ", Phone: " + phone + ", Email: " + email;
        }
    }
}