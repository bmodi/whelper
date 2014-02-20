package ca.svarb.whelper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class PathTest {

	private Cell c1;
	private Cell c2;
	private Cell c3;
	private Cell c4;
	private Path path1;
	private Path path2;

	private static String[][] gridStrings = { { "c", "a", "t" },
                                             { "h", "o", "g" },
                                             { "d", "o", "g" } };
	
	private TextUtils textUtils;

	private Grid grid;

	private Cell cell00;
	private Cell cell01;
	private Cell cell02;
	private Cell cell10;
	private Cell cell11;
	private Cell cell12;
//	private Cell cell20;
	private Cell cell21;
	private Cell cell22;

	@Before
	public void setup() {
		textUtils = TextUtils.getInstance();
		grid = textUtils.getGridFromString2D(gridStrings);
		cell00 = grid.getCell(0, 0);
		cell01 = grid.getCell(0, 1);
		cell02 = grid.getCell(0, 2);
		cell10 = grid.getCell(1, 0);
		cell11 = grid.getCell(1, 1);
		cell12 = grid.getCell(1, 2);
//		cell20 = grid.getCell(2, 0);
		cell21 = grid.getCell(2, 1);
		cell22 = grid.getCell(2, 2);
		path2 = new Path();
		path2.addCell(cell00);

		c1 = new Cell("a");
		c2 = new Cell("pp");
		c3 = new Cell("");
		c4 = new Cell("le");
		path1 = new Path();
		path1.addCell(c1);
		path1.addCell(c2);
		path1.addCell(c3);
		path1.addCell(c4);
	}


	@Test
	public void getCells() {
		assertEquals(4, path1.getCells().size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void crossPath() {
		path1.addCell(c2);
	}

	@Test
	public void nextPaths() {
		List<Path> nextPaths = path2.nextPaths();
		assertEquals(3, nextPaths.size());
		boolean found01=false;
		boolean found10=false;
		boolean found11=false;
		Path path01=null;
		for (Path path : nextPaths) {
			assertEquals(2, path.getCells().size());
			assertSame(cell00, path.getCells().get(0));
			Cell cell2=path.getCells().get(1);
			if(cell2==cell01) {
				found01=true;
				path01=path;
			}
			if(cell2==cell10) found10=true;
			if(cell2==cell11) found11=true;
		}
		assertTrue(found01);
		assertTrue(found10);
		assertTrue(found11);
		
		// Make sure next level of paths does not include cells already in the path
		List<Path> nextPaths2 = path01.nextPaths();
		assertEquals(4, nextPaths2.size());
		boolean found01_10=false;
		boolean found01_11=false;
		boolean found01_12=false;
		boolean found01_02=false;
		for (Path path : nextPaths2) {
			assertEquals(3, path.getCells().size());
			assertSame(cell00, path.getCells().get(0));
			assertSame(cell01, path.getCells().get(1));
			Cell cell3=path.getCells().get(2);
			if(cell3==cell10) found01_10=true;
			if(cell3==cell11) found01_11=true;
			if(cell3==cell12) found01_12=true;
			if(cell3==cell02) found01_02=true;
		}
		assertTrue(found01_10);
		assertTrue(found01_11);
		assertTrue(found01_12);
		assertTrue(found01_02);
	}
	
	/**
	 * Make sure when there are no more neighbours
	 * that aren't already used that there are no
	 * more paths to return.
	 */
	@Test
	public void nextPathsEnd() {
		Path path=new Path();
		path.addCell(cell21);
		path.addCell(cell11);
		path.addCell(cell12);
		path.addCell(cell22);
		List<Path> nextPaths = path.nextPaths();
		assertEquals(0, nextPaths.size());
	}
	
	@Test
	public void getWord() {
		assertEquals("apple", path1.getWord());
	}
	
	@Test
	public void toString_equalsGetWord() {
		assertEquals("apple", path1.toString());
	}	

	@Test
	public void getWordEmptyPath() {
		Path path=new Path();
		assertEquals("", path.getWord());
	}

	/**
	 * Check for bug where nextPaths() method traverses
	 * blank cells.
	 * Blanking the centre cell should prevent "cog" from
	 * being found from this path:
	 * C00 + C11 + C12 + C22
	 * "c" + ""  + "o" + "g" = "cog"
	 * Traversal should stop when it hits a blank cell.
	 */
	@Test
	public void nextPathsStopsAtBlankCells() {
		cell11.setValue("");
		List<Path> nextPaths = path2.nextPaths();
		assertEquals(2, nextPaths.size());
		boolean found01=false;
		boolean found10=false;
		boolean found11=false;
		for (Path path : nextPaths) {
			assertEquals(2, path.getCells().size());
			assertSame(cell00, path.getCells().get(0));
			Cell cell2=path.getCells().get(1);
			if(cell2==cell01) {
				found01=true;
			}
			if(cell2==cell10) found10=true;
			if(cell2==cell11) found11=true;
		}
		assertTrue(found01);
		assertTrue(found10);
		assertFalse(found11);
	}
}
