package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.WebDriver;

public class ThreatcrowdCrawler {

	Set<String> threatcrowdList = new HashSet<>();

	public void crawl(WebDriver driver, String domain) {
		String query = domain;

		driver.get("https://www.threatcrowd.org/searchApi/v2/domain/report/?domain="+query);
		try
		{
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			
		}
		String response=driver.getPageSource();
		int indexFrom=response.indexOf("\"subdomains\":[");
		response=response.substring(indexFrom);
		int indexTo=response.indexOf("]");
		response=response.substring(0,indexTo);
		response=response.replaceAll("\"", "").replace("[", "");
		response=response.replaceAll(":", "").replaceAll("subdomains", "");
		String[] subdomainList=response.split(",");
		for(String subdomain:subdomainList)
		{
			threatcrowdList.add(subdomain);
		}
	}

	public void printUrl() {
		System.out.println("Subdomain from ThreatCrowd :"+threatcrowdList.size());
		for (String url : threatcrowdList) {
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
