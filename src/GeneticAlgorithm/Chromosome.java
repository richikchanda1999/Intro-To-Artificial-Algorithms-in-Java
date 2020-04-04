package GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Chromosome implements Comparable<Chromosome> {
    private double[][] groups;
    private int[] order;
    private double fitness;

    public Chromosome() {
        fitness = 0.0;
    }

    public Chromosome(int N, int K, int M) {
        order = new int[N];
        groups = new double[K][M];
        fitness = 0;
    }

    private static void addArray(double[] arr1, double[] arr2) {
        for (int i = 0; i < arr1.length; ++i) arr1[i] += arr2[i];
    }

    public static Chromosome build(int[] order, int N, int K, int M, double[][] marks) {
        Chromosome chromosome = new Chromosome(N, K, M);
        int[] counts = new int[K];
        Arrays.fill(counts, 0);

        double[][] sum = new double[K][M];
        for (int j = 0; j < K; ++j) Arrays.fill(sum[j], 0);

        for (int j = 0; j < N; ++j) {
            addArray(sum[order[j] - 1], marks[j]);
            counts[order[j] - 1]++;
        }

        for (int j = 0; j < K; ++j)
            for (int k = 0; k < M; ++k)
                sum[j][k] /= counts[j];

        chromosome.setOrder(order);
        chromosome.setGroups(sum);
        chromosome.calculateFitness();
        return chromosome;
    }

    public static void crossover(Chromosome A, Chromosome B, int N, int K, int M, double[][] marks) {
        int[] orderA = A.getOrder();
        int[] orderB = B.getOrder();

        assert orderA.length == orderB.length;
        int start = RandomGenerators.getRandomInt(0, orderA.length / 2);
        int end = RandomGenerators.getRandomInt(orderA.length / 2, orderA.length);

        //System.out.println("Start : " + start + ", End : " + end);
        while (start <= end) {
            int temp = orderA[start];
            orderA[start] = orderB[start];
            orderB[start] = temp;

            ++start;
        }

        A = Chromosome.build(orderA, N, K, M, marks);
        B = Chromosome.build(orderB, N, K, M, marks);
    }

    private double calculateFitness(double[] arr1, double[] arr2) {
        double temp = 0.0;
        for (int i = 0; i < arr1.length; ++i) temp += Math.pow(arr1[i] - arr2[i], 2);
        return Math.sqrt(temp);
    }

    public void calculateFitness() {
        fitness = 0.0;
        for (int i = 0; i < groups.length; ++i)
            for (int j = i + 1; j < groups.length; ++j)
                fitness += calculateFitness(groups[i], groups[j]);

        fitness = Math.round(fitness * 100.0) / 100.0;
    }

    public Chromosome duplicate() {
        Chromosome chromosome = new Chromosome();
        chromosome.setOrder(this.getOrder());
        chromosome.setGroups(this.getGroups());
        chromosome.setFitnessValue(this.getFitnessValue());

        return chromosome;
    }

    public double getFitnessValue() {
        return fitness;
    }

    public void setFitnessValue(double fitness) {
        this.fitness = fitness;
    }

    public double[][] getGroups() {
        return groups;
    }

    public void setGroups(double[][] groups) {
        this.groups = groups;
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        return Double.compare(fitness, chromosome.getFitnessValue());
    }

    public int[] getOrder() {
        return order;
    }

    public void setOrder(int[] order) {
        this.order = order;
    }

    public String toString() {
        return Arrays.toString(order) + ", Fitness value : " + fitness;
    }

    public void printChromosome() {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

        for (int i = 0; i < order.length; ++i) {
            ArrayList<Integer> list = map.get(order[i]);
            if (list == null) list = new ArrayList<>();
            list.add(i + 1);
            map.put(order[i], list);
        }

        for (Integer in : map.keySet()) {
            String s = map.get(in).toString();
            System.out.println("Group " + in + " : " + s.substring(1, s.length() - 1));
        }
    }
}