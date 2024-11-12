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

public class ProductDetailsTestCases extends BaseSetup{
	
	AdvisorPage advisorPage;
	ClientPage clientPage;
	String advisorname,clientname;
	static ConfigManager app = new ConfigManager("App");

	@BeforeMethod(alwaysRun = true)
	public void initialize() {
		getBrowser().manage().deleteAllCookies();		
	}
	
	@Test
	public void TC_C2260_ViewProductDetailsScreenPopup() {
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
		advisorPage.clickOnProductSearchMenuItem();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatProductSearchPopupIsDisplayed();
		clientPage.switchToClientTab(1);
		clientPage.verifyThatVeilLayerIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnFirstProductModelLink();
		advisorPage.verifyThatProductDetailsPopupIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatDescriptionSectionIsDisplayedOnProductDetailsPopup();
		advisorPage.verifyThatSpecificationSectionIsDisplayedOnProductDetailsPopup();
		//advisorPage.verifyThatMakeAppointmentButtonIsDisplayedOnProductDetailsPopup();
		
		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatClientIsUnableToClickOnDescriptionSectionOfProductDetailsPopup();
		clientPage.verifyThatClientIsUnableToClickOnSpecificationSectionOfProductDetailsPopup();
		//clientPage.verifyThatClientIsUnableToClickOnMakeAppointmentButtonOfProductDetailsPopup();
		
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
	public void TC_C2261_ViewDescriptionOnProductDetailsScreenPopup() {
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
		advisorPage.clickOnFirstProductModelLink();
		advisorPage.verifyThatProductDetailsPopupIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatDescriptionSectionIsDisplayedOnProductDetailsPopup();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatClientIsUnableToClickOnDescriptionSectionOfProductDetailsPopup();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnDescriptionSection();
		advisorPage.verifyThatDescriptionSectionOnProductDetailsPopupIsExpanded();
		advisorPage.verifyThatDescriptionTextIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatDescriptionSectionOnProductDetailsPopupIsExpanded();
		clientPage.verifyThatDescriptionTextIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnDescriptionSection();
		advisorPage.verifyThatDescriptionSectionOnProductDetailsPopupIsCollapsed();
		advisorPage.verifyThatDescriptionTextIsNotDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatDescriptionSectionOnProductDetailsPopupIsCollapsed();
		clientPage.verifyThatDescriptionTextIsNotDisplayed();

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
	public void TC_C2262_ViewSpecificationOnProductDetailsScreenPopup() {
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
		advisorPage.clickOnFirstProductModelLink();
		advisorPage.verifyThatProductDetailsPopupIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.verifyThatSpecificationSectionIsDisplayedOnProductDetailsPopup();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatClientIsUnableToClickOnSpecificationSectionOfProductDetailsPopup();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnSpecificationSection();
		advisorPage.verifyThatSpecificationSectionOnProductDetailsPopupIsExpanded();
		advisorPage.verifyThatSpecificationTextIsDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSpecificationSectionOnProductDetailsPopupIsExpanded();
		clientPage.verifyThatSpecificationTextIsDisplayed();
		
		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnSpecificationSection();
		advisorPage.verifyThatSpecificationSectionOnProductDetailsPopupIsCollapsed();
		advisorPage.verifyThatSpecificationTextIsNotDisplayed();

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSpecificationSectionOnProductDetailsPopupIsCollapsed();
		clientPage.verifyThatSpecificationTextIsNotDisplayed();

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
	public void TC_C2263_ViewProductImageCarouselItemsOnProductDetailsScreenPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String sSearchText = app.getProperty("TC_ProductSearchText");

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

		clientPage.switchToClientTab(1);
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
		advisorPage.searchForSpecifiedModelCodeOnProductSearchPopup(sSearchText);
		advisorPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);

		clientPage.switchToClientTab(1);
		clientPage.switchToInframeContainer();
		clientPage.verifyThatSpecifiedTextIsPresentInProductSearchInputField(sSearchText);

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		advisorPage.clickOnFirstProductModelLink();
		advisorPage.verifyThatProductDetailsPopupIsDisplayed();

		advisorPage.switchToAdvisorTab();
		advisorPage.switchToInframeContainer();
		int totalImageCarouselItemsCount = advisorPage.getCountOfImageCarouselItems();
		System.out.println("No.of image carousel items: "+totalImageCarouselItemsCount);
		for(int i=0;i<totalImageCarouselItemsCount;i++) {
			advisorPage.switchToAdvisorTab();
			advisorPage.switchToInframeContainer();
			advisorPage.verifyThatSpecifiedProductImagecarouselItemIsActive(i);
			clientPage.switchToClientTab(1);
			clientPage.switchToInframeContainer();
			clientPage.verifyThatSpecifiedProductImagecarouselItemIsActive(i);
			if(i!=(totalImageCarouselItemsCount-1)) {
				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				advisorPage.clickOnRightSideArrowOfProductImageCarouselSection();
			}
		}

		for(int i=(totalImageCarouselItemsCount-1);i>=0;i--) {
			advisorPage.switchToAdvisorTab();
			advisorPage.switchToInframeContainer();
			advisorPage.verifyThatSpecifiedProductImagecarouselItemIsActive(i);
			clientPage.switchToClientTab(1);
			clientPage.switchToInframeContainer();
			clientPage.verifyThatSpecifiedProductImagecarouselItemIsActive(i);
			if(i!=0) {
				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				advisorPage.clickOnLeftSideArrowOfProductImageCarouselSection();
			}
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
	public void TC_C2427_ViewColorVariationDetailsScreenPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String showCaseProductCount = app.getProperty("TC_NO_OF_PRODUCTS");
		
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

		int showCaseProduct = Integer.parseInt(showCaseProductCount);
		for(int i=0; i<showCaseProduct; i++){
			advisorPage.switchToAdvisorTab();
			advisorPage.switchToInframeContainer();
			advisorPage.clickOnSpecifiedIndexProductModelLink(i);
			advisorPage.verifyThatProductDetailsPopupIsDisplayed();
			boolean colorVariations = advisorPage.verifyThatVariationSectionIsPresentOrNot();
			if(colorVariations){
				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				int totalColorVariationsCount = advisorPage.getCountOfAllColorVariations();
				System.out.println("No.of color variations displayed: "+totalColorVariationsCount);
				for(int j=1; j<=totalColorVariationsCount; j++){
					advisorPage.switchToAdvisorTab();
					advisorPage.switchToInframeContainer();
					advisorPage.clickOnColorVariationLink(j);

					String advisorViewVariationsProductModelCode = advisorPage.getProductModelCode();
					String advisorViewVariationsProductModelName = advisorPage.getProductName();
					String advisorViewVariationsProductModelPrice = advisorPage.getProductPrice();

					clientPage.switchToClientTab(1);
					clientPage.switchToInframeContainer();
					clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(advisorViewVariationsProductModelCode);
					clientPage.verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(advisorViewVariationsProductModelName);
					clientPage.verifyThatSameProductPriceFromAdvisorViewAreDisplayedInClientView(advisorViewVariationsProductModelPrice);

					advisorPage.switchToAdvisorTab();
					advisorPage.switchToInframeContainer();
					advisorPage.clickOnDescriptionSection();
					advisorPage.verifyThatDescriptionSectionIsDisplayedOnProductDetailsPopup();
					clientPage.switchToClientTab(1);
					clientPage.switchToInframeContainer();
					clientPage.verifyThatClientIsUnableToClickOnDescriptionSectionOfProductDetailsPopup();

					advisorPage.switchToAdvisorTab();
					advisorPage.switchToInframeContainer();
					String advisorViewDescriptionText = advisorPage.getDescriptionSectionText();
					clientPage.switchToClientTab(1);
					clientPage.switchToInframeContainer();
					clientPage.verifyThatSameProductDescriptionFromAdvisorViewAreDisplayedInClientView(advisorViewDescriptionText);

					advisorPage.switchToAdvisorTab();
					advisorPage.switchToInframeContainer();
					advisorPage.clickOnSpecificationSection();
					advisorPage.verifyThatSpecificationSectionIsDisplayedOnProductDetailsPopup();
					clientPage.switchToClientTab(1);
					clientPage.switchToInframeContainer();
					clientPage.verifyThatClientIsUnableToClickOnSpecificationSectionOfProductDetailsPopup();

					advisorPage.switchToAdvisorTab();
					advisorPage.switchToInframeContainer();
					String advisorViewSpecificationText = advisorPage.getSpecificationSectionText();
					clientPage.switchToClientTab(1);
					clientPage.switchToInframeContainer();
					clientPage.verifyThatSameProductSpecificationFromAdvisorViewAreDisplayedInClientView(advisorViewSpecificationText);
				}
				break;
			}
			else {
				String productModelCode = advisorPage.getProductModelCode();
				System.out.println("Color variation is not present for this product, modelcode: "+productModelCode);
				advisorPage.clickOnBackButton();
			}
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
	public void TC_C2428_ViewYouMayAlsoLikeSectionScreenPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName");
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4);
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing"+randomAlphabet);
		String showCaseProductCount = app.getProperty("TC_NO_OF_PRODUCTS");
		
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

		int showCaseProduct = Integer.parseInt(showCaseProductCount);
		for(int i=0; i<showCaseProduct; i++){
			advisorPage.switchToAdvisorTab();
			advisorPage.switchToInframeContainer();
			advisorPage.clickOnSpecifiedIndexProductModelLink(i);
			advisorPage.verifyThatProductDetailsPopupIsDisplayed();
			boolean similarProduct = advisorPage.verifyThatSimilarProductSectionIsPresentOrNot();
			if(similarProduct){
				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				advisorPage.scrollDownToYouMayAlsoLikeSection();
				advisorPage.clickOnYouMayAlsoLikeProductLink();
				advisorPage.verifyThatProductDetailsPopupIsDisplayed();

				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				String advisorViewYouMayAlsoLikeProductCode = advisorPage.getProductModelCode();
				String advisorViewYouMayAlsoLikeProductName = advisorPage.getProductName();
				String advisorViewYouMayAlsoLikeProductPrice = advisorPage.getProductPrice();

				clientPage.switchToClientTab(1);
				clientPage.switchToInframeContainer();
				clientPage.verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(advisorViewYouMayAlsoLikeProductCode);
				clientPage.verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(advisorViewYouMayAlsoLikeProductName);
				clientPage.verifyThatSameProductPriceFromAdvisorViewAreDisplayedInClientView(advisorViewYouMayAlsoLikeProductPrice);
				break;
			}
			else{
				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				String productModelCode = advisorPage.getProductModelCode();
				System.out.println("Similar section is not present for this product, modelcode: "+productModelCode);
				advisorPage.clickOnBackButton();
			}
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
	public void TC_C2429_ViewInspirationSectionScreenPopup() {
		advisorPage = new AdvisorPage(switchBetweenTabs());
		clientPage = new  ClientPage(switchBetweenTabs());
		advisorname = app.getProperty("AdvisorName");
		clientname = app.getProperty("ClientName"); 
		String randomAlphabet = UtilityMethods.getRandomAlphabet(4); 
		String sAdvisorUrl = app.getProperty("AdvisorURL").replace("testing", "testing" + randomAlphabet);
		String expectedAboutPageTitle = app.getProperty("TC_C2429_aboutPageTitle");
		String expectedInstagramPageTitle = app.getProperty("TC_C2429_instagramPageTitle");
		String expectedFeaturePageTitle = app.getProperty("TC_C2429_featurePageTitle");
		String showCaseProductCount = app.getProperty("TC_NO_OF_PRODUCTS");

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

		int showCaseProduct = Integer.parseInt(showCaseProductCount);
		for(int i=0; i<showCaseProduct; i++){
			advisorPage.switchToAdvisorTab();
			advisorPage.switchToInframeContainer();
			advisorPage.clickOnSpecifiedIndexProductModelLink(i);
			advisorPage.verifyThatProductDetailsPopupIsDisplayed();
			boolean inspirationSection = advisorPage.verifyThatInspirationSectionIsPresentOrNot();
			if(inspirationSection){
				advisorPage.switchToAdvisorTab();
				advisorPage.switchToInframeContainer();
				advisorPage.scrollDownToInspirationSection();
				int totalInspirationImageCarouselItemsCount = advisorPage.getCountOfInspirationImageCarouselItems();

				advisorPage.clickOnInspirationImage();
				String pageTitle = advisorPage.getPageTitle();
				for(int j = totalInspirationImageCarouselItemsCount - 1; j >= 1; j--){
					if(pageTitle.equalsIgnoreCase(expectedAboutPageTitle) | pageTitle.equalsIgnoreCase(expectedInstagramPageTitle) | pageTitle.equalsIgnoreCase(expectedFeaturePageTitle)){
						if(pageTitle.equals(expectedAboutPageTitle)){
							advisorPage.switchToAdvisorTab();
							advisorPage.switchToInframeContainer();
							advisorPage.verifyThatExpectedPopupPageTitleIsDisplayed(pageTitle);
							String advisorViewAboutStoryName = advisorPage.getAboutStoryTitle();
							String advisorViewAboutStoryDescription = advisorPage.getAboutStoryDescription();

							clientPage.switchToClientTab(1);
							clientPage.switchToInframeContainer();
							clientPage.verifyThatSameStoryTitleFromAdvisorViewAreDisplayedInClientView(advisorViewAboutStoryName);
							clientPage.verifyThatSameStoryDescriptionFromAdvisorViewAreDisplayedInClientView(advisorViewAboutStoryDescription);
							break;
						}

						else if(pageTitle.equalsIgnoreCase(expectedInstagramPageTitle) | pageTitle.equals(expectedFeaturePageTitle)){
							advisorPage.switchToAdvisorTab();
							advisorPage.switchToInframeContainer();
							advisorPage.verifyThatExpectedPopupPageTitleIsDisplayed(pageTitle);
							String advisorViewInstagramStoryDescription = advisorPage.getStoryDescription();

							clientPage.switchToClientTab(1);
							clientPage.switchToInframeContainer();
							clientPage.verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(advisorViewInstagramStoryDescription);
							break;
						}
					}
					else{
						advisorPage.switchToAdvisorTab();
						advisorPage.switchToInframeContainer();
						advisorPage.clickOnBackButton();
						advisorPage.switchToAdvisorTab();
						advisorPage.switchToInframeContainer();
						advisorPage.scrollDownToInspirationSection();
						advisorPage.clickOnRightSideArrowOfInspirationImageCarouselSection();
						advisorPage.clickOnInspirationImage();
						pageTitle = advisorPage.getPageTitle();
					}
				}
				break;
			}
			else{
				String productModelCode = advisorPage.getProductModelCode();
				System.out.println("Inspiration section is not present for this product, modelcode: "+productModelCode);
				advisorPage.clickOnBackButton();
			}
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

}
