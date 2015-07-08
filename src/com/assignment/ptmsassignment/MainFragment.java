package com.assignment.ptmsassignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainFragment extends Fragment{
	Dialog changePasswordDialog;
	TextView tvStaffNo;
	EditText etOldPassword, etNewPassword, etConfirmPassword;
	Button btnSubmit, btnCancel;
	Button btnChangePassword;
	String staffNo;
    public MainFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    	 staffNo = ((MainActivity)getActivity()).staffNo;
    	return rootView; 
      }
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        initializeComponent();
    }  
    private boolean changePassword(String staffNo, String oldPassword, String newPassword, String confirmPassword){
    	boolean validation = false;
    	try{
    		SQLiteDatabase db = DatabaseAccess.readWriteDatabase("/data/data/com.assignment.ptmsassignment/databases/PrinterDB");
    		Cursor cursor = DatabaseAccess.select(db, "SELECT staffPswd FROM Technician WHERE StaffNo ='"+staffNo+"'");
    		String password ="";
    		while(cursor.moveToNext()!=false){
    			password = cursor.getString(cursor.getColumnIndex("staffPswd"));
    		}
    		if(password.equals(oldPassword)){
    			if(newPassword.equals(confirmPassword)){
    				ContentValues pairs = new ContentValues();
    				pairs.put("staffPswd","confirmPassword");
    				int row = DatabaseAccess.update(db, "Technician", pairs, "staffno = '"+staffNo+"'");
    				if(row>=0){
    					validation = true;
    				}
    				else{
    					Toast.makeText(getActivity(), "Password cannot be changed", Toast.LENGTH_SHORT).show();
    				}
    			}
    			else{
    				Toast.makeText(getActivity(), "New passwords are not the same", Toast.LENGTH_SHORT).show();
    			}
    		}
    		Toast.makeText(getActivity(), "Password is not right", Toast.LENGTH_SHORT).show();
    	}catch(Exception e){
    			Toast.makeText(getActivity(), "ERROR: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
    	return validation;
    }
    private void initializeComponent(){
    	btnChangePassword = (Button) getView().findViewById(R.id.ChangePassword);
    	btnChangePassword.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				changePasswordDialog(staffNo);
			}
    		
    	});
    }
    private void changePasswordDialog(String staffNo){
    	changePasswordDialog = new Dialog(getActivity());
    	changePasswordDialog.setTitle("Add Service Job");
    	changePasswordDialog.setContentView(R.layout.dialog_change_password);
    	
    	tvStaffNo = (TextView) changePasswordDialog.findViewById(R.id.staffNo);
    	etOldPassword = (EditText) changePasswordDialog.findViewById(R.id.oldPassword);
    	etNewPassword = (EditText) changePasswordDialog.findViewById(R.id.newPassword);
    	etConfirmPassword = (EditText) changePasswordDialog.findViewById(R.id.confirmPassword);
    	
    	tvStaffNo.setText(staffNo);
    	btnSubmit = (Button) changePasswordDialog.findViewById(R.id.submit);
    	btnSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String oldPassword = etOldPassword.getText().toString();
				String newPassword = etNewPassword.getText().toString();
				String confirmPassword = etConfirmPassword.getText().toString();
				String staffNo = tvStaffNo.getText().toString();
				boolean right = changePassword(staffNo, oldPassword, newPassword, confirmPassword);
				if(right)
					Toast.makeText(getActivity(), "Password has been changed successfully", Toast.LENGTH_SHORT).show();
				else
					resetFields();
			}
		});
    	btnCancel = (Button) changePasswordDialog.findViewById(R.id.cancel);
    	btnCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				changePasswordDialog.cancel();
			}
		});
    	changePasswordDialog.show();
    }
    private void resetFields(){
    	etOldPassword.setText(null);
    	etNewPassword.setText(null);
    	etConfirmPassword.setText(null);
    }
}
