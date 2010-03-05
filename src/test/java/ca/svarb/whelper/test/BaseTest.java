package ca.svarb.whelper.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ca.svarb.whelper.gui.TextImageLoaderTest;

public class BaseTest {

	protected Properties testProperties;

	protected Properties getTestProperties() throws IOException {
		InputStream inStream = TextImageLoaderTest.class.getClassLoader().getResourceAsStream("test.properties");
		Properties testProperties = new Properties();
		testProperties.load(inStream);
		return testProperties;
	}

}
