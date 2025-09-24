package edu.neu.mgen;

import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    String str = "Oakland";
    System.out.println("the length of str is: " + str.length());
    System.out.println("the index 2 character of str is: " + str.charAt(2));
    System.out.println("get 'land' from str is: " + str.substring(4));
    System.out.println("UpperCase of str: " + str.toUpperCase());
    
    int[] abc = {1, 3, 5, 2, 5};
    System.out.println("the length of abc is: " + abc.length);
    System.out.println("the last element of abc is: " + abc[abc.length - 1]);
    
    ArrayList<String> list = new ArrayList<>();
    list.add("Austin");
    list.add("Houston");
    list.add("Oakland");
    list.add("Paris");
    list.add("San Francisco");
    list.add("Seattle");
    // before remove
    System.out.println(list.toString());
    list.remove("Paris");
    // after remove
    System.out.println(list.toString());
  }
}
