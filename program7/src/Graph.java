import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Graph<T extends Comparable<T>> implements GraphADT<T> {
  private class Vertex {
    private HashSet<T> edges;

    Vertex(T data) {
      this.edges = new HashSet<T>();
    }
  }

  int order, size;
  HashMap<T, Vertex> vertices;

  Graph() {
    this.order = this.size = 0;
    this.vertices = new HashMap<T, Vertex>();
  }

  @Override
  public void addVertex(T vertex) throws IllegalArgumentException {
    if (vertex == null) {
      throw new IllegalArgumentException("Cannot insert null vertex.");
    } else if (!vertices.containsKey(vertex)) {
      vertices.put(vertex, new Vertex(vertex));
      order++;
    }
  }

  @Override
  public void removeVertex(T vertex) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    if (vertex == null) {
      throw new IllegalArgumentException("Cannot remove null vertex.");
    } else if (vertices.containsKey(vertex)) {
      for (T edge : vertices.get(vertex).edges) {
        vertices.get(edge).edges.remove(vertex);
        size--;
      }
      vertices.remove(vertex);
      order--;
    }

  }

  @Override
  public void addEdge(T vertex1, T vertex2) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    if(vertex1 == null || vertex2 == null) {
      throw new IllegalArgumentException("Cannot add edge to null vertex.");
    }else if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
      vertices.get(vertex1).edges.add(vertex2);
      vertices.get(vertex2).edges.add(vertex1);
      size++;
    }  
  }

  @Override
  public void removeEdge(T vertex1, T vertex2) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    if (vertex1 == null || vertex2 == null) {
      throw new IllegalArgumentException("Cannot remove edge from null vertex.");
    }else if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
      vertices.get(vertex1).edges.remove(vertex2);
      vertices.get(vertex2).edges.remove(vertex1);
      size--;
    }
  }

  @Override
  public List<T> getAllVertices() {
    // TODO Auto-generated method stub
    return new ArrayList<T>(vertices.keySet());
  }

  @Override
  public List<T> getAdjacentVerticesOf(T vertex) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    if (vertex == null) {
      throw new IllegalArgumentException("Cannot find adjacent vertices of null verticy");
    }else if (vertices.containsKey(vertex)) {
      return new ArrayList<T>(vertices.get(vertex).edges);
    }
    return new ArrayList<T>();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public int order() {
    return order;
  }
  // TODO: implement this class
}
