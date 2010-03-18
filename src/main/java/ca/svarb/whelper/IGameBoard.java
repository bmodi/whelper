package ca.svarb.whelper;

import java.util.List;

public interface IGameBoard extends Iterable<Cell> {

	/**
	 * Return a list of paths each
	 * of which is a starting point
	 * for each Cell in the grid and
	 * each containing only that starting
	 * Cell.
	 * @return
	 */
	public List<Path> getInitialPaths();

	public void clearSelection();

	public Path findWord(String selectedValue);

	public Cell getCellAt(int x, int y);
}
