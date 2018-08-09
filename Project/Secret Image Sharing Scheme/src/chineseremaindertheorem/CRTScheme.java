/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chineseremaindertheorem;

import imageprocessor.ImageProcessor;
import java.io.IOException;

/**
 *
 * @author 14R PC
 */
public class CRTScheme {
    public int column, row, shares, threshold, primeNumber;
    int counter = 0;
    String filePath = "D:\\Skripsi\\Material\\220px-Lenna.png";
    
    public void dispartImage(int[] mod) throws IOException {
        int counter = 0;
        ImageProcessor[] imageProcessor = new ImageProcessor[shares];
        int redVal, greenVal, blueVal;
        
        for(int i = 0; i < shares; i++) {
            // read original image
            imageProcessor[i] = new ImageProcessor();
            // call function read image
            imageProcessor[i].ReadImage(filePath);
        }
        
        column = imageProcessor[0].getColumn();
        row = imageProcessor[0].getRow();
        int[][] matrixRed = imageProcessor[0].getRedMatrix();
        int[][] matrixGreen = imageProcessor[0].getGreenMatrix();
        int[][] matrixBlue = imageProcessor[0].getBlueMatrix();
        
        for(int j = 0; j < row; j++) {
            for(int i = 0; i < column; i ++) {
//                int[] modRed = setModulus(threshold);
//                int[] modGreen = setModulus(threshold);
//                int[] modBlue = setModulus(threshold);
                
                for(int index = 0; index < shares; index++) {
                    redVal = matrixRed[j][i]%mod[index];
                    greenVal = matrixGreen[j][i]%mod[index];
                    blueVal = matrixBlue[j][i]%mod[index];
                    
                    imageProcessor[index].setColorVal(i, j, redVal, greenVal, blueVal);
                }
            }
        }
        
        while (counter < shares) {
            // write copy image
            try {
                // call function write image
                imageProcessor[counter].WriteImage("D:\\Skripsi\\Material\\CRTShares("+ (mod[counter]) +").png");
            } catch (IOException e) {
                // print the error message
                System.out.println("Error = " +e);
            }
            counter++;
        }   // end of while
    }
    
    public void recoverImage(int[] mod) throws IOException {
        
        ImageProcessor[] imgPrcssr = new ImageProcessor[mod.length];
        for(int i = 0; i < imgPrcssr.length; i++) {
            imgPrcssr[i] = new ImageProcessor();
            imgPrcssr[i].ReadImage("D:\\Skripsi\\Material\\CRTShares("+ (mod[i]) +").png");
        }
        
        int[] redVal = new int[mod.length];
        int[] greenVal = new int[mod.length];
        int[] blueVal = new int[mod.length];
        
        int[][] matrixRed = new int[column][row];
        int[][] matrixGreen = new int[column][row];
        int[][] matrixBlue = new int[column][row];
        
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                for(int index = 0; index < imgPrcssr.length; index++) {
                    redVal[index] = imgPrcssr[index].redOri[i][j];
                    greenVal[index] = imgPrcssr[index].greenOri[i][j];
                    blueVal[index] = imgPrcssr[index].blueOri[i][j];
                }
                
                matrixRed[i][j] = recoverPixel(redVal, mod);
                matrixGreen[i][j] = recoverPixel(greenVal, mod);
                matrixBlue[i][j] = recoverPixel(blueVal, mod);
            }
        }
        
        imgPrcssr[0].setNewPixel(matrixRed, matrixGreen, matrixBlue);
        // write copy image
        try {
            // call function write image
            imgPrcssr[0].WriteImage("D:\\Skripsi\\Material\\HasilCRT.png");
        } catch (IOException e) {
            // print the error message
            System.out.println("Error = " +e);
        }
    }
    
    private int recoverPixel(int[] matrixVal, int[] mod) {
        int val = 0, M = 1, Mi;
        for(int i = 0; i < mod.length; i++) {
            M *= mod[i];
        }
        for(int i = 0; i < mod.length; i++) {
            Mi = M/mod[i];
            val += matrixVal[i]*Mi*eea(Mi, mod[i], 0, 0);
        }
        
        return val%M;
    }
    
    private int eea(int a, int b, int x, int y) {
        if(a == 0) {
            x = 0;
            y = 1;
            
            return b;
        }
        
        int x1 = 1, y1 = 1;
        int gcd =  eea(b%a, a, x1, y1);
        
        x  = y1 - (b/a) * x1;
        y  = x1;
        
        /*
        * Returning value of GCD
        */
//        return gcd;
        
        /*
        * Returning value modular multipicative inverse of "a modulo b"
        */
        return x;
        
        /*
        * Returning value modular multipicative inverse of "b modulo a"
        */
//        return y;
    }
    
    private int gcd(int a, int b) {
        if(a == 0)
            return b;
        return gcd(b%a, a);
    }
    
    
}
