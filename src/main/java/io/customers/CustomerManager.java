package io.customers;

import java.util.Scanner;

public class CustomerManager {

    private MongoDbManager dbManager;
    private Scanner input;

    public CustomerManager (Scanner input) {
        this.input = input;
        this.dbManager = new MongoDbManager();
    }

    public boolean processInput(int selection) {
        boolean result = true;

        if (!valid(selection)) {
            System.out.println("Invalid entry!");
            System.out.println();
        } else {
            switch (selection) {
                case 1 -> displayAllCustomers();
                case 2 -> addCustomer();
                case 3 -> deleteCustomer();
                case 4 -> editCustomer();
                case 5 -> searchCustomer();
                case 6 -> result = false;
            }
        }

        return result;
    }

    public void editCustomer() {

        System.out.println("Enter the ID of the customer that you would like to update:");
        int customerID = Integer.parseInt(input.nextLine());
        System.out.println("Enter the customer's first name:");
        String firstName = input.nextLine();
        System.out.println("Enter the customer's last name:");
        String lastName = input.nextLine();
        System.out.println("Enter the customer's email:");
        String email = input.nextLine();
        System.out.println("Enter the customer's phone number:");
        String phone = input.nextLine();

        dbManager.updateCustomer(customerID, firstName, lastName, email, phone);
    }

    private void searchCustomer() {
        Customer customer;

        System.out.println("Would you like to search by name or ID?:");
        System.out.println("[1] Search by name");
        System.out.println("[2] Search by ID");
        int selection = Integer.parseInt(input.nextLine());
        if (selection == 1) {
            System.out.println("Enter the first or last name of the customer:");
            String name = input.nextLine();
            customer = dbManager.searchCustomerByName(name);
        } else {
            System.out.println("Enter the ID of the customer:");
            int id = Integer.parseInt(input.nextLine());
            customer = dbManager.searchCustomerByID(id);
        }

        displayCustomer(customer);
    }

    private void addCustomer() {

        System.out.println("Enter the customer's first name:");
        String firstName = input.nextLine();
        System.out.println("Enter the customer's last name:");
        String lastName = input.nextLine();
        System.out.println("Enter the customer's email:");
        String email = input.nextLine();
        System.out.println("Enter the customer's phone number:");
        String phone = input.nextLine();

        Customer customer = new Customer(dbManager.getTotalCustomers() + 1, firstName, lastName, email, phone);

        dbManager.insertCustomer(customer);
        System.out.println("The customer has been added!");
        System.out.println();
    }

    private void deleteCustomer() {
        System.out.println("Please enter the ID of the customer to delete: ");
        int customerID = Integer.parseInt(input.nextLine());
        dbManager.deleteEmployee(customerID);
        System.out.println("Customer with ID #" + customerID + " has been removed.");

        System.out.println();
    }

    private void displayAllCustomers() {
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "FIRST NAME", "LAST NAME", "EMAIL", "PHONE");

        dbManager.getAllCustomers().forEach(customer -> {
            table.addRow(String.valueOf(customer.getCustomerID()), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone());
        });
        table.print();
        System.out.println();
    }

    private void displayCustomer(Customer customer) {
        CommandLineTable table = new CommandLineTable();
        table.setShowVerticalLines(true);
        table.setHeaders("ID", "FIRST NAME", "LAST NAME", "EMAIL", "PHONE");
        table.addRow(String.valueOf(customer.getCustomerID()), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone());
        table.print();
        System.out.println();
    }

    private boolean valid(int selection) {
        return (selection >= 1) && (selection <= 6);
    }

}
