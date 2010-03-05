package ca.svarb.whelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ca.svarb.utils.ArgumentChecker;

/**
 * A path is essentially an ArrayList<Cell>.
 * The word can be created by concatenating the values of these cells
 * and next available paths can be evaluated.
 * A path can contain blank cells with certain effects (see
 * method descriptions for specifics).
 */
public class Path {

	private ArrayList<Cell> cells;

	public Path() {
		this.cells=new ArrayList<Cell>();
	}

	public List<Cell> getCells() {
		return cells;
	}

	/**
	 * Add a new cell to the end of the path.
	 * @param cell
	 */
	public void addCell(Cell cell) {
		ArgumentChecker.checkNulls("cell", cell);
		if( cells.contains(cell) ) {
			throw new IllegalArgumentException("Cannot add a cell to path twice");
		}
		cells.add(cell);
	}

	/**
	 * Return a list of paths created by starting with this
	 * path and adding all the neighbours of the last Cell
	 * on the path.  Will not return Paths that cross over
	 * itself.  Will not explore paths where the neighbour
	 * is an empty (blank string) Cell.
	 * @return Next available paths.  Will be empty list if
	 *         no more paths are available, but will not 
	 *         return null.
	 */
	public List<Path> nextPaths() {
		List<Path> nextPaths=new ArrayList<Path>();
		Set<Cell> lastCellNeighbours=cells.get(cells.size()-1).getNeighbours();
		for (Cell neighbour : lastCellNeighbours) {
			if ( !cells.contains(neighbour) &&
					!neighbour.getValue().equals("")) {
				Path path = new Path();
				path.cells=new ArrayList<Cell>(cells);
				path.cells.add(neighbour);
				nextPaths.add(path);
			}
		}
		return nextPaths;
	}

	/**
	 * Return the word formed by concatenating all
	 * values of the Cells in this path.  Blank cells
	 * are Ok, they will have no effect.
	 * e.g. if path contains Cells {"c", "", "a", "t"}
	 * then the word "c"+""+"a"+"t" = "cat" will be
	 * returned.
	 * @return
	 */
	public String getWord() {
		String word="";
		for (Cell cell : cells) {
			word+=cell.getValue();
		}
		return word;
	}
	
	/**
	 * Sets all cells in the path to "selected"
	 */
	public void highlight() {
		for( Cell cell: cells) {
			cell.setSelected(true);
		}
	}
}
