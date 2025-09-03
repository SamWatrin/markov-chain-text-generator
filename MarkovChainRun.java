package comprehensive;

import java.io.IOException;

public class MarkovChainRun {

	public static void main(String[] args) {
		WeightedGraph markovChain = null;
		try {
			markovChain = TextGenerator.buildGraph("ShakespearText.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(markovChain.generateDot());

	}

}
