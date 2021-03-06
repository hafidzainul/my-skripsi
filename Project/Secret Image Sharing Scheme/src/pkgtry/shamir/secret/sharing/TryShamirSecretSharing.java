/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry.shamir.secret.sharing;

import imageprocessor.Recoverer;
import java.io.IOException;
import java.util.Scanner;
import securescheme.SecureScheme;

/**
 *
 * @author jackb
 */
public class TryShamirSecretSharing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int[] xvalues;
        int primeNumber = 256;
        Scanner si = new Scanner(System.in);
        
        System.out.println("Obscuring Host Image");
        
        ShamirSecretSharingScheme ssss = new ShamirSecretSharingScheme();
        ssss.primeNumber = primeNumber;
        
        System.out.print("Input the n shares = ");
        ssss.shares = si.nextInt();
        System.out.print("Input the threshold = ");
        ssss.threshold = si.nextInt();
        
        xvalues = new int[ssss.shares];        
        for(int i = 0; i < xvalues.length; i++) {
            xvalues[i] = (i+1);
        }
        ssss.DispartImage(xvalues);
        
        SecureScheme ss = new SecureScheme();
        ss.SecureScheme();
        ss.retrieveWatermark();
        
        System.out.println("Retrieve Host Image");
        Recoverer recover = new Recoverer();
        recover.primeNumber = primeNumber;
        String[] filePath = new String[xvalues.length];
        for(int i = 0; i < filePath.length; i++) {
            filePath[i] = "D:\\Skripsi\\Material\\SSSShares("+(xvalues[i])+").png";
        }
        recover.MergeImage(filePath, xvalues);
    }
}
