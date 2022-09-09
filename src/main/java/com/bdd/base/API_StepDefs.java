package com.bdd.base;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.api.base.API_BaseClass;
import com.api.reporting.ReportFactory;

//import io.cucumber.core.internal.gherkin.ast.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class API_StepDefs {

	@Before
	public void before(Scenario scn) {
		if(ReportFactory.reporting) {
			ReportFactory.StartTest(scn.getName());
			try {
				ReportFactory.SetTestCase(scn);
				ReportFactory.PrintTestCase();
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Collection<String> tags = scn.getSourceTagNames();
			ReportFactory.AssignCategories(tags);
		}	
	}
	@BeforeStep
	public void beforeStep(Scenario scenario) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		if(ReportFactory.reporting) {
			ReportFactory.PrintCurrentStepText();
		}
	}


	@AfterStep
	public void afterStep() {
		if(ReportFactory.reporting) {
			ReportFactory.StepNumber++;
		}
	}

	@Given("Wait for {int} ms([^\\\"]*)")
	public void wait_for_after_placing_order(Integer time_ms) {
		try {
			Thread.sleep(time_ms);
			//Thread.sleep(time_min);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Given("Wait for {int} sec([^\\\"]*)")
	public void wait_for_sec_after_placing_order(Integer time_sec) {
		try {
			Thread.sleep(time_sec*1000);
			//Thread.sleep(time_min);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Given("Wait for {int} min([^\\\"]*)")
	public void wait_for_min_after_placing_order(Integer time_min) {
		try {
			Thread.sleep(time_min*1000*60);
			//Thread.sleep(time_min);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@When("Application calls the {string} API")
	public void application_calls_the_get_ipo_master_api(String API) {
		API_BaseClass.API_Setup(API,API_BaseClass.getConstantsClassObject());
		API_BaseClass.Send_API_Request();
	}

	@When("Application calls the {string} API with invalid JWT {string}")
	public void application_calls_the_with_invalid_JWT(String API, String token) {
		API_BaseClass.API_Setup(API,API_BaseClass.getConstantsClassObject());
		API_BaseClass.addAPIRequestHeader("Authorization", "Bearer "+token);
		API_BaseClass.Send_API_Request(); 
	}
	@When("Application calls the {string} API with parameters {string}")
	public void application_calls_the_API_with_query_parameters(String API, String queryParamValues) {
		API_BaseClass.API_Setup(API,API_BaseClass.getConstantsClassObject());
		API_BaseClass.Send_API_Request(queryParamValues.split(","));  	
	}
	@Then("API response status is {int}")
	public void api_response_status_is(Integer statusCode) {
		API_BaseClass.Validate_APIResponseStatus(statusCode);
	}
	@Then("WS response status is {int}")
	public void ws_response_status_is(Integer statusCode) {
		API_BaseClass.Validate_WebSocketResponseStatus(statusCode);
	}
	@Then("API returns the expected reponse")
	public void api_returns_the_expected_reponse() {
		//API_BaseClass.Validate_APIResponse();
		API_BaseClass.validateJsonSchema();
	}

	@Then("WS Response pong")
	public void ws_response_pong() {
		if(API_BaseClass.getWSResponse().equalsIgnoreCase("pong")) {
			ReportFactory.PassTest("SUCCESS :: WS returned pong");
		} else {
			ReportFactory.FailTest("FAILURE :: WS did not returned pong");
		}
	}
	@Then("API resppnse is null")
	public void api_resppnse_is_null() {
		if(API_BaseClass.getAPIResponse().equalsIgnoreCase("null")) {
			ReportFactory.PassTest("SUCCESS :: API returned the expected reponse.");
		}else {
			ReportFactory.FailTest("FAILURE :: API did not retur the expected reponse.");
		}
	}

	@Then("API reponse contains the expected headers - {string}")
	public void api_reponse_contains_the_expected_headers(String headers) {
		for(String header:headers.split(",")) {
			API_BaseClass.Validate_APIResponseHeader(header);
		}
	}
	@Then("API reponse contains the expected headers")
	public void api_reponse_contains_the_expected_headers() {
		API_BaseClass.Validate_APIResponseHeaders();
	}

	@Then("API reponse contains the expected parameters")
	public void api_reponse_contains_the_expected_paramters() {
		API_BaseClass.Validate_APIResponseParamters();
	}

	@Then("API reponse contains the expected parameters - {string}")
	public void api_reponse_contains_the_expected_paramters(String params) {
		for(String param:params.split(",")) {
			API_BaseClass.Validate_APIResponseBody(param);
		}
	}

	@Then("API repsonse time is below {int} ms")
	public void api_repsonse_time_is_below_ms(int millis) {
		API_BaseClass.Validate_APIResponseTime(millis);
		//ReportFactory.endTest();
		//ReportFactory.EndReport();
	}
	@Then("API returns {string} in reponse")
	public void api_returns_in_reponse(String text) {
		API_BaseClass.Validate_APIResponseBodyContains(text);
	}

	@Then("API returns an error in reponse")
	public void api_return_an_error_in_reponse() {
		API_BaseClass.validateJsonSchema("AMXError_Schema");
	}

	@Then("API returns an error reponse in Reports")
	public void api_return_an_error_reponse_in_reports() {
		API_BaseClass.validateJsonSchema("AMXError_Schema_Null");
	}

	@Then("Symbol Detail API returns error")
	public void api_return_an_error_in_symbol_detail_response() {
		API_BaseClass.validateJsonSchema("AMXError_SymbolDetail");
	}

	@Then("Value of parameter {string} is {string}")
	public void value_of_paramter_is(String parameter, String value) {
		if(API_BaseClass.Validate_APIResponseBody(parameter, value)){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is "+value);
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not "+value);
		}
	}

	@Then("Value of paramter {string} is {string} or {string}")
	public void value_of_paramter_is_or(String parameter, String value1, String value2) {
		if(API_BaseClass.Validate_APIResponseBody(parameter, value1)) {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is "+value1);
		}else if(API_BaseClass.Validate_APIResponseBody(parameter, value2)) {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is "+value2);
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not "+value1+" or "+value2);
		}
	}

	@Then("Value of string parameter {string} is {string}")
	public void value_of_string_parameter_is(String parameter, String value) {
		if(API_BaseClass.AssertParameterValue(parameter, value)){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of boolean parameter {string} is {string}")
	public void value_of_boolean_parameter_is(String parameter, String value) {	
		if(API_BaseClass.AssertParameterValue(parameter, Boolean.valueOf(value))){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of byte parameter {string} is {int}")
	public void value_of_byte_parameter_is(String parameter, Integer value) {
		if(API_BaseClass.AssertParameterValue(parameter, value)){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of integer parameter {string} is {int}")
	public void value_of_integer_parameter_is(String parameter, Integer value) {
		if(API_BaseClass.AssertParameterValue(parameter, value)){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of long parameter {string} is {string}")
	public void value_of_long_parameter_is(String parameter, String value) {
		if(API_BaseClass.AssertParameterValue(parameter, Long.valueOf(value))){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of float parameter {string} is {double}")
	public void value_of_float_parameter_is(String parameter, Double value) {
		if(API_BaseClass.Validate_APIResponseBody(parameter, value)){
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of string parameter {string} is not {string}")
	public void value_of_string_parameter_is_not(String parameter, String value) {
		if(API_BaseClass.AssertParameterValue(parameter, value)){
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is not \""+value+"\"");
		}

	}

	@Then("Value of boolean parameter {string} is not {string}")
	public void value_of_boolean_parameter_is_not(String parameter, String value) {
		if(API_BaseClass.AssertParameterValue(parameter, Boolean.valueOf(value))){
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of integer parameter {string} is not {int}")
	public void value_of_integer_parameter_is_not(String parameter, Integer value) {
		if(API_BaseClass.AssertParameterValue(parameter, value)){
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of float parameter {string} is not {double}")
	public void value_of_float_parameter_is_not(String parameter, Double value) {
		if(API_BaseClass.AssertParameterValue(parameter, value)){
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is \""+value+"\"");
		}else {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is not \""+value+"\"");
		}
	}

	@Then("Value of long parameter {string} is not {string}")
	public void value_of_long_parameter_is_not(String parameter, String value) {
		if(API_BaseClass.AssertParameterValue(parameter, Long.valueOf(value))){
			ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is "+value);
		}else {
			ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is not "+value);
		}
	}



	@Then("Value of parameter {string} is null")
	public void value_of_parameter_is_null(String parameter) {
		API_BaseClass.Validate_APIResponseBody_ParameterIsNull(parameter);
	}

	@Then("Value of parameter {string} is not null")
	public void value_of_parameter_is_not_null(String parameter) {
		API_BaseClass.Validate_APIResponseBody_ParameterIsNotNull(parameter);
	}

	@Then("Value of parameters {string} is not null")
	public void value_of_parameters_is_not_null(String paramList) {
		for(String parameter:paramList.split(",")) {
			API_BaseClass.Validate_APIResponseBody_ParameterIsNotNull(parameter);
		}
	}

	@Then("Value of parameters {string} is not empty")
	public void value_of_parameters_is_not_empty(String paramList) {
		for(String parameter:paramList.split(",")) {
			String value="";
			if(API_BaseClass.AssertParameterValue(parameter, "")){
				ReportFactory.FailTest("FAILURE :: Value of parameter "+parameter+" is \""+value+"\"");
			}else {
				ReportFactory.PassTest("SUCCESS :: Value of parameter "+parameter+" is not \""+value+"\"");
			}
		}
	}


	@After
	public void after() {
		if(ReportFactory.reporting) {
			ReportFactory.endTest();
		}
	}

	@Given("Report is published on slack")
	public void report_is_published_on_slack() {
		ReportFactory.PublishReportOnSlack2();
	}

}
