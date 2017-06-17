package ca.svarb.whelper.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.svarb.whelper.Dictionary;
import ca.svarb.whelper.DictionaryLoader;
import ca.svarb.whelper.IGameBoard;
import ca.svarb.whelper.WordSearcher;
import ca.svarb.whelper.WhelperException;

@Controller
@RequestMapping("/grids")
public class GridService {

	private Dictionary dictionary=null;
	private ArrayList<Grid> grids = new ArrayList<Grid>();
	
	@Autowired
	private WordSearcher wordSearcher;

	// Log4j2
	Logger logger = LogManager.getRootLogger();

	public GridService() {
		try {
			loadDefaultDictionary();
		} catch (WhelperException e) {
			logger.error("Could not load dictionary: "+e.getMessage());
		}
		logger.info("GridService loaded");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public SortedSet<String> getWords(@RequestBody Grid grid) {
		logger.debug("grid info="+grid);

		// Save grid
		grids.add(grid);
		logger.debug("saved grids: "+grids.size());
		
		IGameBoard board=null;
		if ( grid.getGridType()==Grid.GridType.GRID ) {
			board=new ca.svarb.whelper.Grid(grid.getCells());
		} else {
			board=new ca.svarb.whelper.OffsetGrid(grid.getCells());
		}
		SortedSet<String> words = wordSearcher.findWords(dictionary, board);
		return words;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{gridId}")
	ResponseEntity<Grid> getGrid(@PathVariable int gridId) {
		logger.debug("getGrid id="+gridId+", grid="+grids.get(gridId));
		return new ResponseEntity<Grid>(grids.get(gridId), HttpStatus.OK);
	}
	
	private void loadDefaultDictionary() throws WhelperException {
		String dictionaryName = "TWL06.txt";
		
		try {
			InputStream dictionaryStream = GridService.class.getClassLoader().getResourceAsStream(dictionaryName);
			if ( dictionaryStream==null ) throw new WhelperException("Could not find dictionary file: "+dictionaryName);
			dictionary=DictionaryLoader.getInstance().loadFromReader(new InputStreamReader(dictionaryStream));
		} catch (IOException e) {
			throw new WhelperException("Error reading from dictionary file: "+e.getMessage());
		}
	}
}
