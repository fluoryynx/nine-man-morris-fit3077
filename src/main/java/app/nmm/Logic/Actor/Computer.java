package app.nmm.Logic.Actor;

import app.nmm.Controller.GameController;
import app.nmm.Logic.Action.Action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Computer extends Actor{


    public Computer(String tokenColour, String playerName) {
        super(tokenColour, playerName);
    }

    @Override
    public Action playTurn(Map<Integer, List<Action>> allowableActions, GameController engine) throws IOException {
        return null;
    }
}
