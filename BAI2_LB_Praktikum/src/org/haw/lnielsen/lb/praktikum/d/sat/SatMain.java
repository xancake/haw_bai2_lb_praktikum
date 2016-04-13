package org.haw.lnielsen.lb.praktikum.d.sat;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.haw.lnielsen.lb.praktikum.d.sat.formel.knf.Formel;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.SatParseException;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.SatParser;
import org.haw.lnielsen.lb.praktikum.d.sat.parser.dimacs.DimacsSatParser;
import org.haw.lnielsen.lb.praktikum.d.sat.profiler.Timer;
import org.haw.lnielsen.lb.praktikum.d.sat.solver.SatSolver;
import org.haw.lnielsen.lb.praktikum.d.sat.solver.dpll.DPLLSatSolver;

public class SatMain {
	private static final SatParser DEFAULT_PARSER = new DimacsSatParser();
	private static final SatSolver DEFAULT_SOLVER = new DPLLSatSolver();
	
	private static final String DYNAMIC_MODE_CONTINUE = "ja";
	private static final String DEBUG = "DEBUG";
	
	public static void main(String... args) {
		if(args.length == 0) {
			dynamischerModus(DEFAULT_PARSER, DEFAULT_SOLVER);
		} else {
			if(DEBUG.equals(args[0])) {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {}
			}
			batchModus(DEFAULT_PARSER, DEFAULT_SOLVER, args);
		}
	}
	
	private static void dynamischerModus(SatParser parser, SatSolver solver) {
		System.out.println("Der dynamischer Modus wurde gestartet! Hier können Sie Sat-Formeln nach belieben eingeben und verarbeiten lassen.");
		Scanner keyboard = new Scanner(System.in);
		String eingabe = DYNAMIC_MODE_CONTINUE;
		while(DYNAMIC_MODE_CONTINUE.equals(eingabe)) {
			System.out.println("Geben Sie Ihre dimacs-codierte Sat-Formel ein:");
			
			String formelStr = "";
			int lineCount = 0;
			int lineCountMax = 1;
			while(lineCount<lineCountMax) {
				eingabe = keyboard.nextLine().trim();
				formelStr += eingabe + "\n";
				if(eingabe.startsWith("p cnf")) {
					lineCountMax = Integer.parseInt(eingabe.substring(eingabe.lastIndexOf(" ")+1));
				} else {
					lineCount++;
				}
			}
			
			try {
				verarbeiteFormel(DEFAULT_PARSER, DEFAULT_SOLVER, new ByteArrayInputStream(formelStr.getBytes()));
			} catch(IOException e) {
				e.printStackTrace();
			} catch(SatParseException e) {
				System.err.println("FEHLER: Die Formel konnte nicht verarbeitet werden");
				e.printStackTrace();
			}
			
			System.out.print("Weiter? ('" + DYNAMIC_MODE_CONTINUE + "'), Abbrechen? (nicht '" + DYNAMIC_MODE_CONTINUE + "'): ");
			eingabe = keyboard.nextLine().trim().toLowerCase();
			System.out.println();
		}
		keyboard.close();
	}
	
	private static void batchModus(SatParser parser, SatSolver solver, String... filePaths) {
		for(String filePath : filePaths) {
			System.out.println("Verarbeite '" + filePath +"'");
			try(InputStream fileStream = new FileInputStream(filePath)) {
				verarbeiteFormel(parser, solver, fileStream);
			} catch(SatParseException e) {
				System.err.println("FEHLER: Die Formel konnte nicht verarbeitet werden");
				e.printStackTrace();
			} catch(FileNotFoundException e) {
				System.err.println("FEHLER: Die angegebene Datei konnte nicht gefunden werden!\n  " + filePath);
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}
	
	private static void verarbeiteFormel(SatParser parser, SatSolver solver, InputStream stream) throws IOException, SatParseException {
		Timer timer = new Timer();
		timer.start();
		Formel formel = parseFormel(parser, stream);
		loeseFormel(solver, formel);
		long time = timer.stop();
		System.out.println("Gesamtdauer: " + time + "ms");
	}
	
	private static Formel parseFormel(SatParser parser, InputStream stream) throws IOException, SatParseException {
		System.out.print("lese Formel...");
		Timer timer = new Timer();
		timer.start();
		Formel formel = parser.parseFormel(stream);
		long parseTime = timer.stop();
		System.out.println(" abgeschlossen (" + parseTime + "ms)");
		return formel;
	}
	
	private static void loeseFormel(SatSolver solver, Formel formel) {
		System.out.print("l�se Formel...");
		Timer timer = new Timer();
		timer.start();
		boolean loesbar = solver.solve(formel);
		long solveTime = timer.stop();
		System.out.println(" abgeschlossen: " + (!loesbar ? "UN" : "") + "SATISFIABLE (" + solveTime + "ms)");
	}
}
