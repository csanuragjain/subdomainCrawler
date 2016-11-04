package com.cooltrickshome;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.cooltrickshome.crawler.AskCrawler;
import com.cooltrickshome.crawler.BaiduCrawler;
import com.cooltrickshome.crawler.BingCrawler;
import com.cooltrickshome.crawler.CrtshCrawler;
import com.cooltrickshome.crawler.DnsdumpsterCrawler;
import com.cooltrickshome.crawler.GoogleCrawler;
import com.cooltrickshome.crawler.NetcraftCrawler;
import com.cooltrickshome.crawler.PassivednsCrawler;
import com.cooltrickshome.crawler.SslsanCrawler;
import com.cooltrickshome.crawler.ThreatcrowdCrawler;
import com.cooltrickshome.crawler.VirustotalCrawler;
import com.cooltrickshome.crawler.YahooCrawler;

public class Driver {

	public static Set<String> subdomainList = new HashSet<>();
	public static String[] badCrawledWord={"apache","nginx","\\.\\.\\."};
	public static String domainToTest="";
	static WebDriver driver = null;
	
	public static String removeBadWord(String url)
	{
		String[] removeWord=badCrawledWord;
		for(int i=0;i<removeWord.length;i++)
		{
			url=url.toLowerCase().replaceAll(removeWord[i],"");
		}
		return url;
	}
	
	public static void main(String args[])
	{
		System.setProperty("webdriver.chrome.driver",
				"./driver/chromedriver.exe");
		Scanner s=new Scanner(System.in);
		System.out.println("Enter the website for which subdomain need to be found");
		domainToTest=s.nextLine();
		driver = new ChromeDriver();
		crawlme(driver);
		System.out.println("Total Subdomain found after omitting duplicate: "+subdomainList.size());
		for(String subdomain: subdomainList)
		{
			System.out.println(subdomain);
		}
	}
	
	public static void crawlme(WebDriver driver) {
		
		try
		{
		GoogleCrawler google=new GoogleCrawler();
		google.crawl(driver, domainToTest);
		google.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Bing");
		}
		try
		{
		BingCrawler bing=new BingCrawler();
		bing.crawl(driver, domainToTest);
		bing.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Bing");
		}
		try
		{
		YahooCrawler yahoo=new YahooCrawler();
		yahoo.crawl(driver, Driver.domainToTest);
		yahoo.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Yahoo");
		}
		try
		{
		AskCrawler ask=new AskCrawler();
		ask.crawl(driver, Driver.domainToTest);
		ask.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Ask");
		}
		try
		{
		BaiduCrawler baidu=new BaiduCrawler();
		baidu.crawl(driver, Driver.domainToTest);
		baidu.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Baidu");
		}
		try
		{
		NetcraftCrawler netcrawler=new NetcraftCrawler();
		netcrawler.crawl(driver, Driver.domainToTest);
		netcrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling NetCraft");
		}
		try
		{
		SslsanCrawler sslsancrawler=new SslsanCrawler();
		sslsancrawler.crawl(driver, Driver.domainToTest);
		sslsancrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling SSL SAN");
		}
		try
		{
		DnsdumpsterCrawler dnsdumpstercrawler=new DnsdumpsterCrawler();
		dnsdumpstercrawler.crawl(driver, Driver.domainToTest);
		dnsdumpstercrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Dnsdumpster");
		}
		try
		{
		VirustotalCrawler virustotalcrawler=new VirustotalCrawler();
		virustotalcrawler.crawl(driver, Driver.domainToTest);
		virustotalcrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Virustotal");
		}
		try
		{
		ThreatcrowdCrawler threatcrowdcrawler=new ThreatcrowdCrawler();
		threatcrowdcrawler.crawl(driver, Driver.domainToTest);
		threatcrowdcrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling ThreatCrowd");
		}
		try
		{
		CrtshCrawler crtshcrawler=new CrtshCrawler();
		crtshcrawler.crawl(driver, Driver.domainToTest);
		crtshcrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling CRTSH");
		}
		try
		{
		PassivednsCrawler passivednscrawler=new PassivednsCrawler();
		passivednscrawler.crawl(driver, Driver.domainToTest);
		passivednscrawler.printUrl();
		}
		catch(Exception e)
		{
			System.out.println("Issue found while crawling Passive DNS");
		}


	}

}
