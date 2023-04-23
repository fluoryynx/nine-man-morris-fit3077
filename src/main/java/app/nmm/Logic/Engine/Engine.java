package app.nmm.Logic.Engine;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Actor.Computer;
import app.nmm.Logic.Actor.Player;
import app.nmm.Logic.Handler.CheckLegalMove;
import app.nmm.Logic.Handler.CheckMill;
import app.nmm.Logic.Location.Node;

import java.util.ArrayList;

public class Engine {

    private ArrayList<Node> nodeList;
    private CheckMill checkMill;
    private CheckLegalMove checkLegalMove;
    private  ArrayList<Actor> playerList;

    public Engine(){
        this.playerList = new ArrayList<Actor>();
        this.nodeList =  new ArrayList<Node>();
        this.checkMill =  new CheckMill();
        this.checkLegalMove = new CheckLegalMove();

        for(int i = 0; i < 24; i++){

        }

    }

    public void createPlayerPVPMode(String winnerName, String loserName) {
        //Add Players into the game
        this.playerList.add(new Player("white", winnerName));
        this.playerList.add(new Player("black", loserName));
    }

    public void createPlayerPVCMode(String playerName) {
        //Add Player and Computer into the game
        this.playerList.add(new Player("white", playerName));
        this.playerList.add(new Computer("black", "Comp"));
    }


}


//+ executeAction(Action): void
//+ gamePlay(): void
