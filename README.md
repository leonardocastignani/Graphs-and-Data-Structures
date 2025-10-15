<h2 align="center">
  University of Camerino<br>
  School of Science and Technology<br>
  Degree in Computer Science<br>
  Algorithms and Data Structures Course 2024/2025<br>
  Laboratory Part (6 CFU)<br>
  <br>
    Instructions for Project 2<br>
  <br>
  Project 2: Implementation of Graphs and Data Structures for Working with Graphs 
</h2>

---

## **Undirected graph with adjacency matrix**
The class `Adjacency Matrix Undirected Graph<L>` represents an undirected graph using a **dynamic adjacency matrix**:
- Arches are stored as objects (`GraphEdge<L>`).
- The matrix automatically resizes as nodes are inserted or deleted.
- Node indices follow the order of insertion and are recycled on deletion.

---

## **Disjoint sets with forests**
The `ForestDisjointSets<E>` class manages a collection of disjoint sets using **forests of trees**:
- Each set is represented by a tree, with nodes containing:
  - A pointer to the parent (`parent`).
  - A `rank` field (upper bound on the height of the subtree).
- **Main operations**:
  - **makeSet(x)**: Creates a new disjoint set.
  - **findSet(x)**: Find the representative of the set to which `x` belongs, applying **path compression**.
  - **union(x, y)**: Joins two sets, using **rank union**.

---

## **Calculation of connected components**
An algorithm implemented in the class `UndirectedGraphConnectedComponentsComputer<L>` computes the **connected components** of an undirected graph:
- It uses the structure of disjoint sets to determine which nodes are reachable from each other.

---

## **Kruskal's Minimum Spanning Tree (MST) Algorithm**
Kruskal's algorithm, implemented in the `KruskalMSP<L>` class, computes the **minimum spanning tree** in a weighted graph:
- Use the `Forest Disjoint Sets` class to handle disjoint sets.
- Sort the bows by weight to build the tree.

---

## **Theoretical references**
The project is based on the concepts presented in the book **"Introduction to Algorithms"** by Cormen, Leiserson, Rivest and Stein:
- **Chapter 21**: Disjoint sets and their implementation with forests.
- **Chapter 23**: Greedy algorithms for computing the minimum spanning tree.

---

## **Key points**
- **Dynamic adjacency matrix**: Efficient representation of undirected graphs with edges stored as objects.
- **Forests of trees for disjoint sets**: Optimized handling via rank-merge and path compression heuristics.
- **Connected components**: Computing the components of a graph using disjoint sets.
- **Kruskal's algorithm**: Construction of a minimum spanning tree in weighted graphs, with edge ordering and handling of disjoint sets.
- **Efficiency**: The structures and algorithms are designed to ensure optimal performance in terms of time and space.
