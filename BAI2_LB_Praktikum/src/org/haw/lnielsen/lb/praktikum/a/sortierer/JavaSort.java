package org.haw.lnielsen.lb.praktikum.a.sortierer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JavaSort extends Sortierer_A {
	@Override
	public List<Integer> sort(List<Integer> list) {
		Collections.sort(list);
		return list;
	}
	
	@Override
	public <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
		Collections.sort(list, comparator);
		return list;
	}
	
	public static void main(String... args) {
		System.out.println(new JavaSort().sort(Arrays.asList(3, 5, 1, 4 ,2)));
	}
}
