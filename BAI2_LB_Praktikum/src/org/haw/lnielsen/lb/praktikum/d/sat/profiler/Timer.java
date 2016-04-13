package org.haw.lnielsen.lb.praktikum.d.sat.profiler;

import java.util.Objects;

public class Timer {
	public static final Zeitmesser MILLIS = new SystemMillisZeitmesser();
	public static final Zeitmesser NANOS = new SystemNanosZeitmesser();
	
	private Zeitmesser myZeitmesser;
	private long myStartNanos;
	
	public Timer() {
		this(MILLIS);
	}
	
	public Timer(Zeitmesser messer) {
		setZeitmesser(messer);
	}
	
	public Zeitmesser getZeitmesser() {
		return myZeitmesser;
	}
	
	public void setZeitmesser(Zeitmesser messer) {
		myZeitmesser = Objects.requireNonNull(messer);
	}
	
	public void start() {
		myStartNanos = myZeitmesser.missZeit();
	}
	
	public long stop() {
		return myZeitmesser.missZeit() - myStartNanos;
	}
	
  public long stopAndRestart() {
		long time = stop();
		start();
		return time;
  }
  
  public static interface Zeitmesser {
  	long missZeit();
  }
  
  private static class SystemNanosZeitmesser implements Zeitmesser {
		@Override
		public long missZeit() {
			return System.nanoTime();
		}
  }
  
  private static class SystemMillisZeitmesser implements Zeitmesser {
		@Override
    public long missZeit() {
	    return System.currentTimeMillis();
    }
  }
}
