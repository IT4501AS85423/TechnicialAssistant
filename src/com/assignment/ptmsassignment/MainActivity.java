package com.assignment.ptmsassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private DrawerLayout drawerLayout;
    private ListView nav;
    private ActionBarDrawerToggle drawerToggle;
	private JSONHelper jHelper = null;
    private String[] navItems;
    private String fragTitle = "";
    private String preTitle = "";
    private int preIndex = 0;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        fetchJSON();
        initializeNav(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        //Log.i("UserID",userId);
    }

    public void initializeView(){
    	drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nav = (ListView) findViewById(R.id.nav);
    }
    
    public void initializeNav(Bundle savedInstanceState){
        fragTitle = (String) getTitle();
        preTitle = (String) getTitle();
    	navItems = getResources().getStringArray(R.array.navItems);

        nav.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, navItems));
        nav.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		            selectItem(position);	
		            preIndex = position;
			}
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.drawable.menu,R.string.drawer_open,R.string.drawer_close) {
            @Override
        	public void onDrawerClosed(View view) {
                getActionBar().setTitle(fragTitle);
                invalidateOptionsMenu();
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(preTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean opened = drawerLayout.isDrawerOpen(nav);
        menu.findItem(R.id.action_search).setVisible(!opened);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        	case R.id.action_search:
        		break;
        	default:
        }
            return super.onOptionsItemSelected(item);
    }

    private void selectItem(int index) {

        nav.setItemChecked(index, true);
        getActionBar().setTitle((fragTitle = navItems[index]));
        drawerLayout.closeDrawer(nav);
        
        FragmentManager fragManager = getFragmentManager();
        
        switch(index){
        	case 0:
                fragManager.beginTransaction().replace(R.id.content, new MainFragment()).commit();
                break;
        	case 1:
        		fragManager.beginTransaction().replace(R.id.content, new ServiceJobsFragment()).commit();
        		
        		break;
        	case 2:
        		logoutDialog();
                nav.setItemChecked(preIndex, true);
 				break;
        }


    }

    private void logoutDialog(){
    	AlertDialog.Builder logout = new AlertDialog.Builder(this);
    	logout.setTitle("Confirmaton");
    	logout.setMessage("Do you want to logout?");
    	//logout.setIcon(icon);
    	logout.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
    	logout.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
 				startActivity(intent);
 				finish();
			}
		});

    	logout.create();
    	logout.show();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
    
    private void fetchJSON(){
  	   if(jHelper == null||jHelper.getStatus().equals(AsyncTask.Status.FINISHED)){
  		   jHelper = new JSONHelper();
  		   jHelper.execute();
  	   }
     }

}