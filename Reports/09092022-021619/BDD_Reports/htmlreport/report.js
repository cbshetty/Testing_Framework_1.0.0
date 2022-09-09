$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/Sample.feature");
formatter.feature({
  "name": "Title of your feature",
  "description": "  I want to use this template for my feature file",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@tag"
    }
  ]
});
formatter.scenario({
  "name": "Title of your scenario 1",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@tag"
    },
    {
      "name": "@tag1"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "sample step",
  "keyword": "Given "
});
formatter.match({
  "location": "com.bdd.base.Sample_StepDef.sample_step()"
});
formatter.result({
  "status": "passed"
});
formatter.afterstep({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "Application calls the \"Sample\" API",
  "keyword": "When "
});
formatter.match({
  "location": "com.bdd.base.API_StepDefs.application_calls_the_get_ipo_master_api(java.lang.String)"
});
formatter.result({
  "error_message": "java.lang.RuntimeException\r\n\tat com.api.base.API_BaseClass.Send_API_Request(API_BaseClass.java:645)\r\n\tat com.api.base.API_BaseClass.Send_API_GET_Request(API_BaseClass.java:839)\r\n\tat com.api.base.API_BaseClass.Send_API_Request(API_BaseClass.java:1673)\r\n\tat com.bdd.base.API_StepDefs.application_calls_the_get_ipo_master_api(API_StepDefs.java:90)\r\n\tat ✽.Application calls the \"Sample\" API(file:///C:/Users/Chandrakant.Shetty/eclipse-workspace/API_Testing_Framework/src/test/resources/features/Sample.feature:8)\r\n",
  "status": "failed"
});
formatter.afterstep({
  "status": "passed"
});
formatter.beforestep({
  "status": "skipped"
});
formatter.step({
  "name": "API response status is 200",
  "keyword": "Then "
});
formatter.match({
  "location": "com.bdd.base.API_StepDefs.api_response_status_is(java.lang.Integer)"
});
formatter.result({
  "status": "skipped"
});
formatter.afterstep({
  "status": "skipped"
});
formatter.after({
  "error_message": "java.lang.RuntimeException\r\n\tat com.api.reporting.ReportFactory.endTest(ReportFactory.java:213)\r\n\tat com.bdd.base.API_StepDefs.after(API_StepDefs.java:339)\r\n",
  "status": "failed"
});
});