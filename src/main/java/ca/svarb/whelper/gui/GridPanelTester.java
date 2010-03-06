package ca.svarb.whelper.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ca.svarb.utils.TextImageLoader;
import ca.svarb.whelper.Grid;

public class GridPanelTester extends JFrame {

	private static final long serialVersionUID = -8683993437311301578L;

	public GridPanelTester() {
		super("Wurdle Grid");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

    	TextImageLoader imageLoader=new TextImageLoader("images");

    	Grid grid=new Grid(4);
    	grid.getCell(0, 0).setValue("A");
    	grid.getCell(0, 1).setValue("B");
    	grid.getCell(0, 2).setValue("C");
    	grid.getCell(0, 3).setValue("C");
    	grid.getCell(1, 0).setValue("D");
    	grid.getCell(1, 1).setValue("E");
    	grid.getCell(1, 2).setValue("F");
    	grid.getCell(1, 3).setValue("C");
    	grid.getCell(2, 0).setValue("G");
    	grid.getCell(2, 1).setValue("J");
    	grid.getCell(2, 2).setValue("I");
    	grid.getCell(2, 3).setValue("C");
    	grid.getCell(3, 0).setValue("G");
    	grid.getCell(3, 1).setValue("J");
    	grid.getCell(3, 2).setValue("I");
    	grid.getCell(3, 3).setValue("C");
    	
		GridPanel gridPanel = new GridPanel(grid, imageLoader);

		this.add(gridPanel);
	}

    private static void createAndShowGUI() throws WhelperException{

		//Create and set up the window.
		GridPanelTester frame = new GridPanelTester();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					createAndShowGUI();
				} catch (WhelperException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Cell Button Tester Problem", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
            }
        });
    }
}
