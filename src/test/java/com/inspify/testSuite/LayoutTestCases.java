package com.inspify.testSuite;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(com.inspify.utilities.TestngListener.class)

public class LayoutTestCases extends BaseSetup {

    AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname, clientname, secondClientName, thirdClientName;
	static ConfigManager app = new ConfigManager("App");
	Logger log = Logger.getLogger(LayoutTestCases.class);

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();
	}

    @Test(groups = "SmokeTest")
	public void TC_C40540_ViewCorrectLayoutWhenAdvisorOpensDocumentFiles()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
        secondClientName = app.getProperty("SecondClientName");
        thirdClientName = app.getProperty("ThirdClientName");
		String sAdvisorUrl = app.getProperty("TC_C40540_IWCADVISORURL");
		String sClientUrl = app.getProperty("TC_C40540_IWCCLIENTURL");

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
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();

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
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();

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
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatAdvisorIsInLocalContainer(advisorname);
		advisorPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		advisorPage.verifyThatClientIsInRemoteContainer(clientname);
		advisorPage.verifyThatClientIsInRemoteContainer(secondClientName);
		advisorPage.verifyThatClientIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(1);
		clientPage.verifyThatClientIsInLocalContainer(clientname);
		clientPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(secondClientName);
		clientPage.verifyThatParticipantsIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(2);
		clientPage.verifyThatClientIsInLocalContainer(secondClientName);
		clientPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(clientname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(3);
		clientPage.verifyThatClientIsInLocalContainer(thirdClientName);
		clientPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(secondClientName);
		clientPage.verifyThatParticipantsIsInRemoteContainer(clientname);

		advisorPage.switchToAdvisorTab();
		advisorPage.ClickOnDocumentViewer();
		advisorPage.clickOnVideoDocument();
		advisorPage.verifyThatVideoDocumentIsOpened();
		advisorPage.verifyThatVideoIsPlayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoDocumentIsOpened();
		clientPage.verifyThatVideoIsPlayed();

		clientPage.switchToClientTab(2);
		clientPage.verifyThatVideoDocumentIsOpened();
		clientPage.verifyThatVideoIsPlayed();

		clientPage.switchToClientTab(3);
		clientPage.verifyThatVideoDocumentIsOpened();
		clientPage.verifyThatVideoIsPlayed();

		int loop = 1;
		while(loop != 0){
			advisorPage.switchToAdvisorTab();
			boolean advisorViewVideoEndedValue = advisorPage.verifyThatVideoIsPlayingOrNot();

			if(advisorViewVideoEndedValue || loop == 300){
//				if(loop == 300){
//					break;
//				}
//				for(int i=1; i<=3; i++) {
//					clientPage.switchToClientTab(i);
//                    System.out.println("Client Tab: "+i);
//					clientPage.verifyThatVideoIsPlayingOrNot();
//				}
//				log.info("Video has ended for all participants");
				break;
			}
			else if(loop == 1){
				advisorPage.switchToAdvisorTab();
				advisorPage.verifyThatAdvisorIsNotInLocalContainer(advisorname);
				advisorPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
				advisorPage.verifyThatClientIsInRemoteContainer(clientname);
				advisorPage.verifyThatClientIsInRemoteContainer(secondClientName);
				advisorPage.verifyThatClientIsInRemoteContainer(thirdClientName);

				clientPage.switchToClientTab(1);
				clientPage.verifyThatClientIsInLocalContainer(clientname);
				clientPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
				clientPage.verifyThatAdvisorIsNotPresentInRemoteContainer(advisorname);
				clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(secondClientName);
				clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(thirdClientName);

				clientPage.switchToClientTab(2);
				clientPage.verifyThatClientIsInLocalContainer(secondClientName);
				clientPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
				clientPage.verifyThatAdvisorIsNotPresentInRemoteContainer(advisorname);
				clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(clientname);
				clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(thirdClientName);

				clientPage.switchToClientTab(3);
				clientPage.verifyThatClientIsInLocalContainer(thirdClientName);
				clientPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
				clientPage.verifyThatAdvisorIsNotPresentInRemoteContainer(advisorname);
				clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(secondClientName);
				clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(clientname);
			}
			log.info("Trail " +loop+ " - Video is still playing");
			loop++;
		}

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatAdvisorIsNotInLocalContainer(advisorname);
		advisorPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
		advisorPage.verifyThatClientIsInRemoteContainer(clientname);
		advisorPage.verifyThatClientIsInRemoteContainer(secondClientName);
		advisorPage.verifyThatClientIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(1);
		clientPage.verifyThatClientIsInLocalContainer(clientname);
		clientPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
		clientPage.verifyThatAdvisorIsNotPresentInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(secondClientName);
		clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(thirdClientName);

		clientPage.switchToClientTab(2);
		clientPage.verifyThatClientIsInLocalContainer(secondClientName);
		clientPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
		clientPage.verifyThatAdvisorIsNotPresentInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(clientname);
		clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(thirdClientName);

		clientPage.switchToClientTab(3);
		clientPage.verifyThatClientIsInLocalContainer(thirdClientName);
		clientPage.verifyThatAdvisorIsPresentInLayoutMode(advisorname);
		clientPage.verifyThatAdvisorIsNotPresentInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(secondClientName);
		clientPage.verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(clientname);

		advisorPage.switchToAdvisorTab();
		advisorPage.closePresentationPopup();
		advisorPage.verifyThatAdvisorIsInLocalContainer(advisorname);
		advisorPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		advisorPage.verifyThatClientIsInRemoteContainer(clientname);
		advisorPage.verifyThatClientIsInRemoteContainer(secondClientName);
		advisorPage.verifyThatClientIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(1);
		clientPage.verifyThatClientIsInLocalContainer(clientname);
		clientPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(secondClientName);
		clientPage.verifyThatParticipantsIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(2);
		clientPage.verifyThatClientIsInLocalContainer(secondClientName);
		clientPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(clientname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(thirdClientName);

		clientPage.switchToClientTab(3);
		clientPage.verifyThatClientIsInLocalContainer(thirdClientName);
		clientPage.verifyAdvisorIsNotInPresentingLayoutMode(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(advisorname);
		clientPage.verifyThatParticipantsIsInRemoteContainer(secondClientName);
		clientPage.verifyThatParticipantsIsInRemoteContainer(clientname);

		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}
    
}
