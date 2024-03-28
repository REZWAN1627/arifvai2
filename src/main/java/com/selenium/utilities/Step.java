package com.selenium.utilities;

public class Step {
	// Test Environment
	public String applicationName;
	public String url;
	public String execute;
	public String browser;
	public String objectRepository;
	public String platform;
	public String appVersion;
	
	// Test Case
	public String testCaseID;
	public String testCaseName;
	public String executeTestCase;
	public String dataSet;
	public String dataSetDetails;
	
	// Test Steps
	public String step;
	public String testComponent;
	public String page;
	public String element;
	public String action;
	//public String defaultValue;
	public String param1;
	public String param2;
	public String param3;
	public String param4;

	public Step(String applicationName, String url
			, String execute, String browser
			, String objectRepository, String platform
			, String appVersion) {
		this.applicationName = applicationName;
		this.url = url;
		this.execute = execute;
		this.browser = browser;
		this.objectRepository = objectRepository;
		this.platform = platform;
		this.appVersion = appVersion;
	}
	
	public Step(String testCaseID, String testCaseName
			, String executeTestCase, String dataSet
			, String dataSetDetails) {
		this.testCaseID = testCaseID;
		this.testCaseName = testCaseName;
		this.executeTestCase = executeTestCase;
		this.dataSet = dataSet;
		this.dataSetDetails = dataSetDetails;
	}
	
	public Step(String step, String testComponent
			, String page, String element
			, String action //, String defaultValue
			, String param1, String param2
			, String param3, String param4) {
		this.step = step;
		this.testComponent = testComponent;
		this.page = page;
		this.element = element;
		this.action = action;
		//this.defaultValue = defaultValue;
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
	}

}
