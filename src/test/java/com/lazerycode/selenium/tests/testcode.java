package com.lazerycode.selenium.tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class testcode {
	public static void main(String[] args) {
//        try {
//        	Map<String, String> mapHistory = new HashMap<>();
//        	mapHistory.put("sa", "kjhkas");
//        	
//        	System.out.println(mapHistory.get("sas"));
//        	if(mapHistory.get("sas") == null) {
//        		System.out.println("is null");
//        	}
//        	
//        	List<String> listHistory = new ArrayList<String>(mapHistory.keySet());
//        	if(!listHistory.contains("Belum ")) {
//        		System.out.println("clear");
//        	}
        	
//        	Calendar cal = Calendar.getInstance();
//        	int currentHour = 16;
//        	int currentMinute = cal.get(Calendar.MINUTE);
//        	System.out.println(currentMinute);
//        	
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        	String dateNow = sdf.format(cal.getTime()); 
//        	if (currentHour == 20 && dateNow.equals("11/09/2020")) {
//        		System.out.println("Masuk disclaimer");
//        	}else {
//        		System.out.println("Tidak Masuk disclaimer");
//        	}
        	
//        	if (currentHour >= 20 || (currentHour > 8 && currentHour < 12)) {
//        		System.out.println("yg belum CO saja yah");
//        	}else {
//        		System.out.println("semua cico yah");
//        	}
        	
        	
//        }
//        catch(Exception e){ 
//            e.printStackTrace();
//        }
//        	try (
//        		InputStream input =  new FileInputStream("path/to/config.properties")) {
//                Properties prop = new Properties();
//
//                // load a properties file
//                prop.load(input);
//
//                // get the property value and print it out
//                System.out.println(prop.getProperty("cico.belum"));
//                System.out.println(prop.getProperty("cico.sudah"));
//                System.out.println(prop.getProperty("cico.clear"));
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
		
//		try (InputStream input = testcode.class.getClassLoader().getResourceAsStream("config.properties")) {
//
//            Properties prop = new Properties();
//
//            if (input == null) {
//                System.out.println("Sorry, unable to find config.properties");
//                return;
//            }
//
//            //load a properties file from class path, inside static method
//            prop.load(input);
//
//            //get the property value and print it out
//            System.out.println(prop.getProperty("cico.belum"));
//	        System.out.println(prop.getProperty("cico.sudah"));
//	        System.out.println(prop.getProperty("cico.clear"));
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

//		String dir = System.getProperty("user.dir");
//		System.out.println(dir);
//		try (OutputStream output = new FileOutputStream(dir+"/src/test/resources/config.properties")) {
//
//            Properties prop = new Properties();
//
//            // set the properties value
//            prop.setProperty("cico.sudah", "10");
//            prop.setProperty("cico.belum", "30");
//            prop.setProperty("cico.clear", "0");
//
//            // save properties to project root folder
//            prop.store(output, null);
//
//            System.out.println(prop);
//
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
		
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse("18/09/2020"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int currentHour = cal.get(Calendar.DAY_OF_WEEK);
//    	int currentMinute = cal.get(Calendar.MINUTE);
    	System.out.println(currentHour);
	}
}
