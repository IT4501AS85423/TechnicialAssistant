package com.assignment.ptmsassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
    private DrawerLayout drawerLayout;
    private ListView nav;
    private ActionBarDrawerToggle drawerToggle;
	private JSONHelper jHelper = null;
    private String[] navItems;
    private String fragTitle = "";
    private String preTitle = "";
    private int preIndex = 0;
    EditText etSearch;
    protected String staffNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        staffNo = getIntent().getStringExtra("staffNo");
        fetchJSON(staffNo);
        initializeNav(savedInstanceState);
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
        //getActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF0000));
       // getActionBar().setBackgroundDrawable(getWallpaper());
        //getActionBar().setSplitBackgroundDrawable(new ColorDrawable(R.color.green_blue));
        //getActionBar().setStackedBackgroundDrawable(new ColorDrawable(R.color.green_blue));
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
        Fragment mainFrag = (Fragment)getFragmentManager().findFragmentByTag("mainFrag");
        Fragment serviceJobFrag = (Fragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
        //if(mainFrag != null)
        	//inflater.inflate(R.menu.main, menu);
        if(serviceJobFrag != null){
        	inflater.inflate(R.menu.service_jobs, menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            initializeSearchView(searchView);
           MenuItemCompat.setOnActionExpandListener(searchItem, new OnActionExpandListener(){

				@Override
				public boolean onMenuItemActionCollapse(MenuItem arg0) {
	                ServiceJobsFragment frag = (ServiceJobsFragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
	                frag.searchKey =null;
	                frag.initializeCursorAdapter();
					
					return true;
				}

				@Override
				public boolean onMenuItemActionExpand(MenuItem arg0) {
					// TODO Auto-generated method stub
					return true;
				}});

        }
        return super.onCreateOptionsMenu(menu);
    }
    private void initializeSearchView(SearchView searchView){
    	searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        int searchCloseButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) searchView.findViewById(searchCloseButtonId);
        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);  
        etSearch = (EditText) searchView.findViewById(searchSrcTextId);  
        closeButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
                ServiceJobsFragment frag = (ServiceJobsFragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
                frag.searchKey =null;
                frag.initializeCursorAdapter();
                etSearch.setText(null);
        	}
        });
        	
        searchView.setQueryHint("Search by Job No...");
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
        	 
            @Override
            public boolean onQueryTextChange(String str) {

                ServiceJobsFragment frag = (ServiceJobsFragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
                frag.searchKey = str;
                //frag.initializeCursorAdapter(str);
            	ListView list = (ListView)frag.getView().findViewById(R.id.listView);
            	((SimpleCursorAdapter)list.getAdapter()).getFilter().filter(str);
                return false;
            }
 
            @Override
            public boolean onQueryTextSubmit(String str) {
            	Toast.makeText(getApplicationContext(), "Searching " + str, preIndex).show();
                ServiceJobsFragment frag = (ServiceJobsFragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
                frag.searchKey = str;
                //frag.initializeCursorAdapter(str);
            	ListView list = (ListView)frag.getView().findViewById(R.id.listView);
            	((SimpleCursorAdapter)list.getAdapter()).getFilter().filter(str);

                return false;
            }
 
        });

    } 
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean opened = drawerLayout.isDrawerOpen(nav);
        int[] items = {R.id.action_search,R.id.action_filter,R.id.action_addJob, R.id.action_refresh, R.id.action_sortBy};
        for(int i=0; i<items.length; i++){
        	MenuItem item = menu.findItem(items[i]);
        	if(item != null)
        		item.setVisible(!opened);
        	}
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
    	Fragment frag;
        switch(item.getItemId()) {
        	case R.id.action_refresh:
        		frag = (Fragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
        		if(frag != null)
        			((ServiceJobsFragment) frag).refresh();
        		break;
        	case R.id.action_search:
        		break;
        	case R.id.action_addJob:
        		frag = (Fragment)getFragmentManager().findFragmentByTag("serviceJobFrag");
        		if(frag != null)
        			((ServiceJobsFragment) frag).addJob();
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
                fragManager.beginTransaction().replace(R.id.content, new MainFragment(),"mainFrag").commit();
                break;
        	case 1:
        		fragManager.beginTransaction().replace(R.id.content, new ServiceJobsFragment(),"serviceJobFrag").commit();
        		
        		break;
        	case 2:
        		break;
        	case 3:
        		break;
        		
        	default:
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
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        drawerToggle.onConfigurationChanged(config);
    }
    
    private void fetchJSON(String staffNo){
  	   if(jHelper == null||jHelper.getStatus().equals(AsyncTask.Status.FINISHED)){
  		   jHelper = new JSONHelper(staffNo);
  		   jHelper.execute();
  	   }
     }
    
}