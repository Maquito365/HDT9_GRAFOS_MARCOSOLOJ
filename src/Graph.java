public interface Graph<V,E> {
    void addEdge(V v1, V v2, E weight); // Agrega una arista entre los vértices v1 y v2 con un peso específico
    void removeEdge(V v1, V v2); // Elimina la arista entre los vértices v1 y v2
    int getIndex(V vertex); // Devuelve el índice del vértice en la matriz de adyacencia
    int size(); // Devuelve el número de vértices en el grafo
    double getWeight(int i, int j); // Devuelve el peso de la arista entre los vértices con índices i y j
}
