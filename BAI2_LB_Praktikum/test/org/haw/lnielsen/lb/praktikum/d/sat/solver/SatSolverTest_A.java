package org.haw.lnielsen.lb.praktikum.d.sat.solver;

import static org.junit.Assert.*;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.junit.Before;

public abstract class SatSolverTest_A<T extends SatSolver> {
	protected T mySolver;
	
	@Before
	public void setUp() {
		mySolver = createSolver();
	}
	
	protected void assertSolve(boolean expected, Formel formel) {
		assertEquals(expected, mySolver.solve(formel));
	}
	
	protected abstract T createSolver();
}
