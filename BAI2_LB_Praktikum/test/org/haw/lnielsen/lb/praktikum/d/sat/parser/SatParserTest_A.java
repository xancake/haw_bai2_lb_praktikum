package org.haw.lnielsen.lb.praktikum.d.sat.parser;

import static org.junit.Assert.*;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Klausel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;
import org.junit.Before;

public abstract class SatParserTest_A<T extends SatParser> {
	protected T myParser;
	
	@Before
	public void setUp() {
		myParser = createParser();
	}
	
	protected void assertParseFormel(Formel expectedFormel, String parseFormel) throws SatParseException {
		Formel actualFormel = myParser.parseFormel(parseFormel);
		assertEquals(expectedFormel, actualFormel);
	}
	
	protected void assertParseKlausel(Klausel expectedKlausel, String parseKlausel) throws SatParseException {
		Klausel actualKlausel = myParser.parseKlausel(parseKlausel);
		assertEquals(expectedKlausel, actualKlausel);
	}
	
	protected void assertParseLiteral(Literal expectedLiteral, String parseLiteral) throws SatParseException {
		Literal actualLiteral = myParser.parseLiteral(parseLiteral);
		assertEquals(expectedLiteral, actualLiteral);
	}
	
	protected abstract T createParser();
}
