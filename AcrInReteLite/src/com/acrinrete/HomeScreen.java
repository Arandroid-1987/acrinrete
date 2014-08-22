package com.acrinrete;

import static com.acrinrete.utils.Constraints.COMUNE;
import static com.acrinrete.utils.Constraints.CONTATTI;
import static com.acrinrete.utils.Constraints.FOTO;
import static com.acrinrete.utils.Constraints.METEO;
import static com.acrinrete.utils.Constraints.NEWS;
import static com.acrinrete.utils.Constraints.PRIMO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.acrinrete.core.ActivityLauncher;
import com.acrinrete.core.FirstNewsLauncher;
import com.acrinrete.core.NotizieLoader;
import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.utils.Networking;

public class HomeScreen extends Activity implements OnItemClickListener {
	private GridView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// codice da copiare in tutte le activity
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_screen);
		content = (GridView) findViewById(R.id.content);
		ImageAdapter adapter = new ImageAdapter(this);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);
		// fine codice da copiare
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		switch (position) {
		case COMUNE:
			boolean networkAvailable = Networking.isNetworkAvailable(this);
			if (networkAvailable) {
				new NotizieLoader(this, ComuneScreen.class).execute();
			}
			else{
				Toast.makeText(this, "Connessione internet non disponibile", Toast.LENGTH_SHORT).show();
			}
			break;
		case CONTATTI:
			setResult(RESULT_OK);
			finish();
			new ActivityLauncher(this, ContattiScreen.class).execute("");
			break;
		case FOTO:
			setResult(RESULT_OK);
			finish();
			new ActivityLauncher(this, FotoScreen.class).execute("");
			break;
		case METEO:
			networkAvailable = Networking.isNetworkAvailable(this);
			if (networkAvailable) {
				setResult(RESULT_OK);
				finish();
				new ActivityLauncher(this, MeteoScreen.class).execute("");
				break;
			}
			else{
				Toast.makeText(this, "Connessione internet non disponibile", Toast.LENGTH_SHORT).show();
			}
			break;
		case NEWS:
			networkAvailable = Networking.isNetworkAvailable(this);
			if (networkAvailable) {
				new NotizieLoader(this, NotizieScreen.class).execute();
			}
			else{
				Toast.makeText(this, "Connessione internet non disponibile", Toast.LENGTH_SHORT).show();
			}
			break;
		case PRIMO:
			networkAvailable = Networking.isNetworkAvailable(this);
			if (networkAvailable) {
				FirstNewsLauncher launcher = new FirstNewsLauncher(this);
				launcher.execute();
			}
			else{
				Toast.makeText(this, "Connessione internet non disponibile", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
}
