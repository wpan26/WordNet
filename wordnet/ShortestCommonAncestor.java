// finds shortest common ancestor and length

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class ShortestCommonAncestor {

    // instance variable Digraph to perform operations on
    private final Digraph G;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {

        // checking if rooted DAG
        if (!isRootedDAG(G)) {
            throw new IllegalArgumentException("This is not a rooted DAG!");
        }

        this.G = new Digraph(G);
    }

    // Check for Rooted and DAG conditions
    private boolean isRootedDAG(Digraph G) {

        // tracks the number of roots for later use
        int roots = 0;

        // Check if the graph is acyclic using existing testing method
        DirectedCycle cycleDetector = new DirectedCycle(G);
        if (cycleDetector.hasCycle()) {
            return false;
        }

        // use outdegree() instead of indegree() due to wordnet representation
        // if roots does not equal 1, then the DAG is not rooted
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) == 0) {
                roots++;
                if (roots > 1) {
                    System.out.println(roots);
                    return false;
                }
            }
        }
        return roots == 1;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {

        // create two bfs objects for v and w
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);

        int minDist = Integer.MAX_VALUE;

        // update minimum distance by iterating through vertices
        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int dist = bfsV.distTo(i) + bfsW.distTo(i);
                minDist = Math.min(minDist, dist);
            }
        }

        return minDist;

    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {

        // initialize ancestor variable
        int ancestor = -1;

        // as before, create two bfs objects
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
        int minDist = Integer.MAX_VALUE;

        // iterate through, and if new minimum distance found, update ancestor
        for (int i = 0; i < G.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                int dist = bfsV.distTo(i) + bfsW.distTo(i);
                if (dist < minDist) {
                    minDist = dist;
                    ancestor = i;
                }
            }
        }

        return ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        // All of this code is the same as before but we use the bfs constructor
        // that works with iterables
        BreadthFirstDirectedPaths bfsA = new BreadthFirstDirectedPaths(G, subsetA);
        BreadthFirstDirectedPaths bfsB = new BreadthFirstDirectedPaths(G, subsetB);
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (bfsA.hasPathTo(i) && bfsB.hasPathTo(i)) {

                int dist = bfsA.distTo(i) + bfsB.distTo(i);
                minDist = Math.min(minDist, dist);
            }
        }

        return minDist;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        // All of this code is the same as before but we use the bfs constructor
        // that works with iterables

        int ancestor = -1;
        BreadthFirstDirectedPaths bfsA = new BreadthFirstDirectedPaths(G, subsetA);
        BreadthFirstDirectedPaths bfsB = new BreadthFirstDirectedPaths(G, subsetB);
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < G.V(); i++) {
            if (bfsA.hasPathTo(i) && bfsB.hasPathTo(i)) {
                int dist = bfsA.distTo(i) + bfsB.distTo(i);
                if (dist < minDist) {
                    minDist = dist;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // reading from StdIn and testing constructor, length and ancestor methods
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

            // We create our own iterable subsets from the file we select
            // which is digraph1.txt
            Integer[] subsetA = { 3, 7 };
            Iterable<Integer> iterableA = Arrays.asList(subsetA);
            Integer[] subsetB = { 4, 10 };
            Iterable<Integer> iterableB = Arrays.asList(subsetB);

            // Testing subset length and ancestor methods
            int subLength = sca.lengthSubset(iterableA, iterableB);
            int subAncestor = sca.ancestorSubset(iterableA, iterableB);
            System.out.println("Subset length = " + subLength);
            System.out.println("Subset ancestor = " + subAncestor);
        }
    }

}
