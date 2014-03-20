package ca.svarb.whelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

@Component
public class WordSearcher {

	public SortedSet<String> findWords(Dictionary dictionary, IGameBoard gameBoard) {
		SortedSet<String> foundWords = new TreeSet<String>();

		List<Path> initialPaths = gameBoard.getInitialPaths();
		for (Path path : initialPaths) {
			foundWords.addAll(getWords(path,dictionary));
		}
		
		return foundWords;
	}
	
	private Collection<String> getWords(Path startPath, Dictionary dictionary) {
		Collection<String> words=new HashSet<String>();
		String word=startPath.getWord();
		boolean hasWord = dictionary.hasWord(word);
		if ( hasWord ) {
			words.add(word);
		}

		// Got rid of the last term based on coverage results.
		// Not sure what case it was trying to handle.
		// if ( hasWord || dictionary.hasPrefix(word) || "".equals(word) ) {
		if ( hasWord || dictionary.hasPrefix(word) ) {
			List<Path> availPaths = startPath.nextPaths();
			for (Path path : availPaths) {
				words.addAll(getWords(path, dictionary));
			}
		}
		return words;
	}
}
