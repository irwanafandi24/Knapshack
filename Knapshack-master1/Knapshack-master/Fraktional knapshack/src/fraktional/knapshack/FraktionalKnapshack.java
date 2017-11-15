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
    
     private static double getOptimalProfitByDensity(int capacity, int[] values,int[] weights) { //knapsack by density (keberhargaan)
        double totalProfit = 0; //menginisiasi total value dari item di knapsack
       
        Item[] items = new Item[values.length]; //menginisiasi array sejumlah kapasitas
        for (int i = 0; i < items.length; i++) { //perulangan untuk menginisiasi setiap item dgn value dan beratnya
            items[i] = new Item(values[i], weights[i]);
            //density di definisikan di constuctor item
        }
        // sort items by vi/wi
        Arrays.sort(items, new Comparator<Item>(){ //fungsi untuk mengurutkan setiap item dalam array (berdasarkan keberhargaan/value per unit)
            @Override
            public int compare (Item i1, Item i2) {
                return i1.val_per_unit > i2.val_per_unit ? -1 : 1;
            }
        });
        
        int i = 0;
        while(i < items.length && capacity > 0) { // perulangan berkahir ketika semua item di array telah di hitung atau kapasitas knapsack mencapai 0
            
            double fraction = Math.min(items[i].weight, capacity); //mengembalikan nilai minimal antara berat item dan kapasitas knapsack
            // berdasarkan nilai fraction ada dua kemungkinan
            // 1. item muat maka ambil semuanya
            // 2. item tidak muat maka ambil sesuai capacity
            totalProfit += items[i].val_per_unit * fraction; //menambah nilai kapasitas dengan hasil kali nilai keberhargaan item dengan fraction
            capacity -= fraction; //mengurangi nilai capacity dengan nilai fraction
            i++;
        }
        return totalProfit;
    }
    
    private static double getOptimalProfitByValue(int capacity, int[] values,int[] weights) { //knapsack by profit
        double totalProfit = 0; //menginisiasi nilai profit
        
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
            
        }
        Arrays.sort(items, new Comparator<Item>(){ //fungsi untuk mengurutkan setiap item dalam array (berdasarkan nilai)
            @Override
            public int compare (Item i1, Item i2) {
                return i1.value > i2.value ? -1 : 1;
            }
        });
     
        int i = 0;
        
        while(i < items.length && capacity > 0) { //perulangan berkahir ketika semua item di array telah di hitung atau kapasitas knapsack mencapai 
                
                // Dua cabang IF pertama mengambarkan dua kemungkinan yaitu
                // 1. item muat maka ambil semuanya
                // 2. item tidak muat maka ambil sesuai capacity
                
                if(items[i].weight<=capacity && capacity>0){ //kondisi knapsack masih memiliki kapasitas yg mencukupi item ke[i]
                    totalProfit += items[i].value; //tambah profit dengan value item ke[i]
                    capacity -= items[i].weight; //kurangi nilai capacity dengan berat item ke[i]
                }else if (items[i].weight>capacity && capacity>0){ //kondisi berat item ke[i] melibihi kapasitas
                    totalProfit += (capacity/items[i].weight)*items[i].value; //tambah nilai profit sebesar kapasitas/berat item dikali value item ke[i]
                    capacity =0; //set kapasitas menjadi 0 (penuh)
                }else{
                    break;
                }
                i++;
        }
        return totalProfit;
    }

    private static double getOptimalProfitByWeight(int capacity, int[] values,int[] weights) { //knapsack by weight
        double totalProfit = 0;
        
        Item[] items = new Item[values.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(values[i], weights[i]);
        }
        // sort items by wi
        Arrays.sort(items, new Comparator<Item>(){ //fungsi untuk mengurutkan setiap item dalam array berdasarkan weight
            @Override
            public int compare (Item i1, Item i2) {
                return i1.weight < i2.weight ? -1 : 1;
            }
        });
        
        int i = 0;
        
        while(i < items.length && capacity > 0) { //perulangan berkahir ketika semua item di array telah di hitung atau kapasitas knapsack mencapai 
            
            
                // Dua cabang IF pertama mengambarkan dua kemungkinan yaitu
                // 1. item muat maka ambil semuanya
                // 2. item tidak muat maka ambil sesuai capacity
                if(items[i].weight<=capacity && capacity>0){ //kondisi knapsack masih memiliki kapasitas yg mencukupi item ke[i]
                    totalProfit += items[i].value; //tambahkan nilai totalProfit dengan nilai value item ke[i]
                    capacity -= items[i].weight; //kurangkan nilai capacity dengan berat item ke[i]
                }else if (items[i].weight>capacity && capacity>0){ //kondisi berat item ke[i] melibihi kapasitas
                    totalProfit += (capacity/items[i].weight)*items[i].value; //tambah nilai totalProfit sebesar kapasitas/berat item dikali value item ke[i]
                    capacity =0; //set kapasitas menjadi 0 (penuh)
                }else{
                    break;
                }
                i++;
        }
        return totalProfit;
    }
     
    private static class Item{
        double weight;
        double value;
        double val_per_unit; // density item
        public Item (int val, int wgt) {
            this.weight = wgt;
            this.value = val;
            val_per_unit = Math.round(
                    ((double) val) / ((double) wgt) * 10000.0) / 10000.0;// untuk mendefinisikan density
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
        System.out.println("Input jumlah item = "); //memasukkan jumlah item
        int n = scanner.nextInt();
        System.out.println("Input kapasitas knapsack = "); //memasukkan kapasitas berat knapsack
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Input item ke-"+(i+1)); //menginput masing-masing item
            System.out.print("Values: ");//menginput value item ke[i]
            values[i] = scanner.nextInt();
            System.out.print("Weight: ");//menginput berat item ke[i]
            weights[i] = scanner.nextInt();
        }
        
        double densityValue = getOptimalProfitByDensity(capacity, values, weights);
        System.out.println("By Density: "+densityValue);
        double proviteValue = getOptimalProfitByValue(capacity, values, weights);
        System.out.println("By Value : "+proviteValue);
        double weightValue = getOptimalProfitByWeight(capacity, values, weights);
        System.out.println("By Weight : "+weightValue);
        scanner.close();
    }
    
}
