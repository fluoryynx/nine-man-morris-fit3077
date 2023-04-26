package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;
import app.nmm.Logic.Token.Token;

import java.util.List;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

public class PutTokenAction extends Action{

    public PutTokenAction(int nodeId) {
        super(nodeId);
    }


    @Override
    public void execute(Actor currentPlayer, List<Node> nodeList) {
        currentPlayer.subtractTokenInHand();
        currentPlayer.addTokenOnBoard();
        nodeList.get(super.getNodeId()).addToken(new Token(false,currentPlayer.getTokenColour()));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
