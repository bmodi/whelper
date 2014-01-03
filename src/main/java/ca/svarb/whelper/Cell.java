package ca.svarb.whelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.svarb.utils.ArgumentChecker;

/**
 * A Cell is:
 * - a holder for a cell value
 *   --> Cell value cannot be null
 *   --> Cell default value is empty string ("")
 * - has a (x,y) position
 * - knows it's neighbours
 * - can return which cell to navigate to on
 *   each of "left, right, up, down" directions
 *
 */
@XmlRootElement
public class Cell {

	public static final int CELL_WIDTH = 40;

	private String value;
	private Set<Cell> neighbours=new HashSet<Cell>();
	private boolean selected=false;
	private Cell leftCell=null;
	private Cell rightCell=null;
	private Cell upCell=null;
	private Cell downCell=null;
	private int x;
	private int y;

	public Cell() {
		this("");
	}
	
	/**
	 * Construct a default Cell containing blank string ("") at (0,0)
	 */
	public Cell(String value) {
		this(value, 0, 0);
	}
	
	/**
	 * Construct a Cell containing blank string ("")
	 */
	public Cell(int x, int y) {
		this("", x, y);
	}

	public Cell(String value, int x, int y) {
		ArgumentChecker.checkNulls("value", value);
		this.value=value;
		this.x=x;
		this.y=y;
	}

	public void addNeighbour(Cell newNeighbour) {
		ArgumentChecker.checkNulls("newNeighbour", newNeighbour);
		if ( newNeighbour==this ) throw new IllegalArgumentException("Cannot add cell to itself as a neighbour");
		this.neighbours.add(newNeighbour);
		newNeighbour.neighbours.add(this);
	}

	public Set<Cell> getNeighbours() {
		return Collections.unmodifiableSet(neighbours);
	}

	@XmlElement
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		ArgumentChecker.checkNulls("value", value);
		this.value=value;
	}

	public boolean isSelected() {
		return this.selected;
	}
	public void setSelected(boolean value) {
		this.selected=value;
	}


	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return this.value;
	}

	public void setLeftCell(Cell cell) {
		this.leftCell=cell;
		cell.rightCell=this;
	}

	public void setRightCell(Cell cell) {
		this.rightCell=cell;
		cell.leftCell=this;
	}

	public void setUpCell(Cell cell) {
		this.upCell=cell;
		cell.downCell=this;
	}

	public void setDownCell(Cell cell) {
		this.downCell=cell;
		cell.upCell=this;
	}

	/**
	 * Get the Cell to the "left" of this Cell
	 * @return Cell on the "left"
	 */
	public Cell getLeftCell() {
		return this.leftCell;
	}

	/**
	 * Get the Cell to the "right" of this Cell
	 * @return Cell on the "right"
	 */
	public Cell getRightCell() {
		return this.rightCell;
	}

	/**
	 * Get the Cell just "up" from this Cell
	 * @return Cell just "up" from this cell
	 */
	public Cell getUpCell() {
		return this.upCell;
	}

	/**
	 * Get the Cell just "down" from this Cell
	 * @return Cell just "down" from this cell
	 */
	public Cell getDownCell() {
		return this.downCell;
	}
}
