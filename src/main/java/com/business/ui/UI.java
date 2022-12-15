// Tommy
// Dec 13, 2022
// Swim 4 Less App

package com.business.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.business.pool.SwimmingPool;
import com.business.util.Receipt;

public class UI {

    // Data fields
    private ArrayList<SwimmingPool> pools;
    // Constants
    public static final double TAX_RATE = 0.13, CONTRACTOR_RATE = 0.20, DEC_FORMATT = 100.00;
    public static final int CUSTOMER_INFO_SIZE = 4;
    public static Scanner input = new Scanner(System.in);

    // Constructor
    public UI() {
        pools = new ArrayList<SwimmingPool>();
    }

    // Main start method
    public void start() {
        // Veribles and objects
        Receipt receipt = new Receipt();
        char choice = ' ';
        String[] repNames = { "Henry", "John", "Bob", "Mary", "Jane", "Sue", "Tommy", "Sally", "Sara", "Sam" };
        String[] customerInfo = new String[CUSTOMER_INFO_SIZE];
        int num = 0;
        boolean isConractor = false;
        // Main do-while loop
        do {
            // Generate a random index for sales rep name
            num = (int) (Math.random() * repNames.length);
            System.out.print(
                    "Hello, welcome to Swim 4 Less, i'm " + repNames[num]
                            + ", i'll be your sales representative today. Please choose from the following options:\nA. General Public\nB. Contractor\nC. Exit: ");
            choice = Character.toUpperCase(input.next().charAt(0));
            // Switches to the correct method, General Public or Contractor or Exit
            switch (choice) {
                case 'A':
                    isConractor = false;
                    pos(isConractor, customerInfo);
                    receipt.print(pools, repNames[num], isConractor, calculateTotalCost(isConractor), customerInfo);
                    break;
                case 'B':
                    isConractor = true;
                    pos(isConractor, customerInfo);
                    receipt.print(pools, repNames[num], isConractor, calculateTotalCost(isConractor), customerInfo);
                    break;
                case 'C':
                    System.out.println("Thank you for shopping Swim 4 Less, have a nice day!");
                    sleep(3000);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
            // Clears the pools ArrayList and resets the numOfPools static variable to 0
            SwimmingPool.numOfPools = 0;
            pools.clear();
            clear();
        } while (choice != 'C');
    }

    // Main POS method
    public void pos(boolean isConractor, String[] customerInfo) {
        // Veribles and objects
        char choice = ' ';

        // Ask the user if they would like to add a pool until they say no
        do {
            showPrices();
            pools.add(addPool());
            System.out.print("Would you like to add another pool? (Y/N): ");
            choice = Character.toUpperCase(input.next().charAt(0));
            clear();
        } while (choice == 'Y');

        // List the pools the user has added
        System.out.println("Here are the pools you have added:\n");
        listPools();

        // Ask the user if they would like to change the dimensions of a pool
        do {
            System.out.print(
                    "\nWould you like to A. Process the order or would you like to B. Change the dimensions of a pool?: ");
            choice = Character.toUpperCase(input.next().charAt(0));
            clear();
            switch (choice) {
                case 'A':
                    break;
                case 'B':
                    listPools();
                    resizePool();
                    System.out.println("Here are the pools you have added:\n");
                    listPools();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        } while (choice != 'A');
        clear();
        getCustomerInfo(customerInfo);
        clear();
        listPoolCosts();
        System.out.println("The total cost of your order is: $"
                + calculateTotalCost(isConractor)
                + " (including tax and contractor discount if applicable) a receipt will be generated shortly. Thank you for shoping with Swim 4 Less, have a nice day!");
        sleep(7000);
    }

    // Helper methods
    // Add Pool Method
    public static SwimmingPool addPool() {
        // Veribles and objects
        SwimmingPool pool = new SwimmingPool();
        boolean valid = false;

        // Get the length of the pool
        do {
            try {
                System.out.print("Please enter the length of the pool #" + SwimmingPool.numOfPools + " in feet: ");
                pool.setLength(input.nextDouble());
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
                valid = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                input.nextLine();
                valid = false;
            }
        } while (!valid);

        // Get the width of the pool
        do {
            try {
                System.out.print("Please enter the width of the pool #" + SwimmingPool.numOfPools + " in feet: ");
                pool.setWidth(input.nextDouble());
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
                valid = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                input.nextLine();
                valid = false;
            }
        } while (!valid);

        return pool;
    }

    // Resize Pool Method
    public void resizePool() {
        // Veribles and objects
        int poolNum = 0;
        boolean valid = false;

        // Ask the user for the pool ID they would like to resize
        try {
            System.out.print("Please enter pool ID of the pool you would like to resize: #");
            poolNum = input.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, please try again.");
            input.nextLine();
            resizePool();
        }

        // Ask the user for the new width of the pool, use getPoolPosition to get the
        // index of the pool in the ArrayList
        do {
            try {
                System.out.print("Please enter the new length of the pool #" + poolNum + ": ");
                pools.get(getPoolPosition(poolNum)).setLength(input.nextDouble());
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
                valid = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                input.nextLine();
                valid = false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Cannot find pool #" + poolNum + ", please try again.");
                input.nextLine();
                valid = true;
            }
        } while (!valid);

        // Ask the user for the new width of the pool, use getPoolPosition to get the
        // index of the pool in the ArrayList
        do {
            try {
                System.out.print("Please enter the new width of the pool #" + poolNum + ": ");
                pools.get(getPoolPosition(poolNum)).setWidth(input.nextDouble());
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
                valid = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                input.nextLine();
                valid = false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Cannot find pool #" + poolNum + ", please try again.");
                input.nextLine();
                valid = true;
            }
        } while (!valid);
    }

    // Get customer info method
    public void getCustomerInfo(String[] customerInfo) {
        System.out.print("Please enter your first name: ");
        customerInfo[0] = input.next();
        System.out.print("Please enter your last name: ");
        customerInfo[1] = input.next();
        System.out.print("Please enter your phone number: ");
        customerInfo[2] = input.next();
        System.out.print("Please enter your email address: ");
        customerInfo[3] = input.next();
    }

    // List Pool method
    public void listPools() {
        int listNum = 0;
        for (SwimmingPool pool : pools) {
            listNum++;
            System.out.println(listNum + ". " + pool + ", pool id #" + pool.getPoolId());
        }
    }

    // List the Static Prices
    public static void showPrices() {
        System.out.println("\nThe Prices of Swimming Pool Supplies are Below:\nConcrete: $" + SwimmingPool.CONCRETE_COST
                + " per square foot\nFence: $" + SwimmingPool.FENCE_COST
                + " per linear foot\nSmall Pool (less or equal to 140 sq. ft.): $"
                + SwimmingPool.SMALL_POOL_COST + "\nLarge Pool (more than 140 sq. ft.): $"
                + SwimmingPool.LARGE_POOL_COST + "\n");
    }

    // List Pool Costs
    public void listPoolCosts() {
        // Veribles and objects
        int listNum = 0;
        System.out.println("\nThis is a cost breakdown for the pools that you bought: \n");
        for (SwimmingPool pool : pools) {
            listNum++;
            System.out.println("Pool # " + listNum + ". " + pool + ", pool id #" + pool.getPoolId()
                    + "\nAmount of concrete: " + pool.getConcreteArea() + " sq. ft., cost: $"
                    + pool.getConcreteCost() + "\nLength of fence: " + pool.getFenceLength() + " linear ft., cost: $"
                    + pool.getFenceCost() + "\nCost of pool: $" + pool.getPoolCost() + "\nTotal cost: $"
                    + pool.getTotalCost() + "\n");
        }
    }

    // Get Pool Position from pool id
    public int getPoolPosition(int poolId) {
        // Veribles and objects
        int position = 0;
        // Loop through the pools and find the pool with the pool id
        for (SwimmingPool pool : pools) {
            if (pool.getPoolId() == poolId) {
                return position;
            }
            position++;
        }
        return -1;
    }

    // Calculate Total Cost and checks if they are a contractor and gives them a
    // discount
    public double calculateTotalCost(boolean isContractor) {
        // Veribles and objects
        double totalCost = 0;
        // Loop through the pools and add the total cost of each pool
        for (SwimmingPool pool : pools) {
            totalCost += pool.getTotalCost();
        }
        // If they are a contractor and they bought more than 3 pools, give them a
        // discount
        if (isContractor && pools.size() > 3) {
            totalCost -= totalCost * CONTRACTOR_RATE;
        }
        // Add tax to the total cost
        totalCost += totalCost * TAX_RATE;
        return roundNumber(totalCost);
    }

    // Clear Screen Method
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Round Number Method
    public double roundNumber(double totalCost) {
        return Math.round(totalCost * DEC_FORMATT) / DEC_FORMATT;
    }

    // Sleep Method
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("An error occured.");
        }
    }
}
