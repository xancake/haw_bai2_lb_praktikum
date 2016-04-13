package org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll.literalwaehler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Klausel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;

public class HaeufigstesLiteralWaehler implements LiteralWaehler {
	@Override
	public Literal waehleLiteral(Formel formel) {
		if(formel.isEmpty()) {
			return null;
		}
		
		Map<Integer, List<Literal>> haeufigkeitLiteral = new HashMap<>();
		Map<Literal, Integer> literalHaeufigkeit = new HashMap<>();
		int maxHaeufigkeit = 0;
		for(Klausel klausel : formel) {
			if(klausel.isEmpty()) {
				return null;
			}
			
			for(Literal literal : klausel) {
				Integer haeufigkeit = literalHaeufigkeit.remove(literal);
				List<Literal> literale;
				
				if(haeufigkeit != null) {
					literale = haeufigkeitLiteral.get(haeufigkeit);
					if(literale == null) {
						literale = new ArrayList<>();
						haeufigkeitLiteral.put(haeufigkeit, literale);
					}
					literale.remove(literal);
				} else {
					haeufigkeit = 0;
				}
				
				haeufigkeit++;
				literale = haeufigkeitLiteral.get(haeufigkeit);
				if(literale == null) {
					literale = new ArrayList<>();
					haeufigkeitLiteral.put(haeufigkeit, literale);
				}
				literale.add(literal);
				
				if(haeufigkeit > maxHaeufigkeit) {
					maxHaeufigkeit = haeufigkeit;
				}
			}
		}
		
		return haeufigkeitLiteral.get(maxHaeufigkeit).get(0);
	}
}
