package com.assignment.ptmsassignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {
	private int menuTakePicture = Menu.FIRST;
	private int menuShowPhoto = Menu.FIRST+1;
	private Camera cam;
	int picCount = 0;
	private CameraView camView;
	private String jobNo;
	//String fileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		camView =new CameraView(this, cam, this);
		setContentView(camView);
		jobNo = getIntent().getStringExtra("jobNo");
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//fileName= jobNo+"_"+picCount+".jpg";
	}
	
	protected void onResume(){
		cam = Camera.open();
		camView.setCamera(cam);
		super.onResume();
	}
	public void setJobNo(String jobNo){
		this.jobNo = jobNo;
	}
	protected void onPause(){
		cam.stopPreview();
		cam.release();
		cam = null;
		super.onPause();
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.camera, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
        	case android.R.id.home:
        		finish();
        		break;
			case R.id.action_takePhoto:
				cam.takePicture(new ShutterCallback(){

					@Override
					public void onShutter() {
						// TODO Auto-generated method stub
						
					}}, new PictureCallback(){

						@Override
						public void onPictureTaken(byte[] data, Camera camera) {
							// TODO Auto-generated method stub
							
						}}, camPicCallback);
				Log.i("Take a Photo", "Snapped phtoto");
				break;
			case R.id.action_showPhoto:
				File path = Environment.getExternalStoragePublicDirectory("TechAssistant_pic/"+jobNo);
				path.mkdir();
				File file = new File(path,jobNo+"_"+picCount+".jpg");
			    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//File path = Environment.getExternalStoragePublicDirectory("TechAssistant_pic");
				//path.mkdir();
				//File file = new File(path,jobNo+"_"+picCount+".jpg");
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				startActivityForResult(intent, R.id.action_showPhoto);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == R.id.action_showPhoto && resultCode == Activity.RESULT_OK) {
	        try {
	        	//Log.i("TEST", "TEST");
				InputStream inputStream = getContentResolver().openInputStream(data.getData());
				Intent intent = new Intent(CameraActivity.this, PhotoViewActivity.class);
				//intent.putExtra("selectedImg", BitmapFactory.decodeStream(inputStream));
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		        byte[] bytes = stream.toByteArray(); 
				intent.putExtra("selectedImg", bytes);
				startActivity(intent);
	        } catch (FileNotFoundException e) {
				Toast.makeText(this, "Cannot load photo", Toast.LENGTH_SHORT).show();
			}
	    }
	}
	
	PictureCallback camPicCallback = new PictureCallback(){
		public void onPictureTaken (byte[] data, Camera cam){
			try{
				File path = Environment.getExternalStoragePublicDirectory("TechAssistant_pic/"+jobNo);
				path.mkdir();
				File file = new File(path.getPath(),jobNo+"_"+picCount+".jpg");
				if(file.exists()){
					++picCount;
					Log.i("Filename", file.getName());
					onPictureTaken(data, cam);
				}
				FileOutputStream outStream = new FileOutputStream(file);
				outStream.write(data);
				outStream.close();
			}catch(IOException e){
				Toast.makeText(getApplicationContext(), "Cannot store the taken photo", Toast.LENGTH_SHORT).show();
			}
			cam.startPreview();
		}
	};
}
