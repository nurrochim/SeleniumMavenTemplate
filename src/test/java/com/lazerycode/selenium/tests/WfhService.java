package com.lazerycode.selenium.tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WfhService {
	public WebDriver driver;
	String dir = System.getProperty("user.dir");
	String nameOfProperties = "config.properties";
	String pesanHeader = "_#PesanOtomatis_\n \nYth. \n";
	String pesanWhatsapp = "";
	String findByChatByGroupName = "Test Group Privateku";
	String[] personsName1 = {"Ir. Santosa Yudo Warsono, MT","Aldi Haryadi, S.T.","Drs. Erwin Sjachrial","Ir Wiwiek Joelijani, MT","Novi Mukti Rahayu, S.T., M.T.","Maryunis, S.E."
							,"Rino, S.E","Yenni Kusumawati, S.T.","Aditya Randika, S.E.","Raditya Dananjaya, S.Kom.","Agus Prihartono, S.T., M.T","Cornelia Tantri Wijayaingtyas, S KOM","Amir Faisal Manurung, S.Si., M.P.P","Alwis, SE",
							"Erlani Pusparini, S.T., M.Eng.","Dicky Kurniawan, S.T.","Noor Indriasari, S.E.","Sri Utami, S.Sos","Yulmedianti Karlina Nancy, S.Si","Anteng Setia Ningsih, S.Tp., M.A.",
							"Ulfi Perdanawati, S.T.","Rahmatika Jihad, S.Sos","Ahmad, ST., M.T. Ph.D","Jimmy Akhmadi, ST, MM","Fitri Ramadhani A",
							"Radiwan, SE","Nurochim","Bambang Herlambang, ST","Iskandar, S.Si","Edi Sumedi, A.Md.","Teddy Adhitya, SH",
							"Dody Styawan, S.Kom.","Agung Budi Raharjo, S.E","Febrianto","Aji Siswo Utomo","Syifa Khoiriyah","Karmo","Muhammad Mustakim"};//,"Nila Juwita" 
	
	String[] personsName2 = {"Sarah Fairuz", "Ninik Puji Astuti","Yunida Hary Wardany", "Gracia Krisantiana Agustin","Regina Putri", "Oktarina Elik",
							"Lita Foresti","Mega Fatimah","Shintya Asih Angelita","Uci Sri Sundari",
							"Nurochim", "Aditya Randika", "Raditya Dananjaya", "Dicky Kurniawan","Andi Azhari Putra","Yusnan Rizky", "Moslem Afrizal",
							"Widi Fauzi Asari","Darius Ruruk Paembonan", "Dody Apriadi Indrakusuma", "Hanafi Ahmad Subrata Lubis", "Sefryan Daru"
							};
	String cicoWfhProp = "";
	String cicoWfoProp = "";
	String cicoDinasProp = "";
	String cicoBelumProp = "";
	//String key = "Checkin";
	String key = "Checkout";
	Map<String, String> mapHistory = new HashMap<>();
	Date dateNow = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy", new Locale("id", "ID"));
	Boolean pesanDisclaimer = false;
	Boolean pesanWfhWfoDinas = true;
	Boolean pesanBelumCiCo = true;
	Boolean isClearCiCo = false;
	Boolean sendMsgSucces = false;
	Integer belumCicoProp = 0;
	Integer sudahCicoProp = 0;
	Integer belumCicoCurrent = 0;
	Integer sudahCicoCurrent = 0;
		
    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }

