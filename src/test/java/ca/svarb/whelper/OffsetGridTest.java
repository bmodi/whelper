package ca.svarb.whelper;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
		assertSame( wgrid.getCell(0, 0), wgrid.getCell(4, 0).getRightCell() );

		// Last cell on the col wraps around to top
		assertSame( wgrid.getCell(0, 0), wgrid.getCell(0, 4).getDownCell() );
		
		// Last cell on the odd columns can't navigate left/right
		assertSame( wgrid.getCell(1, 5), wgrid.getCell(1, 5).getLeftCell() );
		
//		assertSame( wgrid.getCell(1, 0), wgrid.getCell(0, 0).getRightCell() );
//		assertSame( wgrid.getCell(0, 2), wgrid.getCell(0, 0).getUpCell() );
//		assertSame( wgrid.getCell(0, 1), wgrid.getCell(0, 0).getDownCell() );
//		assertSame( wgrid.getCell(1, 2), wgrid.getCell(2, 2).getLeftCell() );
//		assertSame( wgrid.getCell(0, 2), wgrid.getCell(2, 2).getRightCell() );
//		assertSame( wgrid.getCell(2, 1), wgrid.getCell(2, 2).getUpCell() );
//		assertSame( wgrid.getCell(2, 0), wgrid.getCell(2, 2).getDownCell() );
	}

//	@Test(expected=IllegalArgumentException.class)
//	public void getCellLowCol() {
//		wgrid.getCell(-1,1);
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void getCellHighCol() {
//		wgrid.getCell(3,1);
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void getCellLowRow() {
//		wgrid.getCell(2,-1);
//	}
//
//	@Test(expected=IllegalArgumentException.class)
//	public void getCellHighRow() {
//		wgrid.getCell(2,3);
//	}
//
//	@Test
//	public void iterator() {
//		Iterator<Cell> iterator = wgrid.iterator();
//		Cell cell=iterator.next();
//		assertEquals("", cell.getValue());
//		iterator.next();
//		iterator.next();
//		iterator.next();
//		iterator.next();
//		cell=iterator.next();
//		assertEquals("A", cell.getValue());
//		iterator.next();
//		iterator.next();
//		iterator.next();
//		assertFalse(iterator.hasNext());
//	}
//
//	@Test(expected=NoSuchElementException.class)
//	public void iteratorFinished() {
//		Iterator<Cell> iterator = wgrid.iterator();
//		for( int i=0; i<9; i++ ) {
//			iterator.next();
//		}
//		assertFalse(iterator.hasNext());
//		iterator.next();
//	}
//
//	@Test(expected=UnsupportedOperationException.class)
//	public void iteratorRemove() {
//		Iterator<Cell> iterator = wgrid.iterator();
//		iterator.remove();
//	}
//	
//	@Test
//	public void getInitialPaths() {
//		List<Path> initialPaths = wgrid.getInitialPaths();
//		assertEquals(9, initialPaths.size());
//		for (Path path : initialPaths) {
//			assertEquals(1, path.getCells().size());
//		}
//		assertSame(wgrid.getCell(0, 0), initialPaths.get(0).getCells().get(0));
//		assertSame(wgrid.getCell(0, 2), initialPaths.get(6).getCells().get(0));
//	}
	
//	@Test
//	public void findWord() {
//		wgrid=TextUtils.getInstance().getGridFromString2D(gridStrings);
//		Path wordPath = wgrid.findWord("hat");
//		assertEquals(3, wordPath.getCells().size());
//		assertSame(wgrid.getCell(0, 1), wordPath.getCells().get(0));
//		assertSame(wgrid.getCell(1, 0), wordPath.getCells().get(1));
//		assertSame(wgrid.getCell(2, 0), wordPath.getCells().get(2));
//	}

//	@Test
//	public void findWordNotFound() {
//		assertNull( wgrid.findWord("hop") );  // Partial word
//		assertNull( wgrid.findWord("hats") ); // Incomplete word
//		assertNull( wgrid.findWord("zim") );  // No letters match
//	}
//	
//	@Test
//	public void clearSelection() {
//		wgrid.getCell(0, 0).setSelected(true);
//		wgrid.getCell(1, 2).setSelected(true);
//		wgrid.getCell(2, 1).setSelected(true);
//		assertTrue(wgrid.getCell(1, 2).isSelected());
//		assertTrue(wgrid.getCell(2, 1).isSelected());
//		assertFalse(wgrid.getCell(2, 2).isSelected());
//		wgrid.clearSelection();
//		assertFalse(wgrid.getCell(1, 2).isSelected());
//		assertFalse(wgrid.getCell(2, 1).isSelected());
//	}
}
