package ca.svarb.whelper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DictionaryTest {

	private String[] words;
	private Dictionary dictionary;

	@Before
	public void setup() {
		words = new String[] {"cat", "dog", "at", "apple", "forest"};
		dictionary = new Dictionary(words);
	}

	@Test
	public void hasWord() {
		assertTrue(dictionary.hasWord("dog"));
		assertFalse(dictionary.hasWord("car"));
		assertFalse(dictionary.hasWord("at")); // Should ignore words less than 3 characters
	}
	
	@Test
	public void hasPrefix() {
		assertTrue(dictionary.hasPrefix("for"));
		assertTrue(dictionary.hasPrefix("fore"));
		assertTrue(dictionary.hasPrefix("fores"));
		assertTrue(dictionary.hasPrefix("c"));
		assertTrue(dictionary.hasPrefix("do"));
		assertFalse(dictionary.hasPrefix("di")); // No words start with di
		assertFalse(dictionary.hasPrefix("at")); // at (the word) is less than 3 chars
		assertFalse(dictionary.hasPrefix("cat"));// No words start with cat, other than cat itself
	}
	
	@Test
	public void getWordCount() {
		assertEquals(4, dictionary.getWordCount()); // 'at' would have been skipped
	}
}
