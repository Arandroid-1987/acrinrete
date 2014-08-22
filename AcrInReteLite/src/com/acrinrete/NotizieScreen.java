package com.acrinrete;

import static com.acrinrete.utils.Constraints.NEWS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.acrinrete.core.GlobalState;
import com.acrinrete.core.Notizia;
import com.acrinrete.core.OtherNewsLoader;
import com.acrinrete.ui.DialogBuilder;
import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.ui.NotiziaAdapter;
import com.acrinrete.utils.ImageUtils;
import com.acrinrete.utils.SlidingMenu;

public class NotizieScreen extends DefaultScreen implements OnClickListener {
	private ImageView imgPrimo;
	private GridView content;
	private TextView tv;
	private List<Notizia> newsList = new ArrayList<Notizia>();
	private List<Notizia> allNewsList;
	private ArrayAdapter<Notizia> adp;
	private ListView listView;
	private Dialog p;
	private FrameLayout layout_primo_piano;
	private Button moreNews;
	private GlobalState gs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// codice da copiare in tutte le activity
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_screen);
		content = (GridView) findViewById(R.id.content);
		ImageAdapter adapter = new ImageAdapter(this);
		content.setAdapter(adapter);
		content.setOnItemClickListener(this);
		drawer = (SlidingDrawer) this.findViewById(R.id.drawer);
		handle = (ImageView) this.findViewById(R.id.handle);
		drawer.setOnDrawerOpenListener(this);
		drawer.setOnDrawerCloseListener(this);
		// fine codice da copiare

		gs = (GlobalState) getApplication();

		listView = (ListView) findViewById(R.id.notizieListView);
		imgPrimo = (ImageView) findViewById(R.id.imageView1);
		tv = (TextView) findViewById(R.id.textView1);

		adp = new NotiziaAdapter(NotizieScreen.this, R.layout.notizia, newsList);

		moreNews = new Button(this);
		moreNews.setText("Carica Altre News");
		moreNews.getBackground().setAlpha(100);
		moreNews.setOnClickListener(this);
		listView.addFooterView(moreNews);

		listView.setAdapter(adp);
		listView.setOnItemClickListener(this);
		p = DialogBuilder.createDialog(this, R.layout.acrinrete_loading_dialog,
				false);
		Parti cl = new Parti();
		new Thread(cl).start();
		p.show();

		layout_primo_piano = (FrameLayout) findViewById(R.id.frameLayout);
		layout_primo_piano.setOnClickListener(this);

	}

	class Parti implements Runnable {

		public void run() {
			creaLista();
		}

		private void creaLista() {
			allNewsList = new ArrayList<Notizia>();
			allNewsList = gs.getNotizie();
			if (allNewsList == null) {
				Toast.makeText(NotizieScreen.this, "Connessione Assente",
						Toast.LENGTH_LONG).show();
			} else {
				final Notizia prima = allNewsList.get(0);
				gs.setFirstNews(prima);
				newsList.addAll(allNewsList.subList(1, allNewsList.size()));

				runOnUiThread(new Runnable() {
					public void run() {
						adp.notifyDataSetChanged();
						imgPrimo.setImageBitmap(prima.getImage());
						tv.setText(prima.getTitolo());
						p.dismiss();

					}
				});
			}
		}

	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if (parent.equals(content)) {
			SlidingMenu.pastina(this, position, drawer, NEWS);
		} else if (parent.equals(listView)) {
			visualizzaNotizia(position + 1);
		}
	}

	private void visualizzaNotizia(int position) {
		Notizia n = allNewsList.get(position);
		String[] s = { n.getTitolo(), n.getAutore(), n.getDescrizione() };
		Intent intent = new Intent(this, ArticoloScreen.class);
		intent.putExtra("dati", s);
		intent.putExtra("img",
				ImageUtils.scaleDownBitmap(n.getImage(), 165, this));
		startActivity(intent);

	}

	public void onClick(View v) {
		if (v.equals(layout_primo_piano)) {
			visualizzaNotizia(0);
		} else if (v.equals(moreNews)) {
			Thread t = new Thread() {
				public void run() {
					OtherNewsLoader onl = new OtherNewsLoader(
							NotizieScreen.this, moreNews, drawer);
					onl.execute("");
					try {
						List<Notizia> ris = onl.get();
						newsList.addAll(ris);
						runOnUiThread(new Runnable() {
							public void run() {
								adp.notifyDataSetChanged();
								if (allNewsList.size() < 200) {
									drawer.setVisibility(View.VISIBLE);
									moreNews.setVisibility(View.VISIBLE);
								}
							}
						});
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
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
			gs.resetNews();
			p = DialogBuilder.createDialog(this,
					R.layout.acrinrete_loading_dialog, false);
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
