package it.unicam.cs.asdl2425.mp2;

import java.util.Set;

/**
 * Abstract class for a generic graph whose nodes are labeled with elements of
 * the class {@code L}. The classes {@code GraphNode<L>} and
 * {@code GraphEdge<L>} define generic operations on nodes and edges.
 * 
 * The graph can be directed or undirected; the subclass that extends this
 * abstract class specifies this aspect. This information is available through
 * the {@code isDirected()} method.
 * 
 * Node labels are mandatory and unique, that is, a node cannot have a null
 * label and two nodes with the same label are the same node.
 * 
 * @param <L>  node labels.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public abstract class Graph<L> {

    /**
     * Returns the number of nodes in this graph.
     * 
     * @return the number of nodes in this graph.
     */
    public abstract int nodeCount();

    /**
     * Returns the number of edges in this graph.
     * 
     * @return the number of edges in this graph.
     */
    public abstract int edgeCount();

    /**
     * Returns the size of this graph defined as the number of nodes plus the
     * number of edges.
     * 
     * @return the size of this graph is defined as the number of nodes plus the
     *         number of edges.
     */
    public int size() {
        return this.nodeCount() + this.edgeCount();
    }

    /**
     * Determine whether this graph is empty, that is, has no nodes and no
     * edges.
     * 
     * @return if this graph is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.nodeCount() == 0;
    }

    /**
     * Deletes all nodes and edges of this graph, making it an empty graph.
     */
    public abstract void clear();

    /**
     * Determine whether this graph is directed or undirected.
     * 
     * @return true if this graph is directed, false otherwise.
     */
    public abstract boolean isDirected();

    /**
     * Returns the set of nodes in this graph.
     * 
     * @return the set of nodes of this graph, possibly empty.
     */
    public abstract Set<GraphNode<L>> getNodes();

    /**
     * Adds a node to this graph.
     * 
     * @param node  the new node to add.
     * @return true if the node has been added, false otherwise, i.e. if the
     *         node is already present.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract boolean addNode(GraphNode<L> node);

    /**
     * Removes a node from this graph. All edges connected to the node are also
     * deleted.
     * 
     * @param node  the knot to be removed.
     * @return true if the node was removed, false if the node was not present.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract boolean removeNode(GraphNode<L> node);

    /**
     * Determine whether there is a certain node in this graph.
     * 
     * @param node  the node sought.
     * @return true if node {@code node} exists in this graph.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract boolean containsNode(GraphNode<L> node);

    /**
     * Returns the node in this graph having the given label.
     * 
     * @param label  the label of the node to return.
     * @return the node in this graph that has label equal to {@code label},
     *         null if such a node does not exist in this graph.
     * @throws NullPointerException if the label is null.
     */
    public abstract GraphNode<L> getNodeOf(L label);

    /**
     * Restituisce un indice unico attualmente associato a un certo nodo nell'
     * intervallo <code>[0, this.nodeCount() - 1]</code>. Questa funzionalità è
     * tipicamente disponibile se il grafo è rappresentato con una matrice di
     * adiacenza. Tale indice può anche essere usato per identificare i nodi in
     * strutture dati esterne come array o liste che contengono informazioni
     * aggiuntive calcolate, ad esempio, da un algoritmo sul grafo.
     * 
     * @param label  the label of the node whose index to return.
     * @return a unique index in the range
     *         <code>[0, this.nodeCount() - 1]</code> currently associated with
     *         the node with label {@code label}. The index may change if the
     *         graph is modified by removing nodes.
     * @throws NullPointerException if the passed label is null.
     * @throws IllegalArgumentException if a node with the label {@code label}
     *         does not exist in this graph
     */
    public abstract int getNodeIndexOf(L label);

    /**
     * Returns the node currently associated with a given index in the range
     * <code>[0, this.nodeCount() - 1]</code>. This functionality is typically
     * available if the graph is represented with an adjacency matrix.
     * 
     * @param i  the node index.
     * @return the node currently associated with index i.
     * @throws IndexOutOfBoundsException if the passed index does not match any
     *         node or is outside the bounds of the range
     *         <code>[0, this.nodeCount() - 1]</code>.
     */
    public abstract GraphNode<L> getNodeAtIndex(int i);

    /**
     * Returns the set of all nodes adjacent to a given node. If the graph is
     * directed, the returned nodes are only those connected by an edge exiting
     * from the given node.
     * 
     * @param node  the node whose adjacent nodes to search for.
     * @return the set of all nodes adjacent to the specified node.
     * @throws IllegalArgumentException if the passed node does not exist.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node);

    /**
     * Returns the set of all nodes connected by an edge entering a given node
     * in a directed graph.
     * 
     * @param node  the node for which to search for successor nodes.
     * @return the set of all nodes connected by an incoming arc to the
     *         specified node.
     * @throws UnsupportedOperationException if the graph on which the method is
     *         called is undirected.
     * @throws IllegalArgumentException if the passed node does not exist.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node);

    /**
     * Returns the set of all edges in this graph.
     * 
     * @return a set, possibly empty, containing all the edges of this graph.
     */
    public abstract Set<GraphEdge<L>> getEdges();

    /**
     * Adds an edge to this graph.
     * 
     * @param edge  the edge to be inserted.
     * @return true if the edge has been inserted, false if an exactly identical
     *         edge already exists.
     * @throws NullPointerException if the passed edge is zero.
     * @throws IllegalArgumentException if at least one of the two nodes
     *         specified in the edge does not exist.
     * @throws IllegalArgumentException if the edge is directed and this graph
     *         is undirected or vice versa.
     */
    public abstract boolean addEdge(GraphEdge<L> edge);

    /**
     * Removes an edge from this graph.
     * 
     * @param edge  the edge to be removed.
     * @return true if the edge was removed, false if the edge was not present.
     * @throws IllegalArgumentException if at least one of the two nodes
     *         specified in the edge does not exist.
     * @throws NullPointerException if the passed edge is zero.
     */
    public abstract boolean removeEdge(GraphEdge<L> edge);

    /**
     * Find if there is a certain edge in this graph.
     * 
     * @param edge  the edge to look for.
     * @return true if the specified edge exists in this graph, false otherwise.
     * @throws NullPointerException if the passed edge is zero.
     * @throws IllegalArgumentException if at least one of the two nodes
     *         specified in the edge does not exist.
     * 
     */
    public abstract boolean containsEdge(GraphEdge<L> edge);

    /**
     * Returns the set of all edges connected to a given node in a graph. In the
     * case of a directed graph, only outgoing edges are returned.
     * 
     * @param node  the node whose connected edges are required.
     * @return a set containing all edges connected to the specified node in
     *         this graph (only the outgoing edges in the case of a directed
     *         graph).
     * @throws IllegalArgumentException if the passed node does not exist.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node);

    /**
     * Returns the set of all edges entering a given node in a directed graph.
     * 
     * @param node  the node of which all incoming edges are to be determined.
     * @return a set containing all edges entering the node with label label in
     *         this directed graph.
     * @throws UnsupportedOperationException if the graph on which the method is
     *         called is undirected.
     * @throws IllegalArgumentException if the passed node does not exist.
     * @throws NullPointerException if the passed node is null.
     */
    public abstract Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node);

    /**
     * Returns the degree of a node, i.e., the number of edges connected to the
     * node. In the case of a directed graph, it is the sum of the number of
     * incoming edges and the number of outgoing edges.
     * 
     * @param node  the node whose degree is to be calculated.
     * @return the degree of the past node.
     * @throws IllegalArgumentException if the passed node does not exist.
     * @throws NullPointerException if the passed node is null.
     */
    public int getDegreeOf(GraphNode<L> node) {
        if (!this.isDirected())
            return this.getEdgesOf(node).size();
        else
            return this.getEdgesOf(node).size() + this.getIngoingEdgesOf(node)
                                                      .size();
    }
}