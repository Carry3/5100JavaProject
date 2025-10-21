package edu.neu.mgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.sql.*;

public class App {
  public static void main(String[] args) {// --- 第1部分：读取文件 ---
    try (BufferedReader reader = new BufferedReader(new FileReader("my_test_file.txt"))) {
      String line = reader.readLine();
      System.out.println("File content: " + line);
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }

    // --- 第2部分：从终端写入文件 ---
    Scanner scanner = new Scanner(System.in);
    System.out.print("Please input the phrase you want to write to the file: ");
    String phrase = scanner.nextLine();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("my_test_file.txt", true))) {
      writer.newLine();
      writer.write(phrase);
      System.out.println("Phrase written to file.");
    } catch (IOException e) {
      System.out.println("Error writing to file: " + e.getMessage());
    }

    // --- 第3部分：数据库查询 --- SCDB
    String url = "jdbc:postgresql://127.0.0.1:5432/mydb";
    String user = "myuser";
    String password = "mypassword";

    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, user, password);
      conn.setAutoCommit(false);

      System.out.println("=== all students ===");
      String selectStudentsSQL = "SELECT sno, sname, age, gender FROM student";
      Statement stmt = conn.createStatement();
      ResultSet rsStudents = stmt.executeQuery(selectStudentsSQL);
      while (rsStudents.next()) {
        System.out.println("Sno: " + rsStudents.getString("sno") +
            " | Name: " + rsStudents.getString("sname") +
            " | Age: " + rsStudents.getInt("age") +
            " | Gender: " + rsStudents.getString("gender"));
      }
      rsStudents.close();

      System.out.println("\n=== all courses ===");
      String selectCoursesSQL = "SELECT cno, cname FROM course";
      ResultSet rsCourses = stmt.executeQuery(selectCoursesSQL);
      while (rsCourses.next()) {
        System.out.println("Cno: " + rsCourses.getString("cno") +
            " | Course Name: " + rsCourses.getString("cname"));
      }
      rsCourses.close();
      stmt.close();

      System.out.print("\nPlease input student sno (e.g. S2025001): ");
      String sno = scanner.nextLine();

      System.out.print("Please input course no (e.g. C101): ");
      String cno = scanner.nextLine();

      String selectSQL = "SELECT score FROM sc WHERE sno = ? AND cno = ?";
      PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
      selectStmt.setString(1, sno);
      selectStmt.setString(2, cno);

      ResultSet rs = selectStmt.executeQuery();

      if (rs.next()) {
        int oldScore = rs.getInt("score");
        System.out.println("original score: " + oldScore);

        System.out.print("Please input the score you want to modify: ");
        int newScore = scanner.nextInt();
        scanner.nextLine();

        String updateSQL = "UPDATE sc SET score = ? WHERE sno = ? AND cno = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
        updateStmt.setInt(1, newScore);
        updateStmt.setString(2, sno);
        updateStmt.setString(3, cno);
        updateStmt.executeUpdate();
        System.out.println("modified score (not committed): " + newScore);

        ResultSet rsCheck = selectStmt.executeQuery();
        if (rsCheck.next()) {
          System.out.println("re-query score: " + rsCheck.getInt("score"));
        }
        rsCheck.close();
        updateStmt.close();

        conn.rollback();
        System.out.println("rolled back, score restored to: " + oldScore);
      } else {
        System.out.println("no score found for this student and course.");
      }

      selectStmt.close();
      scanner.close();
      conn.close();

    } catch (SQLException e) {
      System.out.println("database error: " + e.getMessage());
    } finally {
      try {
        if (conn != null && !conn.isClosed())
          conn.close();
      } catch (SQLException ex) {
        System.out.println("error closing connection: " + ex.getMessage());
      }
    }
  }
}
