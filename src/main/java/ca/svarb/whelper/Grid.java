package ca.svarb.whelper;

import static ca.svarb.whelper.gui.GuiConsts.ICON_SIZE;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Grid stores a set of Cells in a square arrangement.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 */
@XmlRootElement
public class Grid extends AbstractGameBoard implements Iterable<Cell> {

	private int size;
	private Cell[][] cells=null;
	private List<Cell> cellList=null;

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
		cells=new Cell[size][size];
		cellList=new ArrayList<Cell>(size*size);
		for (int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				Cell currentCell=new Cell(col*Cell.CELL_WIDTH, row*Cell.CELL_WIDTH);
				cells[col][row]=currentCell;
				cellList.add(currentCell);
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

	@XmlElement
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size=size;
		this.initializeCells();
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
