import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        AdjacencyMatrix<String> graph = new AdjacencyMatrix<>();
        AlgoritmoFloyd<String> solver = new AlgoritmoFloyd<>();
        Scanner sn = new Scanner(System.in);

        // 1. Lectura inicial del archivo
        try (Scanner fs = new Scanner(new File("guategrafo.txt"))) {
            while (fs.hasNext()) {
                String ciudad1 = fs.next();
                String ciudad2 = fs.next();
                if (fs.hasNextDouble()) {
                    double peso = fs.nextDouble();
                    graph.addEdge(ciudad1, ciudad2, peso);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ALERTA: 'guategrafo.txt' no encontrado. Iniciando con grafo vacío.");
        } catch (Exception e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }

        boolean continuar = true;
        while (continuar) {
            // Sincronizamos el algoritmo con el estado actual del grafo
            solver.solve(graph); 

            System.out.println("\n========================================");
            System.out.println("   LOGISTICA CENTRO DE RESPUESTA COVID-19");
            System.out.println("========================================");
            System.out.println("CIUDADES: " + graph.getVertices());
            System.out.println("1. Calcular ruta mas corta");
            System.out.println("2. Ver ciudad centro (Oficinas Centrales)");
            System.out.println("3. Modificar red vial (Interrupcion/Conexion)");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");
            
            try {
                String entrada = sn.nextLine();
                int opt = Integer.parseInt(entrada);

                switch (opt) {
                    case 1 -> {
                        System.out.print("Ciudad de Origen: "); 
                        String o = sn.nextLine();
                        System.out.print("Ciudad de Destino: "); 
                        String d = sn.nextLine();
                        System.out.println("\n--- RESULTADO DE RUTA ---");
                        System.out.println(solver.getShortestPath(o, d));
                    }
                    case 2 -> {
                        String centro = solver.getCenter();
                        System.out.println("\n--- ANALISIS DE CENTRALIDAD ---");
                        System.out.println("El centro logistico ideal es: " + (centro != null ? centro : "Indeterminado"));
                    }
                    case 3 -> {
                        System.out.println("a) Reportar interrupcion (Eliminar ruta)");
                        System.out.println("b) Agregar nueva conexion (Nueva ruta)");
                        System.out.print("Seleccione (a/b): ");
                        String sub = sn.nextLine().toLowerCase();
                        
                        if (!sub.equals("a") && !sub.equals("b")) {
                            System.out.println("Error: Solo se permite 'a' o 'b'.");
                            break;
                        }
                        
                        System.out.print("Ciudad origen: "); 
                        String c1 = sn.nextLine();
                        System.out.print("Ciudad destino: "); 
                        String c2 = sn.nextLine();
                        
                        if (sub.equals("a")) {
                            graph.removeEdge(c1, c2);
                            System.out.println("Operacion exitosa: Conexion eliminada.");
                        } else {
                            try {
                                System.out.print("Nueva distancia (KM): ");
                                double dist = Double.parseDouble(sn.nextLine());
                                if (dist < 0) {
                                    System.out.println("Error: La distancia no puede ser negativa.");
                                } else {
                                    graph.addEdge(c1, c2, dist);
                                    System.out.println("Operacion exitosa: Conexion agregada.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Debe ingresar un valor numerico para la distancia.");
                            }
                        }
                    }
                    case 4 -> {
                        System.out.println("Cerrando sistema logistico...");
                        continuar = false;
                    }
                    default -> System.out.println("Error: Seleccione una opcion del 1 al 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada no valida. Por favor, use numeros.");
            }
        }
        sn.close();
    }
}