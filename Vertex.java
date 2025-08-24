package comprehensive;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a Vertex object. Each vertex corresponds to a word and maintains:
 * - A List of outgoing edges to possible next words - A mapping of word to edge
 * for fast lookup - A count of how many words follow this word (frequency) - A
 * reference to the previous vertex (used for traversal and path tracking)
 * 
 * @author- Samantha Watrin
 * @version - April 21, 2025
 */
public class Vertex {
	private String value;
	private LinkedList<Edge> adjacencyList;
	private Vertex previous;
	private int wordsFollowingCount;
	private Map<String, Edge> edgeMap;

	/**
	 * Creates a new Vertex object, using the given word value.
	 * 
	 * @param value - string used to identify this vertex
	 */
	public Vertex(String value) {
		this.value = value;
		this.adjacencyList = new LinkedList<Edge>();
		this.previous = null;
		wordsFollowingCount = 0;
		this.edgeMap = new HashMap<String, Edge>();
	}

	/**
	 * Returns the word stored in this vertex
	 * 
	 * @return string used to identify this vertex
	 */
	public String getWord() {
		return value;
	}

	/**
	 * Adds a directed edge from this vertex to specified other vertex.
	 * 
	 * If the edge already exists, increments the edge's count to reflect another
	 * occurrence of the sequence (this word followed by the other word). Otherwise,
	 * creates a new edge to the destination vertex.
	 * 
	 * @param other - destination vertex the edge should connect to
	 */
	public void addEdge(Vertex other) {
		Edge existingEdge = edgeMap.get(other.getWord());
		if (existingEdge != null) {
			existingEdge.increaseCount();

		} else {
			Edge newEdge = new Edge(other);
			edgeMap.put(other.getWord(), newEdge);
			adjacencyList.add(newEdge);
		}

		wordsFollowingCount++;

	}

	/**
	 * Sets the value of the previous vertex.
	 * 
	 * @param previous - the value for which the previous vertex should be set.
	 */
	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	/**
	 * Returns the value of the previous vertex.
	 * 
	 * @return the value (word) of the previous vertex
	 */
	public Vertex getPreviousVertex() {
		return this.previous;
	}

	/**
	 * Returns the adjacency list for this vertex.
	 * 
	 * @return a linked list of outgoing edges
	 */
	public LinkedList<Edge> getAdjacencyList() {
		return adjacencyList;
	}

	/**
	 * Returns the total number of words that follow this vertex, including
	 * duplicates. This is the sum of counts across all edges. *
	 * 
	 * @return the total number of following words.
	 */
	public int getWordsFollowingCount() {
		return wordsFollowingCount;
	}

	/**
	 * Returns an iterator for accessing the outgoing edges of this vertex.
	 * 
	 * @return an iterator over the adjacency list
	 */
	public Iterator<Edge> edges() {
		return adjacencyList.iterator();
	}

	/**
	 * Returns a simple textual representation of this vertex.
	 * 
	 * @return formatted string showing the vertex and its connections
	 */
	@Override
	public String toString() {
		String result = "Vertex " + value + " has edge(s) to vertice(s) ";
		Iterator<Edge> edges = adjacencyList.iterator();
		while (edges.hasNext())
			result += edges.next() + " ";
		return result;
	}
}
