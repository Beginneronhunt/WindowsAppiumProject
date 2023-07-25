package com.gears;

import static org.assertj.core.api.Assertions.assertThat;

import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NixFreshEnrollmentTest {

  private static WindowsDriver nixSession;
  private static WebDriverWait wait;
  String nixStatus;

  @BeforeClass
  public void setUp() {
    try {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(
        "app",
        "C:\\Program Files (x86)\\42Gears\\Nix Agent\\SureMDM Agent.exe"
      );
      capabilities.setCapability("platformName", "Windows");
      capabilities.setCapability("automationName", "Windows");
      capabilities.setCapability("deviceName", "WindowsPC");
      nixSession =
        new WindowsDriver(new URL("http://172.16.201.180:4723"), capabilities);
    } catch (Exception e) {
      e.printStackTrace();
    }
    nixSession.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    wait = new WebDriverWait(nixSession, 60);
    }

  @AfterClass
  public void tearDown() {
    nixSession.quit();
  }

  public String getAppTitle() {
    WebElement appTitle = nixSession.findElementByName("SureMDM Agent");
    String agentName = appTitle.getAttribute("Name");
    return agentName;
  }

  public Boolean isNixSettingsPageDisplayed() {
    Boolean backBtnIcon = false;
    WebElement homePage = nixSession.findElementByXPath(
      "/Window/Custom/Button/Image"
    );
    backBtnIcon = homePage.isDisplayed();
    return backBtnIcon;
  }

  public void goToNixHomePage(){
      WebElement backButton = nixSession.findElementByXPath(
      "/Window/Custom/Button/Image"
    );
    backButton.click();
  }

  public void clickOnSettings() {
    WebElement settings = wait.until(
      ExpectedConditions.elementToBeClickable(
        nixSession.findElementByAccessibilityId("SettingsButton")
      )
    );
    settings.click();
  }

  public void clickOnAccountID() {
    WebElement accountID = wait.until(
      ExpectedConditions.elementToBeClickable(
        nixSession.findElementByAccessibilityId("AccountIdSumary")
      )
    );
    accountID.click();
  }

  public void addAccountId() {
    WebElement newAccountID = wait.until(
      ExpectedConditions.elementToBeClickable(
        nixSession.findElementByAccessibilityId("NewAccountID")
      )
    );
    newAccountID.click();
    newAccountID.clear();
    newAccountID.sendKeys("082200043");
    WebElement okAccountBtn = nixSession.findElementByXPath(
      "//*[@AutomationId='AccountIdView']//Button[@Name='OK']"
    );
    okAccountBtn.click();
  }

  public void clickOnServerPath() {
    WebElement serverPath = nixSession.findElementByAccessibilityId(
      "ServerPathSummary"
    );
    serverPath.click();
  }

  public void addServerPath() {
    WebElement newServerPath = nixSession.findElementByAccessibilityId(
      "NewServerPath"
    );
    newServerPath.click();
    newServerPath.clear();
    newServerPath.sendKeys("swastikinternational.suremdm.io");
    WebElement okServerPathBtn = nixSession.findElementByXPath(
      "//*[@AutomationId='ServerPathView']//Button[@Name='OK']"
    );
    okServerPathBtn.click();
  }

  public String getNixServiceStatus() {
    WebElement fetchNixStatus = nixSession.findElementByAccessibilityId(
      "Status"
    );
    nixStatus = fetchNixStatus.getAttribute("Name");
    return nixStatus;
  }

  public void disableNix() {
    WebElement disableNix = nixSession.findElementByAccessibilityId(
      "EnableNix"
    );
    disableNix.click();
   }

  public void enableNix() {
    WebElement enableNix = nixSession.findElementByAccessibilityId("EnableNix");
    enableNix.click();
    }

  @Test(priority = 1)
  public void testConfigureNix() throws InterruptedException {
    Boolean status = false;
    try {
      status = isNixSettingsPageDisplayed();
    } catch (Exception ex) {}

    if (status) {
      clickOnAccountID();
      addAccountId();
      clickOnServerPath();
      addServerPath();
      enableNix();
      Thread.sleep(30000);
      goToNixHomePage();      
      String nixServiceStatus= getNixServiceStatus();
      assertThat(nixServiceStatus).containsIgnoringCase("Online");
      } else {
      clickOnSettings();
      clickOnAccountID();
      addAccountId();
      clickOnServerPath();
      addServerPath();
      enableNix();
      Thread.sleep(20000);
      goToNixHomePage();      
      String nixServiceStatus= getNixServiceStatus();
      assertThat(nixServiceStatus).containsIgnoringCase("Online");
      nixSession.closeApp();
      }
  }

  @Test(priority = 2)
  public void testAppTitle() throws InterruptedException {
    Thread.sleep(10000);
    nixSession.launchApp();
    String agentName = getAppTitle();
    assertThat(agentName).isEqualTo("SureMDM Agent");
    }

  @Test(priority = 3)
  public void testDisableNix() throws InterruptedException {
    clickOnSettings();    
    disableNix();
    Thread.sleep(20000);
    goToNixHomePage();      
    String nixServiceStatus = getNixServiceStatus();
    assertThat(nixServiceStatus).containsIgnoringCase("Stopped");
    nixSession.closeApp();
  }
}
