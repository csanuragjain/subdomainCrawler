package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DnsdumpsterCrawler {

	Set<String> dnsdumpsterList = new HashSet<>();

	public void crawl(WebDriver driver, String domain) {
		driver.get("https://dnsdumpster.com/");
		driver.findElement(By.id("regularInput")).sendKeys(domain);
		driver.findElement(By.xpath("//*[@id=\"formsubmit\"]/button")).click();
		try
		{
			Thread.sleep(6000);	
		}
		catch(Exception e)
		{
			
		}
		for(int i=1;i<500;i++)
		{
			try
			{
			WebElement e2=driver.findElement(By.xpath("//*[@id=\"intro\"]/div/div[1]/div[9]/table/tbody/tr["+i+"]/td[1]"));
			if(e2==null)
			{
				return;
			}
			String found = e2.getText().replaceFirst("https://", "")
					.replaceFirst("http://", "");
			if (found.indexOf("/") > -1) {
				found = found.substring(0, found.indexOf("/"));
			}
			dnsdumpsterList.add(found);
			}
			catch(Exception e)
			{
				return;
			}
		}
	}

	public void printUrl() {
		System.out.println("Subdomain from Dns Dumpster :"+dnsdumpsterList.size());
		for (String url : dnsdumpsterList) {
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
