package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Capability.Capability;
import app.nmm.Logic.Location.Node;

import java.util.List;

public class MoveTokenAction extends Action{
    public int getTargetId() {
        return targetId;
    }

    private int targetId;
    //nodeId is the current node, targetId is the node you want to move to.
    public MoveTokenAction(int nodeId, int targetId) {
        super(nodeId);
        this.targetId = targetId;
    }


    @Override
    public void execute(Actor currentActor, List<Node> nodeList) {
        nodeList.get(targetId).addToken(nodeList.get(getNodeId()).getToken());
        nodeList.get(getNodeId()).removeToken();
    }
}
