package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.javatuples.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML AnchorPane mainPageScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("im in main page");
    }

    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void playerVsPlayerMode(MouseEvent event)throws IOException {
        AnchorPane tokenScene = FXMLLoader.load(Application.class.getResource("SelectToken.fxml"));
        System.out.println(mainPageScene);
        mainPageScene.getChildren().removeAll();
        mainPageScene.getChildren().setAll(tokenScene);
    }

    @FXML
    void playerVsComputerMode(MouseEvent event)throws IOException {
        // preset the winner loser data
        // winner is always Player 1
        // loser is always Player 2
        Pair<Integer, String> winner = new Pair<>(1, "p1");
        Pair<Integer, String> loser = new Pair<>(2, "computer");
        Data data = new Data (winner, loser, "computer");

        AnchorPane boardScene = FXMLLoader.load(Application.class.getResource("board.fxml"));
        System.out.println(mainPageScene);
        mainPageScene.getChildren().removeAll();
        mainPageScene.getChildren().setAll(boardScene);

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
    }

    @FXML
    void tutorialMode(MouseEvent event)throws IOException {
        // preset the winner loser data
        // winner or loser does not matter in this case
        Pair<Integer, String> winner = new Pair<>(1, "p1");
        Pair<Integer, String> loser = new Pair<>(2, "tutorial");
        Data data = new Data (winner, loser, "tutorial");

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
        mainPageScene.getChildren().removeAll();
        mainPageScene.getChildren().setAll(boardScene);
    }


}
