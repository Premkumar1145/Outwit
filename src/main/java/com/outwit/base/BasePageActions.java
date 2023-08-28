package com.outwit.base;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
/*import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.StringUtils;*/
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import java.util.logging.Logger;
/*
import com.google.common.base.Function;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.joann.library.GenericLib;
import com.joann.report.ExtentManager;
import com.joann.report.ExtentTestManager;
import com.joann.report.utility;
import com.relevantcodes.extentreports.LogStatus;*/

/*import atu.testrecorder.ATUTestRecorder;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;*/
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.outwit.reports.ExtentManager;
import com.outwit.reports.Utility;
import com.outwit.utils.DataProviderSelenium;

import com.outwit.utils.GenericLib;
import com.outwit.utils.JavaEmail;
import com.outwit.utils.MailProjectClass;
import com.outwit.utils.TestUtils;
import com.outwit.utils.ZipUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Function;


import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class BasePageActions {

	
	public static String baseURI = "http://localhost:7001";
	public static String userName = "admin";
	public static String passWord = "password";
	public static String WebUrl;
	public static String WebUsername;
	public static String WebPassword;
	public static String Profile;
	public static String Browser;
	public static String EMAIL;
	public static String[] EmaiIds;
	
	public static WebDriver driver;
	//public static boolean adjustInventoryWorking = true;
	static WebElement wElement;
	public static String port;
	public static String udid;
	public static String deviceName;
	public static String platformVersion;
	public static String device;
	public static String apkFile;
	public static String ENV;
	public static Map<String, String> reportMailBody = new HashMap<String, String>();
	static public String sDirPath = System.getProperty("user.dir");

	// public static String sTestDataFile =
	// GenericLib.getValueFromConfigProperty(ENV,"sTestDataFile");
	public static String sTestDataFile = "";
//	public static String sConfigFile = GenericLib.getValueFromConfigProperty("config", "sConfigFile");

	public static Wait wait;
	static int sStatusCnt = 0;
	public static Properties prop = new Properties();
	public String videoPath;
	public String videoPath1;
	public DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
	public Date date = new Date();

//	public ATUTestRecorder recorder;

	public static SoftAssert Sassert;
	public static String browser = System.getProperty("browser");
	public static String env = System.getProperty("env");

	private Pattern pattern;
	private Matcher matcher;

	public static String Sheetname;

	public String actualPageTitle;

	public String sTestCaseName;

	public String sTestMethodName;

	public static String absolutePath = System.getProperty("user.dir"); 
	public static String Path;
	public static String ReportZipFolderPath;
	public static String destPath;

	public int iTestCaseRow;
	public static int startRow;

	public static ArrayList<Integer> testCaseRow = new ArrayList<Integer>();
	public static ArrayList<String> testCaseExecutionValue = new ArrayList<String>();

	public static ArrayList<String> ColumnNames = new ArrayList<String>();

	public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

	protected static ExtentReports extent;
	public static ExtentTest testInfo;
	private static ThreadLocal parentTest = new ThreadLocal();
	private static ThreadLocal test = new ThreadLocal();
	

	static {

		logger.info("Inside static class");

			String PathFromJenkins = System.getProperty("TestDataExcelPath");
			String WebUrlFromJenkins = System.getProperty("WebsiteUrl");
			String WebUsernameFromJenkins = System.getProperty("WebsiteUsername");
			String WebPasswordFromJenkins = System.getProperty("WebsitePassword");
			String FirefoxProfile = System.getProperty("FirefoxProfile");
			String RunningBrowser = System.getProperty("Browser");
			String SendEmail = System.getProperty("Email");
			String Emailids = System.getProperty("Emailids");
		  if (StringUtils.isEmpty(PathFromJenkins)) {
				Sheetname = "TestDataSheet";
				Path = "D:\\ABFRL\\croma\\TestData\\QAData\\ABFRLData\\ABFRLTestData.xlsx";
				WebUrl = "https://lms.com:Webteam!@uat1.centrepointstores.com/sa/en/";
				WebUsername = "u2ukumar@gmail.com";
				WebPassword = "Gurudatta@45";
				Profile  = "C:\\Users\\User\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\5vn3tfbb.default";
				Browser = "Chrome";
				logger.info("Running in Local");
				EMAIL = "N";
		  	}
		 
		  	else { 
		  		
		  	logger.info("Static class using browser to :" + browser);
			Sheetname = "TestDataSheet";
		  	Path=PathFromJenkins;
		  	WebUrl = WebUrlFromJenkins;
		  	WebUsername = WebUsernameFromJenkins;
		  	WebPassword = WebPasswordFromJenkins;
		  	Profile = FirefoxProfile;
		  	Browser = RunningBrowser;
		  	EMAIL = SendEmail;
		  	EmaiIds = Emailids.split(":");
		  
		  		logger.info("Running from Jenkins" +Path);
		  	logger.info("Static class Setting environment to :" + env);
		 
		  }
		 

	}

	@BeforeSuite
	public void beforeSuite() {
		//File dir = new File(absolutePath + "\\ExtentReport"); 
		
		Arrays.stream(new File(absolutePath + "\\ExtentReport\\").listFiles()).forEach(File::delete);
		
		extent = ExtentManager.createInstance("Extent.html");
		extent.setSystemInfo("OS", "Windows 10");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("UserName", "Premkumar Uppu");
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Extent.html");
		extent.attachReporter(htmlReporter);
	}

	// @Parameters({ "browser" })

	/* @BeforeClass */
	public static void setUp() throws IOException {
		setDriver(Browser);

	}

	public static void setDriver(String browser) {

		logger.info("Setting drivers for " + browser);

		switch (browser) {
		case "Chrome":
			initChromeDriver();
			break;
		case "Firefox":
			initFirefoxDriver();
			break;
		case "IE":
			initInternetExplorerDriver();
			break;
		default:
			logger.info("browser : " + browser + " is invalid, Launching Firefox as browser of choice..");
			initFirefoxDriver();
		}
	}

	private static void initInternetExplorerDriver() {

		// String service =
		// "D:\\ToolsQA\\trunk\\Library\\drivers\\IEDriverServer.exe";

		// System.setProperty("webdriver.chrome.driver", "IEDriverServer.exe");

		System.setProperty("webdriver.ie.driver", "C:\\Users\\User\\Downloads\\IEDriver\\IEDriverServer.exe");

		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieCapabilities.setCapability("nativeEvents", false);
		ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
		ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
		ieCapabilities.setCapability("disable-popup-blocking", true);
		ieCapabilities.setCapability("enablePersistentHover", true);
		ieCapabilities.setCapability("ignoreZoomSetting", true);
		ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		// InternetExplorerDriver driver = new
		// InternetExplorerDriver(ieCapabilities);

		driver = new InternetExplorerDriver(ieCapabilities);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		// Dimension screenResolution = new Dimension((int)
		// toolkit.getScreenSize().getWidth(), (int)
		// toolkit.getScreenSize().getHeight());

		Dimension screenResolution = new Dimension((int) 1920, (int) 1080);

		// logger.info("Screen resolution is " + screenResolution);
		// driver.manage().window().setSize(screenResolution);

		// driver.get("http://localhost:8888");

		driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
		driver.manage().window().maximize();
		/*
		 * driver.navigate().to(prop.getProperty("Store_URL"));
		 * driver.navigate().to(
		 * "javascript:document.getElementById('overridelink').click()");
		 */

	}

	private static void initFirefoxDriver() {System.setProperty("webdriver.gecko.driver", absolutePath+"\\GeckoDriver\\geckodriver.exe");
	/*FirefoxOptions option=new FirefoxOptions();
	option.addPreference("dom.webnotifications.enabled", false);
	driver = new FirefoxDriver(option);
	driver.manage().window().maximize();*/
	
	/*ProfilesIni prof = new ProfilesIni();
	FirefoxProfile myProfile = prof.getProfile("");
	
	driver = new FirefoxDriver(myProfile);*/
	
	
	
	
	FirefoxProfile profile =new FirefoxProfile(new File(Profile));
	profile.setPreference("permissions.default.desktop-notification", 1);
	
	FirefoxOptions option=new FirefoxOptions();
//	option.addPreference("dom.webnotifications.enabled", false);
	option.setProfile(profile);
	// Initialize Firefox driver
	
	DesiredCapabilities caps = DesiredCapabilities.firefox();
	caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
	caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, option);

	caps = caps.merge(DesiredCapabilities.firefox());
	driver = new FirefoxDriver(caps);
//	 driver.manage().window().maximize();
	
	
	
	//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//	driver.get(prop.getProperty("WCS_URL"));
	}

	private static void initChromeDriver() {
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			System.setProperty("webdriver.chrome.driver", absolutePath+"\\Chrome\\chromedriver1.exe");
		} else {
			System.setProperty("webdriver.chrome.driver", "");
		}
		ChromeOptions options = new ChromeOptions();

		options.addArguments("disable-extensions");
		options.addArguments("--start-maximized");
		options.addArguments("test-type");
		options.addArguments("enable-strict-powerful-feature-restrictions");
		options.addArguments("disable-geolocation");
		options.addArguments("--disable-notifications");

		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability(ChromeOptions.CAPABILITY, options);

		caps = caps.merge(DesiredCapabilities.chrome());

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		logPrefs.enable(LogType.DRIVER, Level.ALL);
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		driver = new ChromeDriver(caps);

		logger.info("Value " + prop.getProperty("Store_URL"));

		// driver.manage().window().maximize();

	}

	@BeforeMethod
	public synchronized void beforeMethod(Method caller) throws Exception {

		String methodname = caller.getName();
		// testInfo = extent.createTest("TestCase00");

		logger.info("Method name is : " + caller.getName());
		logger.info("Video path in Testbase is : " + videoPath1);
		// commented to record the video

		// recorder = new ATUTestRecorder(videoPath, caller.getName(), false);

		// recorder.start();
		logger.info("Call Before Method");

		Sassert = new SoftAssert();
		// ExcelUtils.setExcelFile("SalesOrder");

	}

	@AfterMethod
	public synchronized void afterMethod(Method caller, ITestResult result) throws Exception {

		// recorder.stop();
		logger.info("Call After Method");

		/*
		 * if (result.isSuccess()) {
		 * ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed"); }
		 *
		 * else if (result.getStatus() == ITestResult.FAILURE) {
		 * ExtentTestManager.getTest().log(LogStatus.FAIL, "Test failed");
		 * logger.info("Test Case Failed");
		 *
		 * String screenshot_path1 = utility.captureScreen(result.getName());
		 *
		 * String image1 =
		 * ExtentTestManager.getTest().addScreenCapture(screenshot_path1); //
		 * test.log(LogStatus.FAIL, result.getThrowable());
		 * ExtentTestManager.getTest().log(LogStatus.INFO, result.getTestName(),
		 * image1); logger.info("Error Message is " +
		 * result.getThrowable().getMessage());
		 * ExtentTestManager.getTest().log(LogStatus.FAIL, result.getTestName(),
		 * result.getThrowable().getMessage());
		 *
		 * } else if (result.getStatus() == ITestResult.SKIP) {
		 * ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped"); }
		 */

		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshot_path1 = Utility.captureScreen(result.getName());
			/*
			 * String methodName = result.getName(); String screenshotPath =
			 * utility.getScreenhot(result.getName());
			 */
			// testInfo.addScreenCaptureFromPath(screenshotPath);
			// testInfo.addScreencastFromPath( screenshot_path1);
			testInfo.log(Status.FAIL, "The Test Method named as : " + result.getName() + " is Failed"
					+ result.getThrowable().getMessage());
			testInfo.addScreenCaptureFromPath(screenshot_path1);

		} else if (result.getStatus() == ITestResult.SKIP)
			testInfo.log(Status.SKIP,
					"The Test Method named as : " + result.getName() + " is Skipped" + result.getThrowable());
		/*else
			testInfo.log(Status.PASS, "The Test Method named as : " + result.getName() + " is Passed");
*/
	}

	/*
	 * public static Response postRequest(String url, String body, String
	 * WCToken, String WCTrustedToken, String userId) { Response PostResponse =
	 * given().contentType(ContentType.JSON).header(new Header("WCToken",
	 * WCToken)) .header(new Header("WCTrustedToken",
	 * WCTrustedToken)).header(new Header("userId", userId)).body(body)
	 * .log().everything().expect().log().ifError().when().post(url); return
	 * PostResponse; }
	 */

	/*
	 * public static Response getRequest(String url, String WCToken, String
	 * WCTrustedToken, String userId) { Response getResponse =
	 * given().contentType(ContentType.JSON).header(new Header("WCToken",
	 * WCToken)) .header(new Header("WCTrustedToken",
	 * WCTrustedToken)).header(new Header("userId", userId)).log()
	 * .everything().expect().log().ifError().when().get(url); return
	 * getResponse; }
	 */
	public static void elementStatus(WebElement element, String elementName, String checkType)

	{
		switch (checkType) {
		case "displayed":
			try {
				element.isDisplayed();

			} catch (Exception e) {
				sStatusCnt++;

			}
			break;
		case "enabled":
			try {
				element.isEnabled();

			} catch (Exception e) {
				sStatusCnt++;

			}
			break;
		case "selected":
			try {
				element.isSelected();

			} catch (Exception e) {
				sStatusCnt++;

			}
			break;
		}
	}

	public void visibilityOfElementWait(WebElement webElement, String elementName) {
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(webElement));
		} catch (Exception e) {

		}
	}

	public static void IsListDisplayed(List lst, String lstName) {
		try {
			if (lst.size() > 0) {

			}
		} catch (Exception e) {

		}

	}

	public static void handleNavigation(WebElement ele) throws InterruptedException {
		while (!ele.isDisplayed()) {
			driver.navigate().back();
		}
	}

	public static void waitForElement(WebElement ele, String expResult, String actResult) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MICROSECONDS).ignoring(NoSuchElementException.class);
		Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(ele)) != null, actResult);

	}
	
	 public static void waitForWebElementAndClick(String xpath,int TimeValue) {
		 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		 .withTimeout(TimeValue, TimeUnit.SECONDS)
		 .pollingEvery(5, TimeUnit.SECONDS)
		 .ignoring(NoSuchElementException.class);
		 WebElement ele = wait.until(new Function<WebDriver, WebElement>(){

		 public WebElement apply(WebDriver driver ) {
		 return driver.findElement(By.xpath(xpath));
		 }
		 });

		 ele.click();

		 }
	 
	 public static void waitForWebElementAndType(String xpath,String TextValue,int TimeValue) {
		 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		 .withTimeout(TimeValue, TimeUnit.SECONDS)
		 .pollingEvery(5, TimeUnit.SECONDS)
		 .ignoring(NoSuchElementException.class);
		 WebElement ele = wait.until(new Function<WebDriver, WebElement>(){

		 public WebElement apply(WebDriver driver ) {
		 return driver.findElement(By.xpath(xpath));
		 }
		 });

		 ele.sendKeys(TextValue);

		 }
	 


	 
	 
	 
	 public static void waitForWebElementAndScroll(String xpath,int TimeValue) {
		 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		 .withTimeout(TimeValue, TimeUnit.SECONDS)
		 .pollingEvery(5, TimeUnit.SECONDS)
		 .ignoring(NoSuchElementException.class);
		 WebElement ele = wait.until(new Function<WebDriver, WebElement>(){

		 public WebElement apply(WebDriver driver ) {
		 return driver.findElement(By.xpath(xpath));
		 }
		 });

		 JavascriptExecutor js = ((JavascriptExecutor) driver);

			js.executeScript("arguments[0].scrollIntoView(true);", ele);

		 }

	 public static WebElement waitForWebElement(String xpath,int TimeValue) {
		 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		 .withTimeout(TimeValue, TimeUnit.SECONDS)
		 .pollingEvery(5, TimeUnit.SECONDS)
		 .ignoring(NoSuchElementException.class);
		 WebElement ele = wait.until(new Function<WebDriver, WebElement>(){

		 public WebElement apply(WebDriver driver ) {
		 return driver.findElement(By.xpath(xpath));
		
		 }
		 });

		 return ele;

		 }
	
	
	
	
	

	public static void isElementDisplayed(WebElement ele, String elementName, int pollingMiliSecs, int timoutSec,
			String assertionType) {

		boolean eleDispalyed = false;

		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timoutSec, TimeUnit.SECONDS)
					.pollingEvery(pollingMiliSecs, TimeUnit.MICROSECONDS).ignoring(NoSuchElementException.class);

			// Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(ele))
			// != null, actResult);

			wait.until(ExpectedConditions.visibilityOf(ele));

			if (ele.isDisplayed())
				;
			{

				eleDispalyed = true;

				testInfo.log(Status.PASS, elementName + " is displaying ");
			}
		}

		catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			logger.info(elementName + " is dispalyed " + eleDispalyed);
			// ExtentTestManager.getTest().log(LogStatus.INFO, elementName + "
			// is not displaying ");

			testInfo.log(Status.FAIL, elementName + " is not displaying");
			switch (assertionType) {
			case "HardAssertion":

				Assert.assertTrue(eleDispalyed, elementName + "is not dispalying " + sw.toString());
				break;
			case "SoftAssertion":

				Sassert.assertTrue(eleDispalyed, elementName + "is not dispalying " + sw.toString());
				break;

			}

		}

	}

	public static void isElementEnabled(final WebElement ele, String elementName, int pollingMiliSecs, int timoutSec,
			String assertionType) {

		boolean eleDispalyed = false;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.pollingEvery(250, TimeUnit.MILLISECONDS);
			wait.withTimeout(2, TimeUnit.SECONDS);
			wait.ignoring(NoSuchElementException.class); // We need to ignore
			// this
			// exception.

			Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver arg0) {
					logger.info("Checking for the object!!");
					boolean element = ele.isEnabled();
					if (!(element)) {
						logger.info("A new dynamic object is found.");
					}
					return ele;
				}
			};

			wait.until(function);

			eleDispalyed = ele.isEnabled();
		}

		catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			switch (assertionType) {
			case "HardAssertion":

				Assert.assertTrue(eleDispalyed, elementName + "is not dispalying " + sw.toString());
				break;
			case "SoftAssertion":

				Sassert.assertTrue(eleDispalyed, elementName + "is not dispalying " + sw.toString());
				break;

			}

		}
	}

	public static void waitAndClick(WebElement ele, String buttonName, int pollingMiliSecs, int timoutSec) {

		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timoutSec, TimeUnit.SECONDS)
					.pollingEvery(pollingMiliSecs, TimeUnit.MICROSECONDS).ignoring(NoSuchElementException.class);

			// Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(ele))
			// != null, actResult);

			Sassert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(ele)) != null,
					"User is able to click on " + buttonName);

			ele.click();

		}

		catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			Sassert.assertFalse(true, "User is not able to click on " + buttonName + sw.toString());

		}

	}

	public static WebElement getWebElement(String xPath, String elementName) {
		WebElement ele = null;
		;
		try {
			ele = driver.findElement(By.xpath(xPath));
			return ele;
		} catch (Exception e) {
			testInfo.log(Status.FAIL, "Selenium is not able to find WebElement" + elementName);
			logger.info(TestUtils.exceptionsToString(e));
			return ele;
		}

	}

	public static void waitAndEnterText(WebElement ele, String textBox, String textToEnter, int pollingMiliSecs,
			int timoutSec) {

		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timoutSec, TimeUnit.SECONDS)
					.pollingEvery(pollingMiliSecs, TimeUnit.MICROSECONDS).ignoring(NoSuchElementException.class);

			// Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(ele))
			// != null, actResult);

			Sassert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(ele)) != null,
					"Not able to find  " + textBox);

			ele.click();

		}

		catch (Exception e) {

		}

	}

	public void waitTillPageLoad() {

		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				logger.info("Waiting for page load conditon : waitTillPageLoad");
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(pageLoadCondition);
	}

	
	public void waitTillPageLoadwithTime() throws InterruptedException  {

		logger.info("Waiting for page load conditon : waitTillPageLoad");
		int time = 120000;
		while(time!=0)
		{

				boolean value =  ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				if(value)
				{
					testInfo.log(com.aventstack.extentreports.Status.INFO,
							"Javascript loaded properly" );
					time = 0;
					
				}
				else
				{

					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						testInfo.log(com.aventstack.extentreports.Status.FAIL,
								"We are waiting till page completes javascript internally " );
						throw(e);
					
					}
					testInfo.log(com.aventstack.extentreports.Status.INFO,
							"In Browser internally javascript is running so we are waiting 20 seconds more" );
					time = time - 20000;
				}
				
		}
		
		
	}
	
	
	public boolean CheckImage(WebElement ImageFile) throws Exception {

		// WebElement ImageFile =
		// driver.findElement(By.xpath("//img[contains(@id,'Test Image')]"));

		Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				ImageFile);
		if (!ImagePresent) {
			logger.info("Image not displayed.");
		} else {
			logger.info("Image displayed.");
		}
		return ImagePresent;
	}

	public boolean verifyVideo(WebElement video) throws Exception {

		// WebElement ImageFile =
		// driver.findElement(By.xpath("//img[contains(@id,'Test Image')]"));

		Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].play();",
				video);
		if (!ImagePresent) {
			logger.info("Video not displayed.");
		} else {
			logger.info("Video displayed.");
		}
		return ImagePresent;
	}

	public String splitString(String stringToSplit, char spiltChar) throws Exception {

		String str7Array[] = stringToSplit.split("\\" + spiltChar);

		int length = str7Array.length;

		String finalValue = str7Array[length - 1];

		logger.info("Image NAme is: " + finalValue);

		return finalValue;
	}

	public String splitandReturnTotalOrderNumber(String stringToSplit) throws Exception {

		logger.info("Total Number of Records String " + stringToSplit);

		String[] value = stringToSplit.split("\\(");
		String splitValue = value[1];
		String[] value2 = splitValue.split("\\)");
		String totalNumberofOrders = value2[0];

		return totalNumberofOrders;
	}

	public String splitStringByPosition(String stringToSplit, char spiltChar, int i) throws Exception {

		String str7Array[] = stringToSplit.split("\\" + spiltChar);

		int length = str7Array.length;

		String finalValue = str7Array[length - i];

		logger.info("Image NAme is: " + finalValue);

		return finalValue;
	}

	public String verifyImageExtension(String imageName) {

		String str7Array2[] = imageName.split("\\.");

		int length1 = str7Array2.length;

		String imageExtension = str7Array2[length1 - 1];

		logger.info("Image Extension is: " + imageExtension);

		return imageExtension;
	}

	public String splitStringByDot(String stringToSplit, char spiltChar, String containString) throws Exception {

		String str7Array[] = stringToSplit.split("\\."); // prints
		// [abc,
		// def, ghi]
		logger.info("String with | delimiter: " + Arrays.toString(str7Array));
		int length = Arrays.toString(str7Array).length();
		logger.info("Length is : " + length);
		String actualImageName = null;
		for (int k = 0; k < length; k++) {

			String iamgenames = str7Array[k];

			logger.info("Image Extension name is " + iamgenames);

			for (int j = length; j <= length; j--) {

			}

			if (iamgenames.contains(containString)) {
				actualImageName = iamgenames;
				logger.info("Actual Image Extension Name IS : " + actualImageName);
				break;
			}
		}
		return actualImageName;
	}

	public boolean validateImage(final String imageName) {
		logger.info("String Iamge Name is: " + imageName);
		pattern = Pattern.compile(IMAGE_PATTERN);
		matcher = pattern.matcher(imageName);
		return matcher.matches();

	}

	public String fetchTextFromWebElement(WebElement element, String Text) {

		String value = "Not able to fetch " + Text;
		try {

			value = element.getText();
			logger.info("Value is : " + value);
			if (StringUtils.isEmpty(value)) {
				return "Not able to fetch " + Text;
			}
			return value;
		}

		catch (Exception e) {

			String convertedString = exceptionsToString(e);
			return value + convertedString;
		}

	}

	public static String exceptionsToString(Exception e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

	public void mouseHover(WebElement element) {

		Actions builder = new Actions(driver);
		// builder.moveToElement(driver.findElement(By.xpath(".//*[contains(text(),'"+OrderNumber+"')]/ancestor::div[@uid='orderShipmentPanel']/following-sibling::div/span[3]"))).perform();

		builder.moveToElement(element).perform();
	}
	
	
	 public static void WaitingForWebElement (WebElement ele, String buttonName, int pollingMiliSecs, int timoutSec)
	 {

		 while(timoutSec!=0)
			try {

				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timoutSec, TimeUnit.SECONDS)
						.pollingEvery(pollingMiliSecs, TimeUnit.MICROSECONDS).ignoring(NoSuchElementException.class);

				// Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(ele))
				// != null, actResult);

				Sassert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(ele)) != null,
						"User is able to click on " + buttonName);

				ele.click();

			}

			catch (Exception e) {

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);

				Sassert.assertFalse(true, "User is not able to click on " + buttonName + sw.toString());

			}

		
		 
		 
		 
		 
	 }

	public boolean verifyPageTitle(String expectedPageTitle) {

		actualPageTitle = driver.getTitle();
		logger.info("Expected Page title is:" + expectedPageTitle);
		;
		logger.info("Actual Page title is:" + actualPageTitle);
		boolean pageTitelValue = actualPageTitle.equals(expectedPageTitle);
		logger.info("Page title value:" + pageTitelValue);
		return actualPageTitle.equals(expectedPageTitle);

	}

	public String fetchImageName(WebElement srcImage) throws Exception {

		String srcURL = srcImage.getAttribute("src");

		char splitchar = '/';

		String actualImageName = splitString(srcURL, splitchar);

		return actualImageName;

	}

	public void mousescrollTillEle(WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	
	
	public static String OMSAPICall(String baseURI, String userName, String passWord, String testCaseName,
			String xmlPath, String apiName, String isflowvalue, String expectedResultPath) {
		// TODO Auto-generated method stub
		
		String convertResponceToString = null;
		try{
		// logger.info("Executing " + this.getClass().getName());
		logger.info("Baseuri is " + baseURI);
		logger.info("User Name " + userName);
		logger.info("Password " + passWord);
		logger.info("Test case name is: " + testCaseName);
		logger.info("Input XML path is: " + xmlPath);
		logger.info("Api name is: " + apiName);
		logger.info("Is service ? : " + isflowvalue);
		logger.info("Expected result path " + expectedResultPath);

		RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
		RestAssured.baseURI = baseURI;
		String sName;
		String aName;
		String interopName;
		if (isflowvalue.equals("Y")) {
			sName = apiName;
			aName = "";
			interopName = apiName;
		} else {
			aName = apiName;
			sName = "";
			interopName = apiName;
		}

		String inputData = TestUtils.GenerateStrFromRes(xmlPath);
		System.out.println("CreateOrder XML" +inputData);
		String requestResult = TestUtils.convertStringtoFormattedXML(inputData);

		testInfo.log(Status.INFO, "API/Service Name: " + apiName);

		testInfo.log(Status.INFO, "API/Service Request XML : " + requestResult);
		Response respcode = given().

				queryParam("YFSEnvironment.progId", "SterlingHttpTester").queryParam("InteropApiName", interopName)
				.queryParam("IsFlow", isflowvalue)
				.queryParam("ServiceName", sName).queryParam("ApiName", aName)
				.queryParam("YFSEnvironment.userId", userName).queryParam("YFSEnvironment.password", passWord)
				.queryParam("YFSEnvironment.version", "").queryParam("YFSEnvironment.locale", "")
				.queryParam("InteropApiData", inputData).when().post("/smcfs/servlets/IBMApiTesterServlet");
		
		// .contentType(ContentType.XML).extract().response();

		// .assertThat().statusCode(200).and().contentType(ContentType.XML).extract().response();
		int statuscode = respcode.getStatusCode();

 		if (statuscode != 200) {
			testInfo.log(Status.FAIL, "Status code is wrong");
		} else {
			testInfo.log(Status.PASS, " " + apiName + "Response Code is: " + statuscode);
		}

		// Sassert.assertEquals(statuscode, "200");
		// testInfo.log(Status.INFO, "Response Status Code : " + statuscode);
 		
		Response resp = respcode.then().assertThat().and().contentType("text/xml").header("Accept", "text/xml").extract().response();
		String finalResult = TestUtils.convertStringtoFormattedXML(resp.asString());

		// TestUtils.convertStringToDocument("D:\\Automation\\Automation_GitProjects\\myframework\\joannwebapi\\TestDataXMLs\\ResponseXml\\CreateOrderRes.xml",
		// result);

		 convertResponceToString = resp.asString();

		String rootName = TestUtils.getXmlRootName(convertResponceToString);
		if (rootName.equals("Errors")) {
			testInfo.log(Status.FAIL, " " + apiName + "    API is giving failure response          " +convertResponceToString);
			
			
			//Utility.emailNotification();
			throw new Exception();

		} else {
			testInfo.log(Status.PASS, " " + apiName + " API response is success");
		}
		
		
		if(apiName.equals("getOrderDetails")||apiName.equals("getCompleteOrderDetails") || apiName.equals("getOrderList") )
		{
			logger.info("Input XML path is: " + apiName);

		}
		else
		{
			testInfo.log(Status.INFO, "Response is : " + finalResult);
		}
		

		return convertResponceToString;
		
		}
		catch(Exception e)
		{
			testInfo.log(Status.FAIL, "    Api Response is showing error,So we are not executing remaining Scenario ");
			
			//MailProjectClass.acuverEmailNotification("premkumar.uppu@acuverconsulting.com", "premkumar.uppu@acuverconsulting.com", "Gurudatta@222321");

			if(apiName.equals("adjustInventory")|| apiName.equals("getShipNodeInventory"))
			{
				
				try {
					DataProviderSelenium.close();
					driver.get(WebUrl);
			
					driver.quit();
					tearDownAfterSuite();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
			return convertResponceToString;
		}

		
	}
	
	public static String RMSAPICall( String ItemID, String Quantity) {
		// TODO Auto-generated method stub

		// logger.info("Executing " + this.getClass().getName());
		
		logger.info("ItemID is " + ItemID);
		logger.info("Quantity is " +Quantity);

		RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
		RestAssured.baseURI = "http://10.180.66.27:8050";
	

		

		//testInfo.log(Status.INFO, "API Request : " + requestResult);
		Response respcode = given().
				 body("{\r\n  \"item_id\": \""+ItemID+"\",\r\n  \"location\": \"vwh\",\r\n  \"qty\": "+Quantity+"\r\n}\r\n").
				 when().post("/lmg/stock-update/rms").then().
				 assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
		
		
		int statuscode = respcode.getStatusCode();

		if (statuscode != 200) {
			testInfo.log(Status.FAIL, "Status code is wrong");
		} else {
			testInfo.log(Status.PASS,  "Response Code is: " + statuscode);
		}

		// Sassert.assertEquals(statuscode, "200");
		// testInfo.log(Status.INFO, "Response Status Code : " + statuscode);

		Response resp = respcode.then().assertThat().and().contentType(ContentType.XML).extract().response();
		String finalResult = TestUtils.convertStringtoFormattedXML(resp.asString());

		// TestUtils.convertStringToDocument("D:\\Automation\\Automation_GitProjects\\myframework\\joannwebapi\\TestDataXMLs\\ResponseXml\\CreateOrderRes.xml",
		// result);

		String convertResponceToString = resp.asString();

		String rootName = TestUtils.getXmlRootName(convertResponceToString);
		if (rootName.equals("Errors")) {
			Sassert.assertFalse(true, "API response is showing error");
			// Assert.assertFalse(true, "API response is showing error");
			testInfo.log(Status.FAIL, "API response is showing error");

		} else {
			testInfo.log(Status.PASS,  " API response is success");
		}

		

		return convertResponceToString;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static String OMSAPICallwithOutputTemplate(String baseURI, String userName, String passWord,
			 String xmlPath, String apiName, String isflowvalue ,
			String OutputXmlPath) {
		// TODO Auto-generated method stub

		String convertResponceToString = null;
		try
		{
		// logger.info("Executing " + this.getClass().getName());
		logger.info("Baseuri is " + baseURI);
		logger.info("User Name " + userName);
		logger.info("Password " + passWord);
		
		logger.info("Input XML path is: " + xmlPath);
		logger.info("Api name is: " + apiName);
		logger.info("Is service ? : " + isflowvalue);

		

	//	RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
		RestAssured.baseURI = baseURI;
		String sName;
		String aName;
		String interopName;
		if (isflowvalue.equals("Y")) {
			sName = apiName;
			aName = "";
			interopName = apiName;
			testInfo.log(Status.INFO, "Service Name : " + apiName);

		} else {
			aName = apiName;
			sName = "";
			interopName = apiName;
			testInfo.log(Status.INFO, "API Name : " + apiName);

		}

		String inputData = TestUtils.GenerateStrFromRes(xmlPath);
		String requestResult = TestUtils.convertStringtoFormattedXML(inputData);

		String outputData = TestUtils.GenerateStrFromRes(OutputXmlPath);
		String outputTemplate = TestUtils.convertStringtoFormattedXML(outputData);

		testInfo.log(Status.INFO, apiName  + " Request : ");
		testInfo.log(Status.INFO, requestResult);

		Response respcode = given().

				queryParam("YFSEnvironment.progId", "SterlingHttpTester").queryParam("InteropApiName", interopName)
				.queryParam("IsFlow", isflowvalue).queryParam("InvokeFlow", "InvokeFlow")
				.queryParam("ServiceName", sName).queryParam("ApiName", aName)
				.queryParam("YFSEnvironment.userId", userName).queryParam("YFSEnvironment.password", passWord)
				.queryParam("YFSEnvironment.version", "").queryParam("YFSEnvironment.locale", "")
				.queryParam("InteropApiData", inputData).queryParam("TemplateData", outputData).when()
				.post("/smcfs/interop/InteropHttpServlet");

		// .contentType(ContentType.XML).extract().response();

		// .assertThat().statusCode(200).and().contentType(ContentType.XML).extract().response();
		int statuscode = respcode.getStatusCode();

		if (statuscode != 200) {
			testInfo.log(Status.FAIL, "Status code is wrong");
		} else {
			testInfo.log(Status.PASS, " " + apiName + " Response Code is: " + statuscode);
		}

		// Sassert.assertEquals(statuscode, "200");
		// testInfo.log(Status.INFO, "Response Status Code : " + statuscode);

		Response resp = respcode.then().assertThat().and().contentType(ContentType.XML).extract().response();
		String finalResult = TestUtils.convertStringtoFormattedXML(resp.asString());

		// TestUtils.convertStringToDocument("D:\\Automation\\Automation_GitProjects\\myframework\\joannwebapi\\TestDataXMLs\\ResponseXml\\CreateOrderRes.xml",
		// result);

		 convertResponceToString = resp.asString();

		String rootName = TestUtils.getXmlRootName(convertResponceToString);
		if (rootName.equals("Errors")) {
			Sassert.assertFalse(true, "API response is showing error");
			// Assert.assertFalse(true, "API response is showing error");
			testInfo.log(Status.FAIL, "" + apiName + "API response is showing error");

		} else {
			testInfo.log(Status.PASS, " " + apiName + " API response is success");
		}

		if(apiName.equals("getOrderDetails")||apiName.equals("getCompleteOrderDetails")||apiName.equals("getOrderList"))
		{
			logger.info("Input XML path is: " + apiName);

		}else
		{
			testInfo.log(Status.INFO, "Response is : " + finalResult);
		}
		return convertResponceToString;
		
		}
		catch(Exception e)
		{
			testInfo.log(Status.INFO, "Api Response is showing error,so we are not continuing this scenario" );
			return convertResponceToString;
		}

	}

	public String APICall(String baseURI, String userName, String passWord, String testCaseName, String xmlPath,
			String apiName, String isflowvalue, String inputDataPath, String expectedResultPath) {
		// TODO Auto-generated method stub

		logger.info("Executing " + this.getClass().getName());
		logger.info("Baseuri is " + baseURI);
		logger.info("User Name " + userName);
		logger.info("Password " + passWord);
		logger.info("Test case name is: " + testCaseName);
		logger.info("Input XML path is: " + xmlPath);
		logger.info("Api name is: " + apiName);
		logger.info("Is service ? : " + isflowvalue);
		logger.info("Input data path " + inputDataPath);
		logger.info("Expected result path " + expectedResultPath);

		testInfo = extent.createTest(testCaseName);
		testInfo.log(Status.INFO, "Call API " + apiName);

		RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().allowAllHostnames());
		RestAssured.baseURI = baseURI;
		String sName;
		String aName;
		String interopName;
		if (isflowvalue.equals("Y")) {
			sName = apiName;
			aName = "";
			interopName = apiName;
		} else {
			aName = apiName;
			sName = "";
			interopName = apiName;
		}

		String inputData = TestUtils.GenerateStrFromRes(xmlPath);
		String requestResult = TestUtils.convertStringtoFormattedXML(inputData);

		testInfo.log(Status.INFO, "API Request : " + requestResult);
		Response respcode = given().

				queryParam("YFSEnvironment.progId", "SterlingHttpTester").queryParam("InteropApiName", interopName)
				.queryParam("IsFlow", isflowvalue).queryParam("InvokeFlow", "InvokeFlow")
				.queryParam("ServiceName", sName).queryParam("ApiName", aName)
				.queryParam("YFSEnvironment.userId", userName).queryParam("YFSEnvironment.password", passWord)
				.queryParam("YFSEnvironment.version", "").queryParam("YFSEnvironment.locale", "")
				.queryParam("InteropApiData", inputData).when().post("/smcfs/interop/InteropHttpServlet");

		// .contentType(ContentType.XML).extract().response();

		// .assertThat().statusCode(200).and().contentType(ContentType.XML).extract().response();
		int statuscode = respcode.getStatusCode();

		if (statuscode != 200) {
			testInfo.log(Status.FAIL, "Status code is wrong");
		} else {
			testInfo.log(Status.PASS, " " + apiName + "Response Code is: " + statuscode);
		}

		// Sassert.assertEquals(statuscode, "200");
		// testInfo.log(Status.INFO, "Response Status Code : " + statuscode);

		Response resp = respcode.then().assertThat().and().contentType(ContentType.XML).extract().response();
		String finalResult = TestUtils.convertStringtoFormattedXML(resp.asString());

		// TestUtils.convertStringToDocument("D:\\Automation\\Automation_GitProjects\\myframework\\joannwebapi\\TestDataXMLs\\ResponseXml\\CreateOrderRes.xml",
		// result);

		String convertResponceToString = resp.asString();

		String rootName = TestUtils.getXmlRootName(convertResponceToString);
		if (rootName.equals("Errors")) {
			Sassert.assertFalse(true, "API response is showing error");
			// Assert.assertFalse(true, "API response is showing error");
			testInfo.log(Status.FAIL, "" + apiName + "API response is showing error");

		} else {
			
			testInfo.log(Status.PASS, " " + apiName + " API response is success");
		}

		if(apiName.equals("getOrderDetails")||apiName.equals("getCompleteOrderDetails"))
		{
			logger.info("Input XML path is: " + apiName);

		}else
		{
			testInfo.log(Status.INFO, "Response is : " + finalResult);
		}
		return convertResponceToString;

	}

	@AfterClass
	public void tearDown() {
		// driver.quit();
	}

	@DataProvider(name = "data-Provider")

	public Object[][] Authentication(Method name) throws Exception {

		// Setting up the Test Data Excel file

		Sheetname = "MaxKsaTestData";
		Path = "D:\\LandMarkAutomation\\landmark\\TestData\\QAData\\MaxKsa\\KsaMaxSourcingRuleTestData.xlsx";

		DataProviderSelenium.setExcelFile(Path, Sheetname);

		logger.info("Sheet Name is " + Sheetname);
		sTestCaseName = this.toString();

		sTestMethodName = name.getName();

		logger.info("Method name is : " + sTestMethodName);

		// From above method we get long test case name including package and
		// class name etc.

		// The below method will refine your test case name, exactly the name
		// use have used

		sTestCaseName = DataProviderSelenium.getTestCaseName(this.toString());

		// sTestMethodName = ExcelUtils1.getTestCaseName(sTestMethodName);

		// Fetching the Test Case row number from the Test Data Sheet

		// Getting the Test Case name to get the TestCase row from the Test Data
		// Excel sheet

		iTestCaseRow = DataProviderSelenium.getRowContains(sTestMethodName, 0);

		int iTestCaseColumn = DataProviderSelenium.getColUsed(startRow);

		Object[][] testObjArray = DataProviderSelenium.getTableArray1(Path, Sheetname, iTestCaseRow, startRow,
				iTestCaseColumn);

		return (testObjArray);

	}

	@AfterSuite(alwaysRun = true)
	public static void tearDownAfterSuite() throws Exception {

		logger.info("After suite is running");

		extent.flush();
		// ExtentManager.getInstance().flush();
	/*	File src = new File(Path);
		File dest = new File(destPath);
		TestUtils.copy(src,dest);
		*/
	if(EMAIL.equals("Y"))
	{
		ZipUtils.createReportZipFolder();
		try {
			
			
			JavaEmail.sendReport();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	}

}