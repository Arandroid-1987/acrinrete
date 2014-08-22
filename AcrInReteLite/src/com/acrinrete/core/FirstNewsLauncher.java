package com.acrinrete.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import com.acrinrete.ArticoloScreen;
import com.acrinrete.R;
import com.acrinrete.ui.DialogBuilder;
import com.acrinrete.utils.ImageUtils;

public class FirstNewsLauncher extends AsyncTask<Void, Void, Void> {
	private Dialog p;
	private Activity context;
	private GlobalState gs;
	private Notizia n;

	public FirstNewsLauncher(Activity context) {
		this.context = context;
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
		n = gs.getFirstNews();
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
			String[] s = { n.getTitolo(), n.getAutore(), n.getDescrizione() };
			Intent intent = new Intent(context, ArticoloScreen.class);
			intent.putExtra("dati", s);
			intent.putExtra("primaNotizia", true);
			if (n.getImage() != null)
				intent.putExtra("img",
						ImageUtils.scaleDownBitmap(n.getImage(), 165, context));
			else {
				intent.putExtra("img", (Bitmap) null);
			}
			context.startActivity(intent);
		}
		
	}

}
