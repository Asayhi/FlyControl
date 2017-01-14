package de.yadrone.base.video.xuggler;

//import java.awt.image.BufferedImage;
import java.io.InputStream;


import de.yadrone.base.video.ImageListener;
import de.yadrone.base.video.VideoDecoder;

public class XugglerDecoder implements VideoDecoder
{
	
	private ImageListener listener;

	public void decode(InputStream is)
	{
		
	}

	public void setImageListener(ImageListener listener)
	{
		this.listener = listener;
	}
}
