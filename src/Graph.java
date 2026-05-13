public interface Graph<V,E> {
    /** Agrega una arista entre dos vértices con un peso específico */
    void addEdge(V v1, V v2, E weight);
    /** Elimina la conexión directa entre dos ciudades */
    void removeEdge(V v1, V v2);
    /** Devuelve la cantidad de ciudades en la red. */
    int size();
    /** Obtiene el peso almacenado en la matriz para los índices dados. */
    double getWeight(int i, int j);
}

