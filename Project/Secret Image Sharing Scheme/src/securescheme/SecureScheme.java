/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securescheme;

import com.frank.math.Complex;
import com.frank.math.FFT2D;
import imageprocessor.ImageProcessor;
import java.io.IOException;
import singularvaluedecomposition.SVD;

/**
 *
 * @author 14R PC
 */
public class SecureScheme {
    
    double alpha = 0.032;
    double[][] sigmaKey;
    int[] numShare = {2, 4};
    
    public void SecureScheme() throws IOException {
        System.out.println("Transforming selected share");
        ImageProcessor watermark = new ImageProcessor();
        watermark.ReadImage("D:\\Skripsi\\Material\\baboon.png");
        
        Complex[][][] transformR = new Complex [numShare.length][][];
        Complex[][][] transformG = new Complex [numShare.length][][];
        Complex[][][] transformB = new Complex [numShare.length][][];
        Complex[][][] transformTemp = new Complex [numShare.length][][];
        ImageProcessor[] selShare = new ImageProcessor[numShare.length];
        int[][][] shareChannelR = new int[numShare.length][][];
        int[][][] shareChannelG = new int[numShare.length][][];
        int[][][] shareChannelB = new int[numShare.length][][];
        FFT2D fft2 = new FFT2D();
        
        for(int i = 0; i < numShare.length; i++) {
            selShare[i] = new ImageProcessor();
            selShare[i].ReadImage("D:\\Skripsi\\Material\\Shares("+(numShare[i])+").png");
        }
        
        // Perform for red Channel
        System.out.println("Red Channel");
        for(int i = 0; i < numShare.length; i++) {
            shareChannelR[i] = selShare[i].redOri;
            transformR[i] = new Complex [shareChannelR[i].length][shareChannelR[i][0].length];
            for(int v = 0; v < shareChannelR[i].length; v++) {
                for(int u = 0; u < shareChannelR[i][0].length; u++) {
                    transformR[i][v][u] = new Complex(shareChannelR[i][v][u]);
                }
            }
            transformR[i] = fft2.fft(transformR[i]);
        }
        
        transformR = computeEmbedding(watermark.redOri, transformR, 0);
        transformTemp = computeEmbedding(watermark.redOri, transformR, 1);
        
        for(int i = 0; i < numShare.length; i++) {
            for(int v = 0; v < transformR[i].length; v++) {
                for(int u = 0; u < transformR[i][v].length; u++) {
                    transformR[i][v][u].imaginary = transformTemp[i][v][u].imaginary;
                }
            }
        }
        
        for(int i = 0; i < numShare.length; i++) {
            transformR[i] = fft2.ifft(transformR[i]);
            for(int v = 0; v < transformR[i].length; v++) {
                for(int u = 0; u < transformR[i][v].length; u++) {
                    selShare[i].redOri[v][u] = (int) Math.round(transformR[i][u][v].real);
                }
            }
        }
        
        // Perform for green Channel
        System.out.println("Green Channel");
        for(int i = 0; i < numShare.length; i++) {
            shareChannelG[i] = selShare[i].greenOri;
            transformG[i] = new Complex [shareChannelG[i].length][shareChannelG[i][0].length];
            for(int v = 0; v < shareChannelG[i].length; v++) {
                for(int u = 0; u < shareChannelG[i][0].length; u++) {
                    transformG[i][v][u] = new Complex(shareChannelG[i][v][u]);
                }
            }
            transformG[i] = fft2.fft(transformG[i]);
        }
        
        transformG = computeEmbedding(watermark.greenOri, transformG, 0);
        transformTemp = computeEmbedding(watermark.greenOri, transformG, 1);
        
        for(int i = 0; i < numShare.length; i++) {
            for(int v = 0; v < transformG[i].length; v++) {
                for(int u = 0; u < transformG[i][v].length; u++) {
                    transformG[i][v][u].imaginary = transformTemp[i][v][u].imaginary;
                }
            }
        }
        
        for(int i = 0; i < numShare.length; i++) {
            transformG[i] = fft2.ifft(transformG[i]);
            for(int v = 0; v < transformG[i].length; v++) {
                for(int u = 0; u < transformG[i][v].length; u++) {
                    selShare[i].greenOri[v][u] = (int) Math.round(transformG[i][u][v].real);
                }
            }
        }
        
        // Perform for blue Channel
        System.out.println("Blue Channel");
        for(int i = 0; i < numShare.length; i++) {
            shareChannelB[i] = selShare[i].blueOri;
            transformB[i] = new Complex [shareChannelB[i].length][shareChannelB[i][0].length];
            for(int v = 0; v < shareChannelB[i].length; v++) {
                for(int u = 0; u < shareChannelB[i][0].length; u++) {
                    transformB[i][v][u] = new Complex(shareChannelB[i][v][u]);
                }
            }
            transformB[i] = fft2.fft(transformB[i]);
        }
        
        transformB = computeEmbedding(watermark.blueOri, transformB, 0);
        transformTemp = computeEmbedding(watermark.blueOri, transformB, 1);
        
        for(int i = 0; i < numShare.length; i++) {
            for(int v = 0; v < transformB[i].length; v++) {
                for(int u = 0; u < transformB[i][v].length; u++) {
                    transformB[i][v][u].imaginary = transformTemp[i][v][u].imaginary;
                }
            }
        }
        
        for(int i = 0; i < numShare.length; i++) {
            transformB[i] = fft2.ifft(transformB[i]);
            for(int v = 0; v < transformB[i].length; v++) {
                for(int u = 0; u < transformB[i][v].length; u++) {
                    selShare[i].blueOri[v][u] = (int) Math.round(transformB[i][u][v].real);
                }
            }
            selShare[i].setNewPixel(selShare[i].redOri, selShare[i].greenOri, selShare[i].greenOri);
            selShare[i].WriteImage("D:\\Skripsi\\Material\\Shares("+(numShare[i])+").png");
        }
    }
    
