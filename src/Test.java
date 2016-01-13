import java.io.FileNotFoundException;

public class Test {
	
	public static void main(String[] args) throws FileNotFoundException {

		persistence p = new persistence("/Users/antoine/GitHub/Topological-Persistence/filtration.txt");
		
		p.reduction();
		
		p.computeBarCode();
		
	}

}
