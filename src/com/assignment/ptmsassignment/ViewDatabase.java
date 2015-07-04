package com.assignment.ptmsassignment;

import com.assignment.ptmsassignment.MainActivity.ButtonOnClickListener;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewDatabase extends Activity{

	Spinner spinnerChooseDB;
	Button btnShowDB;
	TextView tvShowDB;
	ArrayAdapter<String> arrayAdapter;
	SQLiteDatabase db;
	String[] items;
	DatabaseHelper dbHelper;
	String dbName = "PrinterDB";
	Cursor cursor;
	String dataStr;
	String sql;
	String dataStrHeader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewdb);
		findView();
		items = getResources().getStringArray(R.array.databse);
		arrayAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item,
				items);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChooseDB.setAdapter(arrayAdapter);
		 ButtonOnClickListener buttonOnClickListener = new ButtonOnClickListener();
		 btnShowDB.setOnClickListener(buttonOnClickListener);
		
	}
	
	public void findView(){
		spinnerChooseDB = (Spinner)findViewById(R.id.spinnerChooseDB);
		btnShowDB = (Button)findViewById(R.id.btnShowDB);
		tvShowDB = (TextView)findViewById(R.id.tvShowDB);
	}
	
	class ButtonOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			dbHelper = new DatabaseHelper(ViewDatabase.this, dbName);
			db = dbHelper.getWritableDatabase();
			System.out.println((int)spinnerChooseDB.getSelectedItemId());
			switch((int)spinnerChooseDB.getSelectedItemId()){
				case 0:
					dataStrHeader = String.format("%4s %-10s %-10s %12s\n", "staffNo", "staffLogin", "staffPswd", "staffName");
					sql  = "select * from Technician;";
					cursor = db.rawQuery(sql, null);
					dataStr = dataStrHeader;
					while (cursor.moveToNext()) {
						String staffNo = cursor.getString(cursor.getColumnIndex("staffNo"));
						String staffLogin = cursor.getString(cursor.getColumnIndex("staffLogin"));
						String staffPswd = cursor.getString(cursor.getColumnIndex("staffPswd"));
						String staffName = cursor.getString(cursor.getColumnIndex("staffName"));
						dataStr += String.format("%4s %-10s %-10s %12s\n", staffNo, staffLogin, staffPswd, staffName);
					}
					tvShowDB.setText(dataStr);
					break;
				
				case 1:
					dataStrHeader = String.format("%4s %-10s %-10s\n", "prodNo", "prodName", "prodPrice");
					sql  = "select * from Product;";
					cursor = db.rawQuery(sql, null);
					dataStr = dataStrHeader;
					while (cursor.moveToNext()) {
						String prodNo = cursor.getString(cursor.getColumnIndex("prodNo"));
						String prodName = cursor.getString(cursor.getColumnIndex("prodName"));
						int prodPrice = cursor.getInt(cursor.getColumnIndex("prodPrice"));
						dataStr += String.format("%4s %-10s %-10d\n", prodNo, prodName, prodPrice);
					}
					tvShowDB.setText(dataStr);
					break;
					
				case 2:
					dataStrHeader = String.format("%4s %-10s %-10s %12s\n", "comNo", "comName", "comTel", "comAddr");
					sql  = "select * from Company;";
					cursor = db.rawQuery(sql, null);
					dataStr = dataStrHeader;
					while (cursor.moveToNext()) {
						String comNo = cursor.getString(cursor.getColumnIndex("comNo"));
						String comName = cursor.getString(cursor.getColumnIndex("comName"));
						String comTel = cursor.getString(cursor.getColumnIndex("comTel"));
						String comAddr = cursor.getString(cursor.getColumnIndex("comAddr"));
						dataStr += String.format("%4s %-10s %-10s %12s\n", comNo, comName, comTel, comAddr);
					}
					tvShowDB.setText(dataStr);
					break;
				
				case 3:
					dataStrHeader = String.format("%4s %-10s %-10s %12s\n", "serialNo", "purchaseDate", "prodNo", "comNo");
					sql  = "select * from Purchase;";
					cursor = db.rawQuery(sql, null);
					dataStr = dataStrHeader;
					while (cursor.moveToNext()) {
						String serialNo = cursor.getString(cursor.getColumnIndex("serialNo"));
						String purchaseDate = cursor.getString(cursor.getColumnIndex("purchaseDate"));
						String prodNo = cursor.getString(cursor.getColumnIndex("prodNo"));
						String comNo = cursor.getString(cursor.getColumnIndex("comNo"));
						dataStr += String.format("%4s %-10s %-10s %12s\n", serialNo, purchaseDate, prodNo, comNo);
					}
					tvShowDB.setText(dataStr);
					break;		
				default:
					break;
			}
		}
		
	}
}
