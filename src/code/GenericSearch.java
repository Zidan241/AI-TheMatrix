package code;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class GenericSearch {
    String[] operators;
    State initialState;
    public GenericSearch(String[] operators, State initialState){
        this.operators = operators;
        this.initialState = initialState;
    }
    int nodesExpanded = 0;
    abstract boolean GoalTest(State currentState);
    abstract int PathCost(State state, State nextState);
    abstract State ApplyOperator(State state, String operator);

    public static ArrayList<SearchTreeNode> Expand(GenericSearch problem, SearchTreeNode node){
        ArrayList<SearchTreeNode> children = new ArrayList<SearchTreeNode>();
        for(int i = 0; i < problem.operators.length; i++){
            String operator = problem.operators[i];
            State nextState = problem.ApplyOperator(node.state, operator);
            if(nextState !=null){
                //to calculate the path cost we sent the parent's state and the new node state to be able to
                //calculate the number of deaths and kills that occured in this time step
                int pathCost = node.pathCost + problem.PathCost(node.state, nextState);
                int depth = node.depth + 1;
                SearchTreeNode child = new SearchTreeNode(nextState, node, operator, depth, pathCost);
                children.add(child);
            }
        }
        return children;
    }

    public static SearchTreeNode GenericSearchProcedure(GenericSearch problem, String seatchStrategy){
        LinkedList<SearchTreeNode> queue = new LinkedList<SearchTreeNode>(); 
        SearchTreeNode initialNode = new SearchTreeNode(problem.initialState, null, null, 0, 0);
        queue.add(initialNode);
        while(true){
            
            if(queue.isEmpty())
                return null;
            
            SearchTreeNode currentNode = queue.removeFirst();
            //we increase the number of nodes expanded everytime we dequeue a node from the queue
            problem.nodesExpanded++;

            if(problem.GoalTest(currentNode.state)){
                return currentNode;
            }
            else{
                switch (seatchStrategy){
                    case "BF":
                        BFS(queue, Expand(problem, currentNode));
                    break;
                    case "DF":
                    break;
                    case "ID":
                    break;
                    case "UC":
                    break;
                    default:
                    if(seatchStrategy.substring(0,2)=="GR"){
                    
                    }
                    else {
                        if (seatchStrategy.substring(0,2)=="AS"){
        
                        }
                    }
                    break;          
                }
            }
        }
    }
    public static void BFS(LinkedList<SearchTreeNode> queue , ArrayList<SearchTreeNode> nodes){
        for(int i =0;i<nodes.size();i++){
            queue.addLast(nodes.get(i));
            //System.out.println(nodes.get(i));
        }
    }
}
