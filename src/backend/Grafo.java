/*|———————————————————————————————————————————————————————|
* |          Proyecto final estructura de datos           |
* |                       Grafos                          |
* |                 Inyer Marín Medina                    |
* |          Universidad Tecnológica de Pereira           |
* |                        2021                           |
* |———————————————————————————————————————————————————————|
*/


package backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Inyer
 */
public class Grafo {
    
    //variables
    public static int max=1000;  
    
    //bfs y dfs
    public static ArrayList<Integer> l_adj[] = new ArrayList[max]; 
    public static boolean visitado[] = new boolean[max];
    static String c_dfs = " | ";
    
    //disjkstra
    static ArrayList<Node> adjNodo[] = new ArrayList[max];  
    public static long distancia_dijkstra[] = new long[max];     
    public static boolean marked[]= new boolean[max];
    
    
    //inicializador para algunos vectores y listas
    public static void inicializar(){
        for (int i = 0; i < max; i++) {
            l_adj[i] = new ArrayList<Integer>();
        }
        
        for (int i = 0; i < max; i++) {
            adjNodo[i] = new ArrayList<Node>();
        }
        
        for (int i = 0; i < max; i++) {
            distancia_dijkstra [i] = 1000;
        }
        
    }
    
    //bfs
    public static String bfs(int s){
        //se inicializan los objetos
        inicializar_Dfs();
        
        //declaracion
        int actual, i, next;
        Queue<Integer> q = new LinkedList<Integer>();
        String c = "\n--------------- BFS ------------------\n\n";
        c += "El recorrido que se hizo fue el siguiente: ";
        q.add(s);
        visitado[s] = true;
        
        c += " | " + s + "->";
        
        //mientras la cola no esté vacia
        while (!q.isEmpty()) {   
            //se obtiene el nodo actual
            actual = q.poll();
            if (!visitado[actual]) {
                c += actual + "->";
            }
            //se recorre la lista de adyacencia
            for (i = 0; i < l_adj[actual].size(); i++) {
                
                //se obtiene el siguiente y seguidamente se añade a la cola
                //para seguir con el proceso, hasta llegar al último nodo visitado
                next = l_adj[actual].get(i);
                if (visitado[next] == false) {
                    visitado[next] = true;
                    q.add(next);
                    c += next + "->";
                }
                
            }
        }
        inicializar_Dfs();
        
        return c;
    }
    
    //dfs publico
    public static String dfs(int s){
        c_dfs = "\n--------------- DFS ------------------\n\n";
        c_dfs += "El recorrido que se hizo fue el siguiente: ";
        String c = dfs(s, 0);
        inicializar_Dfs();
        return c;
    }
    
    //dfs privado
    private static String dfs(int s, int a){
        //declaración
        int i, next;
        visitado[s] = true;
        c_dfs += s + "->";
        
        //se recorre la lista de adyacencia
        for (i = 0; i < l_adj[s].size(); i++) {
            next = l_adj[s].get(i);
            if (!visitado[next]) {
                //se hace recursividad
                dfs(next, 0);
            }
        }
        
        return c_dfs;
    }
    
    //inicializador de otros objetos
    static void inicializar_Dfs(){
        for (int i = 0; i < max; i++) {
            visitado [i] = false;
        }
        
        for (int i = 0; i < max; i++) {
            marked [i] = false;
        }
    }
    
    //inicializador de peso
    static void i_peso(long h []){
        
        for (int i = 0; i < h.length; i++) {
            if (h[i] ==  0) {
                h[i] = 1000;
            }
        }
    }
    
