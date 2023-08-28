/**
 * 
 */
package com.outwit.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.outwit.base.BasePageActions;

import io.restassured.http.Method;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;





/**
 * @author shreekant.rs
 *
 */
public final class TestUtils extends BasePageActions {

	public static Document createDocument(String root) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Document doc = null;
			Element ele = doc.createElement("Error");
			ele.setAttribute("RESTApiresponse", "Exception while converting response as a String to XML");

			return doc;
		}

		/*
		 * Element rootElement = doc.createElement(root);
		 * doc.appendChild(rootElement);
		 */

	}

	/**
	 * @author AnanthKrishna
	 *
	 */

	/*
	 * This method returns the value from the Xmlpath as per the Xpath given
	 * 
	 */

	public static String XMLXpathReader(String response, String Xpath) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new FileInputStream(new File(response)));// same
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
	

	public static String getListFromXpathReader(String response, String Xpath) {
		ArrayList<String> ValuesFromXpath = new ArrayList<>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new FileInputStream(new File(response)));// same
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
						ValuesFromXpath.add(c);
					}
				}
			}

			return c;
		} catch (Exception e) {

		}
		return null;
	}
	
	
	public static ArrayList<String> getListofValuesFromXpath(String response, String Xpath) {
		ArrayList<String> ValuesFromXpath = new ArrayList<>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new FileInputStream(new File(response)));// same
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
						ValuesFromXpath.add(c);
					}
				}
			}

			return ValuesFromXpath;
		} catch (Exception e) {

		}
		return null;
	}
	
	
	
	
	

	public static String evaluateXPath(Document document, String xpathExpression) {
		if (getXmlRootName(convertDocumentToString(document)).equals("Error")) {
			return "response is showing error";
		}
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();

		// Create XPath object
		XPath xpath = xpathFactory.newXPath();

		String value;
		try {
			// Create XPathExpression object
			XPathExpression expr = xpath.compile(xpathExpression);

			Node node = (Node) expr.evaluate(document, XPathConstants.NODESET);
			value = node.getNodeValue();
			return value;

		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return exceptionsToString(e);
		}

	}

	public static Document SaveResponseAsXML(String responsefilePath, String xmlStr) {
		Document doc = null;
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			// Write the parsed document to an xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(responsefilePath));
			transformer.transform(source, result);
			return doc;
		} catch (Exception e) {

		}
		return doc;

		/*
		 * DocumentBuilderFactory factory =
		 * DocumentBuilderFactory.newInstance(); DocumentBuilder builder;
		 * 
		 * try { builder = factory.newDocumentBuilder(); Document doc =
		 * builder.parse(new InputSource(new StringReader(xmlStr))); return doc;
		 * 
		 * } catch (Exception e) { e.printStackTrace(); doc = null ; Element ele
		 * = doc.createElement("Error"); ele.setAttribute("RESTApiresponse",
		 * "Exception while converting response as a String to XML");
		 * 
		 * return doc;
		 * 
		 * } return null;
		 */

	}

	public static String convertStringtoFormattedXML(String xmlStr) {

		try {
			org.dom4j.Document doc = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error while parsing string to formatted XML " + exceptionsToString(e));

		}
		StringWriter sw = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xw = new XMLWriter(sw, format);
		try {
			xw.write(xmlStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error while writing string to formatted XML " + exceptionsToString(e));
		}
		String result = sw.toString();

		return result;
	}

	public static String getXmlRootName(String xmlStr) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error while parcing to document builder " + exceptionsToString(e));
			return exceptionsToString(e);
		}
		Document document = null;
		try {
			document = builder.parse(new InputSource(new StringReader(xmlStr)));
			Element rootElementResponse = document.getDocumentElement();
			String rootName = document.getDocumentElement().getNodeName();
			return rootName;
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Error while parcing new input soource" + exceptionsToString(e));
			return exceptionsToString(e);
		}

	}

	/*
	 * public static String convertDocumentToString(Document doc) {
	 * 
	 * 
	 * 
	 * TransformerFactory tf = TransformerFactory.newInstance(); Transformer
	 * transformer; try { transformer = tf.newTransformer(); // below code to
	 * remove XML declaration //
	 * transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	 * StringWriter writer = new StringWriter(); transformer.transform(new
	 * DOMSource(doc), new StreamResult(writer)); String output =
	 * writer.getBuffer().toString(); return output; } catch
	 * (TransformerException e) { e.printStackTrace(); }
	 * 
	 * return null; }
	 */

	public static String convertDocumentToString(Document doc) {

		String fianlString = null;

		try {
			DOMSource source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, result);
			System.out.println("XML IN String format is: \n" + writer.toString());
			fianlString = writer.toString();

		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return fianlString;
	}

	public static String getAnyAttibuteValue(String getCompleteOrderDetailsResponse, String nodeName,
			String attributeName) {

		String attributeValue = null;

		try {
			StringReader sr = new StringReader(getCompleteOrderDetailsResponse);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(sr);
			while (parser.hasNext()) {
				int event = parser.next();
				if (event == XMLStreamConstants.START_ELEMENT) {
					if (parser.getLocalName().equals(nodeName)) {
						attributeValue = parser.getAttributeValue(null, attributeName);
						if (attributeValue != null)
							System.out.println(attributeValue);

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return attributeValue;
	}

	public static String CountXMLElement(String getCompleteOrderDetailsResponse) {

		String attributeValue = null;

		try {
			StringReader sr = new StringReader(getCompleteOrderDetailsResponse);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(sr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return attributeValue;
	}

	public Dimension getSystemScreenSize() {

		String fianlString = null;
		Dimension screenSize = null;

		try {
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			double width = screenSize.getWidth();
			double height = screenSize.getHeight();

			System.out.println("Screen Size is : " + screenSize);

			System.out.println("Width is : " + width);
			System.out.println("Height is : " + height);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return screenSize;
	}

	public static String exceptionsToString(Exception e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();

	}

	public static String GenerateStrFromRes(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return exceptionsToString(e);
		}
	}

	public static void editXmlSingleNode(String nodeName, String nodeValue, String filepath) {
		try {
			// String filepath = "D:\\MyFramework\\joannwebapi\\"+fileName;

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			// Get the root element
			Node company = doc.getFirstChild();
			NamedNodeMap attr1 = company.getAttributes();
			Node orderDetails = attr1.getNamedItem(nodeName);
			orderDetails.setTextContent(nodeValue);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
	
	 public static List<String> FindlistofValuesFromXpath(String response, String xpathExpression) throws Exception 
	    {
		 List<String> values = new ArrayList<>();
		 try
	        {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new FileInputStream(new File(response)));// same
																					// xml
											
	        // Create XPathFactory object
	        XPathFactory xpathFactory = XPathFactory.newInstance();
	         
	        // Create XPath object
	        XPath xpath = xpathFactory.newXPath();
	 
	       
	        
	            // Create XPathExpression object
	            XPathExpression expr = xpath.compile(xpathExpression);
	             
	            // Evaluate expression result on XML document
	            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
	             
	            for (int i = 0; i < nodes.getLength(); i++) {
	                values.add(nodes.item(i).getNodeValue());
	            }
	                 
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	        return values;
	    }
	 
	 
	 
	 public static List<String> FindlistofValuesFromXpath1(String response, String xpathExpression) throws Exception 
	    {
		 List<String> values = new ArrayList<>();
		 try
	        {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new FileInputStream(new File(response)));// same
																					// xml
											
	        // Create XPathFactory object
	        XPathFactory xpathFactory = XPathFactory.newInstance();
	         
	        // Create XPath object
	        XPath xpath = xpathFactory.newXPath();
	 
	       
	        
	            // Create XPathExpression object
	            XPathExpression expr = xpath.compile(xpathExpression);
	             
	            // Evaluate expression result on XML document
	            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
	             
	            for (int i = 0; i < nodes.getLength(); i++) {
	                values.add(nodes.item(i).getNodeValue());
	            }
	                 
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	        return values;
	    }
	
	
	
	
	
	
	

	public static ArrayList<String> getListofValuesFromXml(String TagName,  String nodeName,  String filepath) {
		ArrayList<String> listfromvalues = new ArrayList<>();

		try {
			// String filepath = "D:\\MyFramework\\joannwebapi\\"+fileName;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			logger.info("Root element: " + doc.getDocumentElement().getNodeName());
			// Get the root element
			Node company = doc.getFirstChild();
			NamedNodeMap attr1 = company.getAttributes();
			NodeList nodeList = doc.getElementsByTagName(TagName);
			// nodeList is not iterable, so we are using for loop
			System.out.println(nodeList.getLength());
			String nodeDetails;
			for(int i=0;i<nodeList.getLength();i++)
			{
				Node node = nodeList.item(i);
				NamedNodeMap attr2 = node.getAttributes();
				nodeDetails = attr2.getNamedItem(nodeName).getNodeValue();
				System.out.println(nodeDetails);
				listfromvalues.add(nodeDetails);
			}
			return listfromvalues;

		} catch (ParserConfigurationException pce) {
			testInfo.log(com.aventstack.extentreports.Status.FAIL, "Not able to execute : " + pce.toString());

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
		return listfromvalues;

	}
	
	
	public static HashMap<String, String> getHashMapValuesFromXml(String TagName,  String nodeName1,String nodeName2,String filepath) {
	HashMap<String,String> listfromvalues = new HashMap<>();

		try {
			// String filepath = "D:\\MyFramework\\joannwebapi\\"+fileName;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			logger.info("Root element: " + doc.getDocumentElement().getNodeName());
			// Get the root element
			Node company = doc.getFirstChild();
			NamedNodeMap attr1 = company.getAttributes();
			NodeList nodeList = doc.getElementsByTagName(TagName);
			// nodeList is not iterable, so we are using for loop
			System.out.println(nodeList.getLength());
		
			for(int i=0;i<nodeList.getLength();i++)
			{
				Node node = nodeList.item(i);
				NamedNodeMap attr2 = node.getAttributes();
				String nodeDetails1 = attr2.getNamedItem(nodeName1).getNodeValue();
				String nodeDetails2 = attr2.getNamedItem(nodeName2).getNodeValue();
				
				listfromvalues.put(nodeDetails1, nodeDetails2);
			}
			return listfromvalues;

		} catch (ParserConfigurationException pce) {
			testInfo.log(com.aventstack.extentreports.Status.FAIL, "Not able to execute : " + pce.toString());

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
		return listfromvalues;

	}
	
	
	public static void editXmlFile(String TagName, int nodeCount, String nodeName, String nodeValue, String filepath) {
		try {
			// String filepath = "D:\\MyFramework\\joannwebapi\\"+fileName;

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			logger.info("Root element: " + doc.getDocumentElement().getNodeName());
			// Get the root element
			Node company = doc.getFirstChild();
			NamedNodeMap attr1 = company.getAttributes();
			NodeList nodeList = doc.getElementsByTagName(TagName);
			// nodeList is not iterable, so we are using for loop
			System.out.println(nodeList.getLength());
			Node node = nodeList.item(nodeCount - 1);
			NamedNodeMap attr2 = node.getAttributes();
			Node nodeDetails = attr2.getNamedItem(nodeName);
			nodeDetails.setTextContent(nodeValue);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			testInfo.log(com.aventstack.extentreports.Status.FAIL, "Not able to execute : " + pce.toString());

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}

	}
	
	
	 
	 public static String[] getEmailListFromCongigValue(String sFile, String sKey) {
		 Properties prop = new Properties();
		 String sValue = null;
		 String[] emailList = null;
		 try {
		 InputStream input = new FileInputStream(sFile+".properties");
		 prop.load(input);
		 sValue = prop.getProperty(sKey);
		 emailList = sValue.split(",");

		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 return emailList;
		 }
	 
	 
	 
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
	 
	 public static Map<String, String>  readAllDataAtOnce(String file)
	 {
	 Map<String, String> apiDetails = new HashMap<>();
	    try {
	        // Create an object of file reader
	        // class with CSV file as a parameter.
	        FileReader filereader = new FileReader(file);
	        
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String line =  null;
	   
	        
	        
	        
	        

	        while((line=br.readLine())!=null){
	            String str[] = line.split(",");
	            for(int i=0;i<str.length;i++){
	                String arr[] = str[i].split(":");
	                apiDetails.put(arr[0].trim(), arr[1].trim());
	            }
	        }
	        System.out.println(apiDetails);
	  

	 }
	    catch(Exception e) {
	     e.printStackTrace();
	    }
	 return apiDetails;
	    }
	 
	 public static void TestServices() throws Exception {
			try {
				// creating a constructor of file class and parsing an XML file
				File file = new File("D:\\LandMarkAutomation\\landmark\\TestData\\QAData\\MaxKsa\\ResponseXmls\\getOrderDetailsResponse.xml");
				// an instance of factory that gives a document builder
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				// an instance of builder to parse the specified xml file
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(file);
				doc.getDocumentElement().normalize();
				logger.info("Root element: " + doc.getDocumentElement().getNodeName());
				NodeList nodeList = doc.getElementsByTagName("Order");
				// nodeList is not iterable, so we are using for loop
				for (int itr = 0; itr < nodeList.getLength(); itr++) {
					Node node = nodeList.item(itr);

					logger.info("\nNode Name :" + node.getNodeName());
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node;
						String testCaseName = eElement.getElementsByTagName("OrderHeaderKey").item(0).getTextContent();

						String baseUrl = eElement.getElementsByTagName("BaseURI").item(0).getTextContent();
						String userName = eElement.getElementsByTagName("UserName").item(0).getTextContent();
						String passWord = eElement.getElementsByTagName("Password").item(0).getTextContent();
						String executeAPI = eElement.getElementsByTagName("ExecuteAPI").item(0).getTextContent();
						String isService = eElement.getElementsByTagName("IsService").item(0).getTextContent();

						String apiName = eElement.getElementsByTagName("Api").item(0).getTextContent();
						String inputDataXmlFilePath = eElement.getElementsByTagName("InputData").item(0).getTextContent();
						String expectedResultFilePath = eElement.getElementsByTagName("ExpectedResult").item(0)
								.getTextContent();
						String ValidationClassName = eElement.getElementsByTagName("ValidationClassName").item(0)
								.getTextContent();
						String genericAttributesvalues = eElement.getElementsByTagName("GenericAttributeValues").item(0).getTextContent();
	                    logger.info("Test case name is" +ValidationClassName );
						Class<?> ValidationClass = Class.forName(ValidationClassName);
						Object obj = ValidationClass.newInstance();
						String methodName = "validateAttributes";
						Class<?>[] paramTypes = {String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class };

				
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("RESTAPI Exception "+TestUtils.exceptionsToString(e));
			}
		}

	 
	 
	 
	 

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

	 

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

	
	public static void copy(File src, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(src);
			os = new FileOutputStream(dest); 
			// buffer size 1K
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = is.read(buf)) > 0) {
				os.write(buf, 0, bytesRead);
			}
		} finally {
			is.close();
			os.close();
		}
	}

}
