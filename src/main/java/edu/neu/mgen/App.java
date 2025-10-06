package edu.neu.mgen;

import java.util.Arrays;

public class App {
  public static String reverseString(String str) {
    return new StringBuilder(str).reverse().toString();
  }

  public static String[] reverseNames(String[] names) {
    String[] reversedNames = new String[names.length];
    for (int i = 0; i < names.length; i++) {
      // reverse the string
      String reversedString = reverseString(names[i].toLowerCase());
      // capitalize the first letter
      reversedNames[i] = reversedString.substring(0, 1).toUpperCase() + reversedString.substring(1);
    }
    return reversedNames;
  }

  public static void main(String[] args) {
    String[] names = { "Anne", "John", "Alex", "Jessica" };
    String[] reversedNames = reverseNames(names);
    System.out.println("Names = " + Arrays.toString(names).replace("[", "{").replace("]", "}"));
    System.out.println("Reversed Names = " + Arrays.toString(reversedNames).replace("[", "{").replace("]", "}"));
    names = new String[] { "Sun", "Mercury", "Venis", "Earth", "Mars", "Jupiter" };
    reversedNames = reverseNames(names);
    System.out.println("Names = " + Arrays.toString(names).replace("[", "{").replace("]", "}"));
    System.out.println("Reversed Names = " + Arrays.toString(reversedNames).replace("[", "{").replace("]", "}"));
  }
}
