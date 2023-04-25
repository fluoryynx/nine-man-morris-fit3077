package app.nmm.Logic.Actor;


import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Capability.Capability;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Actor {

    private final String tokenColour;
    private final String playerName;


    private int actorID;
    private Enum<Capability> status;
    private  int numberOfTokensOnBoard;
    private  int numberOfTokensInHand;


    public Actor(String tokenColour, String playerName, Integer actorID) {
        this.tokenColour = tokenColour;
        this.playerName = playerName;
        this.status = Capability.PUT_TOKEN;
        this.numberOfTokensInHand = 9;
        this.actorID = actorID;
    }

    public int getID() { return actorID;}
    public void subtractTokenInHand(){
        this.numberOfTokensInHand -= 1;
    }

    public  void subtractTokenOnBoard(){
        this.numberOfTokensOnBoard -= 1;
    }

    public void  addTokenOnBoard(){
        this.numberOfTokensOnBoard += 1;
    }

    public void updateStatus(Enum<Capability> status){
        this.status = status;
    }

    public Enum<Capability> getStatus(){return this.status;}

    public abstract Action playTurn(Map<Integer,List<Action>> allowableActions, GameController engine);

    public String getTokenColour() {
        return this.tokenColour;
    }

    public int getNumberOfTokensInHand() {
        return numberOfTokensInHand;
    }

}
