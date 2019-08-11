package com.ujjwal.test.lab.assessment;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;


public class ImageDownloaderTest {
	
	private InputStream is;
	private FileOutputStream os;
	byte[] expectedImageSize;
	private int length;
	private URL url;
	private BufferedImage originalImage;

	
	ImageDownloader imagedownloader=new ImageDownloader();
	
	@Before
	public void testDownloadImagewithURLExpected()
	{
		try {
			String destinationFileName=System.getProperty("user.dir")+"//googlelogo_color_272x92dp.png";
			url = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
			is = url.openStream();
			os = new FileOutputStream(destinationFileName, false);
			byte[] b=new byte[2048];
			
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
            
			originalImage = 
                    ImageIO.read(new File("googlelogo_color_272x92dp.png"));
			
			expectedImageSize = ((DataBufferByte) originalImage.getData().getDataBuffer()).getData();
			
			is.close();
			os.close();

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	@Test
	public void testDownloadImagewithURLActual() {
		
		try {
		imagedownloader.downloadImage("ImageURL#~~##~~#urlNo1_googlelogo_color_272x92dp.png",
				"https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
			Thread.sleep(10000);
			BufferedImage originalImage = 
                    ImageIO.read(new File("urlNo1_googlelogo_color_272x92dp.png"));
			
			byte[] ActualbyteArray = ((DataBufferByte) originalImage.getData().getDataBuffer()).getData();
			assertEquals(expectedImageSize.length,ActualbyteArray.length);
		
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
