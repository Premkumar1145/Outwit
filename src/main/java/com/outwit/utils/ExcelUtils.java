package com.outwit.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private  XSSFSheet ExcelWSheet;

	private  XSSFWorkbook ExcelWBook;

	private  XSSFCell Cell;

	private  XSSFRow Row;

	public  String Path;
	
	

	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheetname as Arguments to this method

	public  void setExcelFile(String SheetName , String xmlPath) {

		try {

			// Open the Excel file
			
			Path =xmlPath;

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			if (ExcelWSheet == null) {
				ExcelWSheet = ExcelWBook.createSheet(SheetName);
			}

		} catch (Exception e) {

			try {
				throw (e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num

	public  String getCellData(int RowNum, int ColNum) {

		try {

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			DataFormatter formatter = new DataFormatter();

			// String CellData = Cell.getStringCellValue();

			String j_username = formatter.formatCellValue(Cell);

			return j_username;

		} catch (Exception e) {

			return "";

		}

	}

	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters

	public  void setCellData(String Result, int RowNum, int ColNum)  {

		try {

			Row = ExcelWSheet.getRow(RowNum);

			if (Row == null) {
				ExcelWSheet.createRow(RowNum);
				Row = ExcelWSheet.getRow(RowNum);
			}
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);

			if (Cell == null) {
				Cell = Row.createCell(ColNum);
			}

			Cell.setCellValue(Result);

			// Constant variables Test Data path and Test Data file name

		} catch (Exception e) {

			throw (e);

		}

		// ExcelWBook = new XSSFWorkbook(new FileInputStream(path));

	}

	public  void close()  {

		try
		{
		//System.out.println("Trying to close excel");
		FileOutputStream fileOut = new FileOutputStream(Path);

		ExcelWBook.write(fileOut);
		//
		fileOut.flush();
		//
		fileOut.close();
		}
		catch(Exception e)
		{
		}

	}
}