package ca.svarb.whelper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.SortedSet;

import org.junit.Test;

public class WordSearcherTest {

	private WordSearcher searcher=new WordSearcher();

	public static String[][] gridStrings = { { "c",  "a", "t", "x", "" },
                                             { "h",  "o", "g", "a", "" },
                                             { "d",  "o", "g", "m", "" },
                                             { "qu", "i", "c", "k", "" },
                                             { "z",   "",  "o", "o", "" }};

	// 2 letter words will be ignored
    public static String[] words={"cat", "dog", "do", "go", "cog", "dogma", "quick", "toga", "dot", "coat", "hat", "fox", "zoo"};

	@Test
	public void findWords() {
		Dictionary dictionary=new Dictionary(words);
		IGameBoard grid=new Grid(gridStrings);
		SortedSet<String> foundWords = searcher.findWords(dictionary, grid);
		assertThat(foundWords, hasItems("cat","dog","cog","toga","dot","dogma","coat","hat","quick"));
//		assertEquals(9, foundWords.size()); // ensure no duplicates
		assertFalse(foundWords.contains("fox"));
		assertFalse(foundWords.contains("hog"));
		assertFalse(foundWords.contains("zoo")); // should not find zoo because of the blank cell between
	}
}