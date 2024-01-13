package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Comparator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Nicholas Moy.
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _visited = new ArrayList<>();
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        PriorityQueue<Integer> fringe = new PriorityQueue<>(new CompWeights());
        for (int v : _G.vertices()) {
            setWeight(v, Double.POSITIVE_INFINITY);
            setPredecessor(v, 0);
            fringe.add(v);
        }
        setWeight(getSource(), 0);
        fringe.remove(getSource());
        fringe.add(getSource());
        _visited = new ArrayList<>();

        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            _visited.add(v);
            if (v == getDest()) {
                break;
            }
            for (int neighbor : _G.successors(v)) {
                double total = getWeight(v) + getWeight(v, neighbor);
                if (total > getWeight(neighbor)
                        && _visited.contains(neighbor)) {
                    continue;
                } else if (total < getWeight(neighbor)
                        || !fringe.contains(neighbor)) {
                    setWeight(neighbor, total);
                    setPredecessor(neighbor, v);
                    if (fringe.contains(neighbor)) {
                        fringe.remove(neighbor);
                        fringe.add(neighbor);
                    }
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        Stack<Integer> stack = new Stack<>();
        while (v != _source) {
            stack.push(v);
            v = getPredecessor(v);
        }

        List<Integer> path = new ArrayList<>();
        path.add(_source);
        while (!stack.isEmpty()) {
            path.add(stack.pop());
        }
        return path;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** ArrayList of visited nodes. */
    private ArrayList<Integer> _visited;

    /** Comparator Class to order fringe in setPaths(). */
    private class CompWeights implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            Double first = getWeight(o1) + estimatedDistance(o1);
            Double second = getWeight(o2) + estimatedDistance(o2);
            return first.compareTo(second);
        }
    }
}
