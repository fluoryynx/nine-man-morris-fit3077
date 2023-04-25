package app.nmm.Logic.Action;

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
