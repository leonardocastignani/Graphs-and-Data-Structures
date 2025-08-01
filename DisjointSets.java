package it.unicam.cs.asdl2425.mp2;

import java.util.Set;

/**
 * A class that implements this interface can manage a collection of disjoint
 * sets.
 * 
 * The basic operations are creating a new singleton set from an element,
 * finding the disjoint set of which an element is a member, and joining two
 * disjoint sets.
 * 
 * At any given moment, every disjoint set has a representative element
 * associated with it. The representative of a singleton set is its only
 * element, while the representative of a disjoint set with more than one
 * element can be any element of the disjoint set. The representative may change
 * following the union operation.
 * 
 * @param <E>  the type of the elements of disjoint sets.
 * 
 * @author Leonardo Castignani @UNICAM
 *
 */
public interface DisjointSets<E> {

    /**
     * Determines whether an item has been previously inserted.
     * 
     * @param e  the item to search for.
     * @return true if the element <code>e</code> is already present in some
     *         current disjoint set, false otherwise
     * @throws NullPointerException if the passed element is null.
     */
    boolean isPresent(E e);

    /**
     * Creates a new disjoint set containing only the given element.
     * 
     * @param e  the element to be inserted into the created set.
     * @throws NullPointerException if the passed element is null.
     * @throws IllegalArgumentException if the passed element is already present
     *         in one of the current disjoint sets.
     */
    void makeSet(E e);

    /**
     * Returns the representative of the disjoint set in which the passed
     * element occurs.
     * 
     * @param e  the element whose disjoint set is to be found.
     * @return the element representing the disjoint set in which <code>e</code>
     *         currently resides, or <code>null</code> if the passed element is
     *         not present in any of the current disjoint sets.
     * @throws NullPointerException if the passed element is null.
     */
    E findSet(E e);

    /**
     * Merges the disjoint sets containing the two passed elements. If the
     * passed elements are already part of the same set, it does nothing. After
     * the operation, the representative of the merged set is an element of the
     * disjoint set whose identity is defined by the class implementing this
     * interface.
     * 
     * @param e1  an element of the first set to be joined.
     * @param e2  an element of the second set to be joined.
     * @throws NullPointerException if at least one of the two passed elements
     *         is null.
     * @throws IllegalArgumentException if at least one of the two passed
     *         elements is not present in any of the current disjoint sets.
     */
    void union(E e1, E e2);

    /**
     * Returns the set of representatives of the currently existing disjoint
     * sets.
     * 
     * @return the current set of representatives of disjoint sets.
     */
    Set<E> getCurrentRepresentatives();

    /**
     * Returns the elements belonging to the disjoint set of which a given
     * element is a member.
     * 
     * @param e  l'elemento di cui si vuole ottenere l'insieme disgiunto di cui fa parte.
     * @return the set of elements that the passed element is part of.
     * @throws NullPointerException if the passed set is null.
     * @throws IllegalArgumentException if the passed element is not contained
     *         in any disjoint set.
     */
    Set<E> getCurrentElementsOfSetContaining(E e);

    /**
     * Deletes all current disjoint sets. After this method runs, the forest
     * will be empty.
     */
    public void clear();
}