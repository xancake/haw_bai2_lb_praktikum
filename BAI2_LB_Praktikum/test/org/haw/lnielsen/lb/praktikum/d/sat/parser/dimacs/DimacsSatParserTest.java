package org.haw.lnielsen.lb.praktikum.d.sat.parser.dimacs;

import static org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.FormelUtils.*;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.SatParseException;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.SatParserTest_A;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.dimacs.DimacsSatParser;
import org.junit.Test;

public class DimacsSatParserTest extends SatParserTest_A<DimacsSatParser> {
	@Test
	public void testParseFormel_1() throws Exception {
		String formelStr = "p cnf 3 2\n1 -3 0\n2 3 -1 0";
		Formel formel = formel(klausel(literal(1), literal(-3)), klausel(literal(2), literal(3), literal(-1)));
		assertParseFormel(formel, formelStr);
	}
	
	@Test
	public void testParseFormel_MehrstelligVieleZahlen() throws Exception {
		String formelStr = "p cnf 6 12\n1 0\n-1 0\n2 0\n-2 0\n3 0\n-3 0\n4 0\n-4 0\n5 0\n-5 0\n6 0\n-6 0";
		Formel formel = formel(klausel(literal(1)), klausel(literal(-1)), klausel(literal(2)), klausel(literal(-2)), klausel(literal(3)), klausel(literal(-3)), klausel(literal(4)), klausel(literal(-4)), klausel(literal(5)), klausel(literal(-5)), klausel(literal(6)), klausel(literal(-6)));
		assertParseFormel(formel, formelStr);
	}
	
	@Test(expected=SatParseException.class)
	public void testParseFormel_KeineHeaderZeile() throws Exception {
		myParser.parseFormel("1 -3 0\n2 3 -1 0");
	}
	
	@Test(expected=SatParseException.class)
	public void testParseFormel_ZuWenigZeilen() throws Exception {
		myParser.parseFormel("p cnf 3 3\n1 -3 0\n2 3 -1 0");
	}
	
	@Test(expected=SatParseException.class)
	public void testParseFormel_ZuVieleZeilen() throws Exception {
		myParser.parseFormel("p cnf 3 1\n1 -3 0\n2 3 -1 0");
	}
	
	@Test(expected=SatParseException.class)
	public void testParseFormel_ZeilenangabeKeineZahl() throws Exception {
		myParser.parseFormel("p cnf 3 a\n1 -3 0\n2 3 -1 0");
	}
	
	@Test
	public void testParseKlausel_1() throws Exception {
		assertParseKlausel(klausel(literal(3), literal(-1), literal(2)), "3 -1 2 0");
	}
	
	@Test(expected=SatParseException.class)
	public void testParseKlausel_OhneKlauselEnde() throws Exception {
		myParser.parseKlausel("3 -1 2");
	}
	
	@Test
	public void testParseLiteral_Positive() throws Exception {
		assertParseLiteral(literal(1), "1");
	}
	
	@Test
	public void testParseLiteral_Negative() throws Exception {
		assertParseLiteral(literal(-2), "-2");
	}
	
	@Override
	public DimacsSatParser createParser() {
		return new DimacsSatParser();
	}
}
