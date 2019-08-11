/**@author Ujjwal Chakraborty : ujjwalchk@yahoo.co.in 
 * @Date 11/08/2019 , Bangalore
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
 * This class work as a helper to provide the required formatted data to the Executor class.
 *  
 * <About method readURLFromFile(String,String)>
 * 
 * It's 'readURLFromFile' method takes two inputs -
 * (1)Plain-text file name with Absolute path.
 * (2)Destination Directory where the image to be saved.
 * This method returns a HashMap which contains the key/value pair of each of the URLs provided in the plain text file.
 * Key: TypeOftheURL+Delimiter+DownloadDir+Delimiter+ImageFileName
 * Value : Actual URL/Redirected URL
 * Eg: ImageURL#~~#src/test/java/resources#~~#urlNo1_googlelogo_color_272x92dp.png",
 *    "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png
 * 
 * TypeOftheURL : If the URL contains the image address then considered as 'ImageURL' , 
 * if the URL contains full Website address then considered as 'WebsiteURL'
 * 
 * Delimiter : Unique hard-coded String '#~~#' has been used to delimit between different values.
 * 
 * DownloadDir : Directory name where the images would be stored , based on the user input. If the value found NULL the local working directory
 * would be used.
 * 
 * ImageFileName : Name of the image fileName. In case if the URLType as 'WebsiteURL' , then only the folder name would be sent as DownloadDir.
 * The imageFileName would be considered based on the doc alignment using JSoup library (implemented in ImageDownloader Class).
 * Image file name would be appended with 'urlNo' to ensure we uniquely identify the image.
 * 
 * Actual URL/Redirected URL : This is the value of the hashmap entry, if the URL is not found as Redirected URL then the supplied URL would be
 * kept as Actual URL , else Redirected URL would be populated.
 * 
 * 
 * <About method getFinalURL(String)>
 * 
 * The second method 'getFinalURL' is an internal private method used to validate if the URL is redirected.
 * It takes URL as input and returns the final URL(Actual/Redirected) one.
 * It uses a recursive mechanism to check if the URL is redirected to different one based on the Http-Response-Code.
 * It also ensure if the URL is invalid it remove from the list and won't be considered part of the final hashmap.
 * 
 * <About method urlChecker(String)>
 * 
 * The third method 'urlChecker' is an internal private method used to check if the URL type of 'ImageURL' or 'WebURL'.
 * If the he URL contains the image address then considered as 'ImageURL' , 
 * if the URL contains full Website address then considered as 'WebURL'
 *
 * Every class has its exception handling implemented apart from normal exception , would be caught by the general Exception Class.
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
