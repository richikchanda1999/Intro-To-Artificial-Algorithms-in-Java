//package TravellingSalesman;
//
//import java.util.*;
//
//
//class Vertex<V>{
//    private V value;
//    public Vertex(V value) {
//        this.value = value;
//    }
//
//    public V getValue() {
//        return value;
//    }
//
//    public void setValue(V value) {
//        this.value = value;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        //sb.append("V: ");
//        sb.append(value);
//        return sb.toString();
//    }
//}
//
//class Edge implements Comparable<Edge> {
//    private double cost;
//    private Vertex v1, v2;
//
//    public Edge(Vertex v1, Vertex v2, double cost) {
//        this.v1 = v1;
//        this.v2 = v2;
//        this.cost = cost;
//    }
//
//    public double getCost() {
//        return cost;
//    }
//
//    public void setCost(double cost) {
//        this.cost = cost;
//    }
//
//    public void setV1(Vertex v1) {
//        this.v1 = v1;
//    }
//
//    public void setV2(Vertex v2) {
//        this.v2 = v2;
//    }
//
//    public Vertex getV1() {
//        return v1;
//    }
//
//    public Vertex getV2() {
//        return v2;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        //sb.append("E: ");
//        sb.append(v1);
//        sb.append(" -- ");
//        sb.append(v2);
//        return sb.toString();
//    }
//
//    @Override
//    public int compareTo(Edge edge) {
//        return Double.compare(cost, edge.cost);
//    }
//}
//
//public class Graph {
//    private ArrayList<Vertex> V = new ArrayList<Vertex>();
//    private ArrayList<Edge> E = new ArrayList<Edge>();
//
//    public void addVertex(Vertex v) {
//        V.add(v);
//    }
//
//    public void addEdge(Edge e) {
//        E.add(e);
//    }
//
//    public void removeVertex(Vertex v) {
//        V.remove(v);
//    }
//
//    public void removeEdge(Edge e) {
//        E.remove(e);
//    }
//
//    public ArrayList<Vertex> getAdjacentVertices(Vertex v) {
//        ArrayList<Vertex> adj = new ArrayList<Vertex>();
//        for (Edge e: E) {
//            if (e.getV1() == v) {
//                adj.add(e.getV2());
//                continue;
//            }
//            if (e.getV2() == v) {
//                adj.add(e.getV1());
//                continue;
//            }
//        }
//        return adj;
//    }
//
//    public ArrayList<Edge> getAdjacentEdges(Vertex v) {
//        ArrayList<Edge> adj = new ArrayList<Edge>();
//        for (Edge e: E) {
//            if (e.getV1() == v || e.getV2() == v) {
//                adj.add(e);
//            }
//        }
//        Edge[] a = null;
//        return adj;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(V);
//        sb.append("\n");
//        sb.append(E);
//        return sb.toString();
//    }
//
//    public ArrayList<Vertex> BFS(Vertex s) {
//        if (!V.contains(s))
//            return null;
//        ArrayList<Vertex> bfs = new ArrayList<Vertex>();
//        HashMap<Vertex, Integer> colours = new HashMap<>();
//        Queue<Vertex> Q = new LinkedList<>();
//
//        for (Vertex u: V) {
//            colours.put(u, 0);
//        }
//
//        Q.offer(s);
//        colours.put(s, 1);
//        while (!Q.isEmpty()) {
//            Vertex u = Q.poll();
//            for (Vertex v: getAdjacentVertices(u)) {
//                if (colours.get(v) == 0) {
//                    Q.offer(v);
//                    colours.put(v, 1);
//                }
//            }
//            bfs.add(u);
//            colours.put(u, 2);
//        }
//        return bfs;
//    }
//
//    public Collection<Object> getEdgeList() {
//    }
//}