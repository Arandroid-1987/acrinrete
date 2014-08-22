package com.acrinrete;

import com.acrinrete.core.GlobalState;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class PrimaScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_prima_screen);
		
		GlobalState gs = (GlobalState) getApplication();
		gs.reset();
		
		new Falcao().execute();
		
	}
	
	class Falcao extends AsyncTask<Void, Void, Void>{
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			finish();
			Intent intent = new Intent(PrimaScreen.this, HomeScreen.class);
			startActivity(intent);
		}
	}

}
