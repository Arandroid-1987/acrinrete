package com.acrinrete;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.acrinrete.core.GlobalState;
import com.acrinrete.ui.DialogBuilder;
import com.acrinrete.utils.SlidingMenu;

public class DefaultScreen extends Activity implements OnItemClickListener,
		OnDrawerOpenListener, OnDrawerCloseListener, SensorEventListener {
	protected SlidingDrawer drawer;
	protected ImageView handle;
	protected GridView content;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private float mLastX;
	private boolean mInitialized;
	private final float NOISE = (float) 2.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// codice da copiare in tutte le activity
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(R.layout.activity_news_screen);
		// content = (GridView) findViewById(R.id.content);
		// ImageAdapter adapter = new ImageAdapter(this);
		// content.setAdapter(adapter);
		// content.setOnItemClickListener(this);
		// drawer = (SlidingDrawer) this.findViewById(R.id.drawer);
		// handle = (ImageView) this.findViewById(R.id.handle);
		// drawer.setOnDrawerOpenListener(this);
		// drawer.setOnDrawerCloseListener(this);
		// fine codice da copiare
		mInitialized = false;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	//

	// @Override
	public void onDrawerOpened() {
		handle.setImageResource(R.drawable.freccia_aperta);
	}

	// @Override
	public void onDrawerClosed() {
		handle.setImageResource(R.drawable.freccia_chiusa);
	}

	@Override
	public void onBackPressed() {
		Dialog d = DialogBuilder.createExitDialog(this);
		d.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_menu:
			drawer.animateOpen();
			break;
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsScreen.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return false;
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		SlidingMenu.pastina(this, position, drawer, -1);
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	public void onSensorChanged(SensorEvent event) {
		GlobalState gs = (GlobalState) getApplication();
		if (gs.isAccelerometer_on()) {
			float x = event.values[0];
			if (!mInitialized) {
				mLastX = x;
				mInitialized = true;
			} else {
				float deltaX = Math.abs(mLastX - x);
				if (deltaX < NOISE)
					return;
				mLastX = x;
			}
			if (x > 5) {
				if (!drawer.isOpened())
					drawer.animateToggle();
			} else if (x < -5) {
				if (drawer.isOpened())
					drawer.animateOpen();
			}
		}
	}

}
