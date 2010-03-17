package ca.svarb.whelper.gui;

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
import ca.svarb.whelper.IGameBoard;
import ca.svarb.whelper.Path;

public class WordGamePanel extends JLayeredPane implements KeyListener, FocusListener, MouseListener {

	private static final long serialVersionUID = -1667821127391947380L;
	private TextImageLoader imageLoader;
	
	private HashMap<Cell, CellLabel> map=new HashMap<Cell, CellLabel>();

	private Cell currentCell=null;
	private CellLabel currentLabel=null;
	private IGameBoard gameBoard;
	private JPanel arrowPanel;
	private Path selectedPath;

	public WordGamePanel(IGameBoard gameBoard, TextImageLoader imageLoader) {
		this.imageLoader=imageLoader;
		this.gameBoard=gameBoard;
		this.addKeyListener(this);
		this.addFocusListener(this);
		this.addMouseListener(this);
		this.setForeground(Color.ORANGE);
		this.setFocusable(true);
		this.setRequestFocusEnabled(true);

		int width=0;
		int height=0;
    	for (Cell cell : gameBoard) {
			ImageIcon image = imageLoader.getImage(cell.getValue());
			CellLabel currentLabel = new CellLabel(cell, cell.getX(), cell.getY(), image);
			this.add(currentLabel,0);
			width=Math.max(width, currentLabel.getX()+currentLabel.getWidth());
			height=Math.max(height, currentLabel.getY()+currentLabel.getHeight());
			map.put(cell, currentLabel);
    	}
		Dimension preferredSize = new Dimension(width, height);
		this.setPreferredSize(preferredSize);
    	currentCell = gameBoard.iterator().next();
    	
    	arrowPanel = new ArrowPanel(preferredSize);
    	this.add(arrowPanel,1);
    	
    	currentLabel=map.get(currentCell);
    	changeSelectedLabelTo(currentLabel);
	}

	/**
	 * Pressing a key moves selected label around
	 * up, down, left or right, wrapping around the ends.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if ( keyCode==KeyEvent.VK_LEFT ) {
			currentCell=currentCell.getLeftCell();
		} else if ( keyCode==KeyEvent.VK_RIGHT ) {
			currentCell=currentCell.getRightCell();
		} else if ( keyCode==KeyEvent.VK_UP ) {
			currentCell=currentCell.getUpCell();
		} else if ( keyCode==KeyEvent.VK_DOWN ) {
			currentCell=currentCell.getDownCell();
		}
    	changeSelectedLabelTo(map.get(currentCell));
	}

	public void keyReleased(KeyEvent e) {}

	/**
	 * Typing a key changes the selected label to contain
	 * that key and updates the appropriate Grid Cell.
	 */
	public void keyTyped(KeyEvent e) {
		String letter = Character.toString(e.getKeyChar()).toUpperCase();
		if ( letter.equals("Q") ) {
			letter="QU";
		}
		currentCell.setValue(letter);
		currentLabel.setIcon(imageLoader.getImage(letter));
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
		gameBoard.clearSelection();
		this.selectedPath=gameBoard.findWord(selectedValue);
		if ( selectedPath!=null ) {
			selectedPath.highlight();
		}
		this.repaint();
	}

	public void clearSelection() {
		this.selectedPath=null;
		this.repaint();
	}
	
	public class ArrowPanel extends JPanel {
		private static final long serialVersionUID = -1653463257440136491L;
		ArrowPanel(Dimension size) {
			this.setPreferredSize(size);
			this.setBounds(0, 0, size.width, size.height);
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
