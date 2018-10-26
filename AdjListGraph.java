/********************************************
This class implements a graph using adjacency
list.
********************************************/

public class AdjListGraph extends Graph{ 
private Map<Integer,ArrayList<Integer>> adjList;

	public void implementNewVertex(){
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		adjList.put(v,neighbours)
	}	
	
	public void implementNewEdge(int u, int v){
		adjList.get(u).add(v);

	}
	
	public List<Integer> getNeighbors(int v){
		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(adjList.get(u));
		return neighbours;
	}
}
