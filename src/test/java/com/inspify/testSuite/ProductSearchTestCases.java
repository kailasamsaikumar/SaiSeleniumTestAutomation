package com.inspify.testSuite;

import java.util.ArrayList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;
@Listeners(com.inspify.utilities.TestngListener.class)

public class ProductSearchTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");
	String ExpErrorMessage_IncorrectCredentails = "Invalid Username/Password. Please try again.";

	@BeforeMethod(alwaysRun = true)
	public void initialize() throws Exception {
		getBrowser().manage().deleteAllCookies();
	}
	
	@Test(groups = "SmokeTest")
	public void TC_C2258_ViewProductSearchPopup() {
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

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnProductSearchIcon();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatProductSearchPopupIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVeilLayerIsDisplayed();
		clientPage.switchToInframeContainer();
		clientPage.verifyThatClientIsUnableToClickOnProductSearchComponents();

		advisorPage.switchToAdvisorTab();
		advisorPage.closeProductPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatProductsPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnSideMenuNavigatorIcon();
		advisorPage.clickOnProductSearchMenuItem();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatProductSearchPopupIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
		clientPage.verifyThatVeilLayerIsDisplayed();
		clientPage.switchToInframeContainer();
		clientPage.verifyThatClientIsUnableToClickOnProductSearchComponents();

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
	public void TC_C2426_ViewSearchResultsOnProductSearchPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);
		String sSearchText = app.getProperty("TC_ProductNameSearch");

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
		advisorPage.searchForSpecifiedTextOnProductSearchPopup(sSearchText);
		advisorPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);
		advisorPage.verifyThatCorrectSearchResultsAreShown(sSearchText);
		ArrayList<String> advisorViewResults = advisorPage.getTitlesOfAllDisplayedProducts();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);
		ArrayList<String> clientViewResults = clientPage.getTitlesOfAllDisplayedProducts();
		clientPage.verifyThatSameSearchResultsFromAdvisorViewAreDisplayedInClientView(advisorViewResults, clientViewResults);

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		int advisorViewSearchResultsCount1 = advisorPage.getCountOfAllDisplayedProducts();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		int clientViewSearchResultsCount1 = clientPage.getCountOfAllDisplayedProducts();

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clearSearchTextFromProductSearchInputField();
		advisorPage.verifyThatSearchInputFieldIsEmpty();
		int advisorViewSearchResultsCount2 = advisorPage.getCountOfAllDisplayedProducts();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatProductSearchInputFieldIsEmpty();
		int clientViewSearchResultsCount2 = clientPage.getCountOfAllDisplayedProducts();

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatSearchResultsAreUpdatedAfterClearingSearchText(advisorViewSearchResultsCount1,advisorViewSearchResultsCount2);

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSearchResultsAreUpdatedAfterClearingSearchText(clientViewSearchResultsCount1,clientViewSearchResultsCount2);
		clientPage.verifyThatSameSearchCountResultsFromAdvisorViewAreDisplayedInClientView(advisorViewSearchResultsCount2,clientViewSearchResultsCount2);

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
