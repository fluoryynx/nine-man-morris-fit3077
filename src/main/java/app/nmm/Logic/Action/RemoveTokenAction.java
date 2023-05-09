package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

import java.util.List;

public class RemoveTokenAction extends Action{
    /**
     *
     * @param nodeId ID of node that current player wants to select to remove the token on said node
     */
    public RemoveTokenAction(int nodeId) {
        super(nodeId);
    }

    /**
     * This method removes the token that is on the node that the player selected
     * @param opposingPlayer The opponent
     * @param nodeList List of nodes
     */
    @Override
    public void execute(Actor opposingPlayer, List<Node> nodeList) {
    nodeList.get(getNodeId()).removeToken();
        opposingPlayer.subtractTokenOnBoard();
    }

}
