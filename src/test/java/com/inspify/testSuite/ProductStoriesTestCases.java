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

public class ProductStoriesTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod
	public void initialize() throws Exception {
		getBrowser().manage().deleteAllCookies();
	}
	
	@Test
	public void TC_C4873_ViewStorySearchPopup() throws Exception {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("IWCVBLiteAdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("IWCVBLiteClientURL").replace("testing", "testing"+randomAlphabet);

        String defaultLanguage = app.getProperty("TC_4873_IWCVBLite_UK_ExpectedDefaultLanguage");
		String defaultLanguageIndex =  app.getProperty("TC_4873_IWCVBLite_UK_ExpectedEnglishLangaugeIndex");
		
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
		advisorPage.verifyThatMenuItemIsNotDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMenuItemIsNotDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnStorySearchIcon();
		advisorPage.switchToInframeContainer();
	    advisorPage.verifyThatStorySearchPopupIsDisplayed();
	    clientPage.switchToClientTab(1);
	    clientPage.verifyThatVeilLayerIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
        advisorPage.clickOnFilterIcon();
		advisorPage.verifyThatFilterIconPopupIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		//clientPage.verifyThatFilterIconPopupIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.verifyDefaultLanguage(defaultLanguageIndex,defaultLanguage);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.closeFilterIcon();
	    advisorPage.verifyThatFilterIconPopupIsNotDisplayed();
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatFilterIconPopupIsNotDisplayed();
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    String sText = advisorPage.getSpecifiedIndexStoryText(1);
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatSameStoryTextFromAdvisorViewAreDisplayedInClientView(1,sText);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    String sLanguage = advisorPage.getStoryTextLanguage(sText);
	    System.out.println(sLanguage);

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
	public void TC_C4874_ViewStorySearchOnLanguageAndContextFiltersPopup() throws Exception {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("IWCVBLiteAdvisorURL").replace("testing", "testing"+randomAlphabet);
		
        String language = app.getProperty("TC_4874_IWCVBLite_UK_ExpectedDefaultLanguage");
		String languageIndex = app.getProperty("TC_4874_IWCVBLite_UK_ExpectedLanguageIndex");
		String languageCode = app.getProperty("TC_4874_IWCVBLite_UK_ExpectedLanguageCode");
		String contextIndex = app.getProperty("TC_ExpectedInstagramContextIndex");
		int instagramContextIndex = Integer.parseInt(contextIndex);
		String context = app.getProperty("TC_ExpectedInstagramContext");
		String allIndex = app.getProperty("TC_4874_IWCVBLite_UK_ExpectedAllLanguageIndex");
		String allFieldExpectedText = app.getProperty("TC_4874_IWCVBLite_UK_ExpectedAllLanguageFilter");
		
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
		
	  	advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatMenuItemIsNotDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMenuItemIsNotDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnStorySearchIcon();
		advisorPage.switchToInframeContainer();
	    advisorPage.verifyThatStorySearchPopupIsDisplayed();
	    clientPage.switchToClientTab(1);
	    clientPage.verifyThatVeilLayerIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnFilterIcon();
		advisorPage.verifyThatFilterIconPopupIsDisplayed();
		
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatFilterIconPopupIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
        advisorPage.clickOnFilterLanguageButton(4);
		advisorPage.verifyDefaultLanguage(languageIndex,language);
		advisorPage.closeFilterIcon();
	   
		String sText = advisorPage.getSpecifiedIndexStoryText(1);
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatSameStoryTextFromAdvisorViewAreDisplayedInClientView(1,sText);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    String arabicLanguage = advisorPage.getStoryTextLanguage(sText);
	    System.out.println("Language of the story text is:"+arabicLanguage);
	    if(arabicLanguage.contains(languageCode)) {
	    	System.out.println("Story is in Arabic");
         }
	    else
	    System.out.println("Story language is not as the selected language is : "+arabicLanguage);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.clickOnFilterIcon();
	    advisorPage.verifyThatFilterIconPopupIsDisplayed();
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatFilterIconPopupIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.clickOnFilterContextButton(2);
	    advisorPage.verifySelectedContextIsDisplayedAndActive(instagramContextIndex,context);
	    
	    advisorPage.closeFilterIcon();
	    advisorPage.verifyThatFilterIconPopupIsNotDisplayed();
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatFilterIconPopupIsNotDisplayed();
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.clickOnSpecifiedIndexStory(1);
	    advisorPage.verifyThatInstagramStoryPageTitlePopupIsDisplayed();
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    String sStoryDescription = advisorPage.getStoryDescription();
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(sStoryDescription);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.detectLanguage();
	    arabicLanguage = advisorPage.getStoryTextLanguage(sStoryDescription);
	   
	    if(arabicLanguage.contains(languageCode)) {
	    	System.out.println("Story is in Arabic");
	    }
	    else
	    	System.out.println("Story language is not as the selected language"+arabicLanguage);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.clickOnBackButton();
	    
	    advisorPage.clickOnFilterIcon();
	    advisorPage.verifyThatFilterIconPopupIsDisplayed();
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatFilterIconPopupIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.resetFilterOptions();
		advisorPage.verifyDefaultLanguage(allIndex,allFieldExpectedText);
		
	    advisorPage.closeFilterIcon();
	    advisorPage.verifyThatFilterIconPopupIsNotDisplayed();
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatFilterIconPopupIsNotDisplayed();
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    sText = advisorPage.getSpecifiedIndexStoryText(1);
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatSameStoryTextFromAdvisorViewAreDisplayedInClientView(1,sText);
	    
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.detectLanguage();
	    String allLanguage = advisorPage.getStoryTextLanguage(sText);
	    System.out.println("Language of the story text is:"+allLanguage);
	    if(allLanguage.contains("en")) {
	    	System.out.println("Story is in English");
	    }
	    else
	    	System.out.println("Story language is not as the selected language"+allLanguage);

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
	public void TC_C4875_ViewStorySearchResultsOnStorySearchPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("IWCVBLiteAdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sSearchText = app.getProperty("TC_C4875_StorySearchText");
	
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
		
	  	advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatMenuItemIsNotDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMenuItemIsNotDisplayed();
		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnStorySearchIcon();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.verifyThatStorySearchPopupIsDisplayed();
	    clientPage.switchToClientTab(1);
	    clientPage.verifyThatVeilLayerIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatSearchInputFieldIsEmpty();
		ArrayList<String> advisorViewResults = advisorPage.getStoryTitlesOfAllDisplayedStories();
		
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		ArrayList<String> clientViewResults = clientPage.getStoryTitlesOfAllDisplayedStories();	
		clientPage.verifyThatSameSearchResultsFromAdvisorViewAreDisplayedInClientView(advisorViewResults, clientViewResults);
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.searchForSpecifiedTextOnStorySearchPopup(sSearchText);
		advisorPage.verifyThatSpecifiedTextIsPresentInStorySearchInputField(sSearchText);
		
		ArrayList<String> advisorViewSearchResults = advisorPage.getStoryTitlesOfAllDisplayedStories();	
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		ArrayList<String> clientViewSearchResults = clientPage.getStoryTitlesOfAllDisplayedStories();	
		clientPage.verifyThatSameSearchResultsFromAdvisorViewAreDisplayedInClientView(advisorViewSearchResults, clientViewSearchResults);
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		int advisorViewSearchResultsCount1 = advisorPage.getCountOfAllDisplayedStories();
		
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		int clientViewSearchResultsCount1 = clientPage.getCountOfAllDisplayedStories();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnSpecifiedIndexStory(1);
		String pageTitle = advisorPage.getPageTitle();
		System.out.println("pageTitle: " +pageTitle);
		String description = advisorPage.getStoryDescription();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(description);
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnBackButton();
		advisorPage.clickClearSearchCrossIcon();
		advisorPage.searchForSpecifiedTextOnStorySearchPopup("");
		advisorPage.verifyThatSearchInputFieldIsEmpty();
		
		int advisorViewClearedSearchResultsCount2 = advisorPage.getCountOfAllDisplayedStories();
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatStorySearchInputFieldIsEmpty();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		String sText = advisorPage.getSpecifiedIndexStoryText(1);
		
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatSameStoryTextFromAdvisorViewAreDisplayedInClientView(1,sText);
	    
	    int clientViewClearedSearchResultsCount2 = clientPage.getCountOfAllDisplayedStories();
	    advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
	    advisorPage.verifyThatSearchResultsAreUpdatedAfterClearingSearchText(advisorViewSearchResultsCount1,advisorViewClearedSearchResultsCount2);
	    
	    clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
	    clientPage.verifyThatSearchResultsAreUpdatedAfterClearingSearchText(clientViewSearchResultsCount1,clientViewClearedSearchResultsCount2);
		clientPage.verifyThatSameSearchCountResultsFromAdvisorViewAreDisplayedInClientView(advisorViewClearedSearchResultsCount2,clientViewClearedSearchResultsCount2);

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
