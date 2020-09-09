package com.lazerycode.selenium;

import com.lazerycode.selenium.config.DriverFactory2;
import com.lazerycode.selenium.listeners.ScreenshotListener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Listeners(ScreenshotListener.class)
public class DriverBase2 {

    private static List<DriverFactory2> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactory2>());
    private static ThreadLocal<DriverFactory2> driverFactoryThread;

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory2 driverFactory = new DriverFactory2();
            webDriverThreadPool.add(driverFactory);
            return driverFactory;
        });
    }

    public static WebDriver getDriver() throws Exception {
        return driverFactoryThread.get().getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public static void clearCookies() {
        try {
            driverFactoryThread.get().getStoredDriver().manage().deleteAllCookies();
        } catch (Exception ignored) {
            System.out.println("Unable to clear cookies, driver object is not viable...");
        }
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        for (DriverFactory2 driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }
}