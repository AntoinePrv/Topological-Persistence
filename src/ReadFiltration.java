import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;



class Simplex {
	float val;
	int dim;
	TreeSet<Integer> vert;

	Simplex(Scanner sc){
		val = sc.nextFloat();
		dim = sc.nextInt();
		vert = new TreeSet<Integer>();

		for (int i=0; i<=dim; i++)
			vert.add(sc.nextInt());
	}

	public String toString(){
		return "{val="+val+"; dim="+dim+"; "+vert+"}\n";
	}
	
	static void sortSimplex(Vector<Simplex> v){
		//On implemente un comparateur
		Collections.sort(v,new Comparator<Simplex>() {
		        public int compare(Simplex  s1, Simplex s2)
		        {
		        	//On regarde la difference des temps
		        	float diff = s1.val-s2.val;
		        	
		        	//Si les temps sont egaux on trie par dimension
		        	if (diff == 0){
		        		return s1.dim-s2.dim;
		        	}
		        	
		        	//Sinon on renvoie la difference de temps
		        	if (diff <0){
		        		return -1;
		        	}
		        	
		        	return 1;
		        }
			}
			);

	}
	
}

public class ReadFiltration {

	static Vector<Simplex> readFiltration (String filename) throws FileNotFoundException {
		Vector<Simplex> F = new Vector<Simplex>();
		Scanner sc = new Scanner(new File(filename));
		while (sc.hasNext())
			//System.out.println("plop");			
			F.add(new Simplex(sc));
		sc.close();
		return F;
	}

	public static void main(String[] args) throws FileNotFoundException {

		persistence p = new persistence();
		
		p.F = readFiltration("filtration.txt");
		Simplex.sortSimplex(p.F);
		p.computeMatrix();
		
	
		p.reduction();
		
		System.out.println(p.M);
		
		p.computeBarCode();
		
	}
}
