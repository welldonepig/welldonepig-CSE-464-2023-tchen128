package org.parser;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphParser {

    private DirectedGraph<String, DefaultEdge> graph;

    public GraphParser() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    }
    public DirectedGraph<String, DefaultEdge> parseGraph(String filePath) {
       this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        File file = new File(filePath);

        try {
            DOTImporter<String, DefaultEdge> importer = new DOTImporter<>(new VertexProvider<String>() {
                @Override
                public String buildVertex(String label, Map<String, Attribute> attributes) {
                    return label;
                }
            }, new EdgeProvider<String, DefaultEdge>() {
                @Override
                public DefaultEdge buildEdge(String from, String to, String label, Map<String, Attribute> attributes) {
                    return new DefaultEdge();
                }
            });

            importer.importGraph(graph, file);
        } catch (ImportException e) {
            e.printStackTrace();
        }

        return graph;
    }

    public int getNumberOfNodes() {
        return graph.vertexSet().size();
    }

    public Set<String> getNodeLabels() {
        return graph.vertexSet();
    }

    public int getNumberOfEdges() {
        return graph.edgeSet().size();
    }

    public Set<String> getEdges() {
        Set<String> edges = new HashSet<>();
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            edges.add(source + " -> " + target);
        }
        return edges;
    }

    public void outputGraph(String filePath) {
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("digraph G {");
            for (DefaultEdge edge : graph.edgeSet()) {
                String source = graph.getEdgeSource(edge);
                String target = graph.getEdgeTarget(edge);
                writer.println("    " + source + " -> " + target + ";");
            }
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Number of Nodes: ").append(getNumberOfNodes()).append("\n");
        result.append("Node Labels: ").append(getNodeLabels()).append("\n");
        result.append("Number of Edges: ").append(getNumberOfEdges()).append("\n");
        result.append("Edge Direction: ").append(getEdges()).append("\n");
        return result.toString();
    }


    public static void main(String[] args) {

        GraphParser parser = new GraphParser();

        String path = System.getProperty("user.dir") + "/input.dot";
        parser.parseGraph(path);

        System.out.println(parser.toString());

        parser.outputGraph("output.dot");

    }
}