    public void retrieveWatermark() throws IOException {
        System.out.println("Retrieve Watermark");
        ImageProcessor watermark = new ImageProcessor();
        watermark.ReadImage("D:\\Skripsi\\Material\\baboon.png");
        Complex[][][] transformR = new Complex [numShare.length][][];
        Complex[][][] transformG = new Complex [numShare.length][][];
        Complex[][][] transformB = new Complex [numShare.length][][];
        Complex[][][] transformTemp = new Complex [numShare.length][][];
        ImageProcessor[] selShare = new ImageProcessor[numShare.length];
        int[][][] shareChannelR = new int[numShare.length][][];
        int[][][] shareChannelG = new int[numShare.length][][];
        int[][][] shareChannelB = new int[numShare.length][][];
        int[][] retrievedWR, retrievedWG, retrievedWB;
        FFT2D fft2 = new FFT2D();
        
        for(int i = 0; i < numShare.length; i++) {
            selShare[i] = new ImageProcessor();
            selShare[i].ReadImage("D:\\Skripsi\\Material\\Shares("+(numShare[i])+").png");
        }
        
        // Perform for red Channel
        System.out.println("Retrieve Red Channel");
        for(int i = 0; i < numShare.length; i++) {
            shareChannelR[i] = selShare[i].redOri;
            transformR[i] = new Complex [shareChannelR[i].length][shareChannelR[i][0].length];
            for(int v = 0; v < shareChannelR[i].length; v++) {
                for(int u = 0; u < shareChannelR[i][0].length; u++) {
                    transformR[i][v][u] = new Complex(shareChannelR[i][v][u]);
                }
            }
            transformR[i] = fft2.fft(transformR[i]);
        }
        retrievedWR = computeRetrieve(watermark.redOri, transformR);
        
        // Perform for green Channel
        System.out.println("Retrieve Green Channel");
        for(int i = 0; i < numShare.length; i++) {
            shareChannelG[i] = selShare[i].greenOri;
            transformG[i] = new Complex [shareChannelG[i].length][shareChannelG[i][0].length];
            for(int v = 0; v < shareChannelG[i].length; v++) {
                for(int u = 0; u < shareChannelG[i][0].length; u++) {
                    transformG[i][v][u] = new Complex(shareChannelG[i][v][u]);
                }
            }
            transformG[i] = fft2.fft(transformG[i]);
        }
        retrievedWG = computeRetrieve(watermark.greenOri, transformG);
        
        // Perform for blue Channel
        System.out.println("Retrieve Blue Channel");
        for(int i = 0; i < numShare.length; i++) {
            shareChannelB[i] = selShare[i].blueOri;
            transformB[i] = new Complex [shareChannelB[i].length][shareChannelB[i][0].length];
            for(int v = 0; v < shareChannelB[i].length; v++) {
                for(int u = 0; u < shareChannelB[i][0].length; u++) {
                    transformB[i][v][u] = new Complex(shareChannelB[i][v][u]);
                }
            }
            transformB[i] = fft2.fft(transformB[i]);
        }
        retrievedWB = computeRetrieve(watermark.blueOri, transformB);
        
        
        selShare[0].setNewPixel(retrievedWR, retrievedWG, retrievedWB);
        selShare[0].WriteImage("D:\\Skripsi\\Material\\Retrieved Watermark.png");
    }
    
