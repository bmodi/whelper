package ca.svarb.whelper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import ca.svarb.utils.ArgumentChecker;

public class TextUtils {

	private static TextUtils instance;

	public static TextUtils getInstance() {
		if ( instance==null ) {
			instance=new TextUtils();
		}
		return instance;
	}

	/**
	 * Take a 2D array of Strings and convert it into a square
	 * grid object with all the proper neighbour connections.
	 * @param gridStrings
	 * @return
	 */
	public IGameBoard getGridFromString2D(String[][] gridStrings) {
		ArgumentChecker.checkNulls("gridStrings", gridStrings);
		int colCount=gridStrings.length;
		OffsetGrid grid=new OffsetGrid(colCount);
		for(int col=0; col<colCount; col++) {
			String[] rowStrings=gridStrings[col];
			int rowCount=rowStrings.length;
			if( rowCount!=colCount ) {
				throw new IllegalArgumentException("TextUtils.getGridFromString2D: gridStrings argument must be square");
			}
			for(int row=0; row<rowCount; row++) {
				grid.getCell(col, row).setValue(gridStrings[row][col]);
			}
		}
		return grid;
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
