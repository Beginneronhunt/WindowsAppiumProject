// package com.gears;

// import io.appium.java_client.MobileBy;
// import io.appium.java_client.windows.WindowsDriver;
// import java.net.URL;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.remote.DesiredCapabilities;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.testng.annotations.AfterClass;
// import org.testng.annotations.Test;
// import java.util.concurrent.TimeUnit;

// public class TestLocator {

//   private static WindowsDriver nixSession;
//   private static WebDriverWait wait;
//   String nixStatus;

//   public static void setUp() {
//     try {
//       DesiredCapabilities capabilities = new DesiredCapabilities();
//       capabilities.setCapability(
//         "app",
//         "C:\\Program Files (x86)\\42Gears\\Nix Agent\\SureMDM Agent.exe"
//       );
//       capabilities.setCapability("platformName", "Windows");
//       capabilities.setCapability("automationName", "Windows");
//       capabilities.setCapability("deviceName", "WindowsPC");
//       nixSession =
//         new WindowsDriver(new URL("http://172.16.201.180:4723"), capabilities);
//     } catch (Exception e) {
//       e.printStackTrace();
//     }
//     nixSession.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//     wait = new WebDriverWait(nixSession, 60);
//     wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//   }

//   @AfterClass
//   public void tearDown() {
//     nixSession.quit();
//   }

//   public void enableNix() {
//     WebElement enableNix = nixSession.findElementByAccessibilityId("EnableNix");
//     enableNix.click();
//      WebElement nixToast = wait.until(
//       ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("ToastMessage"))
//       );
//     String toastMessage = nixToast.getAttribute("Name");
//     System.out.println("Status is: " + nixStatus);
//     assertThat(toastMessage).containsIgnoringCase("enabled successfully");
//   }

//   @Test(priority = 1)
//   public void testConfigureNix() {
//     Boolean status = false;
//     try {
//       status = isNixSettingsPageDisplayed();
//     } catch (Exception ex) {}

//     if (status) {
//       clickOnAccountID();
//       addAccountId();
//       clickOnServerPath();
//       addServerPath();
//       enableNix();
//       nixSession.closeApp();
//     } else {
//       clickOnSettings();
//       clickOnAccountID();
//       addAccountId();
//       clickOnServerPath();
//       addServerPath();
//       enableNix();
//       nixSession.closeApp();
//     }
//   }
// }
