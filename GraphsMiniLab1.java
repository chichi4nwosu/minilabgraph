import java.util.Scanner;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;

public class Graph implements AirlineGraph {

    private int[][] graph; // A two-dimensional adjacency matrix.
    private Stack<Integer> stack; // A last in, first out data structure.

    // Constructor
    public Graph() {
        graph = new int[SIZE][SIZE];
        stack = new Stack<>();
        fillGraph();
    }

    // Method to fill the graph from the connections file
    private void fillGraph() {
        try {
            Scanner scanner = new Scanner(new File("connections.dat"));
            for (int row = 0; row < SIZE; row++) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                for (int col = 0; col < SIZE; col++) {
                    graph[row][col] = Integer.parseInt(parts[col]);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to find the index position of an airport code
    private int findAirportCode(String airportCode) {
        for (int i = 0; i < SIZE; i++) {
            if (airportCode.equals(Graph.airportCode[i])) {
                return i;
            }
        }
        return -1; // Not found
    }

    // Method to check if an edge is adjacent
    private boolean adjacent(Point edge) {
        return graph[edge.x][edge.y] > 0;
    }

    // Method to find a path of a specified length between two points
    private boolean findPath(int length, Point p) {
        if (length == 1) {
            if (adjacent(p)) {
                stack.push(p.y); // Push the ending city onto the stack
                return true;
            } else {
                for (int node = 0; node < SIZE; node++) {
                    if (graph[p.x][node] > 0 && findPath(length - 1, new Point(node, p.y))) {
                        stack.push(p.x); // Push the current city/node onto the stack
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Method to find the shortest path from a source city to all other cities
    public int[] shortestPath(String source) {
        int[] dist = new int[SIZE];
        boolean[] visited = new boolean[SIZE];
        for (int i = 0; i < SIZE; i++) {
            dist[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        dist[findAirportCode(source)] = 0;
        for (int count = 0; count < SIZE - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;
            for (int v = 0; v < SIZE; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }
        return dist;
    }

    // Method to find the minimum distance vertex
    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int v = 0; v < SIZE; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Method to find the cheapest route from start to end
    public String cheapestRoute(String start, String end) {
        int[] dist = shortestPath(start);
        stack.clear();
        int startIndex = findAirportCode(start);
        int endIndex = findAirportCode(end);
        if (findPath(dist[endIndex], new Point(startIndex, endIndex))) {
            StringBuilder route = new StringBuilder(Graph.city[startIndex]);
            while (!stack.isEmpty()) {
                route.append(" -> ").append(Graph.city[stack.pop()]);
            }
            return route.toString() + " Total fare: $" + graph[startIndex][endIndex];
        } else {
            return "There isn't a connection!";
        }
    }

    // Method to represent the graph as a string
    public String toString() {
        StringBuilder sb = new StringBuilder("Airline Graph\n");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(graph[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
