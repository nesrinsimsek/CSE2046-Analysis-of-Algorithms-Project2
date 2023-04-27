import java.io.*;
import java.util.*;

// A class to store a graph edge
class Edge {
    int source, dest;

    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
    }
}

// A class to represent a graph object
class Graph {
    // A list of lists to represent an adjacency list
    List<List<Integer>> adjList = null;

    // Constructor
    Graph(List<Edge> edges, int n) {
        adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        // add edges to the undirected graph
        for (Edge edge : edges) {
            int src = edge.source;
            int dest = edge.dest;

            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
    }
}

class Main
{
    public static String output = "";
    public static String nodeQuantity;
    // Add more colors for graphs with many more vertices
 
    public static List<Edge> readFile(String fileName) throws FileNotFoundException{
        List<Edge> readEdges = new ArrayList<>();
        try{
            File file = new File(fileName);
            Scanner scanFile = new Scanner(file);


            while(scanFile.hasNextLine()){
                while (scanFile.hasNext()) {
                    String s = scanFile.next();
                    if(s.equals("p")){
                        s = scanFile.next();
                        nodeQuantity = s;
                    }
                    if(s.equals("e")){
                        String firstNode = scanFile.next();
                        String secondNode = scanFile.next();
                        int first = Integer.parseInt(firstNode);
                        int second = Integer.parseInt(secondNode);
                        readEdges.add(new Edge(first, second));

                    }
                }   
            }        
            
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return readEdges;
    }

    // Function to assign colors to vertices of a graph
    public static void colorGraph(Graph graph, int n)
    {
  
        // keep track of the color assigned to each vertex
        Map<Integer, Integer> result = new HashMap<>();
 
        // assign a color to vertex one by one
        for (int u = 0; u < n; u++)
        {
            // set to store the color of adjacent vertices of `u`
            Set<Integer> assigned = new TreeSet<>();
 
            // check colors of adjacent vertices of `u` and store them in a set
            for (int i: graph.adjList.get(u))
            {
                if (result.containsKey(i)) {
                    assigned.add(result.get(i));
                }
            }
 
            // check for the first free color
            int color = 1;
            for (Integer c: assigned)
            {
                if (color != c) {
                    break;
                }
                color++;
            }
            
            // assign vertex `u` the first available color
            result.put(u, color);

        }
        int max=0;
        for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
            if(entry.getValue()>max)
                max = entry.getValue();
        }
        output += max + "\n";
        for (int v = 1; v < n; v++)
        {
            output += result.get(v)-1;
            if(v != n-1)
            output +=" ";
        }
    }

    // Greedy coloring of a graph
    public static void main(String[] args) throws IOException
    {
        System.out.println("deneme");
        List<Edge> edges;
        if(args.length == 0)
            edges = readFile("input.txt");
        else
            edges = readFile(args[0]);
        
 
        // total number of nodes in the graph (labelled from 0 to 5)
        int n = Integer.parseInt(nodeQuantity)+1;
 
        // build a graph from the given edges
        Graph graph = new Graph(edges, n);
 
        // color graph using the greedy algorithm
        colorGraph(graph, n);
        
        FileWriter writer;
        if(args.length == 0)
            writer = new FileWriter("output.txt");
        else
            writer = new FileWriter(args[1]);
        writer.write(output);
        writer.close();
  }
}   