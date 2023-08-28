package com.outwit.testexecution;

import java.io.BufferedWriter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Orderflow {
	
	

	@BeforeMethod
	public void updateXML() throws Exception {

		UpdateXML.updateOrderNO();
	}

	@Test(invocationCount = 1)

	public void orderflowdemo() throws Exception {
		
        //create order api call
		APIcall queryParams = new APIcall("createOrder");

		queryParams.getQueryParams();

		String resp = queryParams.response("./XML_files/createOrder.xml");

		BufferedWriter writer = FileOperations.writeXML("./XML_files/CreateOrderResponse.xml");
		writer.write(resp);
		writer.close();
		FileOperations.csvfileWriter("D:\\automation_testing\\testauto.csv");

		System.out.println(resp);
	
		//Schedule order api call
		String OHK = FileOperations.XMLXpathReader("./XML_files/CreateOrderResponse.xml", "//Order/@OrderHeaderKey");

		String inputXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<ScheduleOrder OrderHeaderKey=\"" + OHK
				+ "\"/>";

		writer = FileOperations.writeXML("./XML_files/ScheduleOrderInput.xml");
		writer.write(inputXML);
		writer.close();

		queryParams = new APIcall("scheduleOrder");
		queryParams.getQueryParams();

	    resp = queryParams.response("./XML_files/ScheduleOrderInput.xml");

		writer = FileOperations.writeXML("./XML_files/ScheduleOrderResponse.xml");
		writer.write(resp);
		writer.close();

		System.out.println(resp);
	

		//ReleaseOrder order api call
	    inputXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<ReleaseOrder OrderHeaderKey=\"" + OHK
				+ "\"/>";

		writer = FileOperations.writeXML("./XML_files/ReleaseOrderInput.xml");
		writer.write(inputXML);
		writer.close();

		 queryParams = new APIcall("releaseOrder");
		queryParams.getQueryParams();

		 resp = queryParams.response("./XML_files/ReleaseOrderInput.xml");

		writer = FileOperations.writeXML("./XML_files/ReleaseOrderResponse.xml");
		writer.write(resp);
		writer.close();

		System.out.println(resp);
	

		//confirmShipment order api call
		 inputXML = "<Shipment Action=\"Create-Modify\" EnterpriseCode=\"Matrix\">\r\n"
				+ "					<ShipmentLines>\r\n"
				+ "						<ShipmentLine DocumentType=\"0001\"\r\n"
				+ "							OrderHeaderKey=\"" + OHK + "\" PrimeLineNo=\"1\"\r\n"
				+ "							ProductClass=\"Good\" Quantity=\"1\" ReleaseNo=\"1\" SubLineNo=\"1\"\r\n"
				+ "							UnitOfMeasure=\"\"></ShipmentLine>\r\n"
				+ "					</ShipmentLines>\r\n" + "				</Shipment>"

		;

		 writer = FileOperations.writeXML("./XML_files/confirmShipmentInput.xml");
		writer.write(inputXML);
		writer.close();

	    queryParams = new APIcall("confirmShipment");
		queryParams.getQueryParams();

		resp = queryParams.response("./XML_files/confirmShipmentInput.xml");

		writer = FileOperations.writeXML("./XML_files/confirmShipmentResponse.xml");
		writer.write(resp);
		writer.close();

		System.out.println(resp);


		//getOrderDetails order api call
		 queryParams = new APIcall("getOrderDetails");
		queryParams.getQueryParams();

		 resp = queryParams.response("./XML_files/CreateOrderResponse.xml");

		 writer = FileOperations.writeXML("./XML_files/getOrderDetailsResponse.xml");
		writer.write(resp);
		writer.close();

		String OrderHeaderKey = FileOperations.XMLXpathReader("./XML_files/getOrderDetailsResponse.xml",
				                           "//Order/@OrderHeaderKey");
		String OrderNo = FileOperations.XMLXpathReader("./XML_files/getOrderDetailsResponse.xml", "//Order/@OrderNo");
		String status = FileOperations.XMLXpathReader("./XML_files/getOrderDetailsResponse.xml", "//Order/@Status");

		System.out.println("OrderHeaderKey:" + OrderHeaderKey);
		System.out.println("OrderNo:" + OrderNo);
		System.out.println("Status:" + status);
		
		//FileOperations.XmlToExcelWriter();
	}
}


