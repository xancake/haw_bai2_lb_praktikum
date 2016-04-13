package org.haw.lnielsen.lb.praktikum.d.sat.formel.knf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class Klausel implements Iterable<Literal> {
	private Collection<Literal> myLiterale;
	
	public Klausel() {
		this(new ArrayList<Literal>());
	}
	
	public Klausel(Literal... literale) {
		this(new ArrayList<>(Arrays.asList(literale)));
	}
	
	public Klausel(Collection<Literal> literale) {
		setLiterale(literale);
	}
	
	public Collection<Literal> getLiterale() {
		return myLiterale;
	}
	
	public void setLiterale(Collection<Literal> literale) {
		myLiterale = Objects.requireNonNull(literale);
	}
	
	public void addLiteral(Literal literal) {
		myLiterale.add(literal);
	}
	
	public void removeLiteral(Literal literal) {
		myLiterale.remove(literal);
	}
	
	public int getLiteralCount() {
		return myLiterale.size();
	}
	
	public boolean isEmpty() {
		return myLiterale.isEmpty();
	}
	
	public boolean isEinheitsKlausel() {
		return getLiteralCount() == 1;
	}
	
	public boolean containsLiteral(Literal literal) {
		return myLiterale.contains(literal);
	}
	
	public Klausel copy() {
		Klausel copy = new Klausel();
		for(Literal literal : myLiterale) {
			copy.addLiteral(literal);
		}
		return copy;
	}
	
	@Override
	public Iterator<Literal> iterator() {
		return myLiterale.iterator();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myLiterale == null) ? 0 : myLiterale.hashCode());
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
		Klausel other = (Klausel)obj;
		if(myLiterale == null) {
			if(other.myLiterale != null)
				return false;
		} else if(!myLiterale.equals(other.myLiterale))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(Iterator<Literal> iter = iterator(); iter.hasNext(); ) {
			sb.append(iter.next());
			if(iter.hasNext())
				sb.append(" ");
		}
		sb.append(")");
		return sb.toString();
	}
}
