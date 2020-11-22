/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eaustria;

/**
 *
 * @author bmayr
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Class wrapping methods for implementing reciprocal array sum in parallel.
 */
public final class ReciprocalArraySum {

    /**
     * Default constructor.
     */
    private ReciprocalArraySum() {
    }

    /**
     * Sequentially compute the sum of the reciprocal values for a given array.
     *
     * @param input Input array
     * @return The sum of the reciprocals of the array input
     */
    protected static double seqArraySum(final double[] input) {
        double sum = 0;
        for (double d : input) {
            sum += 1 / d;
        }
        return sum;
    }

    /**
     * This class stub can be filled in to implement the body of each task
     * created to perform reciprocal array sum in parallel.
     */
    private static class ReciprocalArraySumTask extends RecursiveAction {

        /**
         * Starting index for traversal done by this task.
         */
        private final int startIndexInclusive;
        /**
         * Ending index for traversal done by this task.
         */
        private final int endIndexExclusive;
        /**
         * Input array to reciprocal sum.
         */
        private final double[] input;
        /**
         * Intermediate value produced by this task.
         */
        private double value;

        private static int SEQUENTIAL_THRESHOLD = 50000;

        /**
         * Constructor.
         *
         * @param setStartIndexInclusive Set the starting index to begin
         * parallel traversal at.
         * @param setEndIndexExclusive Set ending index for parallel traversal.
         * @param setInput Input values
         */
        ReciprocalArraySumTask(final int setStartIndexInclusive, final int setEndIndexExclusive, final double[] setInput) {
            this.startIndexInclusive = setStartIndexInclusive;
            this.endIndexExclusive = setEndIndexExclusive;
            this.input = setInput;
            value = 0.0;
        }

        /**
         * Getter for the value produced by this task.
         *
         * @return Value produced by this task
         */
        public double getValue() {
            return value;
        }

        @Override
        protected void compute() {

            double sum = 0;
            if (endIndexExclusive - startIndexInclusive < SEQUENTIAL_THRESHOLD) {//input.length

                for (int i = startIndexInclusive; i < endIndexExclusive; i++) {
                    sum += (double) 1 / input[i];
                }
                value += sum;
                // System.out.println("sum: " + sum);

            } else {
                ReciprocalArraySumTask rast01 = new ReciprocalArraySumTask(startIndexInclusive, startIndexInclusive + ((endIndexExclusive - startIndexInclusive) / 2), input);//firstHalf
                ReciprocalArraySumTask rast02 = new ReciprocalArraySumTask(startIndexInclusive + ((endIndexExclusive - startIndexInclusive) / 2), endIndexExclusive, input);//secondHalf

                invokeAll(rast01, rast02);

                rast01.join();
                rast02.join();
                value = rast01.getValue() + rast02.getValue();
                //System.out.println("Value: " + value);

            }

        }
    }

    /**
     * TODO: Extend the work you did to implement parArraySum to use a set
     * number of tasks to compute the reciprocal array sum.
     *
     * @param input Input array
     * @param numTasks The number of tasks to create
     * @return The sum of the reciprocals of the array input
     */
    protected static double parManyTaskArraySum(final double[] input,
            final int numTasks) {
        double sum;
        ForkJoinPool fjpool = new ForkJoinPool(numTasks);
        ReciprocalArraySumTask rast = new ReciprocalArraySumTask(0, input.length, input);
        fjpool.invoke(rast);
        sum = rast.getValue();
        return sum;
    }
}
