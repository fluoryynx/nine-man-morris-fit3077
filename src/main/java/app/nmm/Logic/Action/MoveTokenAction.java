package app.nmm.Logic.Action;

import app.nmm.Logic.Actor.Actor;
import app.nmm.Logic.Capability.Capability;
import app.nmm.Logic.Location.Node;

import java.util.List;

public class MoveTokenAction extends Action{
    private int targetId;

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
