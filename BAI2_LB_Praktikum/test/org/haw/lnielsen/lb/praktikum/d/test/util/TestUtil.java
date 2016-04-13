package org.haw.lnielsen.lb.praktikum.d.test.util;

import static org.junit.Assert.*;
import java.util.Set;

public class TestUtil {
	private TestUtil() {}
	
	public static <T> void assertContainsOnly(Set<T> expected, Set<T> actual) {
		assertEquals(expected.size(), actual.size());
		for(T element : expected) {
			assertTrue(actual.contains(element));
		}
	}
}
