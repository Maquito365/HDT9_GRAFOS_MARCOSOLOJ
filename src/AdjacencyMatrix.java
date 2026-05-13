import java.util.*;

public class AdjacencyMatrix<V> {
    private final double INF = Double.POSITIVE_INFINITY;
    private double[][] matrix = new double[0][0];
    private List<V> vertices = new ArrayList<>();

    public void addEdge(V v1, V v2, Double weight) {
        if (!vertices.contains(v1)) addVertex(v1);
        if (!vertices.contains(v2)) addVertex(v2);
        matrix[vertices.indexOf(v1)][vertices.indexOf(v2)] = weight;
    }

    private void addVertex(V vertex) {
        vertices.add(vertex);
        int n = vertices.size();
        double[][] newMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(newMatrix[i], INF);
            newMatrix[i][i] = 0;
        }
        for (int i = 0; i < matrix.length; i++) 
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix.length);
        matrix = newMatrix;
    }

    public void removeEdge(V v1, V v2) {
        int i = vertices.indexOf(v1);
        int j = vertices.indexOf(v2);
        if (i != -1 && j != -1) matrix[i][j] = INF;
    }

    public List<V> getVertices() { return vertices; }
    public int size() { return vertices.size(); }
    public double getWeight(int i, int j) { return matrix[i][j]; }
}