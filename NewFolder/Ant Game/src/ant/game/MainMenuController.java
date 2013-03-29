/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *s
 * @author jozefmaxted
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("Game Started");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GameSetUp.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    private void startTournament(ActionEvent event) throws IOException {
        System.out.println("Tournament started");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("TournamentSetUpView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    private void openBrainEditor(ActionEvent event) throws IOException {
        System.out.println("Opening the brain editor");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("BrainEditor.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    private void openWorldEditor(ActionEvent event) {
        System.out.println("Opening the world editor");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
