package com.inspify.testSuite;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(com.inspify.utilities.TestngListener.class)

public class LoungeTestCases extends BaseSetup{

    AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname = RandomStringUtils.randomAlphabetic(8);
	String clientname = RandomStringUtils.randomAlphabetic(8);
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();
	}

    @Test
	public void TC_C40539_AllowUserWithoutCameraToJoinTheMeeting()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);

		getBrowser().get(sAdvisorUrl);
		advisorPage.verifyThatStartSessionWithoutCameraButtonIsDisplayed();
		advisorPage.enterAdvisorName(advisorname);
        advisorPage.verifyThatCameraNotAccessibleTextIsDisplayed();
		advisorPage.clickOnStartSessionWithoutCameraButton();
		advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
		String sClientUrl = advisorPage.getClientUrl();

		invokeNewTabForClient();
		getBrowser().get(sClientUrl);
		clientPage.verifyThatClientNoteSectionIsDisplayed();
		clientPage.verifyThatCameraNotAccessibleTextIsDisplayed();
		clientPage.verifyThatJoinNowWithoutCameraButtonIsDisplayed();
		String clientName = clientPage.enterClientName(clientname);
		clientPage.clickOnJoinNowWithoutCameraButton();
		clientPage.verifyThatYourHostMessageIsDisplayed(advisorname);

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(clientName);
		advisorPage.clickOnAdmitButton();
		advisorPage.verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView(clientName);

		clientPage.switchToClientTab(1);
		clientPage.verifyThatClientHasJoinedMeeting(clientname);
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();

        advisorPage.switchToAdvisorTab();
        advisorPage.verifyCameraButtonIsMuted();
        clientPage.switchToClientTab(1);
        clientPage.verifyCameraButtonIsMuted();

        advisorPage.switchToAdvisorTab();
		advisorPage.clickOnProductSearchIcon();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatProductSearchPopupIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVeilLayerIsDisplayed();
		clientPage.switchToInframeContainer();
		clientPage.verifyThatClientIsUnableToClickOnProductSearchComponents();

		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test(groups = "SmokeTest")
	public void TC_C40536_ViewAdvisorVideoInSessionForParticipants()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ClientURL").replace("testing", "testing"+randomAlphabet);

		getBrowser().get(sAdvisorUrl);
		advisorPage.verifyThatStartSessionButtonIsDisplayed();
		advisorPage.enterAdvisorName(advisorname);
		advisorPage.verifyLoungeCameraButtonIsDisabled();
		advisorPage.clickOnStartSessionButton();

		advisorPage.verifyThatAdvisorHasJoinedMeeting(advisorname);
		advisorPage.verifyCameraButtonIsMuted();
		advisorPage.verifyAdvisorLocalVideoIsNotStreamingAssertFirstTime();
		String advisorRoomID = advisorPage.getAdvisorParticipantId();

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
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();
		clientPage.verifyClientLocalVideoIsStreaming();
		String clientRoomID = clientPage.getClientParticipantId();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyClientRemoteVideoIsStreaming(clientRoomID);
		clientPage.switchToClientTab(1);
		clientPage.verifyAdvisorRemoteVideoIsNotStreamingForFirstTime(advisorRoomID);

		advisorPage.switchToAdvisorTab();
		advisorPage.clickCameraButton();
		advisorPage.verifyInMeetingCameraButtonIsEnabled();
		advisorPage.verifyAdvisorLocalVideoIsStreaming();
		advisorPage.verifyClientRemoteVideoIsStreaming(clientRoomID);

		clientPage.switchToClientTab(1);
		clientPage.verifyClientLocalVideoIsStreaming();
		clientPage.verifyAdvisorRemoteVideoIsStreaming(advisorRoomID);

		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}
    
}
