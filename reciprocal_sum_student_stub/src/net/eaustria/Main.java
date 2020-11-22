/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eaustria;

import java.util.Arrays;

/**
 *
 * @author Tamara
 */
public class Main {

    public static void main(String[] args) {
        double max = 15;
        double min = 100;
        int size = 100000000;
        double[] input = new double[size];
        input = Arrays.stream(input)
                .map(num -> (int) ((Math.random() * (max - min) + min + 1) * 100) / 100.00)
                .toArray();
        long startSeq = System.currentTimeMillis();
        double sumSeq = ReciprocalArraySum.seqArraySum(input);
        long endSeq = System.currentTimeMillis();

        long startTask = System.currentTimeMillis();
        double sumTask = ReciprocalArraySum.parManyTaskArraySum(input, 100);
        long endTask = System.currentTimeMillis();

        //double sumSeq = ReciprocalArraySum.seqArraySum(input);
        //double sumTask = ReciprocalArraySum.parManyTaskArraySum(input, 100);
        System.out.println("\n SOLUTION"
                + "\nSequential: " + sumSeq + "; time: " + (endSeq - startSeq) + "ms"
                + "\nTasks: " + sumTask + "; time: " + (endTask - startTask) + "ms");

    }
}
