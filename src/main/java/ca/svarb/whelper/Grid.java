package ca.svarb.whelper;

import static ca.svarb.whelper.gui.GuiConsts.ICON_SIZE;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Grid stores a set of Cells in a square arrangement.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
public class Grid extends AbstractGameBoard {

	private int size;
	private Cell[][] cells=null;

	/**
	 * Make a square grid of blank Cells.
	 * Cells will be initialized with neighbours according
	 * to grid position.
	 * @param size
	 */
	public Grid(int size) {
		if (size<1) throw new IllegalArgumentException("Grid.size must be positive - size="+size);
		this.size=size;
		cells=new Cell[size][size];
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				Cell currentCell=new Cell(col*Cell.CELL_WIDTH, row*Cell.CELL_WIDTH);
				cells[col][row]=currentCell;
				initCell(col, row);
			}
		}
	}

	/**
	 * Set Cell neighbours and left/right/up/down navigation cells
	 * @param col
	 * @param row
	 */
	private void initCell(int col, int row) {
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
	
	public Dimension getPreferredSize() {
		return new Dimension(ICON_SIZE*size, ICON_SIZE*size);
	}

}
