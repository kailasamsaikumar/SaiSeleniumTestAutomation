package com.inspify.pages;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;
import com.inspify.locators.AdvisorPageLocators;
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

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvisorPage extends SafeActions implements AdvisorPageLocators {

	private static final Logger log = Logger.getLogger(AdvisorPage.class);
	private final WebDriver driver;

	public AdvisorPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void switchToAdvisorTab() {
		hardWait(VERYSHORTWAIT);
		switchToWindow(0);
	}

	public void verifyThatStartSessionButtonIsDisplayed() {
		waitForPageToLoad();
		for(int i=0; i<3; i++) {
			boolean startSessionValue = isElementVisible(STARTSESSION_BUTTON, LONGWAIT);
			if(!startSessionValue){
				refresh();
			}
			else {
				Assert.assertTrue(isElementVisible(STARTSESSION_BUTTON, LONGWAIT), "Start session button is not displayed in advisor join screen");
				break;
			}
		}	
	}

	public void enterAdvisorName(String name) {
		waitForPageToLoad();
		safeClearViaActions(NAME_INPUTFIELD, MEDIUMWAIT);
		safeClearAndType(NAME_INPUTFIELD, name, MEDIUMWAIT);
		mouseHover(NAME_INPUTFIELD, MEDIUMWAIT);
		safePressEnterViaActions(NAME_INPUTFIELD, MEDIUMWAIT);
		safePressTabKey(NAME_INPUTFIELD, MEDIUMWAIT);
		String enteredAdvisorName = safeGetAttribute(NAME_INPUTFIELD, "Value", MEDIUMWAIT);
		if(!name.equals(enteredAdvisorName.trim())){
			log.info("Clearing the text, displayed text is: "+enteredAdvisorName);
			safeClearViaActions(NAME_INPUTFIELD, MEDIUMWAIT);
			safeType(NAME_INPUTFIELD, name, MEDIUMWAIT);
			safePressEnterViaActions(NAME_INPUTFIELD, MEDIUMWAIT);
		}
	}

	public void clickOnStartSessionButton() {
		hardWait(VERYSHORTWAIT);
		mouseHover(STARTSESSION_BUTTON, MEDIUMWAIT);
		safeJavaScriptClick(STARTSESSION_BUTTON, LONGWAIT);
		Assert.assertTrue(isElementInvisible(STARTSESSION_BUTTON, MEDIUMWAIT), "Start session button is not visible");
	}

	public void verifyThatAdvisorHasJoinedMeeting(String advisorName) {
		waitForPageToLoad(MEDIUMWAIT);
		By ADVISORNAME = By.xpath("//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+advisorName+"']");
		Assert.assertTrue(isElementVisible(ADVISORNAME, MEDIUMWAIT), "Advisor name is not displayed after joining the meeting");
		Assert.assertTrue(isElementVisible(ADVISORROLE_ICONS, MEDIUMWAIT), "Advisor is not logged in with advisor role");
	}

	public String getClientUrl() {
		safeClick(CONTROL_MENUITEM, MEDIUMWAIT);
		safeJavaScriptClick(INVITE_PEOPLE, MEDIUMWAIT);
		safeJavaScriptClick(COPY_URL, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(URL_COPIED, MEDIUMWAIT), "URL is not copied");
		String copiedUrl = "";
		try {
			copiedUrl = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
		return copiedUrl;
	}

	public void verifyThatNewJoinerClientHasJoinedTheLoungeTextIsVisible(String clientName) {
		waitForPageToLoad(MEDIUMWAIT);
		hardWait(VERYSHORTWAIT);
		By loungeText = By.xpath("//div[normalize-space(text())='"+clientName+" is waiting in the lounge']");
		Assert.assertTrue(isElementVisible(loungeText, LONGWAIT), "Client is not displayed in the lounge text: "+clientName);
	}

	public void clickOnAdmitButton() {
		waitForPageToLoad(MEDIUMWAIT);
		safeJavaScriptClick(ADMIT_BUTTON, VERYLONGWAIT);
		Assert.assertTrue(isElementInvisible(ADMIT_BUTTON, VERYLONGWAIT), "Admit button is displayed even after clicking on it in advisor screen");
	}

	public void clickOnProductSearchIcon() {
		waitForPageToLoad();
		safeClick(SEARCH_ICON, MEDIUMWAIT);
	}

	public void clickOnCompareProductsIcon() {
		waitForPageToLoad();
		safeClick(COMPAREPRODUCTS_ICON_INMEETINGCONTROLLER, MEDIUMWAIT);
	}

	public void clickOnSearchStoriesIcon() {
		waitForPageToLoad();
		safeClick(SEARCHSTORIES_ICON_INMEETINGCONTROLLER, MEDIUMWAIT);
	}

	public void switchToInframeContainer() {
		hardWait(VERYSHORTWAIT);
		selectFrame(IFRAMECONTAINER, MEDIUMWAIT);
	}

	public void VerifyThatIframeContainerIsNotDisplayed(){
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(IFRAMECONTAINER, MEDIUMWAIT), "Iframe Container popup is still displayed");
	}

	public void verifyThatProductSearchPopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(PRODUCTSEARCH_PAGETITLE, MEDIUMWAIT), "Product search popup is not displayed");
		Assert.assertTrue(isElementVisible(SEARCH_INPUT, MEDIUMWAIT), "Product search input field is not displayed");
	}

	public void verifyThatProductSearchPopupIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(PRODUCTSEARCH_PAGETITLE, MEDIUMWAIT), "Product search popup is displayed");
		Assert.assertTrue(isElementInvisible(SEARCH_INPUT, MEDIUMWAIT), "Product search input field is displayed");
	}

	public void closeProductPopup() {
		waitForPageToLoad(MEDIUMWAIT);
		safeClick(PRODUCTSPOPUP_CROSSICON, MEDIUMWAIT);
		Assert.assertTrue(isElementInvisible(PRODUCTSPOPUP_CROSSICON, MEDIUMWAIT), "Product popup is displayed even after closing the popup in advisor screen");
	}

	public void clickOnSideMenuNavigatorIcon() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(SIDEMENUNAVIGATOR_ICON, MEDIUMWAIT));
		safeJavaScriptClick(SIDEMENUNAVIGATOR_ICON, MEDIUMWAIT);
	}

	public void clickOnProductSearchMenuItem() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(PRODUCTSEARCH_MENUITEM, MEDIUMWAIT), "Product search menu-item is not displayed on advisor view");
		safeJavaScriptClick(PRODUCTSEARCH_MENUITEM, LONGWAIT);
	}

	public void endMeeting() {
		waitForPageToLoad();
		safeJavaScriptClick(STOPMEETING_BUTTON, MEDIUMWAIT);
		safeJavaScriptClick(ENDMEETING_BUTTON, MEDIUMWAIT);
	}

	public void verifyThatMeetingEnded() {
		waitForPageToLoad(LONGWAIT);
		Assert.assertTrue(isElementInvisible(ADVISORROLE_ICONS, LONGWAIT), "Meeting didn't end on advisor side");
	}

	public void searchForSpecifiedTextOnProductSearchPopup(String sText) {
		waitForPageToLoad();
		enterText(SEARCH_INPUT, sText, MEDIUMWAIT);
		safePressEnterViaActions(SEARCH_INPUT, MEDIUMWAIT);
		safeClick(SEARCH_BUTTON, MEDIUMWAIT);
	}

	public void verifyThatCorrectSearchResultsAreShown(String sText) {
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		String sFirstProductTitle = safeGetText(FIRSTPRODUCT_MODELTITLE, MEDIUMWAIT);
		log.info("sFirstProductTitle:"+sFirstProductTitle);
		Assert.assertTrue(sFirstProductTitle.toLowerCase().contains(sText.toLowerCase()), "First product model title doesn't have the expected search result:"+sText);
	}

	public void searchForSpecifiedModelCodeOnProductSearchPopup(String sText) {
		waitForPageToLoad();
		enterText(SEARCH_INPUT, sText, MEDIUMWAIT);
		safeClick(SEARCH_BUTTON, LONGWAIT);
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		String sFirstProductText = safeGetText(FIRSTPRODUCT_MODELTEXT, MEDIUMWAIT);
		log.info("sFirstProductText:"+sFirstProductText);
		Assert.assertTrue(sFirstProductText.toLowerCase().contains(sText.toLowerCase()), "First product model text doesn't have the expected search result:"+sText);
	}

	public void verifyThatSpecifiedTextIsPresentInProductSearchInputField(String sText) {
		waitForPageToLoad();
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		Assert.assertTrue(sSearchText.toLowerCase().contains(sText.toLowerCase()), "Expected search text:"+sText+" is not displayed in product search field");
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

	public int getCountOfAllDisplayedProducts() {
		hardWait(SHORTWAIT);
		return getLocatorCount(ALLPRODUCTSMODELTITLE);
	}

	public void clearSearchTextFromProductSearchInputField() {
		waitForPageToLoad();
		safeClearViaActions(SEARCH_INPUT, MEDIUMWAIT);
	}

	public void verifyThatSearchInputFieldIsEmpty() {
		waitForPageToLoad();
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		log.info("sSearchText:"+sSearchText);
		Assert.assertTrue(sSearchText.equalsIgnoreCase(""), "product search field is not empty");
	}

	public void verifyThatSearchResultsAreUpdatedAfterClearingSearchText(int countWithSearchResult, int countWithoutSearchResult) {
		waitForPageToLoad();
		Assert.assertTrue(countWithSearchResult<=countWithoutSearchResult, "Search results are not updated correctly after clearing search text. countWithSearchResult:"+countWithSearchResult+","
				+ " countWithoutSearchResult:"+countWithoutSearchResult);
	}

	public void clickOnProductCompareMenuItem() {
		waitForPageToLoad();
		try{ safeJavaScriptClick(PRODUCTCOMPARE_MENUITEM, MEDIUMWAIT); }
		catch (Exception e) { safeClickViaActions(PRODUCTCOMPARE_MENUITEM, MEDIUMWAIT); }
			
	}

	public void verifyThatProductComparePopupIsDisplayed() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(COMPAREPRODUCTS_PAGETITLE, LONGWAIT), "Product compare popup is not displayed");
	}

	public void verifyThatProductComparePopupIsNotDisplayed() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementInvisible(COMPAREPRODUCTS_PAGETITLE, LONGWAIT), "Product compare popup is not displayed");
	}

	public void verifyThatEmptyProductCompareMessageIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(EMPTYMESSAGE_COMPAREPRODUCTSPOPUP_TEXT, MEDIUMWAIT), "YOU DON'T HAVE ANY PRODUCTS TO BE COMPARED text is not displayed");
	}

	public void clickOnSearchProduct() {
		waitForPageToLoad();
		safeClick(SEARCHPRODUCTS_BUTTON, MEDIUMWAIT);
	}

	public void clickOnStartToCompareProduct() {
		waitForPageToLoad();
		safeClick(STARTCOMPARE_PRODUCTS_BUTTON, MEDIUMWAIT);
	}

	public void deleteOneProductInCompareProductsScreenPopup(){
		waitForPageToLoad();
		safeClick(DELETECOMPAREPRODUCTS_CROSSICON, MEDIUMWAIT);
	}

	public String getPageTitle() {
		waitForPageToLoad();
		String pageTitle = safeGetText(IDENTIFY_PAGETITLE, MEDIUMWAIT);
		log.info(pageTitle);
		return pageTitle;
	}

	public String getAboutStoryTitle(){
		waitForPageToLoad();
		String storyTitle = safeGetText(ABOUT_STORY_TITLE, MEDIUMWAIT);
		log.info("Title of the Story:"+storyTitle+" ofAdvisorView");
		return storyTitle;

	}

	public String getAboutStoryDescription(){
		waitForPageToLoad();
		String storyDescription = safeGetText(ABOUTSTORY_DESCRIPTION, MEDIUMWAIT);
		log.info("Description of the story:"+storyDescription+" ofAdvisorView");
		return storyDescription;
	}

	public void verifyThatExpectedPopupPageTitleIsDisplayed(String sPageTitle) {
		waitForPageToLoad();
		By INSPIRATION_PAGETITLE = By.xpath("//span[normalize-space(text())='"+sPageTitle+"']");
		Assert.assertTrue(isElementVisible(INSPIRATION_PAGETITLE, MEDIUMWAIT), "Expected screen popup is not displayed");

	}

	public void clickOnRightSideArrowOfInspirationImageCarouselSection() {
		waitForPageToLoad();
		safeJavaScriptClick(INSPIRATIONIMAGECAROUSELITEM_RIGHTSIDE_BUTTON, MEDIUMWAIT);
	}

	public void verifyThatProductCompareMessageIsDisplayed() {
		String compareSideBarMessage = safeGetText(PRODUCTCOMPARE_SIDEBAR_MESSAGE,MEDIUMWAIT);
		log.info("sSearchText:"+compareSideBarMessage);
		Assert.assertTrue(compareSideBarMessage.equalsIgnoreCase("SELECT UP TO 4 PRODUCTS TO COMPARE"), "product search field is not empty");
	}

	public String getSpecifiedIndexProductModelCode(int sIndex){
		By productModel = By.cssSelector("#showcase-"+sIndex+" .prod-model");
		String sProductCode = safeGetText(productModel, MEDIUMWAIT);
		log.info("sProductModelCode:"+sProductCode +" ofAdvisorView");
		return sProductCode;
	}

	public String getSpecifiedIndexProductName(int sIndex){
		By productTitle = By.cssSelector("#showcase-"+sIndex+" .prod-title");
		String sProductName = safeGetText(productTitle, MEDIUMWAIT);
		log.info("sProductTitle:"+sProductName +" ofAdvisorView");
		return sProductName;
	}

	public void clickOnSpecifiedIndexProductModelLink(int sIndex) {
		By productModelLink = By.cssSelector("#showcase-"+sIndex+" .prod-model");
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		safeJavaScriptClick(productModelLink, MEDIUMWAIT);
	}

	public void clickOnFirstProductModelLink() {
		waitForPageToLoad(MEDIUMWAIT);
		safeJavaScriptClick(FIRSTPRODUCT_MODELTEXT, MEDIUMWAIT);
	}

	public void verifyThatDescriptionSectionIsDisplayedOnProductDetailsPopup() {
		Assert.assertTrue(isElementVisible(DESCRIPTIONSECTION, MEDIUMWAIT), "Description section is not displayed");
	}

	public void verifyThatSpecificationSectionIsDisplayedOnProductDetailsPopup() {
		Assert.assertTrue(isElementVisible(SPECIFICATIONSSECTION, MEDIUMWAIT), "Specification section is not displayed");
	}

	public void clickOnDescriptionSection() {
		waitForPageToLoad();
		safeJavaScriptClick(DESCRIPTIONSECTION, MEDIUMWAIT);

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
		Assert.assertTrue(isElementInvisible(DESCRIPTIONTEXT, MEDIUMWAIT), "Description text is displayed on advisor side");
	}

	public void clickOnSpecificationSection() {
		waitForPageToLoad();
		safeJavaScriptClick(SPECIFICATIONSSECTION, MEDIUMWAIT);
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

	public int getCountOfImageCarouselItems() {
		waitForPageToLoad();
		int count = getLocatorCount(PRODUCTIMAGECAROUSEL_ITEM);
		log.info(count);
		return count;
	}

	public void clickOnRightSideArrowOfProductImageCarouselSection() {
		waitForPageToLoad(MEDIUMWAIT);
		safeJavaScriptClick(PRODUCTIMAGECAROUSEL_RIGHTSIDE_BUTTON, MEDIUMWAIT);
	}

	public void clickOnLeftSideArrowOfProductImageCarouselSection() {
		waitForPageToLoad(MEDIUMWAIT);
		safeJavaScriptClick(PRODUCTIMAGECAROUSEL_LEFTSIDE_BUTTON, MEDIUMWAIT);
	}

	public void scrollDownToSideBarSection() {
		waitForPageToLoad();
		scrollIntoElement(PRODUCTCOMPARE_SIDEBAR_SECTION);
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

	public void verifyProductCompareStartTextMessage(int sIndex){
		By displayText = By.xpath("//button[normalize-space(text())='START TO COMPARE "+sIndex+" PRODUCTS']");
		Assert.assertTrue(isElementPresent(displayText), "Expected 'Start to compare message' is not displayed correctly"+sIndex);
	}

	public void clickOnAddProductCompare(){
		waitForPageToLoad();
		safeClick(ADDPRODUCTTO_COMPARE_BUTTON, MEDIUMWAIT);
	}

	public void verifyThatProductDetailsPopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(PRODUCTDETAILS_PAGETITLE, MEDIUMWAIT), "Product Details popup is not displayed");
	}

	public String getProductModelCode(){
		waitForPageToLoad();
		String sProductModel = safeGetText(PRODUCT_MODEL_CODE, MEDIUMWAIT);
		log.info("sProductModelCode: "+sProductModel +" ofAdvisorView");
		return sProductModel;
	}

	public String getProductName(){
		waitForPageToLoad();
		String sProductName = safeGetText(PRODUCT_NAME, MEDIUMWAIT);
		log.info("Title of the Product:"+sProductName+" ofAdvisorView");
		return sProductName;
	}

	public String getProductPrice(){
		waitForPageToLoad();
		String sProductPrice = safeGetText(PRODUCT_PRICE, MEDIUMWAIT);
		log.info("Price of the Product:"+sProductPrice+" ofAdvisorView");
		return sProductPrice;
	}

	public void clickOnProductDetailsCompareProduct() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(PRODUCTDETAILS_COMPAREPRODUCT_BUTTON, MEDIUMWAIT), "Product compare in product details screen is not present");
		safeJavaScriptClick(PRODUCTDETAILS_COMPAREPRODUCT_BUTTON, MEDIUMWAIT);
	}

	public String getComparedProductModelCode(int sIndex){
		By productModel = By.cssSelector("div.details-row  >  div:nth-child("+sIndex+") > p.modelcode");
		String sProductCode = safeGetText(productModel, MEDIUMWAIT);
		log.info("sProductModelCode:"+sProductCode +" ofAdvisorView");
		return sProductCode;
	}

	public String getDescriptionSectionText(){
		waitForPageToLoad();
		return safeGetText(PRODUCT_DESCRIPTIONTEXT, MEDIUMWAIT);
	}

	public String getSpecificationSectionText(){
		waitForPageToLoad();
		return safeGetText(PRODUCT_SPECIFICATIONTEXT, MEDIUMWAIT);
	}

	public int getCountOfAllColorVariations() {
		waitForPageToLoad();
		int count = getLocatorCount(COLOR_VARIATIONS);
		log.info(count);
		return count;
	}

	public void clickOnColorVariationLink(int sIndex) {
		waitForPageToLoad();
		By sActiveItem = By.xpath("(//div[contains(@class,'colors')]/img)["+sIndex+"]");
		safeJavaScriptClick(sActiveItem,MEDIUMWAIT );
		log.info(sIndex);
		Assert.assertTrue(safeGetAttribute(sActiveItem,"class", MEDIUMWAIT).contains("active"), "Specified color variation item with index "+sIndex+" is not visible");
		waitForPageToLoad();

	}

	public void verifyThatSameProductModelCodeFromProductDetailsPageIsDisplayedInCompareProducts(int sIndex, String sModelCode){
		waitForPageToLoad();
		By productModelCode = By.cssSelector("div.details-row  >  div:nth-child("+sIndex+") > p.modelcode");
		String sProductModelCode = safeGetText(productModelCode, MEDIUMWAIT);
		log.info("sProductModelCode:"+sProductModelCode +"  ofAdvisorView");
		Assert.assertEquals(sProductModelCode, sModelCode, "Product model code doesn't have the expected search result:" + sModelCode);
	}

	public void verifyThatSameProductTitleFromProductDetailsPageIsDisplayedInCompareProducts(int sIndex, String sName){
		waitForPageToLoad();
		By productTitle = By.cssSelector("div.details-row  >  div:nth-child("+sIndex+") > p.prod-title");
		String sProductTitle = safeGetText(productTitle, MEDIUMWAIT);
		log.info("sProductTitle:"+sProductTitle +" ofAdvisorView");
		Assert.assertEquals(sProductTitle, sName, "Product title doesn't have the expected search result:" + sName);
	}

	public void scrollDownToYouMayAlsoLikeSection() {
		waitForPageToLoad(MEDIUMWAIT);
		scrollIntoElement(YOUMAYALSOLIKE_SECTION);
	}

	public void clickOnYouMayAlsoLikeProductLink() {
		waitForPageToLoad();
		Assert.assertTrue(isElementClickable(YOUMAYALSOLIKE_FIRSTPRODUCT, MEDIUMWAIT),"You may also like product is not clickable: "+YOUMAYALSOLIKE_FIRSTPRODUCT);
		safeJavaScriptClick(YOUMAYALSOLIKE_FIRSTPRODUCT, MEDIUMWAIT);
	}

	public void scrollDownToInspirationSection() {
		waitForPageToLoad(MEDIUMWAIT);
		scrollIntoElement(INSPIRATION_SECTION);
	}

	public int getCountOfInspirationImageCarouselItems() {
		waitForPageToLoad();
		int count = getLocatorCount(INSPIRATIONIMAGECAROUSEL_ITEM);
		log.info("No.of Inspiration ImageCarousel Items: "+count);
		return count;
	}

	public void clickOnInspirationImage() {
		waitForPageToLoad();
		Assert.assertTrue(isElementClickable(INSPIRATION_IMAGE, MEDIUMWAIT),"Inspiration element is not clickable: "+INSPIRATION_IMAGE);
		safeClick(INSPIRATION_IMAGE, MEDIUMWAIT);
	}

	public void clickOnBackButton(){
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(PRODUCTSPAGEBACK_BUTTON, MEDIUMWAIT), "Back button is not visible on advisor view");
		safeJavaScriptClick(PRODUCTSPAGEBACK_BUTTON, MEDIUMWAIT);
	}

	public void clickOnWatchesCollectionMenuItem() {
		waitForPageToLoad();
		safeClick(WATCHESCOLLECTION_MENUITEM, MEDIUMWAIT);
	}

	public void verifyThatCollectionMessageIsDisplayed() {
		Assert.assertTrue(isElementVisible(WATCHESCOLLECTION_HEADER_TEXT, MEDIUMWAIT), "PILOT’S WATCHES COLLECTION text is not displayed");
	}

	public void clickOnDashboardIcon(){
		waitForPageToLoad();
		safeClick(CHOPARDDASHBOARD_ICON, MEDIUMWAIT);
	}

	public void clickOnMeetingControlButton(){
		waitForPageToLoad();
		safeClick(MEETING_CONTROLBUTTON);
	}

	public void clickOnNotificationButton(){
		waitForPageToLoad();
		safeClickViaActions(NOTIFICATION_BAR, MEDIUMWAIT);
	}

	public void verifyThatNotificationButtonIsNotDisplayed(){
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(NOTIFICATION_BAR, MEDIUMWAIT), "Notification button is displayed");
	}

	public void verifyThatDashboardMenuIsDisplayed() {
		Assert.assertTrue(isElementVisible(DASHBOARDCONTAINER_MENU, MEDIUMWAIT), "PILOT’S WATCHES COLLECTION text is not displayed");
	}

	public void clickOnIntroductionMenuButton(){
		waitForPageToLoad();
		safeClick(INTRODUCTION_MENU, MEDIUMWAIT);
	}

	public void verifyThatDocumentViewerContainerIsDisplayed() {
		Assert.assertTrue(isElementVisible(PRESENTATIONCONTAINER, MEDIUMWAIT), "Presentation Viewer is not displayed");
	}

	public void verifyThatSpecifiedSlideViewerIsActive(int sIndex) {
		By sActiveItem = By.xpath("//div[@id='slide"+sIndex+"'][contains(@class,'active')]");
		Assert.assertTrue(isElementVisible(sActiveItem, MEDIUMWAIT), "Specified image carousel item with index "+sIndex+" is not visible");
	}

	public void verifyThatSliderViewContainerIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(SLIDELIST, MEDIUMWAIT), "Slide viewcontainer is not displayed in advisor screen");
	}

	public String getImageSource(){
		String imageSrc = safeGetAttribute(IMAGESOURCE, "src" , MEDIUMWAIT);
		log.info("Image Source "+imageSrc +" Of AdvisorView");
		return imageSrc;
	}

	public String getVideoSource(){
		String videoSrc = safeGetAttribute(VIDEOSOURCE, "src" , MEDIUMWAIT);
		log.info("Video Source "+videoSrc +" Of AdvisorView");
		return videoSrc;
	}

	public void clickOnSpecifiedSlideIndex(int sIndex){
		//Index starts with ZERO
		waitForPageToLoad();
		By sIndexItem = By.xpath("//div[@id='slide"+sIndex+"']");
		safeClick(sIndexItem, MEDIUMWAIT);
	}

	public void verifyThatMicrophoneMutedMessageIsDisplayed() {
		waitForPageToLoad();
		String message = safeGetText(MICROPHONEMUTE_MESSAGE, MEDIUMWAIT);
		Assert.assertTrue(message.contains("Your microphone is auto muted while playing video, click unmute if you'd like to speak."), "Your Microphone is auto muted text is not displayed");
	}

	public void pauseVideo() {
		waitForPageToLoad(LONGWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String pauseScript = "(document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].pause()";
		js.executeScript(pauseScript, "Unable to pause the video");
	}

	public void verifyThatVideoIsPaused(){
		waitForPageToLoad();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].paused");
		Assert.assertTrue(value, "Video is not paused after pausing the video: " + value);
	}

	public void verifyThatMicrophoneMutedMessageIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(MICROPHONEMUTE_MESSAGE, MEDIUMWAIT), "Your Microphone is auto muted text is displayed");
	}

	public void playVideo() {
		waitForPageToLoad();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String pauseScript = "(document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].play()";
		js.executeScript(pauseScript);
	}

	public void verifyThatVideoIsPlayed(){
		waitForPageToLoad();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].seeking");
		Assert.assertFalse(value, "Video is not played after resuming the video: " + value);
	}

	public void closePresentationPopup() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementClickable(PRESENTATIONPOPUP_CROSSICON, MEDIUMWAIT));
		safeJavaScriptClick(PRESENTATIONPOPUP_CROSSICON, MEDIUMWAIT);
		Assert.assertTrue(isElementInvisible(PRESENTATIONPOPUP_CROSSICON, MEDIUMWAIT), "Presentation popup is displayed even after closing the popup in advisor screen");
	}

	public void verifyMicrophoneButtonIsUnMuted(){
		waitForPageToLoad();
		Assert.assertTrue(isElementPresent(AUDIO_MUTE_TEXT), "Microphone button is not UnMuted");
	}

	public void verifyMicrophoneButtonIsMuted(){
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(AUDIO_UNMUTE_TEXT, LONGWAIT), "Microphone button is not muted");
	}

	public int getSlideCount(){
		waitForPageToLoad();
		return getLocatorCount(SLIDE_COUNT);
	}

	public String getSlideLocatorValue() {
		waitForPageToLoad();
		By sActiveItem = By.xpath("//div[contains(@class,'active')]//button[contains(@class,'media-')]");
		String imageClassName = safeGetAttribute(sActiveItem, "class" , MEDIUMWAIT);
		return imageClassName.replaceAll("[\\s\\-()]", "");
	}

	public void verifyThatMenuItemIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(ACTIONCONTAINERMENU_ITEM, MEDIUMWAIT), "Menu items is not displayed");
	}

	public void clickOnStorySearchIcon(){
		waitForPageToLoad();
		safeClick(STORYSEARCH_ICON, MEDIUMWAIT);
	}

	public void verifyThatStorySearchPopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(STORYSEARCH_PAGETITLE, MEDIUMWAIT), "Story search popup is not displayed");
		Assert.assertTrue(isElementVisible(SEARCH_INPUT, MEDIUMWAIT), "Story search input field is not displayed");
	}

	public void verifyThatStorySearchPopupIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(STORYSEARCH_PAGETITLE, MEDIUMWAIT), "Story search popup is not displayed");
		Assert.assertTrue(isElementInvisible(SEARCH_INPUT, MEDIUMWAIT), "Story search input field is not displayed");
	}

	public void clickOnFilterIcon() {
		waitForPageToLoad();
		safeClick(FILTER_ICON,MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(FILTER_ICON, MEDIUMWAIT),"Filter icon is not displayed");
	}

	public void closeFilterIcon(){
		waitForPageToLoad();
		safeClick(FILTERPOPUP_CLOSEBUTTON,MEDIUMWAIT);
	}

	public void verifyThatFilterIconPopupIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(STORYFILTER_SECTION, MEDIUMWAIT), "StoryFilter section is getting displayed");
	}

	public void verifyDefaultLanguage(String sIndex,String expectedLanguage) {

		By sActiveItem = By.xpath("//div[contains(@class,'filter-group')][1]//button["+sIndex+"][contains(@class,'active')]");
		String currentLanguage = safeGetText(sActiveItem,MEDIUMWAIT);
		log.info("Current selected language is: "+currentLanguage);
		Assert.assertTrue(currentLanguage.equalsIgnoreCase(expectedLanguage), "Language doesn't have the expected search result:"+expectedLanguage);
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

	public void verifyThatFilterIconPopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(STORYFILTER_SECTION, MEDIUMWAIT), "Story Filter popup is not displayed");
	}

	public void clickOnFilterLanguageButton(int sIndex) {
		By languageOption = By.cssSelector(".filter-group:nth-child(1)>button:nth-child("+sIndex+")");
		safeClick(languageOption,MEDIUMWAIT);
	}

	public void clickOnFilterContextButton(int sIndex) {
		//Index starts with 1
		By contextOption =By.cssSelector(".filter-group:nth-child(2)>button:nth-child("+sIndex+")");
		safeClick(contextOption,MEDIUMWAIT);
	}

	public String getSpecifiedIndexStoryText(int sIndex){
		//sIndex starts with 1
		By stext = By.cssSelector(".results-col .row .item:nth-child("+sIndex+") span");
		String storyText = safeGetText(stext, MEDIUMWAIT);
		log.info("sProductTitle:"+storyText +" ofAdvisorView");
		return storyText;
	}

	public void detectLanguage() {

	}

	public String getStoryTextLanguage(String stext) throws APIError {
		DetectLanguage.apiKey = "095815e078d10a5db56ad8bd8d2c9299";
		return DetectLanguage.simpleDetect(stext);
	}

	public void clickOnSpecifiedIndexStory(int sIndex) {
		//Story index starts with 1
		waitForPageToLoad();
		By contextIndex = By.cssSelector("div.item:nth-child("+sIndex+")");
		safeJavaScriptClick(contextIndex,MEDIUMWAIT);
	}

	public void verifyThatInstagramStoryPageTitlePopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(INSTAGRAM_PAGETITLE, MEDIUMWAIT), "Instagram story popup is not displayed");
	}

	public void verifyThatVideoClipStoryPageTitlePopupIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(VIDEOCLIP_PAGETITLE, MEDIUMWAIT), "Instagram story popup is not displayed");
	}

	public String getStoryDescription(){
		waitForPageToLoad();
		String storyDescription = safeGetText(STORIES_DESCRIPTION, MEDIUMWAIT);
		log.info("Description of the story:"+storyDescription+" ofAdvisorView");
		return storyDescription;
	}

	public void resetFilterOptions(){
		waitForPageToLoad();
		safeClick(RESETFILTER_BUTTON,MEDIUMWAIT);
	}

	public void clickClearSearchCrossIcon(){
		waitForPageToLoad();
		safeClick(CLEAR_SEARCHTEXT);
	}

	public void searchForSpecifiedTextOnStorySearchPopup(String sText) {
		waitForPageToLoad();
		enterText(SEARCH_INPUT, sText, MEDIUMWAIT);
		safeClick(STORYSEARCH_SEARCHICON, MEDIUMWAIT);

	}

	public void verifyThatSpecifiedTextIsPresentInStorySearchInputField(String sText) {
		String sSearchText = safeGetAttribute(SEARCH_INPUT,"value", MEDIUMWAIT);
		Assert.assertTrue(sSearchText.contains(sText), "Expected search text is not displayed in Story search field"+ sText);
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

	public int getCountOfAllDisplayedStories() {
		waitForPageToLoad();
		return getLocatorCount(ALLSTORIES_TITLE);
	}

	public void clickScene(int sceneID) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String formatToSceneID = String.format("%02d", sceneID);
		log.info("Clicking scene: "+formatToSceneID);

		String script = "document.getElementById('krpanoSWFObject').call(\"loadscene('scene_"+formatToSceneID+"',null,MERGE,BLEND(1))\")";
		js.executeScript(script);
		waitForPageToLoad(LONGWAIT);
	}

	public String captureLogsAndVerifyScene(int sceneID) {
		String formatToSceneID = String.format("%02d", sceneID);
		String searchScene = "didstartscenescene_"+formatToSceneID+"";
		LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
		List<LogEntry> logs= entry.getAll();

		boolean initSceneIDNotMatched = false;
		for (LogEntry logEntry : logs) {
			String consoleLog = logEntry.getMessage().replaceAll("\\s", "");
			if (consoleLog.contains(searchScene)) {
				Assert.assertTrue(consoleLog.contains(searchScene), "scene id not matched");
				initSceneIDNotMatched = true;
				break;
			}
		}

		if(initSceneIDNotMatched) {
			Assert.assertTrue(true, "scene id not matched");
		}
		else{
			Assert.assertNotEquals(false, false, "scene id is not present in the console logs");
		}
		waitForPageToLoad(LONGWAIT);
		return searchScene;
	}

	public void clickHotspot(String productID) {
		waitForPageToLoad(MEDIUMWAIT);
		String script = "document.getElementById('krpanoSWFObject').call(\"openPopup('PRODUCT',"+productID+")\")";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(script);
		waitForPageToLoad(MEDIUMWAIT);
	}

	public void captureLogsAndVerifyID(String productID) {
		LogEntries entry = driver.manage().logs().get(LogType.BROWSER);
		List<LogEntry> logs= entry.getAll();

		boolean initSceneIDNotMatched = false;
		for (LogEntry logEntry : logs) {
			String consoleLog = logEntry.getMessage().replaceAll("\\s", "");
			if (consoleLog.contains(productID)) {
				Assert.assertTrue(consoleLog.contains(productID), "product id not matched");
				initSceneIDNotMatched = true;
				break;
			}
		}

		if(initSceneIDNotMatched) {
			Assert.assertTrue(true);
		}
		else{
			Assert.assertNotEquals(false, false, "product id is not present in the console logs");
		}
		waitForPageToLoad(LONGWAIT);
	}

	public void clickInspirationHotspot(String inspirationID) {
		String script = "document.getElementById('krpanoSWFObject').call(\"openPopup('INSPIRATION_BY_TAG',"+inspirationID+")\")";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(script);
		waitForPageToLoad();
	}

	public void moveToCertainAngle(Double hLookAt, Double vLookAt, Double fov) {
		String script = "var krpano = document.getElementById(\"krpanoSWFObject\");krpano.call('loadscene(scene_2,null,MERGE,BLEND(0.5)); lookat("+hLookAt+", "+vLookAt+", "+fov+");');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(script);
		waitForPageToLoad();
	}

	public Long getHorizontalAngle() {
		String script = "return document.getElementById(\"krpanoSWFObject\").get('view.hlookat');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long hlookat =  (Long) js.executeScript(script);
		Long hlookatValue = (long) Math.round(hlookat);
		log.info("Horizontal Angle From Advisor View: "+hlookatValue);
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		return hlookatValue;
	}

	public Long getVerticalAngle() {
		String script = "return document.getElementById(\"krpanoSWFObject\").get('view.vlookat');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long vlookat =  (Long) js.executeScript(script);
		Long vlookatValue = (long) Math.round(vlookat);
		log.info("Horizontal Angle From Advisor View: "+vlookatValue);
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		return vlookatValue;
	}

	public Long getFovAngle() {
		String script = "return document.getElementById(\"krpanoSWFObject\").get('view.fov');";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long fov =  (Long) js.executeScript(script);
		Long fovValue = (long) Math.round(fov);
		log.info("Field of View Angle From Advisor View: "+fovValue);
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		return fovValue;
	}

	public boolean verifyThatSimilarProductSectionIsPresentOrNot(){ return isElementDisplayed(YOUMAYALSOLIKE_SECTION); }

	public boolean verifyThatInspirationSectionIsPresentOrNot(){ return isElementDisplayed(INSPIRATION_SECTION); }

	public boolean verifyThatVariationSectionIsPresentOrNot() { return  isElementDisplayed(COLOR_VARIATIONS); }

	public  void ClickOnDocumentViewer() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(DOCUMENT_VIEWER, MEDIUMWAIT), "Document viewer icon is not displayed");
		safeJavaScriptClick(DOCUMENT_VIEWER, MEDIUMWAIT);
	}

	public void verifyThatToolTipWrapperIsDisplayed(){
		waitForPageToLoad();
		Assert.assertTrue(isElementPresent(TOOLTIP_SECTION, MEDIUMWAIT), "File Viewer tool tip is not present in DOM");
		Assert.assertTrue(isElementVisible(TOOLTIP_SECTION, MEDIUMWAIT), "File Viewer tool tip is not displayed in UI");
	}

	public void clickOnImageDocument() {
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		for(int x = 0; x < 5; x++) {
			try{
				WebElement element = driver.findElement(CONTENTVIEWER_IMAGE_DOCUMENT);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				boolean elementInVisible = isElementInvisible(CONTENTVIEWER_IMAGE_DOCUMENT, MEDIUMWAIT);
				if(!elementInVisible) {
					System.out.println("trying to click file again: "+x);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
				else 
					break;
			}
			catch(Exception e){
				System.out.println("some exception occurred: "+e);
			}
		}
	}

	public void clickOnVideoDocument() {
		// Index starts from 1
		waitForPageToLoad();
		hardWait(VERYSHORTWAIT);
		
		for(int x = 0; x < 5; x++) {
			try{
				WebElement element = driver.findElement(CONTENTVIEWER_VIDEO_DOCUMENT);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				boolean elementInVisible = isElementInvisible(CONTENTVIEWER_VIDEO_DOCUMENT, MEDIUMWAIT);
				if(!elementInVisible) {
					System.out.println("trying to click file again: "+x);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
				else 
					break;
			}
			catch(Exception e){
				System.out.println("some exception occurred: "+e);
			}
		}
	}

	public void verifyThatNoOfGuestsWaitingInLounge (int sNoOfClients) {
		hardWait(SHORTWAIT);
		By guestLoungeText = By.xpath("//p[normalize-space(text())='There is "+sNoOfClients+" guest waiting in the lounge']");
		Assert.assertTrue(isElementVisible(guestLoungeText, MEDIUMWAIT), "Number of clients expected is not matched"+guestLoungeText);
	}

	public void verifyThatClientIsWaitingInLounge(String expectedClientName) {
		String actualClientName = safeGetText(GUESTS_WAITING_INLOUNGE_NAME, MEDIUMWAIT);
		Assert.assertEquals(actualClientName, expectedClientName, "Client Name is not matched with already present in Lounge client, actual name "+actualClientName);
	}

	public void verifyThatDocumentViewerContainerIsNotDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementInvisible(PRESENTATIONCONTAINER, MEDIUMWAIT), "Presentation Viewer is not displayed");
	}

	public void verifyThatSliderViewContainerIsNotDisplayed() {
		Assert.assertTrue(isElementInvisible(SLIDELIST, MEDIUMWAIT), "Dashboard Icon is displayed in client screen");
	}

	public void verifyThatVideoDocumentIsOpened(){
		hardWait(VERYSHORTWAIT);
		Assert.assertTrue(isElementVisible(VIDEOSOURCE, MEDIUMWAIT), "Video file is not displayed");
	}

	public void verifyThatImageDocumentIsOpened() {
		Assert.assertTrue(isElementPresent(IMAGESOURCE, LONGWAIT), "Image file is not displayed");
	}

	public void verifyThatClientHasJoinedMeetingAndDisplayedInAdvisorView (String clientName) {
		waitForPageToLoad(MEDIUMWAIT);
		By CLIENTNAME = By.xpath("//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+clientName+"']");
		Assert.assertTrue(isElementVisible(CLIENTNAME, MEDIUMWAIT), "Client name is not displayed after admitting and joining the meeting");
	}

	public void verifyThatStoriesVideoIsPlaying(){
		waitForPageToLoad(MEDIUMWAIT);
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean value = (boolean) js.executeScript( "return (document.getElementsByClassName('video-container')[0].getElementsByTagName('video'))[0].seeking");
		Assert.assertFalse(value, "Video is not played: " + value);
	}

	public boolean verifyThatVideoHasEnded(){
		waitForPageToLoad();
		return isElementInvisible(VIDEO_DOCUMENT, SHORTWAIT);
	}

	public void verifyThatCameraNotAccessibleTextIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(CAMERANOT_ACCESSIBLE_TEXT, LONGWAIT), "Camera not accessible text is not displayed in advisor join screen");
	}

	public void verifyThatStartSessionWithoutCameraButtonIsDisplayed() {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(STARTSESSION_WITHOUTCAMERA_BUTTON, LONGWAIT), "Start session without camera button is not displayed in advisor join screen");	
	}

	public void clickOnStartSessionWithoutCameraButton() {
		hardWait(VERYSHORTWAIT);
		safeJavaScriptClick(STARTSESSION_WITHOUTCAMERA_BUTTON, MEDIUMWAIT);
	}

	public void verifyCameraButtonIsUnMuted(){
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementInvisible(INMEETING_CAMERA_STATE, MEDIUMWAIT), "camera not muted");
	}

	public void verifyCameraButtonIsMuted(){
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(INMEETING_CAMERA_STATE, MEDIUMWAIT), "camera not muted");
	}

	public void verifyThatAdvisorIsInLocalContainer(String name) {
		waitForPageToLoad();
		By ADVISORNAME = By.xpath("//div[@id='local-wrapper']//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementVisible(ADVISORNAME, MEDIUMWAIT), "Advisor is not in local container");
	}

	public void verifyThatAdvisorIsNotInLocalContainer(String name) {
		waitForPageToLoad();
		By ADVISORNAME = By.xpath("//div[@id='local-wrapper']//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementInvisible(ADVISORNAME, MEDIUMWAIT), "Advisor is still displayed in local container");
	}

	public void verifyAdvisorIsNotInPresentingLayoutMode(String name) {
		waitForPageToLoad();
		By ADVISOR_PRESENTER = By.xpath("//div[@id='presenter-wrapper']//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementInvisible(ADVISOR_PRESENTER, MEDIUMWAIT), "Advisor is still shown in presenting layout");
	}

	public void verifyThatAdvisorIsPresentInLayoutMode(String name) {
		waitForPageToLoad();
		By ADVISOR_PRESENTER = By.xpath("//div[@id='presenter-wrapper']//div[contains(@class,'local-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementVisible(ADVISOR_PRESENTER, MEDIUMWAIT), "Advisor is not shown in presenting layout");
	}

	public void verifyThatClientIsInRemoteContainer(String name) {
		waitForPageToLoad();
		By CLIENTNAME = By.xpath("//div[contains(@class,'remote-video-container')]//div[contains(@class, 'display-name')][normalize-space(text())='"+name+"']");
		Assert.assertTrue(isElementPresent(CLIENTNAME, MEDIUMWAIT), "Client is not seen in presenting layout");
	}

	public boolean verifyThatVideoHasEndedUsingJavaScript() {
		waitForPageToLoad(MEDIUMWAIT);
		String script = "var value = false;" +
						"var vid = (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0];" +
						"vid.onended = function() {" +
						"value = true };" +
						"if(value == true){ return true}" +
						"else{ return false}";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (boolean) js.executeScript(script);
	}

	public boolean verifyThatVideoIsPlayingOrNot() {
		waitForPageToLoad(MEDIUMWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return (document.getElementsByClassName('video-document')[0].getElementsByTagName('video'))[0].ended";
		return (boolean) js.executeScript(script);
	}

	public void verifyLoungeCameraButtonIsDisabled() {
		safeClick(LOUNGE_CAMERABUTTON_DISABLED, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(LOUNGE_CAMERABUTTON_ACTIVE, MEDIUMWAIT), "Camera button is not disabled or toggled off");
	}

	public void verifyLoungeCameraButtonIsEnabled() {
		safeClick(LOUNGE_CAMERABUTTON_ENABLED, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(LOUNGE_CAMERABUTTON_ACTIVE, MEDIUMWAIT), "Camera button is not enabled and or toggled on");
	}

	public void verifyAdvisorLocalVideoIsStreaming() {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack().track.readyState";
		String value = (String) js.executeScript(script);
		Assert.assertTrue(value.contains("live"), "Advisor video is not streaming on local");
	}

	public void verifyAdvisorLocalVideoIsNotStreaming() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack()?.isMuted()";
		String value = (String) js.executeScript(script);
		Assert.assertTrue(value.contains("ended"), "Advisor video is still streaming on local");
	}

	public void verifyAdvisorLocalVideoIsNotStreamingAssertFirstTime() {
		//This needs to be used when advisor disabled camera in lounge
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalVideoTrack()?.isMuted()";
		String value = (String) js.executeScript(script);
		Assert.assertNull(value, "Advisor video is still streaming on local");
	}

	public String getAdvisorParticipantId() {
		String attributeValue = safeGetAttribute(LOCALWRAPPER_VIDEO_ID, "id", MEDIUMWAIT);
		String[] value = attributeValue.split("-");
		return value[1];
	}

	public void verifyClientRemoteVideoIsStreamingForFirstTime(String participantID) {
		hardWait(VERYSHORTWAIT);
		By clientVideoTag = By.cssSelector("#videos-wrapper #video-"+participantID+" video");
		Assert.assertTrue(isElementVisible(clientVideoTag, MEDIUMWAIT), "remote client video tag is not attached");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "room.participants['"+participantID+"']._tracks[1].stream.muted";
		String value = (String) js.executeScript(script);
		Assert.assertNull(value, "Client video is not streaming on advisor view");
	}

	public void verifyClientRemoteVideoIsStreaming(String participantID) {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(t=>t.getType() === 'video')?.isMuted();";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Client video is not streaming on advisor view");
	}

	public void verifyClientRemoteVideoIsNotStreaming(String participantID) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks[1].stream.muted";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertTrue(value, "Client video should not stream on advisor view but it is streaming");
	}

	public void clickCameraButton() {
		waitForPageToLoad();
		safeJavaScriptClick(INMEETING_CAMERA_BUTTON, MEDIUMWAIT);
	}

	public void verifyInMeetingCameraButtonIsEnabled() {
		waitForPageToLoad(MEDIUMWAIT);
		Assert.assertTrue(isElementInvisible(INMEETING_CAMERA_STATE, MEDIUMWAIT), "Camera button is not enabled on advisor view");
	}

	public void enlargeParticipant(String clientName) {
		By client_tile_controls = By.xpath("//div[contains(@class, 'display-name')][normalize-space(text())='"+clientName+"']//following-sibling::div[contains(@class,'ClientControlsButton')]");
		safeJavaScriptClick(client_tile_controls, LONGWAIT);
		Assert.assertTrue(isElementPresent(TOOLTIP_WRAPPER, LONGWAIT), "Enlarge client tool tip wrapper is not displayed");
		safeJavaScriptClick(ENLARGE_PARTICIPANT, VERYLONGWAIT);
		Assert.assertTrue(isElementInvisible(TOOLTIP_WRAPPER, LONGWAIT), "Enlarged client tool tip wrapper is still displayed even after enlarging the participant");
	}

	public void verifyAdvisorLocalAudioIsStreaming() {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalAudioTrack()?.isMuted()";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Advisor audio is not streaming on local");
	}

	public void verifyAdvisorLocalAudioIsNotStreaming() {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.getLocalAudioTrack()?.isMuted()";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Advisor audio is not streaming on local");
	}

	public void verifyClientRemoteAudioIsStreaming(String participantID) {
		hardWait(VERYSHORTWAIT);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(t=>t.getType() === 'audio')?.isMuted();";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Client audio is not streaming on advisor view");
	}

	public void verifyClientEnlargedRemoteVideoIsStreaming(String participantID) {
		hardWait(VERYSHORTWAIT);
		Assert.assertTrue(isElementVisible(ENLARGE_VIDEO, MEDIUMWAIT), "remote client enlarged video tag is not attached");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "return room.participants['"+participantID+"']._tracks.find(t=>t.getType() === 'video')?.isMuted();";
		boolean value = (boolean) js.executeScript(script);
		Assert.assertFalse(value, "Client video is not streaming on advisor view");
	}

	public void changeToPresentationLayoutMode() {
		waitForPageToLoad();
		safeJavaScriptClick(LAYOUT_CONTROL, MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(PRESENTATION_MODE, MEDIUMWAIT), "Layout is not changed to presentation mode");
	}

	public void changeToConversationLayoutMode() {
		waitForPageToLoad(MEDIUMWAIT);
		safeJavaScriptClick(LAYOUT_CONTROL, MEDIUMWAIT);
		Assert.assertTrue(isElementPresent(CONVERSATION_MODE, MEDIUMWAIT), "Layout is not changed to CONVERSATION mode");
	}

	public void hideSlide(int slideNumber) {
		//Slide index starts with 0 as "Zero"
		By hide_button = By.cssSelector("#slide"+slideNumber+" button.btn-hide-page");
		By hide_container = By.cssSelector("#slide"+slideNumber+" .thumbnailHeading");
		mouseHover(hide_container, VERYLONGWAIT);
		safeJavaScriptClick(hide_button, LONGWAIT);
		By slide_hidden = By.cssSelector("#slide"+slideNumber+".hidden");
		Assert.assertTrue(isElementPresent(slide_hidden, LONGWAIT), "Slide-"+slideNumber+" is not hidden after clicking hide button");
	}

	public void verifySlideIsHidden(int slideNumber) {
		//Slide index starts with 0 as "Zero"
		By hidden_state = By.cssSelector("#slide"+slideNumber+" strong.hidden-text");
		Assert.assertTrue(isElementVisible(hidden_state, LONGWAIT), "Slide-"+slideNumber+" is not hidden after clicking hide button");
		String visible_slide = safeGetText(SLIDE_NAVIGATION, LONGWAIT);
		Assert.assertTrue(visible_slide.contains("visible slides"), "visible slides text is not displayed");
	}

	public void verifyHubPageIsLaunched() {
		waitForPageToLoad(LONGWAIT);
		hardWait(1);
		Assert.assertTrue(isElementPresent(HUB_LAYOUT, MEDIUMWAIT), "Hub page is not launched");
	}

	public void enterUsername(String name) {
		waitForPageToLoad(MEDIUMWAIT);
		safeClearAndType(HUB_USERNAME, name, MEDIUMWAIT);
	}

	public void enterPassword(String pass) {
		waitForPageToLoad();
		safeClearAndType(HUB_PASSWORD, pass, MEDIUMWAIT);
		hardWait(1);
	}

	public void clickSignInButton() {
		safeJavaScriptClick(HUB_SIGNIN_BUTTON, MEDIUMWAIT);
		hardWait(VERYSHORTWAIT);
	}

	public void clickOnOpenStorybooksButton() {
		waitForPageToLoad();
		safeJavaScriptClick(OPEN_STORYBOOKS_INMEETING, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(STORYBOOK_POPUP, MEDIUMWAIT), "Storybook popup is not opened");
	}

	public void openStorybookToMeet() {
		waitForPageToLoad();
		safeClick(MY_STORYBOOKS_BUTTON, MEDIUMWAIT);
		safeJavaScriptClick(OPEN_TEST_STORYBOOK, MEDIUMWAIT);
	}

	public void clickOnFavoritesInHub() {
		waitForPageToLoad();
		safeJavaScriptClick(HUB_FAVORITES_BUTTON, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(HUB_FAVORITES_BUTTON_SELECTED, MEDIUMWAIT), "Favorites page is not selected");
	}

	public void clickOnSessionCreateButton() {
		Assert.assertTrue(isElementVisible(HUB_CALEDAR_TEXT, MEDIUMWAIT), "Calendar text is not displayed");
		safeJavaScriptClick(HUB_SESSION_CREATE_BUTTON, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(HUB_CREATE_SESSION_TEXT, MEDIUMWAIT), "Create session text is not displayed and not redirected to create page");
	}

	public void enterSessionName(String topic) {
		safeClearAndType(HUB_SESSION_NAME, topic, MEDIUMWAIT);
		String sessionName = safeGetAttribute(HUB_SESSION_NAME, "Value", MEDIUMWAIT);
		Assert.assertEquals(topic, sessionName, "Entered session topic is not matched");
	}

	public void deSelectCheckBoxes() {
		safeClick(HUB_MUTE_PARTICIPANTS_BUTTON, MEDIUMWAIT);
		safeClick(HUB_AUTO_ADMIT_BUTTON, MEDIUMWAIT);
	}

	public void clickOnShowMoreButton() {
		safeClick(HUB_SESSION_SHOW_BUTTON, MEDIUMWAIT);
		String showLessText = safeGetText(HUB_SESSION_SHOW_BUTTON, MEDIUMWAIT);
		Assert.assertTrue(showLessText.contains("less"), "ShowMore button is not clicked");
	}

	public String getHubHostUrl() {
		waitForPageToLoad();
		return safeGetText(HUB_HOST_URL, MEDIUMWAIT);
	}

	public String getHubGuestUrl() {
		waitForPageToLoad();
		return safeGetText(HUB_CLIENT_URL, MEDIUMWAIT);
	}

	public void saveSession() {
		safeClick(HUB_SAVE_SESSION, MEDIUMWAIT);
		waitForPageToLoad(MEDIUMWAIT);
	}

	public void addStorybooksToSession() {
		safeClick(HUB_ADD_STORYBOOKS, MEDIUMWAIT);
		Assert.assertTrue(isElementVisible(HUB_SESSIONTO_STORYBOOKS_PAGE, MEDIUMWAIT), "Storybooks container page is not opened from create session");
		for(int i=1; i<=2; i++) {
			By HUB_STORYBOOKS_CONTAINER_UPLOAD = By.xpath("(//div[@title='Test']//button)["+i+"]");
			safeClick(HUB_STORYBOOKS_CONTAINER_UPLOAD, MEDIUMWAIT);
		}
		safeClick(HUB_SESSION_STORYBOOKS_OK_BUTTON, MEDIUMWAIT);
		for(int i=1; i<=2; i++) {
			By HUB_STORYBOOKS_FILES_UPLOAD = By.xpath("(//span[text()='Test'])["+i+"]");
			Assert.assertTrue(isElementVisible(HUB_STORYBOOKS_FILES_UPLOAD, MEDIUMWAIT), "Files are not uploaded to storybooks");
		}
	}

	public void clickOnContentFiles(int index) {
		waitForPageToLoad(LONGWAIT);
		for(int x = 0; x < 5; x++) {
			try{
				By contentFile = By.xpath("(//*[text()='Test'])["+index+"]");
				WebElement element = driver.findElement(contentFile);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				boolean elementInVisible = isElementInvisible(contentFile, MEDIUMWAIT);
				if(!elementInVisible) {
					System.out.println("trying to click file again: "+x);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				}
				else
					break;
			}
			catch(Exception e){
				System.out.println("some exception occurred: "+e);
			}
		}
	}

	public void verifySameContentFilesAreNotDisplayed(String source1, String source2) {
		Assert.assertNotEquals(source1, source2, "Both content files are same which has same file names");
	}



}