package com.assignment.ptmsassignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ServiceJobsFragment extends Fragment{
	ListView list;
	TextView tvUpdateTime;
	String userId;
	public ServiceJobsFragment(){
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_service_jobs, container, false);

    	return rootView; 
      }
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        findViews();
        initializeList();
    }  
    private void findViews(){
    	list = (ListView) getView().findViewById(R.id.listView);
    	tvUpdateTime = (TextView) getView().findViewById(R.id.updateTime);
    }

    private void initializeList(){
        initializeCursorAdapter();
    	list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), JobDetailsActivity.class);
                String jobNo = ((TextView)view.findViewById(R.id.jobNo)).getText().toString();
				intent.putExtra("jobNo", jobNo);
                startActivity(intent);
			}
			});
    }
    private void initializeCursorAdapter(){
    	SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
    	
    	int[] views = {R.id.jobNo, R.id.requestDate,R.id.jobProblem,R.id.visitDate,R.id.jobStatus};
    	String[] cols = {"jobNo", "requestDate", "jobProblem", "visitDate", "jobStatus"};
    	
    	Cursor cursor = DatabaseAccess.select(db, "SELECT rowid _id,* FROM ServiceJob ;");
    	
    	SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.listview_service_jobs_items, cursor,cols, views,0 );
    	list.setAdapter(scAdapter);
    	tvUpdateTime.setText(getUpdateTime("' at ' HH:mm:ss ' on ' dd/MM/yyyy"));
    	
    	DatabaseAccess.connectionClose(db);
    }
    private String getUpdateTime(String dateFormat){
    	SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat);
    	return sdFormat.format(Calendar.getInstance().getTime());
    }

}
