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
    private World world;
    
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
        canvas = new Canvas(390,390);
        gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BROWN);
        gc.setStroke(Color.BLUE);
        
        gc.fillRect(0, 0, 390, 390);
        
        
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
    /*
    public void setVariables(Team redTeam, Team blackTeam, World world) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
        
        this.world = world;
        
        redTeamName.setText(redTeam.getName());
        blackTeamName.setText(blackTeam.getName());
    }*/
    
    //Method to set variables
    public void setVariables(Team redTeam, Team blackTeam, World world) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
        
        this.world = world;
        
        redTeamName.setText(redTeam.getName());
        blackTeamName.setText(blackTeam.getName());
        
        drawWorld();
    }
    
    //Draw world
    public void drawWorld() {
        gc.setFill(Color.BLUE);
        for (int i = 0; i < 130; i++) {
            for (int j = 0; j < 130; j++) {
                Cell cell = world.worldGrid[i*130+j];
                switch (cell.getType()) {
                    case ROCKY:
                        gc.setFill(Color.BURLYWOOD);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                    case FOOD:
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                    case REDANTHILL:
                        gc.setFill(Color.RED);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                    case BLACKANTHILL:
                        gc.setFill(Color.BLACK);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                }
                
            }
        }
    }
    
}
