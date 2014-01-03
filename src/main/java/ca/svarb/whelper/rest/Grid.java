package ca.svarb.whelper.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Grid stores a set of Cells in a square arrangement.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
@XmlRootElement
public class Grid {

	private int size;
	private CellList cellList=null;

	/**
	 * Makes a grid with default size of 5
	 */
	public Grid() {
		this(5);
	}
	
	/**
	 * Make a square grid of blank Cells.
	 * Cells will be initialized with neighbours according
	 * to grid position.
	 * @param size
	 */
	public Grid(int size) {
		if (size<1) throw new IllegalArgumentException("Grid.size must be positive - size="+size);
		this.size=size;
		initializeCells();
	}

	private void initializeCells() {
		cellList=new CellList(size*size);
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				Cell currentCell=new Cell("a");
				cellList.add(currentCell);
			}
		}
	}

	@XmlElement
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size=size;
		this.initializeCells();
	}

	@XmlElement
	public CellList getCells() {
		return cellList;
	}
}
