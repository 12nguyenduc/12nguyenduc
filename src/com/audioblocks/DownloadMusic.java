package com.audioblocks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DownloadMusic{
	
	private final static String LOGIN_URL = "https://www.audioblocks.com/login";
	private final static String MUSIC_TAG_URL = "https://www.audioblocks.com/royalty-free-audio/music/love";
	
	private final static String btnChat = "//*[@id=\"intercom-container\"]";
	private final static String inputEmailXPath = "//*[@id=\"email\"]";
	private final static String inputPasswordXPath = "//*[@id=\"password\"]";
	private final static String btnSubmit = "//*[@id=\"login-form\"]/p/button";
	private final static String btnLoadMore = "//*[@id=\"main-search-content\"]/div/div[3]/div[3]/div[2]";
	
	private final static String EMAIL = "ictduc@gmail.com";
	private final static String PASSWORD = "Anhduc12";
	
	private final static int maxPageItems = 36;
	private static int sectionIndex = 0;
	
	public static String getSectionIndex() {
		sectionIndex+=1;
		return String.format("//*[@id=\"as-search-results\"]/div/section[%d]/div[2]/div[3]/div[2]", sectionIndex);
	}
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");

	    WebDriver driver = new ChromeDriver();
	    
	    login(driver);
	    downloadMusic(driver);
	    
//	    driver.quit();
	}
	
	public static void login(WebDriver driver) {
		driver.get(LOGIN_URL);
	    driver.manage().window().maximize();
	    waitForElementVisible(driver, inputEmailXPath);
	    driver.findElement(By.xpath(inputEmailXPath)).sendKeys(EMAIL);
	    driver.findElement(By.xpath(inputPasswordXPath)).sendKeys(PASSWORD);
	    driver.findElement(By.xpath(btnSubmit)).click();
	    waitForElementInvisible(driver, btnSubmit);
	    System.out.println("Login Success");
	}
	
	public static void downloadMusic(WebDriver driver) {
		driver.get(MUSIC_TAG_URL);
		
		driver.manage().window().maximize();
		
		waitForElementVisible(driver, btnChat);
		removeAttributeOfElement(driver, btnChat);
		
		while(true) {
			String currentSection = getSectionIndex();
			System.out.println("currentSection "+currentSection);
			if(sectionIndex%maxPageItems==0) {
				waitForElementVisible(driver, btnLoadMore);
				driver.findElement(By.xpath(btnLoadMore)).click();
				waitForElementInvisible(driver, btnLoadMore);
			}
			waitForElementVisible(driver, currentSection);
			moveToElement(driver, currentSection);
			System.out.println("Download section "+sectionIndex);
		}
	}	
	
	public static void waitForElementVisible(WebDriver driver, String locator) {
		By by = By.xpath(locator);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public static void moveToElement(WebDriver driver, String locator) {
		By by = By.xpath(locator);
		WebElement element = driver.findElement(by);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}
	
	public static void waitForElementInvisible(WebDriver driver, String locator) {
		By by = By.xpath(locator);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}


	public static void clickToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
	}
	
	public static void doubleClickToElement(WebDriver driver, String locator) {
		WebElement ele = driver.findElement(By.xpath(locator));
		Actions builder = new Actions(driver);
		builder.doubleClick(ele).perform();
	}	
	
	public static void removeAttributeOfElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		((JavascriptExecutor) driver).executeScript("arguments[0].parentNode.removeChild(arguments[0])", element);
	}
	
	public static Object jsScrollToElement(WebDriver driver, String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

}
