package com.inspify.testSuite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;
@Listeners(com.inspify.utilities.TestngListener.class)

public class ViewAngleTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod(alwaysRun = true)
	public void initialize() throws Exception {
		getBrowser().manage().deleteAllCookies();
		
	}
	
	@Test(groups = {"SmokeTest"})
	public void TC_C6375_ViewSpecifiedAngle() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4); 
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);

		Double HorizontalLookAt = Double.parseDouble(app.getProperty("TC_C6375_HLookAt"));
		Double VerticalLookAt = Double.parseDouble(app.getProperty("TC_C6375_VLookAt"));
		Double FieldOfView = Double.parseDouble(app.getProperty("TC_C6375_FOV"));
		
		getBrowser().get(sAdvisorUrl);
		advisorPage.verifyThatStartSessionButtonIsDisplayed();
		advisorPage.enterAdvisorName(advisorname);
		advisorPage.clickOnStartSessionButton();
		advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
	  
		invokeNewTabForClient();
	  	getBrowser().get(sClientUrl);
	  	clientPage.verifyThatClientNoteSectionIsDisplayed();
	  	clientPage.verifyThatJoinNowButtonIsDisplayed();
	  	String clientName = clientPage.enterClientName(clientname);
	  	clientPage.clickOnJoinNowButton();
		clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);
	  	
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(clientName);
	  	advisorPage.clickOnAdmitButton();
		advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(clientName);
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatClientHasJoinedMeeting(clientname);
		 
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.moveToCertainAngle(HorizontalLookAt, VerticalLookAt, FieldOfView);
	  	Long hLookAt = advisorPage.getHorizontalAngle();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatSameHorizontalAngleIsInView(hLookAt);
		
	  	advisorPage.switchToAdvisorTab();
	  	Long vLookAt = advisorPage.getVerticalAngle();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatSameVerticalAngleIsInView(vLookAt);
	  	
	  	advisorPage.switchToAdvisorTab();
	  	Long fov = advisorPage.getFovAngle();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatSameFieldOfViewAngleIsInView(fov);

	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.endMeeting(); 
	  	advisorPage.verifyThatMeetingEnded();
	  	clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
		
	  }

}
