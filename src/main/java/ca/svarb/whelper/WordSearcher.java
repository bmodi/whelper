package ca.svarb.whelper;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class WordSearcher {

	private static WordSearcher instance;

	private WordSearcher() {}

	public static WordSearcher getInstance() {
		if ( instance==null ) {
			instance=new WordSearcher();
		}
		return instance;
	}

	public SortedSet<String> findWords(Dictionary dictionary, Grid grid) {
		SortedSet<String> foundWords = new TreeSet<String>();

		List<Path> initialPaths = grid.getInitialPaths();
		for (Path path : initialPaths) {
			foundWords.addAll(TextUtils.getInstance().getWords(path,dictionary));
		}
		
		return foundWords;
	}
}
