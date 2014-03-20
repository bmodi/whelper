package ca.svarb.whelper;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordSearcher {

	@Autowired
	private TextUtils textUtils;

	public SortedSet<String> findWords(Dictionary dictionary, IGameBoard gameBoard) {
		SortedSet<String> foundWords = new TreeSet<String>();

		List<Path> initialPaths = gameBoard.getInitialPaths();
		for (Path path : initialPaths) {
			foundWords.addAll(textUtils.getWords(path,dictionary));
		}
		
		return foundWords;
	}
}
