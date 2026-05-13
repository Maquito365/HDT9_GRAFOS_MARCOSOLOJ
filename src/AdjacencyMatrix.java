import java.util.*;

public class AdjacencyMatrix<V> implements Graph<V, Double> {
    private final double INF = Double.POSITIVE_INFINITY;
    private double[][] matrix = new double[0][0];
    private List<V> vertices = new ArrayList<>();

    @Override
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

    @Override
    public void removeEdge(V v1, V v2) {
        int i = vertices.indexOf(v1);
        int j = vertices.indexOf(v2);
        if (i != -1 && j != -1) matrix[i][j] = INF;
    }

    /** MUESTRA LA MATRIZ (REQUISITO HOJA TRABAJO)  */
    public void printMatrix() {
        System.out.println("\n--- MATRIZ DE ADYACENCIA ACTUAL ---");
        System.out.print("\t");
        for (V v : vertices) System.out.print(v + "\t");
        System.out.println();
        
        for (int i = 0; i < matrix.length; i++) {
            System.out.print(vertices.get(i) + "\t");
            for (int j = 0; j < matrix[i].length; j++) {
                String val = (matrix[i][j] == INF) ? "INF" : String.valueOf(matrix[i][j]);
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }

    public List<V> getVertices() { return vertices; }
    @Override public int size() { return vertices.size(); }
    @Override public double getWeight(int i, int j) { return matrix[i][j]; }
}