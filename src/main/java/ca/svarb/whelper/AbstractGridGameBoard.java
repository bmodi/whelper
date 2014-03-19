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

	public AbstractGridGameBoard(String[][] gridStrings) {
		ArgumentChecker.checkNulls("gridStrings", gridStrings);
		setSize(gridStrings.length);
		for(int col=0; col<size; col++) {
			String[] rowStrings=gridStrings[col];
			int rowCount=rowStrings.length;
			if( rowCount!=size ) {
				throw new IllegalArgumentException("TextUtils.getGridFromString2D: gridStrings argument must be square");
			}
			for(int row=0; row<rowCount; row++) {
				cells[col][row].setValue(gridStrings[row][col]);
			}
		}
	}

	protected AbstractGridGameBoard() {
	}

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


	public Cell getCell(int col, int row) {
		if ( col<0 || col>=size ) throw new IllegalArgumentException("Cell.getCell col out of range: size="+size+", col="+col);
		if ( row<0 || row>=size ) throw new IllegalArgumentException("Cell.getCell row out of range: size="+size+", row="+row);
		return cells[col][row];
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size<1) throw new IllegalArgumentException("Grid.size must be positive - size="+size);
		this.size=size;
		initializeCells();
	}

	private void initializeCells() {
		cells=new Cell[size][size];
		cellList=new ArrayList<Cell>(size*size);
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				Cell currentCell=new Cell();
				cells[col][row]=currentCell;
				cellList.add(currentCell);
				initCell(col, row);
			}
		}
	}

	abstract protected void initCell(int col, int row);
}
