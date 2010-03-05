package ca.svarb.whelper.gui;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import ca.svarb.utils.TextImageLoader;
import ca.svarb.whelper.test.BaseTest;


public class TextImageLoaderTest extends BaseTest {
	private String imageDir;
	private TextImageLoader imageLoader;

	@Before
	public void setup() throws IOException {
		testProperties = getTestProperties();
		imageDir = testProperties.getProperty("imagedir");
		imageLoader = new TextImageLoader(imageDir);
	}

	@Test
	public void loadImage() throws IOException {
		ImageIcon icon = imageLoader.getImage("A");
		assertNotNull(icon);
	}

	@Test
	public void nullImage() {
		ImageIcon icon = imageLoader.getImage("3");
		assertNull(icon);
	}

	@Test
	public void nullValue() {
		ImageIcon icon = imageLoader.getImage(null);
		assertNull(icon);
	}
}
