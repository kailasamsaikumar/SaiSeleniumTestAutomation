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

public class participantStreamTestcases extends BaseSetup {

    AdvisorPage advisorPage;
    ClientPage clientPage;
    String advisorname,clientname, secondClientName, thirdClientName;;
    static ConfigManager app = new ConfigManager("App");

    @BeforeMethod(alwaysRun = true)
    public void initialize() {
        getBrowser().manage().deleteAllCookies();
    }

    @Test()
    public void TC_C40709_ViewTheParticipantsStreamWhenOpeningFiles() {
        advisorPage = new AdvisorPage(switchBetweenTabs());
        clientPage = new  ClientPage(switchBetweenTabs());
        advisorname = app.getProperty("AdvisorName");
        clientname = app.getProperty("ClientName");
        secondClientName = app.getProperty("SecondClientName");
        thirdClientName = app.getProperty("ThirdClientName");
        String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
        String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
        String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);

        getBrowser().get(sAdvisorUrl);
        advisorPage.verifyThatStartSessionButtonIsDisplayed();
        advisorPage.enterAdvisorName(advisorname);
        advisorPage.clickOnStartSessionButton();
        advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);

        //First client
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
        String advisorParticipantID = advisorPage.getAdvisorParticipantId();

        clientPage.switchToClientTab(1);
        clientPage.verifyThatClientHasJoinedMeeting(clientname);
        clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();
        String firstClientParticipantId = clientPage.getClientParticipantId();

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyAdvisorLocalVideoIsStreaming();
        advisorPage.verifyAdvisorLocalAudioIsStreaming();
        advisorPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(firstClientParticipantId);

        clientPage.switchToClientTab(1);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);

        //Second client
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
        String secondClientParticipantId = clientPage.getClientParticipantId();

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyAdvisorLocalVideoIsStreaming();
        advisorPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);

        advisorPage.verifyAdvisorLocalAudioIsStreaming();
        advisorPage.verifyClientRemoteAudioIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(secondClientParticipantId);

        clientPage.switchToClientTab(1);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);

        clientPage.switchToClientTab(2);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);

        //Third client
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
        String thirdClientParticipantId = clientPage.getClientParticipantId();

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyAdvisorLocalVideoIsStreaming();
        advisorPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        advisorPage.verifyAdvisorLocalAudioIsStreaming();
        advisorPage.verifyClientRemoteAudioIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(1);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(2);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(3);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);

        //Enlarge Participant action
        advisorPage.switchToAdvisorTab();
        advisorPage.enlargeParticipant(clientname);
        advisorPage.verifyAdvisorLocalVideoIsStreaming();
        advisorPage.verifyClientEnlargedRemoteVideoIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        advisorPage.verifyAdvisorLocalAudioIsStreaming();
        advisorPage.verifyClientRemoteAudioIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(1);
        clientPage.verifyClientEnlargedLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(2);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientEnlargedRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(3);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientEnlargedRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);

        //Presentation mode
        advisorPage.switchToAdvisorTab();
        advisorPage.changeToPresentationLayoutMode();
        advisorPage.verifyAdvisorLocalVideoIsStreaming();
        advisorPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);

        advisorPage.verifyAdvisorLocalAudioIsStreaming();
        advisorPage.verifyClientRemoteAudioIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(1);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(2);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(3);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);

        //Conversation mode
        advisorPage.switchToAdvisorTab();
        advisorPage.changeToConversationLayoutMode();
        advisorPage.verifyAdvisorLocalVideoIsStreaming();
        advisorPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);
        advisorPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);

        advisorPage.verifyAdvisorLocalAudioIsStreaming();
        advisorPage.verifyClientRemoteAudioIsStreaming(firstClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(secondClientParticipantId);
        advisorPage.verifyClientRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(1);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(2);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(thirdClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(thirdClientParticipantId);

        clientPage.switchToClientTab(3);
        clientPage.verifyClientLocalVideoIsStreaming();
        clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorParticipantID);
        clientPage.verifyClientRemoteVideoIsStreaming(firstClientParticipantId);
        clientPage.verifyClientRemoteVideoIsStreaming(secondClientParticipantId);

        clientPage.verifyClientLocalAudioIsStreaming();
        clientPage.verifyParticipantRemoteAudioIsStreaming(advisorParticipantID);
        clientPage.verifyParticipantRemoteAudioIsStreaming(firstClientParticipantId);
        clientPage.verifyParticipantRemoteAudioIsStreaming(secondClientParticipantId);

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
