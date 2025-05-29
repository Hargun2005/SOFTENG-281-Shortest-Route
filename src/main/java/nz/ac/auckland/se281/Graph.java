package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {
  private Map<T, Set<T>> adjacencyMap;

  public Graph() {
    this.adjacencyMap = new LinkedHashMap<>();
  }

  public void addNode(T node) {
    if (!adjacencyMap.containsKey(node)) {
      adjacencyMap.put(node, new LinkedHashSet<>());
    }
  }

  public void addEdge(T node1, T node2) {
    addNode(node1);
    addNode(node2);

    adjacencyMap.get(node1).add(node2);
  }

  public Set<T> getNeighbors(T node) {
    return adjacencyMap.getOrDefault(node, new LinkedHashSet<>());
  }

  public boolean containsNode(T node) {
    return adjacencyMap.containsKey(node);
  }

  /**
   * Finds the shortest path between two nodes using breadth-first search.
   *
   * @param start The starting node
   * @param dest The destination node
   * @return Ordered list of nodes in the shortest path, or empty list if no path exists
   */
  public List<T> findShortestPath(T start, T dest) {
    List<T> visited = new ArrayList<>();
    Queue<T> queue = new LinkedList<>();
    Map<T, T> parentMap = new HashMap<>(); // Tracks path for reconstruction

    parentMap.put(start, null);
    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      T node = queue.poll();

      if (node.equals(dest)) {
        // Reconstruct path from destination to start, then reverse
        List<T> path = new ArrayList<>();
        for (T n = dest; n != null; n = parentMap.get(n)) {
          path.add(n);
        }
        Collections.reverse(path);
        return path;
      }
      // Explore unvisited neighbors
      for (T n : adjacencyMap.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          parentMap.put(n, node);
        }
      }
    }
    return new ArrayList<>(); // No path found
  }
}
