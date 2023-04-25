package app.nmm.Controller;

import app.nmm.Data;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Actor.Computer;
import app.nmm.Logic.Actor.Player;
import app.nmm.Logic.Capability.Capability;
import app.nmm.Logic.Handler.CheckLegalMove;
import app.nmm.Logic.Handler.CheckMill;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.File;
import java.net.URL;
import java.util.*;


public class GameController implements Initializable {

    Pair<Integer, String> winner;
    Pair<Integer, String> loser;
    String mode;
    @FXML Text p1;
    @FXML Text p2;
    AnchorPane boardScene;
    Scene currentScene;
    @FXML
    Button startButton;
    private final boolean DEBUG = false;

    private ArrayList<app.nmm.Logic.Location.Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private  ArrayList<Actor> playerList;
    private ArrayList<Pair<Integer,Integer>> locationList;

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

        if (DEBUG){
            System.out.println(winner);
            System.out.println(loser);
            System.out.println(mode);
        }
    }

    void loadEngine(){
        this.playerList = new ArrayList<Actor>();
        this.nodeList =  new ArrayList<app.nmm.Logic.Location.Node>();
        this.checkMill =  new CheckMill();
        this.checkLegalMove = new CheckLegalMove();
        this.locationList= new ArrayList<Pair<Integer,Integer>>();
        this.addLocation();
        // Create all the node with its specific location and id
        for(int i = 0; i < 24; i++){
            this.nodeList.add(new app.nmm.Logic.Location.Node(i, this.locationList.get(i)));
        }

    }

    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void startGame(MouseEvent event){
        currentScene = startButton.getScene();
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
    public void putImage(int nodeId, Action action, Actor actor) {
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
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
        imageView.setId("legalMove");
        imageView.setOnMouseClicked(event -> {
            System.out.println("IM HERE");
            if (actor.getTokenColour().equals("white")){
                player1PutToken(action, actor);
            }
            else{
                player2PutToken(action, actor);
            }

        });
    }

    @FXML
    void showHint(){
        // TODO: implement hint function
        System.out.println("No hint yet");
    }

    void pvpMode(){
        //Add Players into the game
        Player player1 = new Player("white", p1.getText());
        Player player2 = new Player("black", p2.getText());
        this.playerList.add(player1);
        this.playerList.add(player2);
        System.out.println(playerList);
        // TODO: implement player vs player engine
        player1ShowLegalPut();
    }

    void pvCMode(){
        //Add Player and Computer into the game
        this.playerList.add(new Player("white", p1.getText()));
        this.playerList.add(new Computer("black", "Comp"));
        // TODO: implement player vs computer mode engine
    }

    void tutorialMode(){
        // TODO: implement tutorial mode
        System.out.println("Hi");
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

    @FXML
    void player1ShowLegalPut(){
        List<Action> returnAction = this.checkLegalMove.calculateLegalPut(nodeList);
        System.out.println(returnAction);
        for (int i = 0; i < returnAction.size(); i++){
            int id = returnAction.get(i).getNodeId();
            putImage(id, returnAction.get(i), this.playerList.get(0));
        }
    }

    void player1PutToken(Action action, Actor actor){
        for (int i = 0; i < 24; i++){
            Group group = (Group) currentScene.lookup("#g"+i);
            ObservableList<Node> childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }
        action.execute(actor, nodeList);
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        File file = new File("project\\src\\main\\resources\\Graphic\\White_Token.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        imageView.setLayoutX(-3);
        imageView.setLayoutY(-3);
        imageView.setId(actor.getTokenColour().charAt(0) + "token"+ (9 - actor.getNumberOfTokensInHand()));
        childList.add(imageView);
        if (actor.getNumberOfTokensInHand() == 0){
            actor.updateStatus(Capability.NORMAL);
        }
        player2ShowLegalPut();
    }

    @FXML
    void player2ShowLegalPut(){
        List<Action> returnAction = this.checkLegalMove.calculateLegalPut(nodeList);
        System.out.println(returnAction);
        for (int i = 0; i < returnAction.size(); i++){
            int id = returnAction.get(i).getNodeId();
            putImage(id, returnAction.get(i), this.playerList.get(1));
        }
    }

    void player2PutToken(Action action, Actor actor){
        for (int i = 0; i < 24; i++){
            Group group = (Group) currentScene.lookup("#g"+i);
            ObservableList<Node> childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }
        action.execute(actor, nodeList);
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        File file = new File("project\\src\\main\\resources\\Graphic\\Black_Token.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        imageView.setLayoutX(-3);
        imageView.setLayoutY(-3);
        imageView.setId(actor.getTokenColour().charAt(0) + "token" + (9 - actor.getNumberOfTokensInHand()));
        childList.add(imageView);
        if (actor.getNumberOfTokensInHand()==0){
            actor.updateStatus(Capability.NORMAL);
        }
        if (actor.getStatus()==Capability.PUT_TOKEN){
            player1ShowLegalPut();
        }
        else{
            for (int i = 0; i < 24; i++){
                group = (Group) currentScene.lookup("#g"+i);
                childList =  group.getChildren();
                Node node =  childList.get(childList.size()-1);
                if (node.getId().equals("legalMove")){
                    childList.remove(node);
                }
            }
            System.out.println("DONE");
//            player1ShowLegalMove();
        }

    }

//    void player1ShowLegalMove(){
//        Map<Integer, List<Action>> returnAction = this.checkLegalMove.calculateLegalMove(this.playerList.get(0),nodeList);
//        System.out.println(returnAction);
//        for (int i = 0; i < returnAction.size(); i++){
//
//            putImage(id, returnAction.get(i), this.playerList.get(0));
//        }
//    }
}
