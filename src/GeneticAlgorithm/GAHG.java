package GeneticAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

//Genetic Algorithm Heterogeneous Group Formation
public class GAHG {
    private final int N, K, M, P;
    private double[][] marks;
    private ArrayList<Chromosome> chromosomes;

    public GAHG(int N, int K, int M, int P) {
        this.N = N;
        this.K = K;
        this.M = M;
        this.P = P;
        marks = new double[N][M];
        chromosomes = new ArrayList<>(P);
    }

    public static void main(String... args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter no. of students (N) : ");
            int N = Integer.parseInt(br.readLine());

            System.out.print("Enter no. of groups (K) : ");
            int K = Integer.parseInt(br.readLine());

            System.out.print("Enter no. of attributes (M) : ");
            int M = Integer.parseInt(br.readLine());

            GAHG gaGroups = new GAHG(N, K, M, 500);

            System.out.print("Enter minimum marks : ");
            double min = Double.parseDouble(br.readLine());

            System.out.print("Enter maximum marks : ");
            double max = Double.parseDouble(br.readLine());

            gaGroups.initMarks(min, max);
            gaGroups.printMarks();

            gaGroups.generatePopulation();

            int i = 50;
            gaGroups.iterate(i);
            gaGroups.getChromosomes().get(0).printChromosome();

            //for(int j = 0 ; j < gaGroups.getChromosomes().size() ; ++j)
            System.out.println("Overall Fitness value : " + gaGroups.getChromosomes().get(0).getFitnessValue());
        } catch (IOException ignored) {

        }
        //test();
    }

    public void initMarks(double min, double max) {
        for (int i = 0; i < N; ++i)
            marks[i] = RandomGenerators.getRandomArray(M, min, max);

        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j) marks[i][j] = ((Math.round(marks[i][j] * 100.0)) / 100.0);
    }

    private Chromosome buildRandomChromosome() {
        int[] order = RandomGenerators.randomDivide(N, K);
        return Chromosome.build(order, N, K, M, marks);
    }

    public void generatePopulation() {
        while (chromosomes.size() != P) chromosomes.add(buildRandomChromosome());
    }

    public void iterate(int limit) {
        int i = 1;
        while (i++ <= limit) {
            int a = RandomGenerators.getRandomInt(0, chromosomes.size());
            int b = RandomGenerators.getRandomInt(0, chromosomes.size());

            if (a == b) b = (b + 1) % chromosomes.size();

            Chromosome A = chromosomes.get(a);
            Chromosome B = chromosomes.get(b);
            Chromosome dupA = A.duplicate();
            Chromosome dupB = B.duplicate();

            Chromosome.crossover(dupA, dupB, N, K, M, marks);

            chromosomes.add(dupA);
            chromosomes.add(dupB);
            chromosomes.add(A);
            chromosomes.add(B);
            Collections.sort(chromosomes);

            setChromosomes(limitToSize(chromosomes, P));
        }
    }

    private ArrayList<Chromosome> limitToSize(ArrayList<Chromosome> chromosomes, int P) {
        if (chromosomes.size() > P) {
            while (chromosomes.size() != P) {
                chromosomes.remove(P);
            }
        }
        return chromosomes;
    }

    private void printMarks() {
        for (int i = 0; i < N; ++i)
            System.out.println("Student " + (i + 1) + " : " + Arrays.toString(marks[i]));
        System.out.println();
    }

    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public double[][] getMarks() {
        return marks;
    }

    public void setMarks(double[][] marks) {
        this.marks = marks;
    }
}


