package com.acrinrete.core;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.acrinrete.ComuneScreen;
import com.acrinrete.NotizieScreen;
import com.acrinrete.R;
import com.acrinrete.ui.DialogBuilder;

public class NotizieLoader extends AsyncTask<Void, Void, Void> {
	private Class<?> activity;
	private Dialog p;
	private Activity context;
	private GlobalState gs;
	private List<Notizia> n;

	public NotizieLoader(Activity context, Class<?> activity) {
		this.context = context;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		if (p == null) {
			p = DialogBuilder.createDialog(context,
					R.layout.acrinrete_loading_dialog, false);
		}
		p.show();
		gs = (GlobalState) context.getApplication();
	}

	@Override
	protected Void doInBackground(Void... args) {
		if(ComuneScreen.class.equals(activity)){
			n = gs.getNotizieComune();
		}
		else if(NotizieScreen.class.equals(activity)){
			n = gs.getNotizie();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		p.dismiss();

		if (n == null)
			Toast.makeText(context, "Connessione Assente", Toast.LENGTH_LONG)
					.show();
		else {
			context.setResult(Activity.RESULT_OK);
			context.finish();
			Intent intent = new Intent(context, activity);
			context.startActivity(intent);
		}
		
	}

}
