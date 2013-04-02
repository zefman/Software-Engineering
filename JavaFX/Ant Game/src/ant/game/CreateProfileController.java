/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class CreateProfileController implements Initializable {
    
    private Scene previousScene;
    private ObservableList<String> brainNames = FXCollections.observableArrayList();
    private Path tempFile;

    @FXML
    public void back(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(previousScene);
        stage.show();
    }
    
    @FXML
    /*
     *
     */
    public void addBrain(ActionEvent event) throws IOException {
        System.out.println("Adding a brain");
        //Fidn the path of the jar
        String path = CreateProfileController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        
        //If the temp file hasn't already been created create one
        if (tempFile == null) {
            //Create a temporary file to store the profile while it is being worked on
            tempFile = Files.createTempFile("tempProfile", ".profile");
            System.out.println("Temp file " + tempFile);
            // Delete the temp file once done
            tempFile.toFile().deleteOnExit();
        }
        
        Charset charset = Charset.forName("US-ASCII");
        String s = "This is a test";

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        try (BufferedReader reader = Files.newBufferedReader(tempFile, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        
    }
    
    public void setVariables(Scene previousScene) {
        this.previousScene = previousScene;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
