package ca.svarb.whelper;

import static org.junit.Assert.*;

import org.junit.Test;

public class WhelperExceptionTest {

	@Test
	public void construct() {
		WhelperException e = new WhelperException("check");
		assertEquals("check", e.getMessage());
	}

}
