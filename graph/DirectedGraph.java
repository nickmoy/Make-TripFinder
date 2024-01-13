package graph;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Nicholas Moy.
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
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
            toNext();
        }

        @Override
        public boolean hasNext() {
            return index < edges.size();
        }

        @Override
        public Integer next() {
            int ans = edges.get(index)[0];
            index++;
            toNext();
            return ans;
        }

        /** Advances index so that edges.get(index) returns correct edge. */
        private void toNext() {
            if (hasNext() && edges.get(index)[1] != v) {
                index++;
                toNext();
            }
        }

        /** Current vertex. */
        private int v;
        /** Current index in edges. */
        private int index;
    }

}
