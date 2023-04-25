package app.nmm.Controller;

import app.nmm.Application;
import app.nmm.Data;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Actor.Computer;
import app.nmm.Logic.Actor.Player;
import app.nmm.Logic.Handler.CheckLegalMove;
import app.nmm.Logic.Handler.CheckMill;
import app.nmm.Logic.Location.Node;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
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



    private ArrayList<Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private  ArrayList<Actor> playerList;
    private ArrayList<Pair<Integer,Integer>> locationList;

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
        this.loadEngine();
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

    private void loadEngine(){
        this.playerList = new ArrayList<Actor>();
        this.nodeList =  new ArrayList<Node>();
        this.checkMill =  new CheckMill();
        this.checkLegalMove = new CheckLegalMove();
        this.locationList= new ArrayList<Pair<Integer,Integer>>();
        this.addLocation();

        // Create all the node with its specific location and id
        for(int i = 0; i < 24; i++){
            this.nodeList.add(new Node(i,this.locationList.get(i)));
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
        getUserAction(event);
    }
    @FXML
    void getUserAction(MouseEvent event) {
        currentScene = p1.getScene();
        String nodeID = ((javafx.scene.Group)event.getSource()).getId();

        if (((javafx.scene.Group)event.getSource()).getId().equals("g23")){
            putImage();
        }

        // pass id to Player class
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
                    ObservableList<javafx.scene.Node> childList =  group.getChildren();
                    // remove any extra children
                    if(childList.size() > 1){
                        while (childList.size() > 1){
                            javafx.scene.Node node =  childList.get(childList.size()-1);
                            childList.remove(node);
                        }
                    }
                    childList =  group.getChildren();
                    File file = new File("src/main/resources/Graphic/Legal_Move.png");
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
        this.playerList.add(new Player("white", p1.getText()));
        this.playerList.add(new Player("black", p2.getText()));
    }

    void pvCMode(){
        this.playerList.add(new Player("white", p1.getText()));
        this.playerList.add(new Computer("black", "Comp"));
    }

    void tutorialMode(){
        // TODO: implement tutorial mode
    }

    private void addLocation(){
        this.locationList.add(new Pair(205,186));
        this.locationList.add(new Pair(315,186));
        this.locationList.add(new Pair(425,186));
        this.locationList.add(new Pair(231,211));
        this.locationList.add(new Pair(315,211));
        this.locationList.add(new Pair(399,211));
        this.locationList.add(new Pair(257,236));
        this.locationList.add(new Pair(315,236));
        this.locationList.add(new Pair(373,236));
        this.locationList.add(new Pair(205,296));
        this.locationList.add(new Pair(231,296));
        this.locationList.add(new Pair(257,296));
        this.locationList.add(new Pair(373,296));
        this.locationList.add(new Pair(399,296));
        this.locationList.add(new Pair(425,296));
        this.locationList.add(new Pair(257,353));
        this.locationList.add(new Pair(315,353));
        this.locationList.add(new Pair(373,353));
        this.locationList.add(new Pair(231,380));
        this.locationList.add(new Pair(315,380));
        this.locationList.add(new Pair(399,380));
        this.locationList.add(new Pair(205,406));
        this.locationList.add(new Pair(315,406));
        this.locationList.add(new Pair(425,406));

    }


    public void executeAction(Action action){
        action.execute();
    }

    /**
     * This is the method that will run the game until one of the player does not have any legal moves or until one of the player have less than 3 token in the board.
     */
    public void gamePlay(){
        boolean stop = false;

        while(stop){
            for (Actor actor : this.playerList) {
                Pair result = this.checkLegalMove.calculateLegalMove(actor, this.nodeList);
                HashMap allowableActions = (HashMap) result.getKey();
                HashMap removeActions =  (HashMap) result.getValue();

                if (allowableActions.size() == 0){
                    stop = true;
                    break;
                }

                Action action = actor.playTurn(allowableActions);
                action.execute();

            }
        }
    }
}
