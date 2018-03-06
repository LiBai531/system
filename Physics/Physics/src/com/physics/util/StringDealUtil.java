package com.physics.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class StringDealUtil {
	
	public static String removeBeforeBlank(String str){
		String newStr = str+"a";
	    return newStr.trim().substring(0, newStr.trim().length()-1);
	}
	public static String removeAfterBlank(String str){
		String newStr = "a"+str;
		return newStr.trim().substring(1);
				
	}
	public static String removeAllBlank(String str){
		return str.replaceAll("\\s*", "");
	}
	public static String removeBeforeAndAfterBlank(String str){
		return str.trim();
	}
	
	public static String getSubStrFromLittleBrackets(String str){
		Pattern pattern = Pattern.compile(".*?\\((.*?)\\).*?");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(1);
		}else{
			return null;
		}
	}
	public static String getSubStrFromMidBrackets(String str){
		Pattern pattern = Pattern.compile(".*?\\[(.*?)\\].*?");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(1);
		}else{
			return null;
		}
	}
	public static String getSubStrFromBigBrackets(String str){
		Pattern pattern = Pattern.compile(".*?\\{(.*?)\\}.*?");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return matcher.group(1);
		}else{
			return null;
		}
	}
	
	public static String operWithInteger(String number,int count,String oper){
		if("+".equals(oper)){
			return String.valueOf(((Integer.valueOf(number)+Integer.valueOf(count))));
		}else{
			return String.valueOf(((Integer.valueOf(number)-Integer.valueOf(count))));
		}
	}
	
	public static String convertList2Str(List<String>strs){
		StringBuilder strBuilder = new StringBuilder();
		boolean isFirst = true;
		for(String str : strs){
			if(isFirst){
				strBuilder.append("'");
				strBuilder.append(str);
				strBuilder.append("'");
				isFirst = false;
			}else{
				strBuilder.append(",");
				strBuilder.append("'");
				strBuilder.append(str);
				strBuilder.append("'");
			}
		}
		return strBuilder.toString();
	}
	
	public static String replaceNullStr(String value){
		if(StringUtils.isBlank(value)||
				"NULL".equals(value.toUpperCase())){
			return "";
		}
		return value;
	}
	
	public static String dealEncodeStr(String str){
		try{
	    	if(StringUtils.isNotBlank(str)&&
	    			str.equals(new String(str.getBytes("ISO-8859-1"),
	    					"ISO-8859-1")))
	    	{
	    		str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
	    	}
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
	    	e.printStackTrace();
	 	}
		return str;
	}
}
