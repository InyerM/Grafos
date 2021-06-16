/*|———————————————————————————————————————————————————————|
* |          Proyecto final estructura de datos           |
* |                       Grafos                          |
* |                 Inyer Marín Medina                    |
* |          Universidad Tecnológica de Pereira           |
* |                        2021                           |
* |———————————————————————————————————————————————————————|
*/


package backend;

import java.util.Arrays;
import java.util.Comparator;

/**********************************************
 ***Algoritmo: Minimum Spanning Tree - Kruskal
 ***Tipo: Teoria de Grafos
 ***@author: Jhosimar George Arias Figueroa
 ***https://github.com/jariasf/Online-Judges-Solutions/blob/master/Algorithms/JAVA/Graph%20Theory/Kruskal.java
 *********************************************/

public class Kruskal {
    
    static final int MAX = 1005;  //maximo numero de vértices
    static int l = 0;

	///UNION-FIND
    static int padre[] = new int[ MAX ];  //Este arreglo contiene el padre del i-esimo nodo

	//Método de inicialización
    static void MakeSet( int n ){
	for( int i = 1 ; i <= n ; ++i ) padre[ i ] = i;
    }

	//Método para encontrar la raiz del vértice actual X
    static int Find( int x ){
	return ( x == padre[ x ] ) ? x : ( padre[ x ] = Find( padre[ x ] ) );
    }

	//Método para unir 2 componentes conexas
    static void Union( int x , int y ){
	padre[ Find( x ) ] = Find( y );
    }

	//Método que me determina si 2 vértices estan o no en la misma componente conexa
    static boolean sameComponent( int x , int y ){
	if( Find( x ) == Find( y ) ) return true;
	    return false;
    }
	///FIN UNION-FIND

    static int V , E;      //numero de vertices y aristas

	//Estructura arista( edge )
    public static class Edge implements Comparator<Edge>{
	int origen;     //Vértice origen
	int destino;    //Vértice destino
	int peso;       //Peso entre el vértice origen y destino
	Edge(){}
	    //Comparador por peso, me servira al momento de ordenar lo realizara en orden ascendente
	    //Ordenar de forma descendente para obtener el arbol de expansion maxima
	@Override
	public int compare(Edge e1 , Edge e2 ) {
			//return e2.peso - e1.peso; //Arbol de expansion maxima
            return e1.peso - e2.peso;   //Arbol de expansion minima
	}
    };
	
    static Edge arista[] = new Edge[ MAX ];      //Arreglo de aristas para el uso en kruskal
    static Edge MST[] = new Edge[ MAX ];     //Arreglo de aristas del MST encontrado

    public static String KruskalMST(){
        String c = "------------------- Kruskal ---------------------\n\n";
	int origen , destino , peso;
	int total = 0;          //Peso total del MST
	int numAristas = 0;     //Numero de Aristas del MST
	    
	MakeSet( V );           //Inicializamos cada componente
	Arrays.sort( arista , 0 , E , new Edge() );    //Ordenamos las aristas por su comparador

	for( int i = 0 ; i < E ; ++i ){     //Recorremos las aristas ya ordenadas por peso
	    origen = arista[ i ].origen;    //Vértice origen de la arista actual
	    destino = arista[ i ].destino;  //Vértice destino de la arista actual
	    peso = arista[ i ].peso;        //Peso de la arista actual

	        //Verificamos si estan o no en la misma componente conexa
	    if( !sameComponent( origen , destino ) ){  //Evito ciclos
	        total += peso;              //Incremento el peso total del MST
	        MST[ numAristas++ ] = arista[ i ];  //Agrego al MST la arista actual
	        Union( origen , destino );  //Union de ambas componentes en una sola
	        }
	}

	    //Si el MST encontrado no posee todos los vértices mostramos mensaje de error
	    //Para saber si contiene o no todos los vértices basta con que el numero
	    //de aristas sea igual al numero de vertices - 1
	if( V - 1 != numAristas ){
	    return "No existe MST valido para el grafo ingresado, el grafo debe ser conexo.";
	}
        c += "-----El MST encontrado contiene las siguientes aristas-----\n";
        
	for( int i = 0 ; i < numAristas ; ++i )
            c += "( " + MST[i].origen + ", " + MST[i].destino + ") : " + MST[i].peso + "\n";    //( vertice u , vertice v ) : peso
        
        c += "El costo minimo de todas las aristas del MST es : " + total + "\n"; 
        
        return c;
    }
    
    //método para ingresar los valores al grafo
    public static void iniciar(int org, int dest, int h) {

	//Realizamos el ingreso del grafo, almacenando las aristas en un arreglo con los datos respectivos

	arista[l] = new Edge();
        arista[l].origen = org;
        arista[l].destino = dest;
	arista[l].peso = h;
	//arista[ i ] = new Arista( sc.nextInt() , )
	
        l++;
    }
    
    //constructor
    public Kruskal(int vert, int arist) {
        V = vert;  //vertices
        E = arist;   //aristas
    }
    
    
}


