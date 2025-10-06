package edu.neu.mgen;

import java.util.ArrayList;
import java.util.Arrays;

public class App {
  public static void main(String[] args) {
    // Part1
    System.out.println("Lab1 Part1:");
    int[] x = { 3, 9, 4, 16, 25 }, y = { 5, 10, 15, 20, 25 };
    int[] z = new int[5];
    for (int i = 0; i < 5; i++) {
      z[i] = Math.max(x[i], y[i]);
    }
    System.out.println("Array x = " + Arrays.toString(x).replace("[", "{").replace("]", "}"));
    System.out.println("Array y = " + Arrays.toString(y).replace("[", "{").replace("]", "}"));
    System.out.println("Array z = max(x, y) = " + Arrays.toString(z).replace("[", "{").replace("]", "}"));

    // Part2
    System.out.println("Lab1 Part2:");
    ArrayList<String> names = new ArrayList<>();
    names.add("Weiming");
    names.add("Jack");
    names.add("John");
    names.add("Avril");
    names.add("William");
    ArrayList<String> switchedNames = new ArrayList<>();
    for (String name : names) {
      switchedNames.add(name.substring(name.length() - 1).toUpperCase() + name.substring(1, name.length() - 1)
          + name.substring(0, 1).toLowerCase());
    }
    System.out.println("Names = " + names.toString().replace("[", "{").replace("]", "}"));
    System.out.println("Names (switched) = " + switchedNames.toString().replace("[", "{").replace("]", "}"));
  }
}
