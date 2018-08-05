/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessor;

import imageprocessor.ImageProcessor;
import java.io.IOException;

/**
 *
 * @author 14R PC
 */
public class Recoverer {
    public int primeNumber;
    int column, row;
    
    public void MergeImage(String[] filePath, int[] xValues) {
        
        ImageProcessor[] imageProcessor = new ImageProcessor[filePath.length];
        
        int[] redVal = new int[filePath.length];
        int[] greenVal = new int[filePath.length];
        int[] blueVal = new int[filePath.length];
        
        
        for(int count = 0; count < filePath.length; count++) {
            imageProcessor[count] = new ImageProcessor();
            // read original image
            try {
                // call function read image
                imageProcessor[count].ReadImage(filePath[count]);
            } catch (IOException e) {
                // print the error message
                System.out.println("Error = " +e);
            }
        }
        
        column = imageProcessor[0].getColumn();
        row = imageProcessor[0].getRow();
        int[][] matrixRed = new int[column][row];
        int[][] matrixGreen = new int[column][row];
        int[][] matrixBlue = new int[column][row];
        
        for(int j = 0; j < row; j++) {
            for(int i = 0; i < column; i++) {
                
                for(int shares = 0; shares < filePath.length; shares++) {
                    redVal[shares] = imageProcessor[shares].redOri[i][j];
                    greenVal[shares] = imageProcessor[shares].greenOri[i][j];
                    blueVal[shares] = imageProcessor[shares].blueOri[i][j];
                }
                matrixRed[j][i] = getNewVal(redVal, xValues);
                matrixGreen[j][i] = getNewVal(greenVal, xValues);
                matrixBlue[j][i] = getNewVal(blueVal, xValues);
            }
        }
        imageProcessor[0].setNewPixel(matrixRed, matrixGreen, matrixBlue);
        // write copy image
        try {
            // call function write image
            imageProcessor[0].WriteImage("D:\\Skripsi\\Material\\Hasil.png");
        } catch (IOException e) {
            // print the error message
            System.out.println("Error = " +e);
        }
    }
    
    private int getNewVal(int[] colorVal, int[] xValues) {
        int output = 0;
        int tempBelow = 1;
        int tempAbove = 1;
        
        for(int y = 0; y < colorVal.length; y++) {
            for(int x = 0; x < xValues.length; x++) {
                if(x != y) {
                    tempAbove *= (0-xValues[x]);
                    tempBelow *= (xValues[y] - xValues[x]);
                }
            }
            output += ((tempAbove/tempBelow)*colorVal[y]);
            tempAbove = tempBelow = 1;
        }
        output %= primeNumber;
        if (output < 0) {
            output += primeNumber;
        }
        return output;
    }
}
