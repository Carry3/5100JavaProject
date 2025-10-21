package edu.neu.mgen;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int number = 0;
    try {
      System.out.print("Enter a number: ");
      number = scanner.nextInt(); // possible input error here
      System.out.println("You entered: " + number);
    } catch (Exception e) {
      System.out.println("Error: Invalid input. Please enter a valid number.");
    } finally {
      scanner.close();
    }

    try {
      int number2 = 10 / number;
      System.out.println("Result: " + number2);
    } catch (ArithmeticException e) {
      System.out.println("Error: Division by zero.");
    } catch (Exception e) {
      System.out.println("Error: Invalid input. Please enter a valid number.");
    }

    System.out.println("Program finished safely.");
  }
}
