package org.haw.lnielsen.lb.praktikum.d.sat.parser;

@SuppressWarnings("serial")
public class SatParseException extends Exception {
	public SatParseException() {
		super();
	}
	
	public SatParseException(String message) {
		super(message);
	}
	
	public SatParseException(Throwable cause) {
		super(cause);
	}
	
	public SatParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
