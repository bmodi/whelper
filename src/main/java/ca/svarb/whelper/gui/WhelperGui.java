package ca.svarb.whelper.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import ca.svarb.whelper.GameBoardFactory;
import ca.svarb.whelper.IGameBoard;

public class WhelperGui extends JFrame {
	private static final String BOOKWORM_STR = "Bookworm";
    private static final String SPELLTOWER_STR = "Spell Tower";
	private static final long serialVersionUID = 7520612959620318053L;
	private WordGamePanel gameBoardPanel;
	private Dictionary dictionary;

	public WhelperGui(IGameBoard gameBoard, Dictionary dictionary, String imageLocation) {
		super("Whelper - The Word Game Helper");
		this.dictionary=dictionary;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		gameBoardPanel = new WordGamePanel(gameBoard, new TextImageLoader(imageLocation));
		this.getContentPane().add(gameBoardPanel, BorderLayout.CENTER);
		
		JList<String> wordsLister = new JList<>();
		wordsLister.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		wordsLister.setFixedCellWidth(160);
        JScrollPane listPane = new JScrollPane(wordsLister);
		listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(listPane, BorderLayout.EAST);
        wordsLister.addListSelectionListener(new WordSelectedAction(gameBoardPanel, wordsLister));

		JButton generateWordsButton = new JButton();
		generateWordsButton.setAction(new GenerateWordsAction(gameBoard, this.dictionary, wordsLister));
		this.getContentPane().add(generateWordsButton, BorderLayout.SOUTH);
		
		this.setResizable(false);
	}

    private static void createAndShowGUI() throws WhelperException{

		Dictionary dictionary = loadDefaultDictionary();
    	
		String sizeStr = queryUserForGameboard();

		IGameBoard gameBoard = createGameBoard(sizeStr);

		//Create and set up the window.
		WhelperGui frame = new WhelperGui(gameBoard, dictionary, "images");
		frame.setLocationRelativeTo(null);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	private static String queryUserForGameboard() {
		Object[] sizes = { "4x4 Grid", "5x5 Grid", "6x6 Grid", "7x7 Grid", "8x8 Grid", BOOKWORM_STR, SPELLTOWER_STR };
		
		String sizeStr = (String)JOptionPane.showInputDialog(null,
				  "Initial board size?",
				  "Welcome to Whelper",
				  JOptionPane.QUESTION_MESSAGE, null, sizes, "5x5 Grid");
		if ( sizeStr==null ) {
			System.exit(0); // User cancelled - just exit
		}
		return sizeStr;
	}

	private static IGameBoard createGameBoard(String sizeStr) {
		IGameBoard gameBoard=null;
		if ( sizeStr.equals(BOOKWORM_STR) ) {
			gameBoard=GameBoardFactory.getInstance().getGameBoard(GameBoardFactory.BoardType.OFFSET_GRID, 7);
        } else if ( sizeStr.equals(SPELLTOWER_STR) ) {
            gameBoard=GameBoardFactory.getInstance().getGameBoard(GameBoardFactory.BoardType.GRID, 8, 11);
		} else {
			int size=Integer.parseInt(sizeStr.substring(0, 1));
			gameBoard=GameBoardFactory.getInstance().getGameBoard(GameBoardFactory.BoardType.GRID, size);
		}
		if ( gameBoard==null ) {
			JOptionPane.showMessageDialog(null, "Sorry that game board is not currently supported");
			System.exit(0);
		}
		return gameBoard;
	}

	private static Dictionary loadDefaultDictionary() throws WhelperException {
		Dictionary dictionary=null;
		String dictionaryName = "TWL06.txt";
		
		try {
			InputStream dictionaryStream = WhelperGui.class.getClassLoader().getResourceAsStream(dictionaryName);
			if ( dictionaryStream==null ) throw new WhelperException("Could not find dictionary file: "+dictionaryName);
			dictionary=DictionaryLoader.getInstance().loadFromReader(new InputStreamReader(dictionaryStream));
		} catch (IOException e) {
			throw new WhelperException("Error reading from dictionary file: "+e.getMessage());
		}
		return dictionary;
	}

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					createAndShowGUI();
				} catch (WhelperException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Wurdle Problem", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
            }
        });
    }	
	
}
