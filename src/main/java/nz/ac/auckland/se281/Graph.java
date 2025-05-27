package nz.ac.auckland.se281;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
}
