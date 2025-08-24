package comprehensive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/***
 * A text generator based on a Markov chain model.
 * 
 * Generator builds a weighted directed graph from a text file, where vertices
 * The generator builds a weighted directed graph from a text file, where
 * vertices represent words and edges represent transitions weighted by
 * frequency. Users can generate text sequences in three modes: - Probable:
 * outputs the k most probable most likely next words - Random: selects the next
 * word semi randomly, with selection weighted by probability - Deterministic :
 * always selects the single most probable next word.
 * 
 * @author - Samantha Watrin
 * @version - August 24, 2025
 */
public class TextGenerator {
	/**
	 * Carries out the command line args for the textGenerator class, allows user to
	 * select probable, deterministic, or random output
	 * 
	 * @param args - argument array
	 * @throws IOException - if the file cannot be found
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 4) {
			System.err.println("Usage: java TextGenerator <file> <startWord> <length> <mode>");
			return;
		}

		String fileName = args[0];
		String startWord = args[1].toLowerCase();
		int length = Integer.parseInt(args[2]);
		String mode = args[3].toLowerCase();

		WeightedGraph graph = buildGraph(fileName);
		List<String> generatedWords = generateText(graph, startWord, length, mode);

		System.out.println(String.join(" ", generatedWords));
	}

	/**
	 * Builds a weighted graph of words and transitions from the input text file.
	 * 
	 * @param fileName - file to be parsed
	 * @return - the weighted graph
	 * @throws IOException - if the file cannot be found
	 */
	private static WeightedGraph buildGraph(String fileName) throws IOException {
		WeightedGraph graph = new WeightedGraph();
		List<String> words = parseWords(fileName);

		for (int i = 0; i < words.size() - 1; i++) {
			graph.addEdge(words.get(i), words.get(i + 1));
		}
		graph.calculateProbabilities();
		return graph;
	}

	/**
	 * Generates text of the specified length using the given mode
	 * 
	 * @param graph     - weighted graph of text
	 * @param startWord - the word to begin generation from
	 * @param length    - the number of words to generate
	 * @param mode      - (probable, random, or deterministic)
	 * @return list of generated words
	 */
	private static List<String> generateText(WeightedGraph graph, String startWord, int length, String mode) {
		List<String> output = new ArrayList<>();
		output.add(startWord);
		String current = startWord;
		Random rand = new Random();

		for (int i = 1; i < length; i++) {
			Vertex vertex = graph.getVertex(current);
			if (vertex == null || vertex.getAdjacencyList().isEmpty()) {
				current = startWord; // fallback
			} else {
				switch (mode) {
				case "probable":
					return getProbable(graph, startWord, length);
				case "random":
					current = getRandom(vertex, rand);
					break;
				default: // deterministic
					current = getDeterministic(vertex);
					break;
				}
			}
			output.add(current);
		}
		return output;
	}

	/**
	 * Parses the words from the specific file, normalizes to lowercase, and returns
	 * them as an ArrayList of strings.
	 * 
	 * @param fileName - name of the file to be parsed
	 * @return words - array list of the words in the file
	 * @throws IOException - if the file cannot be found
	 * 
	 * 
	 */
	public static List<String> parseWords(String fileName) throws IOException {
		List<String> words = new ArrayList<>();
		Scanner fileReader = new Scanner(new File(fileName));
		while (fileReader.hasNext()) {
			String str = fileReader.next().toLowerCase();
			StringBuilder word = new StringBuilder();
			for (char character : str.toCharArray()) {
				if (Character.isLetterOrDigit(character) || character == '\'' || character == '_') {
					word.append(character);
				} else if (word.length() > 0) {
					words.add(word.toString());
					word.setLength(0);
				}
			}
			if (word.length() > 0) {
				words.add(word.toString());
			}
		}
		fileReader.close();
		return words;
	}

	/**
	 * Returns the most probable next words from the graph, up to {@code k}.
	 *
	 * @param graph - the word transition graph
	 * @param word  - starting word
	 * @param k     - number of words to return
	 * @return list of probable next words
	 */
	private static List<String> getProbable(WeightedGraph graph, String word, int k) {
		Vertex vertex = graph.getVertex(word);
		if (vertex == null) {
			return new ArrayList<>();
		}
		ArrayList<Edge> edges = new ArrayList<Edge>(vertex.getAdjacencyList());
		edges.sort(new WordCmp());
		ArrayList<String> result = new ArrayList<>();
		for (int i = 0; i < Math.min(k, edges.size()); i++) {
			result.add(edges.get(i).getOtherVertex().getWord());
		}
		return result;
	}

	/**
	 * Returns the most probable next word from a given vertex. Breaks ties
	 * lexicographically.
	 *
	 * @param vertex the current vertex
	 * @return the most probable next word
	 */
	private static String getDeterministic(Vertex vertex) {
		if (vertex.getAdjacencyList().isEmpty())
			return vertex.getWord();
		Edge bestEdge = null;
		for (Edge edge : vertex.getAdjacencyList()) {
			if (bestEdge == null || edge.getProbability() > bestEdge.getProbability()
					|| (edge.getProbability() == bestEdge.getProbability()
							&& edge.getOtherVertex().getWord().compareTo(bestEdge.getOtherVertex().getWord()) < 0)) {
				bestEdge = edge;
			}
		}
		return bestEdge.getOtherVertex().getWord();
	}

	/**
	 * Returns the next word after this vertex semi randomly, using probabilites to
	 * select
	 * 
	 * @param vertex - root vertex
	 * @param rnd    - instance of random
	 * @return - next word
	 */
	private static String getRandom(Vertex vertex, Random rnd) {
		List<Edge> edges = vertex.getAdjacencyList();
		double random = rnd.nextDouble();
		double cumulative = 0.0;
		for (Edge edge : edges) {
			cumulative += edge.getProbability();
			if (random <= cumulative) {
				return edge.getOtherVertex().getWord();
			}
		}
		return edges.get(0).getOtherVertex().getWord();
	}

	/**
	 * Comparator class for the edge probabilities
	 */
	public static class WordCmp implements Comparator<Edge> {

		@Override
		/**
		 * Compares edge1 to edge 2 based on probability, breaking ties
		 * lexicographically
		 * 
		 * @return - a positive int if edge1 is larger, and a negative int if edge2 is
		 *         larger
		 */
		public int compare(Edge edge1, Edge edge2) {
			double edge2prob = edge2.getProbability();
			double edge1prob = edge1.getProbability();
			double dif = edge2prob - edge1prob;
			if (dif == 0.0) {
				return edge1.getOtherVertex().getWord().compareTo(edge2.getOtherVertex().getWord());
			} else if (dif > 0)
				return 1;
			else
				return -1;
		}

	}
}
