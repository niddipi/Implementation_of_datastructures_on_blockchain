/******************************************
This class extends the implementation of the
Graph using adjacency matrices
******************************************/
public class AdjMatrixGraph extends Graph{

private boolean[][] adj_matrix;

public void addnewEdge(int u,int v){

	adj_matrix[u][v] = true; 
}

//This function does the addition of new vertex
public void implementNewVertex(int a){
	
	int Vnum = getVerticesNumber();
	if(Vnum > adj_matrix.length)
	{

		boolean[][] temp_adj_matrix = new boolean[2*Vnum][2*Vnum];
		for(int i=0;i<= adj_matrix.length;i++){
			for(int j=0;j<= adj_matrix[i].length;j++){
				temp_adj_matrix[i][j] = adj_matrix[i][j];
			}
		}
		adj_matrix = temp_adj_matrix;
	}
}
