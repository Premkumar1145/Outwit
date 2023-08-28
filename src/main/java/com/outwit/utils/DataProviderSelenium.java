package com.outwit.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.outwit.base.BasePageActions;
import com.aventstack.extentreports.Status;

public class DataProviderSelenium extends BasePageActions {

	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;

	// public static String Path = "E:\\AutomationWMSTestData\\TestData.xlsx";

	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheetname as Arguments to this method

	public static void setExcelFile(String Path, String SheetName) throws Exception {

		try {

			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e) {

			throw (e);

		}

	}

	public static Object[][] getTableArray(String FilePath, String SheetName, int iTestCaseRow) throws Exception

	{

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startCol = 2;

			int ci = 0, cj = 0;

			int totalRows = 1;

			int totalCols = 4;

			int actualColm = totalCols - 2;

			tabArray = new String[iTestCaseRow][actualColm];

			for (int k = 0; k < iTestCaseRow; k++) {

				int parametersCount = 0;

				for (int j = startCol; j < totalCols; j++, parametersCount++)

				{

					logger.info("J value is : " + j);
					logger.info("K value is : " + k);
					logger.info("parametersCount value is : " + parametersCount);

					tabArray[k][parametersCount] = getCellData(k + 1, j);

					logger.info(tabArray[k][parametersCount]);

				}

			}

		}

		catch (FileNotFoundException e)

		{

			logger.info("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e)

		{

			logger.info("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return (tabArray);

	}

	public static Object[][] getTableArray1(String FilePath, String SheetName, int iTestCaseRow, int startRow,
			int iTestCaseColumn) throws Exception

	{

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startCol = 1;

			int ci = 0, cj = 0;

			int totalRows = 1;

			int totalCols = iTestCaseColumn;

			int actualColm = totalCols - 1;

			tabArray = new String[iTestCaseRow][actualColm];

			for (int k = 0; k < iTestCaseRow; k++, startRow++) {

				int parametersCount = 0;

				for (int j = startCol; j < totalCols; j++, parametersCount++)

				{

					logger.info("J value is : " + j);
					logger.info("K value is : " + k);
					logger.info("parametersCount value is : " + parametersCount);
					String value = getCellData(startRow, j);
					logger.info(value);

					tabArray[k][parametersCount] = value;

					// logger.info(tabArray[k][parametersCount]);

				}

			}

		}

		catch (FileNotFoundException e)

		{

			logger.info("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e)

		{

			logger.info("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return (tabArray);

	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num

	public static String getCellData(int RowNum, int ColNum) throws Exception {

		try {

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (Cell == null) {
				return null;
			}
			String CellData = null;
			switch (Cell.getCellType()) {
			case XSSFCell.CELL_TYPE_BLANK:
				return CellData = null;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				return CellData = String.valueOf(Cell.getBooleanCellValue());

			case XSSFCell.CELL_TYPE_NUMERIC:

				int val = (int) Cell.getNumericCellValue();
				return CellData = String.valueOf((int) Cell.getNumericCellValue());

			case XSSFCell.CELL_TYPE_STRING:
				return CellData = Cell.getRichStringCellValue().toString();

			}

			return CellData;

		} catch (Exception e) {

			String value = exceptionsToString(e);
			testInfo.log(Status.INFO, "Not able to fetch values from ExcelSheet " + "Row Number :" + RowNum
					+ "Column Number : " + ColNum + "Error : " + value);

			return exceptionsToString(e);

		}

	}

	public static String getTestCaseName(String sTestCase) throws Exception {

		String value = sTestCase;

		try {

			int posi = value.indexOf("@");

			value = value.substring(0, posi);

			posi = value.lastIndexOf(".");

			value = value.substring(posi + 1);

			return value;

		} catch (Exception e) {

			throw (e);

		}

	}

	public static int getRowCountFromTestData(int colNum) throws Exception {

		int i;

		try {

			int rowCount = DataProviderSelenium.getRowUsed();

			int intTotalRun = 0;

			int x = 0;
			for (i = 1; i <= rowCount; i++) {
				String testcasename = DataProviderSelenium.getCellData(i, colNum);
				if (testcasename != null) {
					intTotalRun++;
					// testCaseName.add(testcasename);
					//logger.info("Row which has value " + i);
					testCaseRow.add(i);
					String executeTestCase = DataProviderSelenium.getCellData(i, 1);
					testCaseExecutionValue.add(executeTestCase);
				}

			}

			return intTotalRun;

		} catch (Exception e) {

			throw (e);

		}

	}

	public static int getRowContains(String sTestCaseName, int colNum) throws Exception {

		int i;

		try {

			int rowCount = DataProviderSelenium.getRowUsed();

			int intTotalRun = 0;

			int x = 0;
			for (i = 0; i <= rowCount; i++) {

				if (DataProviderSelenium.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {

					if (x == 0) {
						startRow = i;
					}
					x++;
					intTotalRun++;
					logger.info("Row which has value " + i);
					testCaseRow.add(i);

				}

			}

			return intTotalRun;

		} catch (Exception e) {

			throw (e);

		}

	}

	public static int getColumnContains(String sTestCaseName, int colNum) throws Exception {

		int i;

		try {

			int rowCount = DataProviderSelenium.getRowUsed();

			for (i = 0; i < rowCount; i++) {

				if (DataProviderSelenium.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {

					break;

				}

			}

			return i;

		} catch (Exception e) {

			throw (e);

		}

	}

	public static int getRowUsed() throws Exception {

		try {

			int RowCount = ExcelWSheet.getLastRowNum();

			return RowCount;

		} catch (Exception e) {

			logger.info(e.getMessage());

			throw (e);

		}

	}

	public static int getColUsed(int startRow) throws Exception {

		try {

			int noOfColumns = ExcelWSheet.getRow(startRow).getPhysicalNumberOfCells();

			logger.info("noOfColumns is " + noOfColumns);

			int noOfColumns1 = ExcelWSheet.getRow(startRow).getLastCellNum();

			logger.info("noOfColumns1 is " + noOfColumns1);
			return noOfColumns;

		} catch (Exception e) {

			logger.info(e.getMessage());

			throw (e);

		}

	}

	public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {

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

	public static void setCellDataColour(int RowNum, int ColNum) throws Exception {

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

			CellStyle style = ExcelWBook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Cell.setCellStyle(style);

			// Constant variables Test Data path and Test Data file name

		} catch (Exception e) {

			throw (e);

		}

		// ExcelWBook = new XSSFWorkbook(new FileInputStream(path));

	}

	public static void close() {

		try {
			logger.info("Trying to close excel");
			FileOutputStream fileOut = new FileOutputStream(Path);

			ExcelWBook.write(fileOut);
			//
			fileOut.flush();
			//
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}