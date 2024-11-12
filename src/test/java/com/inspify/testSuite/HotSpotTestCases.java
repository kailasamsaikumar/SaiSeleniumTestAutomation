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

public class HotSpotTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();	
	}
	
	@Test(groups = {"SmokeTest"})
	public void TC_C6373_ViewProductHotspots()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);
		
		String PortugieserCollectionProductID = app.getProperty("TC_C6373_PortugieserCollectionProductID");
		String LimitedCollectionProductID = app.getProperty("TC_C6373_LimitedCollectionProductID");
		String DaVinciCollectionProductID = app.getProperty("TC_C6373_DaVinciCollectionProductID");
		
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
				
		String[] productID = {PortugieserCollectionProductID, LimitedCollectionProductID, DaVinciCollectionProductID};
		for (String sProductID : productID) {
			advisorPage.switchToAdvisorTab();
			advisorPage.clickHotspot(sProductID);
			advisorPage.switchToInframeContainer();
			advisorPage.verifyThatProductDetailsPopupIsDisplayed();
			advisorPage.captureLogsAndVerifyID(sProductID);
			advisorPage.waitForPageToLoad(VERYLONGWAIT);

			clientPage.switchToClientTab(1);
			clientPage.switchToInframeContainer();
			clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sProductID);

			advisorPage.switchToAdvisorTab();
			advisorPage.closeProductPopup();
			clientPage.switchToClientTab(1);
			clientPage.verifyThatProductsPopupIsNotDisplayed();
		}
		
		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting(); 
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded(); 
	}
	
	@Test
	public void TC_C6374_ViewInspirationHotspots() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);
		
		String inspirationHotspotID1 = app.getProperty("TC_C6374_InspirationHotspot1");
		String inspirationHotspotID2 = app.getProperty("TC_C6374_InspirationHotspot2");
		String inspirationHotspotID3 = app.getProperty("TC_C6374_InspirationHotspot3");
		
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
				
		String[] inspirationHotspotIDS = {inspirationHotspotID1, inspirationHotspotID2, inspirationHotspotID3};
		for (String inspirationHotspotID : inspirationHotspotIDS) {
			advisorPage.switchToAdvisorTab();
			advisorPage.clickInspirationHotspot(inspirationHotspotID);
			advisorPage.captureLogsAndVerifyID(inspirationHotspotID);
			advisorPage.waitForPageToLoad(VERYLONGWAIT);
			clientPage.switchToClientTab(1);
			clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(inspirationHotspotID);

			advisorPage.switchToAdvisorTab();
			advisorPage.closeProductPopup();
			clientPage.switchToClientTab(1);
			clientPage.verifyThatProductsPopupIsNotDisplayed();
		}
		
		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting(); 
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded(); 
	}

}
