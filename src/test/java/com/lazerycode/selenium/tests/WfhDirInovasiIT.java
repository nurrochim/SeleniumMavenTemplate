package com.lazerycode.selenium.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.lazerycode.selenium.DriverBase2;

public class WfhDirInovasiIT extends DriverBase2 {
	@Test
    public void runRemote() throws Exception{
		Calendar cal = Calendar.getInstance();
    	int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	String dateNow = sdf.format(cal.getTime()); 
    	
		WebDriver driver = getDriver();
    	WfhService wfhService = new WfhService();
    	wfhService.setDriver(driver);
    	if (currentHour >= 20 || (currentHour > 8 && currentHour < 12)) {
    		wfhService.setPesanWfhWfoDinas(false);
    	}
    	
    	// group Direktorat Inovasi
//    	wfhService.setFindByChatByGroupName("Inovasi Industri");
    	
    	System.out.println("Jam running "+cal.getTime()+"  "+ currentHour);
    	if (currentHour < 12) {
    		wfhService.setKey("Checkin");
    	}else {
    		wfhService.setKey("Checkout");
    	}
    	if (currentHour == 6 && dateNow.equals("14/09/2020")) {
    		wfhService.setPesanDisclaimer(true);
    	}
    	
    	wfhService.wfhHistory(wfhService.getPersonsName1());
    	wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
    	
    	driver.close();
    }
}
