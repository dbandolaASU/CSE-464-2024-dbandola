import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.ImportException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class Feature1Test {

    private DotGraph test;

    @Before
    public void setUp() {
        test = new DotGraph();
    }

    @Test
    public void testParseValidGraph() {
        System.out.println("Starting Parse Valid Graph Test\n");
        String validGraphFile = "test.dot";

        // parse graph
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);

        // verify number of vertices
        assertEquals(3, graph.vertexSet().size());

        // Verify the vertices are correct
        assertTrue(graph.vertexSet().contains("a"));
        assertTrue(graph.vertexSet().contains("b"));
        assertTrue(graph.vertexSet().contains("c"));

        // verify number of edges
        assertEquals(2, graph.edgeSet().size());

        // verify edges are correct
        assertTrue(graph.containsEdge("a", "b"));
        assertTrue(graph.containsEdge("b", "c"));
    }

    @Test
    public void testParseEmptyGraph() {
        System.out.println("Starting Parse Empty Graph Test\n");
        String emptyGraphFile = "empty.dot";

        // Parse the graph
        Graph<String, DefaultEdge> graph = test.parseGraph(emptyGraphFile);

        // verify number of vertices and edges are 0
        assertEquals(0, graph.vertexSet().size());
        assertEquals(0, graph.edgeSet().size());
    }

    @Test
    public void testParseInvalidGraph() {
        System.out.println("Starting Parse Invalid Graph Test\n");
        String invalidGraphFile = "invalid.dot";
        try {
            // Parse the graph
            test.parseGraph(invalidGraphFile);
        } catch (Exception e) {
            assertTrue(e instanceof ImportException);  // check exception
        }
    }

    @Test
    public void testNonExistentFile() {
        String nonExistentFile = "nonexistent.dot";
        try {
            // Parse the graph
            test.parseGraph(nonExistentFile);
        } catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException);  // check exception
        }
    }

    @Test
    public void testToString() {
        String validGraphFile = "test.dot";

        // parse graph
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);
        // expected string
        String expectedOutput = "Number of nodes: 3\n" +
                "Node Labels: [a, b, c]\n" +
                "Number of Edges: 2\n" +
                "Edge Labels: a -> b, b -> c";
        // make sure toString matches expected string
        assertEquals(expectedOutput, test.toString());
    }

    @Test
    public void testOutputGraph(){
        String validGraphFile = "test.dot";
        List<String> expectedFileContents;
        List<String> actualFileContents;
        // parse graph
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);
        test.outputGraph("output.dot");
        Path expectedPath = Paths.get("src/test/resources/test.dot");
        Path actualPath = Paths.get("output.dot");

        // compare contents of output file and the original file
        try {
            expectedFileContents = Files.readAllLines(expectedPath);
            actualFileContents = Files.readAllLines(actualPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("Files are not equal!", expectedFileContents, actualFileContents);
    }

    @Test
    public void testNodeAdd(){
        // set up graph
        String validGraphFile = "test.dot";
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);
        String newNode = "d";

        // add new node
        test.addNode("d");

        // make sure new node is in graph
        Assert.assertTrue(graph.containsVertex(newNode));
    }

    @Test
    public void testNodesAdd(){
        // set up graph
        String validGraphFile = "test.dot";
        String expectedOutput = "Number of nodes: 5\n" +
                "Node Labels: [a, b, c, z, y]\n" +
                "Number of Edges: 2\n" +
                "Edge Labels: a -> b, b -> c";
        String[] nodesToAdd = {"a", "b", "z", "y"};

        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);

        // add new nodes
        test.addNodes(nodesToAdd);

        // make sure new node is in graph
        Assert.assertEquals(expectedOutput, test.toString());
    }

    @Test
    public void testDuplicateNodeAdd(){
        // set up graph
        String validGraphFile = "test.dot";
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);

        // node count before add
        int countBeforeAdd = graph.vertexSet().size();

        // add duplicate node
        test.addNode("a");

        // node count after add
        int countAfterAdd = graph.vertexSet().size();

        // make sure count is the same (dupe node not added)
        assertEquals("Duplicate node was added :(", countBeforeAdd, countAfterAdd);
    }

    @Test
    public void testEdgeAdd(){
        // set up graph
        String validGraphFile = "test.dot";
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);

        // add new edge
        test.addEdge("c", "a");

        // make sure new edge is in graph
        Assert.assertTrue(graph.containsEdge("c", "a"));
    }

    @Test
    public void testDupeEdgeAdd(){
        // set up graph
        String validGraphFile = "test.dot";
        Graph<String, DefaultEdge> graph = test.parseGraph(validGraphFile);

        // edge count before add
        int countBeforeAdd = graph.edgeSet().size();

        // add new duplicate edge
        test.addEdge("a", "b");

        // edge count after add
        int countAfterAdd = graph.edgeSet().size();

        // make sure new edge is in graph
        assertEquals("Duplicate edge was added :(", countBeforeAdd, countAfterAdd);
    }
}