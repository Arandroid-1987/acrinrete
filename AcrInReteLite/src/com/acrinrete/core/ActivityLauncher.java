package com.acrinrete.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.acrinrete.R;
import com.acrinrete.ui.DialogBuilder;

public class ActivityLauncher extends AsyncTask<String, Void, Void> {
	private Class<?> activity;
	private Dialog p;
	private Activity context;

	public ActivityLauncher(Activity context, Class<?> activity) {
		super();
		this.context = context;
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (p == null)
			p = DialogBuilder.createDialog(context,
					R.layout.acrinrete_loading_dialog_freezed, false);
		p.show();
	}

	@Override
	protected Void doInBackground(String... params) {
		Intent intent = new Intent(context, activity);
		context.startActivity(intent);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		p.dismiss();
	}

}
