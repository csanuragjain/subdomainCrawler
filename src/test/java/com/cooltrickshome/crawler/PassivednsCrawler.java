package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PassivednsCrawler {

	Set<String> passivednsList = new HashSet<>();

	public void crawl(WebDriver driver, String domain) {
		String query = domain;
		
		driver.get("http://ptrarchive.com/tools/search.htm?label="+query);
		for(int i=2;i<500;i++)
		{
			try
			{
				String sub=driver.findElement(By.xpath("/html/body/pre/table/tbody/tr["+i+"]/td[3]")).getText();
				if(sub.indexOf("[")!=-1)
				{
					sub=sub.substring(0, sub.indexOf("["));
				}
				passivednsList.add(sub.trim());
			}
			catch(Exception e)
			{
				break;
			}
		}
		driver.get("http://ptrarchive.com/tools/search.htm?label="+query+"&date=ALL");
		String response=driver.findElement(By.xpath("/html/body/pre")).getText();
		String[] temp=response.split("\n");
		for(int i=2;i<temp.length;i++)
		{
			try
			{
			if(!temp[i].contains("Could not find"))
			{
				String subdomain=temp[i].substring(temp[i].indexOf("]")+1,temp[i].length());
				if(subdomain.indexOf("[")>-1)
				{
					subdomain=subdomain.substring(0, subdomain.indexOf("["));
				}
				subdomain=subdomain.trim().replaceAll("\\s", "");
				passivednsList.add(subdomain);
			}
			}
			catch(Exception e)
			{
			}
		}
	}

	public void printUrl() {
		System.out.println("Subdomain from Passive DNS :"+passivednsList.size());
		for (String url : passivednsList) {
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
