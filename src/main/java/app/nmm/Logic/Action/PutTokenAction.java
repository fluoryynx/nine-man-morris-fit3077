package app.nmm.Logic.Action;

public class PutTokenAction extends Action{

    public PutTokenAction(int nodeId) {
        super(nodeId);
    }


    @Override
    public void execute() {
        Actor.subtractTokenInHand();
        Actor.addTokenOnBoard();
        Node.addToken();

    }
}
