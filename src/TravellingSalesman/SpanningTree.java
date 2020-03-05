package TravellingSalesman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static TravellingSalesman.TSP_A_Star.printGraph;

public class SpanningTree {
    public static Graph getMST(Graph graph) {
        ArrayList<Edge<Vertex, Vertex>> edges = graph.getEdges();
        Collections.sort(edges);
        Graph g = new Graph();

        while (true) {
            if (g.getVertices().containsAll(graph.getVertices())) break;
            Edge<Vertex, Vertex> min = edges.get(0);
            if (g.isVertexPresent(min.getFirst()) && g.isVertexPresent(min.getSecond())) {
                edges.remove(0);
                edges.remove(0);
                continue;
            }
            g.addVertex(min.getFirst());
            g.addVertex(min.getSecond());
            g.addEdge(min);
            edges.remove(0);
            edges.remove(0);
        }
        return g;
    }
}
