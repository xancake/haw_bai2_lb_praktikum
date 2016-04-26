package org.haw.lnielsen.lb.praktikum.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.haw.lnielsen.lb.praktikum.a.sortierer.BubbleSort;
import org.haw.lnielsen.lb.praktikum.a.sortierer.JavaSort;
import org.haw.lnielsen.lb.praktikum.a.sortierer.MergeSort;
import org.haw.lnielsen.lb.praktikum.a.sortierer.Sortierer_I;

public class Starter {
	public static void main(String[] args) {
		Sortierer_I bubblesort = new BubbleSort();
		Sortierer_I mergesort  = new MergeSort();
		Sortierer_I javasort   = new JavaSort();
		
		profileSortiererMitLaenge(     100, bubblesort, mergesort, javasort);
		profileSortiererMitLaenge(    1000, bubblesort, mergesort, javasort);
		profileSortiererMitLaenge(   10000, bubblesort, mergesort, javasort);
		profileSortiererMitLaenge(  100000, bubblesort, mergesort, javasort);
		profileSortiererMitLaenge( 1000000, mergesort, javasort);
		profileSortiererMitLaenge(10000000, mergesort, javasort);
	}
	
	public static void profileSortiererMitLaenge(int laenge, Sortierer_I... sortierer) {
		List<Integer> liste = generiereZufaellig(laenge);
//		System.out.println(liste);
		for(Sortierer_I s : sortierer) {
			profileSortiererMitListe(s, new ArrayList<>(liste));
		}
	}
	
	public static void profileSortiererMitListe(Sortierer_I sortierer, List<Integer> liste) {
		long before = System.currentTimeMillis();
		sortierer.sort(liste);
		long after  = System.currentTimeMillis();
		long ms     = after - before;
		System.out.println(sortierer.getClass().getSimpleName() + " sortiert " + liste.size() + " Elemente in " + ms + "ms");
	}
	
	
	public static List<Integer> generiereZufaellig(int laenge) {
		Set<Integer> set = new HashSet<>(laenge);
		while(set.size() < laenge) {
			int zahl = (int)(Math.random() * laenge);
			set.add(zahl);
		}
		List<Integer> liste = new ArrayList<>(set);
		Collections.shuffle(liste);
		return liste;
	}
}
