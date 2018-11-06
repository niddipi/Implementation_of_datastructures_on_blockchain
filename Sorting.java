import java.util.*;
public class Sorting{ 

	public ArrayList<ArrayList<String>> quickSort(ArrayList<String> Sortelements,
			ArrayList<String> corresponding, int left, int right) {
		if (left < right) {
			int pivotIndex = partition(Sortelements,corresponding ,left, right);
			quickSort(Sortelements,corresponding, left, pivotIndex-1);
			quickSort(Sortelements,corresponding, pivotIndex+1, right);
		}
		ArrayList<ArrayList<String>> rt = new ArrayList<ArrayList<String>>();
		rt.add(Sortelements);
		rt.add(corresponding);
		return rt;
	}

	private int partition(ArrayList<String> Sortelements, 
			ArrayList<String> corresponding,int left, int right) {
		int L = left;
		int R = right+1;
		long pivot = Long.parseLong(Sortelements.get(left));
		while(true){
			while((Long.parseLong(Sortelements.get(++L))<pivot)){
				if(L == right) break;
			}
			while((Long.parseLong(Sortelements.get(--R))>pivot)){
                                if(R == left) break;
                        }
			if(L >= R)break;
			String temp = Sortelements.get(L);
                        Sortelements.set(L,Sortelements.get(R));
                        Sortelements.set(R,temp);

                        //swaping corresponding elements like hashcode
                        String temp1 = corresponding.get(L);
                        corresponding.set(L,corresponding.get(R));
                        corresponding.set(R,temp1);
		}
		String temp = Sortelements.get(left);
                Sortelements.set(left,Sortelements.get(R));
                Sortelements.set(R,temp);

                //swaping corresponding elements like hashcode
                String temp1 = corresponding.get(left);
                corresponding.set(left,corresponding.get(R));
                corresponding.set(R,temp1);
		return R;
	}
}
