package ca.svarb.whelper;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Grid stores a set of Cells in an set of columns
 * offset from each other by 1/2 Cell width.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
public class OffsetGrid extends AbstractGameBoard {

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
		for (int col = 0; col < size-1; col++) {
			boolean even = col%2==0;
			for (int row = 0; row < (even ? size-1 : size); row++) {
				int offset = even ? Cell.CELL_WIDTH/2 : 0;
				Cell currentCell=new Cell(col*Cell.CELL_WIDTH, row*Cell.CELL_WIDTH+offset);
				cells[col][row]=currentCell;
				initializeNeighbours(col, row);
			}
		}
	}

	private void initializeNeighbours(int col, int row) {
		Cell currentCell=this.getCell(col, row);
		boolean even = col%2==0;
		boolean odd = !even;
		boolean lastRow = even ? (row>=size-2) : (row>=size-1);
		System.out.println("col="+col+", row="+row+", even="+even+", lastRow="+lastRow);
		// Neighbours
		if(col>0) {
			if ( !(odd && lastRow) ) {
				Cell left=this.getCell(col-1, row);
				currentCell.addNeighbour(left);
			}
			if ( even ) {
				Cell belowLeft=this.getCell(col-1, row+1);
				currentCell.addNeighbour(belowLeft);
			} else if (row>0) {
				Cell aboveLeft=this.getCell(col-1, row-1);
				currentCell.addNeighbour(aboveLeft);
			}
		} //else if (col>0 && odd) {
//			if ( row>0 ) {
//				Cell aboveLeft=this.getCell(col-1, row-1);
//				currentCell.addNeighbour(aboveLeft);
//			}
//			if ( !lastRow ) {
//				Cell left=this.getCell(col-1, row);
//				currentCell.addNeighbour(left);
//			} else {
//				currentCell.setLeftCell(currentCell);
//			}
//		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.addNeighbour(above);
		}

		// Navigation
		if(col>0) {
			if ( even || !lastRow ) {
				Cell left=this.getCell(col-1, row);
				currentCell.setLeftCell(left);
			} else {
				currentCell.setLeftCell(currentCell);
			}
		}
		if (col==this.size-2) {
			Cell leftEdgeCell=this.getCell(0, row);
			currentCell.setRightCell(leftEdgeCell);
		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.setUpCell(above);
		}
		if(lastRow) {
			Cell firstCell=this.getCell(col, 0);
			currentCell.setDownCell(firstCell);
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
				Cell cell=cells[col][row++];
				boolean even = col%2==0;
				boolean lastRow = even ? (row>=size-1) : (row>=size);
				if(lastRow) {
					row=0;
					col++;
					if(col==size-1) hasNext=false;
				}
				return cell;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
}
