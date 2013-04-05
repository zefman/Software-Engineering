/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import static ant.game.Cell.Type.BLACKANTHILL;
import static ant.game.Cell.Type.FOOD;
import static ant.game.Cell.Type.REDANTHILL;
import static ant.game.Cell.Type.ROCKY;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox redBrainSelect;
    @FXML
    private ComboBox blackBrainSelect;
   
    private FadeTransition fadeTransition;
    
    private Path worldPath;
    private Path redProfilePath;
    private Path blackProfilePath;
    
    private ObservableList<String> redBrainNames = FXCollections.observableArrayList();
    private ObservableList<String> blackBrainNames = FXCollections.observableArrayList();
    
    private Canvas canvas;
    private GraphicsContext gc;
    private World world;
    
    private Charset charset = Charset.forName("US-ASCII");
    
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
                Team redTeam = new Team(redTeamName.getText());
                Team blackTeam = new Team(blackTeamName.getText());
                world = new World(redTeam, blackTeam, worldPath);
                drawWorld();
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
        
        System.out.println("Start Game");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        GameController gameController = fxmlLoader.<GameController>getController();
        
        //Pass the new controller the teams and world
        gameController.setVariables(redTeam, blackTeam, world);
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
                        gc.fillOval(j*2, i*2, 2, 2);
                        break;
                    case FOOD:
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(j*2, i*2, 2, 2);
                        break;
                    case REDANTHILL:
                        gc.setFill(Color.RED);
                        gc.fillOval(j*2, i*2, 2, 2);
                        break;
                    case BLACKANTHILL:
                        gc.setFill(Color.BLACK);
                        gc.fillOval(j*2, i*2, 2, 2);
                        break;
                }
                
            }
        }
    }
    
    @FXML
    public void createProfileRed(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfile.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        CreateProfileController profileController = fxmlLoader.<CreateProfileController>getController();
        
        //Pass the new controller this scene so it canbe returned to if needed
        profileController.setVariables(node.getScene(), this, true);
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void createProfileBlack(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateProfile.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        CreateProfileController profileController = fxmlLoader.<CreateProfileController>getController();
        
        //Pass the new controller this scene so it canbe returned to if needed
        profileController.setVariables(node.getScene(), this, false);
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void loadRedProfile(ActionEvent event) throws IOException {
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
        
        //If a file was chosen, set the red profile path
        if (file != null) {
            setRedProfilePath(Paths.get(file.toURI()));
        }
    }
    
    @FXML
    public void loadBlackProfile(ActionEvent event) throws IOException {
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
        
        //If a file was chosen, set the red profile path
        if (file != null) {
            setBlackProfilePath(Paths.get(file.toURI()));
        }
    }
    
    public void setRedProfilePath(Path path) throws IOException {
        redProfilePath = path;
        System.out.println(redProfilePath.toString());
        
        List<String> theProfile = Files.readAllLines(redProfilePath, charset);
        redTeamName.setText(theProfile.get(0).replaceFirst("#name# ", ""));

        //Update the select brain combobox
        
        for (int i = 0; i < theProfile.size(); i++) {
            if (theProfile.get(i).startsWith("&")) {
                redBrainNames.add(theProfile.get(i).replaceAll("&", ""));
            }
        }
        redBrainSelect.setItems(redBrainNames);
        redBrainSelect.setOpacity(1.0);
        //Update the message label
        statusLabel.setText("Red profile loaded.");
        statusLabel.setTextFill(Color.BLACK);
        fadeTransition.play();
    }
    
    public void setBlackProfilePath(Path path) throws IOException {
        blackProfilePath = path;
        System.out.println(blackProfilePath.toString());
        
        List<String> theProfile = Files.readAllLines(blackProfilePath, charset);
        blackTeamName.setText(theProfile.get(0).replaceFirst("#name# ", ""));

        //Update the select brain combobox
        for (int i = 0; i < theProfile.size(); i++) {
            if (theProfile.get(i).startsWith("&")) {
                blackBrainNames.add(theProfile.get(i).replaceAll("&", ""));
            }
        }
        blackBrainSelect.setItems(blackBrainNames);
        blackBrainSelect.setOpacity(1.0);
        //Update the message label
        statusLabel.setText("Black profile loaded.");
        statusLabel.setTextFill(Color.BLACK);
        fadeTransition.play();
        
    }
    
    @FXML
    public void createRedBrain(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BrainEditor.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        BrainEditorController brainEditorController = fxmlLoader.<BrainEditorController>getController();
        
        //Pass the new controller this scene so it canbe returned to if needed
        brainEditorController.setVariables(node.getScene(), this, true);
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void createBlackBrain(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BrainEditor.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        BrainEditorController brainEditorController = fxmlLoader.<BrainEditorController>getController();
        
        //Pass the new controller this scene so it canbe returned to if needed
        brainEditorController.setVariables(node.getScene(), this, false);
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        canvas = new Canvas(260,260);
        gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.GREY);
        
        gc.fillRect(0, 0, 260, 260);
        
        canvasPane.add(canvas, 1, 1);
        
        fadeTransition = new FadeTransition(Duration.seconds(2), statusLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);
        
    }    
}
