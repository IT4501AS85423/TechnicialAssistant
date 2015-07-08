package com.assignment.ptmsassignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class JobVisitedFragment extends Fragment{
	TextView tvVisitDate, tvJobStatus;
	String visitDate, jobStatus;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		    Bundle savedInstanceState) {
		    	View rootView = inflater.inflate(R.layout.fragment_job_visited, container, false);
			    visitDate = getArguments().getString("visitDate");
			    jobStatus = getArguments().getString("jobStatus");
		    	return rootView;
	}
	
	 @Override  
	public void onActivityCreated(Bundle savedInstanceState) {  
		 super.onActivityCreated(savedInstanceState);  
		 findViews();
		 initializeComponent();
	}  
	 
	private void findViews(){
		tvVisitDate = (TextView) getView().findViewById(R.id.visitDate);
		tvJobStatus = (TextView) getView().findViewById(R.id.jobStatus);
	}
	
	private void initializeComponent(){
		tvVisitDate.setText(visitDate);
		tvJobStatus.setText(jobStatus);
		if(jobStatus.equalsIgnoreCase("completed")){
			tvJobStatus.setBackgroundColor(getResources().getColor(R.color.green)); //green
		}
		if(jobStatus.equalsIgnoreCase("cancelled")){
			tvJobStatus.setBackgroundColor(getResources().getColor(R.color.red)); //red
		}
		if(jobStatus.equalsIgnoreCase("postponed")){
			tvJobStatus.setBackgroundColor(getResources().getColor(R.color.orange)); //orange
		}
		if(jobStatus.equalsIgnoreCase("follow-up")){
			tvJobStatus.setBackgroundColor(getResources().getColor(R.color.blue)); //blue
		}
	}

}

