package ca.svarb.whelper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OffsetGridTest {

	public static String[][] gridStrings = { { "c", "a", "t" },
                                             { "h", "o", "g" },
                                             { "d", "o", "g" } };

	private OffsetGrid wgrid;

	@Before
	public void setup() {
		wgrid = new OffsetGrid(6);
		wgrid.getCell(2,1).setValue("A");
		wgrid.getCell(0,0).setValue("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructZero() {
		new Grid(0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructNegative() {
		new Grid(-1);
	}

	/**
	 * Check that values and neighbours are set correctly
	 */
	@Test
	public void getCell() {
		assertSame( wgrid.getCell(0, 0), wgrid.getCell(1, 0).getLeftCell() );
		assertSame( wgrid.getCell(1, 0), wgrid.getCell(0, 0).getRightCell() );

		// Last cell on the row wraps around to the left
		assertSame( wgrid.getCell(0, 0), wgrid.getCell(5, 0).getRightCell() );

		// Last cell on the col wraps around to top
		assertSame( wgrid.getCell(0, 0), wgrid.getCell(0, 5).getDownCell() );
	}
}
