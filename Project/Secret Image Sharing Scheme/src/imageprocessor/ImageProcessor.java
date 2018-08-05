/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageprocessor;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author 14R PC
 */
public class ImageProcessor {
    
    // set column and row of the image
    int column = 963;
    int row = 640;
//    int[] argb = new int[4];
    public int[][] redOri, greenOri, blueOri, pixel;
    
    // create BufferedImage and File variable with the name image & outputImage and myfile respectively
    BufferedImage image = null;         // original image
    BufferedImage outputImage = null;   // copy image
    File myFile = null;
    
    // read the image file
    public void ReadImage(String filePath) throws IOException {
        // tell to system where the image file path
        myFile = new File(filePath);

        // create an object of BufferedImage
        image = new BufferedImage(column, row, BufferedImage.TYPE_INT_ARGB);
        
        // read the image
        image = ImageIO.read(myFile);
        
        // make copy of the original image
        outputImage = image;
        
        pixel = new int[getRow()][getColumn()];
        redOri = new int[getRow()][getColumn()];
        greenOri = new int[getRow()][getColumn()];
        blueOri = new int[getRow()][getColumn()];
        
        for(int height = 0; height < getRow(); height++) {
            for(int width = 0; width < getColumn(); width++) {
                int p = image.getRGB(width, height);
                pixel[height][width] = p;
                redOri[height][width] = (p>>16) & 0xff;
                greenOri[height][width] = (p>>8) & 0xff;
                blueOri[height][width] = p & 0xff;
            }
        }

        System.out.println("Reading Completed!");
    }
    
    public int getRow() {
        this.row = image.getHeight();
        return row;
    }
    
    public int getColumn() {
        this.column = image.getWidth();
        return column;
    }
    
    public int[][] getPixelMatrix() {
        return pixel;
    }
    
    public int[][] getRedMatrix() {
        return redOri;
    }
    
    public int[][] getGreenMatrix() {
        return greenOri;
    }
    
    public int[][] getBlueMatrix() {
        return blueOri;
    }
    
    public void setNewPixel(int[][] matrixRed, int[][] matrixGreen, int[][] matrixBlue) {
        for(int j = 0; j < getRow(); j++) {
            for(int i = 0; i < getColumn(); i++) {
                int alfa    = (image.getRGB(i, j) >> 24) & 0xff;
                int red     = matrixRed[i][j];
                int green     = matrixGreen[i][j];
                int blue     = matrixBlue[i][j];
                int p = (alfa<<24) | (red<<16) | (green<<8) | blue;
                outputImage.setRGB(i, j, p);
            }
        }
    }
    
    public void setColorVal(int i, int j, int redVal, int greenVal, int blueVal) {
        int im = image.getRGB(i, j);
        int alfa    = (im >> 24) & 0xff;
        int p = (alfa<<24) | (redVal<<16) | (greenVal<<8) | blueVal;
        outputImage.setRGB(i, j, p);
    }
    
    // write the copy image file
    public void WriteImage(String filePath) throws IOException {
        
        // tell the system where to write the output image
        myFile = new File(filePath);

        // write the image
        ImageIO.write(outputImage, "png", myFile);

        System.out.println("Write Output Image Completed!");
    }
}
