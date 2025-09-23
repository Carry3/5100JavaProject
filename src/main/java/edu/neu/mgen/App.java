package edu.neu.mgen;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    System.out.println("It is HW4!");
    Scanner scanner = new Scanner(System.in);

    // enter int variables
    System.out.print("Enter first int value: ");
    int int1 = scanner.nextInt();
    System.out.print("Enter second int value: ");
    int int2 = scanner.nextInt();

    // enter long variables
    System.out.print("Enter first long value: ");
    long long1 = scanner.nextLong();
    System.out.print("Enter second long value: ");
    long long2 = scanner.nextLong();

    // enter double variables
    System.out.print("Enter first double value: ");
    double double1 = scanner.nextDouble();
    System.out.print("Enter second double value: ");
    double double2 = scanner.nextDouble();

    // enter boolean variables
    System.out.print("Enter first boolean (true/false): ");
    boolean bool1 = scanner.nextBoolean();
    System.out.print("Enter second boolean (true/false): ");
    boolean bool2 = scanner.nextBoolean();

    // enter char variables
    System.out.print("Enter first char: ");
    char char1 = scanner.next().charAt(0);
    System.out.print("Enter second char: ");
    char char2 = scanner.next().charAt(0);

    // type conversion
    long intToLong1 = (long)int1;
    long intToLong2 = (long)int2;

    int longToInt1 = (int)long1;
    int longToInt2 = (int)long2;

    // print conversion results
    System.out.println("\nConverted int to long:");
    System.out.println("int1 -> long: " + intToLong1);
    System.out.println("int2 -> long: " + intToLong2);

    System.out.println("\nConverted long to int:");
    System.out.println("long1 -> int: " + longToInt1);
    System.out.println("long2 -> int: " + longToInt2);

    // arithmetic operations
    System.out.println("\nArithmetic Operations:");
    System.out.println("int1 + int2 = " + (int1 + int2));
    System.out.println("long1 - long2 = " + (long1 - long2));
    System.out.println("double1 * double2 = " + (double1 * double2));
    System.out.println("int1 / int2 = " + (int1 / int2)); // 整数除法
    System.out.println("double1 / double2 = " +
                       (double1 / double2)); // 浮点除法
    System.out.println("double1 - int1 = " + (int1 % int2));

    // logical operations
    System.out.println("\nLogical Operations:");
    System.out.println("bool1 && bool2 = " + (bool1 && bool2));
    System.out.println("bool1 || bool2 = " + (bool1 || bool2));
    System.out.println("!bool1 = " + (!bool1));
    System.out.println("int1 > int2 || long1 > lon2 = " +
                       (int1 > int2 || long1 > long2));

    // Char Operations（ASCII value addition and subtraction）
    System.out.println("\nChar Operations:");
    System.out.println("char1 + char2 = " + (char1 + char2) +
                       " (as ASCII sum)");
    System.out.println("char1 - char2 = " + (char1 - char2) +
                       " (as ASCII difference)");
    System.out.println("int1: " + int1 + ", char1: " + char1 +
                       ", int1 + char1 = " + (int1 + char1) +
                       " (char1 as ASCII value)");
    scanner.close();
  }
}
