/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class GameController implements Initializable {
    
    @FXML
    BorderPane thePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Canvas canvas = new Canvas(300,300);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BROWN);
        gc.setStroke(Color.BLUE);
        
        gc.fillRect(0, 0, 300, 300);
        
        //Draw rocks
        gc.setFill(Color.YELLOW);
        for (int i =0; i < canvas.getWidth()/10; i++) {
            gc.fillRect(i*10, 0, 10, 10);
            gc.fillRect(i*10, canvas.getHeight() - 10, 10, 10);
            gc.fillRect(0, i*10, 10, 10);
            gc.fillRect(canvas.getWidth() - 10, i*10 - 10, 10, 10);
        }
        
        gc.setFill(Color.BLACK);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                gc.fillOval(10*i, 10*j, 5, 5);
            }
        }
        
        //Experimenting with clicking in the canvas, will be useful for the world editor
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                System.out.println("Testing " + t.getSceneX() + " " + t.getSceneY());
                gc.fillOval(t.getSceneX(), t.getSceneY(), 5, 5);
            }
        });
        
        thePane.setCenter(canvas);
    }    
}
