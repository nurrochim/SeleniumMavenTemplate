package com.lazerycode.selenium.tests;

import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.lazerycode.selenium.DriverBase2;

public class WfhCiCoIT extends DriverBase2 {

	Calendar cal = Calendar.getInstance();
	int currentHour = cal.get(Calendar.HOUR_OF_DAY);
	String jenisKehadiran = "Checkin";
	String namaASN = "Nurochim, S.Kom.";
	String lokasiKerja = "WFH";
	
	@Test
    public void runRemote() throws Exception{
		WebDriver driver = getDriver();
		
		if (currentHour < 12) {
    		jenisKehadiran = "Checkin";
    	}else {
    		jenisKehadiran = "Checkout";
    	}
		
		
		// remote web
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("https://wfh.ristekbrin.go.id/");
		driver.switchTo().alert().accept();
		
		Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("es_1")));
		Select unitKerja = new Select(driver.findElement(By.id("es_1")));
		unitKerja.selectByVisibleText("Deputi Bidang Penguatan Inovasi");
		
		// wait name
		Thread.sleep(2000);
		Select dbNamaASN = new Select(driver.findElement(By.id("nama")));
		dbNamaASN.selectByVisibleText(namaASN);
		
		// select Radio Button Lokasi Kerja (default = WFH
		WebElement option = null;
		if(lokasiKerja.equals("WFO")) {
			option = driver.findElement(By.id("kantor"));
		}
		if(lokasiKerja.equals("Dinas")) {
			option = driver.findElement(By.id("dinas"));
		}
		if(option!=null) {
			option.click();
		}
		
		//  select Radio Button Jenis Kehadiran (default = Checkin)
		if(jenisKehadiran.equals("Checkout")) {
			WebElement optionKeharidan = driver.findElement(By.id("checkout"));
			optionKeharidan.click();
		}
		
		Thread.sleep(2000);
		WebElement simpanBtn = driver.findElement(By.id("simpan"));
    	simpanBtn.click();
		
		driver.close();
	}
}
