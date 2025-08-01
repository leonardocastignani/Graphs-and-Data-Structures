package it.unicam.cs.asdl2425.mp2;

/**
 * This class groups the operations typically associated with a node belonging
 * to a graph. The nodes of the graph are labeled with objects of the {@code L}
 * class. The label cannot be null. The {@code GraphEdge<L>} and
 * {@code Graph<L>} classes define typical operations on edges and the graph,
 * respectively.
 * 
 * The operations present are those used by the most common graph algorithms:
 * assigning and modifying a color, a distance, a pointer to a predecessor node,
 * and entry/exit times during a visit. The label is immutable; other
 * information can change and does not define the node's identity, which is
 * given exclusively by the label. In other words, two nodes are equal if and
 * only if they have the same label.
 * 
 * In many graph algorithms, nodes are placed in a priority queue. This class
 * implements the PriorityQueueElement interface using the floatingPointDistance
 * field as the priority and the integerDistance field as the integer handle to
 * a priority queue implemented with a heap represented by an array.
 * 
 * @param <L>  node labels.
 * 
 * @author Leonardo Castignani @UNICAM
 */
public class GraphNode<L> {

    public static int COLOR_WHITE = 0;
    public static int COLOR_GREY = 1;
    public static int COLOR_BLACK = 2;
    private final L label;
    private int color;
    private double floatingPointDistance;
    private int integerDistance;
    private int enteringTime;
    private int exitingTime;
    private GraphNode<L> previous;

    /**
     * Constructs a node by assigning all associated values to the default
     * values.
     * 
     * @param label  the label to associate with the node.
     * 
     * @throws NullPointerException if the label is null.
     */
    public GraphNode(L label) {
        if (label == null) throw new NullPointerException();
        
        this.label = label;
    }

    /**
     * Returns the label associated with the node that uniquely identifies it in
     * the graph.
     * 
     * @return the label.
     */
    public L getLabel() {
        return this.label;
    }

    /**
     * Returns the current color of the node.
     * 
     * @return the color.
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Assigns the node a certain color.
     * 
     * @param color  the color to set.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Returns the current value of an integer distance associated with the
     * node.
     * 
     * @return the distance.
     */
    public int getIntegerDistance() {
        return this.integerDistance;
    }

    /**
     * Assigns the node a value of an integer distance associated with it.
     * 
     * @param distance  the distance to set.
     */
    public void setIntegerDistance(int distance) {
        this.integerDistance = distance;
    }

    /**
     * Returns the current value of a distance associated with the node.
     * 
     * @return the distance.
     */
    public double getFloatingPointDistance() {
        return this.floatingPointDistance;
    }

    /**
     * Assigns the node a value of a distance associated with it.
     * 
     * @param distance  the distance to set.
     */
    public void setFloatingPointDistance(double distance) {
        this.floatingPointDistance = distance;
    }

    /**
     * Returns the graph node currently assigned as the ancestor of this node.
     * For example, it can be used by an algorithm that constructs a spanning
     * tree.
     * 
     * @return the previous.
     */
    public GraphNode<L> getPrevious() {
        return this.previous;
    }

    /**
     * Assign this node a predecessor node.
     * 
     * @param previous  the previous to set.
     */
    public void setPrevious(GraphNode<L> previous) {
        this.previous = previous;
    }

    /**
     * Returns the entry time to this node during a depth-first search.
     * 
     * @return the time of entry into this node during a depth visit.
     */
    public int getEnteringTime() {
        return this.enteringTime;
    }

    /**
     * Assign a time to enter this node during a depth search.
     * 
     * @param time  the entry time to be assigned.
     */
    public void setEnteringTime(int time) {
        this.enteringTime = time;
    }

    /**
     * Returns the exit time from this node during a depth-first search.
     * 
     * @return the time to exit this node during a depth visit.
     */
    public int getExitingTime() {
        return this.exitingTime;
    }

    /**
     * Assign a time to exit this node during a depth search.
     * 
     * @param time  the exit time to be assigned.
     */
    public void setExitingTime(int time) {
        this.exitingTime = time;
    }

    /*
     * Based on the label's hashcode.
     */
    @Override
    public int hashCode() {
        return this.label.hashCode();
    }

    /*
     * Based on the label, which cannot be null.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof GraphNode)) return false;
        
        GraphNode<?> other = (GraphNode<?>) obj;
        
        if (this.label.equals(other.label)) return true;
        
        return false;
    }

    @Override
    public String toString() {
        return "Nodo[ " + label.toString() + " ]";
    }
}