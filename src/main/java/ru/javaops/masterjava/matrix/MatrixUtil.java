package ru.javaops.masterjava.matrix;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    // TODO implement parallel multiplication matrixA*matrixB
    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        executor.submit(() -> IntStream.range(0, matrixSize)
                .parallel()
                .forEach(row -> multiplyMatrix(matrixA, matrixB, matrixSize, matrixC, row)))
                .get();

        return matrixC;
    }


    /*
     // TODO optimize by https://habrahabr.ru/post/114797/
     public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
         final int matrixSize = matrixA.length;
         final int[][] matrixC = new int[matrixSize][matrixSize];

         for (int i = 0; i < matrixSize; i++) {
             for (int j = 0; j < matrixSize; j++) {
                 int sum = 0;
                 for (int k = 0; k < matrixSize; k++) {
                     sum += matrixA[i][k] * matrixB[k][j];
                 }
                 matrixC[i][j] = sum;
             }
         }
         return matrixC;
     }
   */

    /**
     * optimized multiply matrix
     *
     * @param matrixA
     * @param matrixB
     * @return
     */
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            multiplyMatrix(matrixA, matrixB, matrixSize, matrixC, i);
        }

        return matrixC;
    }

    private static void multiplyMatrix(int[][] matrixA, int[][] matrixB, int matrixSize, int[][] matrixC, int row) {
        int thatColumn[] = new int[matrixB.length];
        for (int k = 0; k < matrixSize; k++) {
            thatColumn[k] = matrixB[k][row];
        }

        for (int i = 0; i < matrixSize; i++) {
            int thisRow[] = matrixA[i];
            int summand = 0;
            for (int k = 0; k < matrixSize; k++) {
                summand += thisRow[k] * thatColumn[k];
            }
            matrixC[i][row] = summand;
        }
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
