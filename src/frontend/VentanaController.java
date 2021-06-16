
/*|———————————————————————————————————————————————————————|
* |          Proyecto final estructura de datos           |
* |                       Grafos                          |
* |                 Inyer Marín Medina                    |
* |          Universidad Tecnológica de Pereira           |
* |                        2021                           |
* |———————————————————————————————————————————————————————|
*/

package frontend;

import backend.Grafo;
import backend.Kruskal;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author Inyer
 */
public class VentanaController implements Initializable {
    
    //Declaración de la ventana emergente
    public static Alert alert = new Alert(Alert.AlertType.INFORMATION);
    public static TextInputDialog tx = new TextInputDialog();
    
    //variables globales 
    private static int cant_nodos;
    private static int cant_aristas;
    private static final int [] valores = new int [99];
    private static ArrayList<Integer> lista[] = new ArrayList[100];
    private Grafo grafo = new Grafo();
    private Kruskal kruskal;
    
    
    
    //interfaz
    @FXML
    private Button btnInsertar, btnInsertar_archivo, btnRecorridos, btnSalir, btnIniciar,
            btnBfs, btnDfs, btnDijkstra, btnKruskall;
    
    @FXML
    private Pane pnInsertar, paneRecorridos;
    
    @FXML
    private TextField txtFile;
    
    @FXML
    private ComboBox cbRecorrido;
    
    
    //funcion que ejecutarán todos los botones, y dentro unos condicionales para validar
    //que botón fue presionado
    @FXML
    private void action_btn(ActionEvent ev){
        //se obtiene el botón presionado
        Object btn = ev.getSource();
        
        //validaciones de cada botón
        if (btn.equals(btnInsertar)) {
            pnInsertar.setVisible(true);
            paneRecorridos.setVisible(false);
        } 
        else if(btn.equals(btnRecorridos)){
            paneRecorridos.setVisible(true);
            pnInsertar.setVisible(false);
        }
        else if(btn.equals(btnSalir)){
            System.exit(0);
        }
        else if(btn.equals(btnInsertar_archivo)){
            Insertar();
        }
        else if(btn.equals(btnIniciar)){
            //se obtiene el valor del combobox, para saber cual método de recorrido aplicar
            String seleccionado = cbRecorrido.getSelectionModel().getSelectedItem().toString();
            IniciarRecorrido(seleccionado);
        }
        else if(btn.equals(btnBfs)){
            alert.setContentText("Formalmente, BFS es un algoritmo de búsqueda "
                    + "sin información, que expande y examina todos los nodos "
                    + "de un árbol sistemáticamente para buscar una solución"
                    + ". El algoritmo no usa ninguna estrategia heurística.");
            
            alert.showAndWait(); 
        }
        else if(btn.equals(btnDfs)){
            alert.setContentText("Una Búsqueda en profundidad (en inglés DFS o "
                    + "Depth First Search) es un algoritmo de búsqueda no "
                    + "informada utilizado para recorrer todos los nodos de "
                    + "un grafo o árbol (teoría de grafos) de manera ordenada,"
                    + " pero no uniforme. Su funcionamiento consiste en ir"
                    + " expandiendo todos y cada uno de los nodos que va "
                    + "localizando, de forma recurrente, en un camino concreto."
                    + " Cuando ya no quedan más nodos que visitar en dicho"
                    + " camino, regresa (Backtracking), de modo que repite el"
                    + " mismo proceso con cada uno de los hermanos del"
                    + " nodo ya procesado.");
            alert.showAndWait(); 
        }
        else if(btn.equals(btnDijkstra)){
            alert.setContentText("La idea subyacente en este algoritmo consiste "
                    + "en ir explorando todos los caminos más cortos que "
                    + "parten del vértice origen y que llevan a todos los demás"
                    + " vértices; cuando se obtiene el camino más corto desde"
                    + " el vértice origen hasta el resto de los vértices que"
                    + " componen el grafo, el algoritmo se detiene. Se trata"
                    + " de una especialización de la búsqueda de costo uniforme"
                    + " y, como tal, no funciona en grafos con aristas de coste"
                    + " negativo (al elegir siempre el nodo con distancia menor,"
                    + " pueden quedar excluidos de la búsqueda nodos que en"
                    + " próximas iteraciones bajarían el costo general del"
                    + " camino al pasar por una arista con costo negativo).");
            alert.showAndWait(); 
        }
        else if(btn.equals(btnKruskall)){
            alert.setContentText("El algoritmo de Kruskal es un algoritmo de la"
                    + " teoría de grafos para encontrar un árbol recubridor"
                    + " mínimo en un grafo conexo y ponderado. Es decir,"
                    + " busca un subconjunto de aristas que, formando un árbol,"
                    + " incluyen todos los vértices y donde el valor de la suma"
                    + " de todas las aristas del árbol es el mínimo. Si el"
                    + " grafo no es conexo, entonces busca un bosque expandido"
                    + " mínimo (un árbol expandido mínimo para cada componente"
                    + " conexa). Este algoritmo toma su nombre de Joseph"
                    + " Kruskal, quien lo publicó por primera vez en 1956​"
                    + ". Otros algoritmos que sirven para hallar el árbol"
                    + " de expansión mínima o árbol recubridor mínimo son el"
                    + " algoritmo de Prim, el algoritmo del borrador inverso"
                    + " y el algoritmo de Boruvka.");
            alert.showAndWait(); 
        }
        
    }

    
    //metodo insertar
    private void Insertar(){
        //se declara un objeto que nos servirá para abrir el menú de escoger archivos
        FileChooser fc = new FileChooser();
        
        //se le añade como requisito archivos .txt
        fc.getExtensionFilters().addAll(
                new ExtensionFilter("TXT Files", "*.txt"));
        
        //se abre el dialogo
        File archivo = fc.showOpenDialog(null);
        
        //si archivo es diferente de null, significa que escogió un archivo correcto
        if (archivo != null) {
            //si el archivo no se leyó correctamente, asignará a archivo null
            if (!leerTxt(archivo.getAbsolutePath())) {
                
                archivo = null;
            }
            //si no, se manda la alerta de que el arhivo fue cargado con éxito
            else{
                txtFile.setText(archivo.getAbsolutePath());
                alert.setContentText("Archivo cargado con éxito");
                alert.showAndWait(); 
            }
            
        }
        else{
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El archivo seleccionado debe ser de tipo texto");
            alert.showAndWait(); 
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
        }
    }
    
