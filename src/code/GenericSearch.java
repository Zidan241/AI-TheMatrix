package code;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
    abstract int PathCost(State state , int depth);
    abstract int Heuristic1(State state);
    abstract int Heuristic2(State state);
    abstract State ApplyOperator(State state, String operator);

    public static ArrayList<SearchTreeNode> Expand(String seatchStrategy, GenericSearch problem, SearchTreeNode node, HashSet<String> previousStates){
        ArrayList<SearchTreeNode> children = new ArrayList<SearchTreeNode>();
        for(int i = 0; i < problem.operators.length; i++){
            String operator = problem.operators[i];
            State nextState = problem.ApplyOperator(node.state, operator);
            if(nextState !=null){
                //to avoid repeated states check that i didn't reach this state before
                if(!previousStates.contains(nextState.toString())){
                    //to calculate the path cost we sent the parent's state and the new node state to be able to
                    //calculate the number of deaths and kills that occured in this time step
                    previousStates.add(nextState.toString());
                    int depth = node.depth + 1;
                    int pathCost = problem.PathCost(nextState,depth);
                    SearchTreeNode child;
                    if(seatchStrategy.substring(0,2).equals("GR")){
                        if(seatchStrategy.charAt(2)=='1'){
                            child = new SearchTreeNode(nextState, node, operator, depth, pathCost, problem.Heuristic1(nextState));
                        }else{
                            child = new SearchTreeNode(nextState, node, operator, depth, pathCost, problem.Heuristic2(nextState));
                        }                   
                    }
                    else {
                        if (seatchStrategy.substring(0,2).equals("AS")){
                            if(seatchStrategy.charAt(2)=='1'){
                                child = new SearchTreeNode(nextState, node, operator, depth, pathCost, problem.Heuristic1(nextState));           
                            }
                            else{
                                child = new SearchTreeNode(nextState, node, operator, depth, pathCost, problem.Heuristic2(nextState));
                            }        
                        }
                        else{
                            child = new SearchTreeNode(nextState, node, operator, depth, pathCost, 0);
                        }
                    }
                   
                    children.add(child);

                }
            }
        }
        return children;
    }

    public static SearchTreeNode GenericSearchProcedure(GenericSearch problem, String seatchStrategy){
        LinkedList<SearchTreeNode> queue = new LinkedList<SearchTreeNode>();
        HashSet<String> previousStates = new HashSet<String>();
        SearchTreeNode initialNode;
        // intialize the search tree node with the root state's heurisitc or 0 if no heurisitic is needed 
        if(seatchStrategy.substring(0,2).equals("GR")){
            if(seatchStrategy.charAt(2)=='1'){
                initialNode = new SearchTreeNode(problem.initialState, null, null, 0, 0, problem.Heuristic1(problem.initialState));
            }
            else{
                initialNode = new SearchTreeNode(problem.initialState, null, null, 0, 0, problem.Heuristic2(problem.initialState));
            }                   
        }
        else {
            if (seatchStrategy.substring(0,2).equals("AS")){
                if(seatchStrategy.charAt(2)=='1'){
                    initialNode = new SearchTreeNode(problem.initialState, null, null, 0, 0, problem.Heuristic1(problem.initialState));              
                }
                else{
                    initialNode = new SearchTreeNode(problem.initialState, null, null, 0, 0, problem.Heuristic2(problem.initialState));
                }        
            }else{
                initialNode = new SearchTreeNode(problem.initialState, null, null, 0, 0, 0);
            }
        }
        queue.add(initialNode);
        int depth=0;
        while(true){
            
            if(queue.isEmpty()){
                if(seatchStrategy.equals("ID")){
                    depth++;
                    queue.add(initialNode);
                    previousStates.clear();;
                }
                else{
                    return null;
                }
            }
            
            SearchTreeNode currentNode = queue.removeFirst();
            //we increase the number of nodes expanded everytime we dequeue a node from the queue
            problem.nodesExpanded++;

            if(problem.GoalTest(currentNode.state)){
                return currentNode;
            }
            else{
                switch (seatchStrategy){
                    case "BF":
                        BFS(queue, Expand(seatchStrategy, problem, currentNode, previousStates));
                    break;
                    case "DF":
                        DFS(queue, Expand(seatchStrategy, problem, currentNode, previousStates));
                    break;
                    case "ID":
                        if(currentNode.depth < depth){
                            DFS(queue, Expand(seatchStrategy, problem, currentNode, previousStates));
                        }
                    break;
                    case "UC":
                        UCS(queue, Expand(seatchStrategy, problem, currentNode, previousStates));
                    break;
                    default:
                    if(seatchStrategy.substring(0,2).equals("GR")){
                        Greedy(queue, Expand(seatchStrategy, problem, currentNode, previousStates));                   
                    }
                    else {
                        AStar(queue, Expand(seatchStrategy, problem, currentNode, previousStates));

                    }
                    break;          
                }
            }
        }
    }
    public static void BFS(LinkedList<SearchTreeNode> queue , ArrayList<SearchTreeNode> nodes){
        for(int i =0;i<nodes.size();i++){
            queue.addLast(nodes.get(i));
        }
    }
    public static void DFS(LinkedList<SearchTreeNode> queue , ArrayList<SearchTreeNode> nodes){
        for(int i =0;i<nodes.size();i++){
            queue.add(0,nodes.get(i));
        }
    }
    public static void UCS(LinkedList<SearchTreeNode> queue , ArrayList<SearchTreeNode> nodes){
        for(int i =0;i<nodes.size();i++){
            queue.add(0,nodes.get(i));
        } 
        queue.sort(new Comparator<SearchTreeNode>() {
            @Override
            public int compare(SearchTreeNode o1, SearchTreeNode o2) {
                return o1.pathCost - o2.pathCost;
            }
        });
    }

    public static void Greedy(LinkedList<SearchTreeNode> queue , ArrayList<SearchTreeNode> nodes){
        for(int i =0;i<nodes.size();i++){
            queue.add(0,nodes.get(i));
        } 
        queue.sort(new Comparator<SearchTreeNode>() {
            @Override
            public int compare(SearchTreeNode o1, SearchTreeNode o2) {          
                if(o1.heuristic < o2.heuristic){
                    return -1;
                }else{
                    if(o1.heuristic > o2.heuristic){
                        return 1;
                    }else{
                        return 0;
                    }

                }
                
            }
            
        });
    }
    public static void AStar(LinkedList<SearchTreeNode> queue , ArrayList<SearchTreeNode> nodes){
        for(int i =0;i<nodes.size();i++){
            queue.add(0,nodes.get(i));
        } 
        queue.sort(new Comparator<SearchTreeNode>() {
            @Override
            public int compare(SearchTreeNode o1, SearchTreeNode o2) {        
                if(o1.heuristic+o1.pathCost < o2.heuristic+o2.pathCost){
                    return -1;
                }else{
                    if(o1.heuristic+o1.pathCost > o2.heuristic+o2.pathCost){
                        return 1;
                    }else{
                        return 0;
                    }

                }
                
            }
            
        });
 
    }
}
