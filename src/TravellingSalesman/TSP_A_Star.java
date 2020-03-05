package TravellingSalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


public class TSP_A_Star {
    private PriorityQueue<Node> openList;
    private Comparator<Node> comparator;
    private int[] path;
    private int costIncurred;

    private int visited[];

    public TSP_A_Star(int numOfCities) {
        this.path = new int[numOfCities];
        this.visited = new int[numOfCities];
        for (int i = 0; i < numOfCities; i++) {
            this.visited[i] = 0;
        }

        openList = new PriorityQueue<Node>(numOfCities);
    }

    public void solve(int[][] cost, int numOfCities, int startCity) {
        int numOfVisited = 1;
        int currentCity = startCity;
        visited[currentCity] = numOfVisited++;
        while (numOfVisited <= numOfCities) {
            for (int i = 0; i < numOfCities; i++) {
                if (i != currentCity && cost[currentCity][i] < Integer.MAX_VALUE) {
                    if (visited[i] == 0) {                //i.e. city is not visited
                        MSTKruskal tempSpanTree = new MSTKruskal(cost, visited, numOfCities);
                        int hCost = tempSpanTree.getTotalCost();
                        Node tempNode = new Node(i, (hCost + cost[currentCity][i]));
                        openList.add(tempNode);
                    }
                }
            }
            currentCity = openList.poll().cityId;
            visited[currentCity] = numOfVisited++;
        }
        //System.out.println(Arrays.toString(visited));
        this.calPath(cost);
    }

    private void calPath(int cost[][]) {
        for (int i = 1; i <= this.visited.length; i++) {
            for (int j = 0; j < this.visited.length; j++)
                if (visited[j] == i) {
                    this.path[i - 1] = j + 1;
                }
        }
        //System.out.println(Arrays.toString(path));
        long tempTotalCost = 0;
        for (int i = 0; i < this.visited.length - 1; i++) {
            if(path[i] != 0 && path[i + 1] != 0) tempTotalCost += cost[path[i] - 1][path[i + 1] - 1];
        }
        if (tempTotalCost > Integer.MAX_VALUE)
            costIncurred = Integer.MAX_VALUE;
        else
            costIncurred = (int) tempTotalCost;
    }

    public void printPath() {
        for (int i = 0; i < this.path.length; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.print("(" + this.costIncurred + ")");
    }
}

class Node implements Comparable<Node>{
    int cityId;
    int cost;

    public Node(int cId, int c) {
        this.cityId = cId;
        this.cost = c;
    }

    @Override
    public int compareTo(Node node) {
        return cost - node.cost;
    }
}