package com.inspify.testSuite;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
@Listeners(com.inspify.utilities.TestngListener.class)

public class LateJoinerTestCases extends BaseSetup {
    AdvisorPage advisorPage;
    ClientPage clientPage;
    String advisorname,clientname, secondClientName, thirdClientName;
    static ConfigManager app = new ConfigManager("App");

    @BeforeMethod(alwaysRun = true)
    public void initialize() {
        getBrowser().manage().deleteAllCookies();
    }

    @Test
    public void TC_C34696_ViewCorrectSearchResultsForLateJoiner() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        secondClientName = app.getProperty("SecondClientName");
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
        String clientName = clientPage.enterClientName(clientname);
        clientPage.clickOnJoinNowButton();
        clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(clientName);
        advisorPage.clickOnAdmitButton();
        advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(clientName);

        advisorPage.switchToAdvisorTab();
        advisorPage.clickScene(1);
        advisorPage.clickScene(3);
        String sceneID = advisorPage.captureLogsAndVerifyScene(3);

        clientPage.switchToClientTab(1);
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);
        clientPage.verifyThatClientHasJoinedMeeting(clientname);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.clickOnProductSearchIcon();
        advisorPage.switchToInframeContainer();
        advisorPage.verifyThatProductSearchPopupIsDisplayed();
        advisorPage.searchForSpecifiedTextOnProductSearchPopup(sSearchText);
        advisorPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);
        ArrayList<String> advisorViewResults = advisorPage.getTitlesOfAllDisplayedProducts();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);
        ArrayList<String> firstClientViewResults = clientPage.getTitlesOfAllDisplayedProducts();
        clientPage.verifyThatSameSearchResultsFromAdvisorViewAreDisplayedInClientView(advisorViewResults, firstClientViewResults);

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        int advisorViewSearchResultsCount = advisorPage.getCountOfAllDisplayedProducts();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        int firstClientViewSearchResultsCount = clientPage.getCountOfAllDisplayedProducts();
        clientPage.verifyThatSameSearchCountResultsFromAdvisorViewAreDisplayedInClientView(advisorViewSearchResultsCount,firstClientViewSearchResultsCount);

        invokeNewTabForClient();
        getBrowser().get(sClientUrl);
        clientPage.verifyThatClientNoteSectionIsDisplayed();
        clientPage.verifyThatJoinNowButtonIsDisplayed();
        clientPage.enterClientName(secondClientName);
        clientPage.clickOnJoinNowButton();
        clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(secondClientName);
        advisorPage.clickOnAdmitButton();
        advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(secondClientName);

        clientPage.switchToClientTab(2);
        clientPage.verifyThatClientHasJoinedMeeting(secondClientName);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);

        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);
        ArrayList<String> secondClientViewResults = clientPage.getTitlesOfAllDisplayedProducts();
        clientPage.verifyThatSameSearchResultsFromAdvisorViewAreDisplayedInClientView(advisorViewResults, secondClientViewResults);

        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        int secondClientViewSearchResultsCount = clientPage.getCountOfAllDisplayedProducts();
        clientPage.verifyThatSameSearchCountResultsFromAdvisorViewAreDisplayedInClientView(advisorViewSearchResultsCount,secondClientViewSearchResultsCount);

        advisorPage.switchToAdvisorTab();
        advisorPage.closeProductPopup();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatProductsPopupIsNotDisplayed();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatProductsPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatMeetingEnded();
    }

    @Test(groups = {"SmokeTest"})
    public void TC_C34697_ViewProductDetailsScreenPopupForLateJoiner() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        secondClientName = app.getProperty("SecondClientName");
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

        advisorPage.switchToAdvisorTab();
        advisorPage.clickScene(1);
        advisorPage.clickScene(3);
        String sceneID = advisorPage.captureLogsAndVerifyScene(3);

        clientPage.switchToClientTab(1);
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);
        clientPage.verifyThatClientHasJoinedMeeting(clientname);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.clickOnProductSearchIcon();
        advisorPage.switchToInframeContainer();
        advisorPage.verifyThatProductSearchPopupIsDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatVeilLayerIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnFirstProductModelLink();
        advisorPage.verifyThatProductDetailsPopupIsDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatVeilLayerIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.verifyThatDescriptionSectionIsDisplayedOnProductDetailsPopup();
        advisorPage.verifyThatSpecificationSectionIsDisplayedOnProductDetailsPopup();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatClientIsUnableToClickOnDescriptionSectionOfProductDetailsPopup();
        clientPage.verifyThatClientIsUnableToClickOnSpecificationSectionOfProductDetailsPopup();

        invokeNewTabForClient();
        getBrowser().get(sClientUrl);
        clientPage.verifyThatClientNoteSectionIsDisplayed();
        clientPage.verifyThatJoinNowButtonIsDisplayed();
        clientPage.enterClientName(secondClientName);
        clientPage.clickOnJoinNowButton();
        clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(secondClientName);
        advisorPage.clickOnAdmitButton();
        advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(secondClientName);

        clientPage.switchToClientTab(2);
        clientPage.verifyThatClientHasJoinedMeeting(secondClientName);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);

        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatClientIsUnableToClickOnDescriptionSectionOfProductDetailsPopup();
        clientPage.verifyThatClientIsUnableToClickOnSpecificationSectionOfProductDetailsPopup();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        String advisorViewModelCode = advisorPage.getProductModelCode();
        String advisorViewProductName = advisorPage.getProductName();
        String advisorViewProductPrice = advisorPage.getProductPrice();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(advisorViewModelCode);
        clientPage.verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(advisorViewProductName);
        clientPage.verifyThatSameProductPriceFromAdvisorViewAreDisplayedInClientView(advisorViewProductPrice);

        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(advisorViewModelCode);
        clientPage.verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(advisorViewProductName);
        clientPage.verifyThatSameProductPriceFromAdvisorViewAreDisplayedInClientView(advisorViewProductPrice);

        advisorPage.switchToAdvisorTab();
        advisorPage.closeProductPopup();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatProductsPopupIsNotDisplayed();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatProductsPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatMeetingEnded();
    }

    @Test
    public void TC_C34698_ViewProductCompareScreenPopupForLateJoiner() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        secondClientName = app.getProperty("SecondClientName");
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

        advisorPage.switchToAdvisorTab();
        advisorPage.clickScene(1);
        advisorPage.clickScene(3);
        String sceneID = advisorPage.captureLogsAndVerifyScene(3);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);

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

        for(int i=0;i<2;i++){
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

        invokeNewTabForClient();
        getBrowser().get(sClientUrl);
        clientPage.verifyThatClientNoteSectionIsDisplayed();
        clientPage.verifyThatJoinNowButtonIsDisplayed();
        clientPage.enterClientName(secondClientName);
        clientPage.clickOnJoinNowButton();
        clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(secondClientName);
        advisorPage.clickOnAdmitButton();
        advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(secondClientName);

        clientPage.switchToClientTab(2);
        clientPage.verifyThatClientHasJoinedMeeting(secondClientName);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);

        for(int i=0;i<2;i++){
            clientPage.switchToClientTab(2);
            clientPage.switchToInframeContainer();
            clientPage.verifyThatSameComparedProductModelCodeFromAdvisorViewAreDisplayedInClientView(i+1, productModelCode[i]);
            clientPage.verifyThatSameComparedProductTitleFromAdvisorViewAreDisplayedInClientView(i+1, productTitle[i]);
        }

        advisorPage.switchToAdvisorTab();
        advisorPage.closeProductPopup();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatProductsPopupIsNotDisplayed();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatProductsPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatMeetingEnded();
    }

    @Test(groups = {"SmokeTest"})
    public void TC_C34699_ViewInspirationScreenPopupForLateJoiner() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        secondClientName = app.getProperty("SecondClientName");
        thirdClientName = app.getProperty("ThirdClientName");
        String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
        String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
        String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);

        String iContextIndex = app.getProperty("TC_ExpectedInstagramContextIndex");
        String vContextIndex = app.getProperty("TC_ExpectedVideoClipContextIndex");
        String instagramContext = app.getProperty("TC_ExpectedInstagramContext");
        String videoContext = app.getProperty("TC_ExpectedVideoClipContext");
        int instagramContextIndex = Integer.parseInt(iContextIndex);
        int videoClipContextIndex = Integer.parseInt(vContextIndex);

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
        advisorPage.clickScene(3);
        String sceneID = advisorPage.captureLogsAndVerifyScene(3);

        clientPage.switchToClientTab(1);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);

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
        advisorPage.clickOnFilterContextButton(2);
        advisorPage.verifySelectedContextIsDisplayedAndActive(instagramContextIndex, instagramContext );
        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifySelectedContextIsDisplayedAndActive(instagramContextIndex, instagramContext );

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.closeFilterIcon();
        advisorPage.verifyThatFilterIconPopupIsNotDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatFilterIconPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnSpecifiedIndexStory(1);
        advisorPage.verifyThatInstagramStoryPageTitlePopupIsDisplayed();
        String sStoryDescription = advisorPage.getStoryDescription();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(sStoryDescription);

        invokeNewTabForClient();
        getBrowser().get(sClientUrl);
        clientPage.verifyThatClientNoteSectionIsDisplayed();
        clientPage.verifyThatJoinNowButtonIsDisplayed();
        clientPage.enterClientName(secondClientName);
        clientPage.clickOnJoinNowButton();
        clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(secondClientName);
        advisorPage.clickOnAdmitButton();
        advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(secondClientName);

        clientPage.switchToClientTab(2);
        clientPage.verifyThatClientHasJoinedMeeting(secondClientName);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(sStoryDescription);

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnBackButton();
        advisorPage.verifyThatSearchInputFieldIsEmpty();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatStorySearchInputFieldIsEmpty();
        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatStorySearchInputFieldIsEmpty();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnFilterIcon();
        advisorPage.verifyThatFilterIconPopupIsDisplayed();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatFilterIconPopupIsDisplayed();
        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatFilterIconPopupIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnFilterContextButton(2);
        advisorPage.verifySelectedContextIsInActive(2);

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifySelectedContextIsInActive(2);
        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifySelectedContextIsInActive(2);

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnFilterContextButton(7);
        advisorPage.verifySelectedContextIsDisplayedAndActive(videoClipContextIndex, videoContext);

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifySelectedContextIsDisplayedAndActive(videoClipContextIndex, videoContext);
        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifySelectedContextIsDisplayedAndActive(videoClipContextIndex, videoContext);

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.closeFilterIcon();
        advisorPage.verifyThatFilterIconPopupIsNotDisplayed();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatFilterIconPopupIsNotDisplayed();
        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatFilterIconPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.switchToInframeContainer();
        advisorPage.clickOnSpecifiedIndexStory(1);
        advisorPage.verifyThatStoriesVideoIsPlaying();
        advisorPage.verifyThatVideoClipStoryPageTitlePopupIsDisplayed();
        String videoDescription = advisorPage.getStoryDescription();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatStoriesVideoIsPlaying();
        clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(videoDescription);

        clientPage.switchToClientTab(2);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatStoriesVideoIsPlaying();
        clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(videoDescription);

        invokeNewTabForClient();
        getBrowser().get(sClientUrl);
        clientPage.verifyThatClientNoteSectionIsDisplayed();
        clientPage.verifyThatJoinNowButtonIsDisplayed();
        clientPage.enterClientName(thirdClientName);
        clientPage.clickOnJoinNowButton();
        clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(thirdClientName);
        advisorPage.clickOnAdmitButton();
        advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(thirdClientName);

        clientPage.switchToClientTab(3);
        clientPage.verifyThatClientHasJoinedMeeting(thirdClientName);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sceneID);

        clientPage.switchToClientTab(3);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatStoriesVideoIsPlaying();
        clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(videoDescription);

        advisorPage.switchToAdvisorTab();
        advisorPage.closeProductPopup();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatProductsPopupIsNotDisplayed();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatProductsPopupIsNotDisplayed();
        clientPage.switchToClientTab(3);
        clientPage.verifyThatProductsPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(2);
        clientPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(3);
        clientPage.verifyThatMeetingEnded();
    }

}
