package org.haw.lnielsen.lb.praktikum.a.sortierer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.haw.lnielsen.lb.praktikum.a.sortierer.comparator.IntegerComparator;

public abstract class Sortierer_A implements Sortierer_I {
	@Override
	public List<Integer> sort(List<Integer> list) {
		return sort(list, new IntegerComparator(), true);
	}
	
	@Override
	public <T> List<T> sort(List<T> list, Comparator<? super T> comparator, boolean ascending) {
		if(!ascending) {
			comparator = Collections.reverseOrder(comparator);
		}
		return sort(list, comparator);
	}
	
	public abstract <T> List<T> sort(List<T> list, Comparator<? super T> comparator);
}
