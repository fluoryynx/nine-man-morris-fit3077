package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Capability.Capability;
import app.nmm.Logic.Location.Node;

public class MoveTokenAction extends Action{
    private int targetId;

    public MoveTokenAction(int nodeId, int targetId) {
        super(nodeId);
        this.targetId = targetId;
    }


    @Override
    public void execute() {
        Node currentNode = new Node(nodeId)
        Node(targetId).addToken(getNodeId());
        Node(nodeId).removeToken();

        Node(getNodeId()).removeToken();
    }
}
