package app.nmm.Controller;

import app.nmm.Data;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.MoveTokenAction;
import app.nmm.Logic.Actor.Actor;
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
    @FXML
    AnchorPane boardScene;
    Scene currentScene;
    @FXML
    Button startButton;

    private final boolean DEBUG = false;
    private ArrayList<app.nmm.Logic.Location.Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private ArrayList<Actor> playerList;
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
        Group group = (Group) currentScene.lookup("#overlay");
        ObservableList<Node> childList =  group.getChildren();
        for(int i = 0; i < childList.size()+1;i++){
            Node node = childList.get(0);
            childList.remove(node);
        }
        group.resize(0,0);
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
    void showHint(){
        // TODO: implement hint function
        System.out.println("No hint yet");
    }

    void pvpMode(){
        //Add Players into the game
        Player player1 = new Player("White", p1.getText(),0);
        Player player2 = new Player("Black", p2.getText(),1);
        this.playerList.add(player1);
        this.playerList.add(player2);
        System.out.println(playerList);
        // TODO: implement player vs player engine
        showLegalPut(player1,player2);
    }

    void pvCMode(){
        //Add Player and Computer into the game
//        this.playerList.add(new Player("White", p1.getText(),0));
//        this.playerList.add(new Computer("Black", "Comp",1));
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
    public void putLegalMoveImage(int nodeId, Action action, Actor currentActor, Actor nextActor, int allowChildSize, ArrayList<Integer>highlightedNode) {
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        // remove any extra children
        if(childList.size() > allowChildSize){
            while (childList.size() > allowChildSize){
                Node node =  childList.get(childList.size()-1);
                childList.remove(node);
            }
        }
        // find the image, and put inside image view
        String path = "";
        if (System.getProperty("os.name").substring(0,1) == "W"){
            path = "src\\main\\resources\\Graphic\\Legal_Move.png";
        }
        else{
            path = "src/main/resources/Graphic/Legal_Move.png";
        }
        String legalMoveID = "legalMove";
        ImageView imageView = addItemToBoard(path,legalMoveID,-3,-3,24,25,childList);

        // make imageview clickable
        imageView.setOnMouseClicked(event -> {
            System.out.println("IM HERE");
            if (currentActor.getStatus() == Capability.PUT_TOKEN) {
                putTokenExecutor(action, currentActor, nextActor);
            }
            else if(currentActor.getStatus() == Capability.NORMAL){
                //TODO
                moveTokenExecutor(action, currentActor,nextActor, highlightedNode);
            }
            else if(currentActor.getStatus() == Capability.FLY){
                //TODO
            }

        });
    }

    @FXML
    void showLegalPut(Actor currentActor, Actor nextActor){
        List<Action> returnAction = this.checkLegalMove.calculateLegalPut(nodeList);
        System.out.println(returnAction);
        for (int i = 0; i < returnAction.size(); i++){
            int id = returnAction.get(i).getNodeId();
            putLegalMoveImage(id, returnAction.get(i), currentActor, nextActor, 1,null);
        }
    }

    void putTokenExecutor(Action action, Actor currentActor, Actor nextActor){
        //Remove legal image
        for (int i = 0; i < 24; i++){
            Group group = (Group) currentScene.lookup("#g"+i);
            ObservableList<Node> childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }
        // subtract token count
        action.execute(currentActor, nodeList);
        // get node id
        // add token to front end
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();

        String tokenColour = currentActor.getTokenColour();
        String tokenID = tokenColour + "token"+ (9 - currentActor.getNumberOfTokensInHand());
        String path = "";
        if (System.getProperty("os.name").substring(0,1) == "W"){
            path = "src\\main\\resources\\Graphic\\" + tokenColour + "_Token.png";
        }
        else{
            path = "src/main/resources/Graphic/" + tokenColour + "_Token.png";
        }
        addItemToBoard(path, tokenID,0,0,18,18 ,childList);

        if (currentActor.getNumberOfTokensInHand() == 0){
            currentActor.updateStatus(Capability.NORMAL);
        }
        if (this.playerList.get(1).getStatus()==Capability.PUT_TOKEN){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showLegalPut(nextActor, currentActor);
        }
        else{
            normalGamePlay();
        }

    }


    @FXML
    public ImageView addItemToBoard(String pathname, String itemID, int xLayout, int yLayout, int fitWidth, int fitHeight, ObservableList<Node> childList) {
        File file = new File(pathname);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(fitWidth);
        imageView.setFitHeight(fitHeight);
        imageView.setLayoutX(xLayout);
        imageView.setLayoutY(yLayout);
        // id: "w" + "token" + "tokencount"
        imageView.setId(itemID);
        childList.add(imageView);
        return imageView;
    }

    public void normalGamePlay() {
        Group group;
        ObservableList<Node> childList;
        // clear legal move image
        for (int i = 0; i < 24; i++){
            group = (Group) currentScene.lookup("#g"+i);
            childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }
        System.out.println("DONE");
        // start gameplay
        // same idea for two players for normal phase
        Actor actor1 = this.playerList.get(0);
        Actor actor2 =  this.playerList.get(1);
        Map<Integer, ArrayList<Action>> returnAction = this.checkLegalMove.calculateLegalMove(actor1, this.nodeList);
        Map<Integer, ArrayList<Action>> returnActionFly = this.checkLegalMove.calculateLegalFly(actor1, this.nodeList);

        addChecker(returnActionFly,actor1,actor2);
//        showLegalMove(actor1,actor2);
    }

    @FXML
    void addChecker(Map<Integer, ArrayList<Action>> returnAction,Actor currentActor, Actor nextActor){
        for(int i = 0; i <24; i++){
            Group group = (Group) currentScene.lookup("#g"+i);
            ObservableList<Node> childList =  group.getChildren();
            String path = "";
            if (System.getProperty("os.name").substring(0,1) == "W"){
                path = "src\\main\\resources\\Graphic\\transparent_mask.png";
            }
            else{
                path = "src/main/resources/Graphic/transparent_mask.png";
            }
            File file = new File(path);
            Image image =  new Image(file.toURI().toString());
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitHeight(24);
            imageView.setFitWidth(24);
            imageView.setLayoutX(-3);
            imageView.setLayoutY(-3);
            imageView.setId("transparent_mask");
            childList.add(imageView);

            if(! this.nodeList.get(i).hasToken()){

                imageView.setOnMouseClicked(event -> {
                    String msg = "fuck_off\n";
                    System.out.println("IM HERE from Normal without token");
                    System.out.println(msg);
                });
            }
            else{
                if( currentActor.getTokenColour() != this.nodeList.get(i).getToken().getColour()){
                    imageView.setOnMouseClicked(event -> {

                        System.out.println("IM HERE from Normal with token");
                        String msg = "But Not my turn la!!!";
                        System.out.println(msg);
                    });
                }
                else{
                    ArrayList<Action> actionsList =  returnAction.get(i);
                    Integer currentNodeID = i;
                    imageView.setOnMouseClicked(event -> {

//                        System.out.println("IM HERE from Normal with token");
//                        System.out.println(actionsList.size());
//                        String tokenColour =  this.nodeList.get(currentNodeID).getToken().getColour();
//
//                        String paths = "";
//                        if (System.getProperty("os.name").substring(0,1) == "W"){
//                            paths = "src\\main\\resources\\Graphic\\" + tokenColour + "_Token_when_user_select.png";
//                        }
//                        else{
//                            paths = "src/main/resources/Graphic/" + tokenColour + "_Token_when_user_select.png";
//                        }
//                        File newFile = new File(paths);
//
//                        Group currentGroup = (Group) currentScene.lookup("#g"+currentNodeID);
//                        ObservableList<Node>currentChildList =  currentGroup.getChildren();
//                        String id =  tokenColour+"token";
//                        for(int j = 0; j < childList.size(); j++){
//                            Node node = currentChildList.get(j);
//                            if(node.getId().contains(id) ){
//                                ((ImageView) node).setImage(new Image(newFile.toURI().toString()));
//                                ((ImageView) node).setFitWidth(24);
//                                ((ImageView) node).setFitHeight(24);
//                                ((ImageView) node).setLayoutX(-3);
//                                ((ImageView) node).setLayoutY(-3);
//                                break;
//                            }
//                        }




                        unhighlightSelectedToken(currentNodeID,actionsList,currentActor,nextActor);
                        validateActorSelection(actionsList,currentActor,nextActor);
                    });
                }

            }
        }

    }

    void validateActorSelection(ArrayList<Action> allowableActions, Actor currentActor, Actor nextActor){
        if(allowableActions != null){
            ArrayList<Integer> highlighted_node = new ArrayList<>();
            for(Action action: allowableActions) {
                Integer targetID = ((MoveTokenAction) action).getTargetId();
                highlighted_node.add(targetID);
            };

            System.out.println("IM HERE from Normal with token2");
            for(Action action: allowableActions){
                int targetID =  ((MoveTokenAction) action).getTargetId();
                putLegalMoveImage(targetID,action,currentActor,nextActor,2,highlighted_node);

            }
        }
    }

    void moveTokenExecutor(Action action, Actor currentActor, Actor nextActor, ArrayList<Integer> highlightedNode){
        //Remove legal image
        for (int i = 0; i < highlightedNode.size(); i++){
            Integer nodeId = highlightedNode.get(i);
            Group group = (Group) currentScene.lookup("#g"+nodeId);
            ObservableList<Node> childList =  group.getChildren();
            while (childList.size() > 1){
                Node node =  childList.get(childList.size()-1);
                childList.remove(node);
            }


        }
        // subtract token count
        action.execute(currentActor, nodeList);
        // get node id
        // add token to front end
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        while (childList.size() > 1) {
            Node node = childList.get(childList.size() - 1);
            childList.remove(node);

        }

        int targetId = ((MoveTokenAction)action).getTargetId();
        group = (Group) currentScene.lookup("#g"+targetId);
        childList =  group.getChildren();
        String tokenColour =  this.nodeList.get(targetId).getToken().getColour();

        String path = "";
        if (System.getProperty("os.name").substring(0,1) == "W"){
            path = "src\\main\\resources\\Graphic\\" + tokenColour + "_Token.png";
        }
        else{
            path = "src/main/resources/Graphic/" + tokenColour + "_Token.png";
        }

        String tokenID =  tokenColour+"token" +targetId;
        // id: "w" + "token" + "tokencount"
        addItemToBoard(path, tokenID,0,0,18,18 ,childList);

        Map<Integer, ArrayList<Action>> returnAction = this.checkLegalMove.calculateLegalMove(nextActor, this.nodeList);
        Map<Integer, ArrayList<Action>> returnActionFly = this.checkLegalMove.calculateLegalFly(nextActor, this.nodeList);

        addChecker(returnActionFly,nextActor,currentActor);



    }

    void unhighlightSelectedToken(int currentNodeID, ArrayList<Action> actionsList, Actor currentActor, Actor nextActor){
        Group group = (Group) currentScene.lookup("#g"+currentNodeID);
        ObservableList<Node> childList =  group.getChildren();
        Node node = childList.get(childList.size() - 1);

        node.setOnMouseClicked(event -> {

            System.out.println("Unhighlight the selected token");

            unhighlightSelectedTokenValidation(currentNodeID, actionsList, currentActor, nextActor);
        });


    }

    void unhighlightSelectedTokenValidation(int currentNodeID, ArrayList<Action> actionsList, Actor currentActor, Actor nextActor){

        ArrayList<Integer> highlighted_node = new ArrayList<>();
        for(Action action: actionsList) {
            Integer targetID = ((MoveTokenAction) action).getTargetId();
            highlighted_node.add(targetID);
        };

        for(int nodeIndex: highlighted_node){
            Group group = (Group) currentScene.lookup("#g"+nodeIndex);
            ObservableList<Node> childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }

        Group group = (Group) currentScene.lookup("#g"+currentNodeID);
        ObservableList<Node> childList =  group.getChildren();
        Node node = childList.get(childList.size() - 1);
        System.out.println("change the image's action.");
//        String tokenColour =  this.nodeList.get(currentNodeID).getToken().getColour();
//
//        String paths = "";
//        if (System.getProperty("os.name").substring(0,1) == "W"){
//            paths = "src\\main\\resources\\Graphic\\" + tokenColour + "_Token.png";
//        }
//        else{
//            paths = "src/main/resources/Graphic/" + tokenColour + "_Token.png";
//        }
//        File newFile = new File(paths);
//
//
//        String id =  tokenColour+"token";
//        for(int j = 0; j < childList.size(); j++){
//            Node currentNode = childList.get(j);
//            if(currentNode.getId().contains(id) ){
//                ((ImageView) currentNode).setImage(new Image(newFile.toURI().toString()));
//                ((ImageView) currentNode).setFitWidth(18);
//                ((ImageView) currentNode).setFitHeight(18);
//                ((ImageView) currentNode).setLayoutX(0);
//                ((ImageView) currentNode).setLayoutY(0);
//                break;
//            }
//        }
       node.setOnMouseClicked(event -> {

//           System.out.println("IM HERE from Normal with token");
//           System.out.println(actionsList.size());
//           String tokenColour2 =  this.nodeList.get(currentNodeID).getToken().getColour();
//
//           String paths2 = "";
//           if (System.getProperty("os.name").substring(0,1) == "W"){
//               paths2 = "src\\main\\resources\\Graphic\\" + tokenColour + "_Token_when_user_select.png";
//           }
//           else{
//               paths2 = "src/main/resources/Graphic/" + tokenColour + "_Token_when_user_select.png";
//           }
//           File newFile2 = new File(paths2);
//
//           Group currentGroup = (Group) currentScene.lookup("#g"+currentNodeID);
//           ObservableList<Node>currentChildList =  currentGroup.getChildren();
//           String id2 =  tokenColour2+"token";
//           for(int j = 0; j < childList.size(); j++){
//               Node node2 = currentChildList.get(j);
//               if(node2.getId().contains(id2) ){
//                   ((ImageView) node2).setImage(new Image(newFile2.toURI().toString()));
//                   ((ImageView) node2).setFitWidth(24);
//                   ((ImageView) node2).setFitHeight(24);
//                   ((ImageView) node2).setLayoutX(-3);
//                   ((ImageView) node2).setLayoutY(-3);
//                   break;
//               }
//           }
           unhighlightSelectedToken(currentNodeID,actionsList,currentActor,nextActor);
           validateActorSelection(actionsList,currentActor,nextActor);

        });

    }



}
