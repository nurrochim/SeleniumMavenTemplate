package com.lazerycode.selenium.tests;

import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.lazerycode.selenium.DriverBase2;

public class WfhDirInovasiIT extends DriverBase2 {
	@Test
    public void runRemote() throws Exception{
    	WebDriver driver = getDriver();
    	WfhService wfhService = new WfhService();
    	wfhService.setDriver(driver);
//    	wfhService.setPesanWfhWfoDinas(false);
    	
    	// group Direktorat Inovasi
//    	wfhService.setFindByChatByGroupName("Inovasi Industri");
    	Calendar cal = Calendar.getInstance();
    	int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    	System.out.println(cal.getTime()+" /n "+ currentHour);
    	if (currentHour < 12) {
    		wfhService.setKey("Checkin");
    	}else {
    		wfhService.setKey("Checkout");
    	}
//    	if (currentHour < 7 ) {
    		wfhService.setPesanDisclaimer(true);
//    	}
    	
//    	wfhService.wfhHistory(wfhService.getPersonsName1());
    	wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
    	
    	driver.close();
    }
}
