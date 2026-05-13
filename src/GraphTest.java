import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    private AdjacencyMatrix<String> grafo;
    private AlgoritmoFloyd<String> floyd;

    @Before
    public void setUp() {
        grafo = new AdjacencyMatrix<>();
        floyd = new AlgoritmoFloyd<>();
        // Caso de prueba simple
        grafo.addEdge("A", "B", 10.0);
        grafo.addEdge("B", "C", 5.0);
        grafo.addEdge("A", "C", 50.0); // Ruta directa larga
        floyd.solve(grafo);
    }

    @Test
    public void testRutaMasCorta() {
        // La ruta de A a C debe ser 15 (A->B->C) y no 50
        String resultado = floyd.getShortestPath("A", "C");
        assertTrue("La distancia debería ser 15.0", resultado.contains("15.0"));
        assertTrue("La trayectoria debe pasar por B", resultado.contains("A -> B -> C"));
    }

    @Test
    public void testCentroGrafo() {
        // En un grafo lineal A->B->C, el centro debería ser B
        // (Dependiendo de la conectividad total de tu archivo)
        assertNotNull(floyd.getCenter(), "El centro no debería ser nulo");
    }

    @Test
    public void testAgregarEliminarArista() {
        grafo.removeEdge("A", "B");
        floyd.solve(grafo);
        String resultado = floyd.getShortestPath("A", "B");
        assertTrue("Debería indicar que no hay ruta tras eliminar", resultado.contains("No hay ruta"));
    }
}