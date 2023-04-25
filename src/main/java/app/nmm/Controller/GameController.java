package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GameController implements Initializable {

    Pair<Integer, String> winner;
    Pair<Integer, String> loser;
    String mode;
    @FXML Text p1;
    @FXML Text p2;
    AnchorPane boardScene;
    private final boolean DEBUG = false;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("im in game scene");
        winner = Data.getWinner();
        loser = Data.getLoser();
        mode = Data.getMode();
        p1.setText(winner.getValue());
        p2.setText(loser.getValue());

        if (DEBUG == true){
            System.out.println(winner);
            System.out.println(loser);
            System.out.println(mode);
        }
        if (mode.equals("computer")){
            pvCMode();
        }
        else{
            pvpMode();
        }
    }

    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void showLegalMove(MouseEvent event){
        double x = event.getSceneX();
        double y = event.getSceneY();

        System.out.println("x: " + x + " y: " + y);
        System.out.println(((javafx.scene.Group)event.getSource()).getId());
    }

    @FXML
    void showHint(){
        // TODO: implement hint function
        System.out.println("No hint yet");
    }

    void pvpMode(){
        // TODO: implement player vs player engine
    }

    void pvCMode(){
        // TODO: implement player vs computer mode engine
    }

    void tutorialMode(){
        // TODO: implement tutorial mode
    }
}
