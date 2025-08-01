package it.unicam.cs.asdl2425.mp2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ForestDisjointSets class. This class verifies the
 * correctness of ForestDisjointSets features, including makeSet, union,
 * findSet, and exception handling. It includes tests for normal, edge, and
 * performance scenarios.
 * 
 * @author Leonardo Castignani @UNICAM
 *
 */
class ForestDisjointSetsTest {

    /**
     * Make sure the constructor initializes an empty structure.
     */
    @Test
    final void testForestDisjointSets() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        assertTrue(ds.getCurrentRepresentatives().isEmpty());
    }

    /**
     * Test the behavior of isPresent for present and non-present elements.
     */
    @Test
    final void testIsPresent() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        assertFalse(ds.isPresent(1));
        ds.makeSet(1);
        assertTrue(ds.isPresent(1));
        ds.makeSet(2);
        assertTrue(ds.isPresent(2));
        assertFalse(ds.isPresent(3));
    }

    /**
     * Check isPresent after multiple joins.
     */
    @Test
    final void testIsPresentAfterUnion() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        ds.makeSet(1);
        ds.makeSet(2);
        ds.union(1, 2);
        assertTrue(ds.isPresent(1));
        assertTrue(ds.isPresent(2));
        assertFalse(ds.isPresent(3));
        ds.makeSet(3);
        assertTrue(ds.isPresent(3));
        ds.union(2, 3);
        assertTrue(ds.isPresent(1));
        assertTrue(ds.isPresent(2));
        assertTrue(ds.isPresent(3));
    }

    /**
     * Ensure that makeSet throws exceptions when attempting to add null or
     * duplicate elements.
     */
    @Test
    final void testMakeSetExceptions() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        assertThrows(NullPointerException.class, () -> ds.makeSet(null));
        ds.makeSet(1);
        assertThrows(IllegalArgumentException.class, () -> ds.makeSet(1));
    }

    /**
     * Verify that makeSet handles complex types correctly.
     */
    @Test
    final void testMakeSetWithCustomObjects() {
        class Custom {
            String value;

            Custom(String value) {
                this.value = value;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Custom custom = (Custom) o;
                return this.value.equals(custom.value);
            }

            @Override
            public int hashCode() {
                return this.value.hashCode();
            }
        }

        ForestDisjointSets<Custom> ds = new ForestDisjointSets<Custom>();
        Custom obj1 = new Custom("A");
        Custom obj2 = new Custom("B");
        Custom obj3 = new Custom("A");

        ds.makeSet(obj1);
        ds.makeSet(obj2);

        assertTrue(ds.isPresent(obj1));
        assertTrue(ds.isPresent(obj2));
        assertTrue(ds.isPresent(obj3));
    }
    
    /**
     * Stress test with many elements.
     */
    @Test
    final void testStressUnionFind() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        int numElements = 100000;

        for (int i = 1; i <= numElements; i++) {
            ds.makeSet(i);
        }
        assertEquals(numElements, ds.getCurrentRepresentatives().size());

        for (int i = 2; i <= numElements; i++) {
            ds.union(1, i);
        }

        for (int i = 1; i <= numElements; i++) {
            assertEquals(ds.findSet(1), ds.findSet(i));
        }

        assertEquals(1, ds.getCurrentRepresentatives().size());
    }

    /**
     * Check for "path compression" in findSets.
     */
    @Test
    final void testPathCompression() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        ds.union(1, 2);

        ds.union(2, 3);

        assertEquals(2, ds.findSet(1), "Il rappresentante di 1 dovrebbe essere "
        		     + "2 dopo l'unione");
        assertEquals(2, ds.findSet(3), "Il rappresentante di 3 dovrebbe essere "
        		     + "2 dopo l'unione");

        ForestDisjointSets.Node<Integer> node1 = ds.currentElements.get(1);
        ForestDisjointSets.Node<Integer> node3 = ds.currentElements.get(3);
        ForestDisjointSets.Node<Integer> node2 = ds.currentElements.get(2);

        assertEquals(node2, node1.parent, "Il parent di 1 dovrebbe puntare a 2 "
        		     + "dopo la compressione del cammino");
        assertEquals(node2, node3.parent, "Il parent di 3 dovrebbe puntare a 2 "
        		     + "dopo la compressione del cammino");
    }

    /**
     * Check the union by rank.
     */
    @Test
    final void testUnionByRank() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);
        ds.union(1, 2);
        assertEquals(1, ds.currentElements.get(2).rank);
        ds.union(2, 3);
        assertEquals(1, ds.currentElements.get(2).rank);
        assertEquals(0, ds.currentElements.get(3).rank);
    }

    /**
     * Verify that clear cleans the structure correctly.
     */
    @Test
    final void testClear() {
        ForestDisjointSets<Integer> ds = new ForestDisjointSets<Integer>();
        ds.makeSet(1);
        ds.makeSet(2);
        ds.union(1, 2);
        ds.clear();
        assertTrue(ds.getCurrentRepresentatives().isEmpty());
        assertTrue(ds.currentElements.isEmpty());
    }
}