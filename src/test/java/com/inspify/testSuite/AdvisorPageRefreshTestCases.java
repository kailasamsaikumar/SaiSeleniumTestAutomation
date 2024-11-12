package com.inspify.testSuite;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(com.inspify.utilities.TestngListener.class)

public class AdvisorPageRefreshTestCases extends BaseSetup {

    AdvisorPage advisorPage;
    ClientPage clientPage;
    String advisorname,clientname;
    static ConfigManager app = new ConfigManager("App");

    @BeforeMethod(alwaysRun = true)
    public void initialize() {
        getBrowser().manage().deleteAllCookies();
    }

    @Test
    public void TC_C8651_ViewIsNotDisplayedWithDocumentsWhenAdvisorRejoins() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        String sAdvisorUrl = app.getProperty("TC_C8651_IWCADVISORURL");
        String sClientUrl = app.getProperty("TC_C8651_IWCCLIENTURL");
        String clients = app.getProperty("TC_Clients");

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
        advisorPage.ClickOnDocumentViewer();
        advisorPage.clickOnImageDocument();
        advisorPage.verifyThatDocumentViewerContainerIsDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.clickOnSpecifiedSlideIndex(0);
        advisorPage.verifyThatSliderViewContainerIsDisplayed();
        advisorPage.verifyThatSpecifiedSlideViewerIsActive(0);

        String sImageSource = advisorPage.getImageSource();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        int No_Of_Clients = Integer.parseInt(clients);
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientName);

        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        advisorPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.ClickOnDocumentViewer();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentIconIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.clickOnVideoDocument();
        advisorPage.verifyThatVideoDocumentIsOpened();
        advisorPage.clickOnNotificationButton();
        advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

        clientPage.switchToClientTab(1);
        clientPage.verifyThatVideoDocumentIsOpened();
        clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyMicrophoneButtonIsMuted();
        clientPage.switchToClientTab(1);
        clientPage.verifyMicrophoneButtonIsMuted();

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientName);

        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        advisorPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
    }

    @Test(groups = "SmokeTest")
    public void TC_C34690_ViewSameSceneWhenAdvisorRejoins() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
        String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
        String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);
        String clients = app.getProperty("TC_Clients");

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
        advisorPage.clickScene(1);
        String firstSceneID = advisorPage.captureLogsAndVerifyScene(1);
        advisorPage.clickScene(1);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(firstSceneID);

        advisorPage.switchToAdvisorTab();
        advisorPage.clickScene(3);
        String thirdSceneID = advisorPage.captureLogsAndVerifyScene(3);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(thirdSceneID);

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        int No_Of_Clients = Integer.parseInt(clients);
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientName);

        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        String sFirstSceneID = advisorPage.captureLogsAndVerifyScene(1);
        advisorPage.clickScene(1);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(sFirstSceneID);

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();

    }

    @Test(groups = "SmokeTest")
    public void TC_C34691_ViewIsNotDisplayedWithFileWhenAdvisorRejoins() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        String sAdvisorUrl = app.getProperty("TC_C34691_IWCADVISORURL");
        String sClientUrl = app.getProperty("TC_C34691_IWCCLIENTURL");
        String clients = app.getProperty("TC_Clients");

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
        advisorPage.ClickOnDocumentViewer();
        advisorPage.clickOnImageDocument();
        advisorPage.verifyThatDocumentViewerContainerIsDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatSliderViewContainerIsDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();

        for(int i=0;i<=2;i++){
            advisorPage.switchToAdvisorTab();
            advisorPage.clickOnSpecifiedSlideIndex(i);
            advisorPage.verifyThatSliderViewContainerIsDisplayed();
            advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);

            String sImageSource = advisorPage.getImageSource();
            System.out.println(sImageSource);
            clientPage.switchToClientTab(1);
            clientPage.verifyThatSliderViewContainerIsNotDisplayed();
            clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);
        }

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        int No_Of_Clients = Integer.parseInt(clients);
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientName);

        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        advisorPage.verifyThatSliderViewContainerIsNotDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatDocumentViewerContainerIsNotDisplayed();
        clientPage.verifyThatSliderViewContainerIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
    }

    @Test(groups = "SmokeTest")
    public void TC_C34695_ViewIsNotDisplayedWithPopupsWhenAdvisorRejoins() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
        String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
        String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);
        String clients = app.getProperty("TC_Clients");

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
        advisorPage.clickOnProductSearchIcon();
        advisorPage.switchToInframeContainer();
        advisorPage.verifyThatProductSearchPopupIsDisplayed();
        String modelCode = advisorPage.getSpecifiedIndexProductModelCode(0);

        clientPage.switchToClientTab(1);
        clientPage.verifyThatVeilLayerIsDisplayed();
        clientPage.switchToInframeContainer();
        clientPage.verifyThatClientIsUnableToClickOnProductSearchComponents();
        clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(0, modelCode);

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatProductSearchPopupIsNotDisplayed();
        clientPage.VerifyThatIframeContainerIsNotDisplayed();
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        int No_Of_Clients = Integer.parseInt(clients);
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientname);

        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.VerifyThatIframeContainerIsNotDisplayed();
        advisorPage.verifyThatProductSearchPopupIsNotDisplayed();

        advisorPage.clickOnSideMenuNavigatorIcon();
        advisorPage.clickOnProductCompareMenuItem();
        advisorPage.switchToInframeContainer();
        advisorPage.verifyThatProductComparePopupIsDisplayed();

        clientPage.switchToClientTab(1);
        clientPage.switchToInframeContainer();
        clientPage.verifyThatEmptyProductCompareMessageIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.VerifyThatIframeContainerIsNotDisplayed();
        clientPage.verifyThatEmptyProductCompareMessageIsNotDisplayed();
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientname);

        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.VerifyThatIframeContainerIsNotDisplayed();
        advisorPage.verifyThatProductComparePopupIsNotDisplayed();

        advisorPage.clickOnSearchStoriesIcon();
        advisorPage.switchToInframeContainer();
        advisorPage.verifyThatStorySearchPopupIsDisplayed();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatStorySearchPopupIsDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.refresh();
        clientPage.switchToClientTab(1);
        clientPage.VerifyThatIframeContainerIsNotDisplayed();
        clientPage.verifyThatStorySearchPopupIsNotDisplayed();
        clientPage.verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyThatNoOfGuestsWaitingInLounge(No_Of_Clients);
        advisorPage.verifyThatClientIsWaitingInLounge(clientname);

        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
        clientPage.switchToClientTab(1);
        clientPage.verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView(advisorname);

        advisorPage.switchToAdvisorTab();
        advisorPage.VerifyThatIframeContainerIsNotDisplayed();
        advisorPage.verifyThatStorySearchPopupIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.endMeeting();
        advisorPage.verifyThatMeetingEnded();
        clientPage.switchToClientTab(1);
        clientPage.verifyThatMeetingEnded();
    }

}



