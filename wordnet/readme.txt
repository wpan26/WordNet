Programming Assignment 6: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */

We used a symbol table to store the synsets information. This was for
combatibility with our digraph representation of hypernyms, so we could convert
from sca to the corresponding synset id when implementing sca-related functionality.
The symbol table mapped from the synset id, an Integer, to a String of
synsets.


Additionally, we also used a second symbol table to map from each noun to a
Queue of Integers, which represented the specific synset ids that the noun
was located in. This accounts for the fact that one noun may be in multiple
different synsets.

/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */

As alluded to above, we used a digraph (specifically a rooted DAG) to store
the hypernyms. This is due to the inherent nature of both the WordNet and
the hypernym-hyponym representation of nouns, where the "is-a" relationship
is best represented by a rooted DAG.


/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */

Description:

First, we check that the graph is acyclic; This is done simply by creating a new
DirectedCycle object and checking if there is a cycle. This takes O(E + V) time

Second, we check that the graph is singly rooted. To do this, we iterate through
each vertex in the digraph and find its outdegree. Assuming that outdegrees will
be small in a wordnet, this takes O(V) time



Order of growth of running time:

O(E + V) + O(V) = O(E + V)

/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description: We create two bfs objects, one for each vertex. Then, we iterate
through each potential ancestor in the digraph and compute the total distance
from each vertex to the potential ancestor, returning the ancestor with the
shortest combined distance.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                O(V)                O(E + V)

ancestor()              O(V)                O(E + V)

lengthSubset()          O(V)                O(E + V)

ancestorSubset()        O(V)                O(E + V)



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

N/A

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

N/A

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

