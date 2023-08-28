package com.outwit.testexecution;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;
import java.io.*;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class FileOperations {

	public static String readXMLFile(String filePath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		return IOUtils.toString(fileInputStream, "UTF-8");
	}

	public static BufferedWriter writeXML(String filePath) throws IOException {
		File file = new File(filePath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		return writer;

	}
	
	public static void csvfileWriter(String csvFilePath) throws IOException {
		
		String OrderHeaderKey = FileOperations.XMLXpathReader("./XML_files/getOrderDetailsResponse.xml",
                "//Order/@OrderHeaderKey");
       String OrderNo = FileOperations.XMLXpathReader("./XML_files/getOrderDetailsResponse.xml", "//Order/@OrderNo");

		String dataToWrite = OrderNo;

		//String dataToWrite = OrderNo +","+OrderHeaderKey;
		String columnNames = "OrderNo,OrderHeaderKey";

		// Read the existing data from the CSV file
		File csvFile = new File(csvFilePath);
		BufferedReader reader = new BufferedReader(new FileReader(csvFile));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
		    if (csvFile.exists()) {
		        if (line.startsWith(columnNames)) {
		            builder.append(line);
		        }
		   }
		    }
		    reader.close();

		BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile,true));
		writer.append("\n");
		writer.write(dataToWrite);
		writer.close();
		
	}

	public static String XMLXpathReader(String filepath, String Xpath) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new FileInputStream(new File(filepath)));// same
			// xml
			// comments
			// as
			// above.
			// System.out.println(document);
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			Object userElement = xpath.evaluate(Xpath, document, XPathConstants.NODE);
			String value = userElement.toString();
			String[] arrOfStr = value.split("=");
			String c = null;
			for (String a : arrOfStr) {
				if (a.startsWith("\"")) {
					String z = a;
					c = z.replaceAll("^\"|\"$", "");
					if (c.contains(".")) {
						int index = c.indexOf(".");
						c = c.substring(0, index);
					}
				}
			}
			return c;

		} catch (Exception e) {

		}
		return null;

	}

	
	
	    public static void ExcelDataReader() {
	        String excelFilePath = "D:\\automation_testing.xlsx";

	        try (FileInputStream fis = new FileInputStream(excelFilePath);
	             Workbook workbook = new XSSFWorkbook(fis)) {

	            Sheet sheet = workbook.getSheetAt(0);

	            for (Row row : sheet) {
	                Cell orderCell = row.getCell(0);
	                Cell enterpriseCodeCell = row.getCell(1);
	                Cell totalNumberOfLinesCell = row.getCell(2);
	                Cell itemIdCell = row.getCell(3);
	                Cell itemQtyCell = row.getCell(4);

	                String order = orderCell.getStringCellValue();
	                String enterpriseCode = enterpriseCodeCell.getStringCellValue();
	                String totalNumberOfLines = totalNumberOfLinesCell != null ? totalNumberOfLinesCell.getStringCellValue() : "";
	                String itemId = itemIdCell.getStringCellValue();
	                double itemQty = itemQtyCell.getNumericCellValue();

	                System.out.println("Order: " + order);
	                System.out.println("EnterpriseCode: " + enterpriseCode);
	                System.out.println("TotalNumberOfLines: " + totalNumberOfLines);
	                System.out.println("ItemId: " + itemId);
	                System.out.println("ItemQty: " + itemQty);
	                System.out.println();
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


