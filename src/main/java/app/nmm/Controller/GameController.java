package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    Parent fxmlLoader;
    Stage stage;
    Scene scene;
    Pair<Integer, String> winner;
    Pair<Integer, String> loser;
    @FXML Text p1;
    @FXML Text p2;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("im in game scene");
        System.out.println(scene);
        Data data = (Data) stage.getUserData();
        System.out.println(data);
        winner = data.getWinner();
        loser = data.getLoser();
        p1.setText(winner.getValue());
        p2.setText(loser.getValue());
    }

    void setP1(Pair<Integer, String> p1){
        this.winner = p1;
    }

    void setP2(Pair<Integer, String> p2){
        this.loser = p2;
    }
}
