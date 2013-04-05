/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
    @FXML
    private TextArea brainArea;
    
    private Scene previousScene;
    private boolean isRed;
    private Charset charset = Charset.forName("US-ASCII");
    
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
    public void loadBrain(ActionEvent event) throws IOException {
        //Show a file chooser
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.brain)", "*.brain");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = null;
        try {
            file = fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            // No file choosen do nothing
        }
        
        //If a file was choosen set the current brain path
        if (file != null) {
            Path currentBrain = Paths.get(file.toURI());
            
            List<String> theBrain = Files.readAllLines(currentBrain, charset);
            
            String brainString = "";
            
            for (String currentState : theBrain) {
                brainString = brainString + currentState + "\n";
            }
            
            brainArea.setText(brainString);
            
            checkBrain(theBrain);
        }
    }
    
    @FXML
    public void saveBrain(ActionEvent event) {
        System.out.println("Save brain");
        //If there is a previous scene, save the brain and pass it back to that scene
        if (previousScene == null) {
            //Just save the brain
            //Show a file chooser
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.brain)", "*.brain");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = null;
            try {
                file = fileChooser.showSaveDialog(stage);
            } catch (Exception e) {
                // No file choosen do nothing
                System.out.println("Some kind of exception");
            }
            
            //If a file was chosen
            if (file != null) {
                Path brainPath = Paths.get(file.toURI());
                try (BufferedWriter writer = Files.newBufferedWriter(brainPath, charset)) {
                    writer.write(brainArea.getText(), 0, brainArea.getText().length());
                    
                } catch (IOException x) {
                    System.err.format("IOException: %s%n", x);
                }
                
                //Go back to the previous screen and pass the profile
                /*
                if (isRed == true) {
                    gameSetupController.setRedProfilePath(theProfile);
                    stage.setScene(previousScene);
                } else {
                    gameSetupController.setBlackProfilePath(theProfile);
                    stage.setScene(previousScene);
                }*/
            }
        }
    }
    
    public void checkBrain(List<String> theBrain) {
        // Check every state
        for (int i = 0; i < theBrain.size(); i++) {
            String[] currentTokens = theBrain.get(i).toLowerCase().split(" ");
            
            //First check that the first token is one of the corrected tokens
            switch (currentTokens[0].toLowerCase()) {
                case "move":
                    //check that the next two tokens are integers representing states in the brain
                    if (Integer.parseInt(currentTokens[1]) < 0 || Integer.parseInt(currentTokens[1]) > theBrain.size()) {
                        System.out.println("State doesn't exist error on line: " + i);
                        System.out.println(theBrain.get(i));
                    }
                    
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                case "sense":
                    //Check direction is correct
                    if (!currentTokens[1].equals("ahead") && !currentTokens[1].equals("leftahead") && !currentTokens[1].equals("rightahead") && !currentTokens[1].equals("here") && !currentTokens[1].equals("rightup") && !currentTokens[1].equals("leftup")) {
                        System.out.println("Incorrect direction " + i);
                        System.out.println(theBrain.get(i));
                    }
                    //check that the next two tokens are integers representing states in the brain
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    
                    if (Integer.parseInt(currentTokens[3]) < 0 || Integer.parseInt(currentTokens[3]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    //Finally check the condition is valid
                    if (!currentTokens[4].equals("food") && !currentTokens[4].equals("marker") && !currentTokens[4].equals("home") && !currentTokens[4].equals("foehome") && !currentTokens[4].equals("friend") && !currentTokens[4].equals("friendwithfood") && !currentTokens[4].equals("foe")) {
                        System.out.println("Sense condition incorrect " + i);
                        System.out.println(theBrain.get(i));
                    } else if (currentTokens[4].equals("marker")) {
                        //Check the the final token isa number between 1 and 5
                        if (Integer.parseInt(currentTokens[5]) > 6 || Integer.parseInt(currentTokens[5]) < 0) {
                            System.out.println("Invalid marker range " + i);
                            System.out.println(theBrain.get(i));
                        }
                    }
                    break;
                case "pickup":
                    //check that the next two tokens are valid states
                    if (Integer.parseInt(currentTokens[1]) < 0 || Integer.parseInt(currentTokens[1]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                case "drop":
                    //check the next state is valid
                    if (Integer.parseInt(currentTokens[1]) < 0 || Integer.parseInt(currentTokens[1]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                case "turn":
                    //check that the left token is either left or right
                    if (!currentTokens[1].equals("left") && !currentTokens[1].equals("right")) {
                        System.out.println("Incorrect turn direction. " + i);
                        System.out.println(theBrain.get(i));
                    }
                    //Check that the next token is valid state
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                case "mark":
                    //Check that the next token is a number between 1 and six
                    if (Integer.parseInt(currentTokens[1]) < 0 || Integer.parseInt(currentTokens[1]) > 6) {
                        System.out.println("Pheromone out of range " + i);
                        System.out.println(theBrain.get(i));
                    }
                    //Check that the next token is a correct state
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                case "unmark":
                    //Check that the next token is a number between 1 and six
                    if (Integer.parseInt(currentTokens[1]) < 0 || Integer.parseInt(currentTokens[1]) > 6) {
                        System.out.println("Pheromone out of range " + i);
                        System.out.println(theBrain.get(i));
                    }
                    //Check that the next token is a correct state
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                case "flip":
                    //check the next token is a positive integer
                    if (Integer.parseInt(currentTokens[1]) < 0) {
                        System.out.println("Flip p needs to be positive " + i);
                        System.out.println(theBrain.get(i));
                    }
                    // Check the next two tokens are valid states
                    if (Integer.parseInt(currentTokens[2]) < 0 || Integer.parseInt(currentTokens[2]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    if (Integer.parseInt(currentTokens[3]) < 0 || Integer.parseInt(currentTokens[3]) > theBrain.size()) {
                        System.out.println("State doesn't exist error " + i);
                        System.out.println(theBrain.get(i));
                    }
                    break;
                default:
                    System.out.println("Syntax error found " + i);
                    System.out.println(theBrain.get(i));
            }
            
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
