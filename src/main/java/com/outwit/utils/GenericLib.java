package com.outwit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.interactions.internal.BaseAction;


import com.outwit.base.BasePageActions;


/*import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;*/

public class GenericLib extends BasePageActions {

	public static String sFile;
	public static int iPassCount = 0;
	public static int iFailCount = 0;
	public static int iSkippedCount = 0;
	static public String sDirPath = System.getProperty("user.dir");
	public static String sConfigFile = sDirPath+"/prod";
//	public static String sKirwaFile = "C:/Users/kantharaju/AndroidAppExecutionReports/propertyfiles" + "/KIRWA.properties";
	//public static Pattern imagePath = null;
	//public static Screen screen = null;
	public static String path = sDirPath + "/PushNotificationImages/";
	public static int sStatusCnt = 0;
	//public static Pattern srcImage = null;
	//public static Pattern TragetImage = null;



	 
	public static String getCongigValue(String sFile, String sKey) {
		Properties prop = new Properties();
		String sValue = null;
		try {
			InputStream input = new FileInputStream(sFile+".properties");
			prop.load(input);
			sValue = prop.getProperty(sKey);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sValue;
	}

	

	 
	public static String  getValueFromConfigProperty(String properyfileName ,String sKey)  {
		
		InputStream input = null;
		try {
		//	input = new FileInputStream(properyfileName + ".properties");
			
			input = new FileInputStream("prod.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// load a properties file
		
		
		String sValue = null;
		try {
			prop.load(input);
			sValue = prop.getProperty(sKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sValue;
	
	}
	
	
	

	
	public static void setCongigValue(String sFile, String sKey, String sValue) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(sFile+".properties"));
			prop.load(fis);
			fis.close();

			FileOutputStream fos = new FileOutputStream(new File(sFile+".properties"));
			prop.setProperty(sKey, sValue);
			prop.store(fos, "Updating folder path");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	 
	public static String[] toReadExcelData(String sSheet, String sTestCaseID) {
		String sData[] = null;
		try {

			FileInputStream fis = new FileInputStream(BasePageActions.sTestDataFile);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			int iRowNum = sht.getLastRowNum();
			for (int i = 1; i <= iRowNum; i++) {
				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getPhysicalNumberOfCells();
					sData = new String[iCellNum];
					for (int j = 0; j < iCellNum; j++) {
						sData[j] = sht.getRow(i).getCell(j).getStringCellValue();
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sData;
	}

	
	
	
	public static void setStatus(String sName, String sResult, ArrayList sTestName, ArrayList sStatus) {
		sName = sName.replace("test", "");
		sTestName.add(sName);
		sStatus.add(sResult);

		if (sResult.equals("Passed")) {
			iPassCount = iPassCount + 1;
		} else if (sResult.equals("Failed")) {
			iFailCount = iFailCount + 1;
		} else {
			iSkippedCount = iSkippedCount + 1;
		}
	}


	 

	
	
	

	 

	public static void setCellData(String Result,String sSheet ,String  RowNum, int ColNum) throws Exception	{
		 
	       try{
	    	   FileInputStream fis = new FileInputStream(BasePageActions.sTestDataFile);
				Workbook wb = (Workbook) WorkbookFactory.create(fis);
				Sheet sht = wb.getSheet(sSheet);
				System.out.println("----------Sheet " + sSheet);
				Row rowNum=sht.getRow(Integer.parseInt(RowNum));
				System.out.println("----------RowNum " + RowNum);
				System.out.println("-----------ColNum " + ColNum);
				Cell cell=rowNum.getCell(ColNum);
				
				if (cell == null) {
					cell = rowNum.createCell(ColNum);
					cell.setCellValue(Result);
					}
				
				else {
					cell.setCellValue(Result);
				}
				FileOutputStream fileOut = new FileOutputStream(BasePageActions.sTestDataFile);
				
				wb.write(fileOut);
				fileOut.flush();

				fileOut.close();

				}catch(Exception e){

				throw (e);

				}
	}
}
