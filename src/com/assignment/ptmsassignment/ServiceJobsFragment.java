package com.assignment.ptmsassignment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceJobsFragment extends Fragment{
	ListView list;
	TextView tvUpdateTime;
	String userId;
	Button btnSubmit, btnCancel;
	TextView tvJobNo, tvJobStatus;
	EditText etJobProblem;
	Spinner spinner;
	String searchKey;
	Dialog addJobDialog;
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
    
	public void onPause(){
		super.onPause();
		Log.i("OnPause!!", "OnPause");
	}
	
	public void onStart(){
		super.onStart();
		refresh();
		Log.i("OnStart!!", "OnStart");
	}
	public void onStop(){
		super.onStop();
		//((ServiceJobsFragment)getActivity().get
		Log.i("OnStop!!", "OnStop");
	}
    
    protected void OnResume(){
    	super.onResume();
    	//refresh();
    	Log.i("OnResime!!!!!!","Resume");
    }
    
    protected void refresh(){
    	initializeCursorAdapter(searchKey);
    }
    
    private void initializeList(){
        initializeCursorAdapter();
    	list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), JobDetailsActivity.class);
                String jobNo = ((TextView)view.findViewById(R.id.staffNo)).getText().toString();
                String visitDate = ((TextView)view.findViewById(R.id.visitDate)).getText().toString();
                String jobStatus = ((TextView)view.findViewById(R.id.jobStatus)).getText().toString();
                intent.putExtra("jobNo", jobNo);
				intent.putExtra("visitDate",visitDate);
				intent.putExtra("jobStatus",jobStatus);
                startActivity(intent);
			}
			});
    }
    protected void initializeCursorAdapter(){
    	initializeCursorAdapter(null);
    }
    protected void initializeCursorAdapter(String key){
    	SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
    	
    	int[] views = {R.id.staffNo, R.id.requestDate,R.id.oldPassword,R.id.visitDate,R.id.jobStatus,R.id.serialNo};
    	String[] cols = {"jobNo", "requestDate", "jobProblem", "visitDate", "jobStatus", "serialNo"};
    	if(key!=null)
    		if(!key.trim().equals(""))
    			key = "WHERE jobNo = '"+key+"'";
    		else
    			key = "";
    	else 
    		key = "";
    	Cursor cursor = DatabaseAccess.select(db, "SELECT rowid _id,* FROM ServiceJob " + key +";");
    	Log.i("SJSQL", "SELECT rowid _id,* FROM ServiceJob " + key +";");
    	SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.listview_service_jobs_items, cursor,cols, views,0 );
    	
    	scAdapter.setFilterQueryProvider(new FilterQueryProvider() {

        @Override
        public Cursor runQuery(CharSequence constraint) {

            String key = "WHERE jobNo LIKE '%"+constraint.toString()+"%'";
            SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
            Cursor cursor = DatabaseAccess.select(db, "SELECT rowid _id,* FROM ServiceJob " + key +";");
            return cursor;

        }
    });
    	list.setAdapter(scAdapter);
    	
    	tvUpdateTime.setText(getUpdateTime("' at ' HH:mm:ss ' on ' dd/MM/yyyy"));
    	
    	DatabaseAccess.connectionClose(db);
    }
    private String getUpdateTime(String dateFormat){
    	SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat);
    	return sdFormat.format(Calendar.getInstance().getTime());
    }
    private ArrayList<String> fetchSerialNo(){
    	ArrayList<String> arrList = new ArrayList<String>();
    	SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
    	
    	Cursor cursor = DatabaseAccess.select(db, "SELECT serialNo FROM Purchase ;");
    	while(cursor.moveToNext() != false){
    		arrList.add(cursor.getString(cursor.getColumnIndex("serialNo")));
    	}
    	DatabaseAccess.connectionClose(db);
    	return arrList;
    }
    protected void addJob(){
    	addJobDialog = new Dialog(getActivity());
    	addJobDialog.setTitle("Add Service Job");
    	addJobDialog.setContentView(R.layout.dialog_add_job);
    	
    	tvJobNo = (TextView) addJobDialog.findViewById(R.id.staffNo);
    	tvJobStatus = (TextView) addJobDialog.findViewById(R.id.jobStatus);
    	etJobProblem = (EditText) addJobDialog.findViewById(R.id.oldPassword);
    	spinner = (Spinner) addJobDialog.findViewById(R.id.spinner);
    	
    	ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, fetchSerialNo());
		arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrAdapter);
    	
		tvJobNo.setText(genJobNo("%03d"));
    	Log.i("TVJobNo",tvJobNo.getText().toString());
    	btnSubmit = (Button) addJobDialog.findViewById(R.id.submit);
    	btnSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(etJobProblem.getText().toString().trim()))
				{
					Toast.makeText(getActivity(), "Fields have not been completed yet.", Toast.LENGTH_SHORT).show();
					return;
				}
				try{
					SQLiteDatabase db = DatabaseAccess.readWriteDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
					Calendar now = Calendar.getInstance();
					SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
		    		String nowDate = sdFormat.format(now.getTime());
		    		ContentValues values = new ContentValues();
		    		values.put("jobNo", tvJobNo.getText().toString());
		    		values.put("jobProblem", etJobProblem.getText().toString().trim());
		    		values.put("jobStatus", tvJobStatus.getText().toString());
		    		values.put("requestDate",nowDate);
		    		values.put("serialNo", spinner.getSelectedItem().toString());
		    		int row = DatabaseAccess.insert(db, "ServiceJob", values);
		    		Toast.makeText(getActivity(), "New Serivce Job has been added successfully.", Toast.LENGTH_SHORT).show();
		    		addJobDialog.dismiss();
		    		refresh();
				}
				catch(Exception e){
					Toast.makeText(getActivity(), "ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
    	btnCancel = (Button) addJobDialog.findViewById(R.id.cancel);
    	btnCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				addJobDialog.cancel();
			}
		});
    	addJobDialog.show();
    }

    private String genJobNo(String format){
    	SQLiteDatabase db = DatabaseAccess.readDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
    	Cursor cursor = DatabaseAccess.select(db, "SELECT jobNo FROM ServiceJob ORDER BY jobNo ASC;");
    	int starting = 0;
    	while(cursor.moveToNext()!=false){
    		boolean equal = String.format(format, starting).equals(cursor.getString(cursor.getColumnIndex("jobNo")));
    		Log.i("GenJobNo",String.valueOf(starting));
    		if(equal)
    			starting++;
    		else
    			break;
    	}
    	DatabaseAccess.connectionClose(db);
    	return String.format(format, starting);
    }
    
}
