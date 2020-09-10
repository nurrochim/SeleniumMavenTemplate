package com.lazerycode.selenium.tests;

import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.lazerycode.selenium.DriverBase2;

public class WfhCpnsIT extends DriverBase2 {
	@Test
    public void runRemote() throws Exception{
    	WebDriver driver = getDriver();
    	WfhService wfhService = new WfhService();
    	wfhService.setDriver(driver);
//    	wfhService.setPesanDisclaimer(false);
//    	wfhService.setPesanWfhWfoDinas(false);
    	
    	// group cpns
//    	wfhService.setFindByChatByGroupName("PAPI 2018");
    	Calendar cal = Calendar.getInstance();
    	int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    	System.out.println(cal.getTime()+" /n "+ currentHour);
    	if (currentHour < 12) {
    		wfhService.setKey("Checkin");
    	}else {
    		wfhService.setKey("Checkout");
    	}
//    	wfhService.setKey("Checkout");// test only
    	
//    	wfhService.wfhHistory(wfhService.getPersonsName2());
//    	if(!wfhService.getIsClearCiCo()) {
    		wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
//    	}
    	
    	driver.close();
    }
}
