package ca.svarb.whelper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;


public class CellTest {

	private Cell c00;
	private Cell c10;
	private Cell c21;
	private Cell c01;

	@Before
	public void setup() {
		c00 = new Cell("a");
		c10 = new Cell("a");
		c21 = new Cell("c");
		c01 = new Cell("d");
	}
	
	@Test
	public void addNeighbour() {
		c00.addNeighbour(c10);
		c00.addNeighbour(c01);
		c10.addNeighbour(c21);
		
		Collection<Cell> neighbours = c00.getNeighbours();
		assertEquals(2, neighbours.size());
		assertThat(neighbours, hasItem(c10));
		assertThat(neighbours, hasItem(c01));
		
		neighbours = c10.getNeighbours();
		assertEquals(2, neighbours.size());
		assertThat(neighbours, hasItem(c00));
		assertThat(neighbours, hasItem(c21));
	}

	@Test(expected=IllegalArgumentException.class)
	public void addNeighbourToSelf() {
		c00.addNeighbour(c00);
	}
	
	@Test(expected=NullPointerException.class)
	public void addNeighbourNullNeighbour() {
		c00.addNeighbour(null);
	}

	@Test(expected=NullPointerException.class)
	public void constructNullValue() {
		new Cell(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void setNullValue() {
		c00.setValue(null);
	}
	
	@Test
	public void testToString() {
		assertEquals("c", c21.toString());
	}
}
