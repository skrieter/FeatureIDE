/**
 * TODO description
 */
public class Edge {
	private Node first, second;
	
	/*@ CONJUNCTIVE
	 requires first != null && second != null; 
	 ensures \result ==> first.equals(((Edge) ob).first) &&
	 	second.equals(((Edge) ob).second)); 
	 @*/
	@Override
	public/* @pure@ */boolean equals(Object ob) {
		if (original(ob)) {
			Edge edge = (Edge) ob;
			if (first.equals(edge.first)
					&& second.equals(edge.second))
				return true;
		}
		return false;
	}
	
	/*@
	requires first != null && second != null;
	ensures \result > 17;
	 @*/
	@Override
	public int hashCode() {
		int hash = 17;
		int hashMultiplikator = 79;
		hash = hashMultiplikator * hash
				+ first.hashCode();
		hash = hashMultiplikator * hash
				+ second.hashCode();
		return hash;
	}

	/*@ EXPLICIT
	 requires first != null && second != null; 
	 ensures \result ==> first.equals(from) &&
	 	second.equals(to);
	 @*/
	public /*@pure@*/ boolean connects(Node from, Node to) {
		if (first.equals(from) && second.equals(to))
			return true;
		return false;
	}
	
	/*@ EXPLICIT
	 requires \original;
	 @*/
//	@Override
	public /*@pure@*/ String toString() {
		return original() + first + " --> " + second;
	}

}