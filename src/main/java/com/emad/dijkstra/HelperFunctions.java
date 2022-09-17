package com.emad.dijkstra;

import java.util.Random;

public class HelperFunctions {

    /***اول مادخل شفلت ببعت بدوخذ السيد وببعتها  */
    /**اول ما يسجلها الاري بصير يبدل بيناتهام ويعطيهم راندم فاليو 
    
     اول مرة لراندم ثاني مرة رقم ثاني 
     مجرد زيادة الراندم 
     والتبديل عشان ما يطلع نفس القيمة 
     */
    public static void shuffle(Node array[], int seed) {
        Random rand = new Random(seed);
        Node temp;
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
    }
    /****
    
        راندوم للاري لكراف للمسافة 
     */
    public static int[][] randomGraph(int length, int seed){
        int[][] graphMatrix = new int[length][length];
        Random rand = new Random(seed);

        for(int i=0;i<length;i++){//كل الداتا 
            // 3 connections
            for(int n=0;n<3;n++){

                int node = rand.nextInt(length);
                int distance = rand.nextInt(20);
                while(node == i) node = rand.nextInt(length);//كل نود 

                if(graphMatrix[i][node] == 0)
                    graphMatrix[i][node] = distance;//اذ اوصلنا اخر صف 
                if(graphMatrix[node][i] == 0)
                    graphMatrix[node][i] = distance;// كل شقا من الاري بعملها 3 طرق وبعطيها راندم فاليو 
            }
        }
        return graphMatrix;
    }
}
