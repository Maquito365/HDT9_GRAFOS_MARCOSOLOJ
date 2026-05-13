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

        if (i == -1 || j == -1) return "ERROR: Una o ambas ciudades no existen.";
        if (dists[i][j] == Double.POSITIVE_INFINITY) return "No hay ruta entre " + start + " y " + end;
        
        StringBuilder path = new StringBuilder(start.toString());
        int curr = i;
        while (curr != j) {
            curr = next[curr][j];
            path.append(" -> ").append(vertices.get(curr));
        }
        return "Distancia: " + dists[i][j] + " KM\nTrayectoria: " + path.toString();
    }

    public V getCenter() {
        if (vertices.isEmpty()) return null;
        int n = vertices.size();
        double minMaxDist = Double.POSITIVE_INFINITY;
        V center = null;

        for (int i = 0; i < n; i++) { // i es la ciudad que evaluamos como posible centro
            double maxDistDesdeI = 0;
            boolean puedeLlegarATodos = true;

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    if (dists[i][j] == Double.POSITIVE_INFINITY) {
                        puedeLlegarATodos = false;
                        break;
                    }
                    if (dists[i][j] > maxDistDesdeI) maxDistDesdeI = dists[i][j];
                }
            }

            // El centro es el nodo cuya distancia máxima a cualquier otro es la mínima
            if (puedeLlegarATodos && maxDistDesdeI < minMaxDist) {
                minMaxDist = maxDistDesdeI;
                center = vertices.get(i);
            }
        }
        return (center == null && !vertices.isEmpty()) ? vertices.get(0) : center;
    }
}