package com.lazerycode.selenium.tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testcode {
	public static void main(String[] args) {
        try {
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
        	
        	Calendar cal = Calendar.getInstance();
        	int currentHour = 16;
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	String dateNow = sdf.format(cal.getTime()); 
//        	if (currentHour == 20 && dateNow.equals("11/09/2020")) {
//        		System.out.println("Masuk disclaimer");
//        	}else {
//        		System.out.println("Tidak Masuk disclaimer");
//        	}
        	
        	if (currentHour >= 20 || (currentHour > 8 && currentHour < 12)) {
        		System.out.println("yg belum CO saja yah");
        	}else {
        		System.out.println("semua cico yah");
        	}
        }
        catch(Exception e){ 
            e.printStackTrace();
        }
	}
}
