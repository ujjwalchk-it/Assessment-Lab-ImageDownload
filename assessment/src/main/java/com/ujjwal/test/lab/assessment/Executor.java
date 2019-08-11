/**@author Ujjwal Chakraborty : ujjwalchk@yahoo.co.in 
 * @Date 11/08/2019 , Bangalore
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
 * text path containing image URLs) "Helper Class" returns a HashMap with
 * "Key" as imageTypeDetails & value as source URL (eg. below) 
 * eg: for a URL
 * https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * , below are key/value pair 
 * key : ImageURL#~~#src/test/java/resources#~~#urlNo1_googlelogo_color_272x92dp.png
 * value : https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * 
 * The second invocation is for the actual downloader class ,"ImageDownloader
 * Class" , based on the hashmap entry , it calls the downloader class. The
 * arguments being passed (1)imageTypeDetails (2)URL. 
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
