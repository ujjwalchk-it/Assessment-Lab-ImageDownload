package com.ujjwal.test.lab.assessment;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.junit.Test;

public class HelperTest {

	@Test
	public void testReadURLFromFileForImageURL() {
		LinkedHashMap<String,String> stubHM= new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> actualHM= new LinkedHashMap<String,String>();
		stubHM.put("ImageURL#~~#src/test/java/resources#~~#urlNo1_googlelogo_color_272x92dp.png",
				"https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		
		Helper helper=new Helper();
		actualHM=helper.readURLFromFile("imageurl-list.txt", "src/test/java/resources");
		
		assertEquals(stubHM , actualHM);
		stubHM.clear();
		actualHM.clear();
		
	}
	
	@Test
	public void testReadURLFromFileForWebURL() {
		LinkedHashMap<String,String> stubHM= new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> actualHM= new LinkedHashMap<String,String>();
		stubHM.put("WebsiteURL#~~#src/test/java/resources#~~#urlNo1_www.google.com",
				"https://www.google.com");
		
		Helper helper=new Helper();
		actualHM=helper.readURLFromFile("weburl-list.txt", "src/test/java/resources");
		
		assertEquals(stubHM , actualHM);
		stubHM.clear();
		actualHM.clear();
		
	}

	@Test
	public void testReadURLFromFileForRedirectURL() {
		LinkedHashMap<String,String> stubHM= new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> actualHM= new LinkedHashMap<String,String>();
		stubHM.put("WebsiteURL#~~#src/test/java/resources#~~#urlNo1_219",
				"https://www.goldnames.com/view/219");
		
		Helper helper=new Helper();
		actualHM=helper.readURLFromFile("redirecturl-list.txt", "src/test/java/resources");
		
		assertEquals(stubHM , actualHM);
		stubHM.clear();
		actualHM.clear();
		
	}
	
	@Test
	public void testReadURLFromFileForInvalidwithValidURL() {
		LinkedHashMap<String,String> stubHM= new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> actualHM= new LinkedHashMap<String,String>();
		stubHM.put("WebsiteURL#~~#src/test/java/resources#~~#urlNo1_www.google.com",
				"https://www.google.com");
		
		Helper helper=new Helper();
		actualHM=helper.readURLFromFile("invalidurl-list.txt", "src/test/java/resources");
		
		assertEquals(stubHM , actualHM);
		stubHM.clear();
		actualHM.clear();
		
	}
	
	@Test
	public void testReadURLFromFileForEmptyLinewithValidURL() {
		LinkedHashMap<String,String> stubHM= new LinkedHashMap<String,String>();
		LinkedHashMap<String,String> actualHM= new LinkedHashMap<String,String>();
		stubHM.put("WebsiteURL#~~#src/test/java/resources#~~#urlNo2_www.google.com",
				"https://www.google.com");
		
		Helper helper=new Helper();
		actualHM=helper.readURLFromFile("emptylinevalidurl-list.txt", "src/test/java/resources");
		
		assertEquals(stubHM , actualHM);
		stubHM.clear();
		actualHM.clear();
		
	}
	
	
	
}
