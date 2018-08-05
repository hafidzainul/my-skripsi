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
    int column, row, shares, threshold, primeNumber;
    int counter = 0;
    String filePath = "D:\\Skripsi\\Material\\220px-Lenna.png";
    
    public void DispartImage(int[] mod) throws IOException {
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
        int M = 1;
        for(int i = 0; i < mod.length; i++) {
            M *= mod[i];
        }
        
        ImageProcessor[] imgPrcssr = new ImageProcessor[mod.length];
        for(int i = 0; i < imgPrcssr.length; i++) {
            imgPrcssr[i] = new ImageProcessor();
            imgPrcssr[i].ReadImage("D:\\Skripsi\\Material\\CRTShares("+ (mod[i]) +").png");
        }
        
        
    }
}
