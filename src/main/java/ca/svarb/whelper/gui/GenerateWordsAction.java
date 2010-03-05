package ca.svarb.whelper.gui;

import java.awt.event.ActionEvent;
import java.util.SortedSet;

import javax.swing.AbstractAction;
import javax.swing.JList;

import ca.svarb.whelper.Dictionary;
import ca.svarb.whelper.Grid;
import ca.svarb.whelper.WordSearcher;

public class GenerateWordsAction extends AbstractAction {

	private static final long serialVersionUID = -7568568024717221223L;
	private Grid grid;
	private Dictionary dictionary;
	private JList wordsLister;

	public GenerateWordsAction(Grid grid, Dictionary dictionary, JList wordsLister) {
		super("Generate Word List");
		this.dictionary=dictionary;
		this.grid=grid;
		this.wordsLister=wordsLister;
	}

	public void actionPerformed(ActionEvent e) {
		SortedSet<String> words = WordSearcher.getInstance().findWords(this.dictionary, this.grid);
		this.wordsLister.setListData(words.toArray());
	}

}
