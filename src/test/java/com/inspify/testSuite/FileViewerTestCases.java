package com.inspify.testSuite;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.inspify.pages.AdvisorPage;
import com.inspify.pages.ClientPage;
import com.inspify.utilities.BaseSetup;
import com.inspify.utilities.ConfigManager;
import com.inspify.utilities.UtilityMethods;
@Listeners(com.inspify.utilities.TestngListener.class)

public class FileViewerTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");
	String sessionTopic = RandomStringUtils.randomAlphabetic(8);

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();
	}

	@Test
	public void TC_C4870_ViewTheFileViewerVideoPresentationScreenPopup()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("ChopardAdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ChopardClientURL").replace("testing", "testing"+randomAlphabet);
		String videoClassValue = app.getProperty("TC_expectedFileViewer_Video_Value");

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
		clientPage.verifyThatDashboardIconIsNotDisplayed();
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnDashboardIcon();
		advisorPage.verifyThatDashboardMenuIsDisplayed();
		advisorPage.clickOnIntroductionMenuButton();
		advisorPage.verifyThatDocumentViewerContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatDocumentViewerContainerIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		advisorPage.verifyMicrophoneButtonIsUnMuted();
		advisorPage.verifyThatSpecifiedSlideViewerIsActive(0);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();
		clientPage.verifyMicrophoneButtonIsUnMuted();

		advisorPage.switchToAdvisorTab();
		String sImageSource = advisorPage.getImageSource();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);

		advisorPage.switchToAdvisorTab();
		int totalSlideCount =  advisorPage.getSlideCount();
		System.out.println(totalSlideCount);

		for(int i=0;i<totalSlideCount;i++){
			advisorPage.switchToAdvisorTab();
			advisorPage.clickOnSpecifiedSlideIndex(i);
			advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);

			String classValue= advisorPage.getSlideLocatorValue();
			if(classValue.contains(videoClassValue)) {
				advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);
				advisorPage.clickOnNotificationButton();
				advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsMuted();
				clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.verifyMicrophoneButtonIsMuted();
				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsMuted();

				advisorPage.switchToAdvisorTab();
				advisorPage.pauseVideo();
				advisorPage.verifyThatVideoIsPaused();
				clientPage.switchToClientTab(1);
				clientPage.verifyThatVideoIsPaused();

				advisorPage.switchToAdvisorTab();
				advisorPage.verifyMicrophoneButtonIsUnMuted();
				advisorPage.verifyThatNotificationButtonIsNotDisplayed();
				advisorPage.verifyThatMicrophoneMutedMessageIsNotDisplayed();

				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsUnMuted();
				clientPage.verifyThatMicrophoneMutedMessageIsNotDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.playVideo();
				advisorPage.verifyThatVideoIsPlayed();
				advisorPage.clickOnNotificationButton();
				advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				clientPage.switchToClientTab(1);
				clientPage.verifyThatVideoIsPlayed();
				clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.verifyMicrophoneButtonIsMuted();
				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsMuted();
				break;
			}
		}

		advisorPage.switchToAdvisorTab();
		advisorPage.waitForPageToLoad(LONGWAIT);
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.closeProductPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatProductsPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}
	
	@Test(groups = "SmokeTest")
	public void TC_C4871_ViewTheFileViewerImagePresentationScreenPopup()  {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("ChopardAdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ChopardClientURL").replace("testing", "testing"+randomAlphabet);
		
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
		
		clientPage.verifyThatDashboardIconIsNotDisplayed();
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();
		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnDashboardIcon();
		advisorPage.verifyThatDashboardMenuIsDisplayed();
		
		advisorPage.clickOnIntroductionMenuButton();
		advisorPage.verifyThatDocumentViewerContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatDocumentViewerContainerIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();
		
		for(int i=0;i<2;i++){
			advisorPage.switchToAdvisorTab();
			advisorPage.clickOnSpecifiedSlideIndex(i);
			advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);

			String sImageSource = advisorPage.getImageSource();
			System.out.println(sImageSource);
			clientPage.switchToClientTab(1);
			clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);
		}
		
		advisorPage.switchToAdvisorTab();
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.closeProductPopup();
		clientPage.switchToClientTab(1);
	  	clientPage.verifyThatProductsPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}
	
	@Test
	public void TC_C4872_ViewTheFileViewerImageAndVideoPresentationScreenPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("ChopardAdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sClientUrl = app.getProperty("ChopardClientURL").replace("testing", "testing"+randomAlphabet);
		String videoClassValue = app.getProperty("TC_expectedFileViewer_Video_Value");
		
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
		
		clientPage.verifyThatDashboardIconIsNotDisplayed();
		clientPage.verifyThatSideMenuButtonIsNotDisplayed();
		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnDashboardIcon();
		advisorPage.verifyThatDashboardMenuIsDisplayed();
		
		advisorPage.clickOnIntroductionMenuButton();
		advisorPage.verifyThatDocumentViewerContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatDocumentViewerContainerIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();
		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSpecifiedSlideViewerIsActive(0);
		
		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsUnMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsUnMuted();
		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();
		
		advisorPage.switchToAdvisorTab();
		int totalSlideCount =  advisorPage.getSlideCount();
		System.out.println(totalSlideCount);
		
		for(int i=0;i<totalSlideCount;i++){
			advisorPage.switchToAdvisorTab();
			advisorPage.clickOnSpecifiedSlideIndex(i);
			advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);
			
			String classValue= advisorPage.getSlideLocatorValue();
			if(classValue.contains(videoClassValue)) {
				advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);
				advisorPage.clickOnNotificationButton();
				advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsMuted();
				clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.verifyMicrophoneButtonIsMuted();
				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsMuted();

				advisorPage.switchToAdvisorTab();
				advisorPage.pauseVideo();
				advisorPage.verifyThatVideoIsPaused();
				clientPage.switchToClientTab(1);
				clientPage.verifyThatVideoIsPaused();

				advisorPage.switchToAdvisorTab();
				advisorPage.verifyMicrophoneButtonIsUnMuted();
				advisorPage.verifyThatNotificationButtonIsNotDisplayed();
				advisorPage.verifyThatMicrophoneMutedMessageIsNotDisplayed();

				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsUnMuted();
				clientPage.verifyThatMicrophoneMutedMessageIsNotDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.playVideo();
				advisorPage.verifyThatVideoIsPlayed();
				advisorPage.clickOnNotificationButton();
				advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				clientPage.switchToClientTab(1);
				clientPage.verifyThatVideoIsPlayed();
				clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.verifyMicrophoneButtonIsMuted();
				clientPage.switchToClientTab(1);
				clientPage.verifyMicrophoneButtonIsMuted();
			}
			else {
				advisorPage.switchToAdvisorTab();
				advisorPage.clickOnSpecifiedSlideIndex(i);
				advisorPage.verifyThatSpecifiedSlideViewerIsActive(i);

				String sImageSource = advisorPage.getImageSource();
				clientPage.switchToClientTab(1);
				clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);
			}
		}
		
		advisorPage.switchToAdvisorTab();
		advisorPage.waitForPageToLoad(LONGWAIT);
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.closeProductPopup();
		clientPage.switchToClientTab(1);
	  	clientPage.verifyThatProductsPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test(groups = "SmokeTest")
	public void TC_C34934_ViewTheContentVideoPresentationScreenWhenItPlayedAgain() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String sAdvisorUrl = app.getProperty("TC_C34934_IWCADVISORURL");
		String sClientUrl = app.getProperty("TC_C34934_IWCCLIENTURL");

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
		advisorPage.clickOnVideoDocument();
		advisorPage.verifyThatVideoDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoDocumentIsOpened();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatVideoIsPlayed();
		advisorPage.clickOnNotificationButton();
		advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();
		advisorPage.verifyMicrophoneButtonIsMuted();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();
		clientPage.clickOnNotificationButton();
		clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();
		clientPage.verifyMicrophoneButtonIsMuted();

		int loop = 1;
		while(loop != 0){
			advisorPage.switchToAdvisorTab();
			boolean advisorViewVideoEndedValue = advisorPage.verifyThatVideoHasEnded(); 
			if(advisorViewVideoEndedValue){
				clientPage.switchToClientTab(1);
				clientPage.verifyThatVideoHasEnded();
				System.out.println("Video has ended on both sides");
				break;
			}
			System.out.println("Trail " +loop+ " Video is still playing");
			loop++;
		}

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.waitForPageToLoad(MEDIUMWAIT);
		advisorPage.ClickOnDocumentViewer();
		advisorPage.clickOnVideoDocument();
		advisorPage.verifyThatVideoDocumentIsOpened();
		advisorPage.clickOnNotificationButton();
		advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoDocumentIsOpened();
		clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatVideoIsPlayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test(groups = "SmokeTest")
	public void TC_C34935_ViewTheContentVideoPresentationScreenPauseForParticipants() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String sAdvisorUrl = app.getProperty("TC_C34935_IWCADVISORURL");
		String sClientUrl = app.getProperty("TC_C34935_IWCCLIENTURL");

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
		advisorPage.clickOnVideoDocument();
		advisorPage.verifyThatVideoDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoDocumentIsOpened();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatVideoIsPlayed();
		advisorPage.clickOnNotificationButton();
		advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();
		clientPage.clickOnNotificationButton();
		clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();

		advisorPage.switchToAdvisorTab();
		advisorPage.pauseVideo();
		advisorPage.verifyThatVideoIsPaused();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPaused();

		advisorPage.switchToAdvisorTab();
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test
	public void TC_C34936_ViewTheContentVideoPresentationResumeForParticipants() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String sAdvisorUrl = app.getProperty("TC_C34936_IWCADVISORURL");
		String sClientUrl = app.getProperty("TC_C34936_IWCCLIENTURL");

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
		clientPage.verifyThatClientHasJoinedMeeting(advisorname);
		clientPage.verifyThatSideMenuNavigatorIconIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.waitForPageToLoad(MEDIUMWAIT);
		advisorPage.ClickOnDocumentViewer();
		// clientPage.switchToClientTab();
		// clientPage.verifyThatDocumentIconIsNotDisplayed();

		// advisorPage.switchToAdvisorTab();
		// advisorPage.verifyThatToolTipWrapperIsDisplayed();
		advisorPage.clickOnVideoDocument();
		advisorPage.verifyThatVideoDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoDocumentIsOpened();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatVideoIsPlayed();
		advisorPage.clickOnNotificationButton();
		advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();
		clientPage.clickOnNotificationButton();
		clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();

		advisorPage.switchToAdvisorTab();
		advisorPage.pauseVideo();
		advisorPage.verifyThatVideoIsPaused();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPaused();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsUnMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsUnMuted();

		advisorPage.switchToAdvisorTab();
		advisorPage.playVideo();
		advisorPage.verifyThatVideoIsPlayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();

		advisorPage.switchToAdvisorTab();
		boolean videoEndedValue = advisorPage.verifyThatVideoHasEnded();
		if(videoEndedValue) {
			System.out.println("Video has ended no need to close presentation popup");
		}
		else{
			advisorPage.closePresentationPopup();
			clientPage.switchToClientTab(1);
			clientPage.verifyThatPresentationPopupIsNotDisplayed();
		}

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test(groups = "SmokeTest")
	public void TC_C40809_VerifyAdvisorCanMakeTheSlideInvisible () {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String sAdvisorUrl = app.getProperty("TC_C40809_IWCADVISORURL");
		String sClientUrl = app.getProperty("TC_C40809_IWCCLIENTURL");

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
		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnSpecifiedSlideIndex(1);
		advisorPage.verifyThatSpecifiedSlideViewerIsActive(1);

		advisorPage.hideSlide(1);
		advisorPage.verifySlideIsHidden(1);
		advisorPage.verifyThatSpecifiedSlideViewerIsActive(2);

		String sImageSource = advisorPage.getImageSource();
		System.out.println(sImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test
	public void TC_C40840_ViewStorybooksInMeeting () {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String sAdvisorUrl = app.getProperty("TC_C40840_IWCADVISORURL");
		String sClientUrl = app.getProperty("TC_C40840_IWCCLIENTURL");
		String username = app.getProperty("Hub_Username");
		String password = app.getProperty("Hub_Password");

		getBrowser().get(sAdvisorUrl);
		advisorPage.verifyHubPageIsLaunched();
		advisorPage.enterUsername(username);
		advisorPage.enterPassword(password);
		advisorPage.clickSignInButton();

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
		advisorPage.clickOnOpenStorybooksButton();

		advisorPage.openStorybookToMeet();
		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		String sImageSource = advisorPage.getImageSource();
		System.out.println(sImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnSpecifiedSlideIndex(1);
		advisorPage.verifyThatSpecifiedSlideViewerIsActive(1);

		advisorPage.verifyThatVideoIsPlayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnNotificationButton();
		advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();
		clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();

		advisorPage.switchToAdvisorTab();
		advisorPage.waitForPageToLoad(LONGWAIT);
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.ClickOnDocumentViewer();

		advisorPage.clickOnOpenStorybooksButton();
		advisorPage.clickOnFavoritesInHub();
		advisorPage.clickOnOpenStorybooksButton();

		advisorPage.openStorybookToMeet();
		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		String kImageSource = advisorPage.getImageSource();
		System.out.println(sImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(kImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test
	public void TC_C40841_ViewStorybooksWithSameTitleInMeetings () {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String sHubUrl = app.getProperty("Hub_Url");
		String username = app.getProperty("Hub_Username");
		String password = app.getProperty("Hub_Password");

		getBrowser().get(sHubUrl);
		advisorPage.verifyHubPageIsLaunched();
		advisorPage.enterUsername(username);
		advisorPage.enterPassword(password);
		advisorPage.clickSignInButton();

		advisorPage.clickOnSessionCreateButton();
		advisorPage.enterSessionName(sessionTopic);
		advisorPage.deSelectCheckBoxes();

		String sClientUrl = advisorPage.getHubGuestUrl();
		advisorPage.clickOnShowMoreButton();
		String sAdvisorUrl = advisorPage.getHubHostUrl();
		advisorPage.addStorybooksToSession();
		advisorPage.saveSession();

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
		advisorPage.clickOnContentFiles(1);

		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		String sImageSource = advisorPage.getImageSource();
		System.out.println(sImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.ClickOnDocumentViewer();

		advisorPage.clickOnContentFiles(2);
		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		String kImageSource = advisorPage.getImageSource();
		System.out.println(kImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(kImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.verifySameContentFilesAreNotDisplayed(sImageSource, kImageSource);
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

	@Test
	public void TC_C40862_ViewStorybooksWithoutCreatingSession () {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("InspifyTestAdvisorURL").replace("testing", "testing"+randomAlphabet);;
		String sClientUrl = app.getProperty("InspifyTestClientURL").replace("testing", "testing"+randomAlphabet);;
		String sHubUrl = app.getProperty("Hub_Url");
		String username = app.getProperty("Hub_Username");
		String password = app.getProperty("Hub_Password");

		getBrowser().get(sHubUrl);
		advisorPage.verifyHubPageIsLaunched();
		advisorPage.enterUsername(username);
		advisorPage.enterPassword(password);
		advisorPage.clickSignInButton();

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
		advisorPage.openStorybookToMeet();

		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		String sImageSource = advisorPage.getImageSource();
		System.out.println(sImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(sImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyThatSliderViewContainerIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSliderViewContainerIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnSpecifiedSlideIndex(1);
		advisorPage.verifyThatSpecifiedSlideViewerIsActive(1);

		advisorPage.verifyThatVideoIsPlayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVideoIsPlayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnNotificationButton();
		advisorPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();
		clientPage.verifyThatMicrophoneMutedMessageIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.verifyMicrophoneButtonIsMuted();
		clientPage.switchToClientTab(1);
		clientPage.verifyMicrophoneButtonIsMuted();

		advisorPage.switchToAdvisorTab();
		advisorPage.waitForPageToLoad(LONGWAIT);
		advisorPage.closePresentationPopup();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatPresentationPopupIsNotDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.ClickOnDocumentViewer();
		advisorPage.clickOnFavoritesInHub();

		advisorPage.openStorybookToMeet();
		advisorPage.verifyThatImageDocumentIsOpened();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatImageDocumentIsOpened();

		String kImageSource = advisorPage.getImageSource();
		System.out.println(sImageSource);
		clientPage.switchToClientTab(1);
		clientPage.verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(kImageSource);

		advisorPage.switchToAdvisorTab();
		advisorPage.clickOnMeetingControlButton();
		advisorPage.endMeeting();
		advisorPage.verifyThatMeetingEnded();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatMeetingEnded();
	}

}
