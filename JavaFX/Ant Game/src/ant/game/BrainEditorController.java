/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class BrainEditorController implements Initializable {
    
    @FXML
    private Button backButton;
    
    private Scene previousScene;
    private boolean isRed;
    
    public void setVariables(Scene previousScene, GameSetUpController gameSetupController, boolean isRed) {
        this.previousScene = previousScene;
        this.isRed = isRed;
        
        backButton.setText("Back");
    }

    @FXML
    public void backToMainMenu(ActionEvent event) throws IOException {
        if (previousScene == null) {
            System.out.println("Back to main menu");
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Back to main menu");
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        }
        
    }
    
    @FXML
    public void loadBrain(ActionEvent event) {
        //Show a file chooser
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.world)", "*.world");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = null;
        try {
            file = fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            // No file choosen do nothing
        }
        
        //If a file was choosen set the current brain path
        if (file != null) {
            //currentBrain = Paths.get(file.toURI());
            
            //brainPath.setText("Brain Path: " + currentBrain.toString());
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
