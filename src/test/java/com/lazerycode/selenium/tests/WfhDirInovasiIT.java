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
    	
    	// group Direktorat Inovasi
    	wfhService.setFindByChatByGroupName("Inovasi Industri");
    	System.out.println("Jam running "+cal.getTime()+"  "+ currentHour);
    	if ((currentHour > 8 && currentHour < 12) || (currentHour > 19)) {
    		// HANYA PESAN YANG BELUM CICO SAJA, YANG SUDAH CICO GA USAH TAMPIL
    		wfhService.setPesanWfhWfoDinas(false);
    		wfhService.setPesanBelumCiCo(true);
    	}
    	if (currentHour < 7 || (currentHour > 14 && currentHour < 17)) {
    		// HANYA PESAN YANG SUDAH CICO SAJA, YANG BELUM CICO GA USAH TAMPIL
    		wfhService.setPesanWfhWfoDinas(true);
    		wfhService.setPesanBelumCiCo(false);
    	}
    	
    	
    	if (currentHour < 12) {
    		wfhService.setKey("Checkin");
    	}else {
    		wfhService.setKey("Checkout");
    	}
//    	if (currentHour == 5 && dateNow.equals("14/09/2020")) {
//		//if (currentHour == 5) {
//    		wfhService.setPesanDisclaimer(true);
//    		wfhService.setPesanWfhWfoDinas(false);
//    		wfhService.setPesanBelumCiCo(false);
//    	}
    	
    	if (currentHour != 5) {
    		wfhService.setPesanDisclaimer(true);
    	wfhService.wfhHistory(wfhService.getPersonsName1());
    	if(!wfhService.getIsClearCiCo()) {
    		wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
    	}
    	}
    	driver.close();
    }
}
