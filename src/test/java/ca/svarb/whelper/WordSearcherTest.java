package ca.svarb.whelper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.SortedSet;

import org.junit.Test;

public class WordSearcherTest {

	public static String[][] gridStrings = { { "c",  "a", "t", "x" },
                                             { "h",  "o", "g", "a" },
                                             { "d",  "o", "g", "m" },
                                             { "qu", "i", "c", "k" } };

	// 2 letter words will be ignored
    public static String[] words={"cat", "dog", "do", "go", "cog", "dogma", "quick", "toga", "dot", "coat", "hat", "fox"};

	
	@Test
	public void singleton() {
		WordSearcher instance1 = WordSearcher.getInstance();
		WordSearcher instance2 = WordSearcher.getInstance();
		assertNotNull(instance1);
		assertSame(instance1,instance2);
	}
	
	@Test
	public void findWords() {
		WordSearcher searcher = WordSearcher.getInstance();
		Dictionary dictionary=new Dictionary(words);
		IGameBoard grid=TextUtils.getInstance().getGridFromString2D(gridStrings);
		SortedSet<String> foundWords = searcher.findWords(dictionary, grid);
		assertThat(foundWords, hasItems("cat","dog","cog","toga","dot","dogma","coat","hat","quick"));
		assertEquals(9, foundWords.size()); // ensure no duplicates
		assertFalse(foundWords.contains("fox"));
		assertFalse(foundWords.contains("hog"));
	}
}