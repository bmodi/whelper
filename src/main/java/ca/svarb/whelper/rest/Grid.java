package ca.svarb.whelper.rest;

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Grid {
	
	public enum GridType { GRID, OFFSET_GRID };

	private String cells[][]=null;
	private GridType gridType;

	public Grid() {
	}
	
	public Grid(String cells[][], GridType gridType) {
		this.cells=cells;
		this.gridType=gridType;
	}
	
	public String[][] getCells() {
		return this.cells;
	}

	public GridType getGridType() {
		return gridType;
	}

	@Override
	public String toString() {
		return this.gridType+": "+Arrays.deepToString(cells);
	}
}
