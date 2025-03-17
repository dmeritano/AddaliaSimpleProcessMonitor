package com.addalia.simplemonitor.helpers;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static void sleep(int miliseconds) {
		
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getJsonString(Object obj) {
		String jsString;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			jsString = null;
		}
		return jsString;
	}
	
	public static Object jsonToClass(String json, Class<?> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Object obj = clazz.getDeclaredConstructor().newInstance();
			obj = mapper.readValue(json, clazz);
			return obj;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}	
	
	public static String padStringRight(String s, int nCantCarac) {
		String formato = "%-" + nCantCarac + "s";
		String resp = String.format(formato, s);
		return resp;
	}

	public static String padStringRight(String s, String formato) {
		String resp = String.format(formato, s);
		return resp;
	}

	public static String padStringLeft(String s, int nCantCarac) {
		String formato = "%" + nCantCarac + "s";
		String resp = String.format(formato, s);
		return resp;
	}

	public static String padStringLeft(String s, String formato) {
		String resp = String.format(formato, s);
		return resp;
	}

	public static String padNumberLeft(Long num, int nCantCarac) {
		String formato = "%0" + nCantCarac + "d";
		String resp = String.format(formato, num);
		return resp;
	}

	public static String padNumberLeft(Long num, String formato) {
		String resp = String.format(formato, num);
		return resp;
	}

	public static boolean fileExist(String file) {
		boolean res = false;
		try {
			if (file != null && !file.isEmpty()) {
				File f = new File(file);
				return f.exists();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage() + " checking if exist file " + file);
		}
		return res;
	}	
	
}
