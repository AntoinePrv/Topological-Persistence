import java.io.FileNotFoundException;
import java.util.*;


public class sparse_persistence {
	//Sparsified
	
	public Vector<Simplex> F; //filtration vector. Vector is to be sorted in constructor private 
	ArrayList<ArrayList<Integer>> M; //initial boundary matrix. 
	int size; // F.size, number of sigma

	public sparse_persistence(String filename)throws FileNotFoundException {
		System.out.println("Reading from " + filename + "...\n");
		F = ReadFiltration.readFiltration(filename);
		
		System.out.println("Sorting filtration...\n");
		ReadFiltration.sortSimplex(F);
		size = F.size();
		
		System.out.println("Computing border matrix...");
		computeMatrix();
			
	}
	
	//Sparsified
	public void computeMatrix(){
		int m = F.size();
		M = new ArrayList<ArrayList<Integer>>();

		for(int j=0; j<m; j++){
			M.add(new ArrayList<Integer>());
		}

		for(int j=0; j<F.size(); j++){// F is sorted
			Simplex s = F.get(j);
			Integer[] nodes = s.vert.toArray(new Integer[s.dim +1]);
			if(s.dim >= 1)// is simplex has dimension 0 then is border is null
				for(int rmv : nodes){
					s.vert.remove(rmv);// remove one of the nodes
					for(int i=0; i<F.size(); i++){// F is sorted
						Simplex b = F.get(i);
						if(b.dim == s.dim-1)
							if(b.vert.equals(s.vert))
								M.get(j).add(i);
					}
					s.vert.add(rmv);
				}
		}
	}
	
	int low(int j){
		ArrayList<Integer> j_col=M.get(j);

		int max = -1;

		for(int i : j_col){
			if (i >= max){
				max = i;
			}
		}

		return max;
	}

	//Sparsified
	public void reduction(){
		size = F.size();
		System.out.println(size);
		ArrayList<Integer> lowPosition = new ArrayList<Integer>(); // position of the first(low = j)
		for (int i =0;i<size;i++){
			lowPosition.add(-1);
		}
		for (int i=0;i<size;i++){ // i is the indice of the column we are working on
			ArrayList<Integer> column = M.get(i);
			int low = low(i);  // actual low
			if (low>-1){	// if not null column
				if (lowPosition.get(low)>-1) { // if we already have this low we add the two columns
					while(low>-1 && lowPosition.get(low)>-1){ 
					
							for (Integer line : M.get(lowPosition.get(low))){
							
								int index = column.indexOf(line);
								//If the firs doesn't contain the entry, we add it
								if(index == -1){
									column.add(line);
								}
								//else we remove it
								else{
									column.remove(index);
								}
							}	
					
						low=low(i);
					}
				}
				if (low>-1){
					lowPosition.set(low,i); 
				}
			}
		}
	}

	Vector<Interval> computeBarCode(){
		Vector<Interval> bar_code = new Vector<Interval>();
		
		Vector<Integer> which_column = new Vector<Integer>(M.size());
		for(int j = 0; j < M.size() ; j++){
			which_column.add(-1);
		}
		
		for(int j = 0; j < M.size();j++){
			if(low(j) > -1){
				which_column.set(low(j),j);
			}
		}
		
		//On parcourt les colonnes
		for(int j = 0; j < M.size(); j++){
			//On calcule le low
			int l = low(j);
			
			//Si il n'y a pas de low, c'est que c'est un debut d'interval infini
			if (l < 0){
				if(which_column.get(j) == -1){
					bar_code.add(new Interval(F.get(j).dim,F.get(j).val));
				}
				else{
					bar_code.add(new Interval(F.get(j).dim,F.get(j).val,F.get(which_column.get(j)).val));
				}
			}

		}
	
		for (Interval bc : bar_code){
			System.out.println(bc);
		}
		return bar_code;
	}

	public String toString(){
	ArrayList<ArrayList<Integer>> toPrint = new ArrayList<ArrayList<Integer>>();
	
	int n = M.size();
	for (int i = 0 ; i < n; i++){
		ArrayList<Integer> temp = new ArrayList<Integer>(n);
		for(int j = 0; j < n ; j++){
			temp.add(0);
		}
		for(int j : M.get(i)){
			temp.set(j, 1);
		}
		toPrint.add(temp);
	}
	
	return ""+toPrint;
}

}