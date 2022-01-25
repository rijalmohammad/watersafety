package it.univaq.disim.se4iot.watersafety.mqtt;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
  
public class Helper {
	public List<Map<String, String>> CsvToJson(String filePath) throws Exception {
	      try {
	  	    File input = new File(filePath);
	         CsvSchema csv = CsvSchema.emptySchema().withHeader();
	         CsvMapper csvMapper = new CsvMapper();
	         MappingIterator<Map<String, String>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
	         List<Map<String, String>> list = mappingIterator.readAll();
	       
	         return list;
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
		return null;
	}
	
	
	public String getLocation(String input) {
        Map<String, String> locationMap = new HashMap<String, String>();
        
        locationMap.put("manggarai-floodgate", "Manggarai Floodgate");
        locationMap.put("ciliwung-river", "Ciliwung River");
        locationMap.put("sunter-lake", "Sunter Lake");
        
        return locationMap.get(input);
	}
	
	public String getParameterData(String input) {
		Map<String, String> dataMap = new HashMap<String, String>();
		
		dataMap.put("condition", "./data/condition_data.csv");	
		dataMap.put("ph", "./data/ph_data.csv");
		dataMap.put("solids", "./data/solids_data.csv");
		dataMap.put("hardness", "./data/hardness_data.csv");
		dataMap.put("chloramines", "./data/chloramines_data.csv");
		dataMap.put("conductivity", "./data/conductivity_data.csv");
		dataMap.put("organic_carbon", "./data/organic_carbon_data.csv");
		dataMap.put("trihalomethanes", "./data/trihalomethanes_data.csv");
		dataMap.put("turbidity", "./data/turbidity_data.csv");

		
		return dataMap.get(input);
	}
}