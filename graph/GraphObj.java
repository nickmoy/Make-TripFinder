package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Nicholas Moy.
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        numVertex = 0;
        verts = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public int vertexSize() {
        return verts.size();
    }

    @Override
    public int maxVertex() {
        PriorityQueue<Integer> orderedVerts =
                new PriorityQueue<>(10, Collections.reverseOrder());
        orderedVerts.addAll(verts);
        if (orderedVerts.isEmpty()) {
            return 0;
        }
        return orderedVerts.poll();
    }

    @Override
    public int edgeSize() {
        if (isDirected()) {
            return edges.size();
        }
        return edges.size() / 2;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        int ans = 0;
        for (int i = 0; i < edges.size(); i++) {
            int[] edge = edges.get(i);
            if (edge[0] == v) {
                if (edge[0] == edge[1]) {
                    i++;
                }
                ans++;
            }
        }
        return ans;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        if (u != 0 && verts.contains(u)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            for (int[] edge : edges) {
                if (edge[0] == u && edge[1] == v) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        numVertex++;
        for (int i = 1; i <= verts.size(); i++) {
            if (!verts.contains(i)) {
                verts.add(i);
                return i;
            }
        }
        verts.add(numVertex);
        return numVertex;
    }

    @Override
    public int add(int u, int v) {
        int[] e = new int[] {u, v};
        int[] erev = new int[] {v, u};
        if (contains(u) && contains(v)) {
            if (!contains(u, v)) {
                numEdge++;
                edges.add(e);
                if (!isDirected()) {
                    edges.add(erev);
                }
            }
        }
        return edgeId(u, v);
    }

    /** Removes from verts array.
     * @param v */
    private void removeFromVerts(int v) {
        for (int i = 0; i < verts.size(); i++) {
            if (verts.get(i) == v) {
                verts.remove(i);
                return;
            }
        }
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            numVertex--;
            removeFromVerts(v);
            ArrayList<int[]> toRemove = new ArrayList<>();
            for (int[] edge : edges) {
                if (edge[0] == v || edge[1] == v) {
                    toRemove.add(edge);
                }
            }
            for (int[] edge : toRemove) {
                edges.remove(edge);
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            ArrayList<int[]> toRemove = new ArrayList<>();
            for (int[] edge : edges) {
                if (edge[0] == u && edge[1] == v) {
                    toRemove.add(edge);
                }
                if (!isDirected() && u != v) {
                    if (edge[0] == v && edge[1] == u) {
                        toRemove.add(edge);
                    }
                }
            }
            for (int[] edge : toRemove) {
                edges.remove(edge);
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return new VerticesIteration();
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return new SuccessorsIteration(v);
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        return new EdgesIteration();
    }

    /* Commented out
    @Override
    protected void checkMyVertex(int v) {

    }
    */

    @Override
    protected int edgeId(int u, int v) {
        return (((u + v) * (u + v + 1)) / 2) + v;
    }

    /** Array of LinkedLists representing vertices
     * and the list of edges to other vertices. */
    protected ArrayList<Integer> verts;
    /** ArrayList of edges as two element int arrays. */
    protected ArrayList<int[]> edges;
    /** Number of vertices. */
    private int numVertex;
    /** Number of edges. */
    private int numEdge;

    /** Iteration over successors of given vertex. */
    private class SuccessorsIteration extends Iteration<Integer> {

        /** Constructor.
         * @param V */
        SuccessorsIteration(int V) {
            this.v = V;
            index = 0;
            alreadyUsed = new ArrayList<>();
            toNext();
        }

        @Override
        public boolean hasNext() {
            return index < edges.size();
        }

        @Override
        public Integer next() {
            int ans = edges.get(index)[1];
            alreadyUsed.add(ans);
            index++;
            toNext();
            return ans;
        }

        /** Advances index correctly. */
        public void toNext() {
            if (hasNext()) {
                int[] cur = edges.get(index);
                if (cur[0] != v || alreadyUsed.contains(cur[1])) {
                    index++;
                    toNext();
                }
            }
        }

        /** Current index. */
        private int index;
        /** The vertex. */
        private int v;
        /** ArrayList of all vertices already used. */
        private ArrayList<Integer> alreadyUsed;
    }

    /** Iteration over the vertices of graph. */
    private class VerticesIteration extends Iteration<Integer> {

        /** Constructor. */
        VerticesIteration() {
            orderedVerts = new PriorityQueue<>();
            orderedVerts.addAll(verts);
        }

        @Override
        public boolean hasNext() {
            return !orderedVerts.isEmpty();
        }

        @Override
        public Integer next() {
            return orderedVerts.poll();
        }

        /** Priority Queue of ordered vertices. */
        private PriorityQueue<Integer> orderedVerts;
    }

    /** Iteration over the edges of graph. */
    private class EdgesIteration extends Iteration<int[]> {

        /** Constructor. */
        EdgesIteration() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < edges.size();
        }

        @Override
        public int[] next() {
            int[] ans = edges.get(index);
            if (!isDirected()) {
                index += 2;
            } else {
                index += 1;
            }
            return ans;
        }

        /** Index in edges. */
        private int index;
    }

}
