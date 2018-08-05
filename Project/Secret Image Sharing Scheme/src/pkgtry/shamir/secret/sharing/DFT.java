/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry.shamir.secret.sharing;

/**
 * Discrete Fourier transform
 * @author 14R PC
 */
public class DFT {
    
    /*
    * Discrete Fourier transform
    * by Project Nayuki, 2017. Public domain.
    * https://www.nayuki.io/page/how-to-implement-the-discrete-fourier-transform
    */
    public void computeDft(double[] inreal, double[] inimag, double[] outreal, double[] outimag) {
        int n = inreal.length;
        for (int k = 0; k < n; k++) {  // For each output element
            double sumreal = 0;
            double sumimag = 0;
            for (int t = 0; t < n; t++) {  // For each input element
                double angle = 2 * Math.PI * t * k / n;
                sumreal +=  inreal[t] * Math.cos(angle) + inimag[t] * Math.sin(angle);
                sumimag += -inreal[t] * Math.sin(angle) + inimag[t] * Math.cos(angle);
            }
            outreal[k] = sumreal;
            outimag[k] = sumimag;
        }
    }
    
    public double[] forwardTransform(int[][] timeDomain, int u, int v) {
        double[] frequencyDomain = new double[2];
        double real, imag;
        int N = /*timeDomain.length*/4;
        int M = /*timeDomain[0].length*/4;
//        double i = Math.sqrt(-1);
//        System.out.println(i);
        real = imag = 0;
        for(int x = 0; x < N; x++) {
            for(int y = 0; y < M; y++) {
                double angle = 2*Math.PI*((u*x/N) + (v*y/M));
                real += timeDomain[y][x]*Math.cos(angle);
                imag += -1*timeDomain[y][x]*Math.sin(angle);
            }
        }
        frequencyDomain[0] = real;
        frequencyDomain[1] = imag;
        return frequencyDomain;
    }
    
    public int[][] inversTransform(double[][] frequencyDomain, int x, int y) {
        int[][] timeDomain = new int [frequencyDomain.length][frequencyDomain[0].length];
        
        return timeDomain;
    }
    
    /*
    * 
    */
//    public void libFFT(int[][] timeDomain, int u, int v) {
////        DoubleFFT_2D fft = new DoubleFFT_2D();
//        DoubleFFT_2D fft = new DoubleFFT_2D();
//        
//    }
}
