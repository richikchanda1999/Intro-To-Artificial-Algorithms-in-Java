package TravellingSalesman;

import java.util.*;
import java.io.*;

public class Kruskal {
    public double kruskalMST(ArrayList<Edge> graphEdges, int nodeCount) {
        Collections.sort(graphEdges);        //sort edges with smallest weight 1st
        ArrayList<Edge> mstEdges = new ArrayList<Edge>();    //list of edges included in the Minimum spanning tree (initially empty)

        DisjointSet nodeSet = new DisjointSet(nodeCount + 1);        //Initialize singleton sets for each node in graph. (nodeCount +1) to account for arrays indexing from 0

        for (int i = 0; i < graphEdges.size() && mstEdges.size() < (nodeCount - 1); i++) {        //loop over all edges. Start @ 1 (ignore 0th as placeholder). Also early termination when number of edges=(number of nodes-1)
            Edge currentEdge = graphEdges.get(i);
            int root1 = nodeSet.find(currentEdge.getVertex1());        //Find root of 1 vertex of the edge
            int root2 = nodeSet.find(currentEdge.getVertex2());
            String unionMessage = ",\tNo union performed\n";        //assume no union is to be performed, changed later if a union DOES happen
            if (root1 != root2) {            //if roots are in different sets the DO NOT create a cycle
                mstEdges.add(currentEdge);        //add the edge to the graph
                nodeSet.union(root1, root2);    //union the sets
                unionMessage = ",\tUnion(" + root1 + ", " + root2 + ") done\n";        //change what's printed if a union IS performed
            }
        }
        int mstTotalEdgeWeight = 0;
        for (Edge edge : mstEdges) mstTotalEdgeWeight += edge.getWeight();

        return mstTotalEdgeWeight;
    }
}


class Edge implements Comparable<Edge> {
    private int vertex1;    //an edge has 2 vertices & a weight
    private int vertex2;
    private double weight;

    public Edge(int vertex1, int vertex2, double weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public int getVertex1() {
        return vertex1;
    }

    public int getVertex2() {
        return vertex2;
    }

    public double  getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge otherEdge) {                //Compare based on edge weight (for sorting)
        return Double.compare(weight, otherEdge.getWeight());
    }

    @Override
    public String toString() {
        return "Edge (" + getVertex1() + ", " + getVertex2() + ") weight=" + getWeight();
    }
}


// DisjointSet class
//
// CONSTRUCTION: with int representing initial number of sets
//
// ******************PUBLIC OPERATIONS*********************
// void union(root1, root2) --> Merge two sets
// int find(x)              --> Return set containing x
// ******************ERRORS********************************
// No error checking is performed
// http://users.cis.fiu.edu/~weiss/dsaajava3/code/DisjSets.java

/**
 * Disjoint set class, using union by rank and path compression
 * Elements in the set are numbered starting at 0
 *
 * @author Mark Allen Weiss
 */
class DisjointSet {
    private int[] set;        //the disjoint set as an array

    public int[] getSet() {        //mostly debugging method to print array
        return set;
    }

    /**
     * Construct the disjoint sets object.
     *
     * @param numElements the initial number of disjoint sets.
     */
    public DisjointSet(int numElements) {        //constructor creates singleton sets
        set = new int[numElements];
        for (int i = 0; i < set.length; i++) {        //initialize to -1 so the trees have nothing in them
            set[i] = -1;
        }
    }

    /**
     * Union two disjoint sets using the height heuristic.
     * For simplicity, we assume root1 and root2 are distinct
     * and represent set names.
     *
     * @param root1 the root of set 1.
     * @param root2 the root of set 2.
     */
    public void union(int root1, int root2) {
        if (set[root2] < set[root1]) {        // root2 is deeper
            set[root1] = root2;        // Make root2 new root
        } else {
            if (set[root1] == set[root2]) {
                set[root1]--;            // Update height if same
            }
            set[root2] = root1;        // Make root1 new root
        }
    }

    /**
     * Perform a find with path compression.
     * Error checks omitted again for simplicity.
     *
     * @param x the element being searched for.
     * @return the set containing x.
     */
    public int find(int x) {
        if (set[x] < 0) {        //If tree is a root, return its index
            return x;
        }
        int next = x;
        while (set[next] > 0) {        //Loop until we find a root
            next = set[next];
        }
        return next;
    }

}