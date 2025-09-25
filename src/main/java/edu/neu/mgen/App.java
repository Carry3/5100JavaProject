package edu.neu.mgen;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    // 记录开始时间
    System.out.print("Enter any word:");
    long startTime = System.currentTimeMillis();
    
    // 读取用户输入
    String input = scanner.nextLine();
    long endTime = System.currentTimeMillis();
    
    // 计算反应时间（秒）
    double reactionTime = (endTime - startTime) / 1000.0;
    
    // 检查是否输入了空字符串
    if (input.trim().isEmpty()) {
      System.out.println("You did not enter any word");
      scanner.close();
      return;
    }
    
    // 计算单词长度
    int wordLength = input.length();
    
    // 分类单词
    String category;
    if (wordLength <= 5) {
      category = "short";
    } else if (wordLength <= 10) {
      category = "medium";
    } else {
      category = "long";
    }
    
    // 输出结果
    System.out.println("Your word is " + input);
    System.out.println("It is a " + category + " word");
    System.out.println("The length of the word is " + wordLength);
    System.out.println("Your reaction time is " + reactionTime + " seconds");
    
    scanner.close();
  }
}
