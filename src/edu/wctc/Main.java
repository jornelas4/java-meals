package edu.wctc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {



    private Scanner keyboard;
    private Cookbook cookbook;

    private static List<MealType> mealTypeList = new ArrayList<>();

    private void doControlBreak() {

        List<Meal> list = cookbook.getMeals();


        int count = 0;
        int max = 0;
        int min = 0;
        int total = 0;
        double mean = 0;
        int median = 0;


        String currentMeal = cookbook.getMeals().get(0).getMealType().getPrettyPrint();

        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n", "MealType", "Number of Meals", "Total","Mean", "Min", "Max", "Median");

        for ( Meal meal: list) {

            if (!currentMeal.equals(meal.getMealType().getPrettyPrint())) {

//


                System.out.printf("%-20s%-20d%-20d%-20f%-20d%-20d%-20d%n", currentMeal, count, total, mean, min, max, median);

                currentMeal = meal.getMealType().getPrettyPrint();

                count = 0;
                max = 0;
                min = 0;
                total = 0;



            }
            count++;
            if (meal.getCalories() > max) {
                max = meal.getCalories();
            }
            if (min == 0 || meal.getCalories() < min) {
                min = meal.getCalories();
            }
            total += meal.getCalories();
            mean = total / count;



        }

        System.out.printf("%-20s%-20d%-20d%-20f%-20d%-20d%-20d", currentMeal, count, total, mean, min, max, median);
    }


    public Main() {
        keyboard = new Scanner(System.in);
        cookbook = new Cookbook();

        try {
            System.out.println("Reading in meals information from file...");
            List<String> fileLines = Files.readAllLines(Paths.get("meals_data.csv"));

            for (String line : fileLines) {
                String[] fields = line.split(",");
                cookbook.addMeal(fields[0], fields[1], fields[2]);
            }

            runMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }



    private void listByMealType() {
        // Default value pre-selected in case
        // something goes wrong w/user choice
        MealType mealType = MealType.DINNER;

        System.out.println("Which meal type? ");

        // Generate the menu using the ordinal value of the enum
        for (MealType m : MealType.values()) {
            System.out.println((m.ordinal() + 1) + ". " + m.getPrettyPrint());
        }

        System.out.print("Please enter your choice: ");
        String ans = keyboard.nextLine();

        try {
            int ansNum = Integer.parseInt(ans);
            if (ansNum < MealType.values().length) {
                mealType = MealType.values()[ansNum - 1];
            }
        } catch (NumberFormatException nfe) {
            System.out.println(String.format("Invalid meal type %s. Defaulted to %s.",
                    ans, mealType.getPrettyPrint()));
        }

        cookbook.printMealsByType(mealType);
    }

    private void printMenu() {
        System.out.println("");
        System.out.println("Select Action");
        System.out.println("1. List All Items");
        System.out.println("2. List All Items by Meal");
        System.out.println("3. Search by Meal Name");
        System.out.println("4. Do Control Break");
        System.out.println("5. Exit");
        System.out.print("Please enter your choice: ");
    }

    private void runMenu() {
        boolean userContinue = true;

        while (userContinue) {
            printMenu();

            String ans = keyboard.nextLine();
            switch (ans) {
                case "1":
                    cookbook.printAllMeals();
                    break;
                case "2":
                    listByMealType();
                    break;
                case "3":
                    searchByName();
                    break;
                case "4":
                    doControlBreak();
                    break;
                case "5":
                    userContinue = false;
                    break;
            }
        }

        System.out.println("Goodbye");
        System.exit(0);
    }

    private void searchByName() {
        keyboard.nextLine();
        System.out.print("Please enter name to search: ");
        String ans = keyboard.nextLine();
        cookbook.printByNameSearch(ans);
    }
}


