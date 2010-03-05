package ca.svarb.whelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ca.svarb.utils.ArgumentChecker;

/**
 * A Cell is:
 * - a holder for a cell value
 *   --> Cell value cannot be null
 *   --> Cell default value is empty string ("")
 * - knows it's neighbours
 * - maintains it's own selection status
 *
 */
public class Cell {

	private String value;
	private Set<Cell> neighbours=new HashSet<Cell>();
	private boolean selected=false;

	/**
	 * Construct a default Cell containing blank string ("")
	 */
	public Cell() {
		this("");
	}

	public Cell(String value) {
		ArgumentChecker.checkNulls("value", value);
		this.value=value;
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

	public String toString() {
		return this.value;
	}
}
