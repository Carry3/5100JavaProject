package edu.neu.mgen;

import java.util.ArrayList;

public class App {
  public static boolean canMultiply(int[][] A, int[][] B) {
    boolean canMultiply = true;
    int ARowSize = A.length;
    int AColSize = A[0].length;
    int BRowSize = B.length;
    int BColSize = B[0].length;
    // check column size of A and row size of B are equal
    if (AColSize != BRowSize) {
      canMultiply = false;
    }
    // check each row of A has the same column size
    for (int i = 0; i < ARowSize; i++) {
      if (A[i].length != AColSize) {
        canMultiply = false;
      }
    }
    // check each row of B has the same column size
    for (int i = 0; i < BRowSize; i++) {
      if (B[i].length != BColSize) {
        canMultiply = false;
      }
    }
    if (!canMultiply) {
      System.out.println("A and B cannot be multiplied");
      return false;
    }
    return true;
  }

  public static ArrayList<ArrayList<Integer>> multiply(int[][] A, int[][] B) {
    if (!canMultiply(A, B)) {
      return null;
    }
    int ARowSize = A.length;
    int AColSize = A[0].length;
    int BColSize = B[0].length;
    ArrayList<ArrayList<Integer>> multipliedResult = new ArrayList<>();
    for (int i = 0; i < ARowSize; i++) {
      ArrayList<Integer> row = new ArrayList<>();
      for (int j = 0; j < BColSize; j++) {
        int sum = 0;
        for (int k = 0; k < AColSize; k++) {
          sum += A[i][k] * B[k][j];
        }
        row.add(sum);
      }
      multipliedResult.add(row);
    }
    return multipliedResult;
  }

  public static void main(String[] args) {
    int[][] A = { { 2, 3, 4 }, { 3, 4, 5 } }, B = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
    if (!canMultiply(A, B)) {
      return;
    }
    ArrayList<ArrayList<Integer>> multipliedResult = multiply(A, B);
    System.out.println(multipliedResult);
  }
}
