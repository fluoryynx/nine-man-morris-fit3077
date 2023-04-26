package app.nmm.Logic.Actor;

import app.nmm.Logic.Capability.Capability;



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

    public String getTokenColour() {
        return this.tokenColour;
    }

    public int getNumberOfTokensInHand() {
        return numberOfTokensInHand;
    }

}
