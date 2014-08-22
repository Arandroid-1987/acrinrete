package com.acrinrete.utils;

import static com.acrinrete.utils.Constraints.COMUNE;
import static com.acrinrete.utils.Constraints.CONTATTI;
import static com.acrinrete.utils.Constraints.FOTO;
import static com.acrinrete.utils.Constraints.METEO;
import static com.acrinrete.utils.Constraints.NEWS;
import static com.acrinrete.utils.Constraints.PRIMO;
import android.app.Activity;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.acrinrete.ComuneScreen;
import com.acrinrete.ContattiScreen;
import com.acrinrete.FotoScreen;
import com.acrinrete.MeteoScreen;
import com.acrinrete.NotizieScreen;
import com.acrinrete.core.ActivityLauncher;
import com.acrinrete.core.FirstNewsLauncher;
import com.acrinrete.core.NotizieLoader;

public class SlidingMenu {

	public static void pastina(Activity context, int position,
			SlidingDrawer drawer, int activity) {
		switch (position) {
		case COMUNE:
			drawer.close();
			if (activity != position) {
				boolean networkAvailable = Networking
						.isNetworkAvailable(context);
				if (networkAvailable) {
					new NotizieLoader(context, ComuneScreen.class).execute();
				} else {
					Toast.makeText(context,
							"Connessione internet non disponibile",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case CONTATTI:
			drawer.close();
			if (activity != position) {
				context.setResult(Activity.RESULT_OK);
				context.finish();
				new ActivityLauncher(context, ContattiScreen.class).execute("");
			}
			break;
		case FOTO:
			drawer.close();
			if (activity != position) {
				context.setResult(Activity.RESULT_OK);
				context.finish();
				new ActivityLauncher(context, FotoScreen.class).execute("");
			}
			break;
		case METEO:
			drawer.close();
			if (activity != position) {
				boolean networkAvailable = Networking
						.isNetworkAvailable(context);
				if (networkAvailable) {
					context.setResult(Activity.RESULT_OK);
					context.finish();
					new ActivityLauncher(context, MeteoScreen.class)
							.execute("");
				} else {
					Toast.makeText(context,
							"Connessione internet non disponibile",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case NEWS:
			drawer.close();
			if (activity != position) {
				boolean networkAvailable = Networking
						.isNetworkAvailable(context);
				if (networkAvailable) {
					new NotizieLoader(context, NotizieScreen.class).execute();
				} else {
					Toast.makeText(context,
							"Connessione internet non disponibile",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case PRIMO:
			drawer.close();
			if (activity != position) {
				boolean networkAvailable = Networking
						.isNetworkAvailable(context);
				if (networkAvailable) {
					FirstNewsLauncher task = new FirstNewsLauncher(context);
					task.execute();
				} else {
					Toast.makeText(context,
							"Connessione internet non disponibile",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}

}
