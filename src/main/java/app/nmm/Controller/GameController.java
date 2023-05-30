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
import app.nmm.Logic.Token.Token;
import app.nmm.UiEditor.BoardSceneEditor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.javatuples.Pair;
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
    @FXML Text displayWinner;
    @FXML Text reasonWin;
    @FXML
    AnchorPane boardScene;
    Scene currentScene;
    @FXML
    Button startButton;
    @FXML
    Button confirmButton;
    @FXML
    Button unconfirmedButton;

    private final boolean DEBUG = false;
    private ArrayList<app.nmm.Logic.Location.Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private ArrayList<Actor> playerList;
    private boolean clicked = false;
    private int prevNodeId;
    private final String windowsResourcePath = "resources\\Graphic\\";
    private final String macResourcePath = "resources/Graphic/";
    private BoardSceneEditor sceneEditor;


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
        loadEngine();

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
        playerList = new ArrayList<>();
        nodeList =  new ArrayList<>();
        checkMill =  new CheckMill();
        checkLegalMove = new CheckLegalMove();



        // Use the locations generated and manually generate the nodes
        for(int i = 0; i < 24; i++){
            nodeList.add(new app.nmm.Logic.Location.Node(i));
        }

    }

    /**
     * simple button to click and stop the application
     * @param event
     */
    @FXML
    void backToMain(MouseEvent event) throws IOException {
        AnchorPane mainPageScene = FXMLLoader.load(Application.class.getResource("main.fxml"));
        boardScene.getChildren().removeAll();
        boardScene.getChildren().setAll(mainPageScene);
    }

    @FXML
    void closeOverlay(MouseEvent event) {
        Group group = (Group) currentScene.lookup("#pauseToMain");
        group.setVisible(false);
    }

    @FXML
    void showBackToMainOverlay(MouseEvent event) {
        // set overlay visibility to false
        Group group = (Group) currentScene.lookup("#pauseToMain");
        group.setVisible(true);
    }

    /**
     * a method for a button to click to start the game
     * @param event
     */
    @FXML
    void startGame(MouseEvent event){
        // get the current scene and save as an attribute
        currentScene = startButton.getScene();
        sceneEditor =  new BoardSceneEditor(currentScene,boardScene);

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
     * display the winner and reason the other player loss in an overlay
     * @param winner
     * @param loseCondition
     */
    @FXML void endGame(Actor winner, String loseCondition){
        gameStatus.setText(winner.getTokenColour() + " won");
        displayWinner.setText(winner.getTokenColour() + " won" );
        System.out.println(loseCondition);
        reasonWin.setText(loseCondition);
        //set overlay visibility to true
        Group group = (Group) currentScene.lookup("#end_game_overlay");
        group.setVisible(true);

        if (DEBUG){
            System.out.println(currentScene);
            System.out.println("game end");
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
        playerList.add(player1);
        playerList.add(player2);
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
        playerList.add(new Player("White", p1.getText(),0));
        playerList.add(new Computer("Black", "Comp",1));
        showLegalPut(playerList.get(0), playerList.get(1));
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
        for (Action action : returnAction){
            int id = action.getNodeId();
            putLegalMoveImage(id, action, currentActor, nextActor,null);
        }
        // set the game status in the UI
        gameStatus.setText(currentActor.getTokenColour() + "'s Turn To Place Token") ;
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
        // remove any extra children
        sceneEditor.removeImage(nodeId, "legalMove");
        // find the image, and put inside image view
        // get the correct path, mac and windows different way of calling path
        String path = getTokenImagePath("Legal_Move.png");
        // photo fxid
        String legalMoveID = "legalMove";
        // add to the board
        ImageView imageView = sceneEditor.addItemToBoard(path,legalMoveID,-3,-3,24,24,nodeId);

        // make imageview clickable
        imageView.setOnMouseClicked(event -> {
            selectExecutor(action, currentActor, nextActor, highlightedNode);
        });
    }

    private void selectExecutor(Action action, Actor currentActor, Actor nextActor, ArrayList<Integer> highlightedNode) {
        if (DEBUG){
            System.out.println("IM HERE");
        }
        // if currently still in put token phase
        if (currentActor.getStatus() == Capability.PUT_TOKEN) {
            // continue and let the player choose to where to put
            putTokenExecutor(action, currentActor, nextActor);
        }
        else {
            // continue and start the game normally
            moveTokenExecutor(action, currentActor, nextActor, highlightedNode);
        }
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
            sceneEditor.removeImage(i, "legalMove");
        }
        // subtract token count by executing the action
        action.execute(currentActor, nodeList);
        // get necessary information to add to the board
        int nodeId = action.getNodeId();
        String tokenColour = currentActor.getTokenColour();
        String tokenID = tokenColour + "token"+ (9 - currentActor.getNumberOfTokensInHand());
        String path = getTokenImagePath(tokenColour+"_Token.png");

        // add token to board
        sceneEditor.addItemToBoard(path, tokenID,0,0,18,18 ,nodeId);

        // Check if form mill
        ArrayList<Boolean> isMill = checkMill.checkPossibleMill(nodeList,action.getNodeId());
        ArrayList<ArrayList<Integer>> millCombinationTokenPosition = checkMill.getMillNodes(action.getNodeId());

        // Check if there are any possible tokens to be removed on the board
        checkLegalMove.calculateLegalRemove(currentActor, nodeList);

        // If mill is formed, provide the current player option to remove enemy token
        if(isMill.get(0) || isMill.get(1)) {
            if (currentActor instanceof Computer && checkLegalMove.getCurrentRemovables().size()>0){
                action = ((Computer) currentActor).getRemoveAction(checkLegalMove.getCurrentRemovables(),nodeList,checkMill);
                putRemoveTokenExecutor(action,currentActor,nextActor);
            }
            else{
                swapTokenToMill(action.getNodeId(), isMill, millCombinationTokenPosition);
            }
        }
        //If removable tokens are on board
        if ((isMill.get(0) || isMill.get(1)) && checkLegalMove.getCurrentRemovables().size()>0 && currentActor instanceof Player){
            showPutRemovable(currentActor,nextActor);
            }
        else // If no removable tokens are available just continue
        {
            sceneEditor.updateTokenCount(whiteTokenCount, blackTokenCount, currentActor.getTokenColour(), currentActor.getNumberOfTokensOnBoard());

            // update the current player status
            if (currentActor.getNumberOfTokensInHand() == 0){
                currentActor.updateStatus(Capability.NORMAL);
            }
            // find if need to continue put token
            if (playerList.get(1).getStatus()==Capability.PUT_TOKEN){


                if (nextActor instanceof Computer){
                    ArrayList<Action> allowableAction =  checkLegalMove.calculateLegalPut(nodeList);
                    checkLegalMove.calculateLegalRemove(nextActor,nodeList);
                    ArrayList<Action> removableActions =  checkLegalMove.getCurrentRemovables();
                    action =  ((Computer) nextActor).getAction(allowableAction,removableActions,nodeList,checkMill);
                    putTokenExecutor(action,nextActor,currentActor);
                }
                else
                {
                    // continue put
                    showLegalPut(nextActor, currentActor);
                }

            }
            else{
                // start normal gameplay
                normalGamePlay();
            }

        }


    }





    /**
     * a method to run the normal gameplay
     */
    public void normalGamePlay() {
        // clear remaining legal move image on the board
        for (int i = 0; i < 24; i++){
            sceneEditor.removeImage(i, "legalMove");
        }
        if (DEBUG){
            System.out.println("DONE");
        }
        // start gameplay
        // same idea for two players for normal phase
        Actor actor1 = playerList.get(0);
        Actor actor2 =  playerList.get(1);
        // get available action
        checkLegalMove.calculateLegalMove(actor1, nodeList);
        Map<Integer, ArrayList<Action>> returnAction = checkLegalMove.getCurrentActions();
        for (int i = 0; i < 24; i++){
            addChecker(i, returnAction, actor1, actor2);
        }

        if (DEBUG){
            System.out.println("return action: ");
            System.out.println(returnAction);
        }
        // check if actor 1 have any legal move action
        if (checkIfHaveLegalMove(returnAction, actor1.getTokenColour())){
            if (DEBUG){
                System.out.println(actor1.getTokenColour() + " number of tokens: " + actor1.getNumberOfTokensOnBoard());
            }
            gameStatus.setText(actor1.getTokenColour()+ "'s Turn To Move");
        }
        else{ // if actor 1 has no legal moves then declare the other actor as winner
            this.endGame(actor2,actor1.getTokenColour() + " have no legal move");
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
        String path = getTokenImagePath("transparent_mask.png");
        // add a mask
        ImageView imageView = sceneEditor.addItemToBoard(path,"transparent_mask",-3, -3, 24, 24, nodeId);
        // set the action when click the mask here
        if(nodeList.get(nodeId).hasToken() && currentActor.getTokenColour().equals(nodeList.get(nodeId).getToken().getColour())){

                // this action is for nodes with own token
                ArrayList<Action> actionsList =  returnAction.get(nodeId);
                imageView.setOnMouseClicked(event -> {
                    selectedToken(nodeId, currentActor, nextActor, actionsList);
                });

        }
    }


    private void selectedToken(int nodeId, Actor currentActor, Actor nextActor, ArrayList<Action> actionsList) {
        System.out.println("IM HERE from Normal with token");
        String tokenColour =  nodeList.get(nodeId).getToken().getColour();
        String paths = getTokenImagePath(tokenColour+"_Token_when_user_select.png");
        sceneEditor.changeTokenImage(nodeId,tokenColour, paths,24,-3);

        // if action is not null, remove legal move from previous selected token and show current legal move for current token
        if(actionsList != null){
            ArrayList<Integer> highlighted_node = new ArrayList<>();
            for(Action action: actionsList) {
                int targetID = ((MoveTokenAction) action).getTargetId();
                highlighted_node.add(targetID);
            };
            if (DEBUG){
                System.out.println("IM HERE from Normal with token2");
            }
            if (clicked && prevNodeId != nodeId){
                ArrayList<Action> previousNodeAction = checkLegalMove.getCurrentActions().get(prevNodeId);
                unhighlightSelectedToken(prevNodeId,previousNodeAction,currentActor,nextActor);
            }

            // add unhighlight action to mask to the current node
            addUnhighlightSelectedToken(nodeId, actionsList, currentActor, nextActor);
            clicked = true;
            prevNodeId = nodeId;
            // put legal image
            for(Action action: actionsList){
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
            sceneEditor.removeImage(nodeId, "legalMove");
        }
        // remove transparent mask on all nodes
        for(app.nmm.Logic.Location.Node node: this.nodeList){
            int nodeId = node.getId();
            sceneEditor.removeImage(nodeId,"transparent_mask");
        }
        //Remove mill if the token that is being moved is part of mill
        int nodeId = action.getNodeId();
        ArrayList<ArrayList<Integer>> possibleMillPosition = checkMill.getMillNodes(nodeId);
        String tokenColour =  currentActor.getTokenColour();
        // change the graphic of the token
        String paths = getTokenImagePath(tokenColour+"_Token.png");

        // Loop through each of the possible mill set
        for(int i = 0; i < possibleMillPosition.size(); i++){
            // Check each position if mill exist
            for(int j = 0; j < possibleMillPosition.get(i).size(); j++){
                int id  = possibleMillPosition.get(i).get(j);
                // Check if the token in the node is belonged to the current used
                if(nodeList.get(id).getToken()!= null && nodeList.get(id).getToken().getColour().equals(currentActor.getTokenColour())){
                    //Check if the token is part of a mill
                    if (nodeList.get(id).getToken().getIsMill()){

                        // Check if mill is belong to the horizontal or vertical mill set and change the mill status into false
                        if( i == 0 ){

                            nodeList.get(id).getToken().setMillHorizontal(false);
                        }
                        else{
                            nodeList.get(id).getToken().setMillVertical(false);
                        }

                        // Check if the current token is still part of a mill set.
                        if (!nodeList.get(id).getToken().getIsMill()){
                            // change the token image if it is not part of the mill set
                            sceneEditor.changeTokenImage(id, tokenColour, paths, 18,0);
                        }
                    }
                }
                else{
                    break;
                }
            }
            //Change the status of mill in the token that is being moved to false if it is belonged to any mill set.
            if( i == 0 ){
                nodeList.get(nodeId).getToken().setMillHorizontal(false);
            }
            else{
                nodeList.get(nodeId).getToken().setMillVertical(false);
            }
        }

        // move the token
        action.execute(currentActor, nodeList);
        // reset clicked
        clicked = false;
        prevNodeId = 0;
        // get node id
        // use node id to remove the token from the board
        nodeId = action.getNodeId();
        ObservableList<Node> childList =  sceneEditor.getChildList(nodeId);
        while (childList.size() > 1) {
            Node node = childList.get(childList.size() - 1);
            childList.remove(node);
        }
        // get node id
        // add token to new location
        int targetId = ((MoveTokenAction)action).getTargetId();
        String path = getTokenImagePath(tokenColour+"_Token.png");
        String tokenID =  tokenColour+"token" +targetId;
        sceneEditor.addItemToBoard(path, tokenID,0,0,18,18 ,targetId);


        // Check mill and highlight if the node form a mill
        targetId = ((MoveTokenAction)action).getTargetId();
        ArrayList<Boolean> isMill = checkMill.checkPossibleMill(nodeList,targetId);
        ArrayList<ArrayList<Integer>> millCombinationTokenPosition = checkMill.getMillNodes(targetId);
        // Check if there are any possible tokens to be removed on the board
        checkLegalMove.calculateLegalRemove(currentActor,nodeList);
        // If a mill is formed
        if((isMill.get(0)|| isMill.get(1))){
            swapTokenToMill(targetId, isMill, millCombinationTokenPosition);
        }

        if((isMill.get(0)|| isMill.get(1)) && checkLegalMove.getCurrentRemovables().size()>0) {


            // If there are removable opponent tokens
                showMoveRemoval(currentActor,nextActor);
        }
        else{
            if (nextActor.getNumberOfTokensOnBoard() < 3){
                endGame(currentActor, nextActor.getTokenColour() + " have less than 3 tokens");
            }

            if (nextActor.getNumberOfTokensOnBoard() > 3){
                // calculate for the allowable action for next actor
                checkLegalMove.calculateLegalMove(nextActor, nodeList);
            }
            else {
                // calculate for the allowable action for next actor
                checkLegalMove.calculateLegalFly(nextActor, nodeList);
            }
            Map<Integer, ArrayList<Action>> returnAction = checkLegalMove.getCurrentActions();
            if(! checkIfHaveLegalMove(returnAction,nextActor.getTokenColour())){
                this.endGame(nextActor,nextActor.getTokenColour() + " have no legal move");
            }



            // find if the next actor has any legal moves the player can play
            processNextActorTurn(nextActor, currentActor, returnAction);
        }

    }

    private void processNextActorTurn(Actor currentActor, Actor nextActor, Map<Integer, ArrayList<Action>> returnAction) {
        Action action;
        if (currentActor instanceof Computer){

            checkLegalMove.calculateLegalRemove(currentActor, nodeList);
            ArrayList<Action> removableAction = checkLegalMove.getCurrentRemovables();
            action = ((Computer) currentActor).getAction(returnAction, removableAction, nodeList, checkMill);

            moveTokenExecutor(action, currentActor, nextActor, new ArrayList<>());

        }
        else{
            // add the mask
            for (int i = 0; i < 24; i++){
                addChecker(i, returnAction, currentActor, nextActor);
            }
            gameStatus.setText(currentActor.getTokenColour()+ "'s Turn To Move");
        }
    }


    /**
     * a method to get a path of an image
     * @param imageName - the image name
     * @return - a path that for the image
     */
    public String getTokenImagePath(String imageName){
        String path;
        if (System.getProperty("os.name").charAt(0) == 'W'){
            path = windowsResourcePath + imageName;
        }
        else{
            path = macResourcePath + imageName;
        }
        return path;
    }


    /***
     *  A method that swap the image of the token to mill graphic if mill form
     * @param nodeId node that is being executed
     * @param isMill list of boolean indicating if the particular mill set form a mill
     * @param millCombinationTokenPosition list of possible mill set
     */
    private void swapTokenToMill(int nodeId, ArrayList<Boolean> isMill, ArrayList<ArrayList<Integer>> millCombinationTokenPosition) {
        String tokenColour =  this.nodeList.get(nodeId).getToken().getColour();
        String paths = getTokenImagePath(tokenColour+"_Token_with_Mill.png");
        // Loop through each mill set
        for (int i = 0; i < isMill.size(); i++){
            // Check if the mill set form a mill
            if (isMill.get(i)){
                // Loop through each node id in the possible mill set list
                for(int currentNodeId : millCombinationTokenPosition.get(i)){
                    // Update the status of the token according to its mill direction(Horizontal, Vertical)
                    if (i == 0){
                        nodeList.get(currentNodeId).getToken().setMillHorizontal(true);
                        nodeList.get(nodeId).getToken().setMillHorizontal(true);
                    }
                    else
                    {
                        nodeList.get(currentNodeId).getToken().setMillVertical(true);
                        nodeList.get(nodeId).getToken().setMillVertical(true);
                    }
                    // Change the image of the token to graphic will mill highlight
                    sceneEditor.changeTokenImage(currentNodeId,tokenColour,paths,24,-3);
                }
            }
        }
        // Change the current token to graphic with mill highlight
        sceneEditor.changeTokenImage(nodeId,tokenColour,paths,24,-4);
    }

    /**
     * Main method used to call putRemoveTokenImage repeatedly to place relevant images on board
     * @param currentActor Current Player
     * @param otherActor Other Player
     */
    @FXML
    private void showPutRemovable(Actor currentActor, Actor otherActor){
        // Get the available remove token actions
        ArrayList<Action> returnAction = checkLegalMove.getCurrentRemovables();
        // Places an image to indicate that token is removable for each action retrieved.
        for (Action action : returnAction){
            int id = action.getNodeId();
            putRemoveTokenImage(id, action, currentActor, otherActor);
        }
        // Update current game status
        gameStatus.setText(currentActor.getTokenColour()+ " Select a Token to Remove.");
        sceneEditor.updateTokenCount(whiteTokenCount, blackTokenCount, currentActor.getTokenColour(), currentActor.getNumberOfTokensOnBoard());
    }

    /**
     * Method to put images for token that can be removed on board(put phase)
     * @param nodeId The node of token that can be removed
     * @param action Remove action
     * @param currentActor Current Player
     * @param otherActor Next Player
     */
    @FXML
    private void putRemoveTokenImage(int nodeId,Action action, Actor currentActor, Actor otherActor ){
        // Get the color of the removable token, create path to relevant image
        String tokenColour = nodeList.get(nodeId).getToken().getColour();
        String paths = getTokenImagePath(tokenColour+"_Token_for_removing_token.png");
        String removeTokenID = "removeToken"; //FxID for image
        // Adding image to the board
        ImageView imageView = sceneEditor.addItemToBoard(paths,removeTokenID,-3,-3,24,24,nodeId);
        // On-click event
        imageView.setOnMouseClicked(event ->{
            putRemoveTokenExecutor(action, currentActor, otherActor);
        });
    }

    /**
     * Method that is called when the image created by putRemoveTokenImage is clicked.
     * Removes the image and removes the token on the board, then proceeds to the next player (Put Phase)
     * @param action RemoveAction on a specific token on a node
     * @param currentActor Current Player
     * @param nextActor Next Player
     */
    void putRemoveTokenExecutor(Action action, Actor currentActor, Actor nextActor){
        // Remove all the Removable Token images on board based on FxID of image assigned earlier
        for (int i=0; i<24; i++){
            sceneEditor.removeImage(i, "removeToken");
        }
        // Remove token action is called, update token count for opponent
        action.execute(nextActor, nodeList);

        // Get nodeId for token that is to be removed
        int nodeId = action.getNodeId();
        // Removal of said token on board
        ObservableList<Node> childList =  sceneEditor.getChildList(nodeId);
        while (childList.size() > 1) {
            Node node = childList.get(childList.size() - 1);
            childList.remove(node);
        }
        //Update token count in UI accordingly
        if(nextActor.getTokenColour().equals("White")){
            whiteTokenCount.setText(Integer.toString(nextActor.getNumberOfTokensOnBoard()));
            blackTokenCount.setText(Integer.toString(currentActor.getNumberOfTokensOnBoard()));
        }
        else {
            blackTokenCount.setText(Integer.toString(nextActor.getNumberOfTokensOnBoard()));
            whiteTokenCount.setText(Integer.toString(currentActor.getNumberOfTokensOnBoard()));
        }
        // update the current player status
        if (currentActor.getNumberOfTokensInHand() == 0){
            currentActor.updateStatus(Capability.NORMAL);
        }
        // find if need to continue put token
        if (playerList.get(1).getStatus()==Capability.PUT_TOKEN){


            if (nextActor instanceof Computer){
                ArrayList<Action> allowableAction =  checkLegalMove.calculateLegalPut(nodeList);
                checkLegalMove.calculateLegalRemove(nextActor,nodeList);
                ArrayList<Action> removableActions =  checkLegalMove.getCurrentRemovables();
                action =  ((Computer) nextActor).getAction(allowableAction,removableActions,nodeList,checkMill);
                putTokenExecutor(action,nextActor,currentActor);
            }
            else
            {
                // continue put
                showLegalPut(nextActor, currentActor);
            }

        }
        else{
            // start normal gameplay
            normalGamePlay();
        }
    }

    /**
     * Main method used to call moveRemoveTokenImage repeatedly to place relevant images on board
     * @param currentActor Current Player
     * @param otherActor Next Player
     */
    @FXML
    private void showMoveRemoval(Actor currentActor, Actor otherActor){
        // Get the available remove token actions
        ArrayList<Action> returnAction = checkLegalMove.getCurrentRemovables();
        //Places an image to indicate that token is removable for each action retrieved
        for (Action action : returnAction){
            int id = action.getNodeId();
            moveRemoveTokenImage(id, action, currentActor, otherActor);
        }
        // Update current game status
        gameStatus.setText(currentActor.getTokenColour()+ " Select a Token to Remove.");
    }

    /**
     * Method to put images for token that can be removed on board(move phase)
     * @param nodeId The node of token that can be removed
     * @param action Remove action
     * @param currentActor Current Player
     * @param otherActor Next Player
     */
    @FXML
    private void moveRemoveTokenImage(int nodeId,Action action, Actor currentActor, Actor otherActor ){
        // Removal of the mask for each node so that the player is enforced to only remove opponent tokens if available
        for (int i=0; i<24; i++){
            sceneEditor.removeImage(i,"transparent_mask");
        }
        // Get color of removable token, create path to relevant image
        String tokenColour = nodeList.get(nodeId).getToken().getColour();
        String paths = getTokenImagePath(tokenColour+"_Token_for_removing_token.png");
        String removeTokenID = "removeToken";
        // Adding image to the board
        ImageView imageView = sceneEditor.addItemToBoard(paths,removeTokenID,-3,-3,24,24,nodeId);
        // On-click event
        imageView.setOnMouseClicked(event ->{
            moveRemoveTokenExecutor(action, currentActor, otherActor);
        });
    }
    /**
     * Method that is called when the image created by moveRemoveTokenImage is clicked.
     * Removes the image and removes the token on the board, then proceeds to the next player (Move Phase)
     * @param action RemoveAction on a specific token on a node
     * @param currentActor Current Player
     * @param nextActor Next Player
     */
    void moveRemoveTokenExecutor(Action action, Actor currentActor, Actor nextActor){
        // Remove all the Removable Token images on board based on FxID of image assigned earlier
        for (int i=0; i<24; i++){
            sceneEditor.removeImage(i, "removeToken");
        }
        // Remove token action is called, update token count for opponent
        action.execute(nextActor, nodeList);
        // Get nodeId for token that is to be removed
        int nodeId = action.getNodeId();
        // Removal of said token on board
        ObservableList<Node> childList =  sceneEditor.getChildList(nodeId);
        while (childList.size() > 1) {
            Node node = childList.get(childList.size() - 1);
            childList.remove(node);

        }
        //Update token count in UI accordingly
        sceneEditor.updateTokenCount(whiteTokenCount, blackTokenCount, nextActor.getTokenColour(), nextActor.getNumberOfTokensOnBoard());

        if (nextActor.getNumberOfTokensOnBoard() < 3){
            endGame(currentActor, nextActor.getTokenColour() + " have less than 3 tokens");
        }

        if (nextActor.getNumberOfTokensOnBoard() > 3){
            // calculate for the allowable action for next actor
            checkLegalMove.calculateLegalMove(nextActor, nodeList);
        }
        else {
            // calculate for the allowable action for next actor
            checkLegalMove.calculateLegalFly(nextActor, nodeList);
        }

        if(! checkIfHaveLegalMove(checkLegalMove.getCurrentActions(),nextActor.getTokenColour())){
            this.endGame(nextActor,nextActor.getTokenColour() + " have no legal move");
        }


        Map<Integer, ArrayList<Action>> returnAction = checkLegalMove.getCurrentActions();
        // find if the next actor has any legal moves the player can play
        processNextActorTurn(nextActor, currentActor, returnAction);

    }
    /**
     * a method to call the removal of legal moves of one token on board when running normal game
     * @param currentNodeID
     * @param actionsList
     * @param currentActor
     * @param nextActor
     */
    void addUnhighlightSelectedToken(int currentNodeID, ArrayList<Action> actionsList, Actor currentActor, Actor nextActor){
        ObservableList<Node> childList =  sceneEditor.getChildList(currentNodeID);
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
            sceneEditor.removeImage(targetID, "legalMove");
        }
        ObservableList<Node> childList =  sceneEditor.getChildList(currentNodeID);
        Node node = childList.get(childList.size() - 1);

        if (DEBUG){
            System.out.println("change the image's action.");
        }

        String tokenColour =  nodeList.get(currentNodeID).getToken().getColour();
        if (nodeList.get(currentNodeID).getToken().getIsMill()){
           String paths = getTokenImagePath(tokenColour+"_Token_with_Mill.png");
            sceneEditor.changeTokenImage(currentNodeID,tokenColour,paths,24,-3);
        }
        else{
            String paths = getTokenImagePath(tokenColour+"_Token.png");
            sceneEditor.changeTokenImage(currentNodeID,tokenColour,paths,18,0);
        }
        prevNodeId = 0;
        clicked = false;
        node.setOnMouseClicked(event -> {
            selectedToken(currentNodeID, currentActor, nextActor, actionsList);

        });

    }

    boolean checkIfHaveLegalMove(Map<Integer, ArrayList<Action>> actionList, String actorColor){
        boolean moveAction = false;
        for (int i = 0; i < 24; i++){
            ArrayList<Action> action = actionList.get(i);
            if (nodeList.get(i).getToken() != null && nodeList.get(i).getToken().getColour().equals(actorColor)){
                if (action.size()>0){
                    moveAction = true;
                    break;
                }
            }
        }
        return moveAction;
    }


}
