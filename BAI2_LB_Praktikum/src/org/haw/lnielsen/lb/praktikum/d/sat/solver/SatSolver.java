package org.haw.lnielsen.lb.praktikum.d.sat.solver;

import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;

public interface SatSolver {
	boolean solve(Formel formel);
}
