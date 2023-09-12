// Client class to detect outcasts using WordNet

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    // single instance variable
    private final WordNet wordnet;

    // constructor initializes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // return outcast
    public String outcast(String[] nouns) {

        // champion variable
        String outcast = null;
        // champion variable
        int maxDist = Integer.MIN_VALUE;

        // iterated loop to find max distance
        for (String noun1 : nouns) {
            int totalDist = 0;
            for (String noun2 : nouns) {
                if (!noun1.equals(noun2)) {
                    totalDist += wordnet.distance(noun1, noun2);
                }
            }

            if (totalDist > maxDist) {
                maxDist = totalDist;
                outcast = noun1;
            }
        }

        // return noun with maximal distance
        return outcast;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
