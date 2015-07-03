package com.assignment.ptmsassignment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginSystem extends Activity{
	EditText etUserId, etPassword;
	Button btnLogin;
	String id, password;
	SQLiteDatabase db;
	DatabaseHelper dbHelper;
    Cursor cursor;
    String dbName = "PrinterDB";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        manageDatabase();
        findView();
        BtnLoginOnclick btnLoginOnclick = new BtnLoginOnclick();
        btnLogin.setOnClickListener(btnLoginOnclick);

	}

    class BtnLoginOnclick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            id = etUserId.getText().toString();
            password = etPassword.getText().toString();
            String[] args = {id, password};
            String sql = "select * from Technician where staffLogin =? and staffPswd = ?";
            cursor = db.rawQuery(sql, args);
            if(cursor.moveToFirst()==true){
                Intent intent = new Intent(LoginSystem.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Invalid userID or password, Please try again!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void manageDatabase(){
        dbHelper = new DatabaseHelper(LoginSystem.this, dbName);
        db = dbHelper.getWritableDatabase();
    }
	
	public void findView(){
		etUserId = (EditText)findViewById(R.id.etUserId);
		etPassword = (EditText)findViewById(R.id.etPassword);
		btnLogin = (Button)findViewById(R.id.btnLogin);
	}

}
