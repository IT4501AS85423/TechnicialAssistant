package com.assignment.ptmsassignment;

import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

public class PhotoViewActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		byte[] bitmap = (byte[]) getIntent().getByteArrayExtra("selectedImg");
		Bitmap bmp = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
		imageView.setImageBitmap(bmp);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return super.onOptionsItemSelected(item);
	    }
		return super.onOptionsItemSelected(item);
	}
}
