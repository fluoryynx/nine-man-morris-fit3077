package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.javatuples.Pair;

import java.net.URL;
import java.util.Random;

import java.io.IOException;
import java.util.ResourceBundle;

public class SelectTokenController implements Initializable {

    @FXML
    Text player1Field;
    @FXML
    Text player2Field;
    @FXML
    AnchorPane tokenScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("im in select token");
    }

    @FXML
    void backToMain(MouseEvent event) throws IOException {
        AnchorPane mainPageScene = FXMLLoader.load(Application.class.getResource("main.fxml"));
        tokenScene.getChildren().removeAll();
        tokenScene.getChildren().setAll(mainPageScene);
    }

    @FXML
    void flipCoin(MouseEvent event) throws IOException{
        // flip coin method, got a bit of bug
        String str1 = player1Field.getText();
        String str2 = player2Field.getText();
        Random r = new Random();
        int headOrTail = r.nextInt(10) + 1;
        System.out.println(headOrTail);
        Pair<Integer, String> winner = null;
        Pair<Integer, String> loser = null;
        if (headOrTail > 5) {
            // head wins
            if (str1.equals("Heads")){
                winner = new Pair<>(1, "p1");
                loser = new Pair<>(2, "p2");
            }
            else {
                winner = new Pair<>(1, "p2");
                loser = new Pair<>(2, "p1");
            }
        }
        else{
            if (str1.equals("Tails")){
                winner = new Pair<>(1, "p1");
                loser = new Pair<>(2, "p2");
            }
            else {
                winner = new Pair<>(1, "p2");
                loser = new Pair<>(2, "p1");
            }
        }
        changeToGameScene(winner, loser, event);
    }

    @FXML
    void swap(MouseEvent event) throws IOException{
        // swaps the head and tail between two players
        String str1 = player1Field.getText();
        String str2 = player2Field.getText();
        player1Field.setText(str2);
        player2Field.setText(str1);
    }

    void changeToGameScene(Pair<Integer, String> winner, Pair<Integer, String> loser, MouseEvent event) throws IOException {
        // saves the data in a data static object and changes scene
        Data data = new Data(winner, loser, "player");
        System.out.println(data);
        AnchorPane boardScene = FXMLLoader.load(Application.class.getResource("board.fxml"));
        Group group =  (Group) boardScene.lookup("#overlay");

        for (Node node: group.getChildren()){
            String id  = node.getId();
            if ( id != null && id.equals("whitePlayerID")){
                ((Text) node).setText(winner.getValue1());
            }
            else if (id != null && id.equals("blackPlayerID")){
                ((Text) node).setText(loser.getValue1());
            }
        }
        // set the text inside the Text node
       ((Text)boardScene.lookup("#p1")).setText(winner.getValue1());
        ((Text)boardScene.lookup("#p2")).setText(loser.getValue1());
        tokenScene.getChildren().removeAll();
        tokenScene.getChildren().setAll(boardScene);
    }
}
