package app.nmm.Logic.Actor;


import app.nmm.Action.Action;
import app.nmm.Logic.Capability.Capability;

import java.util.List;
import java.util.Map;

public abstract class Actor {

    private final String tokenColour;
    private Enum<Capability> status;
    private  int numberOfTokensOnBoard;
    private  int numberOfTokensInHand;


    public Actor(String tokenColour) {
        this.tokenColour = tokenColour;
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
    public void updateStatus(Enum<Capability> status){
        this.status = status;
    }

    public abstract Action playTurn(Map<Integer,List<Action>> actionList);

}


