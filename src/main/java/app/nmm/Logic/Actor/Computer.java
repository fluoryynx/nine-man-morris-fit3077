package app.nmm.Logic.Actor;

import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Computer extends Actor{


    public Computer(String tokenColour, String playerName) {
        super(tokenColour, playerName);
    }

    @Override
    public CompletableFuture<Action> playTurn(Map<Integer, List<Action>> allowableActions, GameController controller) {
        return null;
    }

    @Override
    public CompletableFuture<Action> playTurn(ArrayList<Action> allowableActions, GameController controller) {
        return null;
    }

    @Override
    public void sendUserAction(Integer nodeID) {

    }
}
