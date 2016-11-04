package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetcraftCrawler {
	
	Set<String> netcraftList=new HashSet<>();
	int current=0;
	
	public void crawl(WebDriver driver, String domain)
	{
		String query=domain;

		driver.get("http://searchdns.netcraft.com/?restriction=subdomain+matches&host="+query);
		while(current<50)
		{
		current++;	
		for(int i=2;i<22;i++)
		{
				try
				{
					WebElement e2=driver.findElement(By.xpath("//*[@id=\"content\"]/div[5]/table/tbody/tr["+i+"]/td[2]/a"));
					if(e2==null)
					{
						return;
					}	
				String found=e2.getAttribute("href").replaceFirst("https://", "").replaceFirst("http://", "");
				if(found.indexOf("/")>-1)
				{
					found=found.substring(0, found.indexOf("/"));
				}
				netcraftList.add(found);
				}
				catch(Exception e)
				{
					continue;
				}
		}
		try
		{
			WebElement e3=driver.findElement(By.xpath("//*[@id=\"content\"]/div[5]/p[2]/a/b"));
			if(e3!=null)
			{
				e3.click();
			}
			else
			{
				break;
			}
		}
		catch(Exception e){
			break;
		}
		}
	}
	
	public void printUrl()
	{
		System.out.println("Subdomain from Netcraft :"+netcraftList.size());
		for(String url:netcraftList)
		{
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
