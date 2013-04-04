/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import static ant.game.Cell.Type.BLACKANTHILL;
import static ant.game.Cell.Type.FOOD;
import static ant.game.Cell.Type.REDANTHILL;
import static ant.game.Cell.Type.ROCKY;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jozefmaxted
 */
public class WorldEditorController implements Initializable {
    
    private World world;
    private Canvas canvas;
    private GraphicsContext gc;
    
    @FXML
    private AnchorPane canvasPane;
    @FXML
    private ToggleButton redBrush;
    @FXML
    private ToggleButton blackBrush;
    @FXML 
    private ToggleButton rockBrush;
    @FXML
    private ToggleButton foodBrush;
    @FXML
    private ToggleButton deleteBrush;
    
    private ToggleGroup brushGroup;

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
    
    //Draw world
    public void drawWorld() {
        gc.setFill(Color.BLUE);
        for (int i = 0; i < 130; i++) {
            for (int j = 0; j < 130; j++) {
                Cell cell = world.worldGrid[i*130+j];
                switch (cell.getType()) {
                    case ROCKY:
                        gc.setFill(Color.BURLYWOOD);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                    case FOOD:
                        gc.setFill(Color.YELLOW);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                    case REDANTHILL:
                        gc.setFill(Color.RED);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                    case BLACKANTHILL:
                        gc.setFill(Color.BLACK);
                        gc.fillOval(j*3, i*3, 3, 3);
                        break;
                }
                
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Create base world
        Team redTeam = new Team("Base team");
        Team blackTeam = new Team("Base team");
        world = new World(redTeam, blackTeam);
        
        canvas = new Canvas(400,400);
        gc = canvas.getGraphicsContext2D();
        
        
        gc.setFill(Color.BROWN);
        gc.setStroke(Color.BLUE);
        
        gc.fillRect(0, 0, 390, 390);
        
        //Experimenting with clicking in the canvas, will be useful for the world editor
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                //Get the worldGrid coordinates
                int x = (int)t.getX()/3;
                int y = (int)t.getY()/3;
                System.out.println("Testing " + x + " " + y);
                
                //get the selected brush
                if (redBrush.isSelected()) {
                    System.out.println("Placing red ant hill");
                    world.generateAntHill(y, x, "red");
                } else if (blackBrush.isSelected()) {
                    System.out.println("Placing black ant hill");
                    world.generateAntHill(y, x, "black");
                } else if (rockBrush.isSelected()) {
                    System.out.println("Placing rock");
                    switch (world.worldGrid[y*130+x].getType()) {
                        case CLEAR:
                            world.worldGrid[y*130+x].setType(ROCKY); 
                            break;
                        default:
                            //Do nothing as a rock can't be placed here
                            break;
                    }
                } else if (foodBrush.isSelected()) {
                    System.out.println("Placing food");
                    world.generateFoodBlocks(y, x);
                } else if (deleteBrush.isSelected()) {
                    System.out.println("Deleting");
                }
                
                drawWorld();
            }
        });
        
        canvasPane.setLeftAnchor(canvas, 50.0);
        canvasPane.setTopAnchor(canvas, 0.0);
        canvasPane.getChildren().add(canvas);
        
        //Set the groups for the brush toggle buttons
        brushGroup = new ToggleGroup();
        redBrush.setToggleGroup(brushGroup);
        blackBrush.setToggleGroup(brushGroup);
        rockBrush.setToggleGroup(brushGroup);
        foodBrush.setToggleGroup(brushGroup);
        deleteBrush.setToggleGroup(brushGroup);
        
        drawWorld();
    }    
}
