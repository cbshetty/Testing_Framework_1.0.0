package com.bdd.base;

import java.util.Date;
import java.util.List;

import com.api.base.API_BaseClass;
import com.api.reporting.ReportFactory;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WS_API_StepDef {

	@Then("Date type of parameter {string} is {string}")
	public void date_type_of_parameter_is(String paramPath, String paramType) {
		API_BaseClass.validateJsonParamterType(paramPath, paramType);
	}

	@Then("Websocket API returns the expected reponse")
	public void websocket_API_returns_the_expected_reponse() {
		API_BaseClass.validateJsonSchema();
	}

	@Then("Websocket API returns the expected parameters")
	public void websocket_API_returns_the_expected_parameters() {
		API_BaseClass.Validate_WS_APIResponseParameters();	
		//API_BaseClass.Validate_WS_APIResponseParameterTypes();	
	}

	@Then("Appilication pings the websocket connection")
	public void appilication_pings_the_websocket_connection() {
		API_BaseClass.WS_Ping();
		System.out.println(API_BaseClass.getAPIReponseStringList());
		System.out.println(API_BaseClass.getAPIReponseObjectList());
		System.out.println(API_BaseClass.getAPIReponsePongList());
	}

	@Then("API response is {string}")
	public void api_response_is(String ExpResponse) {
		String actResponse = API_BaseClass.getAPIResponse();
		if(actResponse.equals(ExpResponse)) {
			ReportFactory.PassTest("SUCCESS :: API returned the expected response");
		}else {
			ReportFactory.FailTest("FAILURE :: API did notreturn the expected response");
		}

	}

	@When("Applications listens to the Websocket connection")
	public void applications_listens_to_the_Websocket_connection() {
		API_BaseClass.WS_Listen();
		System.out.println(API_BaseClass.getAPIReponseStringList());
		System.out.println(API_BaseClass.getAPIReponseObjectList());
		System.out.println(API_BaseClass.getAPIReponsePongList());
	}

	@When("Applications sends message {string} and listens to the Websocket connection")
	public void applications_sends_message_and_listens_to_the_Websocket_connection(String message) {
		API_BaseClass.WS_Listen(message);
		System.out.println(API_BaseClass.getAPIReponseStringList());
		System.out.println(API_BaseClass.getAPIReponseObjectList());
		System.out.println(API_BaseClass.getAPIReponsePongList());
	}

	@When("Applications sends message and listens to the Websocket connection")
	public void applications_sends_message_and_listens_to_the_Websocket_connection() {
		API_BaseClass.WS_Listen();
		System.out.println(API_BaseClass.getAPIReponseStringList());
		System.out.println(API_BaseClass.getAPIReponseObjectList());
		System.out.println(API_BaseClass.getAPIReponsePongList());
	}

	@Then("{int} message\\(s) recieved in {int} seconds")
	public void message_s_recieved_in_seconds(Integer messageCount, Integer timeInSeconds) {
		List<Long> timestamps  = API_BaseClass.getAPIResponseTimeStamps();
		if(timestamps.size()<(messageCount+1)) {
			ReportFactory.FailTest("FAILURE :: Failed to recieve "+messageCount+" message(s) in 30 sec");
		}else {
			long start = timestamps.get(0);
			long end = timestamps.get(messageCount);
			long timeElapsed = end-start;
			if(timeElapsed<=(timeInSeconds*1000)) {
				ReportFactory.PassTest("SUCCESS :: "+messageCount+" message(s) recieved in "+timeElapsed+" ms(< threshold of "+(timeInSeconds*1000)+" ms)");
			}else {
				ReportFactory.FailTest("FAILURE :: "+messageCount+" message(s) recieved in "+timeElapsed+" ms(> threshold of "+(timeInSeconds*1000)+" ms)");
			}
		}
	}

	@Then("Display last {int} responses of Websocket API")
	public void display_last_responses_of_Websocket_API(Integer count) {
		int size=0;
		if(size<count)
			if(API_BaseClass.getAPIReponseStringList().size()>=count) {
				size = API_BaseClass.getAPIReponseObjectList().size();
				for(int i=1;i<=count;i++) {
					ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+API_BaseClass.getAPIReponseStringList().get(size-i)+"</font></details></a>");
				} 	
			}else if(API_BaseClass.getAPIReponseStringList().size()>0) {
				size = API_BaseClass.getAPIReponseObjectList().size();
				ReportFactory.testInfo("INFO :: only "+size+" response(s) recieved");
				for(int i=1;i<=size;i++) {
					ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+API_BaseClass.getAPIReponseStringList().get(size-i)+"</font></details></a>");
				} 
			}
		if(API_BaseClass.getAPIReponseObjectList().size()>=count) {
			size = API_BaseClass.getAPIReponseObjectList().size();
			for(int i=1;i<=count;i++) {
				Object o = API_BaseClass.getAPIReponseObjectList().get(size-i);
				ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+API_BaseClass.getJSONStringFromBinary(o)+"</font></details></a>");
			}     	
		}else if(API_BaseClass.getAPIReponseObjectList().size()>0) {
			size = API_BaseClass.getAPIReponseObjectList().size();
			ReportFactory.testInfo("INFO :: only "+size+" response(s) recieved");
			for(int i=1;i<=size;i++) {
				Object o = API_BaseClass.getAPIReponseObjectList().get(size-i);
				ReportFactory.testInfo("<a><details><summary>Response "+i+"</summary><font color=black>"+API_BaseClass.getJSONStringFromBinary(o)+"</font></details></a>");
			} 
		}

	}

	@Then("Application disconnects the websocket API")
	public void application_disconnects_the_websocket_API() {
		API_BaseClass.Disconnect_WS_API();

	}

	@Then("API response status is {string}")
	public void api_response_status_is(String statusCode) {
		API_BaseClass.Validate_APIResponseStatus(statusCode);
	}


}
