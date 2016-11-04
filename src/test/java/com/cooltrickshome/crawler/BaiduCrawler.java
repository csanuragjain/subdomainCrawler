package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaiduCrawler {
	
	Set<String> baiduList=new HashSet<>();
	int MAX_DOMAIN=15;
	int MAX_ITERATION=30;
	int current=0;
	
	public void crawl(WebDriver driver, String domain)
	{
		int pNum=0;
		boolean complete=false;
		String query="site:"+domain;
		int counter=0;
		while(!complete && counter<MAX_ITERATION)
		{
		counter++;	
		driver.get("https://www.baidu.com/s?wd="+query+"&oq="+query+"&pn="+pNum);
		String tempData="";
		if(driver.getPageSource().contains("\"site:"+domain+"\""))
		{
			return;
		}
		List<WebElement> e1=driver.findElements(By.xpath("//*[@class=\"c-showurl\"]"));
		if(e1.size()==0)
		{
			query="site:"+domain;
			pNum+=10;
		}
		int notFound=0;
			for(WebElement e2:e1)
			{
				try
				{
				String found=e2.getText().replaceFirst("https://", "").replaceFirst("http://", "");
				if(found.indexOf("/")>-1)
				{
					found=found.substring(0, found.indexOf("/"));
				}
				if(found.equals(domain) || !baiduList.add(found))
				{
					notFound++;
					if(notFound>=e1.size())
					{
						pNum+=10;
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
				pNum+=10;
			}
		query=query+tempData;
		}
	}
	
	public void printUrl()
	{
		System.out.println("Subdomain from Baidu :"+baiduList.size());
		for(String url:baiduList)
		{
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
