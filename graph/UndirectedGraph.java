package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Nicholas Moy.
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        Iteration<Integer> iter = predecessors(v);
        int sum = 0;
        for (Integer i : iter) {
            sum++;
        }
        return sum;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return new PredecessorsIteration(v);
    }

    /** Iteration over predecessors of given vertex. */
    private class PredecessorsIteration extends Iteration<Integer> {

        /** Constructor.
         * @param V */
        PredecessorsIteration(int V) {
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
            int ans = edges.get(index)[0];
            alreadyUsed.add(ans);
            index++;
            toNext();
            return ans;
        }

        /** Advances index so that edges.get(index) returns correct edge. */
        private void toNext() {
            if (hasNext()) {
                int[] cur = edges.get(index);
                if (cur[1] != v || alreadyUsed.contains(cur[0])) {
                    index++;
                    toNext();
                }
            }
        }

        /** Current vertex. */
        private int v;
        /** Current index in edges. */
        private int index;
        /** ArrayList of all vertices already used. */
        private ArrayList<Integer> alreadyUsed;
    }

}
