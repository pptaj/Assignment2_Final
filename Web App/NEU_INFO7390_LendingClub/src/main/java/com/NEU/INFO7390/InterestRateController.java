package com.NEU.INFO7390;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
public class InterestRateController {
	HttpPost post;
	HttpClient client;
	StringEntity entity;
	@RequestMapping(value={"getInterestRate"}, method = RequestMethod.POST)
	public String loanDecision(HttpServletRequest request, HttpServletRequest response) {
		int term = 36 , inq_last_6mths=0, 
				total_acc = 24 , 
				tot_cur_bal = 15000, 
				avg_fico_range =700,
				verification_status_nmbr=2,
				purpose_nmbr=2,
				addr_state_factor=4;
		
		HttpSession session = request.getSession();
		String loanAmount = request.getParameter("loan_amount");
		String riskScoreStr = request.getParameter("riskScore");
		String dtiStr = request.getParameter("dti");
		String employmentLength = request.getParameter("employmentLength");
		
		String termStr = request.getParameter("loan_term");
		String inq_last_6mthsStr = request.getParameter("inq_last_6mths");
		String total_accStr = request.getParameter("total_acc");
		String tot_cur_balStr = request.getParameter("tot_cur_bal");
		String avg_fico_rangeStr = request.getParameter("avg_fico_range");
		String purpose_nmbrStr = request.getParameter("purpose_nmbr");
		String stateStr = request.getParameter("state");
		String verification_status = request.getParameter("verification_status");
		
		Double loan_amnt=0.00, dti=0.00;
		int risk_score=0, emp_length=0;
		try{
			loan_amnt = Double.parseDouble(loanAmount);
		}catch(Exception e){
			response.setAttribute("error1", "Please enter valid loan amount");
			return "home";
		}
		try{
			dti = Double.parseDouble(dtiStr);
		}catch(Exception e){
			response.setAttribute("error2", "Please enter valid dti");
			return "home";
		}
		try{
			risk_score = Integer.parseInt(riskScoreStr);
		}catch(Exception e){
			response.setAttribute("error3", "Please enter valid risk score");
			return "home";
		}
		try{
			term = Integer.parseInt(termStr);
		}catch(Exception e){
			response.setAttribute("error4", "Please enter valid term");
			return "home";
		}
		try{
			inq_last_6mths = Integer.parseInt(inq_last_6mthsStr);
		}catch(Exception e){
			response.setAttribute("error5", "Please enter valid value");
			return "home";
		}
		try{
			total_acc = Integer.parseInt(total_accStr);
		}catch(Exception e){
			response.setAttribute("error6", "Please enter valid value");
			return "home";
		}
		try{
			tot_cur_bal = Integer.parseInt(tot_cur_balStr);
		}catch(Exception e){
			response.setAttribute("error7", "Please enter valid value");
			return "home";
		}
		try{
			avg_fico_range = Integer.parseInt(avg_fico_rangeStr);
		}catch(Exception e){
			response.setAttribute("error8", "Please enter valid value");
			return "home";
		}
		try{
			verification_status_nmbr = Integer.parseInt(verification_status);
		}catch(Exception e){
			response.setAttribute("error9", "Please enter valid value");
			return "home";
		}
		try{
			purpose_nmbr = Integer.parseInt(purpose_nmbrStr);
		}catch(Exception e){
			response.setAttribute("error10", "Please enter valid value");
			return "home";
		}
		try{
			addr_state_factor = Integer.parseInt(stateStr);
		}catch(Exception e){
			response.setAttribute("error11", "Please enter valid value");
			return "home";
		}





		//				pub_rec = 0, revol_bal = 15000, total_rev_hi_lim = 3000, acc_open_past_24mths = 3,delinq_amnt =0, mo_sin_old_il_acct = 100,
		//				mo_sin_old_rev_tl_op =120 , mo_sin_rcnt_rev_tl_op =3, mo_sin_rcnt_tl = 3, mort_acc =1, mths_since_recent_bc=20, mths_since_recent_bc_dlq =4,
		//				mths_since_recent_revol_delinq = 11,  num_accts_ever_120_pd = 0, num_actv_bc_tl=3, num_actv_rev_tl=3, num_bc_sats=3, num_bc_tl=4, num_il_tl=8, num_op_rev_tl=8,


		/**	
		 * Download full code from github - [https://github.com/nk773/AzureML_RRSApp](https://github.com/nk773/AzureML_RRSApp)
		 */
		/**
		 * Call REST API for retrieving prediction from Azure ML 
		 * @return response from the REST API
		 */    


		


		


		JSONObject obj = new JSONObject();
		JSONObject inputs = new JSONObject();
		JSONObject input = new JSONObject();
		JSONArray columnName = new JSONArray();
		columnName.add("loan_amnt");
		columnName.add("dti");
		columnName.add("risk_score");
		columnName.add("term");
		columnName.add("inq_last_6mths");
		columnName.add("total_acc");
		columnName.add("tot_cur_bal");
		columnName.add("verification_status_nmbr");
		columnName.add("avg_fico_range");
		columnName.add("purpose_nmbr");
		columnName.add("addr_state_factor");
		JSONArray allValues = new JSONArray();
		JSONArray value = new JSONArray();
		value.add(loan_amnt);
		value.add(dti);
		value.add(risk_score);
		value.add(term);
		value.add(inq_last_6mths);
		value.add(total_acc);
		value.add(tot_cur_bal);
		value.add(verification_status_nmbr);
		value.add(avg_fico_range);
		value.add(purpose_nmbr);
		value.add(addr_state_factor);

		//		JSONArray value2 = new JSONArray();
		//		value2.add(loanAmountDbl);
		//		value2.add(riskScoreInt);
		//		value2.add(dtiDbl);
		//		value2.add(employmentLengthInt);

		allValues.add(value);
		//		allValues.add(value2);

		input.put("ColumnNames", columnName);
		input.put("Values", allValues);
		inputs.put("input1", input);
		obj.put("Inputs", inputs);

		//converting json to string
		String jsonBody = obj.toString(); 
		String interestRateFromAPIWithNoCluster = "3.0";
		response.setAttribute("loanAmount", loan_amnt);
//		________________
//		Get reults from prediction API with no clusters
		String apiurl = "https://ussouthcentral.services.azureml.net/workspaces/8c0ba32b88114d858c39aa985aa29919/services/cbbf5667cfdb4c20aca9e803febf88e2/execute?api-version=2.0&details=true";
		String apikey = "SQ1z+qcsujoKHMlQB6+t0+eQNIRBEOQbxu9cBf5rV6OSaj15BMRF+dsN6eX286gnxBVR09jCVfRBPb6otOR8JQ==";

		ArrayList list = predictionWithNoCluster(apiurl, apikey, jsonBody);
		interestRateFromAPIWithNoCluster = (String)list.get(0);
		String deviationValueFromAPIWithNoCluster = (String)list.get(1);
		
		response.setAttribute("interestRate_NoCluster", interestRateFromAPIWithNoCluster);
		response.setAttribute("decisionProbability_NoCluster", deviationValueFromAPIWithNoCluster);
//		________________
		
		
//		________________
//		Get reults from clustering API
		apiurl = "https://ussouthcentral.services.azureml.net/workspaces/a03e4f551ee949ed8fc7ebd0762eab67/services/781f0718e251437d8f500c4ad9466428/execute?api-version=2.0&details=true";
		apikey = "dGNpo5ZnHDKyeANsTr2ed9/OxyXbCCULFFterW/eb8UgXeMiq0Tsjsvydr/Sxa3Tlliddi2Tn+TBst5t7pRioQ==";

		ArrayList list2 = predictionWithNoCluster(apiurl, apikey, jsonBody);
		String clusterNumber = (String)list2.get(0);
		String clusterProbability = (String)list2.get(1);
		System.out.println("Cluster Number : " + clusterNumber);
		response.setAttribute("ClusterNumber", clusterNumber);
//		response.setAttribute("decisionProbability_NoCluster", deviationValueFromAPIWithNoCluster);
//		________________
		String interestRateCluster = "3.1", deviationValueCluster0 = "";
		/*
//		________________
//		Get reults from Prediction API for Cluster 0
		
		if(clusterNumber.equals("0")){
			apiurl = "https://ussouthcentral.services.azureml.net/workspaces/a03e4f551ee949ed8fc7ebd0762eab67/services/7579b3358ad24d2ab22e55f7ed4fb29a/execute?api-version=2.0&details=true";
			apikey = "bnKDCK4B/wRq9r5vosZHsFenz9pl+hHSTYCNC2DstDG9ldQzAlkY0nQPh4Nm3MloKQeqI53ividAlbULx5TvkA==";

			ArrayList list3 = predictionWithNoCluster(apiurl, apikey, jsonBody);
			interestRateCluster = (String)list3.get(0);
			deviationValueCluster0 = (String)list3.get(1);
			System.out.println("Interest Rate from kmeans clustering : " + interestRateCluster);
			response.setAttribute("interestRate_kmeans", interestRateCluster);

		}
		
//		________________
//		Get reults from Prediction API for Cluster 1
		String deviationValueCluster1 = "";
		if(clusterNumber.equals("1")){
			apiurl = "https://ussouthcentral.services.azureml.net/workspaces/a03e4f551ee949ed8fc7ebd0762eab67/services/781f0718e251437d8f500c4ad9466428/execute?api-version=2.0&details=true";
			apikey = "dGNpo5ZnHDKyeANsTr2ed9/OxyXbCCULFFterW/eb8UgXeMiq0Tsjsvydr/Sxa3Tlliddi2Tn+TBst5t7pRioQ==";

			ArrayList list4 = predictionWithNoCluster(apiurl, apikey, jsonBody);
			interestRateCluster = (String)list4.get(0);
			deviationValueCluster1 = (String)list4.get(1);
			System.out.println("Interest Rate from kmeans clustering : " + interestRateCluster);
			response.setAttribute("interestRate_kmeans", interestRateCluster);

		}
		
		
//		________________
		
		
//		Get reults from Prediction API for Cluster 2
		String deviationValueCluster2 = "";
		if(clusterNumber.equals("2")){
			apiurl = "https://ussouthcentral.services.azureml.net/workspaces/a03e4f551ee949ed8fc7ebd0762eab67/services/781f0718e251437d8f500c4ad9466428/execute?api-version=2.0&details=true";
			apikey = "dGNpo5ZnHDKyeANsTr2ed9/OxyXbCCULFFterW/eb8UgXeMiq0Tsjsvydr/Sxa3Tlliddi2Tn+TBst5t7pRioQ==";

			ArrayList list5 = predictionWithNoCluster(apiurl, apikey, jsonBody);
			interestRateCluster = (String)list5.get(0);
			deviationValueCluster2 = (String)list5.get(1);
			System.out.println("Interest Rate from kmeans clustering : " + interestRateCluster);
			response.setAttribute("interestRate_kmeans", interestRateCluster);

		}
		
//		________________
		
		
//		________________
//		Get reults from Prediction API for Cluster 3
		String deviationValueCluster3 = "";
		if(clusterNumber.equals("3")){
			apiurl = "https://ussouthcentral.services.azureml.net/workspaces/a03e4f551ee949ed8fc7ebd0762eab67/services/781f0718e251437d8f500c4ad9466428/execute?api-version=2.0&details=true";
			apikey = "dGNpo5ZnHDKyeANsTr2ed9/OxyXbCCULFFterW/eb8UgXeMiq0Tsjsvydr/Sxa3Tlliddi2Tn+TBst5t7pRioQ==";

			ArrayList list6 = predictionWithNoCluster(apiurl, apikey, jsonBody);
			interestRateCluster = (String)list6.get(0);
			deviationValueCluster3 = (String)list6.get(1);
			System.out.println("Interest Rate from kmeans clustering : " + interestRateCluster);
			response.setAttribute("interestRate_kmeans", interestRateCluster);

		}
		
//		________________
*/
		
//		HIGHEST INTEREST RATE
		Double interestRate_NoCluster = 0.000, interestRate_kmeans = 0.000, highestInterestRate = 0.000;
		try{
			interestRate_NoCluster = Double.parseDouble(interestRateFromAPIWithNoCluster);
			interestRate_kmeans = Double.parseDouble(interestRateCluster);
			if(interestRate_NoCluster >= interestRate_kmeans){
				highestInterestRate = interestRate_NoCluster;
			}else{
				highestInterestRate = interestRate_kmeans;
			}
		}catch(Exception e){
			highestInterestRate = Double.parseDouble(interestRateCluster);
		}
		
		response.setAttribute("highestInterestRate", Double.valueOf((new DecimalFormat("###.##")).format(highestInterestRate)));
		
		return "interestRateOP";
	}
	
	
	private ArrayList<Object> predictionWithNoCluster(String apiurl, String apikey, String jsonBody){
		ArrayList<Object> list = new ArrayList<Object>();
		String decisionValue= "", decisionDeviation= "";
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
			System.out.println(result);
			JSONParser parser = new JSONParser();
			Object obj3 = parser.parse(result);
			JSONObject obj4 = (JSONObject) obj3;
			JSONObject results = (JSONObject)obj4.get("Results");
			System.out.println(results);
			JSONObject output1 = (JSONObject)results.get("output1");
			JSONObject values = (JSONObject)output1.get("value");
			JSONArray valuesArray = (JSONArray)values.get("Values");
			JSONArray firstValuesArray = (JSONArray) valuesArray.get(0);
			decisionValue = (String)firstValuesArray.get(11);
			decisionDeviation = (String)firstValuesArray.get(12);
			
			list.add(decisionValue);
			list.add(decisionDeviation);

		}
		catch (Exception e) {
			System.out.println(e.toString()); 
			
			return null;
		}
		
		return list;
	}
	
}
