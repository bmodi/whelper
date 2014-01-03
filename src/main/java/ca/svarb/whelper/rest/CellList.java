package ca.svarb.whelper.rest;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CellList extends ArrayList<Cell> {

	public CellList(int size) {
		super(size);
	}
	
	@XmlElement
	public ArrayList<Cell> getCellList() {
		return this;
	}
}
