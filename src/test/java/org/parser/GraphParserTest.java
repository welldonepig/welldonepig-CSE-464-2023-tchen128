package org.parser;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    public void testOutputGraph() throws IOException {
        String expectedFilePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/expected.txt";

        String inputFilePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/input.dot";
        parser.parseGraph(inputFilePath); // Load the test graph
        // Execute the outputGraph method
        String outputFilePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/output.txt";
        parser.outputGraph(outputFilePath);

        // Read the actual output from the generated "output.dot" file
        String actualOutput = readFileContent(outputFilePath);

        // Read the expected output from the "expected.dot" file
        String expectedOutput = readFileContent(expectedFilePath);

        // Compare the expected and actual outputs
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetNumberOfNodes() {
        String filePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/test.dot";
        parser.parseGraph(filePath); // Load a test graph
        int numberOfNodes = parser.getNumberOfNodes();
        assertEquals(5, numberOfNodes);
    }

    @Test
    public void testGetNodeLabels() {
        String filePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/test.dot";
        parser.parseGraph(filePath); // Load a test graph
        Set<String> nodeLabels = parser.getNodeLabels();
        assertTrue(nodeLabels.contains("A"));
        assertTrue(nodeLabels.contains("B"));
    }

    @Test
    public void testGetNumberOfEdges() {
        String filePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/test.dot";
        parser.parseGraph(filePath); // Load a test graph
        int numberOfEdges = parser.getNumberOfEdges();
        assertEquals(6, numberOfEdges);
    }
    @Test
    public void testGetEdges() {
        String filePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/test.dot";
        parser.parseGraph(filePath); // Load a test graph
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

    @Test
    public void testAddEdge() {
        GraphParser parser = new GraphParser();
        parser.addNode("A");
        parser.addNode("B");
        parser.addNode("C");

        // Test adding a valid edge
        parser.addEdge("A", "B");
        assertTrue(parser.getGraph().containsEdge("A", "B"));

        // Test adding an edge that already exists
        parser.addEdge("A", "B");
        assertTrue(parser.getGraph().containsEdge("A", "B"));

        // Test adding an edge with a missing source vertex
        parser.addEdge("X", "A");
        assertFalse(parser.getGraph().containsEdge("X", "A"));

        // Test adding an edge with a missing destination vertex
        parser.addEdge("A", "Y");
        assertFalse(parser.getGraph().containsEdge("A", "Y"));

        // Test adding an edge with both missing vertices
        parser.addEdge("X", "Y");
        assertFalse(parser.getGraph().containsEdge("X", "Y"));
    }


    @Test
    public void testOutputDOTGraph() throws IOException {
        String expectedFilePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/expected.dot";

        String inputFilePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/input.dot";
        parser.parseGraph(inputFilePath); // Load the test graph
        // Execute the outputDOTGraph method
        String outputFilePath = System.getProperty("user.dir")  + "/welldonepig-CSE-464-2023-tchen128/src/test/resources/output.dot";
        parser.outputDOTGraph(outputFilePath);

        // Read the actual output from the generated "output.dot" file
        String actualOutput = readFileContent(outputFilePath);

        // Read the expected output from the "expected.dot" file
        String expectedOutput = readFileContent(expectedFilePath);

        // Compare the expected and actual outputs
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    private String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
