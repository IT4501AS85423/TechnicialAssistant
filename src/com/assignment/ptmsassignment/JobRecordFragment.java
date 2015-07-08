package com.assignment.ptmsassignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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

public class JobRecordFragment extends Fragment{
	EditText etVisitDate, etJobStartTime, etJobEndTime,etRemark;
	TextView tvJobNo;
	Spinner spinner;
	Button btnReset, btnSubmitNSign;
	String jobNo;
	Calendar visitDate = Calendar.getInstance();
	Calendar jobTime = Calendar.getInstance();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		    Bundle savedInstanceState) {
		    	View rootView = inflater.inflate(R.layout.fragment_job_record, container, false);
		    	jobNo = getArguments().getString("jobNo");
		    	return rootView;
	}
	
	 @Override  
	public void onActivityCreated(Bundle savedInstanceState) {  
		 super.onActivityCreated(savedInstanceState);  
	    // jobNo = getArguments().getString("jobNo");
	     initializeComponent();
	}  
	 
	private void findViews(){
		etVisitDate = (EditText) getView().findViewById(R.id.visitDate);
		etJobStartTime = (EditText) getView().findViewById(R.id.jobStartTime);
		etJobEndTime = (EditText) getView().findViewById(R.id.jobEndTime);
		etRemark = (EditText) getView().findViewById(R.id.remark);
		tvJobNo = (TextView) getView().findViewById(R.id.staffNo);
		spinner = (Spinner) getView().findViewById(R.id.spinner);
		btnReset = (Button) getView().findViewById(R.id.reset);
		//btnRestore = (Button) getView().findViewById(R.id.restore);
		btnSubmitNSign = (Button) getView().findViewById(R.id.submitNSign);
	}
	
	private void initializeComponent(){
	    findViews();
	    
	    tvJobNo.setText(jobNo);
	    
		visitDatePicker();
		jobStartTimePicker();
		jobEndTimePicker();
		
		String[] jobStatusItems = {"completed", "cancelled", "postponed" , "follow-up"};
		ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, jobStatusItems);
		arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrAdapter);
		
		btnSubmitNSign.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean prsCheck = passPresenceCheck(etVisitDate, etJobStartTime, etJobEndTime);
				if(prsCheck){
					updateJobDetails(jobNo);
					refreshCompletedRecord(etVisitDate.getText().toString().trim(), spinner.getSelectedItem().toString());
				}
				else{
					Toast.makeText(getActivity(), "Fields have not been completed yet", Toast.LENGTH_SHORT).show();
				}
				//Fragment frag = getActivity().getSupportFragmentManager().findFragmentByTag(getArguments().getString("tag"));
				//frag.onResume();
			}
			
		});
		btnReset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				etVisitDate.setText(null);
				etRemark.setText(null);
				etJobStartTime.setText(null);
				etJobEndTime.setText(null);
			}
		});
	}
	private boolean passPresenceCheck(EditText... et){
		for(int i = 0; i<et.length; i++){
			String checkField = et[i].getText().toString().trim();
			if(TextUtils.isEmpty(checkField))
				return false;
		}
		return true;
	}
	private void updateJobDetails(String key){
		SQLiteDatabase db = DatabaseAccess.readWriteDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
		
		ContentValues values = new ContentValues();
		values.put("visitDate", etVisitDate.getText().toString().trim());
		values.put("jobStartTime", etJobStartTime.getText().toString().trim());
		values.put("jobEndTime", etJobEndTime.getText().toString().trim());
		values.put("remark", etRemark.getText().toString());
		values.put("jobStatus", spinner.getSelectedItem().toString().trim());
		int row = DatabaseAccess.update(db, "ServiceJob", values,"jobNo = '"+jobNo+"'");
		if(row >= 0){
			Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
		}
	}
	private void visitDatePicker(){
		visitDate = Calendar.getInstance();
		etVisitDate.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					DatePickerDialog.OnDateSetListener datePickerListener 
						= new DatePickerDialog.OnDateSetListener(){
						
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							visitDate.set(Calendar.YEAR, year);
							visitDate.set(Calendar.MONTH, monthOfYear);
							visitDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
							etVisitDate.setText(sdFormat.format(visitDate.getTime()));
						}	
					};
					new DatePickerDialog(getActivity(),datePickerListener,visitDate.get(Calendar.YEAR),
							visitDate.get(Calendar.MONTH), visitDate.get(Calendar.DAY_OF_MONTH)).show();
				}					
				//etVisitDate.clearFocus();
			}
		});
	}
	private void jobStartTimePicker(){
		jobTime = Calendar.getInstance();
		etJobStartTime.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					TimePickerDialog.OnTimeSetListener timePickerListener 
						= new TimePickerDialog.OnTimeSetListener(){
					
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							jobTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
							jobTime.set(Calendar.MINUTE, minute);
							SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
							etJobStartTime.setText(sdFormat.format(jobTime.getTime()));
						}


					};
					new TimePickerDialog(getActivity(),timePickerListener,jobTime.get(Calendar.HOUR_OF_DAY),
							jobTime.get(Calendar.MINUTE), true).show();
				}					
				//etVisitDate.clearFocus();
			}
		});
	}

	private void jobEndTimePicker(){
		jobTime = Calendar.getInstance();
		etJobEndTime.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					TimePickerDialog.OnTimeSetListener timePickerListener 
						= new TimePickerDialog.OnTimeSetListener(){
					
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							jobTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
							jobTime.set(Calendar.MINUTE, minute);
							SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
							etJobEndTime.setText(sdFormat.format(jobTime.getTime()));
						}


					};
					new TimePickerDialog(getActivity(),timePickerListener,jobTime.get(Calendar.HOUR_OF_DAY),
							jobTime.get(Calendar.MINUTE), true).show();
				}					
				//etVisitDate.clearFocus();
			}
		});
	}
	protected void refreshCompletedRecord(String visitDate, String jobStatus){
		ViewPager vPager = (ViewPager)getActivity().findViewById(R.id.viewPager);
		Fragment jobVisitedFrag = new JobVisitedFragment();
		Bundle args = new Bundle();
		args.putString("jobStatus",jobStatus);
		args.putString("visitDate", visitDate);
		jobVisitedFrag.setArguments(args);
		//((JobDetailsActivity)getActivity()).
		
		//vPager.setCurrentItem(getActivity().getActionBar().);
		//getActivity().getActionBar().setSelectedNavigationItem(0);
		//vPager.getAdapter().startUpdate(vPager);
		//getActivity().
		FragmentStatePagerAdapter adapter = ((FragmentStatePagerAdapter)vPager.getAdapter());
		JobDetailsActivity act = ((JobDetailsActivity)getActivity());
		//act.getFragment(1)
		act.jobStatus= spinner.getSelectedItem().toString();
		//getActivity().getSupportFragmentManager().beginTransaction().remove(((JobDetailsActivity)getActivity()).getFragment(1)).commit();
		//adapter.destroyItem(vPager, 1, ((JobDetailsActivity)getActivity()).getFragment(1));
		//adapter.instantiateItem(vPager, 1);
		adapter.notifyDataSetChanged();
		//getActivity().getSupportFragmentManager().beginTransaction().remove(((JobDetailsActivity)getActivity()).getFragment(1)).commit();
		//getActivity().getSupportFragmentManager().executePendingTransactions();
		//vPager.getAdapter().destroyItem(vPager, 1, null);
		//vPager.getAdapter().instantiateItem(vPager, 1);
		//vPager.getAdapter().finishUpdate(vPager);
		//((JobDetailsActivity)getActivity()).initializeViewPager();
		//getActivity().getSupportFragmentManager().beginTransaction().remove(((JobDetailsActivity)getActivity()).getFragment(0)).commit();
		//getActivity().getSupportFragmentManager();
		//getActivity().getSupportFragmentManager().executePendingTransactions();
		//vPager.getAdapter().getItemPosition(1);
		//vPager.getAdapter().notifyDataSetChanged();
		//vPager.getAdapter().getItemPosition(1);
		//vPager.getAdapter().instantiateItem(vPager, 1);
		//getActivity().getSupportFragmentManager().beginTransaction().rep
		//FragmentPagerAdapter fragPagerAdapter = (FragmentPagerAdapter) vPager.getAdapter();
		//fragPagerAdapter.getItem(1);
		//((JobDetailsActivity)getActivity()).initializeActionMenu();
		//((JobDetailsActivity)getActivity()).initializeViewPager();
		//getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.job_record_frag,jobVisitedFrag, "jobVisitedFrag").commit();
		//getActivity().getSupportFragmentManager().executePendingTransactions();
		//vPager.getAda;
		//getActivity().finish();
		//((JobDetailsActivity)getActivity()).initializeViewPager();
		//((JobDetailsActivity)getActivity()).finish();
		//((JobDetailsActivity)getActivity()).onCreate(null);
		//getActivity().getSupportFragmentManager().executePendingTransactions();
		//fragPagerAdapter.instantiateItem(vPager, 1);
		//vPager.setAdapter(fragPagerAdapter);
		//getActivity().getActionBar().setSelectedNavigationItem(1);
		//getFragmentManager().beginTransaction().replace(R.id.job_record_frag, jobVisitedFrag, "jobVisitedFrag");
		//((JobDetailsActivity)getActivity()).getFragment(1).getFragmentManager().beginTransaction().replace(((JobDetailsActivity)getActivity()).getFragment(1)., jobVisitedFrag, "jobVisitedFrag").commit();
		//getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.job_record_frag, jobVisitedFrag, "jobVisitedFrag").commit();
	
	}
}

