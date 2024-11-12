package com.inspify.testSuite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;
import com.inspify.utilities.BaseSetup;
@Listeners(com.inspify.utilities.TestngListener.class)

public class ProductCompareTestCases extends BaseSetup{

	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();
	}
	
	@Test
	public void TC_C2259_ViewProductComparePopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);

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
	  	clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();

	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.clickOnSideMenuNavigatorIcon();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatSideMenuSectionIsNotDisplayed();
		
	  	advisorPage.switchToAdvisorTab();
		advisorPage.clickOnProductCompareMenuItem();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatProductComparePopupIsDisplayed();
		advisorPage.verifyThatEmptyProductCompareMessageIsDisplayed();
		
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVeilLayerIsDisplayed(); 
		clientPage.switchToInframeContainer();
		clientPage.verifyThatEmptyProductCompareMessageIsDisplayed();
		clientPage.verifyThatClientIsUnableToClickOnProductCompareEmptyPopupComponents();

		advisorPage.switchToAdvisorTab();
		advisorPage.closeProductPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatProductsPopupIsNotDisplayed();	
		
		advisorPage.switchToAdvisorTab();
	  	advisorPage.endMeeting();
	  	advisorPage.verifyThatMeetingEnded();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatMeetingEnded();
	}
	
	@Test
	public void TC_C2430_ViewCorrectProductsOnProductCompareScreenPopup() {
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
	  	advisorPage.clickOnProductCompareMenuItem();
	  	advisorPage.switchToInframeContainer();
	  	advisorPage.verifyThatProductComparePopupIsDisplayed();
	  	advisorPage.verifyThatEmptyProductCompareMessageIsDisplayed();
	  
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatVeilLayerIsDisplayed();
	  	clientPage.switchToInframeContainer();
	  	clientPage.verifyThatEmptyProductCompareMessageIsDisplayed();
	  	clientPage.verifyThatClientIsUnableToClickOnProductCompareEmptyPopupComponents();
	  
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.switchToInframeContainer();
	  	advisorPage.clickOnSearchProduct();
	  	advisorPage.verifyThatProductSearchPopupIsDisplayed();
	  	advisorPage.verifyThatProductCompareMessageIsDisplayed();
	  	  
	  	int productCount = 4;
	  	String[] productModelCode = new String[productCount];
	  	String[] productTitle = new String[productCount];
	  	  
	  	for(int i=0;i<productCount;i++){
			  advisorPage.switchToAdvisorTab();
			  advisorPage.switchToInframeContainer();
			  productModelCode[i] = advisorPage.getSpecifiedIndexProductModelCode(i);
			  productTitle[i] = advisorPage.getSpecifiedIndexProductName(i);
		}
	  	  
	  	for(int i=0;i<2;i++) {
	  			advisorPage.switchToAdvisorTab();
	  			advisorPage.switchToInframeContainer();
			  	advisorPage.clickOnSpecifiedIndexProductModelLink(i);
				advisorPage.scrollDownToSideBarSection();
				advisorPage.verifyThatSelectedProductModelCodeIsDisplayedInSideBar(i+1, productModelCode[i]);
				advisorPage.verifyThatSelectedProductTitleIsDisplayedInSideBar(i+1, productTitle[i]);

				if(i==1){
					advisorPage.switchToAdvisorTab();
					advisorPage.switchToInframeContainer();
					advisorPage.verifyProductCompareStartTextMessage(2);
				  	advisorPage.clickOnStartToCompareProduct();
				  	advisorPage.verifyThatProductComparePopupIsDisplayed();
				}
	  	}
		 
	  	for(int i=0;i<2;i++){
			  clientPage.switchToClientTab(1);
			  clientPage.switchToInframeContainer();
			  clientPage.verifyThatSameComparedProductModelCodeFromAdvisorViewAreDisplayedInClientView(i+1, productModelCode[i]);
			  clientPage.verifyThatSameComparedProductTitleFromAdvisorViewAreDisplayedInClientView(i+1, productTitle[i]);
	  	}
	  	
	  	for(int i=2;i<productCount;i++) {
			  if(i==2){
				  advisorPage.switchToAdvisorTab();
				  advisorPage.switchToInframeContainer();
				  advisorPage.clickOnAddProductCompare();
				  advisorPage.verifyThatProductSearchPopupIsDisplayed();
	  		  }

			  advisorPage.switchToAdvisorTab();
			  advisorPage.switchToInframeContainer();
	  		  advisorPage.clickOnSpecifiedIndexProductModelLink(i);
	  		  advisorPage.scrollDownToSideBarSection();
	  		  advisorPage.verifyThatSelectedProductModelCodeIsDisplayedInSideBar(i+1, productModelCode[i]);
	  		  advisorPage.verifyThatSelectedProductTitleIsDisplayedInSideBar(i+1, productTitle[i]);
	  		  
	  		  if(i==3){
					advisorPage.switchToAdvisorTab();
	  				advisorPage.switchToInframeContainer();
	  			  	advisorPage.verifyProductCompareStartTextMessage(4);
	  		  	  	advisorPage.clickOnStartToCompareProduct();
	  		  	  	advisorPage.verifyThatProductComparePopupIsDisplayed();
	  		  }
		}
	  	  
	  	for(int i=2;i<productCount;i++){
			  clientPage.switchToClientTab(1);
			  clientPage.switchToInframeContainer();
	  		  clientPage.verifyThatSameComparedProductModelCodeFromAdvisorViewAreDisplayedInClientView(i+1, productModelCode[i]);
	  		  clientPage.verifyThatSameComparedProductTitleFromAdvisorViewAreDisplayedInClientView(i+1, productTitle[i]);
	  	}
		 
	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.switchToInframeContainer();
	  	advisorPage.deleteOneProductInCompareProductsScreenPopup();
	  	System.out.println("DELETED ONE PRODUCT");
		
	  	for(int i=1;i<=3;i++) {
			  clientPage.switchToClientTab(1);
			  clientPage.switchToInframeContainer();
			  clientPage.verifyThatSameComparedProductModelCodeFromAdvisorViewAreDisplayedInClientView(i, productModelCode[i]);
			  clientPage.verifyThatSameComparedProductTitleFromAdvisorViewAreDisplayedInClientView(i, productTitle[i]);
	  	}

	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.closeProductPopup();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatProductsPopupIsNotDisplayed();

	  	advisorPage.switchToAdvisorTab();
	  	advisorPage.endMeeting();
	  	advisorPage.verifyThatMeetingEnded();
	  	clientPage.switchToClientTab(1);
	  	clientPage.verifyThatMeetingEnded();
		
	}

	@Test
	public void TC_C4867_ViewProductForComparisonComingFromProductDetailsScreenPopup() {
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
		advisorPage.clickOnProductSearchMenuItem();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatProductSearchPopupIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVeilLayerIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnSpecifiedIndexProductModelLink(0);
		advisorPage.verifyThatProductDetailsPopupIsDisplayed();
		String productModelCode = advisorPage.getProductModelCode();
		String productName = advisorPage.getProductName();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(productModelCode);
		clientPage.verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(productName);

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnProductDetailsCompareProduct();
		advisorPage.verifyThatProductComparePopupIsDisplayed();

		String compareScreenModelCode = advisorPage.getComparedProductModelCode(1);
		advisorPage.verifyThatSameProductModelCodeFromProductDetailsPageIsDisplayedInCompareProducts(1, compareScreenModelCode);
		advisorPage.verifyThatSameProductTitleFromProductDetailsPageIsDisplayedInCompareProducts(1, productName);

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSameComparedProductModelCodeFromAdvisorViewAreDisplayedInClientView(1, compareScreenModelCode);
		clientPage.verifyThatSameComparedProductTitleFromAdvisorViewAreDisplayedInClientView(1, productName);

		advisorPage.switchToAdvisorTab();
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
