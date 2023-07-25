package com.gears;

import io.appium.java_client.windows.WindowsDriver;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

  public static WindowsDriver nixSession;
  public static WebDriverWait wait;
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
}
