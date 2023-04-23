package app.nmm.Logic.Actor;

import app.nmm.Logic.Action.Action;

import java.util.List;
import java.util.Map;

public class Computer extends Actor{
    public Computer(String tokenColour) {
        super(tokenColour);
    }

    @Override
    public Action playTurn(Map<Integer,List<Action>> actionList) {
        return null;
    }
}
