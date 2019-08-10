/**@author Ujjwal Chakraborty : ujjwalchk@yahoo.co.in 
 * @Date 04/08/2019 , Bangalore
 */
package com.ujjwal.test.lab.assessment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 * 
 * 
 * This class work as a helper to provide the required formatted data to the
 * Executor class. It's 'readURLFromFile' method takes single inputs
 * (1)Plain-text file name with Absolute path.
 * 
 * The second method 'extensionChecker' is an internal private method used to
 * validate the extension of the image-file. It's also uses the
 * customExceptionClass 'InvalidExtentionException Class' to generate error
 * incase the image extension regex match fails.
 * 
 * This class reads the lines of the plain text file and takes care of below
 * actions - 1. Generate the image file name to be saved as 2. Check the
 * Extension of image file with Regex 3. Finally populate the key(Target Image
 * file name) & value (Source URL) into Hashtable and returns to the executor
 * class
 * 
 * "Helper Class" returns a Hashtable with "Key" as target image file name &
 * value as source URL (eg. below) eg: for a URL
 * https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * , below are key/value pair key : url_1_googlelogo_color_272x92dp.png value :
 * https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * Please note that the second parameter of the key : "_1_" , it is the line
 * number of the particular URL in the plain text. It has been kept to avoid
 * image over-riding & easily find-out the image when we have a big list.
 * 
 * Any other exception during execution of above tasks , would be caught by the
 * general Exception Class.
 *
 */

public class Helper {

	private String url;
	private String finalUrl;
	private String destinationFile;
	private String imageExtn;
	private int lineNo = 1;
	private String imageFileName;
	private File file;
	private BufferedReader br;
	boolean patternValidator;
	private String urlTypeFlag;
	private String IMAGE_EXTENTION = "(jpeg|jpg|png|gif|bmp|tif|ppm)";


	protected LinkedHashMap<String, String> readURLFromFile(String fileName,String downloadDestination) {
		
		System.out.println("#############################################");
		System.out.println("#### Helper Class Execution Started #########");
		System.out.println("#############################################");
		
		LinkedHashMap<String, String> urlList = new LinkedHashMap<String, String>();
		file = new File(fileName);

		try {
			br = new BufferedReader(new FileReader(file));

			while ((url = br.readLine()) != null) {
				
				
				if (url.length() > 0) {
					System.out.println("#== Helper class going to start with the URL :: " + url +" ==#");
					System.out.println("#== Task :: URL Redirection validator ==#");
					System.out.println("Validating if the URL :: " + url +" is redirected? ");
					
					finalUrl=getFinalURL(url);
					
					if(finalUrl != "Error")
					{
			
					 if(!finalUrl.equals(url))
					{
						System.out.println("URL Redirection found , new URL ::" + finalUrl);
					}
					else
					{
						System.out.println("No URL Redirection found");
					}
								
					
					
					System.out.println("#== Task :: URL Type(Image Only URL/Full Site) validator ==#");
					imageFileName = finalUrl.substring(finalUrl.lastIndexOf("/") + 1, finalUrl.length());
					imageExtn = imageFileName.substring(imageFileName.indexOf(".") +1, imageFileName.length());

					urlTypeFlag = urlChecker(imageExtn);

   			        if(urlTypeFlag=="WebsiteURL")
   			        {					
   			        	destinationFile ="WebsiteURL" + "#~~#" + downloadDestination + "#~~#" +"urlNo" + lineNo +"_"+ imageFileName;
   			        	System.out.println("URL :: " + url +" is marked as WebsiteURL as it contains full website address");
   			        }
   			        else if (urlTypeFlag=="ImageURL")
   			        {
   			        	destinationFile = "ImageURL"+ "#~~#" + downloadDestination + "#~~#" + "urlNo" + lineNo +"_"+ imageFileName;
   			        	System.out.println("URL :: " + url +" is marked as ImageURL as it contains absolute image address");
   			        }
					urlList.put(destinationFile, finalUrl);
					lineNo++; 
					System.out.println("#== Helper class completed to task for the URL :: " + url +" ==#");
					System.out.println("\n" + "\n");
				}
					else
					{
						System.out.println("Error in redirection validator for the URL :: " + finalUrl
								+ " No Action would be taken ! If the issue is temporary then try again");
					}
				}
				else {
					lineNo++;
				}
			}
			System.out.println("#############################################");
			System.out.println("#### Helper Class Execution Completed #######");
			System.out.println("#############################################");
			br.close();

		} catch (FileNotFoundException e) {

			System.out.println("#== Error while reading the file , please check the stack-trace ==#");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("#== IO Exception occured , please check the stack-trace ==#");
			e.printStackTrace();
		}

		catch (Exception e) {

			System.out.println("#== Exception occured , please check the stack-trace ==#");
			e.printStackTrace();
		}

		finally {

			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println("#== IO Exception occured , please check the stack-trace ==#");
				e.printStackTrace();
			}

		}
  
		return urlList;
		
		

	}

	private static String getFinalURL(String url) {
		HttpURLConnection httpConn = null;
		try {
			httpConn = (HttpURLConnection) new URL(url).openConnection();
			httpConn.setInstanceFollowRedirects(false);
			httpConn.connect();
			httpConn.getInputStream();
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
					|| httpConn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
				String redirectUrl = httpConn.getHeaderField("Location");
				return getFinalURL(redirectUrl);
			}
		} catch (Exception e) {
			System.out.println("Error caught for the URL-->" + url + " Exception-->" + e.toString());
			url="Error";
		} finally {
			httpConn.disconnect();
		}
		return url;

	}
	
	private String urlChecker(String imageFileExtn) {

		boolean extensionValidator;
		extensionValidator = imageFileExtn.matches(IMAGE_EXTENTION);
		if (!extensionValidator) {
			urlTypeFlag = "WebsiteURL";
		} else {
			urlTypeFlag = "ImageURL";
		}
		return urlTypeFlag;
	}
	
	
	
}
