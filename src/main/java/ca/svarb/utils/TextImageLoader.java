package ca.svarb.utils;

import java.net.URL;

import javax.swing.ImageIcon;


/**
 * TextImageLoader loads an image from files based on a
 * given text string.
 *
 */
public class TextImageLoader {

	private final String imageDir;

	public TextImageLoader(String imageDir) {
		this.imageDir=imageDir;
	}

	public ImageIcon getImage(String imageName) {
		ImageIcon icon=null;
		if ( imageName!=null ) {
			String resourceName = imageDir+"/"+imageName+".gif";
			URL imageURL = TextImageLoader.class.getClassLoader().getResource(resourceName);
			if ( imageURL!= null ) {
				icon=new ImageIcon(imageURL);
			}
		}
		return icon;
	}

}
