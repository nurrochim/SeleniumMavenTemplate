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
    	int currentMinute = cal.get(Calendar.MINUTE);
    	int currentDay = cal.get(Calendar.DAY_OF_WEEK);
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	String dateNow = sdf.format(cal.getTime());
    	

    	
		WebDriver driver = getDriver();
    	WfhService wfhService = new WfhService();
    	wfhService.setDriver(driver);
    	
    	// group Direktorat Inovasi
    	wfhService.setFindByChatByGroupName("Inovasi Industri");
    	System.out.println("Jam running "+cal.getTime()+"  "+ currentHour);
    	
    	if (currentHour == 6 && currentMinute < 20) {
    		wfhService.setSudahCicoProp(0);
    		wfhService.setBelumCicoProp(0);
    		wfhService.writeProperties(0,0);
    	}
    	
    	// 6 itu jumat
    	if(currentDay<6) {
    		if (currentHour == 15) {
    			wfhService.setSudahCicoProp(0);
        		wfhService.setBelumCicoProp(0);
        		wfhService.writeProperties(0,0);
        	}
    	}else {
    		// jumat
    		if (currentHour == 16 && currentMinute < 15) {
    			wfhService.setSudahCicoProp(0);
        		wfhService.setBelumCicoProp(0);
        		wfhService.writeProperties(0,0);
        	}
    	}
    	
    	// readProperties
    	wfhService.readProperties();
    	
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
    	
//    	if (currentHour == 6 && currentMinute < 20  && dateNow.equals("14/09/2020")) {
		//if (currentHour == 5) {
//    		wfhService.setPesanDisclaimer(true);
//    		wfhService.setPesanWfhWfoDinas(false);
//    		wfhService.setPesanBelumCiCo(false);
//    	}
    	
    	wfhService.wfhHistory(wfhService.getPersonsName1());
		wfhService.pesanWhatsappCompile();
		
		if ((currentHour > 8 && currentHour < 12) || (currentHour > 19)) {
			if(wfhService.getSudahCicoCurrent() > wfhService.getSudahCicoProp()) {
				wfhService.setPesanWfhWfoDinas(true);
				wfhService.setPesanBelumCiCo(true);
			} 
			if(wfhService.getBelumCicoCurrent() == wfhService.getBelumCicoProp()) {
				wfhService.setPesanBelumCiCo(false);
			}
		}
		
		wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
		
		// save to properties
		wfhService.writeProperties(wfhService.getSudahCicoCurrent(),wfhService.getBelumCicoCurrent());
		
    	driver.close();
    }
}
