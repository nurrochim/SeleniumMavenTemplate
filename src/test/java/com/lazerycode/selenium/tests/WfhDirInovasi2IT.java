package com.lazerycode.selenium.tests;

import static org.assertj.core.api.Assertions.setMaxLengthForSingleLineDescription;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.lazerycode.selenium.DriverBase2;

public class WfhDirInovasi2IT extends DriverBase2 {
	Logger logger = null;
	
	
	@Test
    public void runRemote() throws Exception{
		Calendar cal = Calendar.getInstance();
    	int currentHour = cal.get(Calendar.HOUR_OF_DAY);
    	int currentMinute = cal.get(Calendar.MINUTE);
    	int currentDay = cal.get(Calendar.DAY_OF_WEEK);
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//    	String dateNow = sdf.format(cal.getTime());
    	
    	loggerInit();
    	
    	WfhService2 wfhService = new WfhService2();
    	wfhService.setNameOfProperties("config2.properties");
    	wfhService.setLogger(logger);
    	
    	logger.log(Level.INFO, "Start Service | Jam running "+cal.getTime()+"  "+ currentHour);
    	//System.out.println("Jam running "+cal.getTime()+"  "+ currentHour);
    	
    	// readProperties
    	wfhService.readProperties();
		wfhService.setInitProperties(currentHour, currentMinute, currentDay);
    	
		logger.log(Level.INFO, "Sudah CO Properties = " +wfhService.getSudahCicoProp());
		logger.log(Level.INFO, "Belum CO Properties = " + wfhService.getBelumCicoProp());
//    	System.out.println("Sudah CO Properties = " +wfhService.getSudahCicoProp());
//		System.out.println("Belum CO Properties = " + wfhService.getBelumCicoProp());
		
		// Jam nya Checkin atau Chekout
		if (currentHour < 12) {
    		wfhService.setKey("Checkin");
    	}else {
    		wfhService.setKey("Checkout");
    	}
    	
		if(wfhService.getCicoService()) {
			WebDriver driver = getDriver();
	    	wfhService.setDriver(driver);
	    	wfhService.wfhHistory(wfhService.getPersonsName1());
	    	wfhService.addNumberToMapHistory();
	    	
			
//			wfhService.setFindByChatByGroupName("Inovasi Industri");
			
			int cicoCurrent = wfhService.getSudahCicoCurrent();
			logger.log(Level.INFO, "CicoCurrent = "+cicoCurrent);
//			if(cicoCurrent>=10 && cicoCurrent<20 && !wfhService.getSavePoint1()) {
//				logger.log(Level.CONFIG, "SavePoint1 | Init Remote Whatsapp : ");
//				wfhService.setPesanBelumCiCo(false);
//				wfhService.pesanWhatsappCompile();
//				wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
//				wfhService.setSavePoint1(true);
//			}else 
			if(cicoCurrent>=15 && cicoCurrent<25 && !wfhService.getSavePoint2()) {
				logger.log(Level.CONFIG, "SavePoint2 | Init Remote Whatsapp : ");
				wfhService.setPesanBelumCiCo(false);
				wfhService.pesanWhatsappCompile();
				wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
				wfhService.setSavePoint1(true);
				wfhService.setSavePoint2(true);
			}else if(cicoCurrent>=35 && cicoCurrent<wfhService.getCicoMaxPersons() && !wfhService.getSavePoint3()) {
				logger.log(Level.CONFIG, "SavePoint3 | Init Remote Whatsapp : ");
				wfhService.pesanWhatsappCompile();
				wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
				wfhService.setSavePoint1(true);
				wfhService.setSavePoint2(true);
				wfhService.setSavePoint3(true);
				wfhService.setTotalSavePoint3(cicoCurrent);
			} 
			
			if(cicoCurrent==wfhService.getCicoMaxPersons()) {
				logger.log(Level.WARNING, "LastMsg | Init Remote Whatsapp : ");
				wfhService.pesanWhatsappCompile();
				wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
				wfhService.setSavePoint1(true);
				wfhService.setSavePoint2(true);
				wfhService.setSavePoint3(true);
				wfhService.setCicoService(false);
			}else if(cicoCurrent>=33 && cicoCurrent<wfhService.getCicoMaxPersons() && wfhService.getSavePoint3()) {
				if ((currentHour > 7 && currentHour < 12) || (currentHour > 19)) {
					int retry = wfhService.getCicoServiceRetry()+1;
					wfhService.setCicoServiceRetry(retry);
					logger.log(Level.CONFIG, "Retry Scheduler : "+retry);
					
					if(retry>4 && cicoCurrent>wfhService.getTotalSavePoint3())  {
						logger.log(Level.WARNING, "LastMsgMaxRetry | Init Remote Whatsapp : ");
						wfhService.pesanWhatsappCompile();
						wfhService.wfhHistorySendToWhatsapp(wfhService.getFindByChatByGroupName());
						wfhService.setCicoService(false);
					}
				}
			}
			
			wfhService.writeProperties();
			driver.close();
			
			cal = Calendar.getInstance();
			logger.log(Level.INFO, "End Service | Time "+cal.getTime());
		}
    }
	
	
	public void loggerInit() throws SecurityException, IOException {
		// create logger
//		System.setProperty("java.util.logging.SimpleFormatter.format","%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
		System.setProperty("java.util.logging.SimpleFormatter.format","[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(WfhDirInovasi2IT.class.getName());
    	
    	Handler consoleHandler = new ConsoleHandler();
    	Handler fileHandler  = new FileHandler("./WfhDirInovasi2IT.log", true);
    	SimpleFormatter formatter = new SimpleFormatter();
    	fileHandler.setFormatter(formatter);
    	//Assigning handlers to LOGGER object
    	logger.addHandler(consoleHandler);
    	logger.addHandler(fileHandler);
         
        //Setting levels to handlers and LOGGER
        consoleHandler.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
	}
}
