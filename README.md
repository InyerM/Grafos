# Grafos
Programa de grafos que nos permitirá, a partir de un archivo de texto plano, crear grafos no direccionales y ponderados, y a su vez hacer cuatro importantes recorridos

Este proyecto hace parte del proyecto final de Estructura de datos, en el que el objetivo era modificar un poryecto de grafos, de tal forma que funcione correctamente,
y además, tenga un frontend diseñado para permitir la interacción usuario-algoritmo

Cuenta con una clase grafo, una clase nodo y una clase Kruskal(clase que nos servirá para hacer el recorrido Kruskal, además de que tiene su propio grafo).
Gracias a todas estas clases, es que pudimos permitir al usuario hacer recorridos del grafo que ha insertado. 

La forma ede agregar nodos y enlaces al grafo es la siguiente: 
Mediante un archivo txt, que el usuario tendrá que cargar en el programa, se creará el grafo, los requisitos del archivo de texto son los siguientes:

- La primera línea del archivo debe contener la cantidad de nodos que contendrá el grafo
- Desde la segunda hasta el final cada línea serán tres números separados por coma (,) Y se interpreta así:
- El primer número es el numero del nodo origen
- El segundo número es el número del nodo destino 
- El tercer número es el costo de recorrer ese arco

Si la información del archivo no es coherente o no contiene el formato se debe sacar un mensaje informando que no se ha cargado el fichero.

Para los algoritmos que no requieren ponderación simplemente se ignorará ese dato. La idea es que una vez subido un archivo pueda usar 
el grafo para todos los algoritmos.

Algoritmos de recorrido: 

- BFS
- DFS
- DIJKSTRA
- KRUSKAL
