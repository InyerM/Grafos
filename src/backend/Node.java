/*|———————————————————————————————————————————————————————|
* |          Proyecto final estructura de datos           |
* |                       Grafos                          |
* |                 Inyer Marín Medina                    |
* |          Universidad Tecnológica de Pereira           |
* |                        2021                           |
* |———————————————————————————————————————————————————————|
*/


package backend;

/**
 *
 * @author Inyer
 */
public class Node {
    int nroNodoVisita;
    long costo;
    int previo;

    public Node(int nroNodoVisita, long costo, int previo) {
        this.nroNodoVisita = nroNodoVisita;
        this.costo = costo;
        this.previo = previo;
    }
    
    
   
    public Node(int nroNodoVisita, long costo){
        this.nroNodoVisita = nroNodoVisita;
        this.costo = costo;
    }
}
