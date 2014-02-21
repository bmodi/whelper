package ca.svarb.whelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;


public class TextUtilsTest {

	public static String[][] gridStrings = { { "c", "a", "t" },
                                             { "h", "o", "m" },
                                             { "d", "o", "g" } };
	
	// Note 2 letter words should be ignored - "ca" and "do" will not be found
	public static String[] words={"cat", "dog", "do", "ca", "dot", "cog", "hat", "fox", "dogma"};

	private TextUtils textUtils;

	private Grid grid;
	private OffsetGrid offsetGrid;

	private Cell cell00;
//	private Cell cell01;
	private Cell cell02;
//	private Cell cell10;
	private Cell cell11;
//	private Cell cell12;
//	private Cell cell20;

//	private Cell cell21;
//	private Cell cell22;

	private Path path00;
	private Path path02;

	private Dictionary dictionary;

	@Before
	public void setup() {
		textUtils = TextUtils.getInstance();
		grid = new Grid(gridStrings);
		offsetGrid = textUtils.getOffsetGridFromString2D(gridStrings);
		cell00 = grid.getCell(0, 0);
//		cell01 = grid.getCell(0, 1);
		cell02 = grid.getCell(0, 2);
//		cell10 = grid.getCell(1, 0);
		cell11 = grid.getCell(1, 1);
//		cell12 = grid.getCell(1, 2);
//		cell20 = grid.getCell(2, 0);
//		cell21 = grid.getCell(2, 1);
//		cell22 = grid.getCell(2, 2);

		path00 = new Path();
		path00.addCell(cell00);
		
		path02 = new Path();
		path02.addCell(cell02);
		
		dictionary=new Dictionary(words);
	}

	@Test
	public void singleton() {
		TextUtils textUtils1 = TextUtils.getInstance();
		TextUtils textUtils2 = TextUtils.getInstance();
		assertNotNull(textUtils1);
		assertSame(textUtils1,textUtils2);
	}

	@Test
	public void getWords() {
		Collection<String> foundWords = textUtils.getWords(path00, dictionary);
		assertThat(foundWords, hasItems("cat","cog"));
		assertEquals(2, foundWords.size());
	}

	/**
	 * Check for bug where getWords would stop once it found a word,
	 * e.g. if it found "dog" it would not continue to find "dogma".
	 */
	@Test
	public void getWordsBeyondPrefix() {
		Collection<String> foundWords = textUtils.getWords(path02, dictionary);
		assertThat(foundWords, hasItems("dog","dot","dogma"));
		assertEquals(3, foundWords.size());
	}
	
	/**
	 * Check for bug where path traverses blank cells.
	 * Blanking the centre cell should prevent "cog" from
	 * being found from this path:
	 * C00 + C11 + C12 + C22
	 * "c" + ""  + "o" + "g" = "cog"
	 * Traversal should stop when it hits a blank cell.
	 */
	@Test
	public void blankCell() {
		cell11.setValue("");
		Collection<String> foundWords = textUtils.getWords(path00, dictionary);
		assertEquals(1, foundWords.size());
		assertThat(foundWords, hasItem("cat"));
	}
}
