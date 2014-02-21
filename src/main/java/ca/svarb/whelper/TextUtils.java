package ca.svarb.whelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TextUtils {

	private static TextUtils instance;

	public static TextUtils getInstance() {
		if ( instance==null ) {
			instance=new TextUtils();
		}
		return instance;
	}

	public OffsetGrid getOffsetGridFromString2D(String[][] gridStrings) {
		return null;
	}

	/**
	 * Get a set of unique words starting with the given
	 * path.
	 * @param startPath
	 * @param dictionary
	 * @return
	 */
	public Collection<String> getWords(Path startPath, Dictionary dictionary) {
		Collection<String> words=new HashSet<String>();
		String word=startPath.getWord();
		boolean hasWord = dictionary.hasWord(word);
		if ( hasWord ) {
			words.add(word);
		}
		if ( hasWord || dictionary.hasPrefix(word) || "".equals(word) ) {
			List<Path> availPaths = startPath.nextPaths();
			for (Path path : availPaths) {
				words.addAll(getWords(path, dictionary));
			}
		}
		return words;
	}
}