    //método usado para leer el archivo de texto, retorna un booleano dependiendo de 
    //si lo leyó, o no lo leyó correctamente
    private boolean leerTxt(String direccion){
        try {
            
            //objeto que nos sirve para leer las lineas del archivo
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            //objeto para leer lineas del archivo, es un auxiliar
            BufferedReader bf_1 = new BufferedReader(new FileReader(direccion));
            
            //obtenemos de la primera linea la cantidad de nodos
            int nodos = Integer.parseInt(bf_1.readLine());
            cant_nodos = nodos;
            
            //variables
            String linea;
            int cont = 1;
            
            //mientras no se haya terminado de leer todas las lineas, se ejecutará
            while ((linea = bf.readLine()) != null) { 
                
                //variables para obtener el nodo inicio, nodo final, y peso del archivo
                int n1 , n2 , n3;
                n1 = n2 = n3 = 0;
                
                //si cont es 1, entonces significa que está leyendo la línea 1
                if (cont == 1){
                    
                    //se valida que sea una cadena entera
                    if (!linea.matches("^[0-9]*$")){
                        alerta();
                        return false;
                    }
                    
                }
                //si cont es mayor a 1, estará leyendo las siguientes lineas a la 1
                else{
                    
                    //se dividen la cadena en las partes numéricas
                    String [] partes = linea.split(", ");

                    //se comprueba si cada una de las partes es un número entero
                    if(!partes[0].matches("^[0-9]*$") || !partes[1].matches("^[0-9]*$") || !partes[2].matches("^[0-9]*$")){
                        alerta();
                        return false;
                    }

                    //se agregan los valores verificados a cada uno de las variables
                    n1 = Integer.parseInt(partes[0]);
                    n2 = Integer.parseInt(partes[1]);
                    n3 = Integer.parseInt(partes[2]);

                    //finalmente se agregan a la lista vectorial, los valores recogidos
                    lista[cont-2].add(n1);  // nodo inicio
                    lista[cont-2].add(n2);  // nodo fin
                    lista[cont-2].add(n3);  // peso de la unión
                    
                }
                cont++;
                
            }
            
            //se asigna la cantidad de enlaces o aristas
            cant_aristas = cont-2;

            //si hay más nodos que aristas, significa que el grafo está incorrecto
            if(cant_aristas < cant_nodos){
                alerta();
                return false;
            }

            return true;
        } 
        catch (Exception e) {
            alerta();
            return false;
        }
    }
    
