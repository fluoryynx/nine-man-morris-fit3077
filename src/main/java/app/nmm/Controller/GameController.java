package app.nmm.Controller;

import app.nmm.Data;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Action.MoveTokenAction;
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
    @FXML Text whiteTokenCount;
    @FXML Text blackTokenCount;
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

    /**
     * initialise method. to initalise winner and loser value before starting the game
     * @param location
     * @param resources
     */
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // get the winner and loser data from static Data class
        winner = Data.getWinner();
        loser = Data.getLoser();
        mode = Data.getMode();
        // set the text inside the Text node
        p1.setText(winner.getValue());
        p2.setText(loser.getValue());
        // load the game engine
        this.loadEngine();

        if (DEBUG){
            System.out.println("im in game scene");
            System.out.println(winner);
            System.out.println(loser);
            System.out.println(mode);
        }
    }

    /**
     * load the game by generating all the necessary information before starting the game
     */
    void loadEngine(){
        // declare the necessary attributes
        this.playerList = new ArrayList<>();
        this.nodeList =  new ArrayList<>();
        this.checkMill =  new CheckMill();
        this.checkLegalMove = new CheckLegalMove();
        this.locationList= new ArrayList<>();
        // Manually generate all the locations
        this.addLocation();
        // Use the locations generated and manually generate the nodes
        for(int i = 0; i < 24; i++){
            this.nodeList.add(new app.nmm.Logic.Location.Node(i, this.locationList.get(i)));
        }
    }

    /**
     * simple button to click and stop the application
     * @param event
     */
    @FXML
    void closeApplication(MouseEvent event) {
        Platform.exit();
    }

    /**
     * a method for a button to click to start the game
     * @param event
     */
    @FXML
    void startGame(MouseEvent event){
        // get the current scene and save as an attribute
        currentScene = startButton.getScene();
        // set overlay visibility to false
        Group group = (Group) currentScene.lookup("#overlay");
        group.setVisible(false);
        // find current suitable gamemode
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

    /**
     * a method to show the hint
     */
    @FXML
    void showHint(){
        // TODO: implement hint function
        if (DEBUG){
            System.out.println("No hint yet");
        }
    }

    /**
     * a method  to start a game between player and player
     */
    void pvpMode(){
        // Add Players into the game
        Player player1 = new Player("White", p1.getText(),0);
        Player player2 = new Player("Black", p2.getText(),1);
        this.playerList.add(player1);
        this.playerList.add(player2);
        if (DEBUG){
            System.out.println(playerList);
        }
        // start the game here
        showLegalPut(player1,player2);
    }

    /**
     * a method to start a game between player and computer
     */
    void pvCMode(){
        //Add Player and Computer into the game
        this.playerList.add(new Player("White", p1.getText(),0));
        this.playerList.add(new Computer("Black", "Comp",1));
        // TODO: implement player vs computer mode
    }

    /**
     * a method to call tutorial mode
     */
    void tutorialMode(){
        // TODO: implement tutorial mode
        if (DEBUG){
            System.out.println("Hi");
        }
    }

    /**
     * a method to manually generate locations and add into the arraylist
     */
    public void addLocation(){
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

    /**
     * a main method to call putLegalMoveImage repeatedly to put the image on the board
     * @param currentActor
     * @param nextActor
     */
    @FXML
    void showLegalPut(Actor currentActor, Actor nextActor){
        // get the available actions from CheckLegalMove class
        List<Action> returnAction = this.checkLegalMove.calculateLegalPut(nodeList);
        if (DEBUG){
            System.out.println(returnAction);
        }
        // repeatedly call method to put image on the board
        for (int i = 0; i < returnAction.size(); i++){
            int id = returnAction.get(i).getNodeId();
            putLegalMoveImage(id, returnAction.get(i), currentActor, nextActor, 1,null);
        }
    }

    /**
     * a method to put legal move photo on the board
     * @param nodeId current node id
     * @param action current action
     * @param currentActor current actor
     * @param nextActor next actor turn after current actor
     * @param allowChildSize number of children allowed in the node
     * @param highlightedNode current node
     */
    @FXML
    public void putLegalMoveImage(int nodeId, Action action, Actor currentActor, Actor nextActor, int allowChildSize, ArrayList<Integer>highlightedNode) {
        // get current group using nodeId
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
        // get the correct path, mac and windows different way of calling path
        String path = "";
        if (System.getProperty("os.name").charAt(0) == 'W'){
            path = "resources\\Graphic\\Legal_Move.png";
        }
        else{
            path = "resources/Graphic/Legal_Move.png";
        }
        // photo fxid
        String legalMoveID = "legalMove";
        // add to the board
        ImageView imageView = addItemToBoard(path,legalMoveID,-3,-3,24,25,childList);

        // make imageview clickable
        imageView.setOnMouseClicked(event -> {
            if (DEBUG){
                System.out.println("IM HERE");
            }
            // if currently still in put token phase
            if (currentActor.getStatus() == Capability.PUT_TOKEN) {
                // continue and let the player choose to where to put
                putTokenExecutor(action, currentActor, nextActor);
            }
            else if(currentActor.getStatus() == Capability.NORMAL){
                // continue and start the game normally
                moveTokenExecutor(action, currentActor,nextActor, highlightedNode);
            }
            else {
                //TODO: implement fly
            }
        });
    }

    /**
     * a method for legal move image to call. remove the legal moves image, and put the token on the board
     * @param action current action to be executed
     * @param currentActor actor to execute the actor
     * @param nextActor next actor after current actor
     */
    void putTokenExecutor(Action action, Actor currentActor, Actor nextActor){
        // remove all legal moves images on the board by using the fxid of the image to find the images
        for (int i = 0; i < 24; i++){
            Group group = (Group) currentScene.lookup("#g"+i);
            ObservableList<Node> childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }
        // subtract token count by executing the action
        action.execute(currentActor, nodeList);
        // get necessary information to add to the board
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        String tokenColour = currentActor.getTokenColour();
        String tokenID = tokenColour + "token"+ (9 - currentActor.getNumberOfTokensInHand());
        String path = "";
        if (System.getProperty("os.name").charAt(0) == 'W'){
            path = "resources\\Graphic\\" + tokenColour + "_Token.png";
        }
        else{
            path = "resources/Graphic/" + tokenColour + "_Token.png";
        }
        // add token to board
        addItemToBoard(path, tokenID,0,0,18,18 ,childList);
        // update the token count on the UI
        if (currentActor.getTokenColour().equals("White")){
            whiteTokenCount.setText(Integer.toString(currentActor.getNumberOfTokensOnBoard()));
        }
        else{
            blackTokenCount.setText(Integer.toString(currentActor.getNumberOfTokensOnBoard()));
        }

        // update the current player status
        if (currentActor.getNumberOfTokensInHand() == 0){
            currentActor.updateStatus(Capability.NORMAL);
        }
        // find if need to continue put token
        if (this.playerList.get(1).getStatus()==Capability.PUT_TOKEN){
            // for animation purposes
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // continue put
            showLegalPut(nextActor, currentActor);
        }
        else{
            // start normal gameplay
            normalGamePlay();
        }

    }

    /**
     * a method to put the image on the board
     * @param pathname the path to locate the image
     * @param itemID the fxid to be assigned
     * @param xLayout x coordinate
     * @param yLayout y coordinate
     * @param fitWidth width of the image
     * @param fitHeight height of the image
     * @param childList the list the item to be added into
     * @return
     */
    @FXML
    public ImageView addItemToBoard(String pathname, String itemID, int xLayout, int yLayout, int fitWidth, int fitHeight, ObservableList<Node> childList) {
        // get the file
        File file = new File(pathname);
        // convert to image
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView();
        // set the image view properties
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

    /**
     * a method to run the normal gameplay
     */
    public void normalGamePlay() {
        Group group;
        ObservableList<Node> childList;
        // clear remaining legal move image on the board
        for (int i = 0; i < 24; i++){
            group = (Group) currentScene.lookup("#g"+i);
            childList =  group.getChildren();
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals("legalMove")){
                childList.remove(node);
            }
        }
        if (DEBUG){
            System.out.println("DONE");
        }
        // start gameplay
        // same idea for two players for normal phase
        Actor actor1 = this.playerList.get(0);
        Actor actor2 =  this.playerList.get(1);
        // get available action
        Map<Integer, ArrayList<Action>> returnAction = this.checkLegalMove.calculateLegalMove(actor1, this.nodeList);
        Map<Integer, ArrayList<Action>> returnActionFly = this.checkLegalMove.calculateLegalFly(actor1, this.nodeList);

        addChecker(returnActionFly,actor1,actor2);
    }

    /**
     * a method to add a mask on all the tokens
     * @param returnAction
     * @param currentActor
     * @param nextActor
     */
    @FXML
    void addChecker(Map<Integer, ArrayList<Action>> returnAction,Actor currentActor, Actor nextActor){
        for(int i = 0; i <24; i++){
            // loop every group
            Group group = (Group) currentScene.lookup("#g"+i);
            ObservableList<Node> childList =  group.getChildren();
            String path = "";
            if (System.getProperty("os.name").charAt(0) == 'W'){
                path = "resources\\Graphic\\transparent_mask.png";
            }
            else{
                path = "resources/Graphic/transparent_mask.png";
            }
            // add a mask
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

            // set the action when click the mask here
            if(! this.nodeList.get(i).hasToken()){
                // this action is for nodes without token
                imageView.setOnMouseClicked(event -> {
                    String msg = "fuck_off\n";
                    System.out.println("IM HERE from Normal without token");
                    System.out.println(msg);
                });
            }
            else{
                if( currentActor.getTokenColour() != this.nodeList.get(i).getToken().getColour()){
                    // this action is for nodes with enemy token
                    imageView.setOnMouseClicked(event -> {
                        System.out.println("IM HERE from Normal with token");
                        String msg = "But Not my turn la!!!";
                        System.out.println(msg);
                    });
                }
                else{
                    // this action is for nodes with own token
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

    /**
     * a method to add legal moves on the tokens
     * @param allowableActions
     * @param currentActor
     * @param nextActor
     */
    void validateActorSelection(ArrayList<Action> allowableActions, Actor currentActor, Actor nextActor){
        // if action is not null
        if(allowableActions != null){
            ArrayList<Integer> highlighted_node = new ArrayList<>();
            for(Action action: allowableActions) {
                int targetID = ((MoveTokenAction) action).getTargetId();
                highlighted_node.add(targetID);
            };
            if (DEBUG){
                System.out.println("IM HERE from Normal with token2");
            }
            for(Action action: allowableActions){
                int targetID =  ((MoveTokenAction) action).getTargetId();
                putLegalMoveImage(targetID,action,currentActor,nextActor,2,highlighted_node);

            }
        }
    }

    /**
     * a method to call for the player to move the token here
     * @param action
     * @param currentActor
     * @param nextActor
     * @param highlightedNode
     */
    void moveTokenExecutor(Action action, Actor currentActor, Actor nextActor, ArrayList<Integer> highlightedNode){
        // remove legal move image
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
        // use node id to remove the token from the board
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        while (childList.size() > 1) {
            Node node = childList.get(childList.size() - 1);
            childList.remove(node);

        }
        // get node id
        // add token to new location
        int targetId = ((MoveTokenAction)action).getTargetId();
        group = (Group) currentScene.lookup("#g"+targetId);
        childList =  group.getChildren();
        String tokenColour =  this.nodeList.get(targetId).getToken().getColour();

        String path = "";
        if (System.getProperty("os.name").charAt(0) == 'W'){
            path = "resources\\Graphic\\" + tokenColour + "_Token.png";
        }
        else{
            path = "resources/Graphic/" + tokenColour + "_Token.png";
        }

        String tokenID =  tokenColour+"token" +targetId;
        // id: "w" + "token" + "tokencount"
        // here to add
        addItemToBoard(path, tokenID,0,0,18,18 ,childList);
        // calculate for the allowable action
        Map<Integer, ArrayList<Action>> returnAction = this.checkLegalMove.calculateLegalMove(nextActor, this.nodeList);
        Map<Integer, ArrayList<Action>> returnActionFly = this.checkLegalMove.calculateLegalFly(nextActor, this.nodeList);
        // add the mask
        addChecker(returnActionFly,nextActor,currentActor);
    }

    /**
     * a method to call the removal of legal moves on board when running normal game
     * @param currentNodeID
     * @param actionsList
     * @param currentActor
     * @param nextActor
     */
    void unhighlightSelectedToken(int currentNodeID, ArrayList<Action> actionsList, Actor currentActor, Actor nextActor){
        Group group = (Group) currentScene.lookup("#g"+currentNodeID);
        ObservableList<Node> childList =  group.getChildren();
        Node node = childList.get(childList.size() - 1);

        node.setOnMouseClicked(event -> {
            if (DEBUG){
                System.out.println("Unhighlight the selected token");
            }
            unhighlightSelectedTokenValidation(currentNodeID, actionsList, currentActor, nextActor);
        });


    }

    /**
     * main method to remove the legal moves of the board when the player click the token again
     * @param currentNodeID
     * @param actionsList
     * @param currentActor
     * @param nextActor
     */
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
        if (DEBUG){
            System.out.println("change the image's action.");
        }
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
