import java.util.List;

public class AlgoritmoFloyd<V> {
    private double[][] dists;
    private int[][] next;
    private List<V> vertices;

    public void solve(AdjacencyMatrix<V> graph) {
        int n = graph.size();
        this.vertices = graph.getVertices();
        dists = new double[n][n];
        next = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dists[i][j] = graph.getWeight(i, j);
                next[i][j] = (dists[i][j] != Double.POSITIVE_INFINITY && i != j) ? j : -1;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dists[i][k] + dists[k][j] < dists[i][j]) {
                        dists[i][j] = dists[i][k] + dists[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public String getShortestPath(V start, V end) {
        int i = vertices.indexOf(start);
        int j = vertices.indexOf(end);
        if (i == -1 || j == -1) return "Error: Ciudad no registrada.";
        if (dists[i][j] == Double.POSITIVE_INFINITY) return "No hay ruta disponible.";
        
        StringBuilder path = new StringBuilder(start.toString());
        int curr = i;
        while (curr != j) {
            curr = next[curr][j];
            path.append(" -> ").append(vertices.get(curr));
        }
        return "Distancia total: " + dists[i][j] + " KM\nRuta: " + path.toString();
    }

    public V getCenter() {
        if (vertices.isEmpty()) return null;
        int n = vertices.size();
        double minMaxDist = Double.POSITIVE_INFINITY;
        V center = null;

        for (int j = 0; j < n; j++) {
            double maxColDist = 0;
            for (int i = 0; i < n; i++) {
                if (i != j && dists[i][j] > maxColDist) maxColDist = dists[i][j];
            }
            if (maxColDist < minMaxDist) {
                minMaxDist = maxColDist;
                center = vertices.get(j);
            }
        }
        return center;
    }
}