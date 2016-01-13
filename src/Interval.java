
public class Interval {
	/*
	*  Classe qui implemente un intervalle
	*  int k : dimension de la feature
	*  float b : debut
	*  float d : fin
	*  boolean finite : si le segment est infini ou pas 
	*/
	int k;
	float b,d;
	boolean finite;
	
	//Constructeur quand on dispose de toutes les infos
	Interval(int kk, float bb, float dd){
		k=kk;
		b=bb;
		d=dd;
		finite = true;
	}
	
	//Constructeur quand on ne dispose pas de la fin de l'intervalle
	Interval(int kk,float bb){
		k=kk;
		b=bb;
		d=Float.MAX_VALUE;
		finite = false;
	}
	
	public String toString(){
		if (finite){
			return k+" "+b+" "+d;
		}
		return k+" "+b+" inf";
	}
}
