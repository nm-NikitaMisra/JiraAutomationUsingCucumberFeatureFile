package com.test.Cucumber_Jira;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.junit.Cucumber;
import cucumber.api.Scenario;


import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;
	
@RunWith(Cucumber.class)				
@CucumberOptions(
		features="C:\\Users\\nikitamisra\\eclipse-workspace\\Cucumber_Jira\\Jira_Features\\JiraSteps.feature",
		glue={"C:\\Users\\nikitamisra\\eclipse-workspace\\Cucumber_Jira\\src\\main\\java\\com\\qait\\Cucumber_Jira\\App.java","C:\\Users\\nikitamisra\\eclipse-workspace\\Cucumber_Jira\\src\\test\\java\\com\\qait\\Cucumber_Jira\\AppTest.java"}
)	

public class AppTest
    
{

	@Test
	public void submitForm() {
	
    }
}


