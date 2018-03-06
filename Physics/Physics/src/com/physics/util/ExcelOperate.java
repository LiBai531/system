package com.physics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelOperate {

	public static void readExcel(String filePath) throws Exception{
		InputStream in = new FileInputStream(filePath);
		Workbook wb = Workbook.getWorkbook(in);
		Sheet sheet = wb.getSheet(0);
		int sheetColumns = sheet.getColumns();
		int sheetRows = sheet.getRows();
		for(int i = 0;i < sheetRows;i++){
			for(int j = 0;j < sheetColumns;j++){
				Cell cell = sheet.getCell(j, i);
				System.out.print(cell.getContents()+"  ");
			}
			System.out.println();
		}
		
	}
	
	
	public static void readMultipleExcel(Set<File> files) throws BiffException, IOException{
		Iterator<File> it = files.iterator();
		while(it.hasNext()){
			File file = (File) it.next();
			InputStream in = new FileInputStream(file);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			Workbook wb = Workbook.getWorkbook(in);
			Sheet sheet = wb.getSheet(0);
			int sheetColumns = sheet.getColumns();
			int sheetRows = sheet.getRows();
			for(int i=1;i<sheetRows;i++){
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				for(int j=0;j<sheetColumns;j++){
					Cell cell = sheet.getCell(j, i);
					Cell keyCell = sheet.getCell(j,0);
					map.put(keyCell.getContents(), cell.getContents());
				}
				list.add(map);
			}
		}
		
		return;
	}
	
	public static List<Map<String,String>> readExcelToMap(File file) throws BiffException, IOException{
			InputStream in = new FileInputStream(file);
			List<Map<String,String>> list = new LinkedList<Map<String,String>>();
			
			Workbook wb = Workbook.getWorkbook(in);
			Sheet sheet = wb.getSheet(0);
			int sheetColumns = sheet.getColumns();
			int sheetRows = sheet.getRows();
			for(int i=1;i<sheetRows;i++){
				
				Map<String,String> map = new HashMap<String,String>();
				
				for(int j=0;j<sheetColumns;j++){
					Cell cell = sheet.getCell(j, i);
					Cell keyCell = sheet.getCell(j,0);
					map.put(keyCell.getContents(), cell.getContents());
				}
				list.add(map);
			}
		
			return list;
	}
}
