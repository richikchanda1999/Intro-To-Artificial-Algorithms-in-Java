package TravellingSalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Graph {
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge<Vertex, Vertex>> edges;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Graph(ArrayList<Vertex> vertices, ArrayList<Edge<Vertex, Vertex>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Edge<Vertex, Vertex>> getEdges() {
        return edges;
    }

    public ArrayList<Edge<Vertex, Vertex>> getEdges(Vertex v) {
        ArrayList<Edge<Vertex, Vertex>> ret = new ArrayList<>();
        edges.forEach((edge) -> {
            if (edge.getFirst().compareTo(v) == 0) ret.add(edge);
        });
        return ret;
    }

    public void setEdges(ArrayList<Edge<Vertex, Vertex>> edges) {
        this.edges = edges;
    }

    public void addVertex(Vertex v) {
        if (!vertices.contains(v))
            vertices.add(v);
    }

    public void removeVertex(Vertex v) {
        vertices.forEach((vertex) -> {
            if (vertex.compareTo(v) == 0) vertices.remove(v);
        });
        edges.forEach((edge) -> {
            if (edge.getFirst().compareTo(v) == 0) edges.remove(edge);
            if (edge.getSecond().compareTo(v) == 0) edges.remove(edge);
        });
    }

    public boolean isVertexPresent(Vertex v) {
        return vertices.contains(v);
    }

    public void addEdge(Vertex a, Vertex b, double weight) {
        Edge<Vertex, Vertex> newEdge = new Edge<>(a, b, weight);
        Edge<Vertex, Vertex> newEdgeRev = new Edge<>(a, b, weight);
        if (!edges.contains(newEdge) && !edges.contains(newEdgeRev)) {
            edges.add(newEdge);
            edges.add(newEdgeRev);
        }
    }

    public void addEdge(Edge<Vertex, Vertex> edge) {
        Edge<Vertex, Vertex> rev = new Edge<>(edge.getSecond(), edge.getFirst(), edge.getWeight());
        if (!edges.contains(edge) && !edges.contains(rev)) {
            edges.add(edge);
            edges.add(rev);
        }
    }

    public void removeEdge(Edge<Vertex, Vertex> edge) {
        edges.remove(edge);
        edges.remove(new Edge<>(edge.getSecond(), edge.getFirst(), edge.getWeight()));
    }

    public boolean isEdgePresent(Edge e) {
        return edges.contains(e);
    }

    public Graph copy() {
        ArrayList<Vertex> verticesNew = new ArrayList<>();
        verticesNew.addAll(vertices);
        ArrayList<Edge<Vertex, Vertex>> edgesNew = new ArrayList<>();
        edgesNew.addAll(edges);

        return new Graph(verticesNew, edgesNew);
    }

    public Graph excluding(ArrayList<Vertex> vertices) {
        Graph g = copy();
        for(int i = 0 ; i < vertices.size() ; ++i) {
            g.removeVertex(vertices.get(i));
        }
        return g;
    }

    public static Graph randomGraph(int N, double edgeMin, double edgeMax) {
        Graph g = new Graph();
        ArrayList<Vertex> vertices = g.getVertices();
        for (int i = 0; i < N; ++i) {
            vertices.add(new Vertex(i + 1));
        }

        ArrayList<Edge<Vertex, Vertex>> edges = g.getEdges();
        Random random = new Random();
        for (int i = 1; i <= N; ++i) {
            Vertex i_v = vertices.get(i - 1);
            for (int j = i + 1; j <= N; ++j) {
                double edgeWeight = edgeMin + (edgeMax - edgeMin) * (random.nextDouble());
                String s = String.valueOf(edgeWeight);
                s = s.substring(0, s.indexOf('.') + 3);
                edgeWeight = Double.parseDouble(s);
                Vertex j_v = vertices.get(j - 1);
                Edge<Vertex, Vertex> edge = new Edge<>(i_v, j_v, edgeWeight);
                g.addEdge(edge);
            }
        }
        return g;
    }

    public double getTotalWeight(Graph graph) {
        double total = 0.0;
        ArrayList<Edge<Vertex, Vertex>> edges = graph.getEdges();
        Collections.sort(edges);

        for(int i = 0 ; i < edges.size() ; i += 2) {
            Edge edge = edges.get(i);
            total += edge.getWeight();
        }
        return total;
    }
}

class Edge<P, T> implements Comparable<Edge<P, T>> {
    private double weight;
    private P a;
    private T b;

    public Edge(P a, T b, double weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public P getFirst() {
        return a;
    }

    public T getSecond() {
        return b;
    }

    public void setFirst(P a) {
        this.a = a;
    }

    public void setSecond(T b) {
        this.b = b;
    }

    @Override
    public int compareTo(Edge edge) {
        if(weight < edge.getWeight()) return -1;
        else if(weight == edge.getWeight()) return 0;
        else return 1;
    }

    @Override
    public String toString() {
        return a + " ---> " + b + " (" + weight + ")";
    }
}

class Vertex implements Comparable<Vertex> {
    private int value;
    //private ArrayList<Vertex> neighbours;

//    public Vertex(int value, ArrayList<Vertex> neighbours) {
//        this.value = value;
//        this.neighbours = neighbours;
//    }

    public Vertex(int value) {
        this.value = value;
        //neighbours = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

//    public ArrayList<Vertex> getNeighbours() {
//        return neighbours;
//    }
//
//    public void setNeighbours(ArrayList<Vertex> neighbours) {
//        this.neighbours = neighbours;
//    }

    @Override
    public int compareTo(Vertex vertex) {
        return value - vertex.getValue();
    }

    public String toString() {
        return "Vertex (" + value + ")";
    }
}
