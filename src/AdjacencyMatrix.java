import java.util.*;

public class AdjacencyMatrix<V> implements Graph<V, Double> {
    private final double INF = Double.POSITIVE_INFINITY; // Representa la ausencia de arista
    private double[][] matrix;
    private List<V> vertices;

    public AdjacencyMatrix() {
        matrix = new double[0][0];
        vertices = new ArrayList<>();
    }
     // Agrega un vértice al grafo, expandiendo la matriz de adyacencia
    public void addVertex(V vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            int n = vertices.size();
            double[][] newMatrix = new double[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(newMatrix[i], INF);
                newMatrix[i][i] = 0;
            }
            // Copiar datos anteriores
            for (int i = 0; i < matrix.length; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix.length);
            }
            matrix = newMatrix;
        }
    }
    // Implementación de los métodos de la interfaz Graph
    @Override
    public void addEdge(V v1, V v2, Double weight) {
        addVertex(v1);
        addVertex(v2);
        matrix[getIndex(v1)][getIndex(v2)] = weight;
    }
    // Elimina la arista entre v1 y v2 estableciendo su peso a INF
    @Override
    public void removeEdge(V v1, V v2) {
        int i = getIndex(v1);
        int j = getIndex(v2);
        if (i != -1 && j != -1) matrix[i][j] = INF;
    }
    
    @Override
    public int getIndex(V vertex) { return vertices.indexOf(vertex); }
    
    @Override
    public int size() { return vertices.size(); }
    
    @Override
    public double getWeight(int i, int j) { return matrix[i][j]; }

    public List<V> getVertices() { return vertices; }
}