package com.NEU.INFO7390;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoanDecisionController {

	@RequestMapping(value={"getLoanDecision"}, method = RequestMethod.GET)
	public String loanDecision(HttpServletRequest request, HttpServletRequest response) {
		String decisionLabel= "", decisionProbability= "" ;
		HttpSession session = request.getSession();
		String loanAmount = request.getParameter("loan_amount");
		String riskScore = request.getParameter("riskScore");
		String dti = request.getParameter("dti");
		String employmentLength = request.getParameter("employmentLength");
		String state = request.getParameter("state");
		String emailID = request.getParameter("emailID");


		Double loanAmountDbl=0.00, dtiDbl=0.00;
		int riskScoreInt=0, employmentLengthInt=0;
		try{
			loanAmountDbl = Double.parseDouble(loanAmount);
			dtiDbl = Double.parseDouble(dti);
			riskScoreInt = Integer.parseInt(riskScore);
			employmentLengthInt = Integer.parseInt(employmentLength);
		}catch(Exception e){
			response.setAttribute("error", "Please enter valid loan amount");
			return "home";
		}

		/**
		 * Download full code from github - [https://github.com/nk773/AzureML_RRSApp](https://github.com/nk773/AzureML_RRSApp)
		 */
		/**
		 * Call REST API for retrieving prediction from Azure ML 
		 * @return response from the REST API
		 */    


		HttpPost post;
		HttpClient client;
		StringEntity entity;


		String apiurl = "https://ussouthcentral.services.azureml.net/workspaces/8c0ba32b88114d858c39aa985aa29919/services/73315fd62a7a4b2ebed3afcbaf3f31e5/execute?api-version=2.0&details=true";
		String apikey = "VedbnBQFh1jl1+Mcv2QroyUWqZzIgMwKl7DMpFv3+joPqEUwdnHpC8OHZQVwGOltkDWDcKNsoM+sB2bS9eGAMQ==";


		JSONObject obj = new JSONObject();
		JSONObject inputs = new JSONObject();
		JSONObject input = new JSONObject();

		JSONArray columnName = new JSONArray();
		columnName.add("Amount_Requested");
		columnName.add("Risk_Score");
		columnName.add("Debt-To-Income_Ratio");
		columnName.add("Employment_Length");

		JSONArray allValues = new JSONArray();
		JSONArray value = new JSONArray();
		value.add(loanAmountDbl);
		value.add(riskScoreInt);
		value.add(dtiDbl);
		value.add(employmentLengthInt);


		allValues.add(value);
		//		allValues.add(value2);

		input.put("ColumnNames", columnName);
		input.put("Values", allValues);
		inputs.put("input1", input);
		obj.put("Inputs", inputs);


		//converting json to string
		String jsonBody = obj.toString(); 


		try {
			// create HttpPost and HttpClient object

			post = new HttpPost(apiurl);
			client = HttpClientBuilder.create().build();
			// setup output message by copying JSON body into 
			// apache StringEntity object along with content type
			entity = new StringEntity(jsonBody, HTTP.UTF_8);
			entity.setContentEncoding(HTTP.UTF_8);
			entity.setContentType("text/json");

			// add HTTP headers
			post.setHeader("Accept", "text/json");
			post.setHeader("Accept-Charset", "UTF-8");

			// set Authorization header based on the API key
			post.setHeader("Authorization", ("Bearer "+apikey));
			post.setEntity(entity);


			// Call REST API and retrieve response content
			HttpResponse authResponse = client.execute(post);
			String result= EntityUtils.toString(authResponse.getEntity());
			JSONParser parser = new JSONParser();
			Object obj3 = parser.parse(result);
			JSONObject obj4 = (JSONObject) obj3;

			JSONObject results = (JSONObject)obj4.get("Results");
			JSONObject output1 = (JSONObject)results.get("output1");
			JSONObject values = (JSONObject)output1.get("value");
			
			JSONArray valuesArray = (JSONArray)values.get("Values");
			JSONArray firstValuesArray = (JSONArray) valuesArray.get(0);
			decisionLabel = (String)firstValuesArray.get(4);
			decisionProbability = (String)firstValuesArray.get(5);


		}
		catch (Exception e) {
			System.out.println("in catch");
			System.out.println(e.toString()); 
			response.setAttribute("error", "Error with API. Please try again" );
			return "home";
		}

		response.setAttribute("decisionLabel", decisionLabel);
		response.setAttribute("decisionProbability", decisionProbability);
		response.setAttribute("loanAmount", loanAmountDbl);
		response.setAttribute("dti", dtiDbl);
		response.setAttribute("riskScore", riskScoreInt);
		response.setAttribute("employmentLength", employmentLengthInt);
		
		System.out.println(decisionLabel);
		
		return "loanDecisionOP";
	}
}
