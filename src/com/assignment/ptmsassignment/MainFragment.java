package com.assignment.ptmsassignment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.*;
import android.util.Log;
import 	android.widget.ListView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainFragment extends Fragment{
	private Button btnLogout;
    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_main, container, false);

    	//btnLogout.setOnClickListener(new View.OnClickListener() {
 		//	@Override
 		//	public void onClick(View v) {
 		//		//Intent intent = new Intent(MainActivity.this, LoginSystem.class);
 				//startActivity(intent);
 				//finish();
 		//	}
 		//});
    	 
    	return rootView; 
      }
    //@Override  
    //public void onActivityCreated(Bundle savedInstanceState) {  
     //   super.onActivityCreated(savedInstanceState);  
    //	btnLogout = (Button)getView().findViewById(R.id.btnLogout);
   // }  
    
}
