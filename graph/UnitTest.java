package graph;

import org.junit.Before;
import org.junit.Test;
import ucb.junit.textui;

import java.util.ArrayList;

import static org.junit.Assert.*;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "Testing" in their name. These
 * may not be part of your graph package per se (that is, it must be
 * possible to remove them and still have your package work). */

/** Unit tests for the graph package.  This class serves to dispatch
 *  other test classes, which are listed in the argument to runClasses.
 *  @author Nicholas Moy.
 */
public class UnitTest {

    private UndirectedGraph ug;
    private DirectedGraph dg;

    /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(graph.GraphTest.class));
    }

    @Before
    public void setup() {
        ug = new UndirectedGraph();
        dg = new DirectedGraph();
    }

    /** Tests Undirected graph. */
    @Test
    public void testUndirectedGraph() {
        ug.add();
        assertEquals(1, ug.vertexSize());
        assertEquals(1, ug.maxVertex());

        ug.add();
        assertEquals(2, ug.vertexSize());
        assertEquals(2, ug.maxVertex());

        ug.add(1, 2);
        assertEquals(1, ug.edgeSize());

        ug.remove(1);
        assertEquals(1, ug.vertexSize());
        assertEquals(2, ug.maxVertex());
        assertEquals(0, ug.edgeSize());
    }

    /** Tests directed graph. */
    @Test
    public void testDirectedGraph() {
        dg.add();
        assertEquals(1, dg.vertexSize());
        assertEquals(1, dg.maxVertex());

        dg.add();
        assertEquals(2, dg.vertexSize());
        assertEquals(2, dg.maxVertex());

        dg.add(1, 2);
        assertEquals(1, dg.edgeSize());

        dg.remove(1);
        assertEquals(1, dg.vertexSize());
        assertEquals(2, dg.maxVertex());
        assertEquals(0, dg.edgeSize());
    }

    /** Tests Undirected edges. */
    @Test
    public void testUndirEdges() {
        ug.add();
        ug.add();
        ug.add();
        ug.add();

        assertEquals(4, ug.vertexSize());
        assertEquals(4, ug.maxVertex());

        ug.add(1, 2);
        ug.add(2, 3);
        ug.add(3, 4);

        assertEquals(3, ug.edgeSize());

        ArrayList<int[]> edges = new ArrayList<>();
        edges.add(new int[]{1, 2});
        edges.add(new int[]{2, 3});
        edges.add(new int[]{3, 4});

        Iteration<int[]> iter = ug.edges();
        int i = 0;
        for (int[] edge : iter) {
            assertArrayEquals(edge, edges.get(i));
            i++;
        }

        Iteration<Integer> predIter = ug.predecessors(2);
        assertTrue(predIter.next() == 1);
        assertTrue(predIter.next() == 3);
        assertTrue(!predIter.hasNext());


        ug.remove(3);
        assertEquals(3, ug.vertexSize());
        assertEquals(4, ug.maxVertex());
        assertEquals(1, ug.edgeSize());

        ug.remove(1, 2);
        assertEquals(0, ug.edgeSize());

    }

    /** Tests directed edges. */
    @Test
    public void testDirEdges() {
        dg.add();
        dg.add();
        dg.add();
        dg.add();

        assertEquals(4, dg.vertexSize());
        assertEquals(4, dg.maxVertex());

        dg.add(1, 2);
        dg.add(2, 3);
        dg.add(3, 4);

        assertEquals(3, dg.edgeSize());

        ArrayList<int[]> edges = new ArrayList<>();
        edges.add(new int[]{1, 2});
        edges.add(new int[]{2, 3});
        edges.add(new int[]{3, 4});

        Iteration<int[]> iter = dg.edges();
        int i = 0;
        for (int[] edge : iter) {
            assertArrayEquals(edge, edges.get(i));
            i++;
        }

        dg.remove(3);
        assertEquals(3, dg.vertexSize());
        assertEquals(4, dg.maxVertex());
        assertEquals(1, dg.edgeSize());
    }

    /** Tests vertices iteration. */
    @Test
    public void testVerticesIteration() {
        ug.add();
        ug.add();
        ug.add();
        ug.add();

        ug.remove(3);

        Iteration<Integer> iter = ug.vertices();
        int v = iter.next();
        assertEquals(1, v);
        v = iter.next();
        assertEquals(2, v);
        v = iter.next();
        assertEquals(4, v);
    }

}
