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
//    	String dateNow = sdf.format(cal.getTime());
    	
    	WfhService wfhService = new WfhService();
    	
    	System.out.println("Jam running "+cal.getTime()+"  "+ currentHour);
    	// readProperties
    	wfhService.setNameOfProperties("config.properties");
    	wfhService.readProperties();
		wfhService.setInitProperties(currentHour, currentMinute, currentDay);
    	
    	System.out.println("Sudah CO Properties = " +wfhService.getSudahCicoProp());
		System.out.println("Belum CO Properties = " + wfhService.getBelumCicoProp());
    	
		// Check if sudahCiCo > 0 and belumCiCo = 0 
		// artinya system sudah jalan dan semua sudah CICO 
		// Jadi ga perlu lagi jalankan service
		if(wfhService.getSudahCicoProp()>0 && wfhService.getBelumCicoProp()==0) {
			System.out.println("sebelum return : sudahCiCo > 0 and belumCiCo = 0");
			return;
		}
		
		// Jam nya Checkin atau Chekout
		if (currentHour < 12) {
    		wfhService.setKey("Checkin");
    	}else {
    		wfhService.setKey("Checkout");
    	}
		
    	if ((currentHour > 7 && currentHour < 12) || (currentHour > 20)) {
    		// HANYA PESAN YANG BELUM CICO SAJA, YANG SUDAH CICO GA USAH TAMPIL
    		wfhService.setPesanWfhWfoDinas(false);
    		wfhService.setPesanBelumCiCo(true);
    	}
    	if (currentHour < 7 || (currentHour > 14 && currentHour < 17)) {
    		// HANYA PESAN YANG SUDAH CICO SAJA, YANG BELUM CICO GA USAH TAMPIL
    		wfhService.setPesanWfhWfoDinas(true);
    		wfhService.setPesanBelumCiCo(false);
    	}
    	
    	WebDriver driver = getDriver();
    	wfhService.setDriver(driver);
    	wfhService.wfhHistory(wfhService.getPersonsName1());
    	wfhService.addNumberToMapHistory();
		
		
		// di jam-jam terakhir, jika ada update sudah cico sampaikan ke group (belum CiCo nya yg disampaikan)
		// jika tidak ada update cico, ya ga usah disampaikan ke group
 		if ((currentHour > 7 && currentHour < 12) || (currentHour > 20)) {
			if(wfhService.getBelumCicoCurrent() > 0 // selama masih ada yang belum CO maka tampilkan Belum CO nya 
					&& wfhService.getBelumCicoCurrent() < wfhService.getBelumCicoProp()) // dan currentnya telah menurun atau < dari CicoProp 
			{
				wfhService.setPesanBelumCiCo(true);
			}else {
				wfhService.setPesanBelumCiCo(false);
			}
		}
		
 		
 		wfhService.pesanWhatsappCompile();
		// save to properties
		System.out.println("Sudah CiCo Actual = " +wfhService.getSudahCicoCurrent());
		System.out.println("Belum CiCo Actual = " + wfhService.getBelumCicoCurrent());
		System.out.println("Pesan WfhWfoDinas = " + wfhService.getPesanWfhWfoDinas());
		System.out.println("Pesan Belum CiCo = " + wfhService.getPesanBelumCiCo());
		wfhService.writeProperties();
		
		// send wea
		System.out.println("Init Remote Whatsapp : ");
		wfhService.setFindByChatByGroupName("Inovasi Industri");
		if(wfhService.getPesanBelumCiCo()||wfhService.getPesanWfhWfoDinas()||(wfhService.getBelumCicoCurrent() == 0 && wfhService.getBelumCicoProp()>0)) {
			System.out.println("Start Remote Whatsapp");
			wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
		}
		System.out.println("End Remote Whatsapp");
		
		driver.close();
    }
}

// baru saja clearCiCO 
//(wfhService.getBelumCicoCurrent() == 0 && wfhService.getBelumCicoProp()>0)