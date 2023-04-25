package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Location.Node;

import java.util.List;

public class RemoveTokenAction extends Action{
    public RemoveTokenAction(int nodeId) {
        super(nodeId);
    }

    @Override
    public void execute(Actor currentPlayer, List<Node> nodeList) {
    nodeList.get(getNodeId()).removeToken();
    currentPlayer.subtractTokenOnBoard();
    }

}
