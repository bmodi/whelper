package ca.svarb.whelper;

public class GameBoardFactory {

	public enum BoardType { GRID, OFFSET_GRID };
	
	public static GameBoardFactory getInstance() {
		return null;
	}

	public GameBoard getGameBoard(BoardType boardType, int size) {
		Grid grid=new Grid(size);
		return grid;
	}

}
