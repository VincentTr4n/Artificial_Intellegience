package Helpers;

public class Pair<T extends Comparable<T>, K> implements Comparable<Pair<T, K>>{

	public T First;
	public K Second;
	
	public Pair() {}
	
	public Pair(T first, K second) {
		First = first;
		Second = second;
	}
	
	@Override
	public int compareTo(Pair<T, K> o) {
		return First.compareTo(o.First);
	}
	
	@Override
	public String toString() {
		return "(" + First.toString() + "," + Second.toString() + ")";
	}
	
}
