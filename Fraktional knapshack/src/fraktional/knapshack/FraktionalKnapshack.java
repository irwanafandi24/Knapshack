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
    
     private static double getOptimalValueByDensity(int capacity, int[] values,int[] weights) { //knapsack by density (keberhargaan)
        double kapasitas = 0; //menginisiasi kapasitas knapsack
        // build val-per-unit for each item
        Item[] items = new Item[values.length]; //menginisiasi array sejumlah kapasitas
        for (int i = 0; i < items.length; i++) { //perulangan untuk menginisiasi setiap item dgn value dan beratnya
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){ //fungsi untuk mengurutkan setiap item dalam array (berdasarkan keberhargaan/value per unit)
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
            double fraction = Math.min(items[i].weight, capacity); //mengembalikan nilai minimal antara berat item dan kapasitas knapsack
            kapasitas += items[i].val_per_unit * fraction; //menambah nilai kapasitas dengan hasil kali nilai keberhargaan item dengan fraction
            capacity -= fraction; //mengurangi nilai capacity dengan nilai fraction
            i++;
        }
        return kapasitas; //mengembalikan nilai optimal berdasarkan density dari semua data yang telah diolah
    }
    
private static double getOptimalValueByProfit(int capacity, int[] values,int[] weights) { //knapsack by profit
        double provit = 0; //menginisiasi nilai profit
        // menentukan profit masing-masing item dan disikmpan pada sebuah array, yang nanti akan diurutkan
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){ //fungsi untuk mengurutkan setiap item dalam array (berdasarkan nilai)
            @Override
            public int compare (Item i1, Item i2) {
                return i1.value > i2.value ? -1 : 1;
            }
        });
        int i = 0;
        while(i < items.length && capacity > 0) { //perulangan untuk memeriksa setiap item di list
                if(items[i].weight<capacity && capacity>0){ //kondisi knapsack masih memiliki kapasitas yg mencukupi item ke[i]
                    provit += items[i].value; //tambah profit dengan value item ke[i]
                    capacity -= items[i].weight; //kurangi nilai capacity dengan berat item ke[i]
                }else if (items[i].weight>capacity && capacity>0){ //kondisi berat item ke[i] melibihi kapasitas
                    provit += (capacity/items[i].weight)*items[i].value; //tambah nilai profit sebesar kapasitas/berat item dikali value item ke[i]
                    capacity =0; //set kapasitas menjadi 0 (penuh)
                }else{
                    break;
                }
                i++; //perulangan akan terus dilakukan selama berat belum memenuhi kapasitas kpanshack
        }
        return provit; //mengembalikan nilai optimal berdasarkan provit dari semua data yang telah diolah
    }

private static double getOptimalValueByWeight(int capacity, int[] values,int[] weights) { //knapsack by weight
        double nWeight = 0;
        // untuk menentukan weight tiap item dan disimpan pada sebuah eray yang bernama item.
        Item[] items = new Item[values.length]; //penampung setiap item
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]); // item yang diinput akan disimpan di array items
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){ //fungsi untuk mengurutkan setiap item dalam array
            @Override
            public int compare (Item i1, Item i2) {
                return i1.weight < i2.weight ? -1 : 1;
            }
        });
        int i = 0;
        while(i < items.length && capacity > 0) { //perulangan untuk memeriksa setiap item di list
                if(items[i].weight<capacity && capacity>0){ //kondisi knapsack masih memiliki kapasitas yg mencukupi item ke[i]
                    nWeight += items[i].value; //tambahkan nilai nWeight dengan nilai profit item ke[i]
                    capacity -= items[i].weight; //kurangkan nilai capacity dengan berat item ke[i]
                }else if (items[i].weight>capacity && capacity>0){ //kondisi berat item ke[i] melibihi kapasitas
                    nWeight += (capacity/items[i].weight)*items[i].value; //tambah nilai nWeight sebesar kapasitas/berat item dikali value item ke[i]
                    capacity =0; //set kapasitas menjadi 0 (penuh)
                }else{
                    break;
                }
                i++; //perulangan akan terus dilakukan selama kapasitas knpashack belum 0
        }
        return nWeight; //mengembalikan nilai optimal berdasarkan weight dari semua data yang telah diolah
    }
     
    private static class Item{ //membuat clas item dimana terdapat 2 atribut yaitu weight dan value
        double weight; //membuat variable weight
        double value; //membuat variable value
        double val_per_unit; // variable density item
        public Item (int val, int wgt) {
            this.weight = wgt;
            this.value = val;
            // untuk mendefinisikan density
            val_per_unit = Math.round(
                    ((double) val) / ((double) wgt) * 10000.0) / 10000.0; //mengolah density dari masing2 item yang dimasukkan
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
        
        Scanner scanner = new Scanner(System.in); //membuat system input dari keyboard
        System.out.println("Input jumlah item = "); //memasukkan jumlah item
        int n = scanner.nextInt(); //untuk memasukkan dari keyboard berupa int
        System.out.println("Input kapasitas knapsack = "); //memasukkan kapasitas berat knapsack
        int capacity = scanner.nextInt();
        int[] values = new int[n]; //membuat array value
        int[] weights = new int[n]; //membuat array weight
        for (int i = 0; i < n; i++) {
            System.out.println("Input item ke-"+(i+1)); //menginput masing-masing item
            System.out.print("Values: ");//menginput value item ke[i]
            values[i] = scanner.nextInt();
            System.out.print("Weight: ");//menginput berat item ke[i]
            weights[i] = scanner.nextInt();
        }
        
        double densityValue = getOptimalValueByDensity(capacity, values, weights); //untuk memperoleh nilai optimal dari density
        System.out.println("Density: "+densityValue); //hasilnya dari optimasi density
        double proviteValue = getOptimalValueByProfit(capacity, values, weights); // untuk memperoleh nilai optimal dari profit
        System.out.println("Prifit : "+proviteValue); //hasil dari optimasi provit
        double weightValue = getOptimalValueByWeight(capacity, values, weights); //untuk memperoleh nilai optmal dari weight
        System.out.println("Weight : "+weightValue); //hasil dari optimasi weight
        scanner.close();
    }
    
}
