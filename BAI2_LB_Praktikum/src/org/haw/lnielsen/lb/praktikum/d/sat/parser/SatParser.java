package org.haw.lnielsen.lb.praktikum.d.sat.parser;

import java.io.IOException;
import java.io.InputStream;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Klausel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;

public interface SatParser {
	
	Formel parseFormel(InputStream inputStream) throws SatParseException, IOException;
	
	
	Formel parseFormel(String formel) throws SatParseException;
	
	
	Klausel parseKlausel(String klausel) throws SatParseException;
	
	
	Literal parseLiteral(String literal) throws SatParseException;
}
