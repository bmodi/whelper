package ca.svarb.whelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import ca.svarb.utils.ArgumentChecker;

/**
 * Dictionary for use in word games.
 * Does not accept any words under 3 characters in length.
 */
public class Dictionary {

	private HashSet<String> words;
	private HashSet<String> prefixes;

	/**
	 * Create a dictionary from the given words.
	 * Words with less than 3 characters will be ignored.
	 * @param words
	 */
	public Dictionary(String[] words) {
		ArgumentChecker.checkNulls("words", words);

		// Add all words from the array to the words set
		this.words=new HashSet<String>(words.length);
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			if ( word.length()>2 ) this.words.add(word);
		}
		
		// Add all prefixes from each word to the prefixes
		// set, skipping prefixes that are already words
		this.prefixes=new HashSet<String>(words.length);
		for (int i = 0; i < words.length; i++) {
			Collection<String> prefixes=getPrefixes(words[i]);
			for (String prefix : prefixes) {
				if( !hasWord(prefix) ) {
					this.prefixes.add(prefix);
				}
			}
		}
	}

	private Collection<String> getPrefixes(String word) {
		Collection<String> prefixes=new ArrayList<String>(word.length());
		for(int i=1; i<word.length(); i++) {
			String prefix=word.substring(0,i);
			prefixes.add(prefix);
		}
		return prefixes;
	}

	public boolean hasWord(String word) {
		ArgumentChecker.checkNulls("word", word);
		return words.contains(word);
	}

	public boolean hasPrefix(String prefix) {
		return prefixes.contains(prefix);
	}
	
	public int getWordCount() {
		return words.size();
	}
}
