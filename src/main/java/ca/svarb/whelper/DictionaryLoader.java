package ca.svarb.whelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DictionaryLoader {

	private static DictionaryLoader instance;

	public static DictionaryLoader getInstance() {
		if ( instance==null ) {
			instance=new DictionaryLoader();
		}
		return instance;
	}

	public Dictionary loadFromReader(Reader reader) throws IOException {
		BufferedReader buffReader=new BufferedReader(reader);
		List<String> words=new ArrayList<String>();
		String nextWord=null;
		while( (nextWord=buffReader.readLine())!=null ) {
			words.add(nextWord);
		}			
		Dictionary dict=new Dictionary(words.toArray(new String[0]));
		return dict;
	}

}
