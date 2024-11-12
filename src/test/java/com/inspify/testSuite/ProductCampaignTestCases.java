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

public class ProductCampaignTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod
	public void initialize() throws Exception {
		getBrowser().manage().deleteAllCookies();
		
	}
	
	@Test
	public void TC_C4868_ViewProductsCampaignScreenPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);

		getBrowser().get(sAdvisorUrl);
		advisorPage.verifyThatStartSessionButtonIsDisplayed();
		advisorPage.enterAdvisorName(advisorname);
		advisorPage.clickOnStartSessionButton();
	  	advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
		String sClientUrl = advisorPage.getClientUrl();
	  	  
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
	  
	  	clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.clickOnSideMenuNavigatorIcon();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatSideMenuSectionIsNotDisplayed();

	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.clickOnWatchesCollectionMenuItem();
	  	advisorPage.switchToInframeContainer();
	  	advisorPage.verifyThatCollectionMessageIsDisplayed();
	  	  
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatVeilLayerIsDisplayed();
	  	clientPage.switchToInframeContainer();
	  	clientPage.verifyThatCollectionMessageIsDisplayed();
	  	  
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.switchToInframeContainer();
	  	int totalCollectionProductsCount = advisorPage.getCountOfAllDisplayedProducts();
	  	System.out.println(totalCollectionProductsCount);
	  	  
	  	for(int i=0;i<totalCollectionProductsCount;i++){
	  		  System.out.println("Product Details: "+(i+1));
	  		  String productModelCode = advisorPage.getSpecifiedIndexProductModelCode(i);
		      clientPage.switchToClientTab(1);
	  		  clientPage.switchToInframeContainer();
	  		  clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(i, productModelCode);
	  		  
	  		  advisorPage.switchToAdvisorTab();
		  	  advisorPage.switchToInframeContainer();
	  		  String productName = advisorPage.getSpecifiedIndexProductName(i);
	  		  
	  		  clientPage.switchToClientTab(1);
	  		  clientPage.switchToInframeContainer();
	  		  clientPage.verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(i, productName);
	  	}
	  	  
	  	clientPage.switchToClientTab(1);
		//clientPage.switchBackFromIframe();
	  	advisorPage.switchToAdvisorTab();
	  	//advisorPage.switchBackFromIframe();
	  	advisorPage.closeProductPopup();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatProductsPopupIsNotDisplayed();

	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.endMeeting();
	  	advisorPage.verifyThatMeetingEnded();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatMeetingEnded();
		
	  }

}
