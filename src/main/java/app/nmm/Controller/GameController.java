package app.nmm.Controller;

import app.nmm.Application;
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
import javafx.fxml.FXMLLoader;
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
//import javafx.util.Pair;
import org.javatuples.Pair;
import java.io.File;
import java.io.IOException;
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
    @FXML Text gameStatus;
    @FXML
    AnchorPane boardScene;
    Scene currentScene;
    @FXML
    Button startButton;
    Button backToMainButton;

    private final boolean DEBUG = false;
    private ArrayList<app.nmm.Logic.Location.Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private ArrayList<Actor> playerList;
    private ArrayList<Pair<Integer,Integer>> locationList;
    private boolean clicked = false;
    private int prevNodeId;
    private final String windowsResourcePath = "resources\\Graphic\\";
    private final String macResourcePath = "resources/Graphic/";


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
        p1.setText(winner.getValue1());
        p2.setText(loser.getValue1());
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


    @FXML
    void backToMain(MouseEvent event) throws IOException {
        AnchorPane mainPageScene = FXMLLoader.load(Application.class.getResource("main.fxml"));
        boardScene.getChildren().removeAll();
        boardScene.getChildren().setAll(mainPageScene);
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

    @FXML void endGame(){

        //set overlay visibility to true
        Group group = (Group) currentScene.lookup("#end_game_overlay");
        group.setVisible(true);

        System.out.println(currentScene);
        System.out.println("game end");

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
     * remove image from the board
     * @param nodeId - int, nodeId
     * @param nodeFxId - String, type of image (the fxid)
     */
    public void removeImage(int nodeId, String nodeFxId){
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        if (childList.size() > 1){
            Node node =  childList.get(childList.size()-1);
            if (node.getId().equals(nodeFxId)){
                childList.remove(node);
            }
        }
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
            putLegalMoveImage(id, returnAction.get(i), currentActor, nextActor,null);
        }
        // set the game status in the UI
        gameStatus.setText(currentActor.getActorname() + "'s Turn To Place Token") ;
    }

    /**
     * a method to put legal move photo on the board
     * @param nodeId current node id
     * @param action current action
     * @param currentActor current actor
     * @param nextActor next actor turn after current actor
     * @param highlightedNode current node
     */
    @FXML
    public void putLegalMoveImage(int nodeId, Action action, Actor currentActor, Actor nextActor, ArrayList<Integer>highlightedNode) {
        // get current group using nodeId
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        // remove any extra children
        removeImage(nodeId, "legalMove");
        // find the image, and put inside image view
        // get the correct path, mac and windows different way of calling path
        String path;
        if (System.getProperty("os.name").charAt(0) == 'W'){
            path = windowsResourcePath + "Legal_Move.png";
        }
        else{
            path = macResourcePath + "Legal_Move.png";
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
            removeImage(i, "legalMove");

        }
        // subtract token count by executing the action
        action.execute(currentActor, nodeList);
        // get necessary information to add to the board
        int nodeId = action.getNodeId();
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        String tokenColour = currentActor.getTokenColour();
        String tokenID = tokenColour + "token"+ (9 - currentActor.getNumberOfTokensInHand());
        String path = getTokenImagePath(tokenColour,"_Token.png");

        // add token to board
        addItemToBoard(path, tokenID,0,0,18,18 ,childList);

        // Check if form mill
        ArrayList<Boolean> isMill = checkMill.checkPossibleMill(nodeList,action.getNodeId());
        ArrayList<ArrayList<Integer>> millCombinationTokenPosition = checkMill.getMillNodes(action.getNodeId());

        if(isMill.get(0) == true || isMill.get(1) == true){
            swapTokenToMill(action.getNodeId(), isMill, millCombinationTokenPosition);
            // ToDo: Remove token code
            // ToDo: Rmb to copy and paste the code in the else part after to token is remove so that the game can continue
        }
        // No mill form, continue
        else{

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
        // clear remaining legal move image on the board
        for (int i = 0; i < 24; i++){
            removeImage(i, "legalMove");
        }
        if (DEBUG){
            System.out.println("DONE");
        }
        // start gameplay
        // same idea for two players for normal phase
        Actor actor1 = this.playerList.get(0);
        Actor actor2 =  this.playerList.get(1);
        // get available action
        this.checkLegalMove.calculateLegalMove(actor1, this.nodeList);
        Map<Integer, ArrayList<Action>> returnAction = this.checkLegalMove.getCurrentActions();
//        Map<Integer, ArrayList<Action>> returnActionFly = this.checkLegalMove.calculateLegalFly(actor1, this.nodeList);

        for (int i = 0; i < 24; i++){
            addChecker(i, returnAction, actor1, actor2);
        }

        System.out.println("return action: ");
        System.out.println(returnAction);

        // check if actor 1 have any legal move action
        boolean noMoveAction=true;

        for (ArrayList<Action> value : returnAction.values()) {
            if (value.size() != 0) {
                System.out.println(value.size());
                noMoveAction = false;
            }
        }

        if (actor1.checkLose() == false && noMoveAction == false){
            gameStatus.setText(actor1.getActorname()+ "'s Turn To Move");
        }
        else{
            gameStatus.setText(actor2.getActorname() + " won");
            this.endGame();
        }
    }

    /**
     * a method to add a mask on all the tokens
     * @param returnAction
     * @param currentActor
     * @param nextActor
     */
    @FXML
    void addChecker(int nodeId, Map<Integer, ArrayList<Action>> returnAction,Actor currentActor, Actor nextActor){
        // loop every group
        Group group = (Group) currentScene.lookup("#g"+nodeId);
        ObservableList<Node> childList =  group.getChildren();
        String path = "";
        if (System.getProperty("os.name").charAt(0) == 'W'){
            path = windowsResourcePath + "transparent_mask.png";
        }
        else{
            path = macResourcePath + "transparent_mask.png";
        }
        // add a mask
        ImageView imageView = addItemToBoard(path,"transparent_mask",-3, -3, 24, 24, childList);
        // set the action when click the mask here
        if(! this.nodeList.get(nodeId).hasToken()){
            // this action is for nodes without token
            imageView.setOnMouseClicked(event -> {
                String msg = "fuck off";
                System.out.println("IM HERE from Normal without token");
                System.out.println(msg);
            });
        }
        else{
            if(!currentActor.getTokenColour().equals(this.nodeList.get(nodeId).getToken().getColour())){
                // this action is for nodes with enemy token
                imageView.setOnMouseClicked(event -> {
                    System.out.println("IM HERE from Normal with token");
                    String msg = "But Not my turn la!!!";
                    System.out.println(msg);
                });
            }
            else{
                // this action is for nodes with own token
                ArrayList<Action> actionsList =  returnAction.get(nodeId);
                imageView.setOnMouseClicked(event -> {

                        System.out.println("IM HERE from Normal with token");
                        String tokenColour =  this.nodeList.get(nodeId).getToken().getColour();
                        String paths = getTokenImagePath(tokenColour, "_Token_when_user_select.png");
                        changeTokenImage(nodeId,tokenColour,paths,24,-3);


                        validateActorSelection(actionsList,currentActor,nextActor, nodeId);
                });
            }

        }

    }


    /**
     * a method to add legal moves on the tokens
     * @param allowableActions
     * @param currentActor
     * @param nextActor
     */
    void validateActorSelection(ArrayList<Action> allowableActions, Actor currentActor, Actor nextActor, Integer currentNodeID){
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
            if (clicked && prevNodeId != currentNodeID){
                ArrayList<Action> previousNodeAction = checkLegalMove.getCurrentActions().get(prevNodeId);
                unhighlightSelectedToken(prevNodeId,previousNodeAction,currentActor,nextActor);
            }

                // add unhighlight action to mask to the current node
                addUnhighlightSelectedToken(currentNodeID, allowableActions, currentActor, nextActor);
                clicked = true;
                prevNodeId = currentNodeID;
                // put legal image
                for(Action action: allowableActions){
                    int targetID =  ((MoveTokenAction) action).getTargetId();
                    putLegalMoveImage(targetID, action, currentActor, nextActor, highlighted_node);

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
        for (int nodeId : highlightedNode){
            removeImage(nodeId, "legalMove");
            removeImage(nodeId,"transparent_mask");

        }

        //Remove mill if the token that is being moved is part of mill
        int currentNodeId = action.getNodeId();
        ArrayList<ArrayList<Integer>> possibleMillPosition = checkMill.getMillNodes(currentNodeId);
        String tokenColour =  currentActor.getTokenColour();
        // change the graphic of the token
        String paths = getTokenImagePath(tokenColour, "_Token.png");

        for(int i = 0; i < possibleMillPosition.size(); i++){
            for(int j = 0; j < possibleMillPosition.get(i).size(); j++){
                int id  = possibleMillPosition.get(i).get(j);
                if(this.nodeList.get(id).getToken()!= null && this.nodeList.get(id).getToken().getColour() == currentActor.getTokenColour()){
                    if (this.nodeList.get(id).getToken().getIsMill() == true){

                        if( i == 0 ){
                            nodeList.get(id).getToken().setMillHorizontal(false);
                        }
                        else{
                            nodeList.get(id).getToken().setMillVertical(false);
                        }

                        if (this.nodeList.get(id).getToken().getIsMill() == false){
                            changeTokenImage(id, tokenColour, paths, 18,0);
                        }

                    }
                }
                else{
                    break;
                }

            }
        }

        // move the token
        action.execute(currentActor, nodeList);
        // reset clicked
        this.clicked = false;
        this.prevNodeId = 0;
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
        String path = getTokenImagePath(tokenColour, "_Token.png");
        String tokenID =  tokenColour+"token" +targetId;
        addItemToBoard(path, tokenID,0,0,18,18 ,childList);


        // Check mill and highlight if the node form a mill
        int targetNodeId = ((MoveTokenAction)action).getTargetId();
        ArrayList<Boolean> isMill = checkMill.checkPossibleMill(this.nodeList,targetNodeId);
        ArrayList<ArrayList<Integer>> millCombinationTokenPosition = checkMill.getMillNodes(targetNodeId);

        if(isMill.get(0) == true || isMill.get(1) == true){
            swapTokenToMill(targetNodeId, isMill, millCombinationTokenPosition);
            // ToDo: Remove token code
            // ToDo: Rmb to copy and paste the code in the else part after to token is remove so that the game can continue
        }
        // No mill form
        else{
            // calculate for the allowable action
            this.checkLegalMove.calculateLegalMove(nextActor, this.nodeList);
            Map<Integer, ArrayList<Action>> returnAction = this.checkLegalMove.getCurrentActions();
            // add the mask
            for (int i = 0; i < 24; i++){
                addChecker(i, returnAction, nextActor, currentActor);
            }

            // check if next actor have any legal move action
            boolean noMoveAction=true;

            for (ArrayList<Action> value : returnAction.values()) {
                System.out.println(value.size());
                if (value.size() != 0) {
                    noMoveAction = false;
                }
            }

            if (nextActor.checkLose() == false && noMoveAction == false){
                gameStatus.setText(nextActor.getActorname()+ "'s Turn To Move");
            }
            else{
                gameStatus.setText(currentActor.getActorname() + " won");
                this.endGame();
            }

        }

    }

    private String getTokenImagePath(String tokenColour, String imageName) {
        String paths = "";
        if (System.getProperty("os.name").substring(0,1) == "W"){
            paths = windowsResourcePath + tokenColour + imageName;
        }
        else{
            paths = macResourcePath + tokenColour + imageName;
        }
        return paths;
    }

    private void changeTokenImage(int id, String tokenColour, String paths, Integer size, Integer offSet) {
        Group currentGroup = (Group) currentScene.lookup("#g"+ id);
        ObservableList<Node>currentChildList =  currentGroup.getChildren();
        File newFile = new File(paths);

        // Change the graphic of the selected token to toke with highlight around.
        for(int k = 0; k < currentChildList.size(); k++){
            Node node = currentChildList.get(k);
            if(node.getId().contains(tokenColour) ){
                ((ImageView) node).setImage(new Image(newFile.toURI().toString()));
                ((ImageView) node).setFitWidth(size);
                ((ImageView) node).setFitHeight(size);
                ((ImageView) node).setLayoutX(offSet);
                ((ImageView) node).setLayoutY(offSet);
                break;
            }
        }
    }

    private void swapTokenToMill(int nodeId, ArrayList<Boolean> isMill, ArrayList<ArrayList<Integer>> millCombinationTokenPosition) {
        String tokenColour =  this.nodeList.get(nodeId).getToken().getColour();
        String paths = getTokenImagePath(tokenColour, "_Token_with_Mill.png");
        File newFile = new File(paths);

        for (int i = 0; i < isMill.size(); i++){
            if (isMill.get(i)){
                for(int currentNodeId : millCombinationTokenPosition.get(i)){
                    if (i == 0){
                        nodeList.get(currentNodeId).getToken().setMillHorizontal(true);
                        nodeList.get(nodeId).getToken().setMillHorizontal(true);
                    }
                    else
                    {
                        nodeList.get(currentNodeId).getToken().setMillVertical(true);
                        nodeList.get(nodeId).getToken().setMillVertical(true);
                    }
                    changeTokenImage(currentNodeId,tokenColour,paths,24,-3);
                }
            }
        }

        changeTokenImage(nodeId,tokenColour,paths,24,-4);
        Group currentGroup = (Group) currentScene.lookup("#g"+nodeId);
    }

    /**
     * a method to call the removal of legal moves of one token on board when running normal game
     * @param currentNodeID
     * @param actionsList
     * @param currentActor
     * @param nextActor
     */
    void addUnhighlightSelectedToken(int currentNodeID, ArrayList<Action> actionsList, Actor currentActor, Actor nextActor){
        Group group = (Group) currentScene.lookup("#g"+currentNodeID);
        ObservableList<Node> childList =  group.getChildren();
        Node node = childList.get(childList.size() - 1);

        node.setOnMouseClicked(event -> {
            if (DEBUG){
                System.out.println("Unhighlight the selected token");
            }
            unhighlightSelectedToken(currentNodeID, actionsList, currentActor, nextActor);
        });
    }

    /**
     * main method to remove the legal moves of the board when the player click the token again
     * @param currentNodeID
     * @param actionsList
     * @param currentActor
     * @param nextActor
     */
    void unhighlightSelectedToken(int currentNodeID, ArrayList<Action> actionsList, Actor currentActor, Actor nextActor){
        // remove the legal move image
        for(Action action: actionsList) {
            int targetID = ((MoveTokenAction) action).getTargetId();
            removeImage(targetID, "legalMove");
        }

        Group group = (Group) currentScene.lookup("#g"+currentNodeID);
        ObservableList<Node> childList =  group.getChildren();
        Node node = childList.get(childList.size() - 1);
        if (DEBUG){
            System.out.println("change the image's action.");
        }
        String tokenColour =  this.nodeList.get(currentNodeID).getToken().getColour();
        if (nodeList.get(currentNodeID).getToken().getIsMill()){

           String paths = getTokenImagePath(tokenColour, "_Token_with_Mill.png");
           changeTokenImage(currentNodeID,tokenColour,paths,24,-3);
        }
        else{
            String paths = getTokenImagePath(tokenColour, "_Token.png");
            changeTokenImage(currentNodeID,tokenColour,paths,18,0);

        }
        this.prevNodeId = 0;
        this.clicked = false;
       node.setOnMouseClicked(event -> {
           System.out.println("IM HERE from Normal with token");
           System.out.println(actionsList.size());
           String tokenColour2 =  this.nodeList.get(currentNodeID).getToken().getColour();

           String paths2 = getTokenImagePath(tokenColour2, "_Token_when_user_select.png");
            changeTokenImage(currentNodeID,tokenColour2,paths2,24,-3);
           validateActorSelection(actionsList,currentActor,nextActor, currentNodeID);

        });

    }
}
