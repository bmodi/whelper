package ca.svarb.whelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import ca.svarb.utils.ArgumentChecker;

/**
 * A Grid stores a set of Cells in an set of columns
 * offset from each other by 1/2 Cell width.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
public class OffsetGrid implements GameBoard {

	private int size;
	private Cell[][] cells=null;

	/**
	 * Make an offset-square grid of blank Cells.
	 * Cells will be initialized with neighbours according
	 * to grid position.
	 * @param size
	 */
	public OffsetGrid(int size) {
		if (size<1) throw new IllegalArgumentException("Grid.size must be positive - size="+size);
		this.size=size;
		cells=new Cell[size][size];
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				Cell currentCell=new Cell();
				cells[col][row]=currentCell;
				initializeNeighbours(col, row);
			}
		}
	}

	private void initializeNeighbours(int col, int row) {
		Cell currentCell=this.getCell(col, row);
		if(col>0) {
			Cell left=this.getCell(col-1, row);
			currentCell.addNeighbour(left);
			if(row<this.size-1) {
				Cell aboveRight=this.getCell(col-1, row+1);
				currentCell.addNeighbour(aboveRight);
			}
		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.addNeighbour(above);
			if(col>0) {
				Cell aboveLeft=this.getCell(col-1, row-1);
				currentCell.addNeighbour(aboveLeft);
			}
		}
	}

	public Cell getCell(int col, int row) {
		if ( col<0 || col>=size ) throw new IllegalArgumentException("Cell.getCell col out of range: size="+size+", col="+col);
		if ( row<0 || row>=size ) throw new IllegalArgumentException("Cell.getCell row out of range: size="+size+", row="+row);
		return cells[col][row];
	}

	public int getSize() {
		return size;
	}

	public Iterator<Cell> iterator() {
		return new Iterator<Cell>() {
			private int col=0;
			private int row=0;
			private boolean hasNext=true;
			
			public boolean hasNext() {
				return hasNext;
			}

			public Cell next() {
				if ( !hasNext ) {
					throw new NoSuchElementException();
				}
				Cell cell=cells[col++][row];
				if(col==size) {
					col=0;
					row++;
					if(row==size) hasNext=false;
				}
				return cell;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
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
	 * Call setSelected(false) on all Cells.
	 */
	public void clearSelection() {
		for (Cell cell : this) {
			cell.setSelected(false);
		}
	}

}
