/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class GameSetUpController implements Initializable {
    
    @FXML
    private Label redAntBrainPath;
    @FXML
    private Label blackAntBrainPath;
    @FXML
    private Label worldPath;
    @FXML
    private GridPane canvasPane;

    
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
    
    @FXML
    public void openFileChooser(ActionEvent event) {
        System.out.println("Please locate your ant brain");
        
        Button theButton = (Button) event.getSource();
        
        FileChooser fileChooser = new FileChooser();
        
        //Show file dialog
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        
        // Update the correct label with file path
        switch (theButton.getId()) {
            case "loadRedBrain":
                redAntBrainPath.setText(file.getAbsolutePath());
                break;
            case "loadBlackBrain":
                blackAntBrainPath.setText(file.getAbsolutePath());
                break;
            case "loadWorld":
                worldPath.setText(file.getAbsolutePath());
                break;
        }
        
    }
    
    @FXML
    public void startGame(ActionEvent event) throws IOException {
        System.out.println("Start Game");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Canvas canvas = new Canvas(300,300);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.GREY);
        
        gc.fillRect(0, 0, 300, 300);
        
        canvasPane.add(canvas, 1, 1);
        
        
    }    
}
