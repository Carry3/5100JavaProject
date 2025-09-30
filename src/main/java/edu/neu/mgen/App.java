package edu.neu.mgen;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Enter any word:");
    // get the start time
    long startTime = System.currentTimeMillis();
    
    // read the user input
    String input = scanner.nextLine();
    long endTime = System.currentTimeMillis();
    
    // calculate the reaction time (seconds)
    double reactionTime = (endTime - startTime) / 1000.0;
    
    // check if the input is an empty string
    if (input.trim().isEmpty()) {
      System.out.println("You did not enter any word");
      scanner.close();
      return;
    }
    
    // calculate the length of the word
    int wordLength = input.length();
    
    // classify the word
    String category;
    if (wordLength <= 5) {
      category = "short";
    } else if (wordLength <= 10) {
      category = "medium";
    } else {
      category = "long";
    }
    
    System.out.println("Your word is " + input);
    System.out.println("It is a " + category + " word");
    System.out.println("The length of the word is " + wordLength);
    System.out.println("Your reaction time is " + reactionTime + " seconds");
    
    scanner.close();
  }
}
