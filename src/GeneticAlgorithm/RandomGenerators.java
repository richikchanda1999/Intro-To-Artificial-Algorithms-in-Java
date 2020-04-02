package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomGenerators {
    static Random random = new Random();
    public static int[] getRandomArray(int size, int low, int high) {
        int[] arr = new int[size];
        for(int i = 0 ; i < size ; ++i) arr[i] = low + (int)((high - low) * random.nextDouble());
        return arr;
    }

    public static double[] getRandomArray(int size, double low, double high) {
        Random random = new Random();
        double[] arr = new double[size];
        for(int i = 0 ; i < size ; ++i) arr[i] = low + (high - low) * random.nextDouble();
        return arr;
    }

    public static int getRandomInt(int low, int high) {
        return (int) (low + (high - low) * random.nextDouble());
    }

    public static double getRandomInt(double low, double high) {
        return low + (high - low) * random.nextDouble();
    }

    public static int[] randomDivide(int n, int k) {
        int min = (n / k);
        int extra = n - k * min;

        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 1 ; i <= k ; ++i){
            int temp = min;
            while(temp-- > 0) arrayList.add(i);
        }

        while(extra-- > 0)
            arrayList.add(getRandomInt(1, k + 1));

        Collections.shuffle(arrayList);

        Integer[] tempArr = new Integer[n];
        int[] arr = new int[n];
        tempArr = arrayList.toArray(tempArr);

        for(int i = 0 ; i < n ; ++i) arr[i] = tempArr[i];
        return arr;
    }
}
