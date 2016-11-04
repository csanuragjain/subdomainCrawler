package com.cooltrickshome.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cooltrickshome.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SslsanCrawler {

	Set<String> sslsanList = new HashSet<>();

	public void crawl(WebDriver driver, String domain) {
		String query = domain;

		driver.get("http://www.ssltools.com/?url=" + query);
		try
		{
			Thread.sleep(8000);	
		}
		catch(Exception e)
		{
			
		}
		List<WebElement> e1 = driver.findElements(By
				.xpath("//*[@id=\"san_entries\"]/a"));
		if (e1.size() == 0) {
			return;
		}
		for (WebElement e2 : e1) {
			try {
				String found = e2.getText().replaceFirst("https://", "")
						.replaceFirst("http://", "");
				if (found.indexOf("/") > -1) {
					found = found.substring(0, found.indexOf("/"));
				}
				sslsanList.add(found);
			} catch (Exception e) {
				continue;
			}
		}

	}

	public void printUrl() {
		System.out.println("Subdomain from SSL SAN :"+sslsanList.size());
		for (String url : sslsanList) {
			//System.out.println(url);
			url=Driver.removeBadWord(url);
			Driver.subdomainList.add(url.trim());
		}
	}

}
