package com.assignment.ptmsassignment;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
	private Camera cam;
	private SurfaceHolder surHolder;
	private Activity act;
	
	public CameraView(Context context, Camera cam, Activity act) {
		super(context);
		setCamera(cam);
		setActivity(act);
		surHolder = getHolder();
		surHolder.addCallback(this);
		surHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
	}
	public void setActivity(Activity act){
		this.act = act;
	}
	/*
	protected boolean checkCamera(){
		//return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}
	*/
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			cam.setPreviewDisplay(surHolder);
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(0, info);
			cam.setDisplayOrientation(getScreenRotation());
			cam.startPreview();
		
		} catch (IOException e) {
			Toast.makeText(getContext(), "Cammer cannot be initialized", Toast.LENGTH_SHORT);
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	private short getScreenRotation(){
		int rotation = act.getWindowManager().getDefaultDisplay().getRotation();
		short deg;
		switch(rotation){
			case Surface.ROTATION_0:
				deg = 0;
				break;
			case Surface.ROTATION_90:
				deg = 90;
				break;
			case Surface.ROTATION_180:
				deg = 180;
				break;
			case Surface.ROTATION_270:
				deg = 270;
				break;
			default:
				deg = 0;
				break;
		}
		return deg;
	}
	
	public void setCamera(Camera cam){
		this.cam = cam;
	}
}