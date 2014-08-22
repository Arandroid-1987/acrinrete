package com.acrinrete.core;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class OtherNewsLoader extends AsyncTask<String, Void, List<Notizia>> {
	private Activity context;
	private Button button;
	private GlobalState gs;
	private SlidingDrawer drawer;

	public OtherNewsLoader(Activity context, Button button, SlidingDrawer drawer) {
		this.context = context;
		this.button = button;
		this.drawer = drawer;
	}

	@Override
	protected void onPreExecute() {
		gs = (GlobalState) context.getApplication();
		context.runOnUiThread(new Runnable() {

			public void run() {
				drawer.setVisibility(View.INVISIBLE);
				button.setVisibility(View.INVISIBLE);
				Toast t = Toast
						.makeText(
								context,
								"Caricamento in corso...\nContinua pure a navigare tra gli articoli, verrai notificato!",
								Toast.LENGTH_LONG);
				t.show();
			}
		});

	}

	@Override
	protected List<Notizia> doInBackground(String... arg0) {
		List<Notizia> news = gs.getMoreNotizie();
		return news;
	}

	@Override
	protected void onPostExecute(List<Notizia> result) {
		context.runOnUiThread(new Runnable() {

			public void run() {
				Toast.makeText(context, "Notizie caricate", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
