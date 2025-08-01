package it.unicam.cs.asdl2425.mp2;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * A singleton class that implements Kruskal's algorithm for finding a minimum
 * spanning tree of an undirected, weighted, nonnegative graph. The implemented
 * algorithm uses the {@code ForestDisjointSets<GraphNode<L>>} class to manage a
 * collection of disjoint sets of graph nodes.
 * 
 * @param <L>  type of the labels of the graph nodes.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class KruskalMSP<L> {

    private ForestDisjointSets<GraphNode<L>> disjointSets;

    /**
     * Build a minimum spanning tree calculator using Kruskal's algorithm on an
     * undirected, weighted graph.
     */
    public KruskalMSP() {
        this.disjointSets = new ForestDisjointSets<GraphNode<L>>();
    }

    /**
     * Use Kruskal's greedy algorithm to find a minimum spanning tree in an
     * undirected, weighted graph with nonnegative edge weights. The resulting
     * tree is unrooted, so it is simply represented by a subset of the graph's
     * edges.
     * 
     * @param g  an undirected, weighted graph with nonnegative weights.
     * @return the set of edges of graph g that constitute the minimum spanning
     *         tree found.
     * @throw NullPointerException if the graph is null.
     * @throw IllegalArgumentException whether the graph is directed,
     *        unweighted, or has negative weights.
     */
    public Set<GraphEdge<L>> computeMSP(Graph<L> g) {
        if (g == null) throw new NullPointerException();
        if (g.isDirected()) throw new IllegalArgumentException();

        for (GraphEdge<L> edge : g.getEdges()) {
            if (!edge.hasWeight() || edge.getWeight() < 0)
                throw new IllegalArgumentException();
        }

        this.disjointSets.clear();

        for (GraphNode<L> node : g.getNodes()) {
            this.disjointSets.makeSet(node);
        }

        List<GraphEdge<L>> edges = new ArrayList<GraphEdge<L>>(g.getEdges());
        this.quickSort(edges, 0, edges.size() - 1);

        Set<GraphEdge<L>> edgesACM = new HashSet<GraphEdge<L>>();

        for (GraphEdge<L> edge : edges) {
            GraphNode<L> u = edge.getNode1();
            GraphNode<L> v = edge.getNode2();
            
            if (this.disjointSets.findSet(u) != this.disjointSets.findSet(v)) {
            	edgesACM.add(edge);

                this.disjointSets.union(u, v);
            }
        }

        return edgesACM;
    }

    /**
     * Method to sort a list of edges using the Quicksort algorithm.
     * 
     * @param edges  list of edges to order.
     * @param low  lower index.
     * @param high  upper index.
     */
    private void quickSort(List<GraphEdge<L>> edges, int left, int right) {
        if (left < right) {
            int p = this.partition(edges, left, right);
            this.quickSort(edges, left, p - 1);
            this.quickSort(edges, p + 1, right);
        }
    }

    /**
     * Method to partition the list for the Quicksort algorithm.
     * 
     * @param edges  list of edges to partition.
     * @param low  lower index.
     * @param high  upper index.
     * @return partition index.
     */
    private int partition(List<GraphEdge<L>> edges, int left, int right) {
        double pivot = edges.get(right).getWeight();
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (edges.get(j).getWeight() <= pivot) {
                i++;
                this.swap(edges, i, j);
            }
        }

        this.swap(edges, i + 1, right);

        return i + 1;
    }
    
    /**
     * Method to swap two elements in an edge list.
     * 
     * @param edges  list of edges.
     * @param i  first index.
     * @param j  second index.
     */
    private void swap(List<GraphEdge<L>> edges, int i, int j) {
        GraphEdge<L> temp = edges.get(j);
        edges.set(j, edges.get(i));
        edges.set(i, temp);
    }
}