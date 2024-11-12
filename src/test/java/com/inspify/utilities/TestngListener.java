package com.inspify.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.gurock.testrail.APIException;
import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.CommandExecutor;

public class TestngListener extends TestListenerAdapter {
	private static ExtentReports extent = ExtentManager.getInstance();
	// private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentTest test;
	AdvisorPage advisorPage;
	ClientPage clientPage;

	private static char cQuote = '"';
	ConfigManager sys = new ConfigManager();
	private static String fileSeperator = System.getProperty("file.separator");

	//Logger log = Logger.getLogger(TestngListener.class.getName());
	Logger log = Logger.getLogger("TestListener");
	
	public static final int TEST_CASE_PASSED_STATUS = 1;
	public static final int TEST_CASE_FAILED_STATUS = 5;
	public static final int TEST_CASE_SKIPPED_STATUS = 7;

	/**
	 * This method will be called if a test case is failed. Purpose - For attaching
	 * captured screenshots and videos in ReportNG report
	 */
	public synchronized void onTestFailure(ITestResult result) {
		String methodName = result.getName();
        String[] arrOfMethodName = methodName.split("_");
        String currentTestCaseId = arrOfMethodName[1].replaceAll("[^0-9]", "");
        		
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		log.error("ERROR ----------" + result.getName() + " has failed-----------------");
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		ITestContext context = result.getTestContext();
		WebDriver driver1 = (WebDriver) context.getAttribute("driver");
		//WebDriver driver2 = (WebDriver) context.getAttribute("driver2");
		// String imagepath =
		// ExtentManager.getReportsScreenShotDirectoryPath()+fileSeperator +
		// saveScreenShot(driver);
		int numWindow = driver1.getWindowHandles().size();
		System.out.println("window size"+numWindow);
		
		for(int num = 0; num< numWindow; num++) {
			if(num == 0) {
				advisorPage = new AdvisorPage(driver1);
				advisorPage.switchToAdvisorTab();
				String screenShotName = currentTestCaseId + "_Advisor" + ".png";
				String imagepath1 = "Screenshots" + fileSeperator + saveScreenShot(driver1, screenShotName);
				try {
					test.fail(result.getThrowable()).addScreenCaptureFromPath(imagepath1);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to update fail status or screenshot to report:" + e.getMessage());
				}
			}
			if(num == 1) {
				clientPage = new ClientPage(driver1);
				clientPage.switchToClientTab(num);
				String screenShotName = currentTestCaseId + "_Client1" + ".png";
				String imagepath1 = "Screenshots" + fileSeperator + saveScreenShot(driver1, screenShotName);
				try {
					test.fail(result.getThrowable()).addScreenCaptureFromPath(imagepath1);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to update fail status or screenshot to report:" + e.getMessage());
				}
			}
			if(num == 2) {
				clientPage = new ClientPage(driver1);
				clientPage.switchToClientTab(num);
				String screenShotName = currentTestCaseId + "_Client2" + ".png";
				String imagepath1 = "Screenshots" + fileSeperator + saveScreenShot(driver1, screenShotName);
				try {
					test.fail(result.getThrowable()).addScreenCaptureFromPath(imagepath1);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to update fail status or screenshot to report:" + e.getMessage());
				}
			}
			if(num == 3) {
				clientPage = new ClientPage(driver1);
				clientPage.switchToClientTab(num);
				String screenShotName = currentTestCaseId + "_Client3" + ".png";
				String imagepath1 = "Screenshots" + fileSeperator + saveScreenShot(driver1, screenShotName);
				try {
					test.fail(result.getThrowable()).addScreenCaptureFromPath(imagepath1);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to update fail status or screenshot to report:" + e.getMessage());
				}
			}
		}
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		String failedMessage = result.getThrowable().toString();
		
		try {
			TestrailIntegration.addResultForTestCase(currentTestCaseId, TEST_CASE_FAILED_STATUS, failedMessage);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		} 
		catch (APIException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method will be called if a test case is skipped.
	 * 
	 */
	public synchronized void onTestSkipped(ITestResult result) {
		String methodName = result.getName();
        String[] arrOfMethodName = methodName.split("_");
        String currentTestCaseId = arrOfMethodName[1].replaceAll("[^0-9]", "");
        		
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log.warn("WARN ------------" + result.getName() + " has skipped-----------------");
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		// ************* comment below code if you are using TestNG dependency methods

		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.skip(result.getThrowable());
		String skippedMessage = result.getThrowable().toString();
		
				try {
					TestrailIntegration.addResultForTestCase(currentTestCaseId, TEST_CASE_SKIPPED_STATUS, skippedMessage);
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				} 
				catch (APIException e1) {
					e1.printStackTrace();
				}
				//throw new SkipException("Skipping - This is not ready for testing ");
	}

	/**
	 * This method will be called if a test case is passed. Purpose - For attaching
	 * captured videos in ReportNG report
	 */
	public synchronized void onTestSuccess(ITestResult result) {
		String methodName = result.getName();
        String[] arrOfMethodName = methodName.split("_");
        String currentTestCaseId = arrOfMethodName[1].replaceAll("[^0-9]", "");
               
		log.info("###############################################################");
		log.info("SUCCESS ---------" + result.getName() + " has passed-----------------");
		log.info("###############################################################");
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.pass("Test passed");
		String passedMessage = "Test Executed and Passed - Status updated automatically from Selenium test automation. CaseID: "+currentTestCaseId+"";
		
		try {
			TestrailIntegration.addResultForTestCase(currentTestCaseId, TEST_CASE_PASSED_STATUS, passedMessage);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		} 
		catch (APIException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method will be called before a test case is executed. Purpose - For
	 * starting video capture and launching balloon popup in ReportNG report
	 */
	public synchronized void onTestStart(ITestResult result) {
		log.info("<h2>**************CURRENTLY RUNNING TEST************ " + result.getName() + "</h2>");
		System.out.println((result.getMethod().getMethodName() + " started!"));
		// ExtentTest extentTest =
		// extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		// test.set(extentTest);
		test = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
	}

	public synchronized void onStart(ITestContext context) {
		try {
			File targetFile = new File(ExtentManager.getReportsScreenShotDirectoryPath(), "video.mp4");
			log.info("<h2>**************STARTING SCREEN RECORDING************</h2>");
			String command = "export DISPLAY=:44";
			CommandExecutor.execute(command);

			command = "tmux new-session -d -s voyagerecording \"ffmpeg -f x11grab -video_size 1920x1080 -i :44 -codec:v libx264 -r 12 " +
					targetFile.toPath() + "\"";
			CommandExecutor.execute(command);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized void onFinish(ITestContext context) {
		extent.flush();
		String zipFileName = System.getProperty("user.dir") + fileSeperator + "TestReports" + fileSeperator
				+ "LatestAutomationReport.zip";
		File f = new File(zipFileName);
		if (f.exists()) {
			f.delete();
		}
		try {
			String command = "tmux send-keys -t voyagerecording q";
			CommandExecutor.execute(command);

			zipLatestAutomationReportFolder(zipFileName);
		} catch (Exception e) {
			System.out.println("Exception message while zipping the file");
		}

	}

	public String saveScreenShot(WebDriver driver, String screenShotName) {
		try {
			log.info("Saving screenshot of current browser window");
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File(ExtentManager.getReportsScreenShotDirectoryPath(), screenShotName);
			// FileUtils.copyFile(screenshotFile,targetFile );
			// String path = targetFile.getAbsolutePath();
			Files.copy(screenshotFile.toPath(), targetFile.toPath());
			log.info("Screenshot created and : " + screenShotName);
			return screenShotName;
		} catch (Exception e) {
			log.error("An exception occured while saving screenshot of current browser window.." + e.getMessage());
			return null;
		}
	}

	public void zipLatestAutomationReportFolder(String zipFileName) throws Exception {
		String folderToZip = ExtentManager.getReportsDirectoryPath();
		zipFolder(Paths.get(folderToZip), Paths.get(zipFileName));
	}

	private void zipFolder(final Path sourceFolderPath, final Path zipPath) throws Exception {
		final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
		Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
				Files.copy(file, zos);
				zos.closeEntry();
				return FileVisitResult.CONTINUE;
			}
		});
		zos.close();
	}

	public ExtentTest getExtentTestInstance() {
		return test;
	}

}
