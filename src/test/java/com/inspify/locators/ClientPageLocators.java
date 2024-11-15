package com.inspify.locators;

import org.openqa.selenium.By;

public interface ClientPageLocators {
	By CLIENTNOTE_SECTION = By.cssSelector("div[class$='client-note']");
	By JOINNOW_BUTTON = By.xpath("//button[normalize-space(text())='Join Now']");
	By HOSTMESSAGE = By.cssSelector("span.host-info");
	By NAME_INPUTFIELD = By.cssSelector("input.input-alias");
	By RAISEHAND_ICON = By.cssSelector("div[class*='raise-hand']");
	By VIEWONLY_ROLEINDICATOR_TEXT = By.xpath("//div[contains(@class,'role-indicator')]//span[normalize-space(text())='View Only']");
	By IFRAMECONTAINER = By.cssSelector("#content-popup");
	By FIRSTPRODUCT_MODELTEXT = By.cssSelector("#showcase-0 .prod-model");
	By PRODUCTSPOPUP_CROSSICON = By.cssSelector("button.close");
	By SIDEMENUNAVIGATOR_ICON = By.cssSelector("button.slidenav-icon");
	By PRODUCTSEARCH_MENUITEM = By.cssSelector("button.nav-search");
	By PRODUCTCOMPARE_MENUITEM = By.cssSelector("button.nav-compare");
	By PRODUCTSEARCH_PAGETITLE = By.xpath("//span[normalize-space(text())='PRODUCT SEARCH']");
	By STORYSEARCH_PAGETITLE = By.xpath("//span[normalize-space(text())='STORY SEARCH']");
	By SEARCH_INPUT = By.cssSelector(".keywords-input>input");
	By ALLPRODUCTSMODELTITLE = By.cssSelector(".prod-title");
	By EMPTYMESSAGE_COMPAREPRODUCTSPOPUP_TEXT = By.xpath("//h3[normalize-space(text())=\"You don't have any products to be compared\"]");
	By SEARCHPRODUCTS_BUTTON = By.xpath("//button[normalize-space(text())='SEARCH PRODUCTS']");
	By PRODUCT_MODEL_CODE = By.cssSelector(".modelcode");
	By PRODUCT_NAME = By.tagName("h1");
	By DESCRIPTIONSECTION = By.xpath("//span[normalize-space(text())='Description']");
	By SPECIFICATIONSSECTION = By.xpath("//span[normalize-space(text())='Specifications']");
	By VEIL_LAYYER = By.cssSelector(".veil");
	By DESCRIPTIONSECTION_COLLAPSED = By.xpath("//span[normalize-space(text())='Description']/../following-sibling::div[@class='hide collapse']");
	By DESCRIPTIONSECTION_EXPANDED = By.xpath("//span[normalize-space(text())='Description']/../following-sibling::div[contains(@class,'show collapse')]");
	By DESCRIPTIONTEXT = By.cssSelector(".descriptions-container");
	By SPECIFICATIONSECTION_COLLAPSED = By.xpath("//span[normalize-space(text())='Specifications']/../following-sibling::div[@class='hide collapse']");
	By SPECIFICATIONSECTION_EXPANDED = By.xpath("//span[normalize-space(text())='Specifications']/../following-sibling::div[contains(@class,'show collapse')]");
	By SPECIFICATIONSTEXT = By.cssSelector(".specifications-container");
	By PRODUCT_PRICE = By.xpath("(//h1)/following-sibling::p[contains(@class,'price')]");
	By PRODUCT_DESCRIPTIONTEXT=By.cssSelector(".descriptions-container");
	By PRODUCT_SPECIFICATIONTEXT= By.cssSelector(".specifications-container");
	By STORY_TITLE = By.tagName("h1");
	By STORY_DESCRIPTION = By.xpath("//P[@id='ijxbg']");
	By WATCHESCOLLECTION_HEADER_TEXT = By.xpath("//p[normalize-space(text())='PILOT’S WATCHES COLLECTION']");
	By CHOPARDDASHBOARD_ICON = By.cssSelector("button.btn-express");
	By VIEWFILEMENU_BUTTON = By.cssSelector(".ViewFileButton");
	By INVITECLIENT_BUTTON = By.cssSelector(".InviteClientButton");
	By QRCODE_BUTTON = By.cssSelector(".QRCodeButton");
	By PRESENTATIONCONTAINER = By.cssSelector(".DocumentViewer");
	By SLIDELIST = By.cssSelector(".PageList");
	By IMAGESOURCE = By.cssSelector(".DocumentViewer .DocumentPage > img");
	By VIDEOSOURCE = By.cssSelector(".DocumentViewer .DocumentPage video");
	By MICROPHONEMUTE_MESSAGE = By.cssSelector("div.ntf-label");
	By NOTIFICATION_BAR = By.cssSelector("div.notification-button");
	By PRESENTATIONPOPUP_CROSSICON = By.cssSelector(".popup-control button.btn:nth-child(3)");
	By AUDIO_UNMUTE_TEXT = By.xpath("//span[normalize-space()='Unmute My Mic']");
	By AUDIO_MUTE_TEXT = By.xpath("//span[normalize-space()='Mute My Mic']");
	By ACTIONCONTAINERMENU_ITEM = By.cssSelector(".action-groups");
	By STORYFILTER_SECTION = By.cssSelector(".StoryFilters");
	By STORIES_DESCRIPTION = By.cssSelector("p:nth-child(1)");
	By ALLSTORIES_TITLE = By.cssSelector(".results-col .row .item span");
	By DOCUMENT_VIEWER =  By.xpath("//div[@id='openDocument']");
	By VIDEO_DOCUMENT = By.cssSelector(".video-document");
	By JOINNOW_WITHOUTCAMERA_BUTTON = By.xpath("//button[normalize-space(text())='Join now without camera']");
	By CAMERANOT_ACCESSIBLE_TEXT = By.xpath("//p[normalize-space(text())='Your camera is not accessible at the moment.']");
	By CAMERA_UNMUTE_TEXT = By.xpath("//span[normalize-space()='Turn on My Cam']");
	By CAMERA_MUTE_TEXT = By.xpath("//span[normalize-space()='Turn off My Cam']");
	By LOCALWRAPPER_VIDEO_ID = By.cssSelector("div#local-wrapper > div");
	By INMEETING_CAMERA_BUTTON = By.cssSelector("div#meetingVideoControl");
	By ENLARGE_VIDEO_LOCAL = By.cssSelector("div.enlarge-video video.local-video");
	By ENLARGE_VIDEO = By.cssSelector("div.enlarge-video video.remote-video");
}
