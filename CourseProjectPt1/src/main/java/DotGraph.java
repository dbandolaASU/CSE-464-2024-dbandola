import org.jgrapht.Graph;
import org.jgrapht.nio.dot.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.engine.Format;

import java.io.*;
import java.util.*;

// Daniel Bandola 1221595050
// CSE464 Course Project Pt. 3

class Path {
    private List<String> nodes;

    public Path() {
        this.nodes = new ArrayList<>();
    }
    public void addNode(String node) {
        nodes.add(node);
    }
    public List<String> getNodes() {
        return nodes;
    }
    @Override
    public String toString() {
        return String.join(" -> ", nodes);
    }
}

interface SearchStrategy {
    Path search(String src, String dst);
}

// PART THREE #2
abstract class XXXTemplate {
    protected Graph<String, DefaultEdge> graph;
    protected  Set<String> visited;
    protected Map<String, String> parentMap;

    public XXXTemplate(Graph<String, DefaultEdge> graph){
        this.graph = graph;
        this.visited = new HashSet<>();
        this.parentMap = new HashMap<>();
    }

    // template for search
    public final Path search(String src, String dst) {
        // check if node exists
        if (!graph.containsVertex(src) || !graph.containsVertex(dst)) {
            System.out.println("Source or destination node not found in the graph.");
            return null;
        }

        initializeFrontier(src);

        // graph traversal
        while (!isFrontierEmpty()) {
            String currentNode = getNextNode();

            // dst reached
            if (currentNode.equals(dst)) {
                return buildPath(src, dst);
            }

            processNeighbors(currentNode);
        }

        // dst is not reached
        System.out.println("No path found between " + src + " and " + dst);
        return null;
    }

    // abstract methods
    protected abstract void initializeFrontier(String src);
    protected abstract boolean isFrontierEmpty();
    protected abstract String getNextNode();
    protected abstract void addToFrontier(String node);


    private void processNeighbors(String currentNode) {
        for (DefaultEdge edge : graph.outgoingEdgesOf(currentNode)) {
            String neighbor = graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                addToFrontier(neighbor);
                visited.add(neighbor);
                parentMap.put(neighbor, currentNode);
            }
        }
    }

    // build the shortest path
    private Path buildPath(String src, String dst) {
        Path path = new Path();
        for (String at = dst; at != null; at = parentMap.get(at)) {
            path.addNode(at);
        }
        Collections.reverse(path.getNodes());
        return path;
    }
}

class BFSGraphSearch extends XXXTemplate implements SearchStrategy {
    private Queue<String> frontier;

    public BFSGraphSearch(Graph<String, DefaultEdge> graph) {
        super(graph);
        this.frontier = new LinkedList<>();
    }

    @Override
    protected void initializeFrontier(String src) {
        frontier.add(src);
        visited.add(src);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return frontier.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return frontier.poll();
    }

    @Override
    protected void addToFrontier(String node) {
        frontier.add(node);
    }
}

class DFSGraphSearch extends XXXTemplate implements SearchStrategy{
    private Deque<String> frontier;

    public DFSGraphSearch(Graph<String, DefaultEdge> graph) {
        super(graph);
        this.frontier = new ArrayDeque<>();
    }

    @Override
    protected void initializeFrontier(String src) {
        frontier.push(src);
        visited.add(src);
    }

    @Override
    protected boolean isFrontierEmpty() {
        return frontier.isEmpty();
    }

    @Override
    protected String getNextNode() {
        return frontier.pop();
    }

    @Override
    protected void addToFrontier(String node) {
        frontier.push(node);
    }
}

public class DotGraph {

    private Graph<String, DefaultEdge> graph;

    // REFACTOR 1: Extract Method
    public boolean nodeExists(String node){
        return graph.containsVertex(node);
    }

    // REFACTOR 2: Extract Method
    public boolean edgeExists(String src, String dst){
        return graph.containsEdge(src, dst);
    }

