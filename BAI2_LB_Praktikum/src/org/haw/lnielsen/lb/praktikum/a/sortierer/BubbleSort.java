package org.haw.lnielsen.lb.praktikum.a.sortierer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Bubblesort as described in Axel Schmolitzky's Vorlesung / Praktikum.
 */
public class BubbleSort extends Sortierer_A {
	@Override
	public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
		for(int i=list.size(); i>0; i--) {
			for(int j=0; j<i-1; j++) {
				if(comparator.compare(list.get(j), list.get(j+1)) > 0) {
					T temp = list.get(j+1);
					list.set(j+1, list.get(j));
					list.set(j, temp);
				}
			}
		}
		return list;
	}
	
	public static void main(String... args) {
		System.out.println(new BubbleSort().sort(Arrays.asList(3, 5, 1, 4, 2)));
	}
}
