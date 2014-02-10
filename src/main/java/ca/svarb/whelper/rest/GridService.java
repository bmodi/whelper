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

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public List<String> getWords(@RequestBody List<String> grid) {
		for (String string : grid) {
			System.out.println("grid: "+string);
		}
		List<String> words=new ArrayList<>();
		words.add(grid.get(0)+"abc");
		words.add(grid.get(1)+"def");
		return words;
	}
}
