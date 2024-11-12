package com.inspify.pages;

import com.inspify.locators.ClientPageLocators;
import com.inspify.utilities.SafeActions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ClientPage extends SafeActions implements ClientPageLocators {

	private static final Logger log = Logger.getLogger(ClientPage.class);
	private final WebDriver driver;

	public ClientPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void switchToClientTab(int tabNum) {
		hardWait(VERYSHORTWAIT);
		switchToWindow(tabNum);
	}

	public void verifyThatClientNoteSectionIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementPresent(CLIENTNOTE_SECTION, MEDIUMWAIT), "Client Note section is not displayed in client join screen");
	}

	public String enterClientName(String name) {
		safeClick(NAME_INPUTFIELD, MEDIUMWAIT);
		safeClearAndType(NAME_INPUTFIELD, name, MEDIUMWAIT);
		safePressEnterViaActions(NAME_INPUTFIELD, MEDIUMWAIT);
		safePressTabKey(NAME_INPUTFIELD, MEDIUMWAIT);
		String enteredClientName = safeGetAttribute(NAME_INPUTFIELD, "Value", MEDIUMWAIT);
		for(int i = 1; i <= 5; i++){
			if(!name.equals(enteredClientName.trim())){
				log.info("Clearing the text, displayed text is: "+enteredClientName);
				safeJavaScriptClearType(NAME_INPUTFIELD, name, MEDIUMWAIT);
				safePressEnterViaActions(NAME_INPUTFIELD, MEDIUMWAIT);
				safePressTabKey(NAME_INPUTFIELD, MEDIUMWAIT);
			}
			else
				break;
		}

		return name;
	}

	public void verifyThatJoinNowButtonIsDisplayed() {
		waitForPageToLoad();
		for(int i=1; i<=2; i++) {
			boolean joinNowValue = isElementVisible(JOINNOW_BUTTON, MEDIUMWAIT);
			if(!joinNowValue){
				refresh();
			}
			else {
				Assert.assertTrue(isElementVisible(JOINNOW_BUTTON, MEDIUMWAIT), "Join Now button is not displayed in client join screen");
				break;
			}
		}
	}

	public void clickOnJoinNowButton() {
		waitForPageToLoad(MEDIUMWAIT);
		safeJavaScriptClick(JOINNOW_BUTTON, MEDIUMWAIT);
		for(int i=1; i<=2; i++) {
			boolean joinNowValue = isElementInvisible(JOINNOW_BUTTON, MEDIUMWAIT);
			if(!joinNowValue){
				safeJavaScriptClick(JOINNOW_BUTTON, MEDIUMWAIT);
			}
			else {
				Assert.assertTrue(isElementInvisible(JOINNOW_BUTTON, MEDIUMWAIT), "Join now button is still shown even after clicking join now button");
				break;
			}
		}
	}

	public void verifyThatYourHostMessageIsDisplayed(String advisorName) {
		waitForPageToLoad(MEDIUMWAIT);
		By hostText = By.xpath("//span[normalize-space(text())='Your host today is']/strong[normalize-space(text())='"+advisorName+"']");
		Assert.assertTrue(isElementVisible(hostText, VERYLONGWAIT), "Your host message is not displayed in time "+VERYLONGWAIT+" ");
	}

	public void verifyThatClientHasJoinedMeeting(String clientName) {
		waitForPageToLoad(MEDIUMWAIT);
		By CLIENTNAME = By.xpath("//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+clientName+"']");
		Assert.assertTrue(isElementVisible(CLIENTNAME, MEDIUMWAIT), "Client name is not displayed after joining the meeting");
		Assert.assertTrue(isElementVisible(RAISEHAND_ICON, MEDIUMWAIT), "client is not logged in with client role");
		Assert.assertTrue(isElementPresent(VIEWONLY_ROLEINDICATOR_TEXT, MEDIUMWAIT), "View Only text is not displayed at client side");
	}

	public void switchToInframeContainer() {
		waitForPageToLoad(MEDIUMWAIT);
		selectFrame(IFRAMECONTAINER, LONGWAIT);
	}

	public void VerifyThatIframeContainerIsNotDisplayed(){
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(IFRAMECONTAINER, MEDIUMWAIT), "Iframe Container popup is still displayed");
	}

	public void verifyThatProductsPopupIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(PRODUCTSPOPUP_CROSSICON, MEDIUMWAIT), "Product popup is displayed even after closing the popup in client screen");
	}

	public void verifyThatStorySearchPopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(SEARCH_INPUT, MEDIUMWAIT), "Story search input field is not displayed");
	}

	public void verifyThatProductSearchPopupIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(PRODUCTSEARCH_PAGETITLE, MEDIUMWAIT), "Product search popup is still displayed");
		Assert.assertTrue(isElementInvisible(SEARCH_INPUT, MEDIUMWAIT), "Product search input field is still displayed");
	}

	public void verifyThatStorySearchPopupIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(STORYSEARCH_PAGETITLE, MEDIUMWAIT), "Story search popup is still displayed");
		Assert.assertTrue(isElementInvisible(SEARCH_INPUT, MEDIUMWAIT), "Story search input field is still displayed");
	}

	public void verifyThatClientIsUnableToClickOnProductSearchComponents() {
		Assert.assertTrue(isElementVisible(SEARCH_INPUT, MEDIUMWAIT), "Search input is not displayed");
		Assert.assertFalse(isUIComponentClickable(SEARCH_INPUT, MEDIUMWAIT), "Search input icon is clickable");
		Assert.assertTrue(isElementVisible(FIRSTPRODUCT_MODELTEXT, MEDIUMWAIT), "First search result is not displayed");
		Assert.assertFalse(isUIComponentClickable(FIRSTPRODUCT_MODELTEXT, MEDIUMWAIT), "First search result is clickable");
	}

	public void verifyThatEmptyProductCompareMessageIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(EMPTYMESSAGE_COMPAREPRODUCTSPOPUP_TEXT, MEDIUMWAIT), "YOU DON'T HAVE ANY PRODUCTS TO BE COMPARED text is not displayed");
	}

	public void verifyThatEmptyProductCompareMessageIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(EMPTYMESSAGE_COMPAREPRODUCTSPOPUP_TEXT, MEDIUMWAIT), "YOU DON'T HAVE ANY PRODUCTS TO BE COMPARED text is still displayed");
	}

	public void verifyThatClientIsUnableToClickOnProductCompareEmptyPopupComponents() {
		Assert.assertTrue(isElementVisible(SEARCHPRODUCTS_BUTTON, MEDIUMWAIT), "Search product button is not displayed");
		Assert.assertFalse(isUIComponentClickable(SEARCHPRODUCTS_BUTTON, MEDIUMWAIT), "Search product button is clickable");
	}

	public void verifyThatSideMenuNavigatorIconIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(SIDEMENUNAVIGATOR_ICON, MEDIUMWAIT), "Side menu navigator is displayed in client screen");
	}

	public void verifyThatSideMenuSectionIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(PRODUCTSEARCH_MENUITEM, MEDIUMWAIT), "Product Search menu item is displayed in client screen");
		Assert.assertTrue(isElementInvisible(PRODUCTCOMPARE_MENUITEM, MEDIUMWAIT), "Product Compare menu item is displayed in client screen");
	}

	public void verifyThatMeetingEnded() {
		hardWait(VERYSHORTWAIT);
		Assert.assertTrue(isElementInvisible(RAISEHAND_ICON, MEDIUMWAIT), "Meeting didn't end on client side");
	}

	public void verifyThatClientIsUnableToClickOnDescriptionSectionOfProductDetailsPopup() {
		Assert.assertTrue(isElementVisible(DESCRIPTIONSECTION, LONGWAIT), "Description section is not displayed");
		Assert.assertFalse(isUIComponentClickable(DESCRIPTIONSECTION, MEDIUMWAIT), "Description section is clickable on client view");
	}

	public void verifyThatClientIsUnableToClickOnSpecificationSectionOfProductDetailsPopup() {
		Assert.assertTrue(isElementVisible(SPECIFICATIONSSECTION, MEDIUMWAIT), "Specification section is not displayed");
		Assert.assertFalse(isUIComponentClickable(SPECIFICATIONSSECTION, MEDIUMWAIT), "Specification section is clickable on client view");
	}

	public void verifyThatVeilLayerIsDisplayed() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(VEIL_LAYYER, MEDIUMWAIT), "Veil layer is not displayed in client screen");
	}

	public void verifyThatDescriptionSectionOnProductDetailsPopupIsExpanded() {
		Assert.assertTrue(isElementVisible(DESCRIPTIONSECTION_EXPANDED, MEDIUMWAIT), "Description section is not expanded");
	}

	public void verifyThatDescriptionSectionOnProductDetailsPopupIsCollapsed() {
		Assert.assertTrue(isElementPresent(DESCRIPTIONSECTION_COLLAPSED, MEDIUMWAIT), "Description section is not collapsed");
	}

	public void verifyThatDescriptionTextIsDisplayed() {
		String sText = safeGetText(DESCRIPTIONTEXT, MEDIUMWAIT);
		Assert.assertTrue(sText.length()>1, "Description text is not displayed");
	}

	public void verifyThatDescriptionTextIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(DESCRIPTIONTEXT, MEDIUMWAIT), "Description text is displayed on client side");
	}

	public void verifyThatSpecificationSectionOnProductDetailsPopupIsExpanded() {
		Assert.assertTrue(isElementVisible(SPECIFICATIONSECTION_EXPANDED, MEDIUMWAIT), "Specification section is not expanded");
	}

	public void verifyThatSpecificationSectionOnProductDetailsPopupIsCollapsed() {
		Assert.assertTrue(isElementPresent(SPECIFICATIONSECTION_COLLAPSED, MEDIUMWAIT), "Specification section is not collapsed");
	}

	public void verifyThatSpecificationTextIsDisplayed() {
		String sText = safeGetText(SPECIFICATIONSTEXT, MEDIUMWAIT);
		Assert.assertTrue(sText.length()>1, "Specification text is not displayed");
	}

	public void verifyThatSpecificationTextIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(SPECIFICATIONSTEXT, MEDIUMWAIT), "Specification text is displayed on advisor side");
	}

	public void verifyThatSpecifiedProductImagecarouselItemIsActive(int sIndex) {
		By sActiveItem = By.xpath("//li[@data-index='"+sIndex+"'][contains(@class,'react-multi-carousel-item--active')]");
		Assert.assertTrue(isElementVisible(sActiveItem, MEDIUMWAIT), "Specified image carousel item with index "+sIndex+" is not visible");
	}

	public void verifyThatSpecifiedTextIsPresentInProductSearchInputField(String sText) {
		waitForPageToLoad(SHORTWAIT);
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		Assert.assertTrue(sSearchText.toLowerCase().contains(sText.toLowerCase()), "Expected search text:"+sText+" is not displayed in product search field in client view");
	}

	public ArrayList<String> getTitlesOfAllDisplayedProducts() {
		waitForPageToLoad();
		List<WebElement> allProductTitlesElements =  LocatorWebElements(ALLPRODUCTSMODELTITLE);
		ArrayList<String> allProductTitlesText  = new ArrayList<>();
		for (WebElement allProductTitlesElement : allProductTitlesElements) {
			String text = allProductTitlesElement.getText().trim();
			allProductTitlesText.add(text);
		}
		return allProductTitlesText;
	}

	public void verifyThatSameSearchResultsFromAdvisorViewAreDisplayedInClientView(ArrayList<String> advisorViewResults, ArrayList<String> clientViewResults) {
		Assert.assertEquals(advisorViewResults, clientViewResults, "Advisor view search results are not matching with client view search results. Advisor View Results:"+advisorViewResults+
				", Client View Results:"+clientViewResults);
	}

	public void verifyThatProductSearchInputFieldIsEmpty() {
		waitForPageToLoad();
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		Assert.assertTrue(sSearchText.toLowerCase().contains(""), "product search field is not empty");
	}

	public int getCountOfAllDisplayedProducts() {
		waitForPageToLoad();
		return getLocatorCount(ALLPRODUCTSMODELTITLE);
	}

	public void verifyThatSearchResultsAreUpdatedAfterClearingSearchText(int countWithSearchResult, int countWithoutSearchResult) {
		waitForPageToLoad();
		Assert.assertTrue(countWithSearchResult<=countWithoutSearchResult, "Search results are not updated correctly after clearing search text. countWithSearchResult:"+countWithSearchResult+","
				+ " countWithoutSearchResult:"+countWithoutSearchResult);
	}

	public void verifyThatSameSearchCountResultsFromAdvisorViewAreDisplayedInClientView(int advisorViewResultsCount, int clientViewResultsCount) {
		waitForPageToLoad();
		Assert.assertEquals(clientViewResultsCount, advisorViewResultsCount, "Advisor view search results are not matching with client view search results. Advisor View Results Count:" + advisorViewResultsCount +
				", Client View Results Count:" + clientViewResultsCount);
	}

	public void verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(String sModelCode){
		waitForPageToLoad(MEDIUMWAIT);
		String sProductModel = safeGetText(PRODUCT_MODEL_CODE, MEDIUMWAIT);
		log.info("sProductModelCode: "+sProductModel+" ofClientView");
		Assert.assertEquals(sModelCode, sProductModel, "Client view model code "+sProductModel+ " doesn't match with Advisor view model code: " + sModelCode);
	}

	public void verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(String sText){
		waitForPageToLoad();
		String sProductName = safeGetText(PRODUCT_NAME, MEDIUMWAIT);
		log.info("Title of the Product:"+sProductName+" ofClientView");
		Assert.assertEquals(sProductName, sText, "Product name code doesn't have the expected search result:"+sText);
	}

	public void verifyThatSameProductPriceFromAdvisorViewAreDisplayedInClientView(String sPrice){
		waitForPageToLoad();
		String sProductPrice = safeGetText(PRODUCT_PRICE, MEDIUMWAIT);
		log.info("Price of the Product:"+sProductPrice+" ofClientView");
		Assert.assertEquals(sProductPrice, sPrice, "Product price code doesn't have the expected search result:"+sPrice);
	}

	public void verifyThatSameProductDescriptionFromAdvisorViewAreDisplayedInClientView(String sDescription){
		waitForPageToLoad();
		String sThirdProductDescription = safeGetText(PRODUCT_DESCRIPTIONTEXT, MEDIUMWAIT);
		Assert.assertEquals(sThirdProductDescription, sDescription, "Product description doesn't have the expected search result in client view:"+sDescription);
	}

	public void verifyThatSameProductSpecificationFromAdvisorViewAreDisplayedInClientView(String sSpecification){
		waitForPageToLoad();
		String sThirdProductSpecification = safeGetText(PRODUCT_SPECIFICATIONTEXT, MEDIUMWAIT);
		Assert.assertEquals(sSpecification, sThirdProductSpecification, "Product specification doesn't have the expected search result in client view:" + sSpecification);
	}

	public void verifyThatSameStoryTitleFromAdvisorViewAreDisplayedInClientView(String sTitle){
		waitForPageToLoad();
		String storyTitle = safeGetText(STORY_TITLE, MEDIUMWAIT);
		log.info("Title of the story:"+storyTitle+" ofClientView");
		Assert.assertEquals(sTitle, storyTitle, "Story title doesn't have the expected search result:" + sTitle);
	}

	public void verifyThatSameStoryDescriptionFromAdvisorViewAreDisplayedInClientView(String sDescription){
		waitForPageToLoad();
		String storyDescription = safeGetText(STORY_DESCRIPTION, MEDIUMWAIT);
		log.info("Title of the story:"+storyDescription+" ofClientView");
		Assert.assertEquals(sDescription, storyDescription, "Story title doesn't have the expected search result:" + sDescription);
	}

	public void verifyThatSameComparedProductModelCodeFromAdvisorViewAreDisplayedInClientView(int sIndex, String sModelCode){
		waitForPageToLoad();
		By productModelCode = By.cssSelector("div.details-row  >  div:nth-child("+sIndex+") > p.modelcode");
		String sProductModelCode = safeGetText(productModelCode, LONGWAIT);
		log.info("sProductModelCode:"+sProductModelCode +"  of ClientView");
		Assert.assertEquals(sProductModelCode, sModelCode, "Product model code doesn't have the expected search result:" + sModelCode);
	}

	public void verifyThatSelectedProductModelCodeIsDisplayedInSideBar(int sIndex, String sModelCode){
		By productModelCode = By.cssSelector("div.selected-products > button:nth-child("+sIndex+") > p.prod-model");
		String sProductModelCode = safeGetText(productModelCode, MEDIUMWAIT);
		log.info("sProductModelCode:"+sProductModelCode +" ofAdvisorView");
		Assert.assertEquals(sProductModelCode, sModelCode, "Product model code doesn't match with the sidebar section:" + sModelCode);
	}

	public void verifyThatSelectedProductTitleIsDisplayedInSideBar(int sIndex, String sName){
		By productTitle = By.cssSelector("div.selected-products > button:nth-child("+sIndex+") > p.prod-title");
		String sProductTitle = safeGetText(productTitle, MEDIUMWAIT);
		log.info("sProductTitle:"+sProductTitle +" ofAdvisorView");
		Assert.assertEquals(sProductTitle, sName, "Product title doesn't match with the sidebar section:" + sName);
	}

	public void verifyThatSameComparedProductTitleFromAdvisorViewAreDisplayedInClientView(int sIndex, String sName){
		waitForPageToLoad();
		By productTitle = By.cssSelector("div.details-row  >  div:nth-child("+sIndex+") > p.prod-title");
		String sProductTitle = safeGetText(productTitle, MEDIUMWAIT);
		log.info("sProductTitle:"+sProductTitle +" ofAdvisorView");
		Assert.assertEquals(sProductTitle, sName, "Product title doesn't have the expected search result:" + sName);
	}

	public void verifyThatCollectionMessageIsDisplayed() {
		Assert.assertTrue(isElementVisible(WATCHESCOLLECTION_HEADER_TEXT, MEDIUMWAIT), "PILOT’S WATCHES COLLECTION text is not displayed");
	}

	public void verifyThatSameProductModelCodeFromAdvisorViewAreDisplayedInClientView(int sIndex, String sModelCode){
		waitForPageToLoad();
		By productModelCode = By.cssSelector("#showcase-"+sIndex+" .prod-model");
		String sProductModelCode = safeGetText(productModelCode, MEDIUMWAIT);
		log.info("sProductModelCode:"+sProductModelCode +"  ofClientView");
		Assert.assertEquals(sProductModelCode, sModelCode, "Product model code doesn't have the expected search result:" + sModelCode);
	}

	public void verifyThatSameProductNameFromAdvisorViewAreDisplayedInClientView(int sIndex, String sName){
		waitForPageToLoad();
		By productName = By.cssSelector("#showcase-"+sIndex+" .prod-title");
		String sProductName = safeGetText(productName, MEDIUMWAIT);
		log.info("sProductModelCode:"+sProductName +"  ofClientView");
		Assert.assertEquals(sProductName, sName, "Product model code doesn't have the expected search result:" + sName);
	}

	public void verifyThatDashboardIconIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(CHOPARDDASHBOARD_ICON, MEDIUMWAIT), "Dashboard Icon is displayed in client screen");
	}

	public void verifyThatSideMenuButtonIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(VIEWFILEMENU_BUTTON, MEDIUMWAIT), "File menu item is displayed in client screen");
		Assert.assertTrue(isElementInvisible(INVITECLIENT_BUTTON, MEDIUMWAIT), "Invite Client menu item is displayed in client screen");
		Assert.assertTrue(isElementInvisible(QRCODE_BUTTON, MEDIUMWAIT), "QRCode menu item is displayed in client screen");
	}

	public void verifyThatDocumentViewerContainerIsDisplayed() {
		Assert.assertTrue(isElementVisible(PRESENTATIONCONTAINER, MEDIUMWAIT), "Presentation Viewer is not displayed");
	}

	public void verifyThatSliderViewContainerIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(SLIDELIST, MEDIUMWAIT), "Dashboard Icon is displayed in client screen");
	}

	public void clickOnNotificationButton(){
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		safeClickViaActions(NOTIFICATION_BAR, MEDIUMWAIT);
	}

	public void verifyThatMicrophoneMutedMessageIsDisplayed(){
		waitForPageToLoad(MEDIUMWAIT);
		String message = safeGetText(MICROPHONEMUTE_MESSAGE, LONGWAIT);
		Assert.assertTrue(message.contains("Your microphone is auto muted while playing video, click unmute if you'd like to speak."), "Your Microphone is auto muted text is not displayed on client side");
	}

	public void verifyThatSameImageSourceFromAdvisorPageIsDisplayedInClientView(String src){
		waitForPageToLoad(MEDIUMWAIT);
		String imgSource = safeGetAttribute(IMAGESOURCE, "src" , MEDIUMWAIT);
		Assert.assertEquals(imgSource, src, "Image source doesn't have the expected search result:" + src);
	}

	public void verifyThatMicrophoneMutedMessageIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(MICROPHONEMUTE_MESSAGE, MEDIUMWAIT), "Your Microphone is auto muted text is displayed");
	}

	public void verifyThatPresentationPopupIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(PRESENTATIONPOPUP_CROSSICON, MEDIUMWAIT), "Presentation popup is displayed even after closing the popup in client screen");
	}

	public void verifyThatVideoIsPaused(){
		waitForPageToLoad(MEDIUMWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].paused");
		Assert.assertTrue(value, "Video is not paused on pausing the video: " + value);
	}

	public void verifyThatVideoIsPlayed(){
		waitForPageToLoad(LONGWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].seeking");
		Assert.assertFalse(value, "Video is not played after resuming the video: " + value);
	}

	public void verifyMicrophoneButtonIsUnMuted(){
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(AUDIO_MUTE_TEXT), "Microphone button is not UnMuted on client-side");
	}

	public void verifyMicrophoneButtonIsMuted(){

		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(AUDIO_UNMUTE_TEXT), "Microphone button is not muted on client-side");
	}

	public void verifyThatMenuItemIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(ACTIONCONTAINERMENU_ITEM, MEDIUMWAIT), "Menu items is not displayed");
	}

	public void verifyThatFilterIconPopupIsDisplayed() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(STORYFILTER_SECTION, MEDIUMWAIT), "Story Filter popup is not displayed");
	}

	public void verifyThatFilterIconPopupIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(STORYFILTER_SECTION, MEDIUMWAIT), "StoryFilter section is getting displayed");
	}

	public void verifySelectedContextIsDisplayedAndActive(int sIndex,String expectedContext) {
		By sActiveItem = By.xpath("//div[contains(@class,'filter-group')][2]//button["+sIndex+"][contains(@class,'active')]");
		String currentContext = safeGetText(sActiveItem,MEDIUMWAIT);
		currentContext = currentContext.replaceAll("\\s", "");
		log.info("Current selected context is:" +currentContext);
		Assert.assertTrue(expectedContext.equalsIgnoreCase(currentContext), "Context doesn't have the expected search result:" + expectedContext);
	}

	public void verifySelectedContextIsInActive(int sIndex) {
		By inActiveItem = By.xpath("//div[contains(@class,'filter-group')][2]//button["+sIndex+"][contains(@class,'active')]");
		Assert.assertTrue(isElementInvisible(inActiveItem, MEDIUMWAIT), "Context is still in active state: " + inActiveItem);
	}

	public void verifyThatSameStoryTextFromAdvisorViewAreDisplayedInClientView(int sIndex, String sProductStoryText ){
		waitForPageToLoad();
		By productinfo = By.cssSelector(".results-col .row .item:nth-child("+sIndex+") span");
		String sProductinfo = safeGetText(productinfo, MEDIUMWAIT);
		log.info("sProductInfo:"+sProductinfo +"  ofClientView");
		Assert.assertEquals(sProductStoryText, sProductinfo, "Product info doesn't have the expected search result:" + sProductStoryText);
	}

	public void verifyThatSearchInputFieldIsEmpty() {
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		log.info("sSearchText:"+sSearchText);
		Assert.assertTrue(sSearchText.equalsIgnoreCase(""), "product search field is not empty");
	}

	public void verifyThatSameStoriesPopupDescriptionFromAdvisorViewAreDisplayedInClientView(String sDescription){
		waitForPageToLoad(MEDIUMWAIT);
		String storyDescription = safeGetText(STORIES_DESCRIPTION, MEDIUMWAIT);
		Assert.assertEquals(sDescription, storyDescription, "Story title doesn't have the expected search result:" + sDescription);
	}

	public ArrayList<String> getStoryTitlesOfAllDisplayedStories() {
		waitForPageToLoad();
		List<WebElement> allStoryTitlesElements =  LocatorWebElements(ALLSTORIES_TITLE);
		ArrayList<String> allStoryTitlesText  = new ArrayList<>();
		for (WebElement allStoryTitlesElement : allStoryTitlesElements) {
			String text = allStoryTitlesElement.getText().trim();
			allStoryTitlesText.add(text);
		}
		return allStoryTitlesText;
	}

	public void verifyThatStorySearchInputFieldIsEmpty() {
		waitForPageToLoad(MEDIUMWAIT);
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		log.info("sSearchText:"+sSearchText);
		Assert.assertTrue(sSearchText.equalsIgnoreCase(""), "Story search field is not empty");
	}

	public int getCountOfAllDisplayedStories() {
		waitForPageToLoad();
		return getLocatorCount(ALLSTORIES_TITLE);
	}

	public void verifyThatSameIDFromAdvisorViewAreDisplayedInClientView(String productID) {
		waitForPageToLoad();
		LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
		List<LogEntry> logs= entry.getAll();

		boolean initSceneIDNotMatched = false;
		for (LogEntry logEntry : logs) {
			String consoleLog = logEntry.getMessage().replaceAll("\\s", "");
			if (consoleLog.contains(productID)) {
				Assert.assertTrue(consoleLog.contains(productID), "scene id not matched");
				initSceneIDNotMatched = true;
				break;
			}
		}

		if(initSceneIDNotMatched) {
			Assert.assertTrue(true, "scene id not matched");
		}
		else{
			Assert.assertNotEquals(false, false, "scene id is not present in the console logs in client view");
		}
	}

	public void verifyThatSameHorizontalAngleIsInView(Long AdvisorHLookAt) {
		String script = "return document.getElementById(\"krpanoSWFObject\").get('view.hlookat');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long hlookat = (Long) js.executeScript(script);
		Long hlookatValue = (long) Math.round(hlookat);
		log.info("Horizontal Angle From Client View: "+hlookatValue);
		waitForPageToLoad();
		Assert.assertEquals(AdvisorHLookAt, hlookatValue, "Client-side horizontal view is not matched with advisor view" + hlookatValue);
	}

	public void verifyThatSameVerticalAngleIsInView(Long AdvisorVLookAt) {
		String script = "return document.getElementById('krpanoSWFObject').get('view.vlookat');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long vlookat =  (Long) js.executeScript(script);
		Long vlookatValue = (long) Math.round(vlookat);
		log.info("Vertical Angle From Client View: "+vlookatValue);
		waitForPageToLoad();
		Assert.assertEquals(AdvisorVLookAt, vlookatValue, "Client-side vertical view is not matched with advisor view" + vlookatValue);
	}

	public void verifyThatSameFieldOfViewAngleIsInView(Long AdvisorFov) {
		String script = "return document.getElementById(\"krpanoSWFObject\").get('view.fov');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long fov =  (Long) js.executeScript(script);
		Long fovValue = (long) Math.round(fov);
		log.info("Field of View Angle From Advisor View: "+fovValue);
		waitForPageToLoad();
		Assert.assertEquals(AdvisorFov, fovValue, "Client-side field of view is not matched with advisor view" + fovValue);
	}

	public  void verifyThatDocumentIconIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(DOCUMENT_VIEWER, MEDIUMWAIT));
	}

	public void verifyThatDocumentViewerContainerIsNotDisplayed() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementInvisible(PRESENTATIONCONTAINER, LONGWAIT), "Presentation Viewer is displayed in Client View");
	}

	public void verifyThatAdvisorHasLeftMeetingAndNotDisplayedInClientView (String advisorName) {
		By ADVISORNAME = By.xpath("//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+advisorName+"']");
		Assert.assertTrue(isElementInvisible(ADVISORNAME, MEDIUMWAIT), "Advisor is stil shown after exited the meeting");
	}

	public void verifyThatAdvisorHasJoinedMeetingAndDisplayedInClientView (String advisorName) {
		waitForPageToLoad(MEDIUMWAIT);
		By ADVISORNAME = By.xpath("//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+advisorName+"']");
		Assert.assertTrue(isElementVisible(ADVISORNAME, MEDIUMWAIT), "Advisor name is not displayed after joining the meeting in client screen");
	}

	public void verifyThatVideoDocumentIsOpened(){
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(VIDEOSOURCE, MEDIUMWAIT), "Video file is not displayed "+VIDEOSOURCE);
	}

	public void verifyThatImageDocumentIsOpened() {
		Assert.assertTrue(isElementPresent(IMAGESOURCE, LONGWAIT), "Image file is not displayed");
	}

	public void verifyThatStoriesVideoIsPlaying(){
		waitForPageToLoad(MEDIUMWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-container')[0].getElementsByTagName('video'))[0].seeking");
		Assert.assertFalse(value, "Video is not played: " + value);
	}

	public boolean verifyThatVideoHasEnded(){
		waitForPageToLoad();
		return isElementInvisible(VIDEO_DOCUMENT, SHORTWAIT);
	}

	public void verifyThatJoinNowWithoutCameraButtonIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(JOINNOW_WITHOUTCAMERA_BUTTON, MEDIUMWAIT), "Join Now without camera button is not displayed in client join screen");
	}

	public void verifyThatCameraNotAccessibleTextIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(CAMERANOT_ACCESSIBLE_TEXT, LONGWAIT), "Camera not accessible text is not displayed in client join screen");
	}

	public void clickOnJoinNowWithoutCameraButton() {
		waitForPageToLoad();
		safeJavaScriptClick(JOINNOW_WITHOUTCAMERA_BUTTON, MEDIUMWAIT);
		hardWait(SHORTWAIT);
	}

	public void verifyCameraButtonIsUnMuted(){
		waitForPageToLoad();
		Assert.assertTrue(isElementPresent(CAMERA_MUTE_TEXT), "Camera button is not UnMuted");
	}

	public void verifyCameraButtonIsMuted(){
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(CAMERA_UNMUTE_TEXT), "Camera button is not muted");
	}

	public void verifyThatClientIsInLocalContainer(String name) {
		waitForPageToLoad();
		By CLIENTNAME = By.xpath("//div[@id='local-wrapper']//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementVisible(CLIENTNAME, MEDIUMWAIT), "Advisor name is not displayed after joining the meeting");
	}

	public void verifyAdvisorIsNotInPresentingLayoutMode(String name) {
		waitForPageToLoad(MEDIUMWAIT);
		By ADVISOR_PRESENTER = By.xpath("//div[@id='presenter-wrapper']//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementInvisible(ADVISOR_PRESENTER, LONGWAIT), "Advisor is still shown in presenting layout on client view");
	}

	public void verifyThatAdvisorIsPresentInLayoutMode(String name) {
		waitForPageToLoad();
		By ADVISOR_PRESENTER = By.xpath("//div[@id='presenter-wrapper']//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementVisible(ADVISOR_PRESENTER, MEDIUMWAIT), "Advisor is not shown in presenting layout on client view");
	}

	public void verifyThatParticipantsIsInRemoteContainer(String name) {
		waitForPageToLoad();
		By PARTICIPANTNAME = By.xpath("//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementPresent(PARTICIPANTNAME, MEDIUMWAIT), "Participant is not displayed in remote container on client side");
		Assert.assertTrue(isElementVisible(PARTICIPANTNAME, MEDIUMWAIT), "Participant name is not shown on client side");
	}

	public void verifyThatParticipantsIsInRemoteContainerAndInCorrectLayout(String name) {
		waitForPageToLoad();
		By PARTICIPANTNAME = By.xpath("//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementPresent(PARTICIPANTNAME, MEDIUMWAIT), "Participant is not displayed in remote container on client side");
		Assert.assertTrue(isElementInvisible(PARTICIPANTNAME, MEDIUMWAIT), "Participant name is shown and is not displayed in presenter layout on client side");
	}

	public void verifyThatAdvisorIsNotPresentInRemoteContainer(String name) {
		waitForPageToLoad();
		By ADVISORNAME = By.xpath("//div[@id='participant-wrapper']//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementInvisible(ADVISORNAME, MEDIUMWAIT), "Advisor is still in remote container but not in presenter layout on client view");
	}

	public void verifyThatVideoIsPlayingOrNot() {
		waitForPageToLoad(MEDIUMWAIT);
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].ended");
		Assert.assertTrue(value, "Video doesn't ended on client side");
	}

	public void verifyClientLocalVideoIsStreaming() {
		waitForPageToLoad(MEDIUMWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack().track.readyState";
		String value = (String) js.executeScript(script);
		Assert.assertTrue(value.contains("live"), "Advisor video is not streaming on local");
	}

	public void verifyClientLocalVideoIsNotStreaming() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack().track.readyState";
		String value = (String) js.executeScript(script);
		Assert.assertTrue(value.contains("ended"), "Advisor video is still streaming on local");
	}

	public String getClientParticipantId() {
		waitForPageToLoad(MEDIUMWAIT);
		String attributeValue = safeGetAttribute(LOCALWRAPPER_VIDEO_ID, "id", MEDIUMWAIT);
		String[] value = attributeValue.split("-");
		return value[1];
	}

	public void verifyAdvisorRemoteVideoIsStreaming(String participantID) {
		waitForPageToLoad(VERYLONGWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(track => track.getType() === 'video')?.muted; ";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Participant video is not streaming on client view, Participant ID:- "+participantID);
	}

	public void verifyClientRemoteVideoIsStreaming(String participantID) {
		waitForPageToLoad(VERYLONGWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(track => track.getType() === 'video')?.muted";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Participant video is not streaming on client view, Participant ID:- "+participantID);
	}

	public void verifyAdvisorRemoteVideoIsNotStreamingForFirstTime(String participantID) {
		hardWait(VERYSHORTWAIT);
		By clientVideoTag = By.cssSelector("#videos-wrapper #video-"+participantID+" video");
		Assert.assertTrue(isElementInvisible(clientVideoTag, MEDIUMWAIT), "remote advisor video tag is not attached");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(t=>t.getType() === 'video')?.isMuted();";
		String value = (String) js.executeScript(script);
		Assert.assertNull(value, "Advisor video should not stream on client view but it is streaming");
	}

	public void verifyAdvisorRemoteVideoIsNotStreamingAssertFirstTime() {
		//This needs to be used when advisor disabled camera in lounge
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack()?.isMuted()";
		String value = (String) js.executeScript(script);
		Assert.assertNull(value, "Advisor video is still streaming on local");
	}

	public void verifyAdvisorRemoteVideoIsNotStreaming(String participantID) {
		hardWait(VERYSHORTWAIT);
		By videoTag = By.cssSelector("#videos-wrapper #video-"+participantID+" video");
		Assert.assertTrue(isElementInvisible(videoTag, MEDIUMWAIT), "remote advisor video tag is not attached");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(t=>t.getType() === 'video')?.isMuted();";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Advisor video is not streaming on client view");
	}

	public void clickCameraButton() {
		waitForPageToLoad();
		safeClick(INMEETING_CAMERA_BUTTON, MEDIUMWAIT);
	}

	public void verifyClientLocalAudioIsStreaming() {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalAudioTrack()?.isMuted()";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Client audio is not streaming on local");
	}

	public void verifyParticipantRemoteAudioIsStreaming(String participantID) {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(track => track.getType() === 'audio')?.muted;";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Participant audio is not streaming on client view, Participant ID:- "+participantID);
	}

	public void verifyClientEnlargedLocalVideoIsStreaming() {
		hardWait(VERYSHORTWAIT);
		Assert.assertTrue(isElementVisible(ENLARGE_VIDEO_LOCAL, MEDIUMWAIT), "Client enlarged local video tag is not attached");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack().track.readyState";
		String value = (String) js.executeScript(script);
		Assert.assertTrue(value.contains("live"), "Client video is not streaming on local");
	}

	public void verifyClientEnlargedRemoteVideoIsStreaming(String participantID) {
		waitForPageToLoad(VERYLONGWAIT);
		Assert.assertTrue(isElementVisible(ENLARGE_VIDEO, LONGWAIT), "remote client enlarged video tag is not attached");


		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(t=>t.getType() === 'video')?.isMuted();";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Client video is not streaming on advisor view");
	}

}	


