package com.outwit.testexecution;

import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UpdateXML {

    public static void replaceXML(String attribute, String oldValue, String newValue, String resourcePath)
            throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        Document input = factory.newDocumentBuilder().parse(resourcePath);

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        String expr = String.format("//* [contains (@%s, '%s')]", attribute, oldValue);
        NodeList nodes = (NodeList) xpath.evaluate(expr, input, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            Element value = (Element) nodes.item(i);
            value.setAttribute(attribute, newValue);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer output = new StringWriter();
        transformer.transform(new DOMSource(input), new StreamResult(output));
        
        BufferedWriter writer = FileOperations.writeXML("./XML_files/createOrder.xml");
		writer.write(output.toString());
		writer.close();

        // Print or write the transformed content to a file
        //System.out.println(output.toString());
    }

    private static int generateUniqueNumber(Set<Integer> usedNumbers, Random random) {
        while (true) {
            int num = random.nextInt(100);
            if (!usedNumbers.contains(num)) {
                usedNumbers.add(num);
                return num;
            }
        }
    }

    public static void updateOrderNO() throws Exception {
        Set<Integer> usedUniqueNumbers = new HashSet<>();
        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        for (int i = 0; i < 1; i++) {
            Date now = new Date();
            int uniqueNumber = generateUniqueNumber(usedUniqueNumbers, random);
            String newOrderNumber = "Test_" + dateFormat.format(now) + String.format("%02d", uniqueNumber);

            String oldOrderNo = FileOperations.XMLXpathReader("./XML_files/createOrder.xml",
                    "//Order/@OrderNo");

            replaceXML("OrderNo", oldOrderNo, newOrderNumber, "./XML_files/createOrder.xml");
        }
    }

	
}
