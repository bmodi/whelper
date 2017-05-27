package ca.svarb.whelper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.SortedSet;

import org.junit.Test;
import org.junit.Before;

public class WordSearcherTest {

	private WordSearcher searcher=new WordSearcher();
	private Dictionary dictionary;

	private static String[][] gridStrings1 = { { "c",  "a", "t", "x", "" },
                                               { "h",  "o", "g", "a", "" },
                                               { "d",  "o", "g", "m", "" },
                                               { "qu", "i", "c", "k", "" },
                                               { "z",   "",  "o", "o", "" }};

	//
	//  +---+   +---+
	//  | T |---| R |
	//  |---| A |---|
	//  | B |---| P |
	//  |---| E |---|
	//  |   |---|   |
	//  +---|   |---+
	//      +---+
	private static String[][] gridStrings2 = { { "t",  "a", "r" },
			                                   { "b",  "e", "p" },
											   { "",  "", "" } };

	// 2 letter words will be ignored
    public static String[] words={"cat", "dog", "do", "go", "cog", "dogma", "quick", "toga",
			"dot", "coat", "hat", "fox", "zoo", "ape", "tap", "tea", "pet", "bar", "pear", "eat"};

    @Before
	public void setup() {
		dictionary = new Dictionary(words);
	}

	@Test
	public void findWordsGrid() {
		IGameBoard grid=new Grid(gridStrings1);
		SortedSet<String> foundWords = searcher.findWords(dictionary, grid);
		assertThat(foundWords, hasItems("cat","dog","cog","toga","dot","dogma","coat","hat","quick"));
		assertEquals(9, foundWords.size()); // ensure no duplicates
		assertFalse(foundWords.contains("fox"));
		assertFalse(foundWords.contains("hog"));
		assertFalse(foundWords.contains("zoo")); // should not find zoo because of the blank cell between
	}

	@Test
	public void findWordsOffsetGrid() {
		IGameBoard grid2=new OffsetGrid(gridStrings2);
		SortedSet<String> foundWords2 = searcher.findWords(dictionary, grid2);

		assertThat(foundWords2, hasItems("ape", "tap", "bar", "pear", "eat"));
		assertEquals(5, foundWords2.size()); // ensure no duplicates
	}
}