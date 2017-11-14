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
    
     private static double getOptimalValueByDensity(int capacity, int[] values,int[] weights) {
        double kapasitas = 0;
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
            double fraction = Math.min(items[i].weight, capacity);
            kapasitas += items[i].val_per_unit * fraction;
            capacity -= fraction;
            i++;
        }
        return kapasitas;
    }
    
private static double getOptimalValueByProfit(int capacity, int[] values,int[] weights) {
        double provit = 0;
        // build val-per-unit for each item
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){
            @Override
            public int compare (Item i1, Item i2) {
                return i1.value > i2.value ? -1 : 1;
            }
        });
//        Item t;
//        for (int z=0; z<items.length;z++){
//            for (int y=0;y<items.length-1;y++){
//                if(items[y].value<items[y+1].value){
//                    t=items[y+1];
//                    items[y+1] = items[y];
//                    items[y] =t;
//                }
//            }
//        } 
//        
        int i = 0;
        // either no items left or no room left
        while(i < items.length && capacity > 0) {
            // if item fits into knapsack, take all of it;
            // o.w. take so much as to fill the knapsack
//            fraction= Math.min(items[i].weight, capacity);
//            provit += items[i].value * fraction;
//            capacity -= fraction;
//            i++;
                if(items[i].weight<capacity && capacity>0){
                    provit += items[i].value;
                    capacity -= items[i].weight;
                }else if (items[i].weight>capacity && capacity>0){
                    provit += (capacity/items[i].weight)*items[i].value;
                    capacity =0;
                }else{
                    break;
                }
                i++;
        }
        return provit;
    }

private static double getOptimalValueByWeight(int capacity, int[] values,int[] weights) {
        double nWeight = 0;
        // build val-per-unit for each item
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){
            @Override
            public int compare (Item i1, Item i2) {
                return i1.weight < i2.weight ? -1 : 1;
            }
        });
//        Item t;
//        for (int z=0; z<items.length;z++){
//            for (int y=0;y<items.length-1;y++){
//                if(items[y].value<items[y+1].value){
//                    t=items[y+1];
//                    items[y+1] = items[y];
//                    items[y] =t;
//                }
//            }
//        } 
//        
        int i = 0;
        // either no items left or no room left
        while(i < items.length && capacity > 0) {
            // if item fits into knapsack, take all of it;
            // o.w. take so much as to fill the knapsack
//            fraction= Math.min(items[i].weight, capacity);
//            provit += items[i].value * fraction;
//            capacity -= fraction;
//            i++;
                if(items[i].weight<capacity && capacity>0){
                    nWeight += items[i].value;
                    capacity -= items[i].weight;
                }else if (items[i].weight>capacity && capacity>0){
                    nWeight += (capacity/items[i].weight)*items[i].value;
                    capacity =0;
                }else{
                    break;
                }
                i++;
        }
        return nWeight;
    }
     
    private static class Item{
        double weight;
        double value;
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

        public double getWeight() {
            return weight;
        }

        public double getValue() {
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
        
        double densityValue = getOptimalValueByDensity(capacity, values, weights);
        System.out.println("Density: "+densityValue);
        double proviteValue = getOptimalValueByProfit(capacity, values, weights);
        System.out.println("Prifit : "+proviteValue);
        double weightValue = getOptimalValueByWeight(capacity, values, weights);
        System.out.println("Weight : "+weightValue);
        scanner.close();
    }
    
}
