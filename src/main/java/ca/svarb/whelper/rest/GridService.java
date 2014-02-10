package ca.svarb.whelper.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedSet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.svarb.whelper.Dictionary;
import ca.svarb.whelper.DictionaryLoader;
import ca.svarb.whelper.IGameBoard;
import ca.svarb.whelper.TextUtils;
import ca.svarb.whelper.WordSearcher;
import ca.svarb.whelper.gui.WhelperException;
import ca.svarb.whelper.gui.WhelperGui;

@Controller
@RequestMapping("/grid")
public class GridService {

	private Dictionary dictionary=null;

	public GridService() {
		try {
			loadDefaultDictionary();
		} catch (WhelperException e) {
			System.out.println("Could not load dictionary: "+e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SortedSet<String> getWords(@RequestBody String[][] cells) {
		IGameBoard grid=TextUtils.getInstance().getGridFromString2D(cells);
		SortedSet<String> words = WordSearcher.getInstance().findWords(dictionary, grid);
		return words;
	}
	
	private void loadDefaultDictionary() throws WhelperException {
		String dictionaryName = "TWL06.txt";
		
		try {
			InputStream dictionaryStream = WhelperGui.class.getClassLoader().getResourceAsStream(dictionaryName);
			if ( dictionaryStream==null ) throw new WhelperException("Could not find dictionary file: "+dictionaryName);
			dictionary=DictionaryLoader.getInstance().loadFromReader(new InputStreamReader(dictionaryStream));
		} catch (IOException e) {
			throw new WhelperException("Error reading from dictionary file: "+e.getMessage());
		}
	}
}
