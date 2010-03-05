package ca.svarb.whelper.gui;

import static ca.svarb.whelper.gui.WurldeGuiConsts.ICON_SIZE;
import static ca.svarb.whelper.gui.WurldeGuiConsts.SELECTED_COLOR;
import static ca.svarb.whelper.gui.WurldeGuiConsts.UNSELECTED_COLOR;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import ca.svarb.whelper.Cell;

/**
 * A label designed to manage Wurdle Cells.
 * Behaves like a normal JLabel but it adds
 * position and stores the Cell it references.
 *
 */
public class CellLabel extends JLabel {

	private static final long serialVersionUID = -4194118729029784255L;
	private LineBorder unselectedBorder=new LineBorder(UNSELECTED_COLOR,2,false);
	private LineBorder selectedBorder = new LineBorder(SELECTED_COLOR,2,false);
	
	Dimension preferredSize = new Dimension(ICON_SIZE, ICON_SIZE);
	private Cell cell;

	public CellLabel(Cell cell, int x, int y, ImageIcon image) {
		super(image);
		this.cell=cell;
		this.setBounds(x, y, ICON_SIZE, ICON_SIZE);
		this.setPreferredSize(preferredSize);
		this.setMinimumSize(preferredSize);
		this.setMaximumSize(preferredSize);
		this.setBorder(unselectedBorder);
	}
	
	public void setSelected(boolean selected) {
		if ( selected ) {
			this.setBorder(selectedBorder);
		} else {
			this.setBorder(unselectedBorder);
		}
	}

	public Cell getCell() {
		return this.cell;
	}
}
