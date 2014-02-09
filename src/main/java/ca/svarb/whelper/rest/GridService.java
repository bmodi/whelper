package ca.svarb.whelper.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.svarb.whelper.rest.Cell;
import ca.svarb.whelper.rest.Grid;

@Controller
@RequestMapping("/grid")
public class GridService {

	private final AtomicLong GRID_ID_SEQ = new AtomicLong();
	private final ConcurrentMap<Long, Grid> gridMap = new ConcurrentHashMap<Long, Grid>();

	@RequestMapping(value = "/{gridId}", method = RequestMethod.GET)
	@ResponseBody
	public Grid getGrid(@PathVariable(value = "gridId") long id) {
		System.out.println("got request 2");
		return gridMap.get(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public long createGrid(@RequestBody Grid grid) {
		long id = GRID_ID_SEQ.incrementAndGet();
		System.out.println("new grid "+id);
		gridMap.put(id, grid);
		return id;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<String> getWords() {
		List<String> words=new ArrayList<>();
		words.add("abc");
		words.add("def");
		return words;
	}

	@RequestMapping(value = "/{gridId}/cells", method = RequestMethod.GET)
	@ResponseBody
	public List<Cell> getCells(@PathVariable(value = "gridId") long id) {
		return gridMap.get(id).getCells();
	}

}
