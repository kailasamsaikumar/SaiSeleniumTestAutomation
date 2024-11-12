package com.inspify.utilities;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.log.Log;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseSetup extends TestngListener implements TimeOuts {
	public WebDriver driver;
	static String fileSeperator = System.getProperty("file.separator");


	/*
	 * WebDriver Setup method which helps to launch respective browser for test per
	 * given parameters
	 * 
	 * @param BrowserName - Browser name
	 * 
	 * @param BrowserVersion - Browser version
	 * 
	 * @param OSPlatform - OS platform
	 * 
	 * @param Environment - Execution environment i.e. local or grid
	 */
	@Parameters("BrowserName")
	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("chrome") String BrowserName, ITestContext context, Method method) {
		try {
			// For launching Firefox browser
			if (BrowserName.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + fileSeperator
						+ "resources" + fileSeperator + "geckodriver.exe");
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				// FirefoxOptions
				firefoxProfile.setPreference("app.update.enabled", false);
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.download.dir", getDownloadLocation());
				firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
						"application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
				firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
				// firefoxProfile.setPreference("browser.download.forbid_open_with", true);
				FirefoxOptions fp = new FirefoxOptions();
				fp.setProfile(firefoxProfile);
				driver = new FirefoxDriver(fp);
				Reporter.log(
						"--------------------------- Launching Firefox Browser on Local machine --------------------------- ");
				System.out.println(
						"---------------------------  Launching Firefox Browser on Local machine --------------------------- ");
			}

			// for launching Google Chrome browser
			 if (BrowserName.equalsIgnoreCase("chrome")) {
				String operSys = System.getProperty("os.name").toLowerCase();
				String methodName = method.getName();

				 if(methodName.contains("40539")){
					 if (operSys.contains("win")) {
						 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + fileSeperator
								 + "resources" + fileSeperator + "chromedriver.exe");
					 }
					 if (operSys.contains("mac")) {
						 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + fileSeperator
								 + "resources" + fileSeperator + "chromedriver");
					 }
					 if (operSys.contains("linux")) {
						 System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
					 }
					 ChromeOptions options = new ChromeOptions();

					 Map<String, Object> prefs = new HashMap<>();
					 prefs.put("download.default_directory", getDownloadLocation());
					 prefs.put("download.prompt_for_download", false);
					 prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
					 prefs.put("profile.default_content_setting_values.media_stream_camera", 2);
//			    	prefs.put("profile.default_content_setting_values.geolocation", 1);
					 prefs.put("profile.default_content_setting_values.notifications", 1);
					 options.setExperimentalOption("prefs", prefs);
					 // options.setBinary("C:\\Desktop\\DART\\dart\\chromium\\chrome.exe");

					 if (operSys.contains("linux")) {
						 options.addArguments("--no-sandbox");
						 options.addArguments("--headless");
						 options.addArguments("--disable-dev-shm-usage");
					 }

//					 String nodeURL = "http://52.220.220.186:4444/wd/hub";
					 options.addArguments(
							 "--use-fake-device-for-media-stream");
					 driver = new ChromeDriver(options);
//					 driver = new RemoteWebDriver(new URL(nodeURL), options.setExperimentalOption("prefs", prefs));

				 }
				 else {
					 operSys = System.getProperty("os.name").toLowerCase();
					 if (operSys.contains("win")) {
						 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + fileSeperator
								 + "resources" + fileSeperator + "chromedriver.exe");
					 }
					 if (operSys.contains("mac")) {
						 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + fileSeperator
								 + "resources" + fileSeperator + "chromedriver");
					 }
					 if (operSys.contains("linux")) {
						 System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
					 }
					 ChromeOptions options = new ChromeOptions();

					 Map<String, Object> prefs = new HashMap<>();
					 prefs.put("download.default_directory", getDownloadLocation());
					 prefs.put("download.prompt_for_download", false);
					 prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
					 prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
					 //			    prefs.put("profile.default_content_setting_values.geolocation", 1);
					 prefs.put("profile.default_content_setting_values.notifications", 1);
					 options.setExperimentalOption("prefs", prefs);
					 // options.setBinary("C:\\Desktop\\DART\\dart\\chromium\\chrome.exe");

					 LoggingPreferences logPrefs = new LoggingPreferences();
					 logPrefs.enable(LogType.BROWSER, Level.ALL);
					 options.setCapability("goog:loggingPrefs", logPrefs);

					 if (operSys.contains("linux")) {
						 options.addArguments("--no-sandbox");
						 options.addArguments("--headless");
						 options.addArguments("--disable-dev-shm-usage");
					 }

					 				options.addArguments("--start-maximized");
					 				options.addArguments("--test-type");
					  options.addArguments("--allow-file-access-from-files",
					          "--use-fake-ui-for-media-stream",
					          "--allow-file-access",
					          "--use-file-for-fake-audio-capture="+getAudioFilePath1(),
					          "--use-file-for-fake-video-capture="+getVideoFilePath1(),
					          "--use-fake-device-for-media-stream");

					 driver = new ChromeDriver(options);

