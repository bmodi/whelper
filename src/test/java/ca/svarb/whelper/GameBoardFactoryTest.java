package ca.svarb.whelper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ca.svarb.whelper.GameBoardFactory.BoardType;

public class GameBoardFactoryTest {

	private GameBoardFactory factory=null;

	@Before
	public void setup() {
		factory = GameBoardFactory.getInstance();
	}
	
	@Test void getInstance() {
		assertSame( factory, GameBoardFactory.getInstance() );
	}

	@Test
	public void getGameBoardGrid() {
		assertTrue( factory.getGameBoard(BoardType.GRID, 3) instanceof Grid );
	}

	@Test
	public void getGameBoardOffsetGrid() {
		assertTrue( factory.getGameBoard(BoardType.OFFSET_GRID, 3) instanceof OffsetGrid );
	}

}
