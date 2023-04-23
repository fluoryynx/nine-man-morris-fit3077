package app.nmm.Logic.Action;

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
        Node(targetId).addToken((getNodeId()).getToken());
        Node(targetId).setId(getNodeId());
        Node(getNodeId()).removeToken();
    }
}
