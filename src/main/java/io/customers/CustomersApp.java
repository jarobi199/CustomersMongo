package io.customers;

import java.util.Scanner;

public class CustomersApp {

    private Scanner input = new Scanner(System.in);
    private CustomerManager manager = new CustomerManager(input);

    public static void main(String[] args) {
        CustomersApp app = new CustomersApp();
        app.run();
    }

    private void run() {
        displayAndProcess();
    }


    public void displayAndProcess() {
        int selection;

        System.out.println("============================================");
        System.out.println("WELCOME TO THE CUSTOMER MANAGER APPLICATION!");
        System.out.println("============================================");

        do {
            System.out.println("[1] List all customers");
            System.out.println("[2] Add a customer");
            System.out.println("[3] Delete a customer");
            System.out.println("[4] Edit a customer");
            System.out.println("[5] Search for a customer");
            System.out.println("[6] Exit.");
            System.out.println();
            System.out.println("Please enter your selection:");

            selection = Integer.parseInt(input.nextLine());
        }
        while (manager.processInput(selection));

        System.out.println();
        System.out.println("GOODBYE!");
        input.close();
    }
}
