package it.unicam.cs.asdl2425.mp2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * A class that implements an undirected graph using an adjacency matrix. Null
 * node labels are not allowed, and duplicate labels within nodes (which in this
 * case are the same node) are not allowed.
 * 
 * Nodes are indexed from 0 to nodeCount() - 1 in the order they are inserted
 * (0 is the index of the first node inserted, 1 of the second, and so on), so
 * at any given time the adjacency matrix has dimension nodeCount(). The matrix,
 * always square, must therefore increase in size with each insertion of a node.
 * For this reason, it is not represented by an array but by an ArrayList.
 * 
 * GraphNode<L> objects, i.e., nodes, are stored in a map that associates each
 * node with the index assigned during insertion. The domain of the map
 * therefore represents the set of nodes.
 * 
 * Edges are stored in the adjacency matrix. Unlike the standard adjacency
 * matrix representation, position i, j of the matrix does not contain a
 * presence flag, but is null if nodes i and j are not connected by an edge and
 * contains an object of class GraphEdge<L> if they are. This object represents
 * the edge. An equal object (according to equals) and with the same weight (if
 * edges are weighted) must be present at position j, i of the matrix.
 * 
 * This class does not support node and edge deletion methods, but it does
 * support all methods that use indices, using the index assigned to each node
 * at insertion time.
 * 
 * @author Leonardo Castignani @UNICAM
 *
 */
public class AdjacencyMatrixUndirectedGraph<L> extends Graph<L> {
    protected Map<GraphNode<L>, Integer> nodesIndex;
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    public AdjacencyMatrixUndirectedGraph() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public int nodeCount() {
    	return this.nodesIndex.size();
    }

    @Override
    public int edgeCount() {
    	int count = 0;
    	
    	for (ArrayList<GraphEdge<L>> row : this.matrix) {
    		for (GraphEdge<L> edge : row) {
    			if (edge != null) count++;
    		}
    	}
    	
    	return count / 2;
    }

    @Override
    public void clear() {
    	this.nodesIndex.clear();
    	this.matrix.clear();
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
    	return this.nodesIndex.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
    	if (node == null) throw new NullPointerException();

        if (this.nodesIndex.containsKey(node)) return false;

        int index = this.nodeCount();
    	this.nodesIndex.put(node, index);

    	for (ArrayList<GraphEdge<L>> column : this.matrix) {
    		column.add(null);
    	}

    	ArrayList<GraphEdge<L>> newRow = 
    			                new ArrayList<GraphEdge<L>>(this.nodeCount());
    	
    	for (int i = 0; i < this.nodeCount(); i++) {
    		newRow.add(null);
    	}
    	
    	this.matrix.add(newRow);

    	return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        if (node == null) throw new NullPointerException();

        if (!this.nodesIndex.containsKey(node)) return false;

        int indexToRemove = this.nodesIndex.get(node);

        this.nodesIndex.remove(node);

        this.matrix.remove(indexToRemove);

        for (ArrayList<GraphEdge<L>> column : this.matrix) {
        	column.remove(indexToRemove);
        }

        for (Map.Entry<GraphNode<L>, Integer> entry :
        	                                  this.nodesIndex.entrySet()) {
            if (entry.getValue() > indexToRemove) {
                entry.setValue(entry.getValue() - 1);
            }
        }

        return true;
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
    	if (node == null) throw new NullPointerException();

    	return this.nodesIndex.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        if (label == null) throw new NullPointerException();

        for (GraphNode<L> node : this.nodesIndex.keySet()) {
            if (node.getLabel().equals(label)) return node;
        }

        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if (label == null) throw new NullPointerException();

        GraphNode<L> node = this.getNodeOf(label);

        if (node == null) throw new IllegalArgumentException();

        return this.nodesIndex.get(node);
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
    	if (i < 0 || i >= this.nodeCount())
    		throw new IndexOutOfBoundsException();

    	for (Map.Entry<GraphNode<L>, Integer> entry :
    		 this.nodesIndex.entrySet()) {

    		if (entry.getValue() == i) return entry.getKey();
    	}

    	return null;
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
    	if (node == null) throw new NullPointerException();

        if (!this.containsNode(node)) throw new IllegalArgumentException();

    	Set<GraphNode<L>> result = new HashSet<GraphNode<L>>();
    	int index = this.nodesIndex.get(node);

    	for (int i = 0; i < this.matrix.get(index).size(); i++) {
    		if (this.matrix.get(index).get(i) != null)
    			result.add(getNodeAtIndex(i));
    	}

    	return result;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
    	Set<GraphEdge<L>> edges = new HashSet<GraphEdge<L>>();

    	for (ArrayList<GraphEdge<L>> row : this.matrix) {
    		for (GraphEdge<L> edge : row) {
    			if (edge != null) edges.add(edge);
    		}
    	}

    	return edges;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
    	if (edge == null) throw new NullPointerException();
        if (edge.isDirected()) throw new IllegalArgumentException();

        GraphNode<L> u = edge.getNode1();
        GraphNode<L> v = edge.getNode2();

        if (!this.containsNode(u) || !this.containsNode(v))
            throw new IllegalArgumentException();

        int indexU = this.nodesIndex.get(u);
        int indexV = this.nodesIndex.get(v);

        if (this.matrix.get(indexU).get(indexV) != null) return false;

        GraphEdge<L> newEdge = new GraphEdge<L>(u, v, false, edge.getWeight());

        this.matrix.get(indexU).set(indexV, newEdge);
        this.matrix.get(indexV).set(indexU, newEdge);

        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        if (edge == null) throw new NullPointerException();
        if (edge.isDirected()) throw new IllegalArgumentException();

        GraphNode<L> u = edge.getNode1();
        GraphNode<L> v = edge.getNode2();

        if (!this.containsNode(u) || !this.containsNode(v))
            throw new IllegalArgumentException();

        int indexU = this.nodesIndex.get(u);
        int indexV = this.nodesIndex.get(v);

        if (this.matrix.get(indexU).get(indexV) == null) return false;

        this.matrix.get(indexU).set(indexV, null);
        this.matrix.get(indexV).set(indexU, null);

        return true;
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
    	if (edge == null) throw new NullPointerException();
        if (edge.isDirected()) throw new IllegalArgumentException();

        GraphNode<L> u = edge.getNode1();
        GraphNode<L> v = edge.getNode2();

        if (!this.containsNode(u) || !this.containsNode(v))
            throw new IllegalArgumentException();

        int indexU = this.nodesIndex.get(u);
        int indexV = this.nodesIndex.get(v);

        return this.matrix.get(indexU).get(indexV) != null;
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
    	if (node == null) throw new NullPointerException();
        if (!this.containsNode(node)) throw new IllegalArgumentException();

    	Set<GraphEdge<L>> edges = new HashSet<GraphEdge<L>>();
        int index = this.nodesIndex.get(node);

        for (GraphEdge<L> edge : this.matrix.get(index)) {
            if (edge != null) edges.add(edge);
        }

        return edges;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException();
    }
}