package TravellingSalesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class MSTKruskal {
    private ArrayList<MSTEdge> tree;
    private Comparator<MSTEdge> comparator;
    private PriorityQueue<MSTEdge> q;

    public MSTKruskal(int [][] cost, int visited[], int numOfCities){
        tree = new ArrayList<MSTEdge>();
        q = new PriorityQueue<MSTEdge>(numOfCities);

        for(int i = 0; i < numOfCities; i++){								//TC: we are taking upper triangular matrix
            for(int j = i + 1; j < numOfCities; j++){
                if(visited[i] == 0 && visited[j] == 0){
                    MSTEdge tempEdge = new MSTEdge(i, j, cost[i][j]);
                    q.add(tempEdge);
                }
            }
        }
        for(int i = 0, tempNum = numOfCities; i < tempNum; i++){
            if(visited[i] > 0)
                numOfCities--;
        }
        this.calMSTKruskal(cost, numOfCities);
    }

    private void calMSTKruskal(int [][] cost, int numOfCities){
        MSTEdge tempEdge;
        int i = 0;
        while((tempEdge = q.poll()) != null  && i < numOfCities-1){
            //System.out.println(tempEdge.getFromNode() + "->" + tempEdge.getToNode() + "(" + tempEdge.getCost()+")");
            if( !this.isCycle(tempEdge)){
                tree.add(tempEdge);
                i++;
            }
        }
    }

    private boolean isCycle(MSTEdge newEdge){
        boolean node1Match, node2Match;
        node1Match = node2Match = false;
        Iterator<MSTEdge> navi = this.tree.iterator();
        while(navi.hasNext()){
            MSTEdge tempEdge = navi.next();
            if(!node1Match && (newEdge.getFromNode() == tempEdge.getFromNode() || newEdge.getFromNode() == tempEdge.getToNode())){
                node1Match = true;
            }
            if(!node2Match && (newEdge.getToNode() == tempEdge.getFromNode() || newEdge.getToNode() == tempEdge.getToNode())){
                node2Match = true;
            }
        }
        if(node1Match && node2Match)
            return true;
        else
            return false;
    }

    public int getTotalCost(){
        int totalCost = 0;
        Iterator<MSTEdge> navi = this.tree.iterator();
        while(navi.hasNext()){
            MSTEdge tempEdge = navi.next();
        }
        return totalCost;
    }
}

class MSTEdge implements Comparable<MSTEdge>{
    private int fromNode;
    private int toNode;
    private int cost;

    public MSTEdge(int from, int to, int cost){
        this.fromNode = from;
        this.toNode = to;
        this.cost = cost;
    }

    public int getCost(){
        return this.cost;
    }
    public int getFromNode(){
        return this.fromNode;
    }
    public int getToNode(){
        return this.toNode;
    }

    @Override
    public int compareTo(MSTEdge mstEdge) {
        return this.getCost() - mstEdge.getCost();
    }
}
