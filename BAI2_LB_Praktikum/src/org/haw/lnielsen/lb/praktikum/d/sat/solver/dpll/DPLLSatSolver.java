package org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Klausel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;
import org.haw.lnielsen.lb.praktikum.d.sat.solver.SatSolver;
import org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll.literalwaehler.ErstesLiteralWaehler;
import org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll.literalwaehler.LiteralWaehler;

public class DPLLSatSolver implements SatSolver {
	private LiteralWaehler myLiteralWaehler;
	
	public DPLLSatSolver() {
		myLiteralWaehler = new ErstesLiteralWaehler();
	}
	
	@Override
	public boolean solve(Formel formel) {
		if(formel.isEmpty()) {
			return true;
		}
		if(formel.enthaeltLeereKlauseln()) {
			return false;
		}
		
		Set<Literal> einheitsLiterale;
		while(!(einheitsLiterale = getEinheitsLiterale(formel)).isEmpty()) {
			formel = propagiereEinheitsLiterale(formel, einheitsLiterale);
		}
		
		Set<Literal> pureLiterale = getPureLiterale(formel);
		weisePureLiteraleZu(formel, pureLiterale);
		
		Literal literal = myLiteralWaehler.waehleLiteral(formel);
		if(literal == null) {
			// Da kein Literal ermittelt werden konnte, muss die Formel frei von Literalen
			// sein. Enthält die Formel keine Klauseln mehr, so ist sie lösbar.
			// Enthält die Formel aber noch (leere) Klauseln, so ist sie nicht lösbar.
			return formel.isEmpty();
		}
		
		Formel copy = formel.copy();
		copy.addKlausel(new Klausel(literal.getKomplement()));
		formel.addKlausel(new Klausel(literal));
		return solve(formel) || solve(copy);
	}
	
	Set<Literal> getEinheitsLiterale(Formel formel) {
		Set<Literal> einheitsLiterale = new HashSet<>();
		for(Iterator<Klausel> iter = formel.iterator(); iter.hasNext(); ) {
			Klausel klausel = iter.next();
			
			// Etwas pre-UnitPropagation, soweit es zu dem Zeitpunkt schon vorgenommen
			// werden kann, um die spätere UnitPropagation etwas zu entlasten.
			boolean klauselEntfernt = false;
			if(!klausel.isEinheitsKlausel()) {
				// Wenn die Klausel keine Einheitsklausel ist, kann sie direkt verarbeitet
				// werden. Möglicherweise wird sie durch die Verarbeitung auch selbst zu
				// einer Einheitsklausel.
				for(Iterator<Literal> literalIter = klausel.iterator(); literalIter.hasNext(); ) {
					Literal literal = literalIter.next();
					if(einheitsLiterale.contains(literal)) {
						iter.remove();
						klauselEntfernt = true;
						break;
					}
					if(einheitsLiterale.contains(literal.getKomplement())) {
						literalIter.remove();
					}
				}
			}
			
			if(klausel.isEinheitsKlausel() && !klauselEntfernt) {
				Literal einheitsLiteral = klausel.getLiterale().iterator().next();
				if(!einheitsLiterale.contains(einheitsLiteral)) {
					einheitsLiterale.add(einheitsLiteral);
				} else {
					// Wenn bereits eine Einheitsklausel mit dem Literal erfasst wurde,
					// dann kann die gerade gefundene Einheitsklausel entfernt werden,
					// da sie ja redundant ist.
					iter.remove();
				}
			}
		}
		
		return einheitsLiterale;
	}
	
	Formel propagiereEinheitsLiterale(Formel formel, Set<Literal> einheitsLiterale) {
		for(Iterator<Klausel> iter = formel.iterator(); iter.hasNext(); ) {
			Klausel klausel = iter.next();
			for(Iterator<Literal> literalIter = klausel.iterator(); literalIter.hasNext(); ) {
				Literal literal = literalIter.next();
				if(einheitsLiterale.contains(literal.getKomplement())) {
					literalIter.remove();
				} else if(einheitsLiterale.contains(literal)) {
					iter.remove();
					break;
				}
			}
		}
		return formel;
	}
	
	Set<Literal> getPureLiterale(Formel formel) {
		Set<Literal> pureLiterale = new HashSet<>();
		Set<Literal> unpureLiterale = new HashSet<>();
		for(Klausel klausel : formel) {
			for(Literal literal : klausel) {
				Literal revertedLiteral = literal.getKomplement();
				if(!pureLiterale.contains(revertedLiteral) && !unpureLiterale.contains(literal)) {
					pureLiterale.add(literal);
				} else {
					pureLiterale.remove(revertedLiteral);
					unpureLiterale.add(literal);
					unpureLiterale.add(revertedLiteral);
				}
			}
		}
		return pureLiterale;
	}
	
	Formel weisePureLiteraleZu(Formel formel, Set<Literal> pureLiterale) {
		for(Iterator<Klausel> iter = formel.iterator(); iter.hasNext(); ) {
			Klausel klausel = iter.next();
			for(Literal literal : klausel) {
				if(pureLiterale.contains(literal)) {
					iter.remove();
					break;
				}
			}
		}
		return formel;
	}
}