//					 String nodeURL = "http://52.220.220.186:4444/wd/hub";
//					 options.addArguments("--use-fake-ui-for-media-stream",
//							 "--use-fake-device-for-media-stream");
//					 driver = new RemoteWebDriver(new URL(nodeURL), options);

				 }
				 Reporter.log(
						 "--------------------------- Launching Chrome Browser on Local machine -----------------------");
				 System.out.println(
						 "--------------------------- Launching Chrome Browser on Local machine --------------------------- ");
			 } else if (BrowserName.equalsIgnoreCase("InternetExplorer")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + fileSeperator + "resources" + fileSeperator + "iedriver.exe");
				driver = new InternetExplorerDriver();
				Reporter.log(
						"--------------------------- Launching Internet Explorer on Local machine -----------------------");
				System.out.println(
						"--------------------------- Launching Internet Explorer on Local machine --------------------------- ");
			}
			context.setAttribute("driver", driver);

			Dimension dm = new Dimension(1920,1080);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITWAIT));
			driver.manage().window().setSize(dm);
			driver.manage().window().maximize();
			System.out.println("from browser class:" + driver);

		} catch (Exception e) {
			Assert.fail("Exception occurred while invoking browser, Exception message:" + e.getMessage());
		}
		//return driver;
	}

	/*
	 * WebDriver tear down method which closes browser after each test
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			Assert.fail("Exception occurred while closing browser instance, exception message:" + e.getMessage());
		}
	}

	public void invokeNewTabForClient() { driver.switchTo().newWindow(WindowType.TAB);	}

	public WebDriver getBrowser() {
		return driver;
	}

	public WebDriver switchBetweenTabs() {
		Set<String> tabs = driver.getWindowHandles();
		String advisorTab = (String) tabs.toArray()[0];
		return driver.switchTo().window(advisorTab);
	}


	public static String getDownloadLocation() {
		return System.getProperty("user.dir") + fileSeperator + "DownloadedFiles";
	}
	
	public static String getAudioFilePath1() {
		return System.getProperty("user.dir") + fileSeperator + "AudioAndVideoFiles"+ fileSeperator + "StarWars60.wav";
	}
	
	public static String getVideoFilePath1() {
		return System.getProperty("user.dir") + fileSeperator + "AudioAndVideoFiles"+ fileSeperator + "sample_640x360.mjpeg";
	}
	
	public static String getAudioFilePath2() {
		return System.getProperty("user.dir") + fileSeperator + "AudioAndVideoFiles"+ fileSeperator + "file_example_WAV_1MG.wav";
	}
	
	public static String getVideoFilePath2() {
		return System.getProperty("user.dir") + fileSeperator + "AudioAndVideoFiles"+ fileSeperator + "sample_960x400_ocean_with_audio.mjpeg";
	}

}
