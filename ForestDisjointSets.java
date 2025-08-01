package it.unicam.cs.asdl2425.mp2;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Implement the <code>DisjointSets<E></code> interface using a forest of trees,
 * each representing a disjoint set.
 *
 * @param <E>  the type of the elements of disjoint sets.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class ForestDisjointSets<E> implements DisjointSets<E> {
	
    protected Map<E, Node<E>> currentElements;
    
    /*
     * Static internal class that represents the nodes of the forest trees.
     */
    protected static class Node<E> {
    	
        protected E item;
        protected Node<E> parent;
        protected int rank;

        /**
         * Constructs a root node with parent pointing to itself and rank zero.
         * 
         * @param item  the element stored in this node.
         */
        public Node(E item) {
            this.item = item;
            this.parent = this;
            this.rank = 0;
        }

    }

    /**
     * Constructs an empty forest of disjoint sets represented by trees.
     */
    public ForestDisjointSets() {
    	this.currentElements = new HashMap<E, Node<E>>();
    }

    @Override
    public boolean isPresent(E e) {
    	if (e == null) throw new NullPointerException();

        return this.currentElements.containsKey(e);
    }

    /*
     * Creates a forest tree consisting of a single zero-rank node whose parent
     * is itself.
     */
    @Override
    public void makeSet(E e) {
        if (e == null) throw new NullPointerException();
        if (this.isPresent(e)) throw new IllegalArgumentException();

        this.currentElements.put(e, new Node<E>(e));
    }

    /*
     * The find-set implementation must implement the "path compression"
     * heuristic.
     */
    @Override
    public E findSet(E e) {
        if (e == null) throw new NullPointerException();
        if (!this.isPresent(e)) return null;

        return this.findSet(this.currentElements.get(e)).item;
    }

    /*
     * The union implementation must implement the "rank-by-rank" heuristic.
     * Specifically, the union representative must be the representative of the
     * set whose corresponding tree has the highest-ranking root. If the rank of
     * the root of the tree containing e1 is equal to the rank of the root of
     * the tree containing e2, the union representative will be the
     * representative of the set containing e2.
     */
    @Override
    public void union(E e1, E e2) {
    	if (e1 == null || e2 == null) throw new NullPointerException();
        if (!this.isPresent(e1) || !this.isPresent(e2))
        	throw new IllegalArgumentException();

        Node<E> root1 = this.findSet(this.currentElements.get(e1));
        Node<E> root2 = this.findSet(this.currentElements.get(e2));

        if (root1 == root2) return;

        if (root1.rank > root2.rank) {
        	root2.parent = root1;
        }
        else {
        	root1.parent = root2;
            if (root1.rank == root2.rank) root2.rank++;
        }
    }

    @Override
    public Set<E> getCurrentRepresentatives() {
        Set<E> representatives = new HashSet<E>();

        for (Node<E> node : this.currentElements.values()) {
            if (node.parent == node) representatives.add(node.item);
        }
        
        return representatives;
    }

    @Override
    public Set<E> getCurrentElementsOfSetContaining(E e) {
    	if (e == null) throw new NullPointerException();
    	if (!this.isPresent(e)) throw new IllegalArgumentException();

    	Set<E> elements = new HashSet<E>();
    	Node<E> representative = this.findSet(this.currentElements.get(e));

    	for(Node<E> node : this.currentElements.values()) {
    		if(this.findSet(node) == representative) elements.add(node.item);
    	}
        
    	return elements;
    }

    @Override
    public void clear() {
    	this.currentElements.clear();
    }
    
    /**
     * Finds and returns the root of the disjoint set to which the node belongs.
     * Uses path compression to optimize future searches.
     *
     * @param node  the node whose root of the set you want to find.
     * @return the root of the set to which the node belongs.
     * 
     */
    private Node<E> findSet(Node<E> node) {
        if(node != node.parent) node.parent = this.findSet(node.parent);
        
        return node.parent;
    }
}