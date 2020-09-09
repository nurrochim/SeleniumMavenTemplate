package com.lazerycode.selenium.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testcode {
	public static void main(String[] args) {
        try {
        	Map<String, String> mapHistory = new HashMap<>();
        	mapHistory.put("sa", "kjhkas");
        	
        	System.out.println(mapHistory.get("sas"));
        	if(mapHistory.get("sas") == null) {
        		System.out.println("is null");
        	}
        	
        	List<String> listHistory = new ArrayList<String>(mapHistory.keySet());
        	if(!listHistory.contains("Belum ")) {
        		System.out.println("clear");
        	}
        	
        	
        }
        catch(Exception e){ 
            e.printStackTrace();
        }
	}
}
