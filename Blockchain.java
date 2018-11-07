import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Blockchain{

	private static ArrayList<String> TransactionHash   = new ArrayList<String>();
	private static ArrayList<String> gas_price 	   = new ArrayList<String>();
	private static ArrayList<String> Blockno 	   = new ArrayList<String>();
	private static Map<String,ArrayList<String>> records  = new HashMap<>();	
	private static Map<String,ArrayList<String>> contract_Block = new HashMap<>();	
	private static Map<String,ArrayList<String>> contract_hash  = new HashMap<>();	
	
	public static void main(String[] args){
		String csvFile = "transactions.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		AdjListGraph G = new AdjListGraph();
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			String[] transaction1 = line.split(cvsSplitBy);
			while ((line = br.readLine()) != null) {
				ArrayList<String> record = new ArrayList<String>();
				// use comma as separator
				String[] transaction = line.split(cvsSplitBy);
				if(transaction.length == 9){
					int i =0;
					while(i < transaction.length){
						record.add(transaction[i]);
						//System.out.println(transaction[i]);
						i++;
					}
					records.put(transaction[6],record);
					G.addnewEdge(transaction[2],transaction[3]);
					G.addweights(transaction[2],transaction[3],transaction[8]);
					TransactionHash.add(transaction[6]);
					gas_price.add(transaction[5]);
					Blockno.add(transaction[1]);
					if(!contract_Block.containsKey(transaction[4])){
						ArrayList<String> blocks_Contract = new ArrayList<String>();
						contract_Block.put(transaction[4],blocks_Contract);
						ArrayList<String> blocks_hash = new ArrayList<String>();
						contract_hash.put(transaction[4],blocks_hash);
					}	
					contract_Block.get(transaction[4]).add(transaction[1]);
					contract_hash.get(transaction[4]).add(transaction[6]);
				}
			}
			System.out.println(G.getVerticesNumber());
			//G.DisplayGraph();
			//System.out.println("BFS Output");
			String start_node = "0x2976924b350bcee8263f36d86cebd584d2363c1f";
			//G.BFS(start_node);
			//System.out.println("DFS Output");
			//G.DFS("knknnnlklkmlkmlk");
			//TransactionsPerBlock(TransactionHash,gas_price,Blockno);
			//SortByGas(TransactionHash,gas_price);
			//BlockbyTransactionNo(TransactionHash,Blockno);	
			sort_block();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}
	public static void TransactionsPerBlock(ArrayList<String> TransactionHash,
			ArrayList<String>gas_price,ArrayList<String>Blockno){
		int length = Blockno.size();
		int i = 0;
		ArrayList<String> blockhash = new ArrayList<String>();
		ArrayList<String> blockgas = new ArrayList<String>();
		Sorting S = new Sorting();
		String prevblock = Blockno.get(0);
		while(i < length){
			if(prevblock.equals(Blockno.get(i))){
				blockhash.add(TransactionHash.get(i));
				blockgas.add(gas_price.get(i));
				i++;
			}
			else{

				ArrayList<ArrayList<String>> rt = S.quickSort(
						blockgas,blockhash,0,blockgas.size()-1);
				System.out.println(prevblock);
				Display(rt.get(1));	
				blockhash.clear() ;
				blockgas.clear();
				prevblock = Blockno.get(i);	
			}

		}	
	}
	public static void SortByGas(ArrayList<String> TransactionHash,
                        ArrayList<String>gas_price){
		
		Sorting S = new Sorting();
		ArrayList<ArrayList<String>> rt =
				S.quickSort(gas_price,TransactionHash,0,gas_price.size()-1);
		Display(rt.get(1));	

	}

	public static void BlockbyTransactionNo(ArrayList<String>TransactionHash,
                        ArrayList<String>Blockno){
			
		int length = Blockno.size();
		int i = 0;
		ArrayList<String> blockhash = new ArrayList<String>();
		ArrayList<String> blockfreq = new ArrayList<String>();
		Sorting S = new Sorting();
		String prevblock = Blockno.get(0);
		int p =0;
		while(i < length){
			if(prevblock.equals(Blockno.get(i))){
				i++;
				p++;
			}
			else{
				blockhash.add(prevblock);
				blockfreq.add(Integer.toString(p));
				prevblock = Blockno.get(i);
				p = 0;
			}
		}
		ArrayList<ArrayList<String>> rt = 
			S.quickSort(blockfreq,blockhash,0,blockhash.size()-1);
		for (int k =0;k<rt.get(1).size();k++){
			System.out.println(rt.get(1).get(k)+"=="+rt.get(0).get(k));
		}	
	}
	public static void sort_block(){
		Sorting S = new Sorting();	
		for (String contract : contract_Block.keySet()){
			
			ArrayList<String> c_block = contract_Block.get(contract);
			ArrayList<String> c_hash = contract_hash.get(contract);
			ArrayList<ArrayList<String>> rt = 
				S.quickSort(c_block,c_hash,0,c_hash.size()-1);
			Display(rt.get(1));	
		}


	}
	public static void Display(ArrayList<String> currentblock){

		for (String key : currentblock){
			if(records.containsKey(key)){
				for(String element:records.get(key)){
					System.out.print(element+" ");
				}}
			System.out.println();
		}

	}






} 
