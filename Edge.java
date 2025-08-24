package comprehensive;

/**
 * Represents a directed edge object that holds the weight(probability) as well
 * as the destination vertex
 * 
 * @author - Samantha Watrin
 * @version - August 24, 2025
 */
public class Edge {
	private Vertex destination;
	private double probability;
	private int count;

	/**
	 * Constructs an edge object, pointing to the destination vertex. Initial
	 * occurrence count is set to 1.
	 * 
	 * @param destination - destination Vertex
	 */
	public Edge(Vertex destination) {
		this.destination = destination;
		this.count = 1;
	}

	/**
	 * Increases the occurrence count of this edge. 
	 */
	public void increaseCount() {
		this.count++;
	}

	/**
	 * Returns the count.
	 * 
	 * @return - the occurrence count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the destination vertex for this edge.
	 * 
	 * @return destination vertex.
	 */
	public Vertex getOtherVertex() {
		return this.destination;
	}

	/**
	 * Sets the probability(weight) of the given edge.
	 * 
	 * @param probability - the probability to assign.
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}

	/**
	 * Gets this edges probability.
	 * 
	 * @return - this edges probability
	 */
	public double getProbability() {
		return this.probability;
	}

	/**
	 * Generates a simple textual representation of this edge.
	 * 
	 * @return simple string representation of this edge
	 */
	@Override
	public String toString() {
		return this.destination.getWord().toString();
	}
}
