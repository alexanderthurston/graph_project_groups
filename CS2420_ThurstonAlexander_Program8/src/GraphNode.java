import java.util.*;

public class GraphNode {

    public GraphNode( ){
        this.nodeID = 0;
        this.succ = new LinkedList<EdgeInfo>();
        this.prevNode = 0;
        this.distance = INF;
     }

    public GraphNode(int nodeID){
        this.nodeID = nodeID;
        this.succ = new LinkedList<EdgeInfo>();
        this.prevNode = 0;
        this.distance = INF;
      }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID+ ": ");
        Iterator<EdgeInfo> itr =succ.iterator();
       while (itr.hasNext()){
            sb.append(itr.next().toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Adds EdgeInfo object to GraphNode successor list
     * @param v1 int from
     * @param v2 int to
     * @param capacity int capacity
     * @param cost int cost
     */
    public void addEdge(int v1, int v2, int capacity,int cost){
        succ.addFirst( new EdgeInfo(v1,v2,capacity,cost) );
    }

    /**
     * Get current capacity of particular edge. If doesn't exist return 0.
     * @param destination
     * @return current capacity
     */
    public int getCapacity(int destination){
       Iterator<EdgeInfo> itr = succ.iterator();
       while (itr.hasNext()){
           EdgeInfo e = itr.next();
           if (e.to == destination)
               return e.currCapacity;
       }
       return 0;
    }

    /**
     * Get cost of particular edge. If doesn't exist return 0.
     * @param destination
     * @return cost
     */
    public int getCost(int destination){
        Iterator<EdgeInfo> itr = succ.iterator();
        while (itr.hasNext()){
            EdgeInfo e = itr.next();
            if (e.to == destination)
                return e.cost;
        }
        return 0;
    }

    /**
     * Update current capacity of particular edge
     * @param destination
     * @param residualFlow
     */
    public void addResidualFlowTo(int destination, double residualFlow){
        Iterator<EdgeInfo> itr = succ.iterator();
        while (itr.hasNext()){
            EdgeInfo e = itr.next();
            if (e.to == destination)
                e.currCapacity -=  residualFlow;;
        }
    }

    static int INF = 9999;
    public int nodeID;
    public LinkedList<EdgeInfo> succ;
    public int prevNode;
    public int distance;

}
