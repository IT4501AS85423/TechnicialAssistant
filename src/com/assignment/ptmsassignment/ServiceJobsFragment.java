package com.assignment.ptmsassignment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
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
    	
    	//DatabaseAccess.connectionClose(db);
    	return cursor;
    }
    
    private void initializeCursorAdapter(){
    	int[] views = {R.id.jobNo};
    	String[] cols = {"jobNo"};
    	Cursor cursor = fetchFromDB();
    	
    	SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.listview_service_jobs_items, cursor,cols, views,0 );
    	list.setAdapter(scAdapter);
    }

}
