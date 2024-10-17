import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.ImportException;
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
        String emptyGraphFile = "empty.dot";

        // Parse the graph
        Graph<String, DefaultEdge> graph = test.parseGraph(emptyGraphFile);

        // verify number of vertices and edges are 0
        assertEquals(0, graph.vertexSet().size());
        assertEquals(0, graph.edgeSet().size());
    }

    @Test
    public void testParseInvalidGraph() {
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
        Path expectedPath = Paths.get("test.dot");
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
}