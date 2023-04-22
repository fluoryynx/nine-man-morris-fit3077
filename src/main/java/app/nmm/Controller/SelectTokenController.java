package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.Random;

import java.io.IOException;
import java.util.ResourceBundle;

public class SelectTokenController implements Initializable {

    Parent fxmlLoader;
    Stage stage;
    Scene scene;
    @FXML
    TextField player1Field;
    @FXML
    TextField player2Field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("im in select token");
        System.out.println(scene);
        System.out.println(fxmlLoader);
    }

    @FXML
    void backToMain(MouseEvent event) throws IOException {
        fxmlLoader = FXMLLoader.load(Application.class.getResource("main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.setTitle("Nine Man's Morris");
        stage.show();
    }

    @FXML
    void flipCoin(MouseEvent event) throws IOException{
        String str1 = player1Field.getText();
        String str2 = player2Field.getText();
        Random r = new Random();
        int headOrTail = r.nextInt(100) + 1;
        Pair<Integer, String> winner = null;
        Pair<Integer, String> loser = null;
        if (headOrTail >= 50) {
            winner = new Pair<>(1, "p1");
            loser = new Pair<>(2, "p2");
        }
        else{
            winner = new Pair<>(1, "p2");
            loser = new Pair<>(2, "p1");
        }
        System.out.println(fxmlLoader);
        changeToGameScene(winner, loser, event);
    }

    @FXML
    void swap(MouseEvent event) throws IOException{
        // how to swap scene
        String str1 = player1Field.getText();
        String str2 = player2Field.getText();
        player1Field.setText(str2);
        player2Field.setText(str1);
    }

    void changeToGameScene(Pair<Integer, String> winner, Pair<Integer, String> loser, MouseEvent event) throws IOException {
        Data data = new Data(winner, loser);
        System.out.println(data);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fxmlLoader =  FXMLLoader.load(Application.class.getResource("board.fxml"));
        stage.setUserData(data);
        scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.setTitle("Nine Man's Morris");
        stage.show();
    }
}