    public Complex[][][] computeEmbedding(int[][] watermarkChannel, Complex[][][] transform, int is_imag) throws IOException {
        SVD[] svdShare = new SVD[transform.length];
        SVD svdWatermark = new SVD();
        SVD svdKey = new SVD();
        double[][] keyMatrix, newSigma;
        
        svdWatermark.computeSVD(intToDouble(watermarkChannel));
        
        for(int i = 0; i < transform.length; i++) {
            svdShare[i] = new SVD();
            if(is_imag == 1) {
                svdShare[i].computeSVD(complexToImag(transform[i]));
            } else {
                svdShare[i].computeSVD(complexToReal(transform[i]));
            }
        }
        
        keyMatrix = new double[svdShare[0].getSigmaRow()][svdShare[0].getSigmaCol()];
        
        for(int v = 0; v < keyMatrix.length; v++) {
            for(int u = 0; u < keyMatrix[0].length; u++) {
                double max = 0.0;
                for(int a = 0; a < svdShare.length; a++) {
                    if(Math.max(max, svdShare[a].getSigmaVal(u, v)) == svdShare[a].getSigmaVal(u, v))
                        max = svdShare[a].getSigmaVal(u, v);
                }
                keyMatrix[v][u] = max;
                max = 0.0;
            }
        }
        svdKey.computeSVD(keyMatrix);
        this.sigmaKey = svdKey.getS();
        newSigma = modifiedSigma(svdKey.getS(), svdWatermark.getS(), alpha);        
        keyMatrix = svdKey.inverseSVD(newSigma);
        
        for(int v = 0; v < keyMatrix.length; v++) {
            for(int u = 0; u < keyMatrix[0].length; u++) {
                int max = 0;
                for(int a = 0; a < svdShare.length; a++) {
                    if(Math.max(max, svdShare[a].getSigmaVal(u, v)) == svdShare[a].getSigmaVal(u, v))
                        max = a;
                }
                svdShare[max].setSigmaVal(u, v, keyMatrix[v][u]);
                max = 0;
            }
        }
        
        for(int i = 0; i < transform.length; i++) {
            svdShare[i].inverseSVD();
            transform[i] = toComplex(transform[i], svdShare[i].A.getArray(), is_imag);
        }
        return transform;
    }
    
