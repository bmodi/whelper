package ca.svarb.whelper;

import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import ca.svarb.whelper.test.BaseTest;

public class WordSearcherAppTest extends BaseTest {

	private Properties testProperties;
	private String gridFileName;

	@Before
	public void setup() throws IOException {
		testProperties = getTestProperties();
		gridFileName = testProperties.getProperty("gridFile");
	}

	@Test
	public void mainNoArgs() {
		String[] args=new String[0];
		WordSearcherApp.main(args);
	}
	
	@Test
	public void mainBadGridFile() {
		String[] args={"badgrid.txt", "baddict.txt"};
		WordSearcherApp.main(args);
	}

	@Test
	public void mainBadDictFile() {
		String[] args={gridFileName, "baddict.txt"};
		WordSearcherApp.main(args);
	}
}
