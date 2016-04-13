package org.haw.lnielsen.lb.praktikum.d.sat.formel.knf;

public class FormelUtils {
	private FormelUtils() {}
	
	public static Literal literal(int id) {
		return Literal.getLiteral(id);
	}
	
	public static Klausel klausel(Literal... literale) {
		return new Klausel(literale);
	}
	
	public static Formel formel(Klausel... klauseln) {
		return new Formel(klauseln);
	}
}
