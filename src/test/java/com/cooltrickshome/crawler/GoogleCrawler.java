package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleCrawler {
	
	Set<String> googleList=new HashSet<>();
	int MAX_DOMAIN=15;
	int MAX_ITERATION=15;
	int current=0;
	boolean isitfirstpage=true;
	
	public void sleep(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {

		}
	}
	
	public void crawl(WebDriver driver, String domain)
	{
		int pNum=0;
		boolean complete=false;
		String query="site:*."+domain +" -site:www."+domain;
		int counter=0;
		while(!complete && counter<MAX_ITERATION)
		{
		sleep(2000);	
		counter++;	
		driver.get("https://google.com/search?q="+query+"&btnG=Search&hl=en-US&biw=&bih=&gbv=1&start="+pNum+"&filter=0");
		if(driver.getPageSource().contains("To continue, please type the characters"))
		{
			System.out.println("Captcha found. Please check browser");
			System.out.println("After completing, press Enter");
			Scanner s1=new Scanner(System.in);
			s1.nextLine();
			s1.close();
		}
		if(driver.getPageSource().contains(" did not match any documents"))
		{
			return;
		}
		String tempData="";
		List<WebElement> e1=driver.findElements(By.xpath("//*[@class=\"_Rm\"]"));
		if(e1.size()==0)
		{
			query="site:*."+domain +" -site:www."+domain;
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
				if(found.equals(domain) || !googleList.add(found))
				{
					notFound++;
					if(notFound>=e1.size())
					{
						if(isitfirstpage)
						{
							pNum+=1;
							isitfirstpage=false;
						}
						else
						{
						pNum+=10;
						}
					}
					continue;
				}
				if(++current<=MAX_DOMAIN)
				{
				tempData+=" -site:"+found;
				pNum=0;
				isitfirstpage=true;
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
				if(isitfirstpage)
				{
					pNum+=1;
					isitfirstpage=false;
				}
				else
				{
				pNum+=10;
				}
			}	
		query=query+tempData;
		}
	}
	
	public void printUrl()
	{
		System.out.println("Subdomain from Google :"+googleList.size());
		for(String url:googleList)
		{
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
