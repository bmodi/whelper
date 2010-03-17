package ca.svarb.whelper.gui;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WordSelectedAction implements ListSelectionListener {

	private WordGamePanel gridPanel;
	private JList wordList;

	public WordSelectedAction(WordGamePanel gridPanel, JList wordList) {
		this.gridPanel=gridPanel;
		this.wordList=wordList;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if ( !e.getValueIsAdjusting() ) {
			Object word = wordList.getSelectedValue();
			System.out.println("highlight new word: "+word);
			if ( word==null ) {
				gridPanel.clearSelection();
			} else {
				gridPanel.highlightWord(word.toString());
			}
		}
	}

}
