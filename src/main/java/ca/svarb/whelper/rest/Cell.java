package ca.svarb.whelper.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.svarb.utils.ArgumentChecker;

/**
 * A Cell is:
 * - a holder for a cell value
 *
 */
@XmlRootElement
public class Cell {

	private String value;

	public Cell() {
		this("");
	}
	
	/**
	 * Construct a default Cell containing blank string
	 */
	public Cell(String value) {
		this.value=value;
	}

	@XmlElement
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		ArgumentChecker.checkNulls("value", value);
		this.value=value;
	}
}
