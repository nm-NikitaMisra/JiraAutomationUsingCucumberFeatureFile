package com.qa.Cucumber_Jira;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class App 
{
	String featureText;
	ArrayList<String> scenarioText;
	ArrayList<ArrayList<String>> scenarioArray;
	static int currentLine;

	@Before
	public void setUp() throws IOException{ 
		File file = new File("C:\\Users\\nikitamisra\\eclipse-workspace\\Cucumber_Jira\\Jira_Features\\JiraSteps.feature"); 
		LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));  
        String st;
        int count=1;
        scenarioText=new ArrayList<String>();
        scenarioArray = new ArrayList<ArrayList<String>>();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>Initial Size"+scenarioArray.size()); 
        int in=0;
        while ((st = lineNumberReader.readLine()) != null)
        {
       	 String temp=st;
			 if(st.startsWith("Feature:"))
			   {
			    this.featureText=st.substring(st.indexOf(':')+1).trim();  
			   }
			  else if(st.startsWith("Scenario:"))
			  {
			    //this.scenarioText=st.substring(st.indexOf(':')+1).trim();
				  scenarioText.add(st.substring(st.indexOf(':')+1).trim());
				  scenarioArray.add(new ArrayList<String>());
			      count++;
			      if(scenarioText.size()>1)
			      {
			    	  in++;
			      }
			  }
			  else if(st.trim().startsWith("Given"))
			  {
				  this.scenarioArray.get(in).add(temp.trim());
			  }
			  else if(st.trim().startsWith("When"))
			  {
				  this.scenarioArray.get(in).add(temp.trim());
			  }
			  else if(st.trim().startsWith("And"))
			  {
				  this.scenarioArray.get(in).add(temp.trim());
			  }
			  else if(st.trim().startsWith("Then"))
			  {
				  this.scenarioArray.get(in).add(temp.trim());
			  }
    	}
