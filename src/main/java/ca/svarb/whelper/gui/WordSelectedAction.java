package ca.svarb.whelper.gui;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WordSelectedAction implements ListSelectionListener {

	private WordGamePanel gridPanel;
	private JList<String> wordList;

	public WordSelectedAction(WordGamePanel gridPanel, JList<String> wordList) {
		this.gridPanel=gridPanel;
		this.wordList=wordList;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if ( !e.getValueIsAdjusting() ) {
			Object word = wordList.getSelectedValue();
			if ( word==null ) {
				gridPanel.clearSelection();
			} else {
				gridPanel.highlightWord(word.toString());
			}
		}
	}

}
