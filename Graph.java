/**********************************************************
Implementation of Abstract data type graph

*********************************************************/
public abstract class Graph{
	private int no_vertices;
	private int no_Edges;

	public Graph(){
		no_vertices = 0;
		no_edges =0;
	}
	//return no of vertices
	public int getVerticesNumber(){
		return no_vertices;
	}
	//return no of edges
	public int getEdgesNumber(){
		return no_edges;
	}
	//Adds new vertex to graph
	public void addnewVertex(){
		implementNewVertex();
		no_vertices++;
	}

	//Abstract method for implementation of addition of new vertices
	//thorugh addjacency matrix if dense graph or adjacency list if sparse
	//graph
	public void abstrct implementNewVertex(){
	}	
	
	
	//Adds new Edge to graph
	public void addnewEdge(){
		implementNewEdge();
		no_vertices++;
	}

	//Abstract method for implementation of addition of new Edge
	//thorugh addjacency matrix if dense graph or adjacency list if sparse
	//graph
	public void abstrct implementNewEdge(){
	}

	//This abstract function return neighbours
	//This is abstract because it can be used in
	//different types of implementation of graphs
	public abstract List<Integer> getNeighborsofVertex(int V);	

}
