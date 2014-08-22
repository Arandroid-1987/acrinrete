package com.acrinrete;

import com.acrinrete.core.GlobalState;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class SettingsScreen extends Activity implements OnCheckedChangeListener{
	private GlobalState gs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_screen);
		ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton1);
		gs = (GlobalState) getApplication();
		boolean accelerometer_on = gs.isAccelerometer_on();
		tb.setChecked(accelerometer_on);
		tb.setOnCheckedChangeListener(this);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		gs.setAccelerometer_on(isChecked);
		
	}

}
