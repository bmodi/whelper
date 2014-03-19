package ca.svarb.whelper;


/**
 * An OffsetGrid stores a set of Cells in an set of
 * columns offset from each other by 1/2 Cell width.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
public class OffsetGrid extends AbstractGridGameBoard {

	/**
	 * Make an offset-square grid of blank Cells.
	 * Cells will be initialized with neighbours according
	 * to grid position.
	 * @param size
	 */
	public OffsetGrid(int size) {
		super();
		setSize(size);
	}

	/**
	 * Create an OffsetGrid filled with given strings into the cells
	 * @param gridStrings
	 */
	public OffsetGrid(String[][] gridStrings) {
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
		boolean even = col%2==0;
		boolean odd = !even;
		// Neighbours
		if(col>0) {
			Cell left=this.getCell(col-1, row);
			currentCell.addNeighbour(left);
			if ( odd && row<this.size-1 ) {
				Cell belowLeft=this.getCell(col-1, row+1);
				currentCell.addNeighbour(belowLeft);
			} else if (row>0) {
				Cell aboveLeft=this.getCell(col-1, row-1);
				currentCell.addNeighbour(aboveLeft);
			}
		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.addNeighbour(above);
		}

		// Navigation
		if(col>0) {
			Cell left=this.getCell(col-1, row);
			currentCell.setLeftCell(left);
		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.setUpCell(above);
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
