package org.haw.lnielsen.lb.praktikum.a.sortierer.comparator;

import java.util.Comparator;

/**
 * Ein Comparator, der zwei Zahlen vergleicht indem die zweite von der
 * ersten abgezogen und die Differenz zurückgegeben wird.
 * Das resultierende Ergebnis gibt somit an, ob die erste Zahl größer
 * oder kleiner der anderen ist und ist entsprechend positiv oder negativ.
 * 
 * @author Lars 'Xancake' Nielsen
 */
public class IntegerComparator implements Comparator<Integer> {
	@Override
	public int compare(Integer a, Integer b) {
		return a-b;
	}
}
