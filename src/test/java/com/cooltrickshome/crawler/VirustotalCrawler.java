package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VirustotalCrawler {

	Set<String> virustotalList = new HashSet<>();

	public void crawl(WebDriver driver, String domain) {
		String query = domain;

		driver.get("https://www.virustotal.com/en/domain/"+query+"/information/");
		driver.get("https://www.virustotal.com/en/domain/"+query+"/information/");
		try
		{
			driver.findElement(By.id("btn-more-observed-subdomains")).click();
			Thread.sleep(2000);
		}
		catch(Exception e)
		{
			
		}
		for(int i=1;i<500;i++)
		{
			try
			{
				WebElement e2=driver.findElement(By.xpath("//*[@id=\"observed-subdomains\"]/div["+i+"]/a"));
				if(e2==null)
				{
					return;
				}	
			String found=e2.getText().replaceFirst("https://", "").replaceFirst("http://", "");
			if(found.indexOf("/")>-1)
			{
				found=found.substring(0, found.indexOf("/"));
			}
			virustotalList.add(found);
			}
			catch(Exception e)
			{
				return;
			}
		}

	}

	public void printUrl() {
		System.out.println("Subdomain from Virustotal :"+virustotalList.size());
		for (String url : virustotalList) {
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
