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
	


}
