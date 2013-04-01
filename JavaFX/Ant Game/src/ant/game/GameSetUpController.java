/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class GameSetUpController implements Initializable {
    
    @FXML
    private Label statusLabel;
    @FXML
    private GridPane canvasPane;
    @FXML
    private TextField redTeamName;
    @FXML
    private TextField blackTeamName;
    
    private FadeTransition fadeTransition;
    
    private Path worldPath;

    
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
        
        File file = null;
        
        
        try {
            file = fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            // No file choosen do nothing
        }
        
        // Update the correct label with file path if there is one and the button has an id
        if (file != null & theButton.getId() != null) {
            switch (theButton.getId()) {
            case "loadRedBrain":
                updateStatusLabel("Loaded red ant brain.");
                break;
            case "loadBlackBrain":
                updateStatusLabel("Loaded black ant brain.");
                break;
            case "loadWorld":
                updateStatusLabel("Loaded world.");
                worldPath = Paths.get(file.toURI());
                break;
            default:
                updateStatusLabel("Something Loaded.");
            }
        }
        
        
    }
    
    private void updateStatusLabel(String theText) {
        statusLabel.setText(theText);
        fadeTransition.setRate(1.0);
        fadeTransition.play();
    }
    
    @FXML
    public void startGame(ActionEvent event) throws IOException {
        //Create teams based on choosen settings
        Team redTeam = new Team(redTeamName.getText());
        Team blackTeam = new Team(blackTeamName.getText());
        World world = new World(redTeam, blackTeam, worldPath);
        
        System.out.println("Start Game");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        GameController gameController = fxmlLoader.<GameController>getController();
        
        //Pass the new controller the teams and world
        gameController.setVariables(redTeam, blackTeam);
        
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
        
        fadeTransition = new FadeTransition(Duration.seconds(2), statusLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        
    }    
}
