package ca.svarb.whelper.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import ca.svarb.utils.TextImageLoader;
import ca.svarb.whelper.Dictionary;
import ca.svarb.whelper.DictionaryLoader;
import ca.svarb.whelper.Grid;

public class GridFrame extends JFrame {
	private static final long serialVersionUID = 7520612959620318053L;
	private GridPanel gridPanel;
	private Dictionary dictionary;

	public GridFrame(Grid grid, Dictionary dictionary, String imageLocation) {
		super("Whelper - The Word Game Helper");
		this.dictionary=dictionary;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		gridPanel = new GridPanel(grid, new TextImageLoader(imageLocation));
		this.getContentPane().add(gridPanel, BorderLayout.CENTER);
		
		JList wordsLister = new JList();
		wordsLister.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		wordsLister.setFixedCellWidth(160);
        JScrollPane listPane = new JScrollPane(wordsLister);
		listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(listPane, BorderLayout.EAST);
        wordsLister.addListSelectionListener(new WordSelectedAction(gridPanel, wordsLister));

		JButton generateWordsButton = new JButton();
		generateWordsButton.setAction(new GenerateWordsAction(grid, this.dictionary, wordsLister));
		this.getContentPane().add(generateWordsButton, BorderLayout.SOUTH);
		
		this.setResizable(false);
	}

    private static void createAndShowGUI() throws WurdleException{

    	// First load default dictionary
		Dictionary dictionary=null;
		String dictionaryName = "TWL06.txt";
		
		try {
			InputStream dictionaryStream = GridFrame.class.getClassLoader().getResourceAsStream(dictionaryName);
			if ( dictionaryStream==null ) throw new WurdleException("Could not find dictionary file: "+dictionaryName);
			System.out.println("Loading dictionary...");
			long startTime = Calendar.getInstance().getTimeInMillis();
			dictionary=DictionaryLoader.getInstance().loadFromReader(new InputStreamReader(dictionaryStream));
			long endTime = Calendar.getInstance().getTimeInMillis();
			System.out.println("Load dictionary complete: "+(endTime-startTime)+" ms");
		} catch (IOException e) {
			throw new WurdleException("Error reading from dictionary file: "+e.getMessage());
		}
    	
		Object[] sizes = { "4", "5", "6" };
		String gridSizeStr = (String)JOptionPane.showInputDialog(null,
				  "Initial grid size?",
				  "Welcome to Whelper",
				  JOptionPane.QUESTION_MESSAGE, null, sizes, "5");
		if ( gridSizeStr==null ) {
			System.exit(0); // User cancelled - just exit
		}

		int gridSize=Integer.parseInt(gridSizeStr);

		Grid grid=new Grid(gridSize);
		
		//Create and set up the window.
		GridFrame frame = new GridFrame(grid, dictionary, "images");
		frame.setLocationRelativeTo(null);

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
				} catch (WurdleException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Wurdle Problem", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
            }
        });
    }	
	
}
