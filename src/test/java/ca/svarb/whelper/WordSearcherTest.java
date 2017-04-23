package ca.svarb.whelper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.SortedSet;

import org.junit.Test;

public class WordSearcherTest {

	private WordSearcher searcher=new WordSearcher();

	public static String[][] gridStrings1 = { { "c",  "a", "t", "x", "" },
                                             { "h",  "o", "g", "a", "" },
                                             { "d",  "o", "g", "m", "" },
                                             { "qu", "i", "c", "k", "" },
                                             { "z",   "",  "o", "o", "" }};

	public static String[][] gridStrings2 = { { "a",  "c" },
                                              { "b",  "d" }};

	// 2 letter words will be ignored
    public static String[] words1={"cat", "dog", "do", "go", "cog", "dogma", "quick", "toga", "dot", "coat", "hat", "fox", "zoo"};
    public static String[] words2={"bad", "cab", "cad", "dab", "dad"};

	@Test
	public void findWordsGrid() {
		Dictionary dictionary=new Dictionary(words1);
		IGameBoard grid=new Grid(gridStrings1);
		SortedSet<String> foundWords = searcher.findWords(dictionary, grid);
		assertThat(foundWords, hasItems("cat","dog","cog","toga","dot","dogma","coat","hat","quick"));
//		assertEquals(9, foundWords.size()); // ensure no duplicates
		assertFalse(foundWords.contains("fox"));
		assertFalse(foundWords.contains("hog"));
		assertFalse(foundWords.contains("zoo")); // should not find zoo because of the blank cell between
	}

	@Test
	public void findWordsOffsetGrid() {
		Dictionary dictionary=new Dictionary(words2);
		IGameBoard grid=new Grid(gridStrings2);
		SortedSet<String> foundWords = searcher.findWords(dictionary, grid);
		assertThat(foundWords, hasItems("bad","cab","cad","dab"));
		assertEquals(4, foundWords.size()); // ensure no duplicates

		IGameBoard grid2=new OffsetGrid(gridStrings2);
		SortedSet<String> foundWords2 = searcher.findWords(dictionary, grid2);
		assertThat(foundWords2, hasItems("bad","cab","cad","dab"));
		assertEquals(4, foundWords2.size()); // ensure no duplicates
		fail("Why is this test passing?");
	}
}