package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Capability.Capability;
import app.nmm.Logic.Location.Node;

import java.util.List;

public class MoveTokenAction extends Action{
    /**
     *
     * @return NodeID that the player wants to move the token to
     */
    public int getTargetId() {
        return targetId;
    }

    private int targetId;

    /**
     *
     * @param nodeId NodeID that the token is currently at
     * @param targetId NodeID that the token is to be moved to
     */
    public MoveTokenAction(int nodeId, int targetId) {
        super(nodeId);
        this.targetId = targetId;
    }

    /**
     *
     * @param currentActor Current player
     * @param nodeList List of nodes
     */
    @Override
    public void execute(Actor currentActor, List<Node> nodeList) {
        nodeList.get(targetId).addToken(nodeList.get(getNodeId()).getToken());
        nodeList.get(getNodeId()).removeToken();
    }
}
