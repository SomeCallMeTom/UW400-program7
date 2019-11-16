import java.util.List;

/**
 * A generic graph interface. Edges are undirected and unweighted. You may choose how to implement the graph data 
 * structure (adjacency matrix, adjacency list) and you may use any of these built-in Java data structures in your 
 * implementation: ArrayList, HashMap, LinkedList, List, Map, Set.
 * @param <T> vertex type
 */
public interface GraphADT<T> {
	/**
	 * Adds a new vertex to the graph. If the vertex already exists in the graph, returns without throwing an exception 
	 * or adding a vertex.
	 * @param vertex to be added
	 * @throws IllegalArgumentException if the vertex is null
	 */
	public void addVertex(T vertex) throws IllegalArgumentException;

	/**
	 * Removes a vertex and all associated edges from the graph. If the vertex does not exist in the graph, returns 
	 * without throwing an exception or removing a vertex.
	 * @param vertex to be removed
	 * @throws IllegalArgumentException if the vertex is null
	 */
	public void removeVertex(T vertex) throws IllegalArgumentException;

	/**
	 * Adds an undirected edge from vertex1 to vertex2 to the graph. If either vertex does not exist in the graph or if 
	 * the edge already exists in the graph, returns without throwing an exception or adding an edge.
	 * @param vertex1 first vertex
	 * @param vertex2 second vertex
	 * @throws IllegalArgumentException if either vertex is null
	 */
	public void addEdge(T vertex1, T vertex2) throws IllegalArgumentException;

	/**
	 * Removes the undirected edge from vertex1 to vertex2 from the graph. If either vertex does not exist in the graph 
	 * or if the edge does not exist in the graph, returns without throwing an exception or removing an edge.
	 * @param vertex1 first vertex
	 * @param vertex2 second vertex
	 * @throws IllegalArgumentException if either vertex is null
	 */
	public void removeEdge(T vertex1, T vertex2) throws IllegalArgumentException;

	/**
	 * Returns a list containing all vertices in the graph.
	 * @return List<T> where T is the vertex type
	 */
	public List<T> getAllVertices();

	/**
	 * Get all neighboring (adjacent) vertices of a vertex. If the vertex does not exist in the graph, returns an empty 
	 * list.
	 * @param vertex specified vertex
	 * @return List<T> of all adjacent vertices for the specified vertex
	 * @throws IllegalArgumentException if the vertex is null
	 */
	public List<T> getAdjacentVerticesOf(T vertex) throws IllegalArgumentException;

	/**
	 * Returns the number of edges in the graph.
	 * @return number of edges in graph
	 */
	public int size();

	/**
	 * Returns the number of vertices in the graph.
	 * @return number of vertices in graph
	 */
	public int order();
}
