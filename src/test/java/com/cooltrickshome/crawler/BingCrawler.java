package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BingCrawler {
	
	Set<String> bingList=new HashSet<>();
	int MAX_DOMAIN=15;
	int MAX_ITERATION=30;
	int current=0;
	
	public void crawl(WebDriver driver, String domain)
	{
		int pNum=0;
		boolean complete=false;
		String query="domain:"+domain;
		int counter=0;
		while(!complete && counter<MAX_ITERATION)
		{
		counter++;	
		driver.get("https://www.bing.com/search?q="+query+"&go=Submit&first="+pNum);
		String tempData="";
		if(driver.getPageSource().contains("No results found for"))
		{
			return;
		}
			List<WebElement> e1=driver.findElements(By.xpath("//*[@class=\"b_algo\"]/h2/a"));
			if(e1.size()==0)
			{
				query="domain:"+domain;
				pNum+=10;
			}
			int notFound=0;
			for(WebElement e2:e1)
			{
				try
				{
				String found=e2.getAttribute("href").replaceFirst("https://", "").replaceFirst("http://", "");
				if(found.indexOf("/")>-1)
				{
					found=found.substring(0, found.indexOf("/"));
				}
				if(found.equals(domain) || !bingList.add(found))
				{
					notFound++;
					if(notFound>=e1.size())
					{
						pNum+=10;
					}
					continue;
				}
				if(++current<=MAX_DOMAIN)
				{
				tempData+=" -domain:"+found;
				pNum=0;
				}
				}
				catch(Exception e)
				{
					complete=true;
					break;
				}
			}
			if(current>MAX_DOMAIN && notFound<e1.size())
			{
				pNum+=10;
			}
		query=query+tempData;
		}
	}
	
	public void printUrl()
	{
		System.out.println("Subdomain from Bing :"+bingList.size());
		for(String url:bingList)
		{
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
