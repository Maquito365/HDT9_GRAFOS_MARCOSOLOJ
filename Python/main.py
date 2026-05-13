import networkx as nx

def main():
    # Creamos un grafo dirigido (DiGraph) como pide la hoja 
    G = nx.DiGraph()

    # 1. Lectura del archivo guategrafo.txt 
    try:
        with open("guategrafo.txt", "r") as f:
            for line in f:
                parts = line.split()
                if len(parts) == 3:
                    ciudad1, ciudad2, distancia = parts
                    # Agregamos la arista con el peso (KM) 
                    G.add_edge(ciudad1, ciudad2, weight=float(distancia))
        print("Archivo cargado exitosamente.")
    except FileNotFoundError:
        print("Error: No se encontró 'guategrafo.txt'.")
        return
    except Exception as e:
        print(f"Error al procesar el archivo: {e}")
        return

    while True:
        # Menú de opciones según el requerimiento 
        print("\n" + "="*30)
        print("  LOGÍSTICA COVID-19 (PYTHON)")
        print("="*30)
        print("1. Calcular ruta más corta")
        print("2. Ver centro del grafo")
        print("3. Modificar red vial")
        print("4. Salir")
        
        opcion = input("Seleccione una opción: ")

        if opcion == "1":
            origen = input("Ciudad de Origen: ")
            destino = input("Ciudad de Destino: ")
            
            if origen in G and destino in G:
                try:
                    # Calculamos Floyd-Warshall para obtener distancias y rutas 
                    distancia = nx.shortest_path_length(G, source=origen, target=destino, weight='weight')
                    trayectoria = nx.shortest_path(G, source=origen, target=destino, weight='weight')
                    
                    print(f"\n--- RESULTADO ---")
                    print(f"Distancia total: {distancia} KM")
                    print(f"Trayectoria: {' -> '.join(trayectoria)}")
                except nx.NetworkXNoPath:
                    print(f"\nNo existe una ruta entre {origen} y {destino}.")
            else:
                print("\nError: Una o ambas ciudades no existen en el grafo.")

        elif opcion == "2":
            # Cálculo manual del centro basado en excentricidad (máximo de la columna) 
            nodos = list(G.nodes())
            if not nodos:
                print("El grafo está vacío.")
                continue

            # Obtenemos todas las distancias mínimas entre todos los pares 
            distancias_todas = dict(nx.all_pairs_dijkstra_path_length(G))
            
            min_max_dist = float('inf')
            centro_logistico = None

            for j in nodos: # Evaluamos cada ciudad 'j' como posible centro
                max_dist_hacia_j = 0
                alcanzable_por_todos = True
                
                for i in nodos:
                    if i != j:
                        # Buscamos la distancia de i a j (columna j en la matriz) 
                        d = distancias_todas.get(i, {}).get(j, float('inf'))
                        if d == float('inf'):
                            alcanzable_por_todos = False
                            break
                        if d > max_dist_hacia_j:
                            max_dist_hacia_j = d
                
                # El centro es el nodo donde el máximo de su columna es el mínimo 
                if alcanzable_por_todos and max_dist_hacia_j < min_max_dist:
                    min_max_dist = max_dist_hacia_j
                    centro_logistico = j

            print(f"\n--- ANÁLISIS DE CENTRALIDAD ---")
            if centro_logistico:
                print(f"El centro logístico ideal es: {centro_logistico}")
            else:
                print("No se pudo determinar un centro (el grafo no está totalmente conectado).")

        elif opcion == "3":
            print("a) Reportar interrupción (Eliminar)")
            print("b) Agregar nueva conexión")
            sub = input("Seleccione (a/b): ").lower()
            
            c1 = input("Ciudad 1: ")
            c2 = input("Ciudad 2: ")
            
            if sub == 'a':
                if G.has_edge(c1, c2):
                    G.remove_edge(c1, c2)
                    print("Conexión eliminada.")
                else:
                    print("La conexión no existe.")
            elif sub == 'b':
                dist = input("Distancia (KM): ")
                G.add_edge(c1, c2, weight=float(dist))
                print("Conexión agregada/actualizada.")

        elif opcion == "4":
            print("Cerrando sistema...")
            break
        else:
            print("Opción no válida.")

if __name__ == "__main__":
    main()