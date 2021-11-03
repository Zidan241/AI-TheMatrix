public class SearchTreeNode {
    String state;
    SearchTreeNode parentNode;
    String operator;
    int depth;
    int pathCost;
    public SearchTreeNode(String state, SearchTreeNode parentNode, String operator, int depth,  int pathCost){
        this.state = state;
        this.parentNode = parentNode;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
}
