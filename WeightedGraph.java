package comprehensive;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains a weighted directed graph, backed by a HashMap of vertices.
 * Each vertex is keyed by its associated word, and edges between vertices
 * contain probabilities based on occurrence counts.
 * 
 * @author- Samantha Watrin
 * @version - April 21, 2025
 * 
 */
public class WeightedGraph {
	private Map<String, Vertex> vertices;

	/**
	 * Creates an empty WeightedGraph object.
	 */
	public WeightedGraph() {
		vertices = new HashMap<String, Vertex>();
	}

	/**
	 * Returns the size of this weighted graph.
	 * 
	 * @return the number of vertices
	 */
	public int getSize() {
		return vertices.size();
	}

	/**
	 * Returns the vertex represented by the given word.
	 * 
	 * @param word - corresponding word of the vertex
	 * @return - the vertex containing the given word data
	 */
	public Vertex getVertex(String word) {
		return vertices.get(word);
	}

	/**
	 * Adds a directed edge from the source vertex to the destination vertex.
	 * 
	 * If either vertex does not already exist in this graph is is created and
	 * added.
	 * 
	 * @param srcValue - word representing the source vertex
	 * @param dstName  - word representing the destination vertex
	 */
	public void addEdge(String srcValue, String dstValue) {
		Vertex srcVertex;
		// if vertex already exists in graph, get its object
		if (vertices.containsKey(srcValue))
			srcVertex = vertices.get(srcValue);

		// else, create a new object and add to graph
		else {
			srcVertex = new Vertex(srcValue);
			vertices.put(srcValue, srcVertex);
		}

		Vertex dstVertex;
		if (vertices.containsKey(dstValue))
			dstVertex = vertices.get(dstValue);
		else {
			dstVertex = new Vertex(dstValue);
			vertices.put(dstValue, dstVertex);
		}

		// add new directed edge from srcVertex to dstVertex
		srcVertex.addEdge(dstVertex);
	}

	/**
	 * Calculates and assigns the probabilities for all word pairs(edges) in the
	 * graph.
	 * 
	 * The probability of an edge is calculated by the count of times its
	 * destination word appears divided by the total number of words following the
	 * source vertex.
	 */
	public void calculateProbabilities() {
		for (Vertex vertex : vertices.values()) {
			int totalWords = vertex.getWordsFollowingCount();
			for (Edge edge : vertex.getAdjacencyList()) {
				double probability = (double) edge.getCount() / totalWords;
				edge.setProbability(probability);
			}
		}
	}

	/**
	 * Returns a simple textual representation of this graph.
	 * 
	 * @return  string representation of this graph
	 */
	public String toString() {
		String result = "";
		for (Vertex v : vertices.values())
			result += v + "\n";
		return result;
	}

}
