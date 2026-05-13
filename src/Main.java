import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        AdjacencyMatrix<String> graph = new AdjacencyMatrix<>();
        AlgoritmoFloyd<String> solver = new AlgoritmoFloyd<>();
        Scanner sn = new Scanner(System.in);

        // Carga de archivo guategrafo.txt
        try (Scanner fs = new Scanner(new File("guategrafo.txt"))) {
            while (fs.hasNext()) {
                graph.addEdge(fs.next(), fs.next(), fs.nextDouble());
            }
        } catch (Exception e) {
            System.out.println("Nota: Archivo no cargado o formato incorrecto.");
        }

        while (true) {
            solver.solve(graph);
            System.out.println("\n--- LOGISTICA COVID-19 (GUATEMALA) ---");
            System.out.println("1. Buscar ruta mas corta\n2. Ver centro logistico\n3. Modificar rutas\n4. Ver matriz de adyacencia\n5. Salir");
            System.out.print("Opcion: ");
            
            try {
                int opt = Integer.parseInt(sn.nextLine());
                if (opt == 5) break;

                switch (opt) {
                    case 1 -> {
                        System.out.print("Ciudad origen: "); String o = sn.nextLine();
                        System.out.print("Ciudad destino: "); String d = sn.nextLine();
                        System.out.println(solver.getShortestPath(o, d));
                    }
                    case 2 -> System.out.println("Centro del grafo: " + solver.getCenter());
                    case 3 -> {
                        System.out.print("a) Bloqueo b) Nueva ruta: ");
                        String sub = sn.nextLine();
                        System.out.print("Origen: "); String c1 = sn.nextLine();
                        System.out.print("Destino: "); String c2 = sn.nextLine();
                        if (sub.equalsIgnoreCase("a")) graph.removeEdge(c1, c2);
                        else {
                            System.out.print("Distancia (KM): ");
                            graph.addEdge(c1, c2, Double.parseDouble(sn.nextLine()));
                        }
                    }
                    case 4 -> graph.printMatrix();
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (Exception e) { System.out.println("Error en los datos ingresados."); }
        }
    }
}