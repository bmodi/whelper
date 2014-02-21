package ca.svarb.whelper;

import static ca.svarb.whelper.gui.GuiConsts.ICON_SIZE;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * An OffsetGrid stores a set of Cells in an set of
 * columns offset from each other by 1/2 Cell width.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
public class OffsetGrid extends AbstractGameBoard {

	private int size;
	private Cell[][] cells=null;
	private List<Cell> cellList=null;

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
		cellList=new ArrayList<Cell>(size*size);
		for (int col = 0; col < size; col++) {
			boolean even = col%2==0;
			boolean odd = !even;
			for (int row = 0; row < size; row++) {
				int offset = odd ? Cell.CELL_WIDTH/2 : 0;
				Cell currentCell=new Cell(col*Cell.CELL_WIDTH, row*Cell.CELL_WIDTH+offset);
				cells[col][row]=currentCell;
				cellList.add(currentCell);
				initializeNeighbours(col, row);
			}
		}
	}

	private void initializeNeighbours(int col, int row) {
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

	public Cell getCell(int col, int row) {
		if ( col<0 || col>=size ) throw new IllegalArgumentException("Cell.getCell col out of range: size="+size+", col="+col);
		if ( row<0 || row>=size ) throw new IllegalArgumentException("Cell.getCell row out of range: size="+size+", row="+row);
		return cells[col][row];
	}

	public int getSize() {
		return size;
	}

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
