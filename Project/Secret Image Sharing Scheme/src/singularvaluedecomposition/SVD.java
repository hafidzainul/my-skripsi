/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singularvaluedecomposition;

/**
 * @author 14R PC
 * Copyright © 2000–2017, Robert Sedgewick and Kevin Wayne.
 */
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import Jama.util.*;
import java.io.IOException;

public class SVD {
    
//    private double[][] U, V;
//    private int[][] W;
//    private double[] d;
    public Matrix A, U, S, V;
    
    public static void libSVD() { 

        // create M-by-N matrix that doesn't have full rank
        int M = 8, N = 5;
        Matrix B = Matrix.random(5, 3);
        Matrix A = Matrix.random(M, N).times(B).times(B.transpose());
        System.out.println("A = ");
        A.print(9, 6);

        // compute the singular vallue decomposition
        System.out.println("A = U S V^T");
        System.out.println();
        SingularValueDecomposition s = A.svd();
        System.out.println("U = ");
        Matrix U = s.getU();
        U.print(9, 6);
        System.out.println("Sigma = ");
        Matrix S = s.getS();
        S.print(9, 6);
        System.out.println("V = ");
        Matrix V = s.getV();
        V.print(9, 6);
        System.out.println("rank = " + s.rank());
        System.out.println("condition number = " + s.cond());
        System.out.println("2-norm = " + s.norm2());

        // print out singular values
        System.out.println("singular values = ");
        Matrix svalues = new Matrix(s.getSingularValues(), 1);
        svalues.print(9, 6);
        
        System.out.println("");
        Matrix J = U.times(S).times(V.transpose());
        J.print(9, 6);
    }
    
    public void computeSVD(double[][] matrix) {
        A = Matrix.constructWithCopy(matrix);
        SingularValueDecomposition s = A.svd();
        U = s.getU();
        S = s.getS();
        V = s.getV();
    }
    
    public double[][] inverseSVD() {
        A = U.times(S).times(V.transpose());
        return A.getArray();
    }
    
    public double[][] inverseSVD(double[][] matrixSigma) {
        Matrix Sigma = Matrix.constructWithCopy(matrixSigma);
        A = U.times(Sigma).times(V.transpose());
        return A.getArray();
    }
    
    public double[][] getU() {
        return U.getArray();
    }
    
    public double[][] getS() {
        return S.getArray();
    }
    
    public int getSigmaCol() {
        return S.getColumnDimension();
    }
    
    public int getSigmaRow() {
        return S.getRowDimension();
    }
    
    public double getSigmaVal(int u, int v) {
        return S.get(u, v);
    }
    
    public void setSigmaVal(int u, int v, double val) {
        S.set(u, v, val);
    }
    
    public double[][] getVt() {
        Matrix Vt = V.transpose();
        return Vt.getArray();
    }
    
//    public void mySVD(int[][] matrixIm) {
//        W = multiplication(matrixIm, transpose(matrixIm));
//        
//        int column = matrixIm[0].length;
//        int row = matrixIm.length;
//        int rc = Math.min(row,column);
//        
//        U = new double [row][rc];
//        d = new double [Math.min(row+1,column)];
//        V = new double [column][column];
//    }
//    
//    private int[][] transpose(int[][] matrixA) {
//        int[][] matrixAt = new int[matrixA[0].length][matrixA.length];
//        for(int height = 0; height < matrixAt.length; height++) {
//            for(int width = 0; width < matrixAt[0].length; width++) {
//                matrixAt[height][width] = matrixA[width][height];
//            }
//        }
//        return matrixAt;
//    }
//    
//    private int[][] multiplication(int[][] matrixA, int[][] matrixB) {
//        int[][] output = new int[matrixA.length][matrixB[0].length];
//        int[] temp = new int[matrixB.length];
//        for(int j = 0; j < output.length; j++) {
//            for(int i = 0; i < output[0].length; i++) {
//                for(int x = 0; x < temp.length; x++) {
//                    temp[x] = matrixB[x][i];
//                }
//                for(int a = 0; a < matrixA[j].length; a++) {
//                    output[j][i] += (matrixA[j][a]*temp[a]);
//                }
//            }
//        }
//        return output;
//    }
//    
//    public double[][] getU() {
//        return U;
//    }
//    
//    public double[][] getV() {
//        return V;
//    }
//    
//    public double[] getD() {
//        return d;
//    }
//    
//    public void print(int[][] matrixA) {
//        for (int[] matrix1 : matrixA) {
//            for (int i = 0; i < matrixA[0].length; i++) {
//                System.out.print(matrix1[i] + "\t");
//            }
//            System.out.println("");
//        }
//    }
}