//        System.out.println(scenarioArray);
//        System.out.println(scenarioText);
//        System.out.println(scenarioArray.get(0).size());
//        System.out.println(scenarioText.size());
        
        for(int a=0;a<scenarioText.size();a++)
        {
        	
        	RestAssured.baseURI = "http://localhost:8080/rest/api/2/issue";
            String inp="{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n" + "          \"key\": \"HJIUN\"\r\n" + "       },\r\n" + "       \"summary\":"+"\""+featureText+"\",\r\n" +"       \"description\":"+"\""+scenarioText.get(a)+"\",\r\n" + "       \"issuetype\": {\r\n" + "          \"id\": \"10007\"\r\n" + "       }\r\n" + "   }\r\n"+"}";
            Response resp =RestAssured.given()
                .auth().preemptive()
                .basic("nikita", "Star@123")
                .contentType("application/json")
                .body(inp)
                .log().all()
                .post()
                .then().extract().response();
    	       String body=resp.getBody().asString();
    	       JsonPath jp = new JsonPath(body);
    	       String value = jp.getString( "id" );
    	       String issueId=jp.getString("key");
    	       System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+value);
    	       System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+issueId);
    	       
    	       
    	       //System.out.println(value);
    	       

	       RestAssured.baseURI = "http://localhost:8080/rest/zapi/latest/teststep/"+value;
	       for(int i=0;i<(scenarioArray.get(a).size()-1);i++)
	       {
	    	   if(!scenarioArray.get(a).get(i+1).startsWith("Then") && !scenarioArray.get(a).get(i).startsWith("Then"))
	    	   {
	    		   String temp="";
	    		   
		    	   if(scenarioArray.get(a).get(i).startsWith("When"))
	    		   {
		    		   temp=scenarioArray.get(a).get(i).replace("When","").trim();
	    		   }
		    	   if(scenarioArray.get(a).get(i).startsWith("And"))
	    		   {
		    		   temp=scenarioArray.get(a).get(i).replace("And","").trim();
	    		   }
		    	   if(scenarioArray.get(a).get(i).startsWith("Given"))
	    		   {
		    		   temp=scenarioArray.get(a).get(i).replace("Given","").trim();
	    		   }
		    	   String sendValues="{\r\n" + 
			        		"  \"step\":"+"\""+temp+"\",\r\n" + 
			        		"  \"result\":"+"\""+""+"\"\r\n" + 
			        		"}";
			       System.out.println(">>>>>>>>>>>>>>>"+sendValues);
			       RestAssured.given()
			            .auth().preemptive()
			            .basic("nikita", "Star@123")
			            .contentType("application/json")
			            .body(sendValues)
			            .log().all()
			            .post()
			            .then().extract().response();
			       
	    	   }
	    	   else if(scenarioArray.get(a).get(i+1).startsWith("Then"))
	    	   {
	    		   String val1="";
	    		   String val2=scenarioArray.get(a).get(i+1).replace("Then","").trim();
	    		   if(scenarioArray.get(a).get(i).startsWith("When"))
	    		   {
		    		   val1=scenarioArray.get(a).get(i).replace("When","").trim();
	    		   }
		    	   if(scenarioArray.get(a).get(i).startsWith("And"))
	    		   {
		    		   val1=scenarioArray.get(a).get(i).replace("And","").trim();
	    		   }
		    	   if(scenarioArray.get(a).get(i).startsWith("Given"))
	    		   {
		    		   val1=scenarioArray.get(a).get(i).replace("Given","").trim();
	    		   }
	    		   String sendValues2="{\r\n" + 
	        		"  \"step\":"+"\""+val1+"\",\r\n" + 
	        		"  \"result\":"+"\""+val2+"\"\r\n" + 
	        		"}";
			       RestAssured.given()
			            .auth().preemptive()
			            .basic("nikita", "Star@123")
			            .contentType("application/json")
			            .body(sendValues2)
			            .log().all()
			            .post()
			            .then().extract().response();
			       
	    	   }
	       }
	       int last=scenarioArray.get(a).size()-1;
	       if(!scenarioArray.get(a).get(last).startsWith("Then"))
	       {
	    	   String temp="";
    		   
	    	   if(scenarioArray.get(a).get(last).startsWith("When"))
    		   {
	    		   temp=scenarioArray.get(a).get(last).replace("When","").trim();
    		   }
	    	   if(scenarioArray.get(a).get(last).startsWith("And"))
    		   {
	    		   temp=scenarioArray.get(a).get(last).replace("And","").trim();
    		   }
	    	   if(scenarioArray.get(a).get(last).startsWith("Given"))
    		   {
	    		   temp=scenarioArray.get(a).get(last).replace("Given","").trim();
    		   }
	    	   String sendValues="{\r\n" + 
		        		"  \"step\":"+"\""+temp+"\",\r\n" + 
		        		"  \"result\":"+"\""+""+"\"\r\n" + 
		        		"}";
		       System.out.println(">>>>>>>>>>>>>>>"+sendValues);
		       RestAssured.given()
		            .auth().preemptive()
		            .basic("nikita", "Star@123")
		            .contentType("application/json")
		            .body(sendValues)
		            .log().all()
		            .post()
		            .then().extract().response();
	       }
	       
	     //To add comment for each issue
	  	 RestAssured.baseURI = "http://localhost:8080/rest/api/2/issue/"+issueId+"/comment";
         String addComment="{\r\n" + 
         		"    \"body\": \"Test Executed Successfully............\",\r\n" + 
         		"    \"visibility\": {\r\n" + 
         		"        \"type\": \"role\",\r\n" + 
         		"        \"value\": \"Administrators\"\r\n" + 
         		"    }\r\n" + 
         		"}";
         Response respCommentToId =RestAssured.given()
             .auth().preemptive()
             .basic("nikita", "Star@123")
             .contentType("application/json")
             .body(addComment)
             .log().all()
             .post()
             .then().extract().response();
        
           String commentBody=respCommentToId.getBody().asString();
	       JsonPath jsonPa = new JsonPath(commentBody);
	       String id = jsonPa.getString( "id" );
           
         
         //To update comment for each issue
         RestAssured.baseURI = "http://localhost:8080/rest/api/2/issue/"+issueId+"/comment/"+id;
         String updateComment="{\r\n" + 
         		"    \"body\": \"................happy birthday.........\",\r\n" + 
         		"    \"visibility\": {\r\n" + 
         		"        \"type\": \"role\",\r\n" + 
         		"        \"value\": \"Administrators\"\r\n" + 
         		"    }\r\n" + 
         		"}";
         Response resUpdateComment =RestAssured.given()
             .auth().preemptive()
             .basic("nikita", "Star@123")
             .contentType("application/json")
             .body(updateComment)
             .log().all()
             .put()
             .then().extract().response();
    }
       
       
        
        
		RestAssured.baseURI = "http://localhost:8080/rest/zapi/latest/cycle";
        //String inp="{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n" + "          \"key\": \"HJIUN\"\r\n" + "       },\r\n" + "       \"summary\":"+"\""+featureText+"\",\r\n" +"       \"description\":"+"\""+scenarioText.get(a)+"\",\r\n" + "       \"issuetype\": {\r\n" + "          \"id\": \"10007\"\r\n" + "       }\r\n" + "   }\r\n"+"}";
        String cycleInput="{\r\n" + 
        		"\r\n" + 
        		"  \"clonedCycleId\": \"\",\r\n" + 
        		"  \"name\": \"nschedule for second test cycle\",\r\n" + 
        		"  \"build\": \"\",\r\n" + 
        		"  \"environment\": \"\",\r\n" + 
        		"  \"description\": \"Create cycle with sprint\",\r\n" + 
        		" \"startDate\": \"4/Dec/19\",\r\n" + 
        		"  \"endDate\": \"30/Dec/20\",\r\n" + 
        		"  \"projectId\": \"10000\",\r\n" + 
        		"  \"versionId\": \"-1\"\r\n" + 
        		"\r\n" + 
        		"}\r\n" + 
        		"";
        Response respCycle =RestAssured.given()
            .auth().preemptive()
            .basic("nikita", "Star@123")
            .contentType("application/json")
            .body(cycleInput)
            .log().all()
            .post()
            .then().extract().response();
        RestAssured.baseURI = "http://localhost:8080/rest/zapi/latest/execution/addTestsToCycle";
        
        //Add test to cycle API
        String addTestBody="{\"issues\":[\"HJIUN-102\"],\"versionId\":-1,\"cycleId\":1,\"projectId\":10001,\"method\":\"1\"}";
        Response respAddTest =RestAssured.given()
            .auth().preemptive()
            .basic("nikita", "Star@123")
            .contentType("application/json")
            .body(addTestBody)
            .log().all()
            .post()
            .then().extract().response();
        String execBody=respAddTest.getBody().asString();
	       JsonPath jsP = new JsonPath(execBody);
	       //String id = jsP.getString( "id" );
	       //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>execution_id<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n"+id);
} 
		@Given("^Open Jira Board$")
		public void open_Jira_Board(){				 
		}
		@When("^Enter the Username and Password$")
		public void enter_the_Username_and_Password(){
	       }
		@When("^User clicks login fbg$")
		public void user_clicks_login_fbg(){    
		}
		@When("^User clicks logout fbg$")
		public void user_clicks_logout_fbg(){ 
		}
		@Then("^Login the credentials$")
		public void login_the_credentials(){    
		}	
}

