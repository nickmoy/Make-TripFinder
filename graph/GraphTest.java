package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Nicholas Moy.
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    /** Tests the add and remove methods of graphs. */
    @Test
    public void testAddRemove() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.add();
        assertEquals(1, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.add();
        assertEquals(2, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.add();
        assertEquals(3, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.add();
        assertEquals(4, g.vertexSize());
        assertEquals(0, g.edgeSize());
        g.add();
        assertEquals(5, g.vertexSize());
        assertEquals(0, g.edgeSize());

        assertEquals(5, g.maxVertex());

        g.remove(4);
        assertEquals(4, g.vertexSize());
        assertEquals(5, g.maxVertex());
    }

    /** Testing adding and removing edges. */
    @Test
    public void testEdges() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();

        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(1, 5);
        assertEquals(4, g.edgeSize());

        g.remove(1);
        assertEquals(0, g.edgeSize());
    }

    /** Tests Undirected self edges. */
    @Test
    public void testUndirectedSelfEdges() {
        UndirectedGraph ug = new UndirectedGraph();

        ug.add();
        ug.add();
        ug.add();
        ug.add();
        ug.add();

        ug.add(1, 1);
        assertEquals(1, ug.edgeSize());

        ug.add(2, 2);
        ug.add(3, 3);
        ug.add(4, 4);
        ug.add(5, 5);
        assertEquals(5, ug.edgeSize());

        ug.remove(1, 1);
        assertEquals(4, ug.edgeSize());
    }

    /** Tests Directed self edges. */
    @Test
    public void testDirectedSelfEdges() {
        DirectedGraph dg = new DirectedGraph();

        dg.add();
        dg.add();
        dg.add();
        dg.add();
        dg.add();

        dg.add(1, 1);
        assertEquals(1, dg.edgeSize());

        dg.add(2, 2);
        dg.add(3, 3);
        dg.add(4, 4);
        dg.add(5, 5);
        assertEquals(5, dg.edgeSize());

        dg.remove(1, 1);
        assertEquals(4, dg.edgeSize());
    }

    @Test
    public void testAddandMaxvert() {
        UndirectedGraph ug = new UndirectedGraph();

        ug.add();
        ug.add();
        ug.add();
        ug.add();

        assertEquals(4, ug.maxVertex());
        assertEquals(4, ug.vertexSize());

        ug.remove(3);
        assertEquals(4, ug.maxVertex());
        assertEquals(3, ug.vertexSize());

        int v = ug.add();
        assertEquals(4, ug.maxVertex());
        assertEquals(4, ug.vertexSize());
        assertEquals(3, v);
    }

}
