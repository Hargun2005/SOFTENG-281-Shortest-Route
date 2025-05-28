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

  public List<T> findShortestPath(T start, T dest) {
    List<T> visited = new ArrayList<>();
    Queue<T> queue = new LinkedList<>();
    Map<T, T> parentMap = new HashMap<>();

    parentMap.put(start, null);
    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      T node = queue.poll();

      if (node.equals(dest)) {
        // Compute the path
        List<T> path = new ArrayList<>();
        for (T n = dest; n != null; n = parentMap.get(n)) {
          path.add(n);
        }
        Collections.reverse(path);
        return path;
      }
      for (T n : adjacencyMap.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
          parentMap.put(n, node);
        }
      }
    }
    return new ArrayList<>();
  }
}
