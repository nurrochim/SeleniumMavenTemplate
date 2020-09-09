package com.lazerycode.selenium.tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.annotations.Test;

import com.lazerycode.selenium.DriverBase2;

public class WfhIT extends DriverBase2 {
	WebDriver driver;
	String pesanWhatsapp = "";
	String findByChatByGroupName = "Test Group Privateku";
	String[] personsName1 = {"Ir. Santosa Yudo Warsono, MT","Aldi Haryadi, S.T","Drs. Erwin Sjachrial","Ir Wiwiek Joelijani, MT","Novi Mukti Rahayu, S.T., M.T.","Maryunis, S.E."
								,"Rino, S.E","Dody Styawan","Agung Budi Raharjo","Febrianto","Aji Siswo Utomo","Syifa Khoiriyah","Karmo","Muhammad Mustakim",
								"Yenni Kusumawati","Aditya Randika","Raditya Dananjaya","Agus Prihartono","Cornelia Tantri W","Amir Faisal Manurung","Alwis",
								"Erlani Pusparini","Dicky Kurniawan","Noor Indriasari","Sri Utami, S.Sos","Yulmedianti Karlina Nancy","Anteng Setia Ningsih",
								"Ulfi Perdanawati","Rahmatika Jihad","Ahmad, ST., M.T. Ph.D","Jimmy Akhmadi","Fitri Ramadhani A",
								"Radiwan, SE","Nurochim","Bambang Herlambang","Iskandar, S.Si","Nila Juwita","Edi Sumedi","Teddy Adhitya"}; 
	
	String[] personsName2 = {"Sarah Fairuz", "Ninik Puji Astuti","Yunida Hary Wardany", "Gracia Krisantiana Agustin","Regina Putri", "Oktarina Elik",
							"Lita Foresti","Mega Fatimah","Shintya Asih Angelita","Uci Sri Sundari",
							"Nurochim", "Aditya Randika", "Raditya Dananjaya", "Dicky Kurniawan","Andi Azhari Putra","Yusnan Rizky", "Moslem Afrizal",
							"Widi Fauzi Asari","Darius Ruruk Paembonan", "Dody Apriadi Indrakusuma", "Hanafi Ahmad Subrata Lubis", "Sefryan Daru"
							};
	//String key = "Checkin";
	String key = "Checkout";
	Map<String, String> mapHistory = new HashMap<>();
	Date dateNow = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy", new Locale("id", "ID"));
	
    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }

//    @Test
    public void wfhHistory(String[] personsName) throws Exception {
    
    	
    	
    	
//    	System.setProperty("webdriver.chrome.driver",
//				"D:\\\\Programmer Study\\\\Automated Test\\\\chromedriver_win32\\\\chromedriver.exe");
//    	WebDriver driver = new ChromeDriver();
//    	
    	driver.get("https://wfh.ristekbrin.go.id/dashboard/history");
    	//driver.manage().window().maximize();
    	Thread.sleep(3000);
    	
    	String jenisKehadiran = "";
    	String wfhWfoDinas = "";
    	for(String nameKey: personsName) {
    		jenisKehadiran = "";
	    	wfhWfoDinas = "";
	    	WebElement findByName = driver.findElement(By.id("nama"));
	    	findByName.sendKeys(nameKey);
	    	
	    	WebElement simpanBtn = driver.findElement(By.id("simpan"));
	    	simpanBtn.click();
	    	
	    	Thread.sleep(2000);
	    	WebElement tableHistory = driver.findElement(By.id("table"));
	    	List<WebElement> rows = tableHistory.findElements(By.tagName("tr"));
	    	
//	    	List<String> jenisKehadirans = new ArrayList<>();
	    	
	    	for(int i=0; i<rows.size(); i++) {
	    		//check column each in row, identification with 'td' tag
	    	    List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
	
	//    	    //column iteration
	//    	    for(int j=0; j<cols.size(); j++) {
	//    	        System.out.println(cols.get(j).getText());
	//    	    }
	    	    
	    	    if(cols != null && cols.size()>0) {
	    	    	jenisKehadiran = cols.get(2).getText();
//	    	    	jenisKehadirans.add(jenisKehadiran); // get Jenis Kehadiran
	    	    	if(jenisKehadiran.equals(key)) {
	    	    		wfhWfoDinas = cols.get(4).getText();
	    	    		break;
	    	    	}
	    	    }
	    	    if(jenisKehadiran.equals(key)) {
	    	    	break;
	    	    }else {
	    	    	jenisKehadiran="";
	    	    }
			}
	    	
	    	if(!jenisKehadiran.isEmpty()) {
	    		System.out.println(nameKey+" "+wfhWfoDinas+" sudah "+key);
	    		// masukkan ke map
	    		if(mapHistory.containsKey(wfhWfoDinas)) {
	    			String personsNameOnMap = mapHistory.get(wfhWfoDinas);
	    			personsNameOnMap = personsNameOnMap+"#"+nameKey+"\n";
	    			mapHistory.put(wfhWfoDinas, personsNameOnMap);
	    		}else {
	    			mapHistory.put(wfhWfoDinas, "#"+nameKey+"\n");
	    		}
	    	}else {
	    		System.out.println(nameKey+" "+wfhWfoDinas+" belum "+key);
	    		if(mapHistory.containsKey("Belum "+key)) {
	    			String personsNameOnMap = mapHistory.get("Belum "+key);
	    			personsNameOnMap = personsNameOnMap+"#"+nameKey+"\n";
	    			mapHistory.put("Belum "+key, personsNameOnMap);
	    		}else {
	    			mapHistory.put("Belum "+key, "#"+nameKey+"\n");
	    		}
	    	}
    	}
    	//driver.quit();
    	List<String> listHistory = new ArrayList<String>(mapHistory.keySet());
    	
    	for (String mapKey : listHistory) {
    		System.out.println(mapKey);
    	}
    }
    
    
    //@Test
    public void wfhHistorySendToWhatsapp(String groupName) throws Exception {
    	pesanWhatsappCompile();
    	
    	driver.get("https://web.whatsapp.com/");
    	Thread.sleep(20000);
    	
    	WebElement search = driver.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]"));
    	search.sendKeys(findByChatByGroupName);
    	
    	Thread.sleep(5000);
    	WebElement tableChat = driver.findElement(By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div"));
    	List<WebElement> rows = tableChat.findElements(By.tagName("div"));
    	
    	WebElement firstResult = rows.get(1);
    	firstResult.click();
    	
    	Thread.sleep(5000);
    	WebElement textMsg = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]"));
    	
    	
    	textMsg.sendKeys(pesanWhatsapp);
    	textMsg.sendKeys(Keys.ENTER);
    	
    	// pesan disclaimer
