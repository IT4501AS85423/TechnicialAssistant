package com.assignment.ptmsassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button btnLogout, btnViewDB;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        ButtonOnClickListener buttonOnClickListener = new ButtonOnClickListener();
        btnLogout.setOnClickListener(buttonOnClickListener);
        btnViewDB.setOnClickListener(buttonOnClickListener);        
    }

    public void findView(){
    	btnLogout = (Button)findViewById(R.id.btnLogout);
    	btnViewDB = (Button)findViewById(R.id.btnViewDB);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //testdfd
    class ButtonOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch(v.getId()){
				case R.id.btnLogout:
					intent.setClass(MainActivity.this, LoginSystem.class);
					startActivity(intent);
					finish();
					break;
				
				case R.id.btnViewDB:
					intent.setClass(MainActivity.this, ViewDatabase.class);
					startActivity(intent);
					break;
			}
		}
    	
    }
    
    
}
