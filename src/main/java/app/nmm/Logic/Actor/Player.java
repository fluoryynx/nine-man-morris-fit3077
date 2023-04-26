package app.nmm.Logic.Actor;

import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;
import app.nmm.Logic.Actor.Actor;

import java.util.List;
import java.util.Map;

public class Player extends Actor {
    public Player(String tokenColour, String playerName, Integer actorID) {
        super(tokenColour, playerName,actorID);
    }
}
