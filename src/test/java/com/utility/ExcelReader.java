package com.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

	public String data;
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;
	
	
	// excel file location 
	public ExcelReader(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// returns the row count in a sheet
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum()+1;
			return number;
		}

	}

	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i< row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {

					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in" + sheetName + ".xls";
		}
	}
/////// returns the data according to the keyValue
	public String getkeyData(String sheetName, String keyColumn, String valueColumn, String keyToGetValue) {

		try {
			if (!isSheetExist(sheetName))
				return "";
			int index = workbook.getSheetIndex(sheetName);
			int Valuecol_Num = -1;
			int Keycol_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			int lastCellNum = row.getLastCellNum();
			
				for (int i = 0; i < lastCellNum; i++) {
					String cellValue = row.getCell(i).getStringCellValue().trim();
						if (cellValue.equalsIgnoreCase(keyColumn)) {
							Keycol_Num = i;
							break;
						} 

					}
				for (int i = 0; i <lastCellNum; i++) {
					String cellValue = row.getCell(i).getStringCellValue().trim();
						if (cellValue.equalsIgnoreCase(valueColumn)) {
							Valuecol_Num = i;
							break;
						} 
					}
			
			int number = sheet.getLastRowNum();
			for (int i = 1; i <= (number); i++) {
				String text = (sheet.getRow(i).getCell(Keycol_Num).getStringCellValue().trim());
				if (text.equalsIgnoreCase(keyToGetValue)) {
					cell=sheet.getRow(i).getCell(Valuecol_Num);
					if (cell.getCellType() == Cell.CELL_TYPE_STRING)
						data= cell.getStringCellValue();
					else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
						data= String.valueOf(cell.getNumericCellValue());
					
					} 
					else{
						data= " ";
					}
				break;
				}
			}
		
			return data;

		} catch (NullPointerException e) {

			e.printStackTrace();
			return "";
		}
	}

	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}
}

