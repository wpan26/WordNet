// This code builds a WordNet based on input synset and hypernym text file

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;

public class WordNet {

    // We use one symboltable to map from synset IDs to synsets
    private final HashMap<Integer, String> idSyn;
    // We use one symbol table to map from nouns to a collection of matching ids
    private final HashMap<String, Queue<Integer>> nounID;
    // Digraph representation
    private final Digraph wordNetDigraph;
    // Create a SCA object for use in methods
    private final ShortestCommonAncestor sca;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        // corner case testing
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("argument is null");
        }

        // initialize new STs
        idSyn = new HashMap<>();
        nounID = new HashMap<>();

        // Read from StdIn
        In synsetsInput = new In(synsets);
        while (synsetsInput.hasNextLine()) {
            // split field by first comma on each line
            String[] fields = synsetsInput.readLine().split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            // populate ST with id and corresponding synset
            idSyn.put(id, synset);

            // populate noun-to-ID ST by splitting within the synset
            // using a space as the delimiter. We use a Queue just because
            // it's a convenient collection to use
            for (String noun : synset.split(" ")) {
                Queue<Integer> idQueue = nounID.get(noun);
                if (idQueue == null) {
                    idQueue = new Queue<Integer>();
                    nounID.put(noun, idQueue);
                }
                idQueue.enqueue(id);
            }
        }

        // create new Digraph with the size equal to the number of IDs
        wordNetDigraph = new Digraph(idSyn.size());

        // Similar to above: read in hypernyms from StdIn
        In hypernymsInput = new In(hypernyms);
        while (hypernymsInput.hasNextLine()) {
            String[] fields = hypernymsInput.readLine().split(",");
            int synID = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int hypernymID = Integer.parseInt(fields[i]);

                // Constructing wordNet by adding synset-hypernym adjacencies
                wordNetDigraph.addEdge(synID, hypernymID);
            }
        }

        // create an SCA object for use in methods. This ensures that we meet
        // constructor performance requirements
        sca = new ShortestCommonAncestor(wordNetDigraph);

    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nounID.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

        // corner case
        if (word == null) {
            throw new IllegalArgumentException("argument is null");
        }

        return nounID.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {

        // corner cases
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("noun is not a WordNet noun");
        }

        // First we get the id of the ancestor of the two nouns; then we
        // return the appropriate synset corresponding to the id
        return idSyn.get(sca.ancestorSubset(nounID.get(noun1), nounID.get(noun2)));

    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {

        // corner cases
        if (noun1 == null || noun2 == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException("noun is not a WordNet noun");
        }

        // Return length of shortest common ancestor between two nouns
        return sca.lengthSubset(nounID.get(noun1), nounID.get(noun2));

    }

    // unit testing (required)
    public static void main(String[] args) {
        String synsets = args[0];
        String hypernyms = args[1];

        System.out.println("Testing Constructor:");
        WordNet wordnet = new WordNet(synsets, hypernyms);

        System.out.println("Testing iteration over all nouns:\n");
        System.out.println(wordnet.nouns());

        System.out.println("Is enzyme a noun?");
        System.out.println(wordnet.isNoun("enzyme"));

        System.out.println("Shortest Common Ancestor of enzyme and fibrin: ");
        System.out.println(wordnet.sca("enzyme", "fibrin"));

        System.out.println("Distance to SCA of Amazon and Arabian_sea: ");
        System.out.println(wordnet.distance("enzyme", "fibrin"));
    }

}
