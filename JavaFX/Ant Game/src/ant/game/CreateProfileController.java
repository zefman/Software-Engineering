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
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private Path theProfile;
    private FadeTransition fadeTransition;
    private Charset charset = Charset.forName("US-ASCII");
    
    @FXML
    private TextField teamName;
    @FXML
    private Label brainPath;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField brainName;
    @FXML
    private ListView brainListView;
    
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
            
            //Add the brain name to the observable list
            brainNames.add(brainName.getText());
            
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
    
    //Method to delete brains stored in the profile
    @FXML
    public void deleteBrain(ActionEvent event) throws IOException {
        String theBrainName = (String) brainListView.getFocusModel().getFocusedItem();
        
        //If the there is a selected item, search through the file to find it
        if (theBrainName != null) {
            //Read in the file 
            List<String> oldFile = Files.readAllLines(tempFile, charset);
            
            boolean found = false;
            int currentLocation = 0;
            //Search for the brain name in the list
            while (found == false && currentLocation < oldFile.size()) {
                if (oldFile.get(currentLocation).contains("&"+theBrainName+"&")) {
                    found = true;
                    System.out.println("Brain found at " + currentLocation);
                } else {
                    System.out.println("Searched " + currentLocation);
                    currentLocation++;  
                }
            }
            
            //Start deleting from the start point of the brain until another brain is found
            //Or the end is reached
            oldFile.remove(currentLocation);
            boolean endOfBrain = false;
            while (endOfBrain == false) {
                //If not at the end of the file, and the currentLocation isn't the start of another brain
                // Delete it
                if (currentLocation < oldFile.size() && !oldFile.get(currentLocation).startsWith("&")) {
                    oldFile.remove(currentLocation);
                    System.out.println("Line removed");
                } else {
                    endOfBrain = true;
                }
            }
            
            //Update the temp file
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile, charset)) {
                for (String string : oldFile) {
                    writer.write(string, 0, string.length());
                    writer.newLine();
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
            
            //Remove the brain name from the observable list
            brainNames.remove(brainListView.getFocusModel().getFocusedIndex());
            
            //Update the message label
            messageLabel.setText("Brain Deleted.");
            messageLabel.setTextFill(Color.BLACK);
            fadeTransition.play();
        }
    }
    
    @FXML
    public void saveProfile(ActionEvent event) {
        if (teamName.getText().equals("")) {
            messageLabel.setText("Please enter a team name.");
            messageLabel.setTextFill(Color.RED);
            fadeTransition.play();
        } else if (tempFile == null) {
            messageLabel.setText("Please add at least one brain.");
            messageLabel.setTextFill(Color.RED);
            fadeTransition.play();
        } else {
            //Show a file chooser
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.profile)", "*.profile");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = null;
            try {
                file = fileChooser.showSaveDialog(stage);
            } catch (Exception e) {
                // No file choosen do nothing
                System.out.println("SSome kind of exception");
            }
            
            //If a file was chosen
            if (file != null) {
                theProfile = Paths.get(file.toURI());
                try {
                    //Copy the temp file to the new profile
                    Files.copy(tempFile, theProfile);
                } catch (IOException ex) {
                    Logger.getLogger(CreateProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @FXML
    public void loadProfile(ActionEvent event) throws IOException {
        //Show a file chooser
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.profile)", "*.profile");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = null;
        try {
            file = fileChooser.showOpenDialog(stage);
        } catch (Exception e) {
            // No file choosen do nothing
        }
        
        //If a file was choosen copy the file to the temp profile
        if (file != null) {
            //If the temp file hasn't already been created create one
            if (tempFile == null) {
                //Create a temporary file to store the profile while it is being worked on
                tempFile = Files.createTempFile("tempProfile", ".profile");
                
                // Delete the temp file once done
                tempFile.toFile().deleteOnExit();
            }
            
            Path originalProfile = Paths.get(file.toURI());
            try {
                //Copy the temp file to the new profile
                Files.copy(originalProfile, tempFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(CreateProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Then look get the team name and brains
            List<String> theProfile = Files.readAllLines(tempFile, charset);
            teamName.setText(theProfile.get(0).replaceFirst("#name# ", ""));
            
            for (int i = 0; i < theProfile.size(); i++) {
                if (theProfile.get(i).startsWith("&")) {
                    brainNames.add(theProfile.get(i).replaceAll("&", ""));
                }
            }
            //Update the message label
            messageLabel.setText("Profile loaded.");
            messageLabel.setTextFill(Color.BLACK);
            fadeTransition.play();
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
        
        brainListView.setItems(brainNames);
    }    
}
