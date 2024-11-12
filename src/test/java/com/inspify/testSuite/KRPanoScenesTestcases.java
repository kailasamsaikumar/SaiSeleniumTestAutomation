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

public class KRPanoScenesTestcases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();	
	}
	
	@Test(groups = {"SmokeTest"})
	public void TC_C6371_ViewSpecifiedScenes() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);
		String noOfscenes = app.getProperty("TC_C6371_NoOfScenesToBeClicked");
	
		getBrowser().get(sAdvisorUrl);
		advisorPage.verifyThatStartSessionButtonIsDisplayed();
		advisorPage.enterAdvisorName(advisorname);
		advisorPage.clickOnStartSessionButton();
		advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);

		invokeNewTabForClient();
	  	getBrowser().get(sClientUrl);
	  	clientPage.verifyThatClientNoteSectionIsDisplayed();
	  	clientPage.verifyThatJoinNowButtonIsDisplayed();
	  	clientPage.enterClientName(clientname);
	  	clientPage.clickOnJoinNowButton();
		clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);
	  	
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(clientname);
	  	advisorPage.clickOnAdmitButton();
		advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(clientname);
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatClientHasJoinedMeeting(clientname);
		
		int scenesToBeClicked = Integer.parseInt(noOfscenes);
		int i = 2;
		while (i<=scenesToBeClicked) {
			advisorPage.switchToAdvisorTab();
			advisorPage.clickScene(i);
			String sceneID = advisorPage.captureLogsAndVerifyScene(i);
			clientPage.switchToClientTab(1);
			clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);
			i++;
		}

		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting(); 
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded(); 
	}
	
	@Test
	public void TC_C6372_ViewCollectionScenes()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);

		String PortugieserCollectionScene = app.getProperty("TC_C6372_PortugieserCollectionSceneNumber");
		String LimitedCollectionScene = app.getProperty("TC_C6372_LimitedCollectionSceneNumber");
		String DaVinciCollectionScene = app.getProperty("TC_C6372_DaVinciCollectionSceneNumber");
		String PortfinoCollectionScene = app.getProperty("TC_C6372_PortfinoCollectionSceneNumber");
		String LadiesCollectionScene = app.getProperty("TC_C6372_LadiesCollectionSceneNumber");
		
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
				
		int[] collectionSceneID = {Integer.parseInt(PortugieserCollectionScene), Integer.parseInt(LimitedCollectionScene), Integer.parseInt(DaVinciCollectionScene)
				, Integer.parseInt(PortfinoCollectionScene), Integer.parseInt(LadiesCollectionScene)};

		for (int i : collectionSceneID) {
			advisorPage.switchToAdvisorTab();
			advisorPage.clickScene(i);
			String sceneID = advisorPage.captureLogsAndVerifyScene(i);
			clientPage.switchToClientTab(1);
			clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);
		}
		
		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting(); 
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded(); 
	}

}
