package app.nmm.Logic.Action;

public class RemoveTokenAction extends Action{
    public RemoveTokenAction(int nodeId) {
        super(nodeId);
    }

    @Override
    public Action newAction() {
        if (Actor.status == Capability.valueOf(REMOVE) && Node.hasToken()!= true)
        {
            return new RemoveTokenAction(getNodeId());
        }
        return null;
    }

    @Override
    public void execute() {
        otherActor.subtractTokenOnBoard();
        Node.removeToken();
    }
}
