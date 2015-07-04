package com.assignment.ptmsassignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JobBackgroundFragment extends Fragment{
	String jobNo;
	TextView tvJobNo, tvJobProblem, tvJobStatus, tvRequestDate, tvVisitDate, tvRemark, tvSerialNo, tvJobStartTime,tvJobEndTime;
	TextView tvComNo, tvComName, tvComAddr, tvComTel;
	TextView tvProdNo, tvProdName,tvPurchaseDate;
	TextView tvUpdateStatus;
	public JobBackgroundFragment(String jobNo){
		this.jobNo = jobNo;
	}
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    		Bundle savedInstanceState) {
	    	View rootView = inflater.inflate(R.layout.fragment_job_background, container, false);
	    	return rootView;
	 }
	 @Override  
	public void onActivityCreated(Bundle savedInstanceState) {  
		 super.onActivityCreated(savedInstanceState);  
	     findViews();
	     fetchJobDetails(jobNo);
	}  
	 private void findViews(){
		 tvJobNo = (TextView) getView().findViewById(R.id.jobNo);
		 tvJobProblem = (TextView) getView().findViewById(R.id.jobProblem);
		 tvJobStatus = (TextView) getView().findViewById(R.id.jobStatus);
		 tvRequestDate = (TextView) getView().findViewById(R.id.requestDate);
		 tvVisitDate = (TextView) getView().findViewById(R.id.visitDate);
		 tvRemark = (TextView) getView().findViewById(R.id.remark);
		 tvSerialNo = (TextView) getView().findViewById(R.id.serialNo);
		 tvJobStartTime = (TextView) getView().findViewById(R.id.jobStartTime);
		 tvJobEndTime = (TextView) getView().findViewById(R.id.jobEndTime);
		 tvComNo = (TextView) getView().findViewById(R.id.comNo);
		 tvComName = (TextView) getView().findViewById(R.id.comName);
		 tvComAddr = (TextView) getView().findViewById(R.id.comAddr);
		 tvComTel = (TextView) getView().findViewById(R.id.comTel);
		 tvProdNo = (TextView) getView().findViewById(R.id.prodNo);
		 tvProdName = (TextView) getView().findViewById(R.id.prodName);
		 tvPurchaseDate = (TextView) getView().findViewById(R.id.purchaseDate);
		 tvUpdateStatus = (TextView) getView().findViewById(R.id.updateStatus);
	 }
	private void fetchJobDetails(String key){
		SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
		Cursor cursor = DatabaseAccess.select(db,"SELECT * FROM ServiceJob sj, Purchase pc, Company cp, "
				+ "Product pd WHERE pc.serialNo = sj.serialNo AND pc.comNo = cp.comNo AND pc.prodNo = "
				+ "pd.prodNo AND sj.jobNo = '"+key+"'");
		while(cursor.moveToNext() != false){
			//job background
			tvJobNo.setText(cursor.getString(cursor.getColumnIndex("jobNo")));
			tvJobProblem.setText(cursor.getString(cursor.getColumnIndex("jobProblem")));
			tvJobStatus.setText(cursor.getString(cursor.getColumnIndex("jobStatus")));
			tvRequestDate.setText(cursor.getString(cursor.getColumnIndex("requestDate")));
			tvVisitDate.setText(cursor.getString(cursor.getColumnIndex("visitDate")));
			tvRemark.setText(cursor.getString(cursor.getColumnIndex("remark")));
			tvJobStartTime.setText(cursor.getString(cursor.getColumnIndex("jobStartTime")));
			tvJobEndTime.setText(cursor.getString(cursor.getColumnIndex("jobEndTime")));
			tvSerialNo.setText(cursor.getString(cursor.getColumnIndex("serialNo")));
			//client info
			tvComNo.setText(cursor.getString(cursor.getColumnIndex("comNo")));
			tvComName.setText(cursor.getString(cursor.getColumnIndex("comName")));
			tvComAddr.setText(cursor.getString(cursor.getColumnIndex("comAddr")));
			tvComTel.setText(cursor.getString(cursor.getColumnIndex("comTel")));
			//product info
			tvProdNo.setText(cursor.getString(cursor.getColumnIndex("prodNo")));
			tvProdName.setText(cursor.getString(cursor.getColumnIndex("prodName")));
			tvPurchaseDate.setText(cursor.getString(cursor.getColumnIndex("purchaseDate")));
		}
		String jobStatus = tvJobStatus.getText().toString();
		if(jobStatus.equalsIgnoreCase("completed")){
			tvJobStatus.setBackgroundColor(0xFF66FF33); //green
		}
		if(jobStatus.equalsIgnoreCase("cancelled")){
			tvJobStatus.setBackgroundColor(0xFFFF5050); //red
		}
		if(jobStatus.equalsIgnoreCase("postponed")){
			tvJobStatus.setBackgroundColor(0xFFFFA347); //orange
		}
		if(jobStatus.equalsIgnoreCase("follow-up")){
			tvJobStatus.setBackgroundColor(0xFF33CCCC); //blue
		}
		if(jobStatus.equalsIgnoreCase("pending")){
			tvJobStatus.setBackgroundColor(0xFFCC9900); //brown
		}
		
		tvUpdateStatus.setText(getUpdateTime("'at'HH:mm:ss 'on' dd/MM/yyyy"));
	}
	
    private String getUpdateTime(String dateFormat){
    	SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat);
    	return sdFormat.format(Calendar.getInstance().getTime());
    }
}
