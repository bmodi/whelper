package ca.svarb.whelper;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ca.svarb.utils.ArgumentChecker;

public abstract class AbstractGridGameBoard extends ArrayList<Cell> implements IGameBoard {

	private static final int ICON_SIZE = 40;
	protected int size;
	protected Cell[][] cells = null;
	protected List<Cell> cellList = null;

	/**
	 * Return a list of paths each
	 * of which is a starting point
	 * for each Cell in the grid and
	 * each containing only that starting
	 * Cell.
	 * @return
	 */
	public List<Path> getInitialPaths() {
		List<Path> paths = new ArrayList<Path>();
		for (Cell cell : this) {
			Path path = new Path();
			path.addCell(cell);
			paths.add(path);
		}
		return paths;
	}

	/**
	 * Call setSelected(false) on all Cells.
	 */
	public void clearSelection() {
		for (Cell cell : this) {
			cell.setSelected(false);
		}
	}

	/**
	 * Checks if this grid contains the given
	 * word, returning the first path it finds
	 * that does, or <code>null</code> if the
	 * word is not contained in the grid.
	 * @param string
	 * @return
	 */
	public Path findWord(String word) {
		ArgumentChecker.checkNulls("word", word);
		List<Path> initialPaths = getInitialPaths();
		for (Path path : initialPaths) {
			Path foundPath = findWord(word,path);
			if ( foundPath!=null ) {
				return foundPath;
			}
		}
		return null;
	}

	private Path findWord(String word, Path path) {
		if ( word.equals(path.getWord()) ) {
			return path;
		}
		if ( path.getCells().size()==word.length() ) {
			return null;
		}
		for( Path nextPath : path.nextPaths() ) {
			Path foundPath = findWord(word,nextPath);
			if ( foundPath!=null ) {
				return foundPath;
			}
		}
		return null;
	}

	public Cell getCellAt(int x, int y) {
		Cell foundCell=null;
		for (Cell cell : this) {
			if ( x>=cell.getX() && x<cell.getX()+Cell.CELL_WIDTH &&
				 y>=cell.getY() && y<cell.getY()+Cell.CELL_WIDTH ) {
				foundCell=cell;
				break;
			}				
		}
		return foundCell;
	}

	/**
	 * The iterator will traverse rows down a column first
	 * then over the next column once all the rows in the
	 * column are returned.
	 */
	public Iterator<Cell> iterator() {
		return this.getCells().iterator();
	}

	public List<Cell> getCells() {
		return Collections.unmodifiableList(cellList);
	}

	public Dimension getPreferredSize() {
		return new Dimension(ICON_SIZE*size, ICON_SIZE*size);
	}

}
