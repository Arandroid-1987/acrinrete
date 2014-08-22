package com.acrinrete;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;

import com.acrinrete.core.Notizia;
import com.acrinrete.ui.ContattiAdapter;
import com.acrinrete.ui.ImageAdapter;
import com.acrinrete.utils.Constraints;
import com.acrinrete.utils.SlidingMenu;

public class ContattiScreen extends DefaultScreen {
	private ImageView handle;
	private GridView content;
	private ListView listView;
	private ArrayAdapter<Notizia> adp;
	private List<Notizia> newsList = new ArrayList<Notizia>();
	private Notizia a;
	private Notizia b;
	private Notizia c;
	private Notizia d;
	private Notizia e;
	private Notizia f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// codice da copiare in tutte le activity
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contatti_screen);
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

		d = new Notizia();
		d.setAutore("0984955691");
		d.setTitolo("Telefono");
		newsList.add(d);

		e = new Notizia();
		e.setAutore("3207534446");
		e.setTitolo("Cellulare");
		newsList.add(e);

		a = new Notizia();
		a.setAutore("info@acrinrete.info");
		a.setTitolo("Vuoi segnalare un problema tecnico incontrato durante la navigazione?");
		newsList.add(a);

		b = new Notizia();
		b.setAutore("news@acrinrete.info");
		b.setTitolo("Vuoi scrivere alla redazione del sito per inviare un articolo? La pubblicazione di un articolo è a totale discrezione della redazione del sito.");
		newsList.add(b);

		c = new Notizia();
		c.setAutore("gianluca@acrinrete.info");
		c.setTitolo("Vuoi avere informazioni sui servizi di acrinrete.info?");
		newsList.add(c);

		f = new Notizia();
		f.setAutore("arandroid@libero.it");
		f.setTitolo("Per la tua app android!");
		newsList.add(f);

		adp = new ContattiAdapter(ContattiScreen.this, R.layout.riga_contatti,
				newsList);
		listView.setAdapter(adp);
		listView.setOnItemClickListener(this);
		registerForContextMenu(listView);

	}

	// @Override
	public void onDrawerOpened() {
		handle.setImageResource(R.drawable.freccia_aperta);
	}

	// @Override
	public void onDrawerClosed() {
		handle.setImageResource(R.drawable.freccia_chiusa);
	}

	public void onItemClick(AdapterView<?> parent, View l, int position,
			long arg3) {
		if (parent.equals(content)) {
			SlidingMenu.pastina(this, position, drawer, Constraints.CONTATTI);
		} else if (parent.equals(listView)) {
			this.openContextMenu(l);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		popolamenu(menu, info.position);

	}

	private void popolamenu(ContextMenu menu, int i) {
		if (i == 0) {
			menu.add(Menu.NONE, 0, 0, "Telefona");
		} else if (i == 1) {
			menu.add(Menu.NONE, 1, 0, "Telefona");
			menu.add(Menu.NONE, 2, 1, "Invia SMS");
		} else if (i == 2) {
			menu.add(Menu.NONE, 3, 0, "Invia E-Mail");
		} else if (i == 3) {
			menu.add(Menu.NONE, 4, 0, "Invia E-Mail");
		} else if (i == 4) {
			menu.add(Menu.NONE, 5, 0, "Invia E-Mail");
		} else if (i == 5) {
			menu.add(Menu.NONE, 6, 0, "Invia E-Mail");
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:

			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ d.getAutore())));
			break;
		case 1:
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ e.getAutore())));
			break;
		case 2:
			Uri smsUri = Uri.parse("smsto:" + e.getAutore());
			Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
			startActivity(intent);
			break;
		case 3:
			inviamail(a.getAutore());
			break;
		case 4:
			inviamail(b.getAutore());
			break;
		case 5:
			inviamail(c.getAutore());
			break;
		case 6:
			inviamail(f.getAutore());
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void inviamail(String string) {
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { string });
		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email, "Scegli il tuo Client:"));

	}

	@Override
	public void onBackPressed() {
		createDialog().show();

	}

	private AlertDialog createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ContattiScreen.this);
		builder.setTitle("Sei sicuro di voler uscire?");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		builder.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		return alert;
	}

}
