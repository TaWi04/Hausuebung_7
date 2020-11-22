/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eaustria.reziprokeSumme;

/**
 *
 * @author Tamara
 */
public class Main {

    public static void main(String[] args) {

        int limit = 2;
        double sum = 0;
        for (int i = 1; i <= limit; i++) {
            double temp = (double) 1 / i;
            sum += temp;
        }
        System.out.println("sum: " + sum);

    }
}
