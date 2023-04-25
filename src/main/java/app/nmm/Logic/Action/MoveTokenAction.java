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
    public Action newAction() {
        if (Actor.status == Capability.valueOf(FLY) && Node(targetId).hasToken()!= true)
        {
            return new MoveTokenAction(getNodeId(),this.targetId);
        }
        return null;
    }

    @Override
    public void execute() {
        Node(targetId).removeToken();
        Node(targetId).addToken(getNodeId());
        Node(getNodeId()).removeToken();
    }

    private Node Node(int targetId) {
    }
}
