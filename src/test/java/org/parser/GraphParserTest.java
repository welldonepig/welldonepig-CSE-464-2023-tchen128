package org.parser;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parser.GraphParser;

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

    @Test
    public void testAddNode() {
        parser.addNode("A");
        parser.addNode("B");
        parser.addNode("C");

        assertEquals(3, parser.getNumberOfNodes()); // Three nodes added
        assertTrue(parser.getNodeLabels().contains("A"));
        assertTrue(parser.getNodeLabels().contains("B"));
        assertTrue(parser.getNodeLabels().contains("C"));
    }

    @Test
    public void testAddNodeWithDuplicate() {
        parser.addNode("X");
        parser.addNode("Y");
        parser.addNode("X"); // Attempt to add a duplicate

        assertEquals(2, parser.getNumberOfNodes()); // Only two unique nodes should be added
        assertTrue(parser.getNodeLabels().contains("X"));
        assertTrue(parser.getNodeLabels().contains("Y"));
    }

    @Test
    public void testAddNodes() {
        String[] nodes = {"A", "B", "C", "D"};
        parser.addNodes(nodes);

        assertEquals(4, parser.getNumberOfNodes()); // Four nodes added
        assertTrue(parser.getNodeLabels().contains("A"));
        assertTrue(parser.getNodeLabels().contains("B"));
        assertTrue(parser.getNodeLabels().contains("C"));
        assertTrue(parser.getNodeLabels().contains("D"));
    }

    @Test
    public void testAddNodesWithDuplicates() {
        String[] nodes = {"X", "Y", "X", "Z"};
        parser.addNodes(nodes);

        assertEquals(3, parser.getNumberOfNodes()); // Only three unique nodes should be added
        assertTrue(parser.getNodeLabels().contains("X"));
        assertTrue(parser.getNodeLabels().contains("Y"));
        assertTrue(parser.getNodeLabels().contains("Z"));
    }

}
