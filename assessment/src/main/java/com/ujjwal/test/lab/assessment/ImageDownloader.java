/**@author Ujjwal Chakraborty : ujjwalchk@yahoo.co.in 
 * @Date 04/08/2019 , Bangalore
 */
package com.ujjwal.test.lab.assessment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * 
 * This class is use to download the image from the URL specified. It's
 * 'downloadImage' method takes two inputs (1)Source URL (2)Destination File
 * location with image-name.
 * 
 * It's read the data/image using the inPutStream and write to disk using
 * outPutStream, java.io package. To Read the data from the inputStream it uses
 * java.io.FileInputStream.read(byte[] b) reads up-to the length from input
 * stream as array of bytes.
 * 
 * Based on the length of the array we write the image to the file using
 * outPutStream. The buffer size has been kept as 2kb in this case , but it
 * would depend on the system to system and based on the file system block size,
 * CPU cache size/latency. We should configure the buffer size power of 2
 * ,at-least equal/double than a file system block size to avoid any wasted
 * reads.
 * 
 * If there are any errors due to connection/downloading/writing to destination
 * file the general- -catch block would capture the exception and print the
 * stack trace accordingly.
 *
 */
public class ImageDownloader {

	private URL url;
	private InputStream is;
	private FileOutputStream os;
	private byte[] b;
	private int length;
	private String finalUrl;
	boolean urlValidator;
	private String imageFileName;
	private String urlTypeFlag;
	private String destinationFile;
	private HttpURLConnection httpConn = null;
	private Elements img = null;

	synchronized protected void downloadImage(String imageTypeDetails, String imageUrl) {
		try {
			System.out.println("\n" + "\n");
			System.out.println("#############################################");
			System.out.println("#### Downloader Class Execution Started #####");
			System.out.println("##############################################");

			System.out.println("#== Downloader class going to start with the URL :: " + imageUrl + " ==#");
			String[] imageTypeST = imageTypeDetails.split("#~~#");
			urlTypeFlag = imageTypeST[0];
			destinationFile = imageTypeST[1].trim();
			if(destinationFile.length() <= 0)
			{
				destinationFile=System.getProperty("user.dir");
			}
			imageFileName = imageTypeST[2];
			
			if (imageFileName.contains("?")) {
				imageFileName = imageFileName.substring(0, imageFileName.indexOf("?"));
			}

			// *Code to download the image where the URL is appended with the absolute image
			// name*//
			if (urlTypeFlag.equals("ImageURL")) {
				System.out.println("#== URL type identified as 'ImageURL' ==#");
				String imgDestinationFile = destinationFile + "//" + imageFileName;
				System.out.println("#== Destination file named as :: " + imgDestinationFile + " ==#");
				url = new URL(imageUrl);
				is = url.openStream();
				System.out.println("#== Successfully connected to the url ==#");
				os = new FileOutputStream(imgDestinationFile, false);
				b = new byte[2048];
				System.out.println("#== Image download Starting ==#");

				System.out.println("Please wait ...");
				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}

				System.out.println("#== Image download Completed ==#");

				is.close();
				os.close();

			}

			// *Code to download the images where the URL is given as full website
			// address*//
			else if (urlTypeFlag.equals("WebsiteURL")) {
				System.out.println("#== URL type identified as 'WebsiteURL' ==#");
				Document doc = Jsoup.connect(imageUrl).get();
				img = doc.select("img[src~=(?i)\\.(png|jpe?g|gif|bmp|tif)]");

				if (img.size() <= 0) {
					img = doc.getElementsByTag("img");

				}
				String destinationFolderName = destinationFile + "//" + imageFileName;
				File file = new File(destinationFolderName);
				if (!file.exists()) {
					if (file.mkdir()) {
						System.out.println(
								"#== Destination Directory folder created as :: " + destinationFolderName + " ==#");
					} else {
						System.out
								.println("#== Failed to create Directory folder :: " + destinationFolderName + " ==#");
					}
				} else {
					if (file.delete()) {
						if (file.mkdir()) {
							System.out.println(
									"#== Destination Directory folder created as :: " + destinationFolderName + " ==#");
						} else {
							System.out.println(
									"#== Failed to create Directory folder :: " + destinationFolderName + " ==#");
						}
					}
				}

				if (img.size() > 0) {
					for (Element el : img) {
						String absoluteURL = el.absUrl("src");
						System.out.println("#== Imaged to be downloaded for the subURL " + absoluteURL + " ==#");
						getImages(absoluteURL, destinationFolderName);

					}
				} else {
					System.out.println("No Image Found, for the URL-->" + imageUrl);
				}
			}

			System.out.println("#== Downloader class completed the task for the URL ==#");

			System.out.println("##############################################");
			System.out.println("#### Downloader Class Execution Completed ####");
			System.out.println("##############################################");

		}

		catch (IOException ex) {
			System.err.println("#== IO Exception occured , please check the stack-trace ==#");
		}

		catch (Exception e) {

			System.err.println(
					"The image can't be downloaded for ->" + finalUrl + " see the below stack-trace for details");
			e.printStackTrace();
		}

		finally {

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					System.err.println("#== IO Exception occured , please check the stack-trace ==#");
					e.printStackTrace();
				}
			}
			if (httpConn != null) {
				httpConn.disconnect();
			}

		}

	}

	synchronized private static void getImages(String absoluteURL, String destinationFolderName) throws IOException {

		try {
			int indexname = absoluteURL.lastIndexOf("/");

			if (indexname == absoluteURL.length()) {
				absoluteURL = absoluteURL.substring(1, indexname);
			}
			indexname = absoluteURL.lastIndexOf("/");
			String name = absoluteURL.substring(indexname + 1, absoluteURL.length());
			if (name.contains("?")) {
				name = name.substring(0, name.indexOf("?"));
			}
			destinationFolderName = destinationFolderName + "//" + name;
			URL url = new URL(absoluteURL);
			InputStream is = url.openStream();
			System.out.println("#== Imaged download Starting for subURL " + absoluteURL + " ==#");
			OutputStream os = new BufferedOutputStream(new FileOutputStream(destinationFolderName));

			System.out.println("Please wait ...");
			for (int b; (b = is.read()) != -1;) {
				os.write(b);
			}
			os.close();
			is.close();
			System.out.println("#== Imaged download completed for the subURL " + absoluteURL + " ==#");
		} catch (Exception e) {
			System.err.println("#== Exception occured , please check the stack-trace ==#");
			e.printStackTrace();
		}
	}

}
