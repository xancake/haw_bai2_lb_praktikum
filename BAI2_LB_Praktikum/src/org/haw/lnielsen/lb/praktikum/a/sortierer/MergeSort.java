package org.haw.lnielsen.lb.praktikum.a.sortierer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Mergesort as described in Axel Schmolitzky's Vorlesung / Praktikum.
 */
public class MergeSort extends Sortierer_A {
	@Override
	public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
		if(list.size() > 1) {
			List<T> left = new ArrayList<>();
			List<T> right = new ArrayList<>();
			
			for(int i=0; i<list.size(); ++i) {
				if(i < list.size()/2) {
					left.add(list.get(i));
				} else {
					right.add(list.get(i));
				}
			}
			
			return merge(sort(left, comparator), sort(right, comparator), comparator);
		} else {
			return list;
		}
	}
	
	private <T> List<T> merge(List<T> left, List<T> right, Comparator<? super T> comparator) {
		List<T> mergedList = new ArrayList<>();
		int i=0, j=0;
		
		while(i < left.size() && j < right.size()) {
			if(comparator.compare(left.get(i), right.get(j)) < 0) {
				mergedList.add(left.get(i));
				i++;
			} else {
				mergedList.add(right.get(j));
				j++;
			}
		}
		
		while(i < left.size() && j >= right.size()) {
			mergedList.add(left.get(i));
			i++;
		}
		while(i >= left.size() && j < right.size()) {
			mergedList.add(right.get(j));
			j++;
		}
		
		return mergedList;
	}
	
	public static void main(String... args) {
		System.out.println(new MergeSort().sort(Arrays.asList(3, 5, 1, 4, 2)));
	}
}
