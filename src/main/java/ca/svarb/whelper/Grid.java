package ca.svarb.whelper;


/**
 * A Grid stores a set of Cells in a square arrangement.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
public class Grid extends AbstractGridGameBoard {

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
		super();
		setSize(size);
	}

	/**
	 * Create a Grid filled with given strings into the cells
	 * @param gridStrings
	 */
	public Grid(String[][] gridStrings) {
		super(gridStrings);
	}

	/**
	 * Set Cell neighbours and left/right/up/down navigation cells
	 * @param col
	 * @param row
	 */
	@Override
	protected void initCell(int col, int row) {
		Cell currentCell=this.getCell(col, row);
		
		if(col>0) {
			Cell left=this.getCell(col-1, row);
			currentCell.addNeighbour(left);
			currentCell.setLeftCell(left);
			if(row<this.size-1) {
				Cell belowLeft=this.getCell(col-1, row+1);
				currentCell.addNeighbour(belowLeft);
			}
		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.addNeighbour(above);
			currentCell.setUpCell(above);
			if(col>0) {
				Cell aboveLeft=this.getCell(col-1, row-1);
				currentCell.addNeighbour(aboveLeft);
			}
		}

		if (row==this.size-1) {
			Cell topCell=this.getCell(col, 0);
			currentCell.setDownCell(topCell);
		}
		if (col==this.size-1) {
			Cell leftEdgeCell=this.getCell(0, row);
			currentCell.setRightCell(leftEdgeCell);
		}

	}
}