//    	textMsg.sendKeys(("Mohon untuk tidak melanjutkan/membalas pesan diatas, pesan tsb dibuat *otomatis* dengan aplikasi _selenium remote_ mengacu pada halaman https://wfh.ristekbrin.go.id/dashboard/history "
//    					+ "\n \nPesan otomatis ini masih dalam tahap Uji Coba, harap dimaklumi jika masih ada kesalahan/error"
//    					+ "\n \nPesan otomatis ini bertujuan untuk membantu mempermudah teman-teman dalam merekap absensi "
//    					+ "dan membantu mengingatkan bagi yang belum CI/CO"
//    					+ "\n \nTerimakasih").replace("\n", Keys.chord(Keys.SHIFT, Keys.ENTER)));
//    	textMsg.sendKeys(Keys.ENTER);
//    	
    	Thread.sleep(5000);
    	//driver.quit();
    }
    
    public void pesanWhatsappCompile () {
    	List<String> listHistory = new ArrayList<String>(mapHistory.keySet());
    	
    	// add number
    	for (String mapKey : listHistory) {
    		String persons = mapHistory.get(mapKey);
    		String personsArray[] = persons.split("#");
    		String personsWithNumber = "";
    		int number = 1;
    		for (int i = 1; i < personsArray.length; i++) {
				personsWithNumber = personsWithNumber+number+". "+personsArray[i];
				number++;
			}
    		mapHistory.put(mapKey, personsWithNumber);
    	}
    	
    	for (String mapKey : listHistory) {
    		System.out.println(mapKey);
    		if(!mapKey.equals("Belum "+key)) {
    	    	if(pesanWhatsapp.isEmpty()) {
    	    		// header
    	    		pesanWhatsapp = "Yang *Sudah "+key+"* hari ini, "+dateFormat.format(dateNow)+" melalui halaman https://wfh.ristekbrin.go.id/ \n \n";
    	    	}
    	    	pesanWhatsapp = pesanWhatsapp+"*"+mapKey+"* \n"+mapHistory.get(mapKey)+" \n";
    	    }
    	}
    	
    	for (String mapKey : listHistory) {
	    	if(mapKey.equals("Belum "+key)) {
	    		pesanWhatsapp = pesanWhatsapp+Keys.chord(Keys.ENTER)+"Yang *Belum "+key+"* hari ini, "+dateFormat.format(dateNow)+" melalui halaman https://wfh.ristekbrin.go.id/ \n \n";
	    		pesanWhatsapp = pesanWhatsapp+mapHistory.get(mapKey)+" \n";
	    		pesanWhatsapp = pesanWhatsapp+"_Catatan :_ \nYang *Belum "+key+"* bisa jadi mungkin Ybs sedang izin/cuti";
		    }
    	}
//    	pesanWhatsapp = "Test aja \n test";
    	pesanWhatsapp = pesanWhatsapp.replace("\n", Keys.chord(Keys.SHIFT, Keys.ENTER));
    }

    @Test
    public void initTestWhfIT() throws Exception{
    	driver = getDriver();
    	
    	//String key = "Checkin";
    	
    	// group cpns
//    	findByChatByGroupName = "PAPI 2018";
    	wfhHistory(personsName2);
    	wfhHistorySendToWhatsapp(findByChatByGroupName);
    	
    	// group DI2
//    	findByChatByGroupName = "Inovasi Industri";
    	wfhHistory(personsName2);
    	wfhHistorySendToWhatsapp(findByChatByGroupName);
    	
    	driver.close();
    }
}
