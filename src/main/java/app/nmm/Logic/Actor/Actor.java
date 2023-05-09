package app.nmm.Logic.Actor;

import app.nmm.Logic.Capability.Capability;

public abstract class Actor {

    private final String tokenColour;
    private final String actorname;
    private int actorID;
    private Enum<Capability> status;
    private int numberOfTokensOnBoard;
    private int numberOfTokensInHand;


    public Actor(String tokenColour, String playerName, Integer actorID) {
        this.tokenColour = tokenColour;
        this.actorname = playerName;
        this.status = Capability.PUT_TOKEN;
        this.numberOfTokensInHand = 9;
        this.numberOfTokensOnBoard = 0;
        this.actorID = actorID;
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

    public String getActorname() {
        return actorname;
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

    public boolean checkLose(){
        if (numberOfTokensOnBoard<3 &&  status == Capability.NORMAL){
            return true;
        }
        else{
            return false;
        }
    }

}
