import java.util.ArrayList;
import java.util.Vector;

import java.util.*;


public class persistence {

	public Vector<Simplex> F; //filtration vector. Vector is to be sorted in constructor private 
	ArrayList<ArrayList<Integer>> M; //initial boundary matrix. 
	int size; // F.size, number of sigma

	int low(int j){
		ArrayList<Integer> j_col=M.get(j);
		int i = M.get(j).size()-1;

		while (i >= 0){
			if (j_col.get(i) != 0) {
				break;
			}
			i--;
		}

		return i;
	}


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
						for(int k =0;k<=low;k++){
							column.set(k,(column.get(k) + M.get(lowPosition.get(low)).get(k) ) %2);
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

	public void computeMatrix(){
        int m = F.size();
        M = new ArrayList<ArrayList<Integer>>();

        for(int j=0; j<m; j++){
            M.add(new ArrayList<Integer>());
            for(int i=0; i<m; i++)
                M.get(j).add(0);
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
                                M.get(j).set(i,1);
                    }
                    s.vert.add(rmv);
                }
        }
    }

	
	
Vector<Interval> computeBarCode(){
		
		Vector<Interval> bar_code = new Vector<Interval>();
		
		//On parcourt les colonnes
		for(int j = 0; j < M.size(); j++){
			//On calcule le low
			int l = low(j);
			
			//Si il n'y a pas de low, c'est que c'est un debut d'interval infini
			if (l < 0){
				bar_code.add(new Interval(F.get(j).dim,j));
			}
			//Sinon, on a un joli segment de debut low(j) et de fin j
			else{
				bar_code.add(new Interval(F.get(l).dim,l,j));
			}
		}
	
		for (Interval bc : bar_code){
			System.out.println(bc);
		}
		return bar_code;
	}



}