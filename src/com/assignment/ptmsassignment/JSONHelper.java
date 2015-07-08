package com.assignment.ptmsassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;

//Throws Exception!!!!!!!!!!!!!!!!!!
class JSONHelper extends AsyncTask<String,String,String>{
	String jobsUrlString = "http://itd-moodle.ddns.me/ptms/service_job.php?";
	String staffNo;
	public JSONHelper(String staffNo){
		this.staffNo = staffNo;
	}
	protected void fetchJobsToDB(String url, String staffNo) throws ClientProtocolException, IOException, JSONException {
		url += "staffNo="+staffNo;
	    HttpClient client = new DefaultHttpClient();
	    HttpGet request = new HttpGet(url);
		String urlContent = "";
		HttpResponse response = client.execute(request);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String rLine;
		while(( rLine = bReader.readLine()) != null)
		{
			urlContent += rLine;
		}
		bReader.close();
		Log.i("url",urlContent);
			
		JSONObject jsonObj = new JSONObject(urlContent);
		JSONArray jsonArray = jsonObj.getJSONArray("ServiceJob");
		SQLiteDatabase db = DatabaseAccess.readWriteDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
		String createTableSQL = "CREATE TABLE IF NOT EXISTS ServiceJob( jobNo text PRIMARY KEY, "
				+ "requestDate Date NOT NULL, jobProblem text NOT NULL, visitDate Date,"
				+ " jobStatus text NOT NULL, jobStartTime Date, jobEndTime Date, serialNo text NOT NULL, remark text);";
		DatabaseAccess.dropTable(db,"ServiceJob");
		DatabaseAccess.createTable(db, createTableSQL);
		for(int pointer=0; pointer<jsonArray.length();pointer++){
			ContentValues values = new ContentValues();
			values.put("jobNo", jsonArray.getJSONObject(pointer).getString("jobNo"));
			values.put("requestDate", jsonArray.getJSONObject(pointer).getString("requestDate"));
			values.put("jobProblem", jsonArray.getJSONObject(pointer).getString("jobProblem"));
			values.put("visitDate", jsonArray.getJSONObject(pointer).getString("visitDate"));
			values.put("jobStatus", jsonArray.getJSONObject(pointer).getString("jobStatus"));
			values.put("jobStartTime", jsonArray.getJSONObject(pointer).getString("jobStartTime"));
			values.put("jobEndTime", jsonArray.getJSONObject(pointer).getString("jobEndTime"));
			values.put("serialNo", jsonArray.getJSONObject(pointer).getString("serialNo"));
			values.put("remark", jsonArray.getJSONObject(pointer).getString("remark"));
			//DatabaseAccess.insertOrIgnore(db,"ServiceJob", values);
			DatabaseAccess.insert(db,"ServiceJob", values);
			
		}
		DatabaseAccess.connectionClose(db);
	    	
	}
	@Override
	protected String doInBackground(String... params) {
		try {
			fetchJobsToDB(jobsUrlString, staffNo);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
