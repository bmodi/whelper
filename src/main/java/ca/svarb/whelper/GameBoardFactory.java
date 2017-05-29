package ca.svarb.whelper;

public class GameBoardFactory {

	public enum BoardType { GRID, OFFSET_GRID };
	
	// Hide constructor to enforce singleton
	private GameBoardFactory() {}
	
	private static GameBoardFactory factory=null;
	
	public static GameBoardFactory getInstance() {
		if ( factory==null ) factory=new GameBoardFactory();
		return factory;
	}

	public IGameBoard getGameBoard(BoardType boardType, int size) {
		IGameBoard board=null;
		switch (boardType) {
		case GRID:
			board=new Grid(size);
			break;
		case OFFSET_GRID:
			board=new OffsetGrid(size);
			break;
		}
		return board;
	}
}
