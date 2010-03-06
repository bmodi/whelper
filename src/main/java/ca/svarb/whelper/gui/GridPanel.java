package ca.svarb.whelper.gui;

import static ca.svarb.whelper.gui.GuiConsts.ICON_SIZE;
import static ca.svarb.whelper.gui.GuiConsts.START_DOT_SIZE;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ca.svarb.utils.TextImageLoader;
import ca.svarb.whelper.Cell;
import ca.svarb.whelper.Grid;
import ca.svarb.whelper.Path;

public class GridPanel extends JLayeredPane implements KeyListener, FocusListener, MouseListener {

	private static final long serialVersionUID = -1667821127391947380L;
	private TextImageLoader imageLoader;
	
	private CellLabel[][] label=null;
	private HashMap<Cell, CellLabel> map=new HashMap<Cell, CellLabel>();
	
	private CellLabel currentLabel=null;
	private int currentRow;
	private int currentCol;
	private int size;
	private Grid grid;
	private JPanel arrowPanel;
	private Path selectedPath;

	public GridPanel(Grid grid, TextImageLoader imageLoader) {
		this.imageLoader=imageLoader;
		this.grid=grid;
		this.addKeyListener(this);
		this.addFocusListener(this);
		this.addMouseListener(this);
		this.setForeground(Color.ORANGE);
		this.setFocusable(true);
		this.setRequestFocusEnabled(true);

    	this.size = grid.getSize();
		this.setPreferredSize(new Dimension(ICON_SIZE*size, ICON_SIZE*size));
    	this.label=new CellLabel[size][size];
    	for( int i=0; i<size; i++ ) {
    		for( int j=0; j<size; j++ ) {
				Cell cell = grid.getCell(i,j);
				ImageIcon image = imageLoader.getImage(cell.getValue());
    			CellLabel currentLabel = new CellLabel(cell, i*ICON_SIZE, j*ICON_SIZE, image);
    			this.add(currentLabel,0);
    			label[i][j] = currentLabel;
    			map.put(cell, currentLabel);
    		}
    	}
    	
    	arrowPanel = new ArrowPanel(size);
    	this.add(arrowPanel,1);
    	
    	this.currentRow=0;
    	this.currentCol=0;
    	currentLabel=label[0][0];
    	changeSelectedLabelTo(currentLabel);
	}

	/**
	 * Pressing a key moves selected label around
	 * up, down, left or right, wrapping around the ends.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if ( keyCode==KeyEvent.VK_LEFT ) {
			if ( --currentCol < 0 ) currentCol=this.size-1;
		} else if ( keyCode==KeyEvent.VK_RIGHT ) {
			if ( ++currentCol == size ) currentCol=0;
		} else if ( keyCode==KeyEvent.VK_UP ) {
			if ( --currentRow < 0 ) currentRow=this.size-1;
		} else if ( keyCode==KeyEvent.VK_DOWN ) {
			if ( ++currentRow == size ) currentRow=0;
		}
		changeSelectedLabelTo(label[currentCol][currentRow]);
	}

	public void keyReleased(KeyEvent e) {}

	/**
	 * Typing a key changes the selected label to contain
	 * that key and updates the appropriate Grid Cell.
	 */
	public void keyTyped(KeyEvent e) {
		String letter = Character.toString(e.getKeyChar()).toUpperCase();
		this.grid.getCell(currentCol, currentRow).setValue(letter);
		label[currentCol][currentRow].setIcon(imageLoader.getImage(letter));
	}

	public void focusGained(FocusEvent e) {
		currentLabel.setSelected(true);
		this.selectedPath=null;
		this.repaint();
	}

	public void focusLost(FocusEvent e) {
		currentLabel.setSelected(false);
	}

	public void mouseClicked(MouseEvent e) {
		this.requestFocusInWindow();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	private void changeSelectedLabelTo(CellLabel label) {
		currentLabel.setSelected(false);
		currentLabel=label;
		currentLabel.setSelected(true);
	}


	public void highlightWord(String selectedValue) {
		grid.clearSelection();
		this.selectedPath=grid.findWord(selectedValue);
		if ( selectedPath!=null ) {
			selectedPath.highlight();
		}
		this.repaint();
	}

	public void clearSelection() {
//		this.grid.clearSelection();
		this.selectedPath=null;
		this.repaint();
	}
	
	public class ArrowPanel extends JPanel {
		private static final long serialVersionUID = -1653463257440136491L;
		ArrowPanel(int size) {
			this.setPreferredSize(new Dimension(ICON_SIZE*size,ICON_SIZE*size));
			this.setBounds(0, 0, ICON_SIZE*size, ICON_SIZE*size);
			this.setOpaque(false);
		}
		
	    public void paint(Graphics g) {
	        super.paint(g);

            if ( selectedPath!=null ) {
    	        // Draw line through centres of
    	        // selected cells.
                Point c2=null;
    	        g.setColor(Color.RED);
	            for(Cell cell : selectedPath.getCells() ) {
	            	CellLabel cellLabel = map.get(cell);
	            	Rectangle bounds = cellLabel.getBounds();
	            	Point c1=new Point(bounds.x+bounds.width/2, bounds.y+bounds.height/2);
	            	if ( c2==null ) {
	            		g.fillOval(c1.x-START_DOT_SIZE/2, c1.y-START_DOT_SIZE/2, START_DOT_SIZE, START_DOT_SIZE);
	            	} else {
	            		g.drawLine(c1.x, c1.y, c2.x, c2.y);
	            	}
	            	c2=c1;
	            }

	            // Highlight selected cells
		        Graphics2D g2d = (Graphics2D)g;
		        g2d.setColor(Color.YELLOW);
	            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
	
		            for(Cell cell : selectedPath.getCells() ) {
		            	CellLabel cellLabel = map.get(cell);
		            	Rectangle bounds = cellLabel.getBounds();
		            	g2d.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		            }
	            }
	    }
	}
}
