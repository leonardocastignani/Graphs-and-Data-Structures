package it.unicam.cs.asdl2425.mp2;

/**
 * This class groups the characteristics of an edge, possibly weighted and
 * labeled, that is part of a graph. The nodes of the graph are labeled with
 * objects of the {@code L} class. The {@code GraphNode<L>} and {@code Graph<L>}
 * classes define generic operations on the nodes and the graph, respectively.
 * 
 * An edge can be directed or undirected; this information is immutable and
 * available via the {@code isDirected()} method. The two nodes connected by the
 * edge are immutable and cannot be null.
 * 
 * The edge can be weighted using the {@code setWeight(double} and
 * {@code getWeight()} methods. If the weight is not specified in the
 * constructor, it is automatically initialized to {@code Double.NaN}. In this
 * case, the edge is considered unweighted until it is assigned a value other
 * than Double.NaN.
 * 
 * Two edges are equal if and only if they connect the same nodes and are both
 * directed or both undirected. In the case of undirected edges, the order of
 * the nodes does not matter; that is, an edge between {@code n1} and {@code n2}
 * and an edge between {@code n2} and {@code n1} are considered the same edge.
 * 
 * @param <L>  graph node labels.
 * 
 * @author Leonardo Castignani @UNICAM
*/
public class GraphEdge<L> {

    private final GraphNode<L> node1;
    private final GraphNode<L> node2;
    private final boolean directed;
    private double weight;

    /**
     * Constructs a weighted edge of a graph.
     * 
     * @param node1  first node (source node in case of directed graph).
     * @param node2  second node (destination node in case of directed graph).
     * @param directed  true if the edge is oriented, false otherwise.
     * @param weight  weight associated with the edge.
     * @throws NullPointerException if at least one of the two nodes is null.
     */
    public GraphEdge(GraphNode<L> node1, GraphNode<L> node2, boolean directed,
            double weight) {
        if (node1 == null) throw new NullPointerException();
        if (node2 == null) throw new NullPointerException();
        
        this.node1 = node1;
        this.node2 = node2;
        this.directed = directed;
        this.weight = weight;
    }

    /**
     * Constructs an edge of a graph.
     * 
     * @param node1  first node (source node in case of directed graph).
     * @param node2  second node (destination node in case of directed graph).
     * @param directed  true if the edge is oriented, false otherwise.
     * @throws NullPointerException if at least one of the two nodes is null.
     */
    public GraphEdge(GraphNode<L> node1, GraphNode<L> node2, boolean directed) {
        if (node1 == null) throw new NullPointerException();
        if (node2 == null) throw new NullPointerException();
        
        this.node1 = node1;
        this.node2 = node2;
        this.directed = directed;
        this.weight = Double.NaN;
    }

    /**
     * Determines whether this edge is currently weighted.
     * 
     * @return true if this edge currently has a weight other than Double.NaN
     *         associated with it.
     */
    public boolean hasWeight() {
        return !Double.isNaN(this.weight);
    }

    /**
     * Returns the first node of this edge, the source in case of a directed
     * edge.
     * 
     * @return the first node of this edge, the source in case of a oriented
     *         edge.
     */
    public GraphNode<L> getNode1() {
        return this.node1;
    }

    /**
     * Returns the second node of this edge, the destination in case of a
     * directed edge.
     * 
     * @return the second node of this edge, the destination in case of a
     *         directed edge.
     */
    public GraphNode<L> getNode2() {
        return this.node2;
    }

    /**
     * Indicates whether this edge is oriented or not.
     * 
     * @return true if this edge is oriented, false otherwise.
     */
    public boolean isDirected() {
        return this.directed;
    }

    /**
     * Returns the weight assigned to the edge. If the weight is equal to
     * Double.Nan, the edge is considered unweighted.
     * 
     * @return the weight associated with the edge.
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Give this edge some weight.
     * 
     * @param weight the weight to assign to this edge.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /*
     * Based on the flag that defines whether the edge is oriented or not and on
     * the hashcodes of the two connected nodes.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        
        result = prime * result + (this.directed ? 1231 : 1237);
        result = prime * result + (this.node1.hashCode()
        		                   + this.node2.hashCode());
        
        return result;
    }

    /*
     * Two edges are equal if they are both directed or undirected and if they
     * connect identical nodes. If the edge is undirected, the order of the
     * nodes doesn't matter.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof GraphEdge)) return false;
        
        GraphEdge<?> other = (GraphEdge<?>) obj;
        
        if (this.directed != other.directed) return false;
        
        if (this.directed) {
            if (!this.node1.equals(other.node1)) return false;
            
            if (!this.node2.equals(other.node2)) return false;
            
            return true;
        } else {
            if (this.node1.equals(other.node1)
            	&& this.node2.equals(other.node2)) return true;
            
            if (this.node1.equals(other.node2)
            	&& this.node2.equals(other.node1)) return true;
            
            return false;
        }
    }

    @Override
    public String toString() {
        if (this.directed)
            return "Edge [ " + this.node1.toString() + " --> "
                    + this.node2.toString() + " ]";
        else
            return "Edge [ " + this.node1.toString() + " -- "
                    + this.node2.toString() + " ]";
    }
}