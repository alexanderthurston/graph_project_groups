public class EdgeInfo {

    public EdgeInfo(int from, int to, int capacity,int cost){
        this.from = from;
        this.to = to;
        this.origCapacity = capacity;
        this.currCapacity = capacity;
        this.cost = cost;

    }
    public String toString(){
        return "Edge " + from + "->" + to + " ("+ currCapacity + ", " + cost + ") " ;
    }

    int from;        // source of edge
    int to;          // destination of edge
    int origCapacity;    // original capacity of edge
    int currCapacity;   //variable indicating the current capacity of the edge
    int cost;        // cost of edge


}
