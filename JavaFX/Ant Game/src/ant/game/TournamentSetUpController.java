/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class TournamentSetUpController implements Initializable {
    
    private ObservableList<String> teamNames = FXCollections.observableArrayList();
    private FadeTransition detailsTransition;
    private FadeTransition teamsTransition;
    @FXML
    private ListView teamListView;
    @FXML
    private AnchorPane teamDetailPane;
    @FXML
    private TextField teamName;
    
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
    public void addTeam(ActionEvent event) {
        teamDetailPane.setOpacity(0.0);
        teamListView.setOpacity(0.0);
        
        teamNames.add(teamName.getText());
        detailsTransition.play();
        teamsTransition.play();
        
        //Reset team details
        teamName.setText("");
        
    }
    
    @FXML
    public void deleteTeam(ActionEvent event) {
        //If there are added teams, remove the selected one
        if (teamListView.getFocusModel().getFocusedIndex() > -1) {
           teamListView.setOpacity(0.0);
           teamNames.remove(teamListView.getFocusModel().getFocusedIndex());
           teamsTransition.play();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        teamListView.setItems(teamNames);
        
        //Set up fade transition
        detailsTransition = new FadeTransition(Duration.seconds(0.7), teamDetailPane);
        detailsTransition.setFromValue(0.0);
        detailsTransition.setToValue(1.0);
        
        teamsTransition = new FadeTransition(Duration.seconds(0.7), teamListView);
        teamsTransition.setFromValue(0.0);
        teamsTransition.setToValue(1.0);

    }    
}
