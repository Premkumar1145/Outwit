package com.outwit.testexecution;

import java.time.LocalDateTime;
import java.util.Random;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.outwit.base.BasePageActions;
import com.outwit.base.InventoryAsPerSourcingRule;
import com.outwit.reports.Utility;

import com.outwit.utils.DataProviderSelenium;
import com.outwit.utils.GenricMethods;
import com.outwit.utils.MailProjectClass;
import com.outwit.utils.TestUtils;

public class ABFRLHappyFlowCopy extends BasePageActions {

	
	static String  DropSAPOrderUpdatesSyncServiceCall=null;
	static String OrderType=null;
	static String CreateOrder = null;
	@Test
	public void CreateOrder() throws Exception {
		//String  DropSAPOrderUpdatesSyncServiceCall=null;
	
		destPath = absolutePath + "\\TestData\\OutwitData.xlsx";
		
		Sheetname = "TestDataSheet";
		Path = absolutePath + "\\TestData\\OutwitData.xlsx";

		DataProviderSelenium.setExcelFile(Path, Sheetname);

		System.out.println("Sheet Name is " + Sheetname);
 		sTestCaseName = this.toString();

		sTestCaseName = DataProviderSelenium.getTestCaseName(this.toString());
		int ColumnValues = DataProviderSelenium.getColUsed(0);
		int z = 0;
		while (ColumnValues != 0) {
			String ColName = DataProviderSelenium.getCellData(0, z++);
			ColumnNames.add(ColName);
			ColumnValues--;
		}

		iTestCaseRow = DataProviderSelenium.getRowCountFromTestData(0);

  		DataProviderSelenium.setExcelFile(Path, Sheetname);

		LocalDateTime myDateObj = LocalDateTime.now();
		String OrderDate = myDateObj + "+04:00";

		Random objGenerator = new Random();

		String SalesDeliveryDateAndRequestDeliveryDate = OrderDate.substring(0, 16);

		for (int k = 0; k < iTestCaseRow; k++) {
			
			//String orderNo = "Automation" + randomNumber;
			String orderNo = GenricMethods.generateDynamicOrderNumber();
			String executeTC = testCaseExecutionValue.get(k);

			try {
 				if (executeTC.equals("Y")) {

					int itemRow = testCaseRow.get(k);
					String CustLoc = DataProviderSelenium.getCellData(itemRow, 3);
					logger.info(CustLoc);
					String totallines = DataProviderSelenium.getCellData(itemRow, 6);
					int toaltnooflines = Integer.parseInt(totallines);
					 OrderType = DataProviderSelenium.getCellData(itemRow, 0);

					testInfo = extent.createTest(OrderType);

					String CreateOrderXML = null;

					for (int j = 0; j < toaltnooflines; j++) {
						CreateOrderXML = absolutePath + "\\TestData\\Xml\\OrderCreation\\" + toaltnooflines
								+ "\\CreateOrder.xml";

						TestUtils.editXmlFile("Order", 1, "OrderNo", orderNo, CreateOrderXML);
						//TestUtils.editXmlFile("Order", 1, "OrderDate", OrderDate, CreateOrderXML);
						
						if(OrderType.contains("error")) {
						TestUtils.editXmlFile("Order", 1, "EnterpriseCode", CustLoc, CreateOrderXML);
						}
						else {
							TestUtils.editXmlFile("Order", 1, "EnterpriseCode", CustLoc, CreateOrderXML);
						}
						int h = j + 1;

						String itemid = DataProviderSelenium.getCellData(itemRow + j, 7);
						logger.info("Item id is " + itemid);
						TestUtils.editXmlFile("Item", h, "ItemID", itemid, CreateOrderXML);
						String itemqty = DataProviderSelenium.getCellData(itemRow + j, 8);
						logger.info("Item qty is " + itemqty);
						TestUtils.editXmlFile("OrderLine", h, "OrderedQty", itemqty, CreateOrderXML);

						String PrimeLineNo = String.valueOf(h);
						TestUtils.editXmlFile("OrderLine", h, "PrimeLineNo", PrimeLineNo, CreateOrderXML);
						TestUtils.editXmlFile("OrderLine", h, "ReqDeliveryDate",
								SalesDeliveryDateAndRequestDeliveryDate, CreateOrderXML);
						//TestUtils.editXmlFile("Extn", h + 1, "OrderItemId", orderNo + "_" + h, CreateOrderXML);

					}

					
					try {
						CreateOrder = OMSAPICall(baseURI, userName, passWord, "createOrder", CreateOrderXML,
								"createOrder", "N", "");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String CreateOrderXmlResponse = absolutePath
							+ "\\TestData\\QAData\\ABFRLData\\ResponseXmls\\CreateOrderResponse.xml";
					TestUtils.SaveResponseAsXML(CreateOrderXmlResponse, CreateOrder);

					Thread.sleep(5000);

					String OrderNo = orderNo;
					DataProviderSelenium.setCellData(OrderNo, itemRow, 2);
					testInfo.log(com.aventstack.extentreports.Status.PASS, "Order Number is : " + OrderNo);

					if (OrderNo.equals("No Order")) {
						testInfo.log(com.aventstack.extentreports.Status.SKIP, "Hybris Didn't create Order");

					} else if (InventoryAsPerSourcingRule
							.verifyOrderInOMS(OrderNo,
									absolutePath + "\\TestData\\QAData\\ABFRLData\\InputXmls\\getOrderDetails.xml",
									absolutePath
											+ "\\TestData\\QAData\\ABFRLData\\ResponseXmls\\getOrderDetailsResponse.xml")
							.equals("Errors")) {
						//testInfo.log(com.aventstack.extentreports.Status.FAIL, "Order has not reached to OMS");
					} else {
						testInfo.log(com.aventstack.extentreports.Status.PASS, "Order Number is : " + OrderNo);

						String getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
						testInfo.log(com.aventstack.extentreports.Status.INFO,
								"Order Status before starting Validations :" + getOrderStatus);

						InventoryAsPerSourcingRule.scheduleOrder(OrderNo,
								absolutePath + "\\TestData\\QAData\\ABFRLData\\InputXmls\\scheduleOrder.xml",
								absolutePath + "\\TestData\\QAData\\ABFRLData\\InputXmls\\getOrderDetails.xml",
								absolutePath
										+ "\\TestData\\QAData\\ABFRLData\\InputXmls\\getOrderDetailsOutputTemplate.xml",
								absolutePath
										+ "\\TestData\\QAData\\ABFRLData\\ResponseXmls\\getOrderDetailsResponse.xml"

						);

						InventoryAsPerSourcingRule.releaseOrder(OrderNo,
								absolutePath + "\\TestData\\QAData\\ABFRLData\\InputXmls\\releaseOrder.xml",
								absolutePath + "\\TestData\\QAData\\ABFRLData\\InputXmls\\getOrderDetails.xml",
								absolutePath
										+ "\\TestData\\QAData\\ABFRLData\\InputXmls\\getOrderDetailsOutputTemplate.xml",
								absolutePath
										+ "\\TestData\\QAData\\ABFRLData\\ResponseXmls\\getOrderDetailsResponse.xml"

						);

						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
						testInfo.log(com.aventstack.extentreports.Status.INFO, "Order Status is in :" + getOrderStatus);

						String getCompleteOrderDetailsInputXml = absolutePath
								+ "\\TestData\\QAData\\ABFRLData\\InputXmls\\getCompleteOrderDetails.xml";
						String getCompleteOrderDetailsOutputTemplate = absolutePath
								+ "\\TestData\\QAData\\ABFRLData\\InputXmls\\getCompleteOrderDetailsOutputTemplate.xml";
						String getCompleteOrderDetailsResponse = absolutePath
								+ "\\TestData\\QAData\\ABFRLData\\ResponseXmls\\getCompleteOrderDetailsResponse.xml";

						TestUtils.editXmlFile("Order", 1, "OrderNo", OrderNo, getCompleteOrderDetailsInputXml);

						String getCompleteOrderDetailsAPICall = OMSAPICallwithOutputTemplate(baseURI, userName,
								passWord, getCompleteOrderDetailsInputXml, "getCompleteOrderDetails", "N",
								getCompleteOrderDetailsOutputTemplate);

						TestUtils.SaveResponseAsXML(getCompleteOrderDetailsResponse, getCompleteOrderDetailsAPICall);

						String ShipmentNo = TestUtils.XMLXpathReader(getCompleteOrderDetailsResponse, "//@ShipmentNo");

						String DropSAPOrderUpdatesSyncInputXml = null;

						for (int j = 0; j < toaltnooflines; j++) {

							int h = j + 1;
							String ItemID = DataProviderSelenium.getCellData(itemRow + j, 7);
							String OrderQty = DataProviderSelenium.getCellData(itemRow + j, 8);
							String ShipmentLineNo = String.valueOf(h);

							DropSAPOrderUpdatesSyncInputXml = absolutePath
									+ "\\TestData\\QAData\\ABFRLData\\DropSAPOrderUpdatesSync\\" + toaltnooflines
									+ "\\DropSAPOrderUpdatesSync.xml";

							TestUtils.editXmlFile("Shipment", 1, "OrderNo", OrderNo, DropSAPOrderUpdatesSyncInputXml);
						
							TestUtils.editXmlFile("Shipment", 1, "ShipmentNo", ShipmentNo,
									DropSAPOrderUpdatesSyncInputXml);
							TestUtils.editXmlFile("Shipment", 1, "Status", "A", DropSAPOrderUpdatesSyncInputXml);
							TestUtils.editXmlFile("Shipment", 1, "TrackingNo", ShipmentNo,
									DropSAPOrderUpdatesSyncInputXml);
							TestUtils.editXmlFile("ShipmentLine", h, "BilledQuantity", OrderQty,
									DropSAPOrderUpdatesSyncInputXml);
							TestUtils.editXmlFile("ShipmentLine", h, "ItemID", ItemID, DropSAPOrderUpdatesSyncInputXml);
							TestUtils.editXmlFile("ShipmentLine", h, "OrderedQuantity", OrderQty,
									DropSAPOrderUpdatesSyncInputXml);
							TestUtils.editXmlFile("ShipmentLine", h, "ShipmentLineNo", ShipmentLineNo,
									DropSAPOrderUpdatesSyncInputXml);

						}

						
					DropSAPOrderUpdatesSyncServiceCall = OMSAPICall(baseURI, userName, passWord,
								"DropSAPOrderUpdatesSync", DropSAPOrderUpdatesSyncInputXml, "DropSAPOrderUpdatesSync",
								"Y", "");

						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
								;
						testInfo.log(com.aventstack.extentreports.Status.INFO, "Order Status is in :" + getOrderStatus);
						
						
						
						TestUtils.editXmlFile("Shipment", 1, "Status", "I", DropSAPOrderUpdatesSyncInputXml);
						
						DropSAPOrderUpdatesSyncServiceCall = OMSAPICall(baseURI, userName, passWord,
								"DropSAPOrderUpdatesSync", DropSAPOrderUpdatesSyncInputXml, "DropSAPOrderUpdatesSync",
								"Y", "");
						
						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
						testInfo.log(com.aventstack.extentreports.Status.INFO, "Order Status is in :" + getOrderStatus);


						TestUtils.editXmlFile("Shipment", 1, "Status", "S", DropSAPOrderUpdatesSyncInputXml);
				DropSAPOrderUpdatesSyncServiceCall = OMSAPICall(baseURI, userName, passWord,
								"DropSAPOrderUpdatesSync", DropSAPOrderUpdatesSyncInputXml, "DropSAPOrderUpdatesSync",
								"Y", "");

						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
						testInfo.log(com.aventstack.extentreports.Status.INFO, "Order Status is in :" + getOrderStatus);

						String CarrierPickedStatusToOrderDeliveredInputXml = absolutePath
								+ "\\TestData\\QAData\\ABFRLData\\InputXmls\\CarrierPickedStatus.xml";

						
						//if (!OrderType.contains("error")) { 
						TestUtils.editXmlFile("Shipment", 1, "ShipmentNo", ShipmentNo,
								CarrierPickedStatusToOrderDeliveredInputXml);
						
						TestUtils.editXmlFile("Shipment", 1, "Status", "PI",
								CarrierPickedStatusToOrderDeliveredInputXml);
					
						TestUtils.editXmlFile("Shipment", 1, "TrackingNo", ShipmentNo,
								CarrierPickedStatusToOrderDeliveredInputXml);
						TestUtils.editXmlFile("ShipmentStatusAudit", 1, "StatusDate", OrderDate,
								CarrierPickedStatusToOrderDeliveredInputXml);

						DropSAPOrderUpdatesSyncServiceCall = OMSAPICall(baseURI, userName, passWord,
								"ProcessDlyStatusUpdate_Sync_EX", CarrierPickedStatusToOrderDeliveredInputXml,
								"ProcessDlyStatusUpdate_Sync_EX", "Y", "");
						
						//}
						

						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);

						TestUtils.editXmlFile("Shipment", 1, "Status", "OFD",
								CarrierPickedStatusToOrderDeliveredInputXml);

						DropSAPOrderUpdatesSyncServiceCall = OMSAPICall(baseURI, userName, passWord,
								"ProcessDlyStatusUpdate_Sync_EX", CarrierPickedStatusToOrderDeliveredInputXml,
								"ProcessDlyStatusUpdate_Sync_EX", "Y", "");
						System.out.println("The value of API Call" +DropSAPOrderUpdatesSyncServiceCall);
                        
						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
						testInfo.log(com.aventstack.extentreports.Status.INFO, "Order Status is in :" + getOrderStatus);

						if (OrderType.contains("NotDelivered")) {
							TestUtils.editXmlFile("Shipment", 1, "Status", "L",
									CarrierPickedStatusToOrderDeliveredInputXml);
						} else {
							TestUtils.editXmlFile("Shipment", 1, "Status", "DE",
									CarrierPickedStatusToOrderDeliveredInputXml);
						}

						DropSAPOrderUpdatesSyncServiceCall = OMSAPICall(baseURI, userName, passWord,
								"ProcessDlyStatusUpdate_Sync_EX", CarrierPickedStatusToOrderDeliveredInputXml,
								"ProcessDlyStatusUpdate_Sync_EX", "Y", "");

						getOrderStatus = InventoryAsPerSourcingRule.getOrderStatus(OrderNo);
			;
						testInfo.log(com.aventstack.extentreports.Status.INFO, "Order Status is in :" + getOrderStatus);

					}

				}

			} catch (Exception e) {
				String ExceptionMessage = exceptionsToString(e);
				testInfo.log(com.aventstack.extentreports.Status.FAIL, "Error Message : " + ExceptionMessage);
				

			}

		}
		
		
		//MailProjectClass.acuverEmailNotification("premkumar.uppu@acuverconsulting.com", "premkumar.uppu@acuverconsulting.com", "Gurudatta@222321");
		DataProviderSelenium.close();
		Thread.sleep(5000);
		
		//MailProjectClass.acuverEmailNotification("premkumar.uppu@acuverconsulting.com", "premkumar.uppu@acuverconsulting.com", "Gurudatta@222321");

	}
	
	

}
