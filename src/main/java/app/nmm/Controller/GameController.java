package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class GameController implements Initializable {

    Pair<Integer, String> winner;
    Pair<Integer, String> loser;
    String mode;
    @FXML Text p1;
    @FXML Text p2;
    AnchorPane boardScene;
    Scene currentScene;
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

        if (DEBUG){
            System.out.println(winner);
            System.out.println(loser);
            System.out.println(mode);
        }
        if (mode.equals("computer")){
            pvCMode();
        }
        else if (mode.equals("player")){
            pvpMode();
        }
        else {
            tutorialMode();
        }
    }

    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void getUserAction(MouseEvent event) {
        currentScene = p1.getScene();

        if (DEBUG){
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println("x: " + x + " y: " + y);
            System.out.println(((javafx.scene.Group)event.getSource()).getId());
            System.out.println(currentScene);
        }
        // pass id to Player class

        // get back then put image
        if (((javafx.scene.Group)event.getSource()).getId().equals("g23")){
            putImage();
        }
    }

    /**
     *
     */
    @FXML
    public void putImage() {
        // manually generate the location beside
        Map<Integer, ArrayList<Integer>> nodeIdList = new HashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(14);
        list.add(22);
        nodeIdList.put(23, list);

        Group group;
        ObservableList<Node> childList;
        // add image
        // iterate each entry of hashmap
        for(Map.Entry<Integer, ArrayList<Integer>> entry: nodeIdList.entrySet()) {

            // if give value is equal to value from entry
            // print the corresponding key
            if(entry.getKey() == 23) {
                ArrayList<Integer> temptList = entry.getValue();

                for (int i = 0; i < temptList.size();i++){
                    int intId = temptList.get(i);
                    group = (Group) currentScene.lookup("#g"+intId);
                    childList =  group.getChildren();
                    // remove any extra children
                    if(childList.size() > 1){
                        while (childList.size() > 1){
                            Node node =  childList.get(childList.size()-1);
                            childList.remove(node);
                        }
                    }
                    File file = new File("project\\src\\main\\resources\\Graphic\\Legal_Move.png");
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    imageView.setFitWidth(24);
                    imageView.setFitHeight(24);
                    imageView.setLayoutX(-3);
                    imageView.setLayoutY(-3);
                    childList.add(imageView);
                }
            }
        }
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
