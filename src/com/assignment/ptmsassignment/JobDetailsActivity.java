package com.assignment.ptmsassignment;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;

public class JobDetailsActivity extends FragmentActivity implements JobBackgroundFragment.CallBackInterface{
	String jobNo, jobStatus, visitDate;
	FragmentTabHost tabHost;
	ViewPager vPager;
	SparseArray<Fragment> mapFragTags;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_details);
		jobNo = getIntent().getStringExtra("jobNo");
		jobStatus = getIntent().getStringExtra("jobStatus");
		visitDate = getIntent().getStringExtra("visitDate");
		
		Log.i("jobNo", jobNo);
		//jobInfoFrag = new JobBackgroundFragment();
		mapFragTags = new SparseArray<Fragment>();
		//jobRecordFrag = new JobRecordFragment();
		//getSupportFragmentManager().beginTransaction().add(new JobBackgroundFragment(), "tag1").commit();
		//getSupportFragmentManager().executePendingTransactions();
		//jobInfoFrag = (JobBackgroundFragment)getSupportFragmentManager().findFragmentByTag("tag1");
		//if(jobInfoFrag == null)
		//	Log.i("Test","NULL");
		//else
		//	Log.i("Test","NOT NULL");
		findViews();
		//getSupportFragmentManager().beginTransaction().add(new JobBackgroundFragment(), "tag1").commit();
		initializeViewPager();
		initializeActionMenu();
	 }

	private void findViews(){
		vPager = (ViewPager)findViewById(R.id.viewPager);
		//TabSpec tab;
        //tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        //tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);;
        //tab = tabHost.newTabSpec("tabDetailInfo");
        //tab.setIndicator("Detail Information");
        //tabHost.addTab(tab,JobBackgroundFragment.class,null);
        //tab = tabHost.newTabSpec("tabUpdateStatus");
        //tab.setIndicator("Job Update Status");
       // tabHost.addTab(tab,JobUpdateStatusFragment.class,null);
       // tabHost.setCurrentTab(0);
	}
	protected void initializeViewPager(){
		FragmentStatePagerAdapter fragPagerAdapter 
			= new FragmentStatePagerAdapter(getSupportFragmentManager()){

			@Override
			public Fragment getItem(int position) {
				//JobBackgroundFragment jobInfoFrag = new JobBackgroundFragment();
				//JobRecordFragment jobRecordFrag = new JobRecordFragment();
				//JobBackgroundFragment jobInfoFrag = (JobBackgroundFragment)getSupportFragmentManager().findFragmentByTag("tag1");
				switch(position){
					case 0:
						//jobInfoFrag.setArguments(args);
						//jobInfoTag = "jobInfoTag";
						//jobInfoTag = jobInfoFrag.getTag();
						//Log.i("JobInfoTag",String.valueOf(jobInfoFrag.getId()));
						//getSupportFragmentManager().beginTransaction().add(jobInfoFrag, "tag1").commit();
						//getSupportFragmentManager().executePendingTransactions();
						//JobBackgroundFragment jobInfoFrag1 = (JobBackgroundFragment)getSupportFragmentManager().findFragmentByTag("tag1");
						//getSupportFragmentManager().beginTransaction().add(jobInfoFrag, jobInfoTag).commit();
						//jobInfoFrag.onResume();
						//notifyDataSetChanged();

						//jobInfoFrag.instantiate(getApplicationContext(), JobBackgroundFragment.class.getName());
						return Fragment.instantiate(getApplicationContext(), JobBackgroundFragment.class.getName(), null);
					default:
						//args.putString("tag",jobInfoFrag.getTag());
						//jobRecordFrag.setArguments(args);
						if(!jobStatus.equals("pending"))
						{
							return Fragment.instantiate(getApplicationContext(), JobVisitedFragment.class.getName(), null);
							
						}
						else
						{
							return Fragment.instantiate(getApplicationContext(), JobRecordFragment.class.getName(), null);
						}
				}
			}
			@Override
			public int getItemPosition(Object object){
				return POSITION_NONE;
			}
			@Override
			public int getCount() {
				return 2;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position, Object object){
				super.destroyItem(container, position, object);
				mapFragTags.remove(position);
			}
			@Override
		    public Object instantiateItem(ViewGroup container, int position) {
		        Object obj = super.instantiateItem(container, position);
		        if (obj instanceof Fragment) {
		            Fragment frag = (Fragment) obj;
		            //final long itemId = getItem(position).getId();  
		             
		           // String name = "android:switcher:"+container.getId()+":" +itemId;  
		            //Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);  
		            //if (fragment != null) {  
		            mapFragTags.put(position, (Fragment)obj);
		            //}
					Bundle args = new Bundle();
					args.putString("jobNo", jobNo);
					
		            if(frag instanceof JobVisitedFragment){
		            	args.putString("jobStatus",jobStatus);
		            	args.putString("visitDate",visitDate);
		            }
		           
		            frag.setArguments(args);
		            //temp = f.getId();
		            //Log.i("ID",String.valueOf(frag.getId()));
		            obj = frag;
		            //Log.i("Tag -Frag", tag);
		        }
		        return obj;
			}
		};
		vPager.setAdapter(fragPagerAdapter);
		vPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			 @Override
             public void onPageSelected(int position) {
				 //JobBackgroundFragment frag = (JobBackgroundFragment)getFragment(position);
				 //frag.fetchJobDetails("503");
				 switch(position){
				 	case 0:
				 		((JobBackgroundFragment)getFragment(position)).fetchJobDetails(jobNo);
				 		break;
				 	case 1:
				 		
				 }
                 getActionBar().setSelectedNavigationItem(position);
             }
		});
	}
    protected Fragment getFragment(int position) {
    	return mapFragTags.get(position);
        //String tag = mapFragTags.get(position);
        //if (tag == null)
       //     return null;
       // return getSupportFragmentManager().findFragmentByTag(tag);
        //return getSupportFragmentManager().findFragmentById(temp);
    }
	protected void initializeActionMenu(){
	    Tab tab;
	    
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				vPager.setCurrentItem(tab.getPosition());
				//refresh();
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}


	    };
	    tab = actionBar.newTab();
	    tab.setText(R.string.information);
	    tab.setTabListener(tabListener);
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab();
	    tab.setText(R.string.jobRecord);
	    tab.setTabListener(tabListener);
	    actionBar.addTab(tab);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return super.onOptionsItemSelected(item);
	        case R.id.action_call:
	        	callCompany();
	        	return super.onOptionsItemSelected(item);
	        case R.id.action_location:
	        	return super.onOptionsItemSelected(item);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void callCompany(){
		JobBackgroundFragment jobBackground = (JobBackgroundFragment)getFragment(0);
    	String phoneNo = jobBackground.tvComTel.getText().toString().trim();
    	Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
    	startActivity(intent);
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.job_details, menu);
	        return super.onCreateOptionsMenu(menu);
	    }

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
