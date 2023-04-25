package app.nmm.Logic.Action;

public class PutTokenAction extends Action{

    public PutTokenAction(int nodeId) {
        super(nodeId);
    }

    @Override
    public Action newAction() {
        if (Actor.status == Capability.valueOf(PUT) && Node.hasToken()!= true)
        {
            return new PutTokenAction(getNodeId());
        }
        return null
    }

    @Override
    public void execute() {
        Actor.subtractTokenInHand();
        Actor.addTokenOnBoard();
        Node.addToken();

    }
}
