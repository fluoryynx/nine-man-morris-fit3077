//package app.nmm.Logic.Engine;
//
//import app.nmm.Application;
//import app.nmm.Controller.GameController;
//import app.nmm.Logic.Action.Action;
//import app.nmm.Logic.Actor.Actor;
//import app.nmm.Logic.Actor.Computer;
//import app.nmm.Logic.Actor.Player;
//import app.nmm.Logic.Handler.CheckLegalMove;
//import app.nmm.Logic.Handler.CheckMill;
//import app.nmm.Logic.Location.Node;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.layout.Pane;
//import javafx.util.Pair;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Engine {
//
//    private ArrayList<Node> nodeList;
//    private CheckMill checkMill;
//    private CheckLegalMove checkLegalMove;
//    private  ArrayList<Actor> playerList;
//    private ArrayList<Pair<Integer,Integer>> locationList;
//
//    public Engine(){
//        this.playerList = new ArrayList<Actor>();
//        this.nodeList =  new ArrayList<Node>();
//        this.checkMill =  new CheckMill();
//        this.checkLegalMove = new CheckLegalMove();
//        this.locationList= new ArrayList<Pair<Integer,Integer>>();
//        this.addLocation();
//
//        // Create all the node with its specific location and id
//        for(int i = 0; i < 24; i++){
//            this.nodeList.add(new Node(i,this.locationList.get(i)));
//        }
//
//    }
//
//    @FXML
//    public void createPlayerPVPMode(String winnerName, String loserName) throws IOException {
//        //Add Players into the game
//        Player p1 = new Player("white", winnerName);
//        Player p2 = new Player("black", loserName);
//        this.playerList.add(p1);
//        this.playerList.add(p1);
//
//        controller.addPlayer(p1);
//        controller.addPlayer(p2);
//    }
//
//    public void createPlayerPVCMode(String playerName) {
//        //Add Player and Computer into the game
//        this.playerList.add(new Player("white", playerName));
//        this.playerList.add(new Computer("black", "Comp"));
//    }
//
//    private void addLocation(){
//        this.locationList.add(new Pair(205,186));
//        this.locationList.add(new Pair(315,186));
//        this.locationList.add(new Pair(425,186));
//        this.locationList.add(new Pair(231,211));
//        this.locationList.add(new Pair(315,211));
//        this.locationList.add(new Pair(399,211));
//        this.locationList.add(new Pair(257,236));
//        this.locationList.add(new Pair(315,236));
//        this.locationList.add(new Pair(373,236));
//        this.locationList.add(new Pair(205,296));
//        this.locationList.add(new Pair(231,296));
//        this.locationList.add(new Pair(257,296));
//        this.locationList.add(new Pair(373,296));
//        this.locationList.add(new Pair(399,296));
//        this.locationList.add(new Pair(425,296));
//        this.locationList.add(new Pair(257,353));
//        this.locationList.add(new Pair(315,353));
//        this.locationList.add(new Pair(373,353));
//        this.locationList.add(new Pair(231,380));
//        this.locationList.add(new Pair(315,380));
//        this.locationList.add(new Pair(399,380));
//        this.locationList.add(new Pair(205,406));
//        this.locationList.add(new Pair(315,406));
//        this.locationList.add(new Pair(425,406));
//
//    }
//
//
//    public void executeAction(Action action){
//        action.execute();
//    }
//
//    /**
//     * This is the method that will run the game until one of the player does not have any legal moves or until one of the player have less than 3 token in the board.
//     */
//    public void gamePlay() throws IOException {
//        boolean stop = false;
//
//        while(!stop){
//            for (Actor actor : this.playerList) {
//                Pair result = this.checkLegalMove.calculateLegalMove(actor, this.nodeList);
//                HashMap allowableActions = (HashMap) result.getKey();
//                HashMap removeActions =  (HashMap) result.getValue();
//
//                if (allowableActions.size() != 0){
//                    stop = true;
//                    break;
//                }
//
//                Action action = actor.playTurn(allowableActions);
//                action.execute();
//
//            }
//        }
//    }
//}
//
