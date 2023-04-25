package app.nmm.Logic.Actor;


import app.nmm.Controller.GameController;
import app.nmm.Logic.Capability.Capability;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class Actor implements Observer {

    private final String tokenColour;

    public String getPlayerName() {
        return playerName;
    }

    private final String playerName;



    private Enum<Capability> status;
    private  int numberOfTokensOnBoard;



    private  int numberOfTokensInHand;


    public Actor(String tokenColour, String playerName) {
        this.tokenColour = tokenColour;
        this.playerName = playerName;

    }
    public void subtractTokenInHand(){
        this.numberOfTokensInHand -= 1;
    }

    public  void subtractTokenOnBoard(){
        this.numberOfTokensOnBoard -= 1;
    }
    public void  addTokenOnBoard(){
        this.numberOfTokensOnBoard += 1;
    }

    public int getNumberOfTokensOnBoard() {
        return numberOfTokensOnBoard;
    }

    public int getNumberOfTokensInHand() {
        return numberOfTokensInHand;
    }

    public void updateStatus(Enum<Capability> status){
        this.status = status;
    }


    public Enum<Capability> getStatus() {
        return status;
    }

    public abstract CompletableFuture<Action> playTurn(Map<Integer,List<Action>> allowableActions, GameController controller);

    public abstract CompletableFuture<Action> playTurn(ArrayList<Action> allowableActions,GameController controller);

}


