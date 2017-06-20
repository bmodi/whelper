package ca.svarb.whelper.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

	protected Properties testProperties;

	protected Properties getTestProperties() throws IOException {
		InputStream inStream = BaseTest.class.getClassLoader().getResourceAsStream("test.properties");
		Properties testProperties = new Properties();
		testProperties.load(inStream);
		return testProperties;
	}
}
