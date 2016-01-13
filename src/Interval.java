
public class Interval {
	/*
	*  class that implements an interval
	*  int k : feature dim
	*  float b : start
	*  float d : end
	*  boolean finite : i the interval infinite 
	*/
	int k;
	float b,d;
	boolean finite;
	
	//constructor with all information
	Interval(int kk, float bb, float dd){
		k=kk;
		b=bb;
		d=dd;
		finite = true;
	}
	
	//constructor without the end of the interval
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
