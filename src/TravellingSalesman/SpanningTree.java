package TravellingSalesman;

import java.util.ArrayList;
import java.util.Collections;

public class SpanningTree {
    public static Graph getMST(Graph graph) {
        Graph g = new Graph();

        while(true) {
            ArrayList<Edge<Vertex, Vertex>> edges = graph.getEdges();
            Collections.sort(edges);
            if(g.getVertices().containsAll(graph.getVertices())) break;
            System.out.println(g.getVertices());
            System.out.println(graph.getVertices());
            Edge<Vertex, Vertex> min = edges.get(0);
            g.addVertex(min.getFirst());
            g.addVertex(min.getSecond());
            g.addEdge(min);
            graph.removeEdge(min);
        }
        return g;
    }
}
