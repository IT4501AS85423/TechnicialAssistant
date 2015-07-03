package com.assignment.ptmsassignment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends Fragment{
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