    //alerta
    private void alerta(){
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("El archivo seleccionado no tiene el formato exigido");
        alert.showAndWait(); 
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
    }
    
    //método para iniciar recorrido, recibe como parámetro una cadena que
    //equivale al método que se hará
    private void IniciarRecorrido(String metodo){
        
        //cada que se inicia el un recorrido, se reinicia el grafo y se
        //le asigna nuevamente los valores
        grafo.vaciar();
        
        for (int i = 0; i < cant_aristas; i++) {
            grafo.rellenar(lista[i].get(0), lista[i].get(1), lista[i].get(2));
        }
        
        //bfs
        if (metodo.equals("Bfs")){
            //text input
            tx.setContentText("Ingrese el nodo desde el que iniciará el recorrido: ");
            Optional<String> node = tx.showAndWait();
            
            //se obtiene lo del text input
            int opc = Integer.parseInt(node.get());
            
            //se imprime en la alerta el resultado que retorna la función bfs
            alert.setContentText(grafo.bfs(opc));
            alert.showAndWait(); 
        }
        //dfs
        else if (metodo.equals("Dfs")) {
            
            //text input
            tx.setContentText("Ingrese el nodo desde el que iniciará el recorrido: ");
            Optional<String> node = tx.showAndWait();
            
            //se obtiene lo del text input
            int opc = Integer.parseInt(node.get());
            
            //se imprime en la alerta el resultado que retorna la función dfs
            alert.setContentText(grafo.dfs(opc));
            alert.showAndWait(); 
        }
        //dijkstra
        else if (metodo.equals("Dijkstra")) {
            
            //text input
            tx.setContentText("Ingrese el nodo de inicio: ");
            Optional<String> node = tx.showAndWait();
            
            //text input
            tx.setContentText("Ingrese el nodo final: ");
            Optional<String> node_end = tx.showAndWait();
            
            //se obtiene lo de los text input
            int opc = Integer.parseInt(node.get());
            int opc_end = Integer.parseInt(node_end.get());
            
            //se imprime el vector que retorna la función dijkstra
            String c[] = grafo.dijkstra(opc, opc_end);
            alert.setContentText(c[1] + "\n" + c[0]);
            alert.showAndWait(); 
        }
        
        //kruskall
        else if (metodo.equals("Kruskall")) {
            
            //kruskal es una clase a parte, con su propio grafo
            //por eso se debe instanciar aquí con el archivo de texto cargado
            kruskal = new Kruskal(cant_nodos, cant_aristas);

            
            for (int i = 0; i < cant_aristas; i++) {
                kruskal.iniciar(lista[i].get(0), lista[i].get(1), lista[i].get(2));
            }
            
            //se imprime la cadena que se obtiene del método kruskalMST
            String c = kruskal.KruskalMST();
            
            alert.setContentText(c);
            alert.showAndWait();
        }
        
    }
    
    
    //inicializador del frame, esto es lo primero que se ejecutará antes de iniciar
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert.setTitle("Información");
        alert.setHeaderText(null);
        //se rellena la lista vectorial en la que almacenamos los datos del archivo
        //de texto
        for (int i = 0; i < 100; i++) {
            lista[i] = new ArrayList<Integer>();
        }
        
        tx.setTitle("Información");
        tx.setHeaderText(null);
        
        //se añaden al combo box los métodos
        cbRecorrido.getItems().add("Bfs");
        cbRecorrido.getItems().add("Dfs");
        cbRecorrido.getItems().add("Dijkstra");
        cbRecorrido.getItems().add("Kruskall");


    }    
    
}