    public int[][] computeRetrieve(int[][] watermarkChannel, Complex[][][] transform) {
        double[][] retrievedSigma;
        double[][] keyMatrix;
        SVD[] svdShare = new SVD[transform.length];
        SVD svdWatermark = new SVD();
        SVD svdKey = new SVD();
        
        svdWatermark.computeSVD(intToDouble(watermarkChannel));
        
        for(int i = 0; i < transform.length; i++) {
            svdShare[i] = new SVD();
            svdShare[i].computeSVD(complexToReal(transform[i]));
        }
        
        keyMatrix = new double[svdShare[0].getSigmaRow()][svdShare[0].getSigmaCol()];
        
        for(int v = 0; v < keyMatrix.length; v++) {
            for(int u = 0; u < keyMatrix[0].length; u++) {
                double max = 0.0;
                for(int a = 0; a < svdShare.length; a++) {
                    if(Math.max(max, svdShare[a].getSigmaVal(u, v)) == svdShare[a].getSigmaVal(u, v))
                        max = svdShare[a].getSigmaVal(u, v);
                }
                keyMatrix[v][u] = max;
                max = 0.0;
            }
        }
        
        svdKey.computeSVD(keyMatrix);
        retrievedSigma = retrieveSigma(svdKey.getS(), this.sigmaKey, alpha);
        
        return doubleToInt(svdWatermark.inverseSVD(retrievedSigma));
    }
    
    public int[][] computeRetrieve(double[][] sigmaKey, int[][] watermarkChannel, Complex[][][] transform) {
        this.sigmaKey = sigmaKey;
        return computeRetrieve(watermarkChannel, transform);
    }
    
    public double[][] modifiedSigma(double[][] sigmaA, double[][] sigmaW, double alpha) {
        for(int v = 0; v < sigmaA.length; v++) {
            for(int u = 0; u < sigmaA[0].length; u++) {
                sigmaA[v][u] += (sigmaW[v][u]*alpha);
            }
        }
        return sigmaA;
    }
    
    public double[][] retrieveSigma(double[][] sigmaA, double[][] sigmaW, double alpha) {
        for(int v = 0; v < sigmaA.length; v++) {
            for(int u = 0; u < sigmaA[0].length; u++) {
                sigmaA[v][u] = (sigmaA[v][u]-sigmaW[v][u])/alpha;
            }
        }
        return sigmaA;
    }
    
    public double[][] complexToReal(Complex[][] matrix) {
        double[][] real = new double[matrix.length][matrix.length];
        for(int v = 0; v < real.length; v++) {
            for(int u = 0; u < real[0].length; u++) {
                real[v][u] = matrix[v][u].real;
            }
        }
        return real;
    }
    
    public double[][] complexToImag(Complex[][] matrix) {
        double[][] imag = new double[matrix.length][matrix.length];
        for(int v = 0; v < imag.length; v++) {
            for(int u = 0; u < imag[0].length; u++) {
                imag[v][u] = matrix[v][u].imaginary;
            }
        }
        return imag;
    }
    
    public double[][] intToDouble(int[][] matrix) {
        double[][] integer = new double[matrix.length][matrix.length];
        for(int v = 0; v < integer.length; v++) {
            for(int u = 0; u < integer[0].length; u++) {
                integer[v][u] = (double) matrix[v][u];
            }
        }
        return integer;
    }
    
    public int[][] doubleToInt(double[][] matrix) {
        int[][] doub = new int[matrix.length][matrix.length];
        for(int v = 0; v < doub.length; v++) {
            for(int u = 0; u < doub[0].length; u++) {
                doub[v][u] = (int) matrix[v][u];
            }
        }
        return doub;
    }
    
    public Complex[][] toComplex(Complex[][] comp, double[][] doub, int is_imag) {
        if(is_imag == 1) {
            for(int v = 0; v < comp.length; v++) {
                for(int u = 0; u < comp[0].length; u++) {
                    comp[v][u].imaginary = doub[v][u];
                }
            }
        } else {
            for(int v = 0; v < comp.length; v++) {
                for(int u = 0; u < comp[0].length; u++) {
                    comp[v][u].real = doub[v][u];
                }
            }
        }
        return comp;
    }
}
