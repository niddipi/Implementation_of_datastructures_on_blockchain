import java.util.*;
public class Sorting{ 

	public ArrayList<ArrayList<String>> quickSort(ArrayList<String> Sortelements,
			ArrayList<String> corresponding, int left, int right) {
		if (left < right) {
			int pivotIndex = partition(Sortelements,corresponding ,left, right);
			quickSort(Sortelements,corresponding, left, pivotIndex-1);
			quickSort(Sortelements,corresponding, pivotIndex, right);
		}
		ArrayList<ArrayList<String>> rt = new ArrayList<ArrayList<String>>();
		rt.add(Sortelements);
		rt.add(corresponding);
		return rt;
	}

	private int partition(ArrayList<String> Sortelements, 
			ArrayList<String> corresponding,int left, int right) {
		long pivot = Long.parseLong(Sortelements.get(right));
		while (left < right){
			while (Long.parseLong(Sortelements.get(left)) < pivot){
				left++;
			}
			while (Long.parseLong(Sortelements.get(right)) > pivot){
				right--;
			}
			if (left < right){
				 String temp = Sortelements.get(left);
				 Sortelements.set(left,Sortelements.get(right));
				 Sortelements.set(right,temp);
				
				//swaping corresponding elements like hashcode
				 String temp1 = corresponding.get(left);
                                 corresponding.set(left,corresponding.get(right));
                                 corresponding.set(right,temp1);
				left++;
				right--;
			}
		}
		return left;
	}
}
