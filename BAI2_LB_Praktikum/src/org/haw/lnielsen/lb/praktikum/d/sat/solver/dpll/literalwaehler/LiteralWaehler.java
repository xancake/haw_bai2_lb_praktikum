package org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll.literalwaehler;

import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;

public interface LiteralWaehler {
	Literal waehleLiteral(Formel formel);
}
