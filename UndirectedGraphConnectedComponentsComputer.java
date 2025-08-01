package it.unicam.cs.asdl2425.mp2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A singleton class that implements a calculator of the connected components of
 * an undirected graph using an efficient data structure (provided by the
 * {@ForestDisjointSets<GraphNode<L>>} class) to manage disjoint sets of graph
 * nodes that are, at the end of the calculation, the connected components.
 *
 * @param <L>  the type of the labels of the nodes in the graph.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class UndirectedGraphConnectedComponentsComputer<L> {

    private ForestDisjointSets<GraphNode<L>> f;

    /**
     * Create a connected component calculator.
     */
    public UndirectedGraphConnectedComponentsComputer() {
        this.f = new ForestDisjointSets<GraphNode<L>>();
    }

    /**
     * Compute the connected components of an undirected graph using a
     * collection of disjoint sets.
     * 
     * @param g  an undirected graph.
     * @return a set of connected components, each represented by a set of graph nodes.
     * @throws NullPointerException if the passed graph is null.
     * @throws IllegalArgumentException if the passed graph is directed.
     */
    public Set<Set<GraphNode<L>>> computeConnectedComponents(Graph<L> g) {
        if (g == null) throw new NullPointerException();
        if (g.isDirected()) throw new IllegalArgumentException();

        this.f.clear();
        
        for (GraphNode<L> node : g.getNodes()) {
            this.f.makeSet(node);
        }

        for (GraphEdge<L> edge : g.getEdges()) {
            if (!edge.isDirected())
            	this.f.union(edge.getNode1(), edge.getNode2());
        }

        Map<GraphNode<L>, Set<GraphNode<L>>> componentsMap =
        		new HashMap<GraphNode<L>, Set<GraphNode<L>>>();
        
        for (GraphNode<L> node : g.getNodes()) {
            GraphNode<L> representative = this.f.findSet(node);

            Set<GraphNode<L>> component = componentsMap.get(representative);
            
            if (component == null) {
            	component = new HashSet<GraphNode<L>>();
            	componentsMap.put(representative, component);
            }

            component.add(node);
        }

        return new HashSet<Set<GraphNode<L>>>(componentsMap.values());
    }
}