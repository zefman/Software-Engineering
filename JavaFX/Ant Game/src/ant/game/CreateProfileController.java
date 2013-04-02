/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class CreateProfileController implements Initializable {
    
    private Scene previousScene;
    private ObservableList<String> brainNames = FXCollections.observableArrayList();
    private Path tempFile;
    private Path currentBrain;
    private FadeTransition fadeTransition;

    @FXML
    private TextField teamName;
    @FXML
    private Label brainPath;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField brainName;
    
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
        
        if (brainName.getText().equals("")) {
            messageLabel.setText("Please give a brain name.");
            messageLabel.setTextFill(Color.RED);
            fadeTransition.play();
        } else if (currentBrain == null) {
            messageLabel.setText("Please create or load a brain.");
            messageLabel.setTextFill(Color.RED);
            fadeTransition.play();
        } else {
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

            //Read the current contents of the file into a List
            List<String> lines = new ArrayList<String>();

            //Add the team name to the begining of the string list
            lines.add(0, "#name# " + teamName.getText());

            //Put the old file in a list of strings
            List<String> oldFile = Files.readAllLines(tempFile, charset);

            //Remove name if it is already there
            if (!oldFile.isEmpty() && oldFile.get(0).startsWith("#name#")) {
                oldFile.remove(0);
            }

            //Now check to see if the old file contains win stats, if not add them to the new string list
            if (oldFile.isEmpty() || !oldFile.get(0).startsWith("#wins#")) {
                lines.add("#wins#");
                lines.add("#losses#");
                lines.add("#draws#");
            }

            //Add the old file to the new string list
            lines.addAll(oldFile);

            //Add new brain name after gap
            lines.add("");
            lines.add("&" + brainName.getText() + "&");
            
            //Add brain to end
            lines.addAll(Files.readAllLines(currentBrain, charset));
            
            //Reset brainName textfield and currentBrain Path
            brainName.setText("");
            currentBrain = null;

            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, charset)) {
                for (String string : lines) {
                    writer.write(string, 0, string.length());
                    writer.newLine();
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }

            try (BufferedReader reader = Files.newBufferedReader(tempFile, charset)) {
                String line2 = null;
                while ((line2 = reader.readLine()) != null) {
                    System.out.println(line2);
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
        
    }
    
    @FXML
    public void openBrainChooser(ActionEvent event) {
        //Show a file chooser
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = null;
        try {
            file = fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            // No file choosen do nothing
        }
        
        //If a file was choosen set the current brain path
        if (file != null) {
            currentBrain = Paths.get(file.toURI());
            
            brainPath.setText("Brain Path: " + currentBrain.toString());
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
        fadeTransition = new FadeTransition(Duration.seconds(2), messageLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
    }    
}
