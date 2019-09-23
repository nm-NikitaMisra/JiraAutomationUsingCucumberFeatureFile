Feature:              Reset functionality on login page of Application 
Scenario:              Verification of Reset button 
Given Open Jira Board
When Enter the Username and Password			
And User clicks login fbg
And User clicks logout fbg
Then Login the credentials
And User clicks login fbg
And User clicks login fbg
When Enter the Username and Password
Then Login the credentials
Then Login the credentials
Then Login the credentials

#Scenario:              Verification of Reset button
#Given Open Jira Board
#When Enter the Username and Password			
#And User clicks login fbg
#And User clicks logout fbg
#Then Login the credentials
#And User clicks login fbg
#And User clicks login fbg
#When Enter the Username and Password
#Then Login the credentials
#Then Login the credentials
#Then Login the credentials