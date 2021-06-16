
/*|———————————————————————————————————————————————————————|
* |          Proyecto final estructura de datos           |
* |                       Grafos                          |
* |                 Inyer Marín Medina                    |
* |          Universidad Tecnológica de Pereira           |
* |                        2021                           |
* |———————————————————————————————————————————————————————|
*/


package frontend;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Inyer
 */
public class main extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        //se carga el archivo de tipado fxml, el cual contiene toda la información del fram
        Parent root = FXMLLoader.load(getClass().getResource("ventana.fxml"));
        //se crea una escena
        Scene scene = new Scene(root);
        
        //se añade un ícono a la ventana del frame
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("grafo.png")));
        //informacion del frame
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Grafos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    //main
    public static void main(String[] args) {
        launch(args);
    }
    
}
