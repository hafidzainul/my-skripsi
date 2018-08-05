/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry.shamir.secret.sharing;

import imageprocessor.ImageProcessor;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author 14R PC
 */
public class ShamirSecretSharingScheme {
    int column, row, shares, threshold, primeNumber;
    String filePath = "D:\\Skripsi\\Material\\220px-Lenna.png";
    
    public void DispartImage(int[] xvalues) {
        int counter = 0;
        ImageProcessor[] imageProcessor = new ImageProcessor[shares];
        int redVal, greenVal, blueVal;
        
        for(int i = 0; i < shares; i++) {
            // read original image
            try {
                imageProcessor[i] = new ImageProcessor();
                // call function read image
                imageProcessor[i].ReadImage(filePath);
            } catch (IOException e) {
                // print the error message
                System.out.println("Error = " +e);
            }
        }
        
        // get column and row of original image
        column = imageProcessor[0].getColumn();
        row = imageProcessor[0].getRow();
        int[][] matrixRed = imageProcessor[0].getRedMatrix();
        int[][] matrixGreen = imageProcessor[0].getGreenMatrix();
        int[][] matrixBlue = imageProcessor[0].getBlueMatrix();
        
        for(int j = 0; j < row; j++) {
            for(int i = 0; i < column; i ++) {
                int[] coefRed = setCoefficient(threshold);
                int[] coefGreen = setCoefficient(threshold);
                int[] coefBlue = setCoefficient(threshold);
                
                for(int index = 0; index < shares; index++) {
                    redVal = calPoly(matrixRed[j][i], coefRed, xvalues[index]);
                    greenVal = calPoly(matrixGreen[j][i], coefGreen, xvalues[index]);
                    blueVal = calPoly(matrixBlue[j][i], coefBlue, xvalues[index]);
                    
                    imageProcessor[index].setColorVal(i, j, redVal, greenVal, blueVal);
                }
            }
        }
        
        while (counter < shares) {
            // write copy image
            try {
                // call function write image
                imageProcessor[counter].WriteImage("D:\\Skripsi\\Material\\SSSShares("+ (counter+1) +").png");
            } catch (IOException e) {
                // print the error message
                System.out.println("Error = " +e);
            }
            counter++;
        }   // end of while
    }
    
    private int calPoly(int colorVal, int[] coef, int x) {
        for(int i = 0; i < coef.length; i++) {
            colorVal += (int) (coef[i] * Math.pow(x, i+1));
        }
        colorVal %= primeNumber;
        return colorVal;
    }
    
    // generating random coefficient polynomial function
    private int[] setCoefficient(int threshold) {
        Random rng = new Random();
        
        int[] coef = new int[threshold-1];
        int range = (int) Math.pow(10, 6);
        
        for(int i = 0; i < coef.length; i++) {
            coef[i] = rng.nextInt(range);
        }
        return coef;
    }
}
