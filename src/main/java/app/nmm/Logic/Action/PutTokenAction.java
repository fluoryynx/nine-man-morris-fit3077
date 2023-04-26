package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;
import app.nmm.Logic.Token.Token;

import java.util.List;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

public class PutTokenAction extends Action{
    /**
     *
     * @param nodeId NodeID of where the token will be placed
     */
    public PutTokenAction(int nodeId) {
        super(nodeId);
    }

    /**
     * This method creates an instance of the current player's token onto the specified node
     * Method is only available for the first 9 tokens placed by each player.
     * @param currentPlayer The player currently taking the turn
     * @param nodeList List of nodes
     */
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
