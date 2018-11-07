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
	private static Map<String,ArrayList<String>> contract_tokens  = new HashMap<>();	

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
			long tokens = 0;
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
						ArrayList<String> tokens_hash = new ArrayList<String>();
						contract_tokens.put(transaction[4],tokens_hash);
					}	
					contract_Block.get(transaction[4]).add(transaction[1]);
					contract_hash.get(transaction[4]).add(transaction[6]);
					tokens = Double.valueOf(transaction[8]).longValue();
					//System.out.println(tokens);
					contract_tokens.get(transaction[4]).add(String.valueOf(tokens));
				}
			}
			System.out.println(G.getVerticesNumber());
			//G.DisplayGraph();
			//System.out.println("BFS Output");
			String start_node = "0x2976924b350bcee8263f36d86cebd584d2363c1f";
			//G.BFS(start_node);
			//System.out.println("DFS Output");
			//G.DFS("knknnnlklkmlkmlk");
			Query1();
			//Query_7("0xf3847b971ac4095f7c45eb31f6e34180cdf8c983e5e9c1ccfc29e06c25ac0e3d");
			//Query_8("0xa4d6ff84f3fc783f3f7f71177958cff19dd7ffc4");
			//Query_9("0xa2d7a9432e98f91fffad9158f3183b24a744ab07");
			 Query_10("0x7b36d8be6f92818dc30f532ae2a67128b4b92b21");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}
	public static void Query1(){
		BlockbyTotalGas(TransactionHash,Blockno);
	}

	public void Query2(){
		BlockbyTransactionNo(TransactionHash,Blockno);	
	}
	public void Query3(){
		SortByGas(TransactionHash,gas_price);
	
	}
	public void Query4(){
		TransactionsPerBlock(TransactionHash,gas_price,Blockno);
	}
	public void Query5(){
		sort_block();
	}
	public void Query6(int block){
			search_blockno(block);
	
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
		System.out.println("Blockno   TransactionsFrequency");
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

	public static void search_blockno(int block){
		String key =null;
		for(int i =0;i<Blockno.size();i++){
			if(block == Integer.parseInt(Blockno.get(i))){
				key = TransactionHash.get(i); 
				if(records.containsKey(key)){
					for(String element:records.get(key)){
						System.out.print(element+" ");
					}
				}
				System.out.println();
			}
		}
	}
	
	public static void Query_7(String transaction_hash){

		for(int i=0;i<TransactionHash.size();i++){
			if(TransactionHash.get(i).equals(transaction_hash)){
				String key = transaction_hash;
					System.out.println("transaction_hash"+
						"                               "+
						"      "+"transaction_fee"+" "+"block#");
                                        System.out.println(transaction_hash+" "+
						records.get(key).get(5)+" "+
							records.get(key).get(1));
			}

		}
	}
	
	public static void Query_8(String public_key){
		long total = 0;
		System.out.println(
			"block-hash  block-number from to contract-id gasprice txnhash index value");	
		for (String key : TransactionHash){
			if(records.get(key).get(2).equals(public_key)){
				for(String element:records.get(key)){
                                        System.out.print(element+" ");
                                }
				System.out.println();
				total = total+Long.parseLong(records.get(key).get(5));
                        }
                }
		System.out.println("The total transaction fee is "+total);
        }


	public static void Query_9(String public_key){
		long total = 0;
		System.out.println(
			"block-hash  block-number from to contract-id gasprice txnhash index value");	
		for (String key : TransactionHash){
			if(records.get(key).get(3).equals(public_key)){
				for(String element:records.get(key)){
                                        System.out.print(element+" ");
                                }
				System.out.println();
				total = total+Long.parseLong(records.get(key).get(5));
                        }
                }
		System.out.println("The total transaction fee is "+total);
        }
	public static void Query_10(String contract_id){

		Sorting S = new Sorting();	
		for (String contract : contract_Block.keySet()){
			if(contract_id.equals(contract)){
				ArrayList<String> c_hash = contract_hash.get(contract);
				ArrayList<String> c_tokens = contract_tokens.get(contract);
				ArrayList<ArrayList<String>> rt = 
					S.quickSort(c_tokens,c_hash,0,c_hash.size()-1);
				int index = rt.get(1).size() -1;
				
				System.out.println(
					"Transaction with Smallest amount of tokens for a contract id");
				String key = rt.get(1).get(0);
					for(String element:records.get(key)){
						System.out.print(element+" ");
					}
				System.out.println();
				System.out.println(
					"Transaction with Largest amount of tokens for a contract id");
				key = rt.get(1).get(index);
					for(String element:records.get(key)){
						System.out.print(element+" ");
					}
				System.out.println();
			}
		}		
	}
	public static void Display(ArrayList<String> currentblock){

		
		System.out.println(
			"block-hash  block-number from to contract-id gasprice txnhash index value");	
		for (String key : currentblock){
			if(records.containsKey(key)){
				for(String element:records.get(key)){
					System.out.print(element+" ");
				}}
			System.out.println();
		}

	}
	
	public static void BlockbyTotalGas(ArrayList<String>TransactionHash,
			ArrayList<String>Blockno){

		int length = Blockno.size();
		int i = 0;
		ArrayList<String> blockhash = new ArrayList<String>();
		ArrayList<String> blockgas = new ArrayList<String>();
		Sorting S = new Sorting();
		String prevblock = Blockno.get(0);
		long p =0;
		while(i < length){
			if(prevblock.equals(Blockno.get(i))){
				p = p+Long.parseLong(gas_price.get(i));
				i++;
			}
			else{
				blockhash.add(prevblock);
				blockgas.add(Long.toString(p));
				prevblock = Blockno.get(i);
				p = 0;
			}
		}
		ArrayList<ArrayList<String>> rt = 
			S.quickSort(blockgas,blockhash,0,blockhash.size()-1);
		System.out.println("Blockno   GasUsed");
		for (int k =0;k<rt.get(1).size();k++){
			System.out.println(rt.get(1).get(k)+"=="+rt.get(0).get(k));
		}	
	}


} 
