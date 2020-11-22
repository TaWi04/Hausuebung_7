/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eaustria.reziprokeSumme;

import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Tamara
 */
public class Task extends RecursiveAction {

    private double value = 0;
    private final int start;
    private final int end;
    // private int[] array;

    public Task(int start, int end) {//, int[] array) {
        this.start = start;
        this.end = end;
        //this.array = array;
    }

    @Override
    protected void compute() {
        double sum = 0;
        for (int i = start; i <= end; i++) {
            sum += (double) 1 / i;
        }
        value += sum;
        System.out.println("sum: " + sum);
    }

    public double getValue() {
        return value;
    }
}
