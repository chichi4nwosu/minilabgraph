import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;

/*************************************
 * Java Built-in Linked List Class:
 *
 *	-Constructor Examples
 *		LinkedList<String> list = new LinkedList<String>();
 *		LinkedList list2 = new LinkedList();
 *	
 *	-Add Example:
 *		list.add("red");
 *	
 *	-Some Relevant Methods:
 *		add(E e)
 *		add(int index, E element)
 *		addFirst()
 *		addLast()
 *		removeFirst()
 *		removeLast()
 *
 *		element() - Retrieves, but does not remove, the head (first element) of this list.
 *		getFirst() - Returns the first element in this list.
 *		getLast() - Returns the last element in this list.
 *		get(int index) - Returns the element at the specified position in this list.
 *
 *		set(int index, E element) - Replaces the element at the specified position in this list with the specified element.
 *
 *		size() - Returns the number of elements in this list
 *
 *************************************/

public class GraphsMiniLab1
{
	final static int numOfVertices = 10;
		
	public static void main (String[] args) throws IOException
	{
		Scanner scanner = new Scanner(new File("edgesA.txt"));
		ArrayList<String> edgeList = new ArrayList<>();
		
		while (scanner.hasNextLine()) {
			edgeList.add(scanner.nextLine());
		}
		
		printAdjMatrix(edgeList);
		printAdjList(edgeList);
	}
	
	static void printAdjMatrix(ArrayList<String> edgeList){
		/* Print an adjacency matrix given
		 * 	a list of a Graph's edges.
		 */
		int[][] adjMatrix = new int[numOfVertices][numOfVertices];
		
		for (String edge : edgeList) {
			String[] vertices = edge.split(",");
			int v1 = Integer.parseInt(vertices[0]);
			int v2 = Integer.parseInt(vertices[1]);
			adjMatrix[v1][v2] = 1;
			adjMatrix[v2][v1] = 1;
		}
		
		for (int i = 0; i < numOfVertices; i++) {
			for (int j = 0; j < numOfVertices; j++) {
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	static void printAdjList(ArrayList<String> edgeList){
		/* Print an adjacency list given
		 * 	a list of a Graph's edges.
		 *	
		 *	-Don't worry about sorting the Linked Lists
		 */
		LinkedList<Integer>[] adjList = new LinkedList[numOfVertices];
		 
		for (int i = 0; i < numOfVertices; i++) {
			adjList[i] = new LinkedList<>();
		}
		
		for (String edge : edgeList) {
			String[] vertices = edge.split(",");
			int v1 = Integer.parseInt(vertices[0]);
			int v2 = Integer.parseInt(vertices[1]);
			adjList[v1].add(v2);
			adjList[v2].add(v1);
		}
		
		for (int i = 0; i < numOfVertices; i++) {
			System.out.print(i + ": ");
			for (int vertex : adjList[i]) {
				System.out.print(vertex + " ");
			}
			System.out.println();
		}
	}

}
