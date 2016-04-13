package org.haw.lnielsen.lb.praktikum.d.sat.formel.knf;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Formel implements Iterable<Klausel> {
	private List<Klausel> myKlauseln;
	
	public Formel() {
		this(new LinkedList<Klausel>());
	}
	
	public Formel(Klausel... klauseln) {
		this(new LinkedList<>(Arrays.asList(klauseln)));
	}
	
	public Formel(List<Klausel> klauseln) {
		setKlauseln(klauseln);
	}
	
	public Klausel getKlausel(int i) {
		return myKlauseln.get(i);
	}
	
	public void setKlauseln(List<Klausel> klauseln) {
		myKlauseln = Objects.requireNonNull(klauseln);
	}
	
	public void addKlausel(Klausel klausel) {
		myKlauseln.add(klausel);
	}
	
	public void removeKlausel(Klausel klausel, int index) {
		myKlauseln.remove(index);
	}
	
	public int getKlauselCount() {
		return myKlauseln.size();
	}
	
	public boolean isEmpty() {
		return myKlauseln.isEmpty();
	}
	
	public boolean enthaeltLeereKlauseln() {
		for(Klausel klausel : myKlauseln) {
			if(klausel.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public Formel copy() {
		Formel copy = new Formel();
		for(Klausel klausel : myKlauseln) {
			copy.addKlausel(klausel.copy());
		}
		return copy;
	}
	
	@Override
	public Iterator<Klausel> iterator() {
		return myKlauseln.iterator();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myKlauseln == null) ? 0 : myKlauseln.hashCode());
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
		Formel other = (Formel)obj;
		if(myKlauseln == null) {
			if(other.myKlauseln != null)
				return false;
		} else if(!myKlauseln.equals(other.myKlauseln))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(Iterator<Klausel> iter=myKlauseln.iterator(); iter.hasNext(); ) {
			sb.append(iter.next());
			if(iter.hasNext())
				sb.append(" ");
		}
		sb.append(")");
		return sb.toString();
	}
}
