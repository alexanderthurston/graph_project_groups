import java.util.ArrayList;
import java.util.LinkedList;

public class MinCostMaxFlow {
    FlowGraph flowGraph;
    ArrayList<LinkedList> paths = new ArrayList<>();
    int source;
    int sink;
    int maxFlow;

    /**
     * Overridden constructor to find paths and maximum flow using flowGraph
     * @param flowGraph FlowGraph
     * @param source int index of source
     * @param sink int index of sink
     */
    public MinCostMaxFlow(FlowGraph flowGraph, int source, int sink){
        this.flowGraph = flowGraph;
        this.source = source;
        this.sink = sink;
        this.maxFlow = FordFulkerson();
    }

    /**
     * Finds maximum flow on a path using the Ford-Fulkerson algorithm.
     * @return int maximum flow for the graph
     */
    public int FordFulkerson() {
        // while there exists an augmenting path, use it
        int value = 0;
        while (DijkstraNeg()) {
            LinkedList path = new LinkedList();
            // compute bottleneck capacity starting at terminal node
            double availFlow = Double.POSITIVE_INFINITY;
            for (int v = sink; v != source; v = flowGraph.G[v].prevNode) {
                path.addFirst(v);
                int u = flowGraph.G[v].prevNode;
                availFlow = Math.min(availFlow, flowGraph.getCapacity(u, v));
            }
            path.addFirst(source);

            // augment flow
            for (int v = sink; v != source; v = flowGraph.G[v].prevNode) {
                int u = flowGraph.G[v].prevNode;
                flowGraph.G[u].addResidualFlowTo(v, availFlow);
                flowGraph.G[v].addResidualFlowTo(u, -availFlow);
            }
            path.addFirst((int) availFlow);
            value += availFlow;
            paths.add(path);

        }
        return value;
    }

    /**
     * Determines if there is an augmenting path using version of Dijkstra's algorithm compatible with negative edges.
     * @return boolean if augmenting path was found
     */
    private boolean DijkstraNeg() {
        LinkedList<Integer> queue = new LinkedList<>(); // breadth-first search
        queue.addLast(source);// queue of all nodes to consider flow from
        clearMarks();
        flowGraph.G[source].distance = 0;
        while( !queue.isEmpty()){
            int v = queue.removeFirst();
            for (EdgeInfo e : flowGraph.G[v].succ) {
                int w = e.to;
                // if residual capacity from v to w – capture forward or backward capacity
                if(e.currCapacity > 0)
                    if(flowGraph.G[v].distance + flowGraph.getCost(v, w) < flowGraph.G[w].distance) {
                        flowGraph.G[w].prevNode = v; // Remembers Path
                        flowGraph.G[w].distance = flowGraph.G[v].distance + flowGraph.getCost(v, w);
                        queue.addLast(w);
                    }
                }
            }
        return flowGraph.G[sink].prevNode >= 0;     // is there an augmenting path?
    }

    /**
     * Resets paths and distances of nodes
     */
    private void clearMarks(){
        for(GraphNode node : flowGraph.G){
            node.prevNode = -1;
            node.distance = node.INF;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("----------------\n" +flowGraph.graphName + " Flows: \n");
        for(int i = 0; i < paths.size(); i++){
            sb.append("Found flow " + paths.get(i).get(0) + ": ");
            for(int j = 1; j < paths.get(i).size(); j ++){
                sb.append(paths.get(i).get(j) + " ");
            }
            sb.append("\n");
        }
        sb.append("\n" + flowGraph.graphName + " Max Flow SPACE " + flowGraph.getMaxFlowIntoSink() + " assigned " + maxFlow + "\n");
        int totalCost = 0;
        for(int k = 0; k < flowGraph.getVertexCt(); k++){
            for (EdgeInfo e : flowGraph.G[k].succ){
                int capacityUsed = (e.origCapacity - e.currCapacity);
                if(capacityUsed <= 0 ) continue;
                sb.append("Edge " + e.from + " -> " + e.to + " assigned " + capacityUsed + " of " + e.origCapacity + " at cost " + e.cost + "\n");
                totalCost += (e.cost * capacityUsed);
            }
        }
        sb.append("Total Cost: " + totalCost +"\n----------------\n");
        return sb.toString();
    }
}
