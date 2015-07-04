package com.assignment.ptmsassignment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class JobDetailsActivity extends Activity{
	String jobNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_details);
		jobNo = getIntent().getStringExtra("jobNo");
		Log.i("jobNo", jobNo);
		initializeActionMenu();
		initializeFragment();
	 }
	private void initializeFragment(){
		FragmentManager fragManager = getFragmentManager();
		fragManager.beginTransaction().replace(R.id.content, new JobBackgroundFragment(jobNo)).commit();
	}
	private void initializeActionMenu(){
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
