import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.dot.*;

import java.io.*;

// Daniel Bandola 1221595050
// CSE464 Course Project Pt. 1

public class DotGraph {

    private Graph<String, DefaultEdge> graph;

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

    @Override
    public String toString(){
        StringBuilder graphString = new StringBuilder();
        // add number of nodes to string
        graphString.append("Number of nodes: ").append(graph.vertexSet().size()).append("\n");
        // add nodes to string
        graphString.append("Node Labels: ").append(graph.vertexSet()).append("\n");
        // add number of edges to string
        graphString.append("Number of Edges: ").append(graph.edgeSet().size()).append("\n");
        // add all the edges to the string
        graphString.append("Edge Labels: ");
        for (DefaultEdge edge : graph.edgeSet()) {
            graphString.append(graph.getEdgeSource(edge)).append(" -> ").append(graph.getEdgeTarget(edge)).append(", ");
        }
        // remove final ,
        graphString.setLength(graphString.length() - 2);
        return graphString.toString();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Feature 2

    public void addNode(String node) {
        // check if node exists
        if (graph.containsVertex(node)){
            System.out.println("Duplicate Node: " + node);
        }
        // add node
        else {
            graph.addVertex(node);
            System.out.println("Node " + node + " added!");
        }
    }

    public void addNodes (String[] nodes){
        for (String node: nodes){
            if (graph.containsVertex(node)){
                System.out.println("Node " + node + " already exists!");
            }
            else{
                graph.addVertex(node);
                System.out.println("Node " + node + " added!");
            }
        }
    }

    public void addEdge(String src, String des){
        // check if edge exists
        if (graph.containsEdge(src, des)){
            System.out.println("Duplicate Vertex: " + src + " -> " + des);
        }
        // add edge
        else {
            graph.addEdge(src, des);
        }
    }

    public static void main(String[] args) {
        DotGraph test = new DotGraph();
        test.parseGraph("localtest.dot");
        System.out.println(test.toString());
        String[] addList = {"a", "z", "b", "y"};
        test.addNodes(addList);
        System.out.println(test.toString());
    }
}