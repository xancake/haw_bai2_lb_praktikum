package org.haw.lnielsen.lb.praktikum.d.sat.parser.dimacs;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Klausel;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Literal;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.SatParseException;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.SatParser;

public class DimacsSatParser implements SatParser {
	private static final String DIMACS_HEADER_PREFIX = "p cnf";
	private static final String KOMMENTAR_INDIKATOR  = "c";
	private static final String LITERAL_SEPARATOR    = " ";
	private static final String KLAUSEL_ENDE         = "0";
	
	@Override
	public Formel parseFormel(InputStream inputStream) throws SatParseException, IOException {
		try(Scanner reader = new Scanner(new BufferedInputStream(inputStream))) {
			
			String header = reader.nextLine();
			if(!header.startsWith(DIMACS_HEADER_PREFIX)) {
				throw new SatParseException("Die angegebene Formel enthält keinen Header '" + DIMACS_HEADER_PREFIX + " x y'");
			}
			
			int klauseln = Integer.parseInt(header.substring(header.lastIndexOf(LITERAL_SEPARATOR)+1));
//			int variablen = Integer.parseInt(header.substring(DIMACS_HEADER_PREFIX.length() + 1, header.lastIndexOf(LITERAL_SEPARATOR)));
			
			Formel formel = new Formel();
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(!line.startsWith(KOMMENTAR_INDIKATOR)) {
					formel.addKlausel(parseKlausel(line));
				}
			}
			
			if(formel.getKlauselCount() != klauseln) {
				throw new SatParseException("Die angegebene Formel enthält nicht '" + klauseln + "' Klauseln (tatsächlich: '" + formel.getKlauselCount() + "')");
			}
//			if(myLiteralCache.size()/2 > variablen) {
//				throw new SatParseException("Die angegebene Formel enthält nicht '" + variablen + "' Literale (tatsächlich: '" + myLiteralCache.size()/2 + "')");
//			}
			
			return formel;
		} catch(NumberFormatException e) {
			throw new SatParseException("Die Header-Zeile war in einem ungültigen Format", e);
		}
	}
	
	@Override
	public Formel parseFormel(String formelStr) throws SatParseException {
		try {
			return parseFormel(new ByteArrayInputStream(formelStr.getBytes()));
		} catch(IOException e) {
			throw new SatParseException("Fehler beim parsen der Formel!", e);
		}
	}
	
	@Override
	public Klausel parseKlausel(String klauselStr) throws SatParseException {
		klauselStr = klauselStr.trim();
		if(!klauselStr.endsWith(KLAUSEL_ENDE)) {
			throw new SatParseException("Die angegebene Klausel endet nicht mit dem Klausel-Ende-Zeichen '" + KLAUSEL_ENDE + "'");
		}
		Klausel klausel = new Klausel();
		for(String literalStr : klauselStr.split(LITERAL_SEPARATOR)) {
			if(KLAUSEL_ENDE.equals(literalStr)) {
				break;
			}
			klausel.addLiteral(parseLiteral(literalStr));
		}
		return klausel;
	}
	
	@Override
	public Literal parseLiteral(String literalStr) throws SatParseException {
		Integer literalInt = Integer.parseInt(literalStr);
		return Literal.getLiteral(literalInt);
	}
}
