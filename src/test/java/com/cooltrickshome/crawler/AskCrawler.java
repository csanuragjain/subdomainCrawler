package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cooltrickshome.Driver;

public class AskCrawler {
	
	Set<String> askList=new HashSet<>();
	int MAX_DOMAIN=15;
	int MAX_ITERATION=30;
	int current=0;
	
	public void crawl(WebDriver driver, String domain)
	{
		int pNum=0;
		boolean complete=false;
		String query="site:*."+domain;
		int counter=0;
		while(!complete && counter<MAX_ITERATION)
		{
		counter++;	
		driver.get("http://www.ask.com/web?q="+query+"&page="+pNum);
		String tempData="";
		if(driver.getPageSource().contains("did not match with any"))
		{
			return;
		}
		List<WebElement> e1=driver.findElements(By.xpath("//*[@class=\"web-result-title-link\"]"));
		if(e1.size()==0)
		{
			query="site:*."+domain;
			pNum++;
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
				if(found.equals(domain) || !askList.add(found))
				{
					notFound++;
					if(notFound>=e1.size())
					{
						pNum++;
					}
					continue;
				}
				if(++current<MAX_DOMAIN)
				{
				tempData+=" -site:"+found;
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
				pNum++;
			}
		query=query+tempData;
		}
	}
	
	public void printUrl()
	{
		System.out.println("Subdomain from Ask :"+askList.size());
		for(String url:askList)
		{
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