    // Feature 1:
    public Graph<String, DefaultEdge> parseGraph(String filepath) {
        // create dot importer and a new empty graph
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();
        importer.setVertexFactory(String::toString);
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filepath)) {
            // throw exception if file not found
            if (input == null) {
                throw new IOException("File not found: " + filepath);
            }
            importer.importGraph(graph, new InputStreamReader(input));
            System.out.println("Parsed graph from " + filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // set graph to the new imported graph
        this.graph = graph;
        // return the graph
        return graph;
    }

    // REFACTOR 5: Code Simplification
    @Override
    public String toString() {
        StringJoiner edges = new StringJoiner(", ");
        graph.edgeSet().forEach(edge -> edges.add(graph.getEdgeSource(edge) + " -> " + graph.getEdgeTarget(edge)));

        return String.format(
                "Number of nodes: %d\nNode Labels: %s\nNumber of Edges: %d\nEdge Labels: %s",
                graph.vertexSet().size(),
                graph.vertexSet(),
                graph.edgeSet().size(),
                edges
        );
    }

    public void outputGraph(String filepath){
        // create structure of a .dot file and write to the given filepath
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write("digraph testGraph{\n");
            for (String vertex : graph.vertexSet()) {
                fileWriter.write("    " + vertex + ";\n");
            }
            for (DefaultEdge edge : graph.edgeSet()) {
                fileWriter.write("    " + graph.getEdgeSource(edge) + " -> " + graph.getEdgeTarget(edge) + ";\n");
            }
            fileWriter.write("}\n");

            System.out.println("Graph output to file " + filepath + "!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Feature 2

    // REFACTOR 3: Combine Methods
    public void addNodes(String... nodes) {
        for (String node : nodes) {
            if (nodeExists(node)) {
                System.out.println("Node " + node + " already exists!");
            } else {
                graph.addVertex(node);
                System.out.println("Node " + node + " added!");
            }
        }
    }

    // Feature 3:

    public void addEdge(String src, String des){
        // check if edge exists
        if (edgeExists(src, des)){
            System.out.println("Duplicate Vertex: " + src + " -> " + des);
        }
        // add edge
        else {
            graph.addEdge(src, des);
            System.out.println("Edge " + src + " to " + des + " added!");
        }
    }

    // Feature 4:

    public void generateGraphImage(String filepath) {
        MutableGraph g = Factory.mutGraph("Test");
        // add nodes
        for (String vertex : graph.vertexSet()) {
            g.add(Factory.node(vertex));
        }
        // add edges
        for (DefaultEdge edge : graph.edgeSet()) {
            g.add(Factory.node(graph.getEdgeSource(edge)).link(Factory.node(graph.getEdgeTarget(edge))));
        }
        // save image to designated file
        try{
            Graphviz.fromGraph(g).width(600).render(Format.PNG).toFile(new File(filepath));
            System.out.println("Graph image saved to " + filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Project Part 2 #1

    // REFACTOR 4: Combine Methods
    public void removeNodes(String... nodes){
        for (String node : nodes) {
            if (!nodeExists(node)){
                System.out.println("Node" + node + " does not exist!");
            } else {
                graph.removeVertex(node);
                System.out.println("Node " + node + " removed!");
            }
        }
    }

    public void removeEdge(String srcLabel, String dstLabel){
        if (edgeExists(srcLabel,dstLabel)){
            graph.removeEdge(srcLabel, dstLabel);
            System.out.println("Edge " + srcLabel + " to " + dstLabel + " was removed!");
        }
        else{
            System.out.println("Edge " + srcLabel + " to " + dstLabel + " does not exist!");
        }
    }

    public enum Algorithm {
        BFS, DFS
    }

    public Path GraphSearch(String src, String dst, Algorithm algo) {
        SearchStrategy strategy;

        // choose algo based on enum
        switch (algo) {
            case BFS:
                strategy = new BFSGraphSearch(graph);
                break;
            case DFS:
                strategy = new DFSGraphSearch(graph);
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algo);
        }
        return strategy.search(src, dst);
    }

    // personal tests :))
    public static void main(String[] args) {
        DotGraph graph = new DotGraph();
        graph.parseGraph("input.dot");
        System.out.println(graph.toString());
        graph.addNodes("z");
        graph.removeNodes("z");

        Path path = graph.GraphSearch("a", "h", Algorithm.BFS);
        if (path != null) {
            System.out.println("Path found: " + path);
        } else {
            System.out.println("No path exists between the specified nodes.");
        }
    }
}