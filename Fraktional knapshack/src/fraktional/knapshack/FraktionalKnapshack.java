/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fraktional.knapshack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author MIAfandi
 */
public class FraktionalKnapshack {

    /**
     * @param args the command line arguments
     */
    
     private static double getOptimalValueByDensity(int capacity,
                                          int[] values,
                                          int[] weights) {
        double value = 0;
        // build val-per-unit for each item
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){
            @Override
            public int compare (Item i1, Item i2) {
                return i1.val_per_unit > i2.val_per_unit ? -1 : 1;
            }
        });
        
        int i = 0;
        // either no items left or no room left
        while(i < items.length && capacity > 0) {
            // if item fits into knapsack, take all of it;
            // o.w. take so much as to fill the knapsack
            int fraction = Math.min(items[i].weight, capacity);
            value += items[i].val_per_unit * fraction;
            capacity -= fraction;
            i++;
        }
        return value;
    }
     
     private static double getOptimalValueByValue(int capacity,
                                          int[] values,
                                          int[] weights) {
        double value = 0;
        Arrays.sort(values);
        // build val-per-unit for each item
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        
        int i = 0;
        // either no items left or no room left
        while(i < items.length && capacity > 0) {
            // if item fits into knapsack, take all of it;
            // o.w. take so much as to fill the knapsack
            int fraction = Math.min(items[i].weight, capacity);
            if (fraction==items[i].weight){
                value += items[i].value;
                capacity -= fraction;
                i++;
            }else{
            value += items[i].value*(capacity/items[i].weight);
            capacity -= fraction;
            i++;
            }
        }
        return value;
    }
     
     private static double getOptimalValueByWeight(int capacity,
                                          int[] values,
                                          int[] weights) {
        double value = 0;
        // build val-per-unit for each item
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by vi/wi
        
        
        int i = 0;
        // either no items left or no room left
        while(i < items.length && capacity > 0) {
            // if item fits into knapsack, take all of it;
            // o.w. take so much as to fill the knapsack
            int fraction = Math.min(items[i].weight, capacity);
            value += items[i].weight * fraction;
            capacity -= fraction;
            i++;
        }
        return value;
    }
    
    private static class Item{
        int weight;
        int value;
        double val_per_unit; // density item
        public Item (int val, int wgt) {
            this.weight = wgt;
            this.value = val;
            // untuk mendefinisikan density
            val_per_unit = Math.round(
                    ((double) val) / ((double) wgt) * 10000.0) / 10000.0;
            /*
             * -----Round decimal digits in Java-----
             * 1. String.format("%.4f", <double>);
             *    but the type is string
             * 2. Math.round(dVal * 10^n) / 10^n.0;
             *    - round to n decimal places:
             *      multiply by 10^n, and then divide by 10^n.
             *    - return type is long!
             */
        }

        public int getWeight() {
            return weight;
        }

        public int getValue() {
            return value;
        }

        
       
        }
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input jumlah item = ");
        int n = scanner.nextInt();
        System.out.println("Input kapasitas knapsack = ");
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Input item ke-"+(i+1));
            System.out.print("Values: ");
            values[i] = scanner.nextInt();
            System.out.print("Weight: ");
            weights[i] = scanner.nextInt();
        }
        

        System.out.println(getOptimalValueByDensity(capacity, values, weights));
        System.out.println(getOptimalValueByValue(capacity, values, weights));
        System.out.println(getOptimalValueByWeight(capacity, values, weights));
        scanner.close();
    }
    
}
