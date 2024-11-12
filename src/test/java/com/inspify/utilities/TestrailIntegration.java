package com.inspify.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

public class TestrailIntegration {
	
	public static void addResultForTestCase(String testCaseID, int status, String testCaseComments) throws  IOException, APIException  {
		
		ConfigManager app = new ConfigManager("App");
		
		String RUN_ID = app.getProperty("TEST_RUN_ID");
		String USERNAME = app.getProperty("TESTRAIL_USERNAME");
		String PASSWORD = app.getProperty("TESTRAIL_PASSWORD");
		String TESTRAIL_URL = app.getProperty("RAILS_ENGINE_URL");
	
		APIClient client = new APIClient(TESTRAIL_URL);
		client.setUser(USERNAME);
	    client.setPassword(PASSWORD);
	    
		Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", testCaseComments);
        client.sendPost("add_result_for_case/"+RUN_ID+"/"+testCaseID+"",data );
	} 

}
