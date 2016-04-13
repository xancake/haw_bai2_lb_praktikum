package org.haw.lnielsen.lb.praktikum.d.sat.formel.knf;

import java.util.HashMap;
import java.util.Map;


public class Literal implements Comparable<Literal> {
	private static Map<Integer, Literal> myLiteralCache = new HashMap<>();
	
	private int myIdentifier;
	
	private Literal(int identifier) {
		myIdentifier = identifier;
	}
	
	public static Literal getLiteral(Integer literalInt) {
		Literal literal;
		if(myLiteralCache.containsKey(literalInt)) {
			literal = myLiteralCache.get(literalInt);
		} else {
			literal = new Literal(literalInt);
			myLiteralCache.put(literalInt, literal);
		}
		return literal;
	}
	
	public int getIdentifier() {
		return myIdentifier;
	}
	
	public boolean isPositiv() {
		return myIdentifier >= 0;
	}
	
	public Literal getKomplement() {
		return getLiteral(-getIdentifier());
	}
	
	@Override
	public int compareTo(Literal o) {
		return myIdentifier - o.myIdentifier;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + myIdentifier;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Literal other = (Literal)obj;
		if(myIdentifier != other.myIdentifier)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return Integer.toString(myIdentifier);
	}
}
