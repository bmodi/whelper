package ca.svarb.whelper;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;


public class DictionaryLoaderTest {

	private DictionaryLoader loader;
	private final static String SEPARATOR=System.getProperty("line.separator");

	@Before
	public void setup() {
		loader = DictionaryLoader.getInstance();
	}
	
	public void singleton() {
		DictionaryLoader instance1 = DictionaryLoader.getInstance();
		DictionaryLoader instance2 = DictionaryLoader.getInstance();
		assertNotNull(instance1);
		assertSame(instance1,instance2);
	}

	@Test
	public void loadFromReader() throws IOException {
		String inputString="cat"+SEPARATOR+"dog"+SEPARATOR+"house"+SEPARATOR+"car";
		Reader stringReader=new StringReader(inputString);
		Dictionary dictionary = loader.loadFromReader(stringReader);
		assertTrue(dictionary.hasWord("cat"));
		assertTrue(dictionary.hasWord("dog"));
		assertTrue(dictionary.hasWord("house"));
		assertTrue(dictionary.hasWord("car"));
	}

	@Test
	public void loadFromEmptyReader() throws IOException {
		String inputString="";
		Reader stringReader=new StringReader(inputString);
		Dictionary dictionary = loader.loadFromReader(stringReader);
		assertFalse(dictionary.hasWord("cat"));
	}

	@Test
	public void loadFromFile() throws IOException {
		InputStream stream = DictionaryLoaderTest.class.getClassLoader().getResourceAsStream("words.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader buffReader = new BufferedReader(reader);
		Dictionary dictionary = loader.loadFromReader(buffReader);
		assertTrue(dictionary.hasWord("cat"));
		assertTrue(dictionary.hasWord("bat"));
		assertTrue(dictionary.hasWord("hat"));
		assertTrue(dictionary.hasWord("hello"));
		assertTrue(dictionary.hasWord("world"));
	}

	@Test
	public void loadFromLargeFile() throws IOException {
		InputStream stream = DictionaryLoaderTest.class.getClassLoader().getResourceAsStream("TWL06.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader buffReader = new BufferedReader(reader);
		Dictionary dictionary = loader.loadFromReader(buffReader);
		assertEquals(178590, dictionary.getWordCount());
		assertTrue(dictionary.hasWord("CIRCUMNAVIGATED"));
	}

}
