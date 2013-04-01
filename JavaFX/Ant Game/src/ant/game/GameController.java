/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class GameController implements Initializable {
   
    
    @FXML
    AnchorPane canvasPane;
    @FXML
    Label redTeamName;
    @FXML
    Label blackTeamName;
    
    private Team redTeam;
    private Team blackTeam;
    
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean test = false;
    private AnimationTimer animationTimer;
    private Random r;
    
    @FXML
    public void backToMainMenu(ActionEvent event) throws IOException {
        System.out.println("Back to main menu");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        canvas = new Canvas(380,380);
        gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BROWN);
        gc.setStroke(Color.BLUE);
        
        gc.fillRect(0, 0, 380, 380);
        
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
        
        animationTimer = new AnimationTimer() {

                @Override public void handle(long now) {
                    updateCanvas();
                }
            };
        
        
        //Experimenting with clicking in the canvas, will be useful for the world editor
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                System.out.println("Testing " + t.getSceneX() + " " + t.getSceneY());
                gc.fillOval(t.getSceneX(), t.getSceneY(), 5, 5);
                if (test == true) {
                    test = false;
                    animationTimer.stop();
                } else {
                    test = true;
                    gc.setFill(Color.BROWN);
                    gc.fillRect(0, 0, 380, 380);
                    gc.setFill(Color.BLACK);
                    animationTimer.start();                    
                }
            }
        });
        
        canvasPane.setLeftAnchor(canvas, 15.0);
        canvasPane.getChildren().add(canvas);
        
    }    
    
    public void updateCanvas() {
        double tester = 0 + (int)(Math.random() * ((380 - 0) + 1));
        double tester2 = 0 + (int)(Math.random() * ((380 - 0) + 1));
        gc.fillRect(tester, tester2, 30, 30);
    }
    
    //Method to set variables
    public void setVariables(Team redTeam, Team blackTeam) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
        
        redTeamName.setText(redTeam.getName());
        blackTeamName.setText(blackTeam.getName());
    }
    
}
