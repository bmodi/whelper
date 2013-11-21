package ca.svarb.whelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class WordSearcherApp {

	public static void main(String args[]) {
		if ( args.length!=2 ) {
			System.out.println("Specify grid and dictionary files.");
			return;
		}
		
		String pathname = args[0];
		String[][] cells = null;
		try {
			cells = getCellsFromFile(pathname);
		} catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
			return;
		}
		AbstractGameBoard grid=TextUtils.getInstance().getGridFromString2D(cells);
		
		Dictionary dictionary=null;
		File dictFile=new File(args[1]);
		try {
			dictionary=DictionaryLoader.getInstance().loadFromReader(new FileReader(dictFile));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find dictionary file: "+dictFile.getAbsolutePath());
			return;
		} catch (IOException e) {
			System.out.println("Error reading from dictionary file: "+e.getMessage());
			return;
		}
		System.out.println("Loaded dictionary: "+dictionary.getWordCount()+" words");
		SortedSet<String> words = WordSearcher.getInstance().findWords(dictionary, grid);
		System.out.println("Found "+words.size()+" words in grid:");
		for (String word : words) {
			System.out.println(word);
		}
	}

	public static String[][] getCellsFromFile(String pathname) throws Exception {
		File gridFile=new File(pathname);
		List<String[]> rows=new ArrayList<String[]>();
		try {
			BufferedReader fileReader=new BufferedReader(new FileReader(gridFile));
			String rowStr;
			while( (rowStr=fileReader.readLine())!=null ) {
				String[] row = rowStr.split(",");
				rows.add(row);
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new Exception("Could not find grid file: "+gridFile.getAbsolutePath());
		} catch (IOException e) {
			throw new Exception("Error reading from grid file: "+e.getMessage());
		}
		String[][] cells=rows.toArray(new String[0][0]);
		return cells;
	}
}
