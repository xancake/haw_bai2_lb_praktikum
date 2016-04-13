package org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll.literalwaehler;

import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Klausel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;

public class ErstesLiteralWaehler implements LiteralWaehler {
	@Override
	public Literal waehleLiteral(Formel formel) {
			if(formel.isEmpty()) {
			return null;
		}
		Klausel klausel = formel.iterator().next();
		if(klausel.isEmpty()) {
			return null;
		}
		return klausel.iterator().next();
	}
}
