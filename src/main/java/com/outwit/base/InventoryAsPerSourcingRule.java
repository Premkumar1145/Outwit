package com.outwit.base;

import java.util.ArrayList;
import java.util.HashMap;

import com.outwit.utils.DataProviderSelenium;
import com.outwit.utils.ExcelUtils;
import com.outwit.utils.TestUtils;
import com.aventstack.extentreports.Status;

public class InventoryAsPerSourcingRule extends BasePageActions {

	public static String ProcurementOrderNo;
	public static String getOrderStatus;
	//public static String get;

	public static void getShipNodeInventory(String inputXmlPath, String ShipNodeInvRespPath, String ShipNode,
			String TotalDemandXpath, int ItemRow, int InvCol, String TotalSupplyXpath, String adjustInvInputXml,
			String ItemId) throws Exception {
		testInfo.log(Status.INFO, "Call API " + "getShipNodeInventory");
		// String inputXmlPath =
		// "D:\\LandMarkDocuments\\LandMarkOmsApi\\getShipNodeInventory.xml";
		//ExcelUtils excel = new ExcelUtils();

		TestUtils.editXmlSingleNode("ItemID", ItemId, inputXmlPath);
		TestUtils.editXmlSingleNode("Node", ShipNode, inputXmlPath);

		String apiResponse = OMSAPICall(baseURI, userName, passWord, "getShipNodeInventory", inputXmlPath,
				"getShipNodeInventory", "N", "");

		TestUtils.SaveResponseAsXML(ShipNodeInvRespPath, apiResponse);

		String ShipNodes = TestUtils.XMLXpathReader(ShipNodeInvRespPath, TotalDemandXpath);
		if (ShipNodes == null) {
			String ShipNodeExcel = DataProviderSelenium.getCellData(ItemRow, InvCol);
			int ShipNodeInvExcel = Integer.parseInt(ShipNodeExcel);
			TestUtils.editXmlFile("Item", 1, "Quantity", ShipNodeExcel, adjustInvInputXml);
			TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
			TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
			InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
		} else {

			int ShipNodeTotalDemand = Integer.parseInt(ShipNodes);
			testInfo.log(Status.INFO, "ShipNode from Excel Sheet: " + ShipNode);

			logger.info("Riyad ShipNode TotalDemand is : " + ShipNodeTotalDemand);
			testInfo.log(Status.INFO, "ShipNode existing Demand: " + ShipNodeTotalDemand);

			String ShipNodeExcel = DataProviderSelenium.getCellData(ItemRow, InvCol);

			int ShipnodeInvExcel = Integer.parseInt(ShipNodeExcel);
			testInfo.log(Status.INFO, "ShipNode Demand from Excel: " + ShipnodeInvExcel);

			logger.info("Inventory in Riyad :" + ShipNodeExcel);

			int TotalDemand = Integer.sum(ShipNodeTotalDemand, ShipnodeInvExcel);
			testInfo.log(Status.INFO, "Updating Inventory Quantity: " + TotalDemand);

			logger.info("The Updated Demand is :" + TotalDemand);

			String TotalQuantity = TestUtils.XMLXpathReader(ShipNodeInvRespPath, TotalSupplyXpath);

			int ShipNodeSupplyQuantity = Integer.parseInt(TotalQuantity);
			testInfo.log(Status.INFO, "Total Supply from Shipnode: " + ShipNodeSupplyQuantity);

			logger.info("Riyad ShipNode TotalQuantity is : " + ShipNodeSupplyQuantity);
			int finalUpdateInventory = 0;
			if (ShipNodeSupplyQuantity > TotalDemand) {
				finalUpdateInventory = -(ShipNodeSupplyQuantity - TotalDemand);
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				String finalUpdateInv = String.valueOf(finalUpdateInventory);
				TestUtils.editXmlFile("Item", 1, "Quantity", finalUpdateInv, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
				InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
				String inputData = TestUtils.GenerateStrFromRes(adjustInvInputXml);
				String requestResult = TestUtils.convertStringtoFormattedXML(inputData);
				// RMSAPICall(ItemId, finalUpdateInv);

			} else if (ShipNodeSupplyQuantity < TotalDemand) {
				finalUpdateInventory = TotalDemand - ShipNodeSupplyQuantity;
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				String finalUpdateInv = String.valueOf(finalUpdateInventory);
				TestUtils.editXmlFile("Item", 1, "Quantity", finalUpdateInv, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
				InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
				String inputData = TestUtils.GenerateStrFromRes(adjustInvInputXml);
				String requestResult = TestUtils.convertStringtoFormattedXML(inputData);
				// RMSAPICall(ItemId, finalUpdateInv);

			} else if (ShipNodeSupplyQuantity == TotalDemand) {
				finalUpdateInventory = ShipNodeSupplyQuantity;
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				testInfo.log(Status.INFO, "Total Supply and Total Demand is same, so no Inventory Update ");

			}
		}

	}
	
	
	public static void getShipNodeInventoryNew(String inputXmlPath, String ShipNodeInvRespPath, String ShipNode,
			String TotalDemandXpath, String Inv, String TotalSupplyXpath, String adjustInvInputXml,
			String ItemId) throws Exception {
		testInfo.log(Status.INFO, "Call API " + "getShipNodeInventory");
		//ExcelUtils excel = new ExcelUtils();
		TestUtils.editXmlSingleNode("ItemID", ItemId, inputXmlPath);
		TestUtils.editXmlSingleNode("Node", ShipNode, inputXmlPath);

		String apiResponse = OMSAPICall(baseURI, userName, passWord, "getShipNodeInventory", inputXmlPath,
				"getShipNodeInventory", "N", "");

		TestUtils.SaveResponseAsXML(ShipNodeInvRespPath, apiResponse);

		//to get total demand from OMS
		String ShipNodes = TestUtils.XMLXpathReader(ShipNodeInvRespPath, TotalDemandXpath);
		if (ShipNodes == null) {
			TestUtils.editXmlFile("Item", 1, "Quantity", Inv, adjustInvInputXml);
			TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
			TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
			InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
		} else {

			//conversion from String to int
			int ShipNodeTotalDemand = Integer.parseInt(ShipNodes);
			testInfo.log(Status.INFO, "ShipNode from Excel Sheet: " + ShipNode);
			logger.info("ShipNode TotalDemand is : " + ShipNodeTotalDemand);
			testInfo.log(Status.INFO, "ShipNode existing Demand: " + ShipNodeTotalDemand);
			testInfo.log(Status.INFO, "ShipNode Demand from Excel: " + Inv);
			logger.info("Inventory in ShipNode:" + Inv);
			
			//conversion from String to int
			int Inventory = Integer.parseInt(Inv);
			
			//Excel Demand + Demand from OMS
			int TotalDemand = Integer.sum(ShipNodeTotalDemand, Inventory);
			testInfo.log(Status.INFO, "Updating Inventory Quantity: " + TotalDemand);
			logger.info("The Updated Demand is :" + TotalDemand);

			//TotalSupplyQuantity from OMS
			String TotalQuantity = TestUtils.XMLXpathReader(ShipNodeInvRespPath, TotalSupplyXpath);
			
			//conversion from String to int
			int ShipNodeSupplyQuantity = Integer.parseInt(TotalQuantity);
			testInfo.log(Status.INFO, "Total Supply from Shipnode: " + ShipNodeSupplyQuantity);

			logger.info("ShipNode TotalQuantity is : " + ShipNodeSupplyQuantity);
			int finalUpdateInventory = 0;
			if (ShipNodeSupplyQuantity > TotalDemand) {
				finalUpdateInventory = -(ShipNodeSupplyQuantity - TotalDemand);
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				String finalUpdateInv = String.valueOf(finalUpdateInventory);
				TestUtils.editXmlFile("Item", 1, "Quantity", finalUpdateInv, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
				InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
				String inputData = TestUtils.GenerateStrFromRes(adjustInvInputXml);
				String requestResult = TestUtils.convertStringtoFormattedXML(inputData);
				// RMSAPICall(ItemId, finalUpdateInv);

			} else if (ShipNodeSupplyQuantity < TotalDemand) {
				finalUpdateInventory = TotalDemand - ShipNodeSupplyQuantity;
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				String finalUpdateInv = String.valueOf(finalUpdateInventory);
				TestUtils.editXmlFile("Item", 1, "Quantity", finalUpdateInv, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
				InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
				String inputData = TestUtils.GenerateStrFromRes(adjustInvInputXml);
				String requestResult = TestUtils.convertStringtoFormattedXML(inputData);
				// RMSAPICall(ItemId, finalUpdateInv);

			} else if (ShipNodeSupplyQuantity == TotalDemand) {
				finalUpdateInventory = ShipNodeSupplyQuantity;
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				testInfo.log(Status.INFO, "Total Supply and Total Demand is same, so no Inventory Update ");

			}
		}

	}
	
	/*public static void getShipNodeInventoryForCancellation(String inputXmlPath, String ShipNodeInvRespPath, String ShipNode,
			String TotalDemandXpath, int ItemRow, int InvCol, String TotalSupplyXpath, String adjustInvInputXml,
			String ItemId) throws Exception {
		testInfo.log(Status.INFO, "Call API " + "getShipNodeInventory");
		// String inputXmlPath =
		// "D:\\LandMarkDocuments\\LandMarkOmsApi\\getShipNodeInventory.xml";
		ExcelUtils excel = new ExcelUtils();

		TestUtils.editXmlSingleNode("ItemID", ItemId, inputXmlPath);
		TestUtils.editXmlSingleNode("Node", ShipNode, inputXmlPath);

		String apiResponse = OMSAPICall(baseURI, userName, passWord, "getShipNodeInventory", inputXmlPath,
				"getShipNodeInventory", "N", "");

		TestUtils.SaveResponseAsXML(ShipNodeInvRespPath, apiResponse);

		String RYDShipNode = TestUtils.XMLXpathReader(ShipNodeInvRespPath, TotalDemandXpath);
		if (RYDShipNode == null) {
			String RydShipNodeExcel = DataProviderSelenium.getCellData(ItemRow, InvCol);
			int RYDInvExcel = Integer.parseInt(RydShipNodeExcel);
			TestUtils.editXmlFile("Item", 1, "Quantity", RydShipNodeExcel, adjustInvInputXml);
			TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
			TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
			InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
		} else {

			int RYDTotalDemand = Integer.parseInt(RYDShipNode);
			testInfo.log(Status.INFO, "ShipNode from Excel Sheet: " + ShipNode);

			logger.info("Riyad ShipNode TotalDemand is : " + RYDTotalDemand);
			testInfo.log(Status.INFO, "ShipNode existing Demand: " + RYDTotalDemand);

			String RydShipNodeExcel = DataProviderSelenium.getCellData(ItemRow, InvCol);

			int RYDInvExcel = Integer.parseInt(RydShipNodeExcel);
			testInfo.log(Status.INFO, "ShipNode Demand from Excel: " + RYDInvExcel);

			logger.info("Inventory in Riyad :" + RydShipNodeExcel);

			int TotalDemand = Integer.sum(RYDTotalDemand, RYDInvExcel);
			testInfo.log(Status.INFO, "Updating Inventory Quantity: " + TotalDemand);

			logger.info("The Updated Demand is :" + TotalDemand);

			String TotalQuantity = TestUtils.XMLXpathReader(ShipNodeInvRespPath, TotalSupplyXpath);

			int RYDSupplyQuantity = Integer.parseInt(TotalQuantity);
			testInfo.log(Status.INFO, "Total Supply from Shipnode: " + RYDSupplyQuantity);

			logger.info("Riyad ShipNode TotalQuantity is : " + RYDSupplyQuantity);
			int finalUpdateInventory = 0;
			if (RYDSupplyQuantity > TotalDemand) {
				finalUpdateInventory = -(RYDSupplyQuantity - TotalDemand);
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				String finalUpdateInv = String.valueOf(finalUpdateInventory);
				TestUtils.editXmlFile("Item", 1, "Quantity", finalUpdateInv, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
				InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
				String inputData = TestUtils.GenerateStrFromRes(adjustInvInputXml);
				String requestResult = TestUtils.convertStringtoFormattedXML(inputData);
				// RMSAPICall(ItemId, finalUpdateInv);

			} else if (RYDSupplyQuantity < TotalDemand) {
				finalUpdateInventory = TotalDemand - RYDSupplyQuantity;
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				String finalUpdateInv = String.valueOf(finalUpdateInventory);
				TestUtils.editXmlFile("Item", 1, "Quantity", finalUpdateInv, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ShipNode", ShipNode, adjustInvInputXml);
				TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, adjustInvInputXml);
				InventoryAsPerSourcingRule.adjustInventory(adjustInvInputXml);
				String inputData = TestUtils.GenerateStrFromRes(adjustInvInputXml);
				String requestResult = TestUtils.convertStringtoFormattedXML(inputData);
				// RMSAPICall(ItemId, finalUpdateInv);

			} else if (RYDSupplyQuantity == TotalDemand) {
				finalUpdateInventory = RYDSupplyQuantity;
				testInfo.log(Status.INFO, "Total Supply Quantity Update in Oms : " + finalUpdateInventory);
				testInfo.log(Status.INFO, "Total Supply and Total Demand is same, so no Inventory Update ");

			}
		}

	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void adjustInventory(String adjustInvInputXml) {
		testInfo.log(Status.INFO, "Call API " + "adjustInventory");
		String apiResponse = OMSAPICall(baseURI, userName, passWord, "adjustInventory", adjustInvInputXml,
				"adjustInventory", "N", "");

	}
	
	//Create Order
	public static void createOrder(String OrderNo, String createOrderInputXml,String responseXMl) throws InterruptedException {
		testInfo.log(Status.INFO, "Call Create Order Serivce ");

		// Thread.sleep(60000);
		// GetOrderDetails to fetch OrderHeaderKey
	//	TestUtils.editXmlSingleNode("OrderNo", OrderNo, getOrderDetailsInputXml);
		String apiResponse = OMSAPICallwithOutputTemplate(baseURI, userName, passWord, 
				createOrderInputXml, "CromaPh2_CreateOrder_Sync", "Y",  responseXMl);
		

		String rootName = TestUtils.getXmlRootName(apiResponse);
		if (rootName.equals("Errors")) {
			// Assert.assertFalse(true, "API response is showing error");
			testInfo.log(Status.FAIL, "Order has not reached to OMS");

		} else {

			TestUtils.SaveResponseAsXML(responseXMl, apiResponse);

			String OrderHeaderkey = TestUtils.XMLXpathReader(responseXMl, "//Order/@OrderHeaderKey");


		}
	}
	
	
	
	// Inventory as per sourcing rule

	public static void scheduleOrder(String OrderNo, String scheduleOrderInputXml, String getOrderDetailsInputXml,
			String getOrderDetailsOutputTemplate, String getOrderDetailsResponse) throws InterruptedException {
		testInfo.log(Status.INFO, "Call API " + "scheduleOrder");

		// Thread.sleep(60000);
		// GetOrderDetails to fetch OrderHeaderKey
		TestUtils.editXmlSingleNode("OrderNo", OrderNo, getOrderDetailsInputXml);
		String apiResponse = OMSAPICallwithOutputTemplate(baseURI, userName, passWord, 
				getOrderDetailsInputXml, "getOrderDetails", "N",  getOrderDetailsOutputTemplate);

		String rootName = TestUtils.getXmlRootName(apiResponse);
		if (rootName.equals("Errors")) {
			// Assert.assertFalse(true, "API response is showing error");
			testInfo.log(Status.FAIL, "Order has not reached to OMS");

		} else {

			TestUtils.SaveResponseAsXML(getOrderDetailsResponse, apiResponse);

			String OrderHeaderkey = TestUtils.XMLXpathReader(getOrderDetailsResponse, "//Order/@OrderHeaderKey");

			TestUtils.editXmlSingleNode("OrderHeaderKey", OrderHeaderkey, scheduleOrderInputXml);

			String omsResponse = OMSAPICall(baseURI, userName, passWord, "scheduleOrder", scheduleOrderInputXml,
					"scheduleOrder", "N", "");

		}
	}
	
	public static void releaseOrder(String OrderNo, String releaseOrderInputXML, String getOrderDetailsInputXml,
			String getOrderDetailsOutputTemplate, String getOrderDetailsResponse) throws InterruptedException {
		testInfo.log(Status.INFO, "Call API " + "scheduleOrder");

		// Thread.sleep(60000);
		// GetOrderDetails to fetch OrderHeaderKey
		TestUtils.editXmlSingleNode("OrderNo", OrderNo, getOrderDetailsInputXml);
		String apiResponse = OMSAPICallwithOutputTemplate(baseURI, userName, passWord, 
				getOrderDetailsInputXml, "getOrderDetails", "N",  getOrderDetailsOutputTemplate);

		String rootName = TestUtils.getXmlRootName(apiResponse);
		if (rootName.equals("Errors")) {
			// Assert.assertFalse(true, "API response is showing error");
			testInfo.log(Status.FAIL, "Order has not reached to OMS");

		} else {

			TestUtils.SaveResponseAsXML(getOrderDetailsResponse, apiResponse);

			String OrderHeaderkey = TestUtils.XMLXpathReader(getOrderDetailsResponse, "//Order/@OrderHeaderKey");

			TestUtils.editXmlSingleNode("OrderHeaderKey", OrderHeaderkey, releaseOrderInputXML);

			String omsResponse = OMSAPICall(baseURI, userName, passWord, "releaseOrder", releaseOrderInputXML,
					"releaseOrder", "N", "");

		}
	}
	
	public static String getOrderStatusDetails(String OrderNo, String getOrderDetailsInputXml,
			String getOrderDetailsResponse)
			{
			String apiResponse = OMSAPICall(baseURI, userName, passWord, "getOrderDetails", getOrderDetailsInputXml,
			"getOrderDetails", "N", "");
			
			TestUtils.SaveResponseAsXML(getOrderDetailsResponse, apiResponse);
			String orderStatus = TestUtils.XMLXpathReader(getOrderDetailsResponse, "//Order/@Status");
			return orderStatus;

			}
	
	public static String getOrderStatus(String OrderNo)
			{
		
	
		try {
			getOrderStatus = InventoryAsPerSourcingRule.getOrderStatusDetails(OrderNo,
					absolutePath + "\\TestData\\QAData\\ABFRLData\\InputXmls\\getOrderDetails.xml",
					absolutePath
							+ "\\TestData\\QAData\\ABFRLData\\ResponseXmls\\getOrderDetailsResponse.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			testInfo.log(Status.FAIL, "Order has not reached to OMS and Getting Invalid Response getOrderStatus API " +e);
		}
			
			
			return getOrderStatus;

			}
	
	
	
	public static String verifyOrderInOMS(String OrderNo, String getOrderDetailsInputXml,
			String getOrderDetailsResponse)
			{
 			TestUtils.editXmlSingleNode("OrderNo", OrderNo, getOrderDetailsInputXml);

			String apiResponse = OMSAPICall(baseURI, userName, passWord, "getOrderDetails", getOrderDetailsInputXml,
			"getOrderDetails", "N", "");
			String rootName = TestUtils.getXmlRootName(apiResponse);
			if (rootName.equals("Errors")) {
			// Assert.assertFalse(true, "API response is showing error");
			//testInfo.log(Status.FAIL, "Order has not reached to OMS");
			return "Errors";

			}
			else
			{
			return "Order reached to OMS";
			}
			}


	public static void sourcingRuleValidations(String OrderNo, String getOrderDetailsInputXml,
			String getOrderDetailsOutputTemplate, String getOrderDetailsSourcingResponse) throws InterruptedException {
		testInfo.log(Status.INFO, "Call API " + "getOrderDetails");

		// GetOrderDetails to fetch procurement Nodes
		TestUtils.editXmlSingleNode("OrderNo", OrderNo, getOrderDetailsInputXml);
		String apiResponse = OMSAPICallwithOutputTemplate(baseURI, userName, passWord, 
				getOrderDetailsInputXml, "getOrderDetails", "N",  getOrderDetailsOutputTemplate);

		TestUtils.SaveResponseAsXML(getOrderDetailsSourcingResponse, apiResponse);

	}
	
	
	public static void ShortPickService (String ItemId,int ItemQty,String OrderNO,int ShortPick,String getOrderListinputXml,String getOrderListOutputTemplate,String getOrderListResponse,String getOrderDetailsInputXml,String getOrderDetailsResponse,
			String ShortPickServiceinputXml,String scheduleOrderInputXml,String getOrderDetailsFromProcurement,String getOrderDetailsFromProcurementResponse)
	{	
	
		testInfo = extent.createTest("ShortPickService");
		testInfo.log(Status.INFO, "Call API " + "getOrderDetails");
		
		TestUtils.editXmlSingleNode("OrderNo", OrderNO, getOrderDetailsInputXml);

		String apiResponse = OMSAPICall(baseURI, userName, passWord, "getOrderDetails", getOrderDetailsInputXml,
				"getOrderDetails", "N", "");
	
		TestUtils.SaveResponseAsXML(getOrderDetailsResponse, apiResponse);
		String OrderHeaderKey = TestUtils.XMLXpathReader(getOrderDetailsResponse, "//Order/@OrderHeaderKey");
		TestUtils.editXmlFile("OrderLine", 1, "ChainedFromOrderHeaderKey", OrderHeaderKey, getOrderListinputXml);

		String getOrderListApi = OMSAPICallwithOutputTemplate(baseURI, userName, passWord,  getOrderListinputXml, "getOrderList", "N",  getOrderListOutputTemplate);

		TestUtils.SaveResponseAsXML(getOrderListResponse, getOrderListApi);

		
		
		ArrayList<String> OrderHeaderkey = TestUtils.getListofValuesFromXml("Order",  "OrderHeaderKey",  getOrderListResponse);
		//System.out.println(OrderHeaderkey);
		
		for(int i=0;i<OrderHeaderkey.size();i++)
		{
			
			String orderheaderkey = OrderHeaderkey.get(i);
			
			TestUtils.editXmlSingleNode("OrderHeaderKey", orderheaderkey, scheduleOrderInputXml);

			String scheduleOrderApi = OMSAPICall(baseURI, userName, passWord, "scheduleOrder", scheduleOrderInputXml,
					"scheduleOrder", "N", "");
			
			//we are passing scheduleOrderInputXml for ReleaseOrderApi
			
			String releaseOrderApi = OMSAPICall(baseURI, userName, passWord, "releaseOrder", scheduleOrderInputXml,
					"releaseOrder", "N", "");
			
		}
		
		
		
		
		ArrayList<String> OrderNo =  TestUtils.getListofValuesFromXml("Order", "OrderNo",getOrderListResponse);
		HashMap<String, String> ShipNodeMap = TestUtils.getHashMapValuesFromXml("Order", "OrderNo", "ShipNode", getOrderListResponse);
		
 		for(int i = 0;i<OrderNo.size();i++)
		{
			
			String orderno = OrderNo.get(i);
			//
			
			TestUtils.editXmlSingleNode("OrderNo", orderno, getOrderDetailsFromProcurement);

			String getOrderPrimeApi = OMSAPICall(baseURI, userName, passWord, "getOrderDetails", getOrderDetailsFromProcurement,
					"getOrderDetails", "N", "");
			
			TestUtils.SaveResponseAsXML(getOrderDetailsFromProcurementResponse, getOrderPrimeApi);
			
			ArrayList<String> ITEMID =  TestUtils.getListofValuesFromXml("Item", "ItemID",getOrderDetailsFromProcurementResponse);

			for(int j=0;j<=ITEMID.size();j++)
			{
				
			}
			
			String PrimeLineNo = TestUtils.XMLXpathReader(getOrderDetailsFromProcurementResponse,"//Order/OrderLines/OrderLine/Item[contains(@ItemID,"+ItemId+")] /../@PrimeLineNo");
			
			
			
			int StatusQty = ItemQty - ShortPick;
			
			String StatusQuantity = String.valueOf(StatusQty);
			
			
			
			
			
			
			
			
			
			//

			String ShipNode = ShipNodeMap.get(orderno);
			
			TestUtils.editXmlFile("OrderLine", 1, "PrimeLineNo", PrimeLineNo, ShortPickServiceinputXml);
			TestUtils.editXmlFile("OrderLine", 1, "StatusQuantity", StatusQuantity, ShortPickServiceinputXml);
			TestUtils.editXmlFile("OrderRelease", 1, "ShipNode", ShipNode, ShortPickServiceinputXml);
			TestUtils.editXmlFile("OrderRelease", 1, "OrderNo", orderno, ShortPickServiceinputXml);
			TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, ShortPickServiceinputXml);
			//TestUtils.editXmlFile("Item", 1, "ItemID", ItemId, ShortPickServiceinputXml);
			
			String Shortpick = OMSAPICall(baseURI, userName, passWord, "ShortPickService", ShortPickServiceinputXml,
					"lmgsvi_shortPick", "Y", "");
			
		}
 		

			String GetOrderNoResponse = OMSAPICall(baseURI, userName, passWord, "getOrderDetails", getOrderDetailsInputXml,
					"getOrderDetails", "N", "");
		
			TestUtils.SaveResponseAsXML(getOrderDetailsResponse, GetOrderNoResponse);
			String OrderHeaderKeyforOrderList = TestUtils.XMLXpathReader(getOrderDetailsResponse, "//Order/@OrderHeaderKey");
			TestUtils.editXmlFile("OrderLine", 1, "ChainedFromOrderHeaderKey", OrderHeaderKeyforOrderList, getOrderListinputXml);
			
			
			TestUtils.editXmlSingleNode("OrderHeaderKey", OrderHeaderKeyforOrderList, scheduleOrderInputXml);
			
			String scheduleOrder = OMSAPICall(baseURI, userName, passWord, "scheduleOrder", scheduleOrderInputXml,
					"scheduleOrder", "N", "");
			
			
			

			String GetOrderListApi = OMSAPICallwithOutputTemplate(baseURI, userName, passWord,  getOrderListinputXml, "getOrderList", "N",  getOrderListOutputTemplate);

			TestUtils.SaveResponseAsXML(getOrderListResponse, GetOrderListApi);


			
			
		
		
		
		//System.out.println(OrderNo);
	
	
	
	
	
	
}	
	
	public static String ProcurementOrderStatusforDC (String DC ,String OrderNo , String getOrderDetailsInputXml , String getOrderDetailsResponseForDC , String getOrderListinputXml , String getOrderListOutputTemplate , String getOrderListResponseForDC)
	{
		
		
		TestUtils.editXmlSingleNode("OrderNo", OrderNo, getOrderDetailsInputXml);
		
		String apiResponse = OMSAPICall(baseURI, userName, passWord, "getOrderDetails", getOrderDetailsInputXml,
				"getOrderDetails", "N", "");

		TestUtils.SaveResponseAsXML(getOrderDetailsResponseForDC, apiResponse);
		
		String OrderHeaderKey = TestUtils.XMLXpathReader(getOrderDetailsResponseForDC, "//Order/@OrderHeaderKey");
		
		TestUtils.editXmlFile("OrderLine", 1, "ChainedFromOrderHeaderKey", OrderHeaderKey, getOrderListinputXml);

		String getOrderListApi = OMSAPICallwithOutputTemplate(baseURI, userName, passWord,  getOrderListinputXml, "getOrderList", "N",  getOrderListOutputTemplate);

		TestUtils.SaveResponseAsXML(getOrderListResponseForDC, getOrderListApi);
		
		String NoOfOrderList = TestUtils.XMLXpathReader(getOrderListResponseForDC, "//OrderList/@TotalOrderList");
		
		String OrderStatus ;
		if(!(NoOfOrderList.equals("0")))
		{
			 OrderStatus = TestUtils.XMLXpathReader(getOrderListResponseForDC, "//Order[contains(@ShipNode,'"+DC+"')]/@Status");
			 
			  ProcurementOrderNo = TestUtils.XMLXpathReader(getOrderListResponseForDC, "//Order[contains(@ShipNode,'"+DC+"')]/@OrderNo");
		
			return OrderStatus;
		
		}
		else
		{
			 OrderStatus = "Released";
			
			return OrderStatus;
		}
		
		
		
		
	}
	
	
	public static void TabSwitch() throws InterruptedException
	{
		String oldTab = driver.getWindowHandle();
		waitForWebElementAndClick(
				"//*[@id='mainContentHolder']/div[2]/div/div/div[1]/div[2]/div/div[2]/button",
				2000);
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		//newTab.remove(oldTab);

		Thread.sleep(5000);
		// change focus to new tab
		driver.switchTo().window(newTab.get(0));
	}
	
	
}
	
	
	
	