//    @Test
    public void wfhHistory(String[] personsName) throws Exception {
    	WebDriverWait wait = new WebDriverWait(driver, 5);
    	driver.get("https://wfh.ristekbrin.go.id/dashboard/history");
    	//driver.manage().window().maximize();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nama")));
    	
    	String jenisKehadiran = "";
    	String wfhWfoDinas = "";
    	Boolean checkHistory = true;
    	cicoBelumProp = "";
    	for(String nameKey: personsName) {
    		checkHistory = true;
    		jenisKehadiran = "";
	    	wfhWfoDinas = "";
	    	
    		// cek apakah nameKey nya belum CiCo?
    		// jika belum (true) maka lanjutkan cek ke web
    		// jika sudah (false) maka continue --> tak perlu cek ke web, lanjut ke nameKey berikutnya
//    		if(!cicoBelumProp.isEmpty() && !cicoBelumProp.contains(nameKey)) {
//    			continue;
//    		}
    		
    		if(cicoWfoProp.contains(nameKey)) {
    			wfhWfoDinas = "WFO";
    		}
    		if(cicoWfhProp.contains(nameKey)) {
    			wfhWfoDinas = "WFH";
    		}
    		if(cicoDinasProp.contains(nameKey)) {
    			wfhWfoDinas = "DINAS";
    		}
    		if(!wfhWfoDinas.isEmpty()) {
    			jenisKehadiran = key;
    			checkHistory = false;
    		}
    		
    		if(checkHistory) {
    			WebElement findByName = driver.findElement(By.id("nama"));
		    	findByName.sendKeys(nameKey);
		    	
		    	WebElement simpanBtn = driver.findElement(By.id("simpan"));
		    	simpanBtn.click();
		    	
				Thread.sleep(3000);
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("table")));
		    	WebElement tableHistory = driver.findElement(By.id("table"));
		    	List<WebElement> rows = tableHistory.findElements(By.tagName("tr"));
		    	
	//	    	List<String> jenisKehadirans = new ArrayList<>();
		    	
		    	for(int i=0; i<rows.size(); i++) {
		    		//check column each in row, identification with 'td' tag
		    	    List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
		
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
    		
		    	// set value of Properties
	    		if(!wfhWfoDinas.isEmpty()) {
					if(wfhWfoDinas.equalsIgnoreCase("WFO")) {
						cicoWfoProp = cicoWfoProp+"#"+nameKey+"\n";
					}else if(wfhWfoDinas.equalsIgnoreCase("WFH")) {
						cicoWfhProp = cicoWfhProp+"#"+nameKey+"\n";
					}else {
						cicoDinasProp = cicoDinasProp+"#"+nameKey+"\n";
					}
				}else {
					cicoBelumProp = cicoBelumProp+"#"+nameKey+"\n";
				}
	    	
    		}
    		
	    	if(!jenisKehadiran.isEmpty()) {
//	    		System.out.println(nameKey+" "+wfhWfoDinas+" sudah "+key);
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
    }
    
    
    //@Test
    public void wfhHistorySendToWhatsapp(String groupName) throws Exception {
    	int count = 0;
    	int maxTries = 3;
    	while(true) {
    	    try {
    	    	System.out.println(count+" try to remote whatsapp");
    	        remoteWhatsapp();
    	        if(sendMsgSucces) {
    	        	break;
    	        }
    	    } catch (Exception e) {
    	        if (++count == maxTries) throw e;
    	    }
    	}
    	
    }
    
    public void remoteWhatsapp() throws Exception{
    	// open whatsapp
    	driver.get("https://web.whatsapp.com/");
    	Thread.sleep(50000);
    	
    	// find side panel chat
    	WebDriverWait wait = new WebDriverWait(driver, 15);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]")));
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"side\"]/span/div/div/div[2]/div[1]")));
    	
    	// search chat by findByChatByGroupName
    	int count = 0;
    	int maxTries = 3;
    	String title = "";
    	while(true) {
    	    try {
    	    	System.out.println("     "+count+" try search chat ");
    	    	title = "";
    	    	WebElement search = driver.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]"));
    	    	search.clear();
    	    	search.sendKeys(findByChatByGroupName);
    	    	
    	    	Thread.sleep(3000);
    	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div")));
    	    	WebElement tableChat = driver.findElement(By.xpath("//*[@id=\"pane-side\"]/div[1]/div/div"));
    	    	List<WebElement> rows = tableChat.findElements(By.tagName("div"));
    	    	
    	    	WebElement firstResult = rows.get(1);
    	    	firstResult.click();
    	    	
    	    	// Validate Title
    	    	Thread.sleep(5000);
    	    	WebElement titleChatGroup = driver.findElement(By.xpath("//*[@id=\"main\"]/header/div[2]/div[1]/div/span"));
    	    	title = titleChatGroup.getText();
    	    	System.out.println("     ChatGroup = "+ title);
    	    	if(!title.equals(findByChatByGroupName)) {
    	    		throw new Exception();
    	    	}
    	    	
    	    	if(title.equals(findByChatByGroupName)) {
    	    		break;
    	    	}
    	    } catch (Exception e) {
    	        if (++count == maxTries) throw e;
    	    }
    	}
    	
    	if(title.equals(findByChatByGroupName)) {
    		// Input Message
	    	WebElement textMsg = driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]"));
	    	
	    	// pesan disclaimer
	    	if(pesanDisclaimer) {
	    		textMsg.sendKeys(getDisclaimer().replace("\n", Keys.chord(Keys.SHIFT, Keys.ENTER)));
	    		textMsg.sendKeys(Keys.ENTER);
	    	}
	    	
	    	textMsg.sendKeys(pesanWhatsapp);
	    	textMsg.sendKeys(Keys.ENTER);
	    	
	    	
    	}
    	Thread.sleep(5000);
	    sendMsgSucces = true;
    }
    
    public void addNumberToMapHistory() {
    	// add number
    	List<String> listHistory = new ArrayList<String>(mapHistory.keySet());
    	for (String mapKey : listHistory) {
    		String personsOnMap = mapHistory.get(mapKey);
    		
    		String personsArray[] = personsOnMap.split("#");
    		String personsWithNumber = "";
    		int number = 1;
    		for (int i = 1; i < personsArray.length; i++) {
				personsWithNumber = personsWithNumber+number+". "+personsArray[i];
				number++;
			}
    		mapHistory.put(mapKey, personsWithNumber);
    		if(!mapKey.equals("Belum "+key)) {
    			sudahCicoCurrent = sudahCicoCurrent+(number-1);
    		}else {
    			belumCicoCurrent = belumCicoCurrent+(number-1);
    		}
    	}
    	
    	// sampaikan semua data lengkap yang SUDAH CICO untuk terakhir kali 
    	// 20 Sept 2020 --> baris code ini tidak perlu lagi.
//    	if(!listHistory.contains("Belum "+key) && (belumCicoCurrent == 0 && belumCicoProp>0)) {
//    		pesanWfhWfoDinas = true;
//    	}
    }
    
    public void pesanWhatsappCompile () {
    	pesanWhatsapp = "";
    	List<String> listHistory = new ArrayList<String>(mapHistory.keySet());
    	
    	if(pesanWfhWfoDinas) {
	    	for (String mapKey : listHistory) {
//	    		System.out.println(mapKey);
	    		if(!mapKey.equals("Belum "+key)) {
	    	    	if(pesanWhatsapp.isEmpty()) {
	    	    		// header
	    	    		pesanWhatsapp = pesanHeader+"Bapak/Ibu yang *Sudah "+key+"* hari ini, "+dateFormat.format(dateNow)+" melalui halaman https://wfh.ristekbrin.go.id/ \n \n";
	    	    	}
	    	    	pesanWhatsapp = pesanWhatsapp+"*"+mapKey+"* \n"+mapHistory.get(mapKey)+" \n";
	    	    }
	    	}
    	
	    	if(pesanWhatsapp.isEmpty()) {
	    		pesanWhatsapp = pesanHeader+"Bapak/Ibu yang *Sudah "+key+"* hari ini, "+dateFormat.format(dateNow)+" melalui halaman https://wfh.ristekbrin.go.id/ \n \n(Belum ada data)";
	    	}
	    	if(!pesanWhatsapp.isEmpty()) {
	    		pesanWhatsapp = pesanWhatsapp+"Data ter_update_ kunjungi halaman https://wfh.ristekbrin.go.id/dashboard/history \n\nTerimakasih"+Keys.chord(Keys.ENTER);
			}
    	}
    	
    	if(pesanBelumCiCo) {
	    	if(listHistory.contains("Belum "+key)) {
	    		pesanWhatsapp = pesanWhatsapp+pesanHeader+"Bapak/Ibu yang *Belum "+key+"* hari ini, "+dateFormat.format(dateNow)+" melalui halaman https://wfh.ristekbrin.go.id/ \n \n";
	    		pesanWhatsapp = pesanWhatsapp+mapHistory.get("Belum "+key)+" \n";
	//    		pesanWhatsapp = pesanWhatsapp+"_Catatan :_ \nYang *Belum "+key+"* bisa jadi mungkin Ybs sedang izin/cuti \n\n_Mohon jangan dibalas/diteruskan_";
				pesanWhatsapp = pesanWhatsapp+"Data ter_update_ kunjungi halaman di https://wfh.ristekbrin.go.id/dashboard/history \n\nTerimakasih";
			}
	    	
	    	
    	}
    	
    	// overide pesan, jika yg belum CO sudah habis
    	if(!listHistory.contains("Belum "+key)) {
    		isClearCiCo = true;
    		cicoBelumProp ="";
//    		pesanWhatsapp = pesanWhatsapp+getClosingMsg();
    	}
    	
    	pesanWhatsapp = pesanWhatsapp.replace("\n", Keys.chord(Keys.SHIFT, Keys.ENTER));
    }
    
    
    public String getDisclaimer() {
    	String pesanDisclaimer = 	pesanHeader
    								+ "Assalamu'alaikum wr wb\n\n" 
    								+ "Yth. Bapak Ibu teman²\n"
    								+ "Pak Santosa, Ibu Yunis, Pak Aldi, Pak Radiwan dan teman² semua.\n \nRohim mohon izin untuk menggunakan aplikasi _#PesanOtomatis_ "
    								+ "yg saya kembangkan untuk melakukan compile data CI/CO teman² kemudian mengirimkannya ke group ini secara berkala"
    								+ Keys.chord(Keys.ENTER)
    								+ "\n \nHarapannya aplikasi ini dapat membantu mempermudah teman² dalam merekap absensi, "
									+ "sekaligus membantu mengurangi pesan CI/CO yg berulang, "
									+ "sehingga group dapat kembali berfungsi sebagai media komunikasi yg lebih efektif"
									+ Keys.chord(Keys.ENTER)
									+ "\n_#PesanOtomatis_ dibuat dengan software opensource Selenium dg sumber data berasal dari https://wfh.ristekbrin.go.id/dashboard/history"
									+ "\n \n_#PesanOtomatis_ ini masih dalam tahap Uji Coba, harap dimaklumi jika masih ada kesalahan error"
									+ "\n \nSelanjutnya dengan tagar _#PesanOtomatis_ menjadi tanda bahwa yg mengirim pesan adalah service/program komputer *bukan* orang"
									+ "\n \n*Jadwal* _#PesanOtomatis_ :"
									+ "\nCI = 6:15,6:30,7:05,7:20,8:15,9:10"
									+ "\nCO = 15:45,16:10,17:05,18:05,20:05"
									+ "\n \nKritik dan saran yg membangun diperlukan, agar dpt mengembangkan inovasi² baru yg bermanfaat."
									+ "\n \nSemoga bisa menjadi solusi yg membantu." 
									+ "\nSalam hormat dan terimakasih"
									;
    	return pesanDisclaimer;
    }
    
    public String getClosingMsg() {
    	return	"_#PesanOtomatis_\n\nTerimakasih Bapak/Ibu, Alhamdulillah semuanya *Sudah "+key+"* pada hari ini, "+dateFormat.format(dateNow)+" melalui halaman https://wfh.ristekbrin.go.id/"
    			+ "\n\n_Mohon jangan dibalas/diteruskan_";
    }
    
    public void readProperties() {
    	try (InputStream input = new FileInputStream(dir+"/src/test/resources/"+nameOfProperties)) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            sudahCicoProp = Integer.valueOf(prop.getProperty("cico.sudah"));
            belumCicoProp = Integer.valueOf(prop.getProperty("cico.belum"));
//            Integer clearCiCoProp =
            cicoWfhProp = prop.getProperty("cico.wfh");
            cicoWfoProp = prop.getProperty("cico.wfo");
            cicoDinasProp = prop.getProperty("cico.dinas");
            cicoBelumProp = prop.getProperty("cico.belumPersons");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void writeProperties() {
		try (OutputStream output = new FileOutputStream(dir+"/src/test/resources/"+nameOfProperties)) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("cico.sudah", sudahCicoCurrent.toString());
            prop.setProperty("cico.belum", belumCicoCurrent.toString());
            prop.setProperty("cico.clear", "0");
            prop.setProperty("cico.wfh", cicoWfhProp);
            prop.setProperty("cico.wfo", cicoWfoProp);
            prop.setProperty("cico.dinas", cicoDinasProp);
            prop.setProperty("cico.belumPersons", cicoBelumProp);

            // save properties to project root folder
            prop.store(output, null);

		} catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public void setInitProperties(int currentHour, int currentMinute, int currentDay) {
    	// Jam Pagi
    	if (currentHour == 6 && currentMinute < 40) {
    		setInitValueProperties();
    	}
    	
    	// Settingan sore 
    	if(currentDay<6) {
    		// untuk hari sebelum jumat (6)
    		if (currentHour == 15) {
    			// Jumat sore
    			setInitValueProperties();
        	}
    	}else {
    		// untuk hari jumat
    		if (currentHour == 16 && currentMinute < 45) {
    			setInitValueProperties();
        	}
    	}
    }
    
    public void setInitPropertiesCpns(int currentHour) {
    	if (currentHour == 7 || currentHour == 20) {
    		setInitValueProperties();
    	}
    }
    
    public void setInitValueProperties() {
    	sudahCicoProp = 0;
		belumCicoProp = 0;
		cicoWfhProp = "";
		cicoWfoProp = "";
		cicoDinasProp = "";
		cicoBelumProp = "";
		sudahCicoCurrent = 0;
		belumCicoCurrent = 0;
				
		writeProperties();
    }

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getPesanWhatsapp() {
		return pesanWhatsapp;
	}

	public void setPesanWhatsapp(String pesanWhatsapp) {
		this.pesanWhatsapp = pesanWhatsapp;
	}

	public String getFindByChatByGroupName() {
		return findByChatByGroupName;
	}

	public void setFindByChatByGroupName(String findByChatByGroupName) {
		this.findByChatByGroupName = findByChatByGroupName;
	}

	public String[] getPersonsName1() {
		return personsName1;
	}

	public void setPersonsName1(String[] personsName1) {
		this.personsName1 = personsName1;
	}

	public String[] getPersonsName2() {
		return personsName2;
	}

	public void setPersonsName2(String[] personsName2) {
		this.personsName2 = personsName2;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getMapHistory() {
		return mapHistory;
	}

	public void setMapHistory(Map<String, String> mapHistory) {
		this.mapHistory = mapHistory;
	}

	public Boolean getPesanDisclaimer() {
		return pesanDisclaimer;
	}

	public void setPesanDisclaimer(Boolean pesanDisclaimer) {
		this.pesanDisclaimer = pesanDisclaimer;
	}

	public Boolean getPesanWfhWfoDinas() {
		return pesanWfhWfoDinas;
	}

	public void setPesanWfhWfoDinas(Boolean pesanWfhWfoDinas) {
		this.pesanWfhWfoDinas = pesanWfhWfoDinas;
	}

	public Boolean getIsClearCiCo() {
		return isClearCiCo;
	}

	public void setIsClearCiCo(Boolean isClearCiCo) {
		this.isClearCiCo = isClearCiCo;
	}

	public Boolean getPesanBelumCiCo() {
		return pesanBelumCiCo;
	}

	public void setPesanBelumCiCo(Boolean pesanBelumCiCo) {
		this.pesanBelumCiCo = pesanBelumCiCo;
	}

	public Integer getBelumCicoProp() {
		return belumCicoProp;
	}

	public void setBelumCicoProp(Integer belumCicoProp) {
		this.belumCicoProp = belumCicoProp;
	}

	public Integer getSudahCicoProp() {
		return sudahCicoProp;
	}

	public void setSudahCicoProp(Integer sudahCicoProp) {
		this.sudahCicoProp = sudahCicoProp;
	}

	public Integer getBelumCicoCurrent() {
		return belumCicoCurrent;
	}

	public void setBelumCicoCurrent(Integer belumCicoCurrent) {
		this.belumCicoCurrent = belumCicoCurrent;
	}

	public Integer getSudahCicoCurrent() {
		return sudahCicoCurrent;
	}

	public void setSudahCicoCurrent(Integer sudahCicoCurrent) {
		this.sudahCicoCurrent = sudahCicoCurrent;
	}

	public String getNameOfProperties() {
		return nameOfProperties;
	}

	public void setNameOfProperties(String nameOfProperties) {
		this.nameOfProperties = nameOfProperties;
	}

	

	
    
    
}
