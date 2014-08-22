package com.acrinrete;

import static com.acrinrete.utils.Constraints.COMUNE;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;

import com.acrinrete.core.GlobalState;
import com.acrinrete.core.Notizia;
import com.acrinrete.ui.ComuneAdapter;
import com.acrinrete.ui.DialogBuilder;
import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.utils.SlidingMenu;

public class ComuneScreen extends DefaultScreen implements OnClickListener {
	private GridView content;
	private List<Notizia> newsList = new ArrayList<Notizia>();
	private List<Notizia> allNewsList;
	private ArrayAdapter<Notizia> adp;
	private ListView listView;
	private Dialog p;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// codice da copiare in tutte le activity
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comune_screen);
		content = (GridView) findViewById(R.id.content);
		ImageAdapter adapter = new ImageAdapter(this);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);
		drawer = (SlidingDrawer) this.findViewById(R.id.drawer);
		handle = (ImageView) this.findViewById(R.id.handle);
		drawer.setOnDrawerOpenListener(this);
		drawer.setOnDrawerCloseListener(this);
		// fine codice da copiare

		listView = (ListView) findViewById(R.id.notizieListView);

		adp = new ComuneAdapter(ComuneScreen.this, R.layout.notizia_comune, newsList);
		listView.setAdapter(adp);
		listView.setOnItemClickListener(this);
		p = DialogBuilder.createDialog(this, R.layout.acrinrete_loading_dialog, true);
		Parti cl = new Parti();
		new Thread(cl).start();
		p.show();

	}

	class Parti implements Runnable {

		public void run() {
			creaLista();
		}

		private void creaLista() {
			allNewsList = new ArrayList<Notizia>();
			GlobalState gs = (GlobalState) getApplication();
			allNewsList = gs.getNotizieComune();
			newsList.addAll(allNewsList.subList(0, allNewsList.size()));

			runOnUiThread(new Runnable() {
				public void run() {
					adp.notifyDataSetChanged();
					p.dismiss();
				}
			});
		}

	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (parent.equals(content)) {
			SlidingMenu.pastina(this, position, drawer, COMUNE);
			// fine codice da copiare
		} else if (parent.equals(listView)) {
			visualizzaNotizia(position);
		}
	}

	private void visualizzaNotizia(int position) {
		Notizia n = newsList.get(position);
		String[] s = { n.getTitolo(), n.getAutore(), n.getDescrizione() };
		Intent intent = new Intent(this, ArticoloScreen.class);
		intent.putExtra("dati", s);
		startActivity(intent);
	}

	public void onClick(View v) {
		visualizzaNotizia(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_news_screen, menu);
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
		case R.id.menu_reload:
			GlobalState gs = (GlobalState) getApplication();
			gs.resetComune();
			p = DialogBuilder.createDialog(this, R.layout.acrinrete_loading_dialog, false);
			Parti cl = new Parti();
			new Thread(cl).start();
			p.show();
			break;
		default:
			break;
		}
		return false;
	}
	

}