    //dijkstra recibe nodo inicio, y nodo fin del recorrido
    public static String [] dijkstra(int nodo, int fin){
        //se inicializan algunas variables y objetos
        inicializar_Dfs();
        
        //declaracion
        Queue<Node> pq = new LinkedList<Node>();
        pq.add(new Node(nodo, 0){});
        
        distancia_dijkstra[nodo] = 0;
        int actual, j, previo;
        int adyacente [] = new int [20];
        long peso [] = new long [20];
        int [] valores = new int [2];
        int prev[] = new int[max];
        long height = 0;
        String c [] = new String [2];
        c [1] = "\n--------------- Dijkstra ------------------\n\n";
        c [1] += "Los datos del recorrido más corto son los siguientes: \n";
        c [1] += "Peso: ";
        c [0] = "Recorrido: " + pq.peek().nroNodoVisita;
       
        //mientras no esté vacia la cola
        while(!pq.isEmpty()){
            //se reincia el peso cada que vuelve a iterar
            
            i_peso(peso);
            actual = pq.peek().nroNodoVisita;//Vemos el elemento pero no lo sacamos
            previo = pq.peek().previo; //sacamos el previo
            pq.poll();
            
            if(!marked[actual]){
                //se marca el nodo actual
                marked[actual] = true;
                // si el nodo fin está visitado, significa que ya ha acabado y puede salir
                if (marked[fin]){
                    break;
                }
                
                //se recorre la lista de adyacencia obteniendo todos los pesos
                for(j = 0; j<adjNodo[actual].size(); j++){
                    if (adjNodo[actual].get(j).nroNodoVisita == previo) {
                    }
                    else{
                        adyacente [j] = adjNodo[actual].get(j).nroNodoVisita;
                        peso [j] = adjNodo[actual].get(j).costo;
                    }
                    
                }
                
                //se valida si hay un camino directo desde el nodo actual al nodo fin
                //con el método buscar
                int vec [] = buscar(actual, fin);
                
                if (vec[2] == 1) {
                    adyacente[0] = vec[0];
                    peso[0] = vec[1];
                }
                //si no hay camino directo entra a este condicional
                else{
                    //se calcula el peso mínimo y se retrona junto con el indice
                    //de donde fue encontrado
                    valores = calcularMin(peso);
                    
                    //se asigna el nodo que seguirá con el recorrido
                    adyacente [0] = adjNodo[actual].get(valores[1]).nroNodoVisita;
                    peso [0] = valores [0];
                    
                    //se asigna la distancia que se va sumando hasta ahora
                    distancia_dijkstra [adyacente[0]] = distancia_dijkstra[actual] + peso [0];
                    
                    //se asigan el previo del adyacente
                    prev [adyacente[0]] = actual;
                }
                
                //este será el peso final del recorrido
                height += peso[0];
                
                //si el adyacente no está marcado y es diferente del nodo actual, entonces entra
                if(!marked[adyacente[0]] && adyacente[0] != actual){
                    //se añade a la cola el adyacente
                    pq.add(new Node(adyacente[0], distancia_dijkstra[adyacente[0]], prev[adyacente[0]]));
                    c[0] += "->" + adyacente[0];
                }
            }
        }
        
        //se retorna el resultado del recorrido
        c[1] += Integer.toString((int) height);
        return c;
    }
    
    //método para buscar enlaces directos al nodo final
    static int []buscar(int actual, int fin){
        int retorno[] = new int [3];
        
        //recorre la lista de adyacencia
        for(int j = 0; j<adjNodo[actual].size(); j++){
            
            //si encuentra uno que coincida con el nodo fin
            //va a retornar el nodo, y el peso
            //para añadirlo como nodo siguiente a visitar
            if (adjNodo[actual].get(j).nroNodoVisita == fin) {
                retorno [0] = adjNodo[actual].get(j).nroNodoVisita;
                retorno [1] = (int) adjNodo[actual].get(j).costo;
                retorno[2] = 1;
                return retorno;
            }
        }
        
        //si no encuentra nada devuelve un booleano indicando que no encontro 
        //coincidencias
        retorno [2] = 0;
        return retorno;
        
    }
    
    //método para calcular el peso menor
    static int [] calcularMin(long h []){
        int valores [] = new int [2];
        int maximo = 1000; //valor maximo para hacer las comparaciones
        
        //se recorre el vector en busca del valor menor
        for (int i = 0; i < h.length; i++) {
            if (h [i] < maximo) {
                maximo = (int) h[i];
                valores[1] = i;
            }
            
            if (h[i] == 1000) {
                break;
            }
        }
        
        valores [0] = maximo;  //al finalizar, la variable maximo alberga el menor

        return valores;
    }
    
    
    //método para rellenar las listas de adyacencia
    public static void rellenar(int origen, int destino, int costo) {
  
        l_adj[origen].add(destino);
        l_adj[destino].add(origen);
        
        adjNodo[origen].add(new Node(destino, costo));
        adjNodo[destino].add(new Node(origen, costo));
        
    }
    
    //método para vaciar las listas de adyacencia
    public static void vaciar(){
        for (int i = 0; i < l_adj.length; i++) {
            l_adj[i].clear();
        }
        
        for (int i = 0; i < adjNodo.length; i++) {
            adjNodo[i].clear();
        }
    }
    
    //inicializador
    public Grafo() {    
        inicializar();
        inicializar_Dfs();
    }
    
    
    
}
