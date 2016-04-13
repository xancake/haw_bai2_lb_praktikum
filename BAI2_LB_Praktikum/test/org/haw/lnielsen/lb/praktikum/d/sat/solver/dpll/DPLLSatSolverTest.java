package org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll;

import static org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.FormelUtils.formel;
import static org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.FormelUtils.klausel;
import static org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.FormelUtils.literal;
import static org.haw.lnielsen.lb.praktikum.d.test.util.TestUtil.assertContainsOnly;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;
import org.haw.lnielsen.lb.praktikum.d.sat.solver.SatSolverTest_A;
import org.junit.Test;

public class DPLLSatSolverTest extends SatSolverTest_A<DPLLSatSolver> {
	@Test
	public void testSolve_1() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-3)),
				klausel(literal(2), literal(3), literal(1))
		);
		assertSolve(true, testFormel);
	}
	
	@Test
	public void testSolve_Beispiel_8_38() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-2), literal(-3)),
				klausel(literal(-1), literal(2), literal(3)),
				klausel(literal(-2))
		);
		assertSolve(true, testFormel);
	}
	
	@Test
	public void testSolve_Beispiel_8_39() {
		Formel testFormel = formel(
				klausel(literal(-1), literal(-2)),
				klausel(literal(-1), literal(2)),
				klausel(literal(2), literal(3)),
				klausel(literal(2), literal(-3))
		);
		assertSolve(true, testFormel);
	}
	
	@Test
	public void testSolve_Beispiel_8_40() {
		Formel testFormel = formel(
				klausel(literal(1), literal(2), literal(-3)),
				klausel(literal(-1), literal(-2)),
				klausel(literal(3)),
				klausel(literal(1), literal(-4)),
				klausel(literal(3), literal(4))
		);
		assertSolve(true, testFormel);
	}
	
	@Test
	public void testSolve_Negative_Kontradiktion() {
		Formel testFormel = formel(
				klausel(literal(1)),
				klausel(literal(-1))
		);
		assertSolve(false, testFormel);
	}
	
	@Test
	public void testGetEinheitsLiterale_MultipleLiterals() {
		Formel testFormel = formel(
		        klausel(literal(3), literal(-1)),
		        klausel(literal(1)),
		        klausel(literal(-2))
		);
		Set<Literal> expectedEinheitsLiterale = new HashSet<>(Arrays.asList(
		        literal(1),
		        literal(-2)
		));
		assertGetEinheitsLiterale(expectedEinheitsLiterale, testFormel);
	}
	
	@Test
	public void testGetEinheitsLiterale_MultipleSameLiterals() {
		Formel testFormel = formel(
		        klausel(literal(-2)),
		        klausel(literal(3), literal(-1)),
		        klausel(literal(1)),
		        klausel(literal(-2)),
		        klausel(literal(1))
		);
		Set<Literal> expectedEinheitsLiterale = new HashSet<>(Arrays.asList(
		        literal(-2),
		        literal(1)
		));
		assertGetEinheitsLiterale(expectedEinheitsLiterale, testFormel);
	}
	
	@Test
	public void testGetEinheitsLiterale_Kontradiktion() {
		Formel testFormel = formel(
				klausel(literal(1)),
				klausel(literal(-1))
		);
		Set<Literal> expectedEinheitsLiterale = new HashSet<>(Arrays.asList(
				literal(1),
				literal(-1)
		));
		assertGetEinheitsLiterale(expectedEinheitsLiterale, testFormel);
	}
	
	private void assertGetEinheitsLiterale(Set<Literal> expectedEinheitsLiterale, Formel testFormel) {
		Set<Literal> actualEinheitsLiterale = mySolver.getEinheitsLiterale(testFormel);
		assertContainsOnly(expectedEinheitsLiterale, actualEinheitsLiterale);
	}
	
	@Test
	public void testPropagiereEinheitsLiterale_EinheitsklauselEntfernen() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-4), literal(2)),
				klausel(literal(1), literal(4), literal(-1)),
				klausel(literal(3)),
				klausel(literal(2))
		);
		Set<Literal> einheitsLiterale = new HashSet<>(Arrays.asList(
				literal(3)
		));
		Formel expectedFormel = formel(
				klausel(literal(1), literal(-4), literal(2)),
				klausel(literal(1), literal(4), literal(-1)),
				klausel(literal(2))
		);
		assertPropagiereEinheitsLiterale(expectedFormel, testFormel, einheitsLiterale);
	}
	
	@Test
	public void testPropagiereEinheitsLiterale_KlauselnMitLiteralEntfernen() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-4), literal(2)),
				klausel(literal(1), literal(3), literal(-1)),
				klausel(literal(2))
		);
		Set<Literal> einheitsLiterale = new HashSet<>(Arrays.asList(
				literal(3)
		));
		Formel expectedFormel = formel(
				klausel(literal(1), literal(-4), literal(2)),
				klausel(literal(2))
		);
		assertPropagiereEinheitsLiterale(expectedFormel, testFormel, einheitsLiterale);
	}
	
	@Test
	public void testPropagiereEinheitsLiterale_NegiertesLiteralAusKlauselnEntfernen() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-3), literal(2)),
				klausel(literal(1), literal(4), literal(-1)),
				klausel(literal(2))
		);
		Set<Literal> einheitsLiterale = new HashSet<>(Arrays.asList(
				literal(3)
		));
		Formel expectedFormel = formel(
				klausel(literal(1), literal(2)),
				klausel(literal(1), literal(4), literal(-1)),
				klausel(literal(2))
		);
		assertPropagiereEinheitsLiterale(expectedFormel, testFormel, einheitsLiterale);
	}
	
	@Test
	public void testPropagiereEinheitsLiterale_AllInOne() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-3), literal(2)),
				klausel(literal(1), literal(3), literal(-1)),
				klausel(literal(3)),
				klausel(literal(2))
		);
		Set<Literal> einheitsLiterale = new HashSet<>(Arrays.asList(
				literal(3)
		));
		Formel expectedFormel = formel(
				klausel(literal(1), literal(2)),
				klausel(literal(2))
		);
		assertPropagiereEinheitsLiterale(expectedFormel, testFormel, einheitsLiterale);
	}
	
	@Test
	public void testPropagiereEinheitsLiterale_AllInOne_Negative() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-3), literal(2)),
				klausel(literal(1), literal(3), literal(-1)),
				klausel(literal(3)),
				klausel(literal(2))
		);
		Set<Literal> einheitsLiterale = new HashSet<>(Arrays.asList(
				literal(-3)
		));
		Formel expectedFormel = formel(
				klausel(literal(1), literal(-1)),
				klausel(),
				klausel(literal(2))
		);
		assertPropagiereEinheitsLiterale(expectedFormel, testFormel, einheitsLiterale);
	}
	
	@Test
	public void testPropagiereEinheitsLiterale_Kontradiktion() {
		Formel testFormel = formel(
				klausel(literal(1)),
				klausel(literal(-1))
		);
		Set<Literal> einheitsLiterale = new HashSet<>(Arrays.asList(
				literal(1)
		));
		Formel expectedFormel = formel(
				klausel()
		);
		assertPropagiereEinheitsLiterale(expectedFormel, testFormel, einheitsLiterale);
	}
	
	private void assertPropagiereEinheitsLiterale(Formel expectedFormel, Formel testFormel, Set<Literal> einheitsLiterale) {
		Formel actualFormel = mySolver.propagiereEinheitsLiterale(testFormel, einheitsLiterale);
		assertEquals(expectedFormel, actualFormel);
	}
	
	@Test
	public void testGetPureLiterale_1() {
		Formel testFormel = formel(
				klausel(literal(1), literal(2)),
				klausel(literal(-1), literal(-2)),
				klausel(literal(1), literal(-4))
		);
		Set<Literal> expectedPureLiterals = new HashSet<>(Arrays.asList(
				literal(-4)
		));
		assertGetPureLiterale(expectedPureLiterals, testFormel);
	}
	
	@Test
	public void testGetPureLiterale_MultipleSameLiterals() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-2), literal(-3)),
				klausel(literal(2), literal(1), literal(4)),
				klausel(literal(-3), literal(1)),
				klausel(literal(-4), literal(-3))
		);
		Set<Literal> expectedPureLiterals = new HashSet<>(Arrays.asList(
				literal(1), literal(-3)
		));
		assertGetPureLiterale(expectedPureLiterals, testFormel);
	}
	
	@Test
	public void testGetPureLiterale_Kontradiktion() {
		Formel testFormel = formel(
				klausel(literal(1)),
				klausel(literal(-1))
		);
		Set<Literal> expectedPureLiterals = new HashSet<>();
		assertGetPureLiterale(expectedPureLiterals, testFormel);
	}
	
	private void assertGetPureLiterale(Set<Literal> expectedPureLiterale, Formel testFormel) {
		Set<Literal> actualLiterale = mySolver.getPureLiterale(testFormel);
		assertContainsOnly(expectedPureLiterale, actualLiterale);
	}
	
	@Test
	public void testWeisePureLiteraleZu_SingleKlausel() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-2), literal(-3)),
				klausel(literal(2), literal(3), literal(4)),
				klausel(literal(-3), literal(2)),
				klausel(literal(-4), literal(-3))
		);
		Set<Literal> pureLiterale = new HashSet<>(Arrays.asList(
				literal(1)
		));
		Formel expectedFormel = formel(
				klausel(literal(2), literal(3), literal(4)),
				klausel(literal(-3), literal(2)),
				klausel(literal(-4), literal(-3))
		);
		assertWeisePureLiteraleZu(expectedFormel, testFormel, pureLiterale);
	}
	
	@Test
	public void testWeisePureLiteraleZu_MultipleKlauseln() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-2), literal(-3)),
				klausel(literal(2), literal(1), literal(4)),
				klausel(literal(-3), literal(1)),
				klausel(literal(-4), literal(-3))
		);
		Set<Literal> pureLiterale = new HashSet<>(Arrays.asList(
				literal(1)
		));
		Formel expectedFormel = formel(
				klausel(literal(-4), literal(-3))
		);
		assertWeisePureLiteraleZu(expectedFormel, testFormel, pureLiterale);
	}
	
	@Test
	public void testWeisePureLiteraleZu_FachlichNichtMoeglich() {
		Formel testFormel = formel(
				klausel(literal(1), literal(-2), literal(-3)),
				klausel(literal(2), literal(-1), literal(4)),
				klausel(literal(-3), literal(1)),
				klausel(literal(-4), literal(-3))
		);
		Set<Literal> pureLiterale = new HashSet<>(Arrays.asList(
				literal(1)
		));
		Formel expectedFormel = formel(
				klausel(literal(2), literal(-1), literal(4)),
				klausel(literal(-4), literal(-3))
		);
		assertWeisePureLiteraleZu(expectedFormel, testFormel, pureLiterale);
	}
	
	@Test
	public void testWeisePureLiteraleZu_Kontradiktion() {
		// Kann fachlich eigentlich nicht vorkommen, da "1" in diesem Fall garkein pures Literal ist
		Formel testFormel = formel(
				klausel(literal(1)),
				klausel(literal(-1))
		);
		Set<Literal> pureLiterale = new HashSet<>(Arrays.asList(
				literal(1)
		));
		Formel expectedFormel = formel(
				klausel(literal(-1))
		);
		assertWeisePureLiteraleZu(expectedFormel, testFormel, pureLiterale);
	}
	
	private void assertWeisePureLiteraleZu(Formel expectedFormel, Formel testFormel, Set<Literal> pureLiterale) {
		Formel actualFormel = mySolver.weisePureLiteraleZu(testFormel, pureLiterale);
		assertEquals(expectedFormel, actualFormel);
	}
	
	@Override
	protected DPLLSatSolver createSolver() {
		return new DPLLSatSolver();
	}
}
