/**@author Ujjwal Chakraborty : ujjwalchk@yahoo.co.in 
 * @Date 04/08/2019 , Bangalore
 */
package com.ujjwal.test.lab.assessment;

import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * 
 * This is the main Executor class that controls the execution between the
 * "Helper Class" & "ImageDownloader Class"
 * 
 * It takes two user inputs : (1)The absolute path of Plain-Text File containing
 * the URLs (2)Destination File location where the images would be saved
 * 
 * First it invoke the "Helper Class" with the first input(i.e Absolute plain
 * text path containing image URLs) "Helper Class" returns a Hashtable with
 * "Key" as target image file name & value as source URL (eg. below) eg: for a
 * URL
 * https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * , below are key/value pair key : url_1_googlelogo_color_272x92dp.png value :
 * https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * Please note that the second parameter of the key : "_1_" , it is the line
 * number of the particular URL in the plain text. It has been kept to avoid
 * image over-riding & easily find-out the image when we have a big list.
 * 
 * The second invocation is for the actual downloader class ,"ImageDownloader
 * Class" , based on the hashtable list , it calls the downloader class. The
 * arguments being passed (1)Source URL (2)Destination File location with
 * image-name. For the Second argument , this class append the User Prompted
 * input (destination file location) along with the image file name , to ensure
 * its entirely on the user where they want to store the downloaded images.
 * Further Download action being carried out by the ImageDownloader Class.
 * 
 * Any exception during execution of above tasks , would be caught by the
 * Exception blocks.
 */

public class Executor {

	@SuppressWarnings("rawtypes")
	public static void main(String args[]) {
		Scanner userInput = null;
		String urlfilePath;
		String imgFilePath;
		Helper urlhelper = new Helper();
		ImageDownloader imagedownloader = new ImageDownloader();
		LinkedHashMap<String, String> urlList = new LinkedHashMap<String, String>();

		try {
			userInput = new Scanner(System.in);
			System.out.println("Enter the absolute path of the url file : ");
			urlfilePath = userInput.nextLine();
			System.out.println("Enter the destination where the images to be saved : ");
			imgFilePath = userInput.nextLine();
			userInput.close();
			urlList=urlhelper.readURLFromFile(urlfilePath,imgFilePath);
			
			for(Map.Entry m:urlList.entrySet()){
				 imagedownloader.downloadImage(m.getKey().toString(), m.getValue().toString());
				  //System.out.println("Key" + m.getKey() + "Value" + m.getValue());
				  System.out.println("\n");
				  }
			
			System.out.println("====No more URL left====");
		} catch (InputMismatchException ex) {
			System.out.println("Error " + ex);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			userInput.close();
		}
	}

}
