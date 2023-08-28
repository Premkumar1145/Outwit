package com.outwit.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	/*
	 * private static ExtentReports instance;
	 * 
	 * public static synchronized ExtentReports getInstance() { if (instance ==
	 * null) { System.out.println(System.getProperty("user.dir")); instance =
	 * new ExtentReports();
	 * 
	 * }
	 * 
	 * return instance; }
	 */

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance("test-output/Extent.html");

		return extent;
	}

	public static ExtentReports createInstance(String fileName) {
		String fileName1 = "./ExtentReport/" + fileName;
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName1);

		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
		htmlReporter.config().setReportName("Joann Sanity Test");

		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		return extent;
	}

}
