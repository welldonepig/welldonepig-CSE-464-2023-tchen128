package org.parser;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class GraphParserTest {
    private GraphParser parser;

    @BeforeEach
    public void setup() {
        parser = new GraphParser();
    }

    @Test
    public void testParseGraph() {
        DirectedGraph<String, DefaultEdge> graph = parser.parseGraph("test.dot");
        assertNotNull(graph);
    }

    @Test
    public void testGetNumberOfNodes() {
        parser.parseGraph("test.dot"); // Load a test graph
        int numberOfNodes = parser.getNumberOfNodes();
        assertEquals(7, numberOfNodes);
    }

    @Test
    public void testGetNodeLabels() {
        parser.parseGraph("test.dot"); // Load a test graph
        Set<String> nodeLabels = parser.getNodeLabels();
        assertTrue(nodeLabels.contains("A"));
        assertTrue(nodeLabels.contains("B"));
    }

    @Test
    public void testGetNumberOfEdges() {
        parser.parseGraph("test.dot"); // Load a test graph
        int numberOfEdges = parser.getNumberOfEdges();
        assertEquals(6, numberOfEdges);
    }
    @Test
    public void testGetEdges() {
        parser.parseGraph("test.dot"); // Load a test graph
        Set<String> edges = parser.getEdges();
        assertTrue(edges.contains("A -> B"));
        assertTrue(edges.contains("B -> D"));
    }
}
