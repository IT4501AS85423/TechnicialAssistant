package com.assignment.ptmsassignment;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ServiceJobsFragment extends Fragment{
	ListView list;
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
        initializeCursorAdapter();
    }  
    private void findViews(){
    	list = (ListView) getView().findViewById(R.id.listView);
    }
    private Cursor fetchFromDB(){
    	SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
    	//Log.i("userID",userId);
    	Cursor cursor =DatabaseAccess.select(db, "SELECT rowid _id,* FROM ServiceJob ;");
    	int i=0;
    	while(cursor.moveToNext()!=false){
        	Log.i(cursor.getColumnName(0),cursor.getString(0));
        	Log.i(cursor.getColumnName(1),cursor.getString(1));
        	Log.i(cursor.getColumnName(2),cursor.getString(2));
        	Log.i(cursor.getColumnName(3),cursor.getString(3));
        	Log.i(cursor.getColumnName(4),cursor.getString(4));
    	}
    	//DatabaseAccess.connectionClose(db);
    	return cursor;
    }
    
    private void initializeCursorAdapter(){
    	int[] views = {R.id.tvJobNo, R.id.tvRequestDate,R.id.tvJobProblem,R.id.tvVisitDate,R.id.tvJobStatus};
    	String[] cols = {"jobNo", "requestDate", "jobProblem", "visitDate", "jobStatus"};
    	Cursor cursor = fetchFromDB();
    	
    	SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.listview_service_jobs_items, cursor,cols, views,0 );
    	list.setAdapter(scAdapter);
    }

}
