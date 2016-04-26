package org.haw.lnielsen.lb.praktikum.a.sortierer;

import java.util.Comparator;
import java.util.List;

public interface Sortierer_I {
	/**
	 * Sortiert eine Liste von {@link Integer}n aufsteigend sortiert.
	 * @param list Die zu sortierende Liste
	 * @return Die sortierte Liste
	 */
	List<Integer> sort(List<Integer> list);
	
	/**
	 * Sortiert eine Liste aufsteigend unter Ber�cksichtigung eines {@link Comparator}s.
	 * Die �bergebene Liste wird m�glicherweise ver�ndert. Das hei�t es kann nicht gew�hrleistet
	 * werden, dass die �bergebene Liste unver�ndert bleibt.
	 * @param list Die zu sortierende Liste
	 * @param comparator Der Comparator
	 * @param ascending Ob die Liste aufsteigend sortiert werden soll oder nicht
	 * @return Die sortierte Liste
	 */
	<T> List<T> sort(List<T> list, Comparator<? super T> comparator);
	
	/**
	 * Sortiert eine Liste auf- oder absteigend unter Ber�cksichtigung eines {@link Comparator}s.
	 * Die �bergebene Liste wird m�glicherweise ver�ndert. Das hei�t es kann nicht gew�hrleistet
	 * werden, dass die �bergebene Liste unver�ndert bleibt.
	 * @param list Die zu sortierende Liste
	 * @param comparator Der Comparator
	 * @param ascending Ob die Liste aufsteigend sortiert werden soll oder nicht
	 * @return Die sortierte Liste
	 */
	<T> List<T> sort(List<T> list, Comparator<? super T> comparator, boolean ascending);
}
