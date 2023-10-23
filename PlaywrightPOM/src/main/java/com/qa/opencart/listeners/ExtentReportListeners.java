package com.qa.opencart.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.opencart.factory.PlaywrightFactory;

import freemarker.core._CoreStringUtils;

public class ExtentReportListeners implements ITestListener{
private static final String OUTPUT_FOLDER=".//build//";
private static final String FILE_NAME="TestExcecutionReport.html";

private static ExtentReports extent=init();
public static ThreadLocal<ExtentTest>extentTest=new ThreadLocal<ExtentTest>();
private static ExtentReports extentReports;
public static ExtentReports init() {
	Path path=Paths.get(OUTPUT_FOLDER);
	if(!Files.exists(path)) {
		try{
			Files.createDirectories(path);
		}catch (IOException e) {
			e.printStackTrace();
	}
}
extentReports=new ExtentReports();
ExtentSparkReporter reporter=new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
reporter.config().setReportName("Open Cart Automation Test Results");
extentReports.attachReporter(reporter);
extentReports.setSystemInfo("System", "Mac");
extentReports.setSystemInfo("Author", "Mohan");
extentReports.setSystemInfo("Build", "1.1");
extentReports.setSystemInfo("Team", "OMS");
extentReports.setSystemInfo("Customer Name", "NAL");
//extentReports.setSystemInfo("ENV NAME",System.getProperty("env"));
return extentReports;
}
@Override
public synchronized void onStart(ITestContext context) {
	System.out.println("Test Suite Started");
}

@Override
public synchronized void onFinish(ITestContext context) {
	System.out.println("Test Suite is Ending....");
	extent.flush();
	extentTest.remove();
}

@Override
public synchronized void onTestStart(ITestResult result) {
	String methodName=result.getMethod().getMethodName();
	String qualifiedName=result.getMethod().getQualifiedName();
	int last=qualifiedName.lastIndexOf(".");
	int mid=qualifiedName.substring(0,last).    lastIndexOf(".");
	String className=qualifiedName.substring(mid+1,last);
	
	System.out.println(methodName+"Started...");
	ExtentTest extentTest1=extent.createTest(result.getMethod().getMethodName());
	result.getMethod().getDescription();
	
	extentTest1.assignCategory(result.getTestContext().getSuite().getName());
	//methodName=StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(methodName),StringUtils.SPACE));
extentTest1.assignCategory(className);
extentTest.set(extentTest1); 
extentTest.get().getModel().setStartTime(getTime(result.getStartMillis()));

}
public synchronized void onTestSuccess(ITestResult result) {
	System.out.println(result.getMethod().getMethodName() + "Passed");
	extentTest.get().pass("Test Paased");
	extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));

}

public synchronized void onTestFailure(ITestResult result) {
	System.out.println(result.getMethod().getMethodName() + "Failed");
System.out.println(result.getMethod().getMethodName());
extentTest.get().fail(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(PlaywrightFactory. takenScreenShot()).build());
extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));

}
public synchronized void onTestSkipped(ITestResult result) {
	System.out.println(result.getMethod().getMethodName() + "Skipped");
	extentTest.get().skip(result.getThrowable(),MediaEntityBuilder.createScreenCaptureFromPath(PlaywrightFactory.takenScreenShot()).build());
	extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));

}
public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	System.out.println(result.getMethod().getMethodName() + "onTestFailedButWithinSuccessPercentage");
}
private Date getTime(long millis) {
	Calendar calendar=Calendar.getInstance();
	calendar.setTimeInMillis(millis);
	return calendar.getTime();
}

}