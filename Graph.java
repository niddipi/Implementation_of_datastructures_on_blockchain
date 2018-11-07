/**********************************************************
Implementation of Abstract data type graph

*********************************************************/
import java.util.*;
public abstract class Graph{
	private int no_vertices;
	private int no_edges;

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
	public void addnewVertex(String vertex){
		implementNewVertex(vertex);
		no_vertices++;
	}

	//Abstract method for implementation of addition of new vertices
	//thorugh addjacency matrix if dense graph or adjacency list if sparse
	//graph
	public abstract void implementNewVertex(String v);	
	
	
	//Adds new Edge to graph
	public void addnewEdge(String u,String v){
		implementNewEdge(u,v);
	}

	//Abstract method for implementation of addition of new Edge
	//thorugh addjacency matrix if dense graph or adjacency list if sparse
	//graph
	public abstract void implementNewEdge(String u,String v);
	//This abstract function return neighbours
	//This is abstract because it can be used in
	//different types of implementation of graphs
	public abstract List<String> getNeighborsofVertex(String V);	

	//BFS - Breadth First Search
	public abstract void BFS(String vertex);
	//DFS - Depth First Search
	public abstract void DFS(String RequiredGoal);
}
