package ca.svarb.whelper;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ca.svarb.whelper.GameBoardFactory.BoardType;

public class GameBoardFactoryTest {

	private GameBoardFactory factory=null;
	
	@Before
	public void setup() {
		factory=GameBoardFactory.getInstance();		
	}

	@Test
	public void singleton() {
		assertSame(factory, GameBoardFactory.getInstance());
	}
	
	@Test
	public void getGameboardGrid_Square() {
		IGameBoard gameBoard = factory.getGameBoard(BoardType.GRID, 3);
		assertTrue(gameBoard instanceof Grid);
	}

	@Test
	public void getGameboardOffsetGrid() {
		IGameBoard gameBoard = factory.getGameBoard(BoardType.OFFSET_GRID, 6);
		assertTrue(gameBoard instanceof OffsetGrid);
	}
}
