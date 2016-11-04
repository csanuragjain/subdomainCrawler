package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CrtshCrawler {

	Set<String> crtshList = new HashSet<>();

	public void crawl(WebDriver driver, String domain) {
		String query = domain;
		
		driver.get("https://crt.sh/?q=%25."+query);
		for(int i=2;i<500;i++)
		{
			try
			{
				crtshList.add(driver.findElement(By.xpath("/html/body/table[2]/tbody/tr/td/table/tbody/tr["+i+"]/td[4]")).getText());
			}
			catch(Exception e)
			{
				return;
			}
		}
	}

	public void printUrl() {
		System.out.println("Subdomain from Crtsh :"+crtshList.size());
		for (String url : crtshList) {
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
