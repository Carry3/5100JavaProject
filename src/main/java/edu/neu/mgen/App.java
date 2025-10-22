package edu.neu.mgen;

public class App {
    public static void main(String[] args) {
        int[][] matrix = {
                { 10, 25, 9, 13 },
                { 12, 15, 18, 21 },
                { 14, 17, 20, 23 },
        };
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
                sum += matrix[i][j];
            }
            System.out.println();
        }
        System.out.println("Sum of the matrix: " + sum);
    }
}
